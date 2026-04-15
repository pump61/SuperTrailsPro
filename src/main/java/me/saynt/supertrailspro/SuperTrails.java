package me.saynt.supertrailspro;

import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import me.saynt.supertrailspro.community.FileSender;
import me.saynt.supertrailspro.community2.CommunityManager;
import me.saynt.supertrailspro.custom.CustomMenusLoader;
import me.saynt.supertrailspro.data.DataManager;
import me.saynt.supertrailspro.data.NPCManager;
import me.saynt.supertrailspro.data.mysql.DBUploader;
import me.saynt.supertrailspro.data.mysql.MySqlManager;
import me.saynt.supertrailspro.data.threads.ConnectThread;
import me.saynt.supertrailspro.downloader.Dl;
import me.saynt.supertrailspro.events.EventManager;
import me.saynt.supertrailspro.eventtrails.EventTrails;
import me.saynt.supertrailspro.inventories.GuiManager;
import me.saynt.supertrailspro.inventory.RainInventory;
import me.saynt.supertrailspro.lang.L;
import me.saynt.supertrailspro.lang.LanguageManager;
import me.saynt.supertrailspro.modules.Modules;
import me.saynt.supertrailspro.particlelib.PlayParticle;
import me.saynt.supertrailspro.permissionchecker.PermissionChecker;
import me.saynt.supertrailspro.spawn.BlockSpawn;
import me.saynt.supertrailspro.spawn.NewBlock;
import me.saynt.supertrailspro.spawn.RainsPackets;
import me.saynt.supertrailspro.trails.Lister;
import me.saynt.supertrailspro.trails.Rains;
import me.saynt.supertrailspro.trails.Tab;
import me.saynt.supertrailspro.trails.Task;
import me.saynt.supertrailspro.trails.TrailsUtil;
import me.saynt.supertrailspro.trails.modes.Modes;
import me.saynt.supertrailspro.wings.CustomWings;
import me.saynt.supertrailspro.wings.WingsManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class SuperTrails extends JavaPlugin implements Listener {
   public static Plugin p;
   public static ServerVersionsEnum currentversion = null;
   public static boolean end = true;
   public static String key = null;
   public static boolean permview;
   public static GuiManager gui;
   ArrayList<String> msinfo = new ArrayList();
   long time = 0L;
   public static List<Player> toggled = new ArrayList();

   public void onEnable() {
      this.msinfo.ensureCapacity(50);
      this.updatems();
      this.initializePlugin();
      new NotSecret();
      this.addms("Plugin initialization complete");
      this.setServerVersion();
      boolean headless = GraphicsEnvironment.isHeadless();
      if (headless) {
         customproperty();
      }

      permview = STUtils.optionb("OnlyPermittedPlayersCanSeeTrails");
      this.updatems();
      MySqlManager.setUp();
      this.addms("MySQL setup complete");
      LanguageManager.LoadLangs();
      this.addms("Language load complete");
      this.registerEvents();
      this.addms("Events registration complete");
      PlayParticle.initialize();
      this.addms("Particles initialized");
      WingsManager.loadFX();
      this.addms("Wings FX initialized");
      Rains.initialize();
      this.addms("Rains initialized");
      BlockSpawn.prepareblocks();
      TrailsUtil.presetTrails();
      this.addms("Trails initialized");
      Task.run();
      this.addms("Task startup complete");
      RainInventory.initialize();
      this.addms("Rains inventory initialization complete");
      EventTrails.load();
      this.addms("Event trails initialized");
      CustomWings.preset();
      this.addms("Custom wings loaded");
      Modes.b();
      this.addms("Modes loaded");
      Debug();
      Modules.load();
      this.addms("Modules initialized");
      Tab.prepare();
      this.addms("Tab autocomplete initialized");
      CustomMenusLoader.load();
      this.addms("Custom menus initialized");
      this.a();
      GuiManager.setupInvs();
      this.addms("New gui initialized");
      DataManager.loadForEveryOne();
      this.addms("Data loaded");
      PermissionChecker.start();
      this.addms("Permission checker loaded");
      NPCManager.initialize();
      end = true;
   }

   public void onDisable() {
      GuiManager.unload();
      Rains.getI().removeAll();
   }

   public void login() {
      String licenseKey = this.getConfig().getString("LicenseKey");
      if (licenseKey != null && !licenseKey.equals("")) {
         key = licenseKey;
      }
   }

   public static void disableOld() {
      Plugin old = Bukkit.getPluginManager().getPlugin("SuperTrails");
      if (old != null) {
         Bukkit.getPluginManager().disablePlugin(old);
         PluginMessages.Error("Sorry, old SuperTrails incompatible with SuperTrailsPro");
      }
   }

   public static void Debug() {}

   public static void customproperty() {
      PluginMessages.Debug("Headless mode is on");
      System.setProperty("java.awt.headless", "true");
   }

   public void addms(String msg) {
      long elapsed = System.currentTimeMillis() - this.time;
      this.msinfo.add(msg + " (" + elapsed + " ms)");
      this.updatems();
   }

   public void updatems() {
      this.time = System.currentTimeMillis();
   }

   public void printInfo() {
      for (String s : this.msinfo) {
         getLogger().info(s);
      }
   }

   public static ServerVersionsEnum getCurrentVersion() {
      return currentversion;
   }

   public void registerEvents() {
      EventManager.RegisterEvents();
   }

   public void initializePlugin() {
      p = this;
      Def.a();
      this.saveDefaultConfig();
      this.login();
      ConnectThread thread = new ConnectThread();
      thread.start();
      Lister.create();
   }

   public void setServerVersion() {
      currentversion = ServerVersion.getCurrent();
      if (currentversion == ServerVersionsEnum.S118) {
         PluginMessages.Error("1.18 not supported, update your plugin");
      }
   }

   public void a() {
      if (!STUtils.optionb("Options.HideEnableMessage")) {
         getLogger().info("======");
         getLogger().info("SuperTrailsPro " + this.getDescription().getVersion() + " loaded! (For MC " + currentversion.s + ")");
         getLogger().info(PluginMessages.getRDM());
         getLogger().info("======");
      }
   }

   public static int r(int min, int max) {
      return new Random().nextInt(max - min + 1) + min;
   }

   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
      if (!end) {
         sender.sendMessage("§cAn error occurred on plugin startup. Please check console errors");
         return false;
      }

      Player player = null;
      if (sender instanceof Player) {
         player = (Player) sender;
      }

      if (cmd.getName().equals("trails")) {
         if (player == null) {
            sender.sendMessage("§cOnly players can use this command");
            return false;
         }

         if (args.length > 0 && args[0].equalsIgnoreCase("toggle") && P.has(player, "trails.toggle")) {
            if (toggled.contains(player)) {
               toggled.remove(player);
               player.sendMessage(L.get(player, "System.ToggleOnMessage"));
            } else {
               toggled.add(player);
               player.sendMessage(L.get(player, "System.ToggleOffMessage"));
               if (Rains.getI() instanceof RainsPackets) {
                  ((RainsPackets) Rains.getI()).destroyItems(player);
               }
            }
            return false;
         }

         GuiManager.open(player, this.getConfig().getString("Options.MenuByDefault"));

      } else if (cmd.getName().equals("supertrails")) {

         if (sender instanceof Player) {
            if (args.length > 1 && args[0].equals("dev") && P.admin(sender)) {
               player.sendMessage(L.convertColors(args[1]));
               L.msg(player, "Test.Message");
            }

            if (args.length == 0) {
               CmdD.sendHome(player);
            } else if (args.length == 1) {
               if (args[0].equals("updates"))          { CmdD.sendU(player); return false; }
               if (args[0].equals("cmds"))             { CmdD.sendCmds(player); return false; }
               if (args[0].equals("usage")    && P.admin(sender)) { CmdD.sendUsage(player); return false; }
               if (args[0].equals("lang")     && P.admin(sender)) { CmdD.sendLang(player); return false; }
               if (args[0].equals("testers")  && P.admin(sender)) { CmdD.testers(player); return false; }

               if (args[0].equals("fixes") && P.admin(sender)) {
                  CmdD.showFixes(player);
               } else if (args[0].equals("blockinfo") && P.admin(sender)) {
                  String mat = ("" + NewBlock.readMaterial(player.getLocation().add(0, -1, 0).getBlock())).replaceAll("LEGACY_", "");
                  player.sendMessage("§7You're standing at : " + mat);
               } else if (args[0].equals("npclist") && P.admin(sender)) {
                  GuiManager.open(player, "NPCManager");
               } else if (args[0].equals("npcmode") && P.admin(sender)) {
                  NPCManager.toggleMode(player);
               } else if (args[0].equals("npcmodedisable") && P.admin(sender)) {
                  NPCManager.disable(player);
               } else if (args[0].equals("uploaduserdata") && P.admin(sender)) {
                  new DBUploader(player).start();
               } else if (args[0].equals("dev") && P.admin(sender)) {
                  if (!player.isOp()) return false;
                  DevTest.dev(player, "");
               } else if (args[0].equals("debug") && P.admin(sender)) {
                  if (!player.isOp()) return false;
                  if (PluginMessages.debug) {
                     PluginMessages.debug = false;
                     player.sendMessage("§eDebugging disabled");
                  } else {
                     PluginMessages.debug = true;
                     player.sendMessage("§aDebugging enabled");
                  }
               } else if (args[0].equals("report") && P.admin(sender)) {
                  this.printInfo();
               } else if (args[0].equals("upl") && P.admin(sender)) {
                  FileSender.openWings(player);
               }
            }

            if (args.length == 2 && args[0].equals("getid") && P.admin(sender)) {
               NewBlock.parse(player, args[1]);
            }

            if (args.length == 2 && args[0].equals("login") && P.admin(sender)) {
               key = args[1];
            }

            if (args.length == 2 && args[0].equals("customopen")) {
               CustomMenusLoader.open(player, args[1]);
            }

            if (args.length == 2 && args[0].equals("open")) {
               if (args[1] == null) return false;
               if (args[1].equalsIgnoreCase("rains") && !P.has(player, "trails.rains")) return false;
               GuiManager.open(player, args[1]);
            }

            if (args.length >= 1 && args[0].equals("settings")) {
               Settings.a(player, args);
            }

            if (args.length >= 1 && args[0].equals("downloads") && P.admin(sender)) {
               if (args.length == 1) CmdD.dl(player);
               if (args.length == 2 && args[1].equals("dlnew")) Dl.startDownload(player);
            }

            if (args.length == 2 && args[0].equals("setlang")) {
               int lang = Integer.parseInt(args[1]);
               DataManager.getData(player).setLang(lang);
               if (LanguageManager.isExists(lang)) {
                  player.sendMessage(L.get(player, "System.Selected"));
               }
            }
         }

         if (args.length > 0 && args[0].equals("event")) {
            CmdD.event(sender, args);
         } else if (args.length == 1 && args[0].equals("oldreload") && P.admin(sender)) {
            sender.sendMessage("§3Reloading... Please wait!");
            this.getServer().getPluginManager().disablePlugin(this);
            this.getServer().getPluginManager().enablePlugin(this);
            sender.sendMessage("§aPlugin was reloaded!");
         } else if (args.length == 1 && args[0].equals("reload") && P.admin(sender)) {
            Reload.a(sender);
         }

      } else if (cmd.getName().equalsIgnoreCase("trailsid") && P.admin(sender)) {
         try {
            if (args.length == 0) {
               sender.sendMessage("Example: /trailsid <value (Name,Id,Custom)> Player");
               return false;
            }

            if (args[0].equalsIgnoreCase("custom")) {
               if (args.length >= 3) {
                  Player target = Bukkit.getPlayer(args[2]);
                  if (target == null) {
                     sender.sendMessage("Player " + args[2] + " isn't online.");
                     return false;
                  }
                  boolean hide = args.length >= 4 && args[3].equals("true");
                  Tab.setWingsTag(args[1], target, sender, hide);
               } else {
                  sender.sendMessage("§cExample: /trailsid CUSTOM id=15;mode=0 <Player> [false/true]");
               }
               return false;
            }

            if (args.length == 1) {
               Tab.setFromCMD(args[0], player);
               return false;
            }

            if (args.length == 2) {
               Player target = Bukkit.getPlayer(args[1]);
               if (target == null) {
                  sender.sendMessage("§cThere is no player with that nickname :(");
                  return false;
               }
               Tab.setFromCMD(args[0], target, sender);
            }
         } catch (Exception e) {
            sender.sendMessage("§cError :S");
         }
      }

      return false;
   }

   public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
      if (cmd.getName().equalsIgnoreCase("supertrails") && P.admin(sender)) {
         if (args.length == 2 && args[0].equalsIgnoreCase("event")) {
            return Tab.getList(args[1], Tab.event);
         }
         if (args.length == 1) {
            return Tab.getList(args[0], Tab.st);
         }
      } else if (cmd.getName().equalsIgnoreCase("trailsid") && P.admin(sender) && args.length == 1) {
         return Tab.getList(args[0], Tab.trails);
      }
      return null;
   }
}