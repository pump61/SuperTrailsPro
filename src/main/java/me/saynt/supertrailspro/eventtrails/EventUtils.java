package me.saynt.supertrailspro.eventtrails;

import java.io.File;

import java.util.List;
import me.saynt.supertrailspro.PluginMessages;
import me.saynt.supertrailspro.STUtils;
import me.saynt.supertrailspro.SuperTrails;
import me.saynt.supertrailspro.data.EventData;
import me.saynt.supertrailspro.data.PlayerData;
import me.saynt.supertrailspro.eventtrails.fairy.TrailFairy;
import me.saynt.supertrailspro.inventories.GuiItem;
import me.saynt.supertrailspro.lang.L;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class EventUtils {
   public static File f;
   public static FileConfiguration fc;

   static {
      f = new File(SuperTrails.p.getDataFolder(), "event_players.yml");
      fc = YamlConfiguration.loadConfiguration(f);
   }

   public static TrailEvent getRandom() {
      Rarity var0 = getRandomRarity();
      int var1 = getRandomFromArray(var0.getInts());
      TrailEvent var2 = getFromID(var1);
      return var2;
   }

   public static Rarity getRandomRarity() {
      int var0 = STUtils.r(1, 20);
      Rarity var1 = null;
      if (var0 <= 15) {
         var1 = Rarity.COMMON;
      } else if (var0 <= 19) {
         var1 = Rarity.RARE;
      } else if (var0 == 20) {
         var1 = Rarity.EPIC;
      }

      return var1;
   }

   public static int getRandomFromArray(int[] var0) {
      int var1 = STUtils.r(0, var0.length - 1);
      return var0[var1];
   }

   public static TrailEvent getFromID(int var0) {
      return (TrailEvent)EventTrails.i.get(var0);
   }

   public static void saveEventConfig() {
      try {
         fc.save(f);
      } catch (Exception var1) {
         var1.printStackTrace();
      }

   }

   public static void saveEventDataToConfig(PlayerData var0) {
      if (var0.getUUID() != null) {
         EventData var1 = var0.getEventData();
         if (var1 != null && !var1.isEmpty()) {
            fc.set(var0.getUUID().toString() + ".Data", var1.toJSON());
            saveEventConfig();
         }
      }
   }

   public static void convert(PlayerData var0) {
      List var1 = fc.getStringList(var0.getUUID() + ".Unlocked");
      int var2 = fc.getInt(var0.getUUID() + ".Chests");
      if (var1.size() > 0 || var2 > 0) {
         EventData var3 = var0.getEventData();
         var3.setUnlocked(var1);
         var3.setChests(var2);
         saveEventDataToConfig(var0);
         fc.set(var0.getUUID() + ".Unlocked", (Object)null);
         fc.set(var0.getUUID() + ".Chests", (Object)null);
         saveEventConfig();
         loadEventDataFromConfig(var0);
      }

   }

   public static GuiItem addplaceholders(TrailEvent var0, GuiItem var1, Player var2) {
      if (var0 == null) {
         return var1;
      } else if (var1 == null) {
         return null;
      } else {
         int var3 = var0.getId();
         var1.addPlaceholder("!r!", L.get(var2, "Event.Rarity." + var0.getRarity().getName()));
         if (var3 >= 401 && var3 <= 407) {
            ConfettiTrail var7 = (ConfettiTrail)var0;
            var1.addPlaceholder("!color1!", L.get(var2, "RainAndWings." + var7.getColors()[0].getName()));
            var1.addPlaceholder("!color2!", L.get(var2, "RainAndWings." + var7.getColors()[1].getName()));
            var1.addPlaceholder("!color3!", L.get(var2, "RainAndWings." + var7.getColors()[2].getName()));
         } else if (var3 >= 411 && var3 <= 417) {
            ColoredCircle var6 = (ColoredCircle)var0;
            var1.addPlaceholder("!color!", L.get(var2, "RainAndWings." + var6.getColor().getName()));
         } else if (var3 >= 421 && var3 <= 427) {
            EventColor var5 = (EventColor)var0;
            var1.addPlaceholder("!color!", L.get(var2, "RainAndWings." + var5.getColor().getName()));
         } else if (var3 >= 431 && var3 <= 437) {
            TrailFairy var4 = (TrailFairy)var0;
            var1.addPlaceholder("!color!", L.get(var2, "RainAndWings." + var4.getColor().getName()));
         }

         return var1;
      }
   }

   public static void loadEventDataFromConfig(PlayerData var0) {
      PluginMessages.Debug("OK WTF");
      if (var0.getUUID() != null) {
         PluginMessages.Debug("HERE WE GO");
         String var1 = fc.getString(var0.getUUID().toString() + ".Data");
         PluginMessages.Debug("Got STRING " + var1);
         if (var1 != null && var1.length() != 0) {
            EventData var2 = var0.getEventData();
            var2.fromJSON(var1);
            PluginMessages.Debug("JSON TRIGERRED WTF");
         } else {
            convert(var0);
         }
      }
   }
}
