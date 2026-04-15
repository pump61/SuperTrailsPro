package me.saynt.supertrailspro.trails;

import java.util.HashMap;
import java.util.Iterator;
import javax.annotation.Nullable;
import me.saynt.supertrailspro.P;
import me.saynt.supertrailspro.STUtils;
import me.saynt.supertrailspro.SuperTrails;
import me.saynt.supertrailspro.data.DataManager;
import me.saynt.supertrailspro.data.PlayerData;
import me.saynt.supertrailspro.eventtrails.ColoredCircle;
import me.saynt.supertrailspro.eventtrails.ConfettiTrail;
import me.saynt.supertrailspro.eventtrails.EventSpawner;
import me.saynt.supertrailspro.eventtrails.EventTrails;
import me.saynt.supertrailspro.eventtrails.fairy.FairyUtils;
import me.saynt.supertrailspro.freehugs.ItemFlagRemover;
import me.saynt.supertrailspro.lang.L;
import me.saynt.supertrailspro.lang.LanguageManager;
import me.saynt.supertrailspro.particlelib.ParticleEffect;
import me.saynt.supertrailspro.spawn.BlockSpawn;
import me.saynt.supertrailspro.spawn.NewBlock;
import me.saynt.supertrailspro.trails.modes.Modes;
import me.saynt.supertrailspro.wings.WingsManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import me.saynt.supertrailspro.ServerVersion;
import me.saynt.supertrailspro.ServerVersionsEnum;

public class TrailsUtil {
   public static HashMap<Integer, TrailParticle> ptr = new HashMap<>();
   public static HashMap<Integer, TrailBlocks> pbl = new HashMap<>();
   static int freeid = 40;
   static TrailRain raintrail = new TrailRain();

