// Runtime probe for SuperTrailsPro on a real Paper server (default 26.3).
//   node probe.js old   -> the jar currently deployed on the server (plugins/supertrailspro-1.0.jar)
//   node probe.js new   -> target/supertrailspro-1.0.jar
//   node probe.js report -> compares the two probes
// Joins with a real client, walks every GUI (clicking every slot, following every window that opens), activates every
// trail id from the console while moving the player, and records chat + server-side exceptions per step.
const { spawnSync } = require('child_process');
const fs = require('fs');
const path = require('path');
const mineflayer = require('mineflayer');
const { Rcon } = require('rcon-client');

const variant = process.argv[2];
const ROOT = path.resolve(__dirname, '..', '..');
const TESTS = path.join(ROOT, 'tests');
const STAGE = path.join(TESTS, '.stage');
const COMPOSE = ['compose', '-f', path.join(TESTS, 'docker-compose.yml')];
const PLAYER = 'TrailTester';
const CLIENT_VERSION = process.env.CLIENT_VERSION || '26.1';
const sleep = (ms) => new Promise((r) => setTimeout(r, ms));
const dc = (...a) => spawnSync('docker', [...COMPOSE, ...a], { encoding: 'utf8', maxBuffer: 1 << 28 });

if (variant === 'report') { report(); process.exit(0); }
if (!['old', 'new'].includes(variant)) { console.error('usage: node probe.js old|new|report'); process.exit(2); }

const NOISE = /No key layers|Unsafe|sun\.misc|Mojang|sessionserver|Read timed out|joml|SERVER IS RUNNING IN OFFLINE|lost connection|Disconnected|Advanced terminal/i;
const ERR = /Exception|SEVERE|\bERROR\]|at me\.(saynt|kvq)\.|NoClassDef|Could not pass event|Error occurred while/i;
const norm = (s) => String(s).replace(/§./g, '').replace(/\[\d\d:\d\d:\d\d[^\]]*\]/g, '').replace(/mc-1\s+\|/g, '').replace(/[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}/gi, '<uuid>').replace(/\s+/g, ' ').trim();

let logSeen = 0;
function newErrors() {
  const lines = dc('logs', '--no-color', 'mc').stdout.split(/\r?\n/);
  const fresh = lines.slice(logSeen);
  logSeen = lines.length;
  return fresh.filter((l) => ERR.test(l) && !NOISE.test(l)).map(norm);
}

let rcon, bot; const chat = [];
const cmd = async (c) => { try { return norm(await rcon.send(c)); } catch (e) { return 'RCON-ERROR ' + e.message.replace(/\d+/g, 'N'); } };
const takeChat = () => chat.splice(0, chat.length);
const itemDesc = (it) => (it ? norm(JSON.stringify({ n: it.name, c: it.count, dn: it.displayName })) : null);
const dumpWin = (w) => (w ? { title: norm(JSON.stringify(w.title)), type: w.type, slots: w.slots.map((it, i) => (it && i < w.inventoryStart ? [i, itemDesc(it)] : null)).filter(Boolean) } : null);
async function waitWin(ms = 4000) { const t0 = Date.now(); while (!bot.currentWindow && Date.now() - t0 < ms) await sleep(100); await sleep(500); return bot.currentWindow; }
async function closeWin() { if (bot.currentWindow) { bot.closeWindow(bot.currentWindow); await sleep(500); } }

const out = { variant, startup: {}, gui: [], ids: {}, errors: {} };
const addErr = (where, errs) => { if (errs.length) for (const e of errs) (out.errors[e] = out.errors[e] || []).push(where); };

