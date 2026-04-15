package me.saynt.supertrailspro.data;

import java.io.File;

import java.util.UUID;
import me.saynt.supertrailspro.PluginMessages;
import me.saynt.supertrailspro.SuperTrails;
import me.saynt.supertrailspro.eventtrails.EventUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigStorage {
   public static File f;
   static FileConfiguration c;

   static {
      f = new File(SuperTrails.p.getDataFolder(), "players.yml");
      c = YamlConfiguration.loadConfiguration(f);
   }

   public static PlayerData loadData(UUID var0) {
      String var1 = c.getString(var0.toString());
      return var1 != null ? new PlayerData(var0, var1) : null;
   }

   public static boolean hasData(UUID var0) {
      String var1 = c.getString(var0.toString());
      return var1 != null;
   }

   public static void saveData(UUID var0, PlayerData var1) {
      PluginMessages.Debug("Data to json");
      c.set(var0.toString(), var1.toJson());
      saveConfig();
      EventUtils.saveEventDataToConfig(var1);
   }

   public static void clearData(UUID var0) {
      String var1 = c.getString(var0.toString());
      if (var1 != null) {
         c.set(var0.toString(), (Object)null);
         saveConfig();
      }
   }

   public static void saveConfig() {
      try {
         c.save(f);
      } catch (Exception var1) {
         var1.printStackTrace();
      }

   }
}
