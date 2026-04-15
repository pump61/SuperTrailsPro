package me.saynt.supertrailspro.data.mysql;

import java.io.File;
import java.util.UUID;
import me.saynt.supertrailspro.PluginMessages;
import me.saynt.supertrailspro.StorageType;
import me.saynt.supertrailspro.SuperTrails;
import me.saynt.supertrailspro.data.DataManager;
import me.saynt.supertrailspro.eventtrails.EventDataManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class MySqlManager {
   static ConfigurationSection f;
   public static String customfile;

   static {
      f = SuperTrails.p.getConfig().getConfigurationSection("MYSQL");
      customfile = null;
   }

   public static void setUp() {
      if (SuperTrails.p.getConfig().getBoolean("MYSQL.Enable")) {
         ConfigurationSection var0 = SuperTrails.p.getConfig().getConfigurationSection("MYSQL");

         try {
            MySql.setUpDatabase(var0.getString("ip"), var0.getString("port"), var0.getString("database"), var0.getString("login"), var0.getString("password"));
            MySql.getConnection().createStatement();
            DataManager.st = StorageType.DB;
            EventDataManager.st = StorageType.DB;
         } catch (Exception var2) {
            PluginMessages.Error("Can't connect to database :(");
         }

      }
   }

   public static String getMYSQLValue(String var0) {
      if (customfile == null) {
         return f.getString(var0);
      } else {
         File var1 = new File(SuperTrails.p.getDataFolder(), customfile);
         YamlConfiguration var2 = YamlConfiguration.loadConfiguration(var1);
         return var2.getString(var0);
      }
   }

   public static void loadPlayer(UUID var0) {
      MySqlThreads var1 = new MySqlThreads();
      var1.uuid = var0;
      var1.command = MySqlCommand.Load;
      var1.start();
   }

   public static void savePlayer(UUID var0) {
      MySqlThreads var1 = new MySqlThreads();
      var1.uuid = var0;
      var1.command = MySqlCommand.Save;
      var1.start();
   }

   public static void unloadPlayer(UUID var0) {
      MySqlThreads var1 = new MySqlThreads();
      var1.uuid = var0;
      var1.command = MySqlCommand.Unload;
      var1.start();
   }

   public static void removePlyaer(UUID var0) {
      MySqlThreads var1 = new MySqlThreads();
      var1.uuid = var0;
      var1.command = MySqlCommand.Remove;
      var1.start();
   }
}
