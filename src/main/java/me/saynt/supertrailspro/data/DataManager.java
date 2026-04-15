package me.saynt.supertrailspro.data;

import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;
import me.saynt.supertrailspro.PluginMessages;
import me.saynt.supertrailspro.STUtils;
import me.saynt.supertrailspro.StorageType;
import me.saynt.supertrailspro.data.mysql.MySqlManager;
import me.saynt.supertrailspro.eventtrails.EventDataManager;
import me.saynt.supertrailspro.permissionchecker.PermissionChecker;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class DataManager {
   public static StorageType st;
   public static HashMap<UUID, PlayerData> p;

   static {
      st = StorageType.Config;
      p = new HashMap();
   }

   public static PlayerData loadPlayerData(UUID var0) {
      if (p.containsKey(var0)) {
         return (PlayerData)p.get(var0);
      } else {
         if (st == StorageType.Config) {
            if (ConfigStorage.hasData(var0)) {
               p.put(var0, ConfigStorage.loadData(var0));
               return ConfigStorage.loadData(var0);
            }
         } else if (st == StorageType.DB) {
            MySqlManager.loadPlayer(var0);
            PlayerData var1 = new PlayerData(var0, false);
            p.put(var0, var1);
            return var1;
         }

         return null;
      }
   }

   public static PlayerData load(UUID var0) {
      return loadOrCreate(var0);
   }

   public static PlayerData loadOrCreate(UUID var0) {
      PlayerData var1 = loadPlayerData(var0);
      return var1 == null ? new PlayerData(var0) : var1;
   }

   public static void unload(UUID var0) {
      savePlayerData(var0);
      p.remove(var0);
   }

   public static void removePlayer(UUID var0) {
      p.remove(var0);
   }

   public static boolean savePlayerData(UUID var0) {
      PlayerData var1 = (PlayerData)p.get(var0);
      PluginMessages.Debug(st.name());
      if (st == StorageType.Config) {
         if (p == null || var1.isEmpty()) {
            PluginMessages.Debug("Fuck its empty");
            clear(var0);
            return false;
         }

         ConfigStorage.saveData(var0, var1);
      } else if (st == StorageType.DB) {
         MySqlManager.savePlayer(var0);
         PluginMessages.Debug("Saving here mysql");
      }

      return false;
   }

   public static void clear(UUID var0) {
      if (st == StorageType.Config) {
         ConfigStorage.clearData(var0);
      } else if (st == StorageType.DB) {
         MySqlManager.removePlyaer(var0);
      }

   }

   public static void loadForEveryOne() {
      Iterator var1 = Bukkit.getOnlinePlayers().iterator();

      while(var1.hasNext()) {
         Player var0 = (Player)var1.next();
         loadOrCreate(var0.getUniqueId());
      }

   }

   public static void registerPlayer(Player var0) {
      PlayerData var1 = loadOrCreate(var0.getUniqueId());
      p.put(var0.getUniqueId(), var1);
      if (STUtils.optionb("PermissionChecker.JoinChecker") && var0 != null && var0.isOnline()) {
         PermissionChecker.check(var0);
      }

   }

   public static void unregisterPlayer(Player var0) {
      if (st == StorageType.DB) {
         MySqlManager.unloadPlayer(var0.getUniqueId());
      }

      if (st == StorageType.Config) {
         p.remove(var0.getUniqueId());
         EventDataManager.unload(var0);
      }

   }

   public static PlayerData getData(Player var0) {
      return loadOrCreate(var0.getUniqueId());
   }

   public static PlayerData getData(UUID var0) {
      return loadOrCreate(var0);
   }
}
