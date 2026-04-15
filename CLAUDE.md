# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

SuperTrailsPro is a Spigot/Bukkit Minecraft plugin (Java 17, Spigot API 1.20.1, targets MC 1.21) that gives players cosmetic particle trails, block trails, rain effects, and particle wings. It is a premium plugin with license-key authentication via a remote community server.

## Build

```bash
mvn package
```

The output JAR is placed in `target/`. There are no tests. Local JARs for vendor dependencies live in `libs/`.

The `pom.xml` pulls from the Spigot nexus, Citizens maven repo, and Minecraft libraries repo. Dependencies bundled into the JAR: `json-simple`, `commons-codec`, `HikariCP`. Provided (not bundled): Spigot API, Citizens API, Mojang authlib.

## Architecture

### Entry Point

`SuperTrails` (`me.kvq.supertrailspro.SuperTrails`) is the `JavaPlugin` main class. `onEnable()` runs every subsystem's initialization in sequence; that method is the best map of the startup order.

### Trail ID Ranges

Trail types are distinguished by their integer ID stored in `PlayerData`. The mapping (used everywhere via `TrailsUtil.getFromID()`):

| Range | Type |
|---|---|
| 1–99 | Particle trails (`TrailParticle`) |
| 100–199 | Block trails (`TrailBlocks`) |
| 201 | Rain trail (`TrailRain`) |
| 300–399 | Wings (`TrailWings`) |
| 400–499 | Event trails |

### Core Game Loop

`Task.run()` starts two recurring `BukkitRunnable` timers:
- **Sync timer (every 2 ticks):** spawns particle/block/rain trails for all online players, drives `Modes.tick()`, `Rains.tick()`, block spawn rotation.
- **Async timer (every tick):** renders custom wings (ID 300–399), manages rainbow/invert tick counters, processes queued GUI opens.

### Trail Hierarchy

```
Trail  (base: id, name, MC version requirement)
 ├── TrailParticle  (particle effect + item display)
 ├── TrailBlocks    (block material sets)
 ├── TrailRain      (falling item rain effect)
 ├── TrailWings     (pixel-art wings drawn with colored particles)
 └── (event trails in eventtrails package)
```

`TrailsUtil` owns the static registries (`ptr` for particles, `pbl` for blocks) and the `spawnTrail()` dispatch method.

### Player Data & Storage

`DataManager` is the central registry (`HashMap<UUID, PlayerData>`). Storage backend is determined by `StorageType` enum:
- `Config` (default) — serialized into the Bukkit config YAML via `ConfigStorage`
- `DB` — MySQL via `MySqlManager` / HikariCP connection pool

`PlayerData` holds the active trail ID, wings array, mode, language preference, and location cache.

### GUI System

`GuiManager` owns named `Gui` instances (Wings, Rains, Event, Blocks, Particles, Main, Languages, NPCManager). Opening a GUI clones the prototype and tracks the open instance per-player. Custom menus are loaded separately by `CustomMenusLoader`.

### Wings System

Wings are bitmap patterns rendered as colored particles. `WingsManager` stores wing definitions and post-FX (Rainbow, Golden, Inline variants, etc.). `CustomWingsLoader` reads user-supplied wing files; `ButterflyWings` / `CustomWings` handle rendering. Wings are animated using `Task.ttciks` (0–7 cycle).

### Community / License Server

The `community2` package manages the persistent TCP connection to the licensing server (`CommunityThread`). Authentication uses `AuthenticationPacket` with the `LicenseKey` from config. `CommunityManager` handles reconnection and keeps an alive heartbeat every 10 s. The older `community` package is legacy; `community3.WebAPI` is a REST fallback.

### Modes

`Modes` (in `trails/modes/`) are display shape overlays (Circle, Helix, Lightning, Pulse, Shield, Spiral, Waves, Slinky, Shooting) applied on top of particle trails when a player has a non-zero mode set. `Modes.tick()` drives animations; `Modes.a()` dispatches the correct mode render.

### Commands

| Command | Purpose |
|---|---|
| `/trails` | Open main GUI (or toggle visibility with `toggle`) |
| `/supertrails` | Admin/debug command (reload, event management, NPC, debug toggle, uploads) |
| `/trailsid <id> [player]` | Set a trail directly by numeric ID |

Permission prefix is `trails.*`. Admin checks use `P.admin()` / `P.has()`.

### Language / Localization

`LanguageManager` loads `.yml` language files; `L.get(player, key)` resolves the player's selected language. Language files live alongside the plugin resources.

### Packet Utilities

`PacketUtils` and `particlelib.ParticleEffect` abstract MC-version differences for spawning particles. `ServerVersionsEnum` / `ServerVersion` provide version comparisons used throughout to gate version-specific features.