   public static void presetTrails() {
      ptr.put(1,  new TrailParticle(1,  "Heart",    ParticleEffect.HEART,            new ItemStack(Material.BONE)));
      ptr.put(2,  new TrailParticle(2,  "Angry",    ParticleEffect.VILLAGER_ANGRY,   new ItemStack(Material.BLAZE_POWDER)));
      ptr.put(3,  new TrailParticle(3,  "Magic",    ParticleEffect.CRIT_MAGIC,       new ItemStack(Material.BOOK)));
      ptr.put(4,  new TrailParticle(4,  "Fun",      ParticleEffect.REDSTONE,         new ItemStack(Material.REDSTONE)));
      ptr.put(5,  new TrailParticle(5,  "Cloud",    ParticleEffect.CLOUD,            new ItemStack(Material.COBWEB)));
      ptr.put(6,  new TrailParticle(6,  "Witch",    ParticleEffect.SPELL_WITCH,      new ItemStack(Material.PLAYER_HEAD)));
      ptr.put(7,  new TrailParticle(7,  "Ender",    ParticleEffect.PORTAL,           new ItemStack(Material.ENDER_PEARL)));
      ptr.put(8,  new TrailParticle(8,  "Green",    ParticleEffect.VILLAGER_HAPPY,   new ItemStack(Material.EMERALD)));
      ptr.put(9,  new TrailParticle(9,  "Spark",    ParticleEffect.FIREWORKS_SPARK,  new ItemStack(Material.FLINT)));
      ptr.put(10, new TrailParticle(10, "Flame",    ParticleEffect.FLAME,            new ItemStack(Material.FLINT_AND_STEEL)));
      ptr.put(11, new TrailParticle(11, "White",    ParticleEffect.SPELL_INSTANT,    new ItemStack(Material.SPIDER_EYE)));

      ItemStack recordItem = new ItemStack(Material.MUSIC_DISC_STRAD);
      recordItem = ItemFlagRemover.removeFlagsRecord(recordItem);
      ptr.put(12, new TrailParticle(12, "Note",     ParticleEffect.NOTE,             recordItem));

      ptr.put(13, new TrailParticle(13, "Snow",     ParticleEffect.SNOW_SHOVEL,      new ItemStack(Material.SNOW_BLOCK)));
      ptr.put(14, new TrailParticle(14, "Water",    ParticleEffect.DRIP_WATER,       new ItemStack(Material.WATER_BUCKET)));
      ptr.put(15, new TrailParticle(15, "Lava",     ParticleEffect.DRIP_LAVA,        new ItemStack(Material.LAVA_BUCKET)));

      ItemStack swordItem = ItemFlagRemover.removeFlags(new ItemStack(Material.IRON_SWORD));
      ptr.put(16, new TrailParticle(16, "Crit",     ParticleEffect.CRIT,             swordItem));

      ptr.put(17, new TrailParticle(17, "Smoke",    ParticleEffect.SMOKE_NORMAL,     new ItemStack(Material.COAL_BLOCK)));
      ptr.put(18, new TrailParticle(18, "Spell",    ParticleEffect.SPELL_MOB_AMBIENT,new ItemStack(Material.STICK)));
      ptr.put(19, new TrailParticle(19, "Enchant",  ParticleEffect.ENCHANTMENT_TABLE,new ItemStack(Material.ENCHANTING_TABLE)));
      ptr.put(20, new TrailParticle(20, "Splash",   ParticleEffect.WATER_SPLASH,     new ItemStack(Material.WHEAT)));
      ptr.put(21, new TrailParticle(21, "Slime",    ParticleEffect.SLIME,            new ItemStack(Material.SLIME_BALL)));
      ptr.put(22, new TrailParticle(22, "Snowball", ParticleEffect.SNOWBALL,         new ItemStack(Material.SNOWBALL)));
      ptr.put(23, new TrailParticle(23, "Void",     ParticleEffect.TOWN_AURA,        new ItemStack(Material.END_STONE)));
      ptr.put(24, new TrailParticle(24, "LavaPop",  ParticleEffect.LAVA,             new ItemStack(Material.BLAZE_ROD)));

      // 1.9+
      Material dragonBreath = Material.getMaterial("DRAGON_BREATH");
      if (dragonBreath == null) dragonBreath = Material.GLASS_BOTTLE;
      Material fermentedEye = Material.getMaterial("FERMENTED_SPIDER_EYE");
      Material endRod       = Material.getMaterial("END_ROD");
      ptr.put(25, new TrailParticle(25, "Breath", ParticleEffect.DRAGON_BREATH, new ItemStack(dragonBreath)));
      if (fermentedEye != null) ptr.put(26, new TrailParticle(26, "Damage",  ParticleEffect.DAMAGE,        new ItemStack(fermentedEye)));
      if (endRod != null)       ptr.put(27, new TrailParticle(27, "EndRod",  ParticleEffect.END_ROD,       new ItemStack(endRod)));

      // 1.11+
      Material totem = Material.getMaterial("TOTEM_OF_UNDYING");
      if (totem != null) ptr.put(28, new TrailParticle(28, "Totem", ParticleEffect.Totem, new ItemStack(totem)));

      // 1.16+
      Material soulLantern  = NewBlock.getMaterial("SOUL_LANTERN");
      Material soulCampfire = NewBlock.getMaterial("SOUL_CAMPFIRE");
      if (soulLantern  != null) ptr.put(29, new TrailParticle(29, "Soul",     ParticleEffect.Soul,      new ItemStack(soulLantern)));
      if (soulCampfire != null) ptr.put(30, new TrailParticle(30, "SoulFire", ParticleEffect.Soul_Fire, new ItemStack(soulCampfire)));

      // 1.17+
      Material glowInkSac = NewBlock.getMaterial("GLOW_INK_SAC");
      Material pinkCandle = NewBlock.getMaterial("PINK_CANDLE");
      ptr.put(31, new TrailParticle(31, "GoldSparks",   ParticleEffect.GOLDEN_SPARKS, new ItemStack(Material.GOLD_INGOT)));
      if (glowInkSac != null) ptr.put(32, new TrailParticle(32, "GlowSquid",    ParticleEffect.GLOW_SQUID,    new ItemStack(glowInkSac)));
      if (pinkCandle  != null) ptr.put(33, new TrailParticle(33, "PinkConfetti", ParticleEffect.PINK,          new ItemStack(pinkCandle)));

      loadBlocks();
   }

