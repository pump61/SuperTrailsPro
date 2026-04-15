package me.saynt.supertrailspro;

import java.util.ArrayList;
import java.util.Set;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

public class Def {
   public static void a() {
      (new BukkitRunnable() {
         public void run() {
            FileConfiguration var1 = SuperTrails.p.getConfig();
            Set var2 = var1.getKeys(true);
            if (!var2.contains("Options.PermissionChecker.TimerChecker")) {
               var1.set("Options.PermissionChecker.TimerChecker", false);
            }

            if (!var2.contains("Options.PermissionChecker.JoinChecker")) {
               var1.set("Options.PermissionChecker.JoinChecker", false);
            }

            if (!var2.contains("Options.BroadcastEventMessages")) {
               var1.set("Options.BroadcastEventMessages", true);
            }

            Def.add("Options.HideInCombatTime", 50);
            Def.add("Options.Inventory.Particles", true);
            Def.add("Options.Inventory.Blocks", true);
            Def.add("Options.Inventory.Rains", true);
            Def.add("Options.Inventory.Wings", true);
            Def.add("Options.BlockTrailsGround", "GRASS,STONE,WOOD,COAL_BLOCK,COAL_ORE,COBBLESTONE,DIAMOND_BLOCK,IRON_ORE,DIRT,EMERALD_ORE,EMERALD_BLOCK,LEAVES,LEAVES_2,WOOL,SMOOTH_BRICK,SAND,GRASS,GRAVEL,QUARTZ_BLOCK,NETHERRACK,BRICK,HAY_BLOCK,CLAY,STAINED_CLAY,GLOWSTONE,OBSIDIAN,SNOW_BLOCK,SPONGE,BEDROCK,GOLD_BLOCK");
            Def.add("Options.HideSpectatorTrails", false);
            Def.add("Options.MenuByDefault", "Main");
            Def.add("Options.Inventory.ParticlesSlot", 19);
            Def.add("Options.Inventory.BlocksSlot", 21);
            Def.add("Options.Inventory.RainsSlot", 23);
            Def.add("Options.Inventory.WingsSlot", 25);
            Def.add("Options.Inventory.RemoveItemSlot", 39);
            Def.add("Options.Inventory.LanguageItemSlot", 41);
            Def.add("Options.Inventory.BackItemSlot", 49);
            Def.add("Options.Inventory.ParticlesItem", "BLAZE_POWDER");
            Def.add("Options.Inventory.BlocksItem", "STAINED_GLASS,3");
            Def.add("Options.Inventory.RainsItem", "GHAST_TEAR");
            Def.add("Options.Inventory.WingsItem", "FEATHER");
            Def.add("Options.Inventory.ParticlesMenuSize", 54);
            Def.add("Options.Inventory.BlocksMenuSize", 54);
            Def.add("Options.Inventory.MainMenuSize", 54);
            Def.add("Options.Inventory.RemoveItem", "PAPER");
            Def.add("Options.Inventory.BackItem", "ARROW");
            Def.add("Options.NoRainItemNames", false);
            Def.add("Options.PreventGhostParticles", true);
            Def.add("Options.Inventory.ModesItem", "ANVIL");
            Def.add("Options.RainsWithPackets", true);
            Def.add("Options.ModeChanger", true);
            Def.add("Options.ForceDefaultMode", false);
            Def.add("Options.OnlyPermittedPlayersCanSeeTrails", false);
            Def.add("Options.DisableSelectMessage", false);
            Def.add("Options.ForceHighParticleSpawnRate", false);
            Def.add("Options.HideNoPermissionTrails", false);
            Def.add("Modes.Circle.MovementSpeed", 30);
            Def.add("Modes.Circle.Y", 2.2D);
            Def.add("Modes.Magician.Y", 0.5D);
            Def.add("Modes.Shooter.LiveTime", 25);
            Def.add("Modes.Shooter.Speed", 0.4D);
            Def.add("Modes.Helix.Radius", 4);
            Def.add("Modes.Helix.ParticlesAmount", 40);
            Def.add("Modes.Dna.Disabled", true);
            Def.add("Modes.Spiral.Disabled", true);
            Def.add("Modes.Spiral.VerticalSpeed", 0.1D);
            Def.add("Modes.Spiral.MovementSpeed", 30);
            Def.add("Modes.Spiral.Y", 0);
            Def.add("Modes.Spiral.Rotate90", true);
            Def.add("Modes.Slinky.MaxFall", 5);
            Def.add("Options.Inventory.LanguagesItemType", "SLIME_BALL");
            Def.add("Options.Inventory.PaneLines.Particles", true);
            Def.add("Options.Inventory.PaneLines.Blocks", true);
            Def.add("Options.Inventory.PaneLines.Rains", true);
            Def.add("Options.Inventory.PaneLines.Event", true);
            Def.add("Options.ResetButton.Particles", false);
            Def.add("Options.ResetButton.Blocks", false);
            Def.add("Options.RainItemTTL", 4);
            ArrayList var3 = new ArrayList();
            var3.add("AIR;ENCHANTMENT_TABLE;ENDER_CHEST;WOOD_BUTTON;APPLE;DIAMOND;COAL;IRON_INGOT;AIR");
            var3.add("AIR;GOLD_INGOT;STRING;FEATHER;WHEAT;BREAD;PORK;GOLDEN_APPLE;AIR");
            var3.add("AIR;REDSTONE;PAPER;EGG;CAKE;COOKIE;MELON;EYE_OF_ENDER;AIR");
            var3.add("AIR;GHAST_TEAR;BOW;GOLD_NUGGET;EMERALD;NETHER_STAR;ANVIL;GOLDEN_APPLE:1;AIR");
            var3.add("AIR;WOOL:1;WOOL:2;WOOL:3;WOOL:4;WOOL:5;WOOL:6;WOOL:7;AIR");
            Def.add("Options.RainItems", var3);
            SuperTrails.p.saveConfig();
         }
      }).runTaskLater(SuperTrails.p, 2L);
   }

   public static void add(String var0, Object var1) {
      FileConfiguration var2 = SuperTrails.p.getConfig();
      Set var3 = var2.getKeys(true);
      if (!var3.contains(var0)) {
         var2.set(var0, var1);
      }

   }
}