async function start() {
  dc('down', '-v');
  fs.rmSync(path.join(STAGE, 'data'), { recursive: true, force: true });
  fs.mkdirSync(path.join(STAGE, 'plugins'), { recursive: true });
  for (const f of fs.readdirSync(path.join(STAGE, 'plugins'))) if (f.endsWith('.jar')) fs.rmSync(path.join(STAGE, 'plugins', f));
  const jar = variant === 'old'
    ? path.resolve(ROOT, '..', '..', 'plugins', 'supertrailspro-1.0.jar')
    : path.join(ROOT, 'target', 'supertrailspro-1.1.jar');
  fs.copyFileSync(jar, path.join(STAGE, 'plugins', path.basename(jar)));
  // the production config, so the probe exercises what the server really runs
  const cfg = path.resolve(ROOT, '..', '..', 'plugins', 'SuperTrailsPro');
  const dst = path.join(STAGE, 'data', 'plugins', 'SuperTrailsPro');
  fs.mkdirSync(dst, { recursive: true });
  fs.cpSync(path.join(cfg, 'config.yml'), path.join(dst, 'config.yml'));
  fs.cpSync(path.join(cfg, 'languages'), path.join(dst, 'languages'), { recursive: true });
  const r = dc('up', '-d', 'mc');
  if (r.status !== 0) throw new Error(r.stderr);
  for (let i = 0; i < 160; i++) { if (/Done \(/.test(dc('logs', '--no-color', 'mc').stdout)) return; await sleep(3000); }
  throw new Error('server did not start');
}

async function joinBot() {
  bot = mineflayer.createBot({ host: '127.0.0.1', port: 25600, username: PLAYER, version: CLIENT_VERSION, auth: 'offline', skipValidation: true });
  bot.on('message', (m) => chat.push(norm(m.toString())));
  bot.on('error', (e) => console.log('bot error', e.message));
  bot.on('kicked', (r) => console.log('kicked', r));
  bot.physicsEnabled = false;   // the bot only opens menus; its 26.1 physics would make Paper kick it for invalid movement
  await new Promise((res, rej) => { bot.once('spawn', res); bot.once('end', () => rej(new Error('bot could not join'))); });
  bot.physicsEnabled = false;
  await sleep(4000);
}

async function exploreGuis() {
  const seen = new Set();
  const queue = [[]];                       // each entry = list of slot clicks starting from /trails
  let opened = 0;
  while (queue.length && opened < 80) {
    const pathClicks = queue.shift();
    await closeWin(); takeChat();
    bot.chat('/trails'); let w = await waitWin();
    let ok = !!w;
    for (const s of pathClicks) { if (!w || !w.slots[s]) { ok = false; break; } try { await bot.clickWindow(s, 0, 0); } catch (e) { } await sleep(700); w = bot.currentWindow; if (!w) { ok = false; break; } }
    if (!ok || !w) continue;
    const d = dumpWin(w);
    const key = d.title + '|' + d.slots.map((x) => x[0]).join(',');
    if (seen.has(key)) continue;
    seen.add(key); opened++;
    const errs0 = newErrors(); addErr('open ' + pathClicks.join('>'), errs0);
    out.gui.push({ path: pathClicks.join('>'), chat: takeChat(), window: d, errors: errs0 });
    for (const [s] of d.slots) {
      // re-open the same window for every click so clicks do not influence each other
      await closeWin(); takeChat();
      bot.chat('/trails'); let w2 = await waitWin(); let good = !!w2;
      for (const p of pathClicks) { if (!w2 || !w2.slots[p]) { good = false; break; } try { await bot.clickWindow(p, 0, 0); } catch (e) { } await sleep(600); w2 = bot.currentWindow; }
      if (!good || !w2 || !w2.slots[s]) continue;
      newErrors();
      try { await bot.clickWindow(s, 0, 0); } catch (e) { }
      await sleep(700);
      const after = bot.currentWindow;
      const errs = newErrors();
      const entry = { path: [...pathClicks, s].join('>'), chat: takeChat(), opened: after ? norm(JSON.stringify(after.title)) : null, errors: errs };
      out.gui.push(entry);
      addErr('click ' + entry.path, errs);
      if (after && pathClicks.length < 3) queue.push([...pathClicks, s]);
    }
  }
}

async function exploreIds() {
  const ids = [];
  for (let i = 1; i <= 140; i++) ids.push(i);
  ids.push(201);
  for (let i = 300; i <= 330; i++) ids.push(i);
  for (let i = 400; i <= 430; i++) ids.push(i);
  await closeWin();
  for (const id of ids) {
    newErrors(); takeChat();
    const msg = await cmd(`trailsid ${id} ${PLAYER}`);
    await sleep(250);
    for (let k = 0; k < 3; k++) { await cmd(`execute as ${PLAYER} at ${PLAYER} run tp ${PLAYER} ~1.5 ~ ~`); await sleep(350); }
    const errs = newErrors();
    out.ids[id] = { msg, chat: takeChat(), errors: errs };
    addErr('trailsid ' + id, errs);
  }
}

(async () => {
  try {
    await start();
    logSeen = dc('logs', '--no-color', 'mc').stdout.split(/\r?\n/).length;
    const startupLog = dc('logs', '--no-color', 'mc').stdout.split(/\r?\n/);
    out.startup = { enabled: startupLog.some((l) => /Enabling SuperTrailsPro/.test(l)), loaded: startupLog.filter((l) => /\[SuperTrailsPro\]/.test(l)).map(norm), errors: startupLog.filter((l) => ERR.test(l) && !NOISE.test(l)).map(norm) };
    addErr('startup', out.startup.errors);
    rcon = await Rcon.connect({ host: '127.0.0.1', port: 25601, password: 'testpass', timeout: 60000 });
    out.startup.plugins = await cmd('plugins');
    await joinBot();
    await cmd(`op ${PLAYER}`); await cmd(`gamemode survival ${PLAYER}`);
    await cmd('difficulty peaceful'); await cmd('gamerule doMobSpawning false');
    await sleep(1500); newErrors();
    await exploreGuis();
    await exploreIds();
    fs.mkdirSync(STAGE, { recursive: true });
    fs.writeFileSync(path.join(STAGE, `probe-${variant}.json`), JSON.stringify(out, null, 1));
    console.log(`probe ${variant}: ${out.gui.length} gui steps, ${Object.keys(out.ids).length} trail ids, ${Object.keys(out.errors).length} distinct error lines`);
    try { bot.quit(); } catch (e) { }
    dc('down', '-v');
    process.exit(0);
  } catch (e) {
    console.error(e);
    fs.writeFileSync(path.join(STAGE, `probe-${variant}.partial.json`), JSON.stringify(out, null, 1));
    dc('down', '-v'); process.exit(1);
  }
})();

function report() {
  const load = (v) => JSON.parse(fs.readFileSync(path.join(STAGE, `probe-${v}.json`), 'utf8'));
  const o = load('old'), n = load('new');
  for (const [name, p] of [['OLD (deployed)', o], ['NEW', n]]) {
    console.log(`\n== ${name}: plugin enabled=${p.startup.enabled}, ${p.gui.length} gui steps, ${Object.keys(p.ids).length} ids`);
    const errs = Object.entries(p.errors);
    console.log(`   ${errs.length} distinct server-side error line(s)`);
    for (const [e, where] of errs.slice(0, 25)) console.log(`   - x${where.length} ${e.slice(0, 200)}  [first: ${where[0]}]`);
  }
  const idMsg = (p, id) => (p.ids[id] || {}).msg;
  const diffIds = Object.keys(n.ids).filter((id) => idMsg(o, id) !== idMsg(n, id));
  console.log(`\ntrailsid replies that differ old vs new: ${diffIds.length}${diffIds.length ? ' -> ' + diffIds.slice(0, 20).join(',') : ''}`);
  const guiTitles = (p) => new Set(p.gui.map((g) => g.window ? g.window.title : null).filter(Boolean));
  const to = guiTitles(o), tn = guiTitles(n);
  console.log(`GUIs reachable: old=${to.size} new=${tn.size}; only in old: ${[...to].filter((t) => !tn.has(t)).length}, only in new: ${[...tn].filter((t) => !to.has(t)).length}`);
}