   public static void loadCustomTrails() {
      ConfigurationSection var0 = SuperTrails.p.getConfig().getConfigurationSection("ParticleTrails");
      if (var0 == null) return;
      for (String var1 : var0.getKeys(false)) {
         if (var0.getString(var1 + ".Particle") != null) {
            Material totem = Material.getMaterial("TOTEM_OF_UNDYING");
            if (totem == null) totem = Material.STONE;
            ptr.put(freeid, new TrailParticle(freeid, var1, ParticleEffect.Totem, new ItemStack(totem)));
            freeid++;
         }
      }
   }

   public static void loadBlocks() {
      ConfigurationSection var0 = SuperTrails.p.getConfig().getConfigurationSection("BlockTrails");
      int var1 = 100;
      if (var0 == null) return;

      for (String var2 : var0.getKeys(false)) {
         try {
            ServerVersionsEnum var4 = ServerVersionsEnum.valueOf(var0.getString(var2 + ".Version"));
            if (!ServerVersion.higherThanOrEqual(var4)) continue;

            int var5 = var0.getInt(var2 + ".Type");
            String[] var6 = var0.getString(var2 + ".Set").split(",");
            Material[] var7 = new Material[var6.length / 2];
            byte[] var8 = new byte[var6.length / 2];
            ItemStack var9 = STUtils.readItemStack(var0.getString(var2 + ".Item"));
            if (var9 == null) var9 = new ItemStack(Material.GRASS_BLOCK);
            int var10 = var0.getInt(var2 + ".Slot");
            boolean var11 = false;
            int var12 = 0;

            for (String var13 : var6) {
               boolean isNumber = true;
               int numVal = 0;
               try {
                  numVal = Integer.parseInt(var13);
               } catch (Exception e) {
                  isNumber = false;
               }

               if (!var11) {
                  Material mat;
                  if (isNumber) {
                     // IDs numéricos não são mais suportados — tenta como nome
                     mat = Material.getMaterial(String.valueOf(numVal));
                  } else {
                     mat = NewBlock.getMaterial(var13.toUpperCase());
                  }
                  var7[var12] = mat;
                  var11 = true;
               } else {
                  var8[var12] = (byte) numVal;
                  var11 = false;
                  var12++;
               }
            }

            pbl.put(var1, new TrailBlocks(var1, var2, var7, var8, var5, var9, var10));
            var1++;
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
   }

   public static void SetTrail(Player var0, int var1) {
      if (var1 != 0 && ptr.containsKey(var1)) {
         PlayerData var2 = DataManager.getData(var0);
         if (Modes.isBlocked(var1, var2.getMode())) {
            var0.sendMessage(L.get(var0, "Modes.ModeNotSupported"));
            return;
         }
         TrailParticle var3 = ptr.get(var1);
         if (!STUtils.optionb("DisableSelectMessage")) {
            var0.sendMessage(L.get(var0, "System.TrailSelected").replaceAll("!name!", ChatColor.stripColor(L.readParticleTrailName(var0, var3.getName()))));
         }
      }
      updatePlayerTrail(var0, var1);
   }

   public static void SetTrail(Player var0, int var1, CommandSender var2) {
      if (var1 != 0 && ptr.containsKey(var1)) {
         PlayerData var3 = DataManager.getData(var0);
         if (Modes.isBlocked(var1, var3.getMode())) {
            var0.sendMessage(L.get(var0, "Modes.ModeNotSupported"));
            return;
         }
         TrailParticle var4 = ptr.get(var1);
         if (!STUtils.optionb("DisableSelectMessage")) {
            var2.sendMessage("§7" + var0.getName() + ":" + L.get(var0, "System.TrailSelected").replaceAll("!name!", ChatColor.stripColor(L.readParticleTrailName(var0, var4.getName()))));
            var0.sendMessage(L.get(var0, "System.TrailSelected").replaceAll("!name!", ChatColor.stripColor(L.readParticleTrailName(var0, var4.getName()))));
         }
      }
      updatePlayerTrail(var0, var1);
   }

   public static void SetMode(Player var0, int var1) {}

   public static void spawnWings(Player var0, int var1) {}

   public static void updatePlayerTrail(Player var0, int var1) {
      PlayerData var2 = DataManager.getData(var0);
      var2.setTrail(var1);
      DataManager.savePlayerData(var0.getUniqueId());
   }

   public static Trail getRTrail(Player var0) {
      if (var0 == null) return null;
      int var1 = DataManager.getData(var0).getTrail();
      if (var1 > 0 && var1 <= 99)        return ptr.get(var1);
      if (var1 >= 100 && var1 <= 199)    return pbl.get(var1);
      if (var1 == 201)                   return raintrail;
      if (var1 >= 300 && var1 <= 399)    return WingsManager.wings.get(var1);
      if (var1 > 400 && var1 < 499)      return EventTrails.i.get(var1);
      return null;
   }

   public static Trail getFromID(int var0) {
      if (var0 > 0 && var0 <= 99)        return ptr.get(var0);
      if (var0 >= 100 && var0 <= 199)    return pbl.get(var0);
      if (var0 == 201)                   return raintrail;
      if (var0 >= 300 && var0 <= 399)    return WingsManager.wings.get(var0);
      if (var0 > 400 && var0 < 499)      return EventTrails.i.get(var0);
      return null;
   }

   public static void setTrailById(int var0, CommandSender var1, @Nullable Player var2) {
      if (var2 == null) {
         if (var1 instanceof ConsoleCommandSender) {
            var1.sendMessage("§cPlayer not selected. Please use /trailsid " + var0 + " <Player>");
         }
         var2 = (Player) var1;
      } else if (!(var1 instanceof ConsoleCommandSender) && !P.has((Player) var1, "trails.admin")) {
         var1.sendMessage("§cYou don't have permission to do that");
         return;
      }

      Trail var3 = null;
      if (var0 > 0 && var0 <= 99)   var3 = ptr.get(var0);
      if (var0 >= 100 && var0 <= 199) var3 = pbl.get(var0);
      if (var0 > 400 && var0 < 499) var3 = EventTrails.i.get(var0);

      if (var3 == null) {
         var1.sendMessage("§cCan not find trail by this id!");
      } else {
         DataManager.getData(var2).setTrail(var0);
      }
   }

   public static void setlang(Player var0, int var1, boolean var2) {
      if (LanguageManager.isExists(var1)) {
         DataManager.getData(var0).setLang(var1);
         var0.sendMessage(L.get(var0, "System.Selected"));
         DataManager.getData(var0).save();
      }
   }

   public static void spawnTrail(PlayerData var0, Trail var1) {
      if (var1 == null) return;
      Player var2 = var0.getPlayer();

      if (var1.getType() == TrailType.Particle) {
         int var3 = var0.getMode();
         if (var3 != 0) {
            Modes.a(var0, var1, var3);
            return;
         }
         TrailParticle var4 = (TrailParticle) var1;
         ParticleEffect var5 = var4.pef;
         FileConfiguration var6 = SuperTrails.p.getConfig();
         int var7    = var6.getInt("ParticleTrails." + var4.getName() + ".Amount");
         float var8  = (float) var6.getDouble("ParticleTrails." + var4.getName() + ".Speed");
         float var9  = (float) var6.getDouble("ParticleTrails." + var4.getName() + ".OffsetX");
         float var10 = (float) var6.getDouble("ParticleTrails." + var4.getName() + ".OffsetY");
         float var11 = (float) var6.getDouble("ParticleTrails." + var4.getName() + ".OffsetZ");
         double var12 = var6.getDouble("ParticleTrails." + var4.getName() + ".Y");
         var5.display(var9, var10, var11, var8, var7, var0.getLocation().add(0.0D, var12, 0.0D), 30.0D);

      } else if (var1.getType() == TrailType.Block) {
         if (var2 == null) return;
         if (Task.blocktick == 5) BlockSpawn.spawn((TrailBlocks) var1, var2);

      } else if (var1.getType() == TrailType.Rain) {
         Rains.getI().spawn(var0);

      } else if (var1.getType() == TrailType.Event_Confetti) {
         if (var2 == null) return;
         EventSpawner.confetti(var2, (ConfettiTrail) var1);

      } else if (var1.getType() == TrailType.Event_Spin) {
         if (var2 == null) return;
         EventSpawner.spin(var2, (ColoredCircle) var1);

      } else if (var1.getType() == TrailType.Event_Fairy && var2 != null) {
         FairyUtils.updateFairy(var2);
      }
   }
}