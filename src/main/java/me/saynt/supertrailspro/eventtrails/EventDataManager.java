package me.saynt.supertrailspro.eventtrails;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import me.saynt.supertrailspro.StorageType;
import me.saynt.supertrailspro.SuperTrails;
import me.saynt.supertrailspro.data.DataManager;
import me.saynt.supertrailspro.data.mysql.MySqlManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class EventDataManager {
   public static StorageType st;
   public static HashMap<UUID, List<Integer>> event;
   public static HashMap<UUID, Integer> boxes;
   public static HashMap<UUID, Integer> temp;
   public static File f;

   static {
      st = StorageType.Config;
      event = new HashMap();
      boxes = new HashMap();
      temp = new HashMap();
      f = new File(SuperTrails.p.getDataFolder(), "event_players.yml");
   }

   public static void addNew(Player var0, int var1) {
      load(var0);
      if (!has(var0, var1)) {
         List var2 = (List)event.get(var0.getUniqueId());
         var2.add(var1);
         event.put(var0.getUniqueId(), var2);
         if (DataManager.getData(var0).isLoaded()) {
            save(var0);
         }

      }
   }

   public static void clearTrails(Player var0) {
      load(var0);
      List var1 = (List)event.get(var0.getUniqueId());
      var1.clear();
      event.put(var0.getUniqueId(), var1);
      if (DataManager.getData(var0).isLoaded()) {
         save(var0);
      }

   }

   public static List<Integer> getList(Player var0) {
      load(var0);
      return (List)event.get(var0.getUniqueId());
   }

   public static void setTempBoxes(Player var0, int var1) {
      temp.put(var0.getUniqueId(), var1);
   }

   public static boolean inTempHm(Player var0) {
      return temp.containsKey(var0.getUniqueId());
   }

   public static void clearTemp() {
      temp.clear();
   }

   public static boolean has(Player var0, int var1) {
      load(var0);
      Iterator var3 = ((List)event.get(var0.getUniqueId())).iterator();

      while(var3.hasNext()) {
         Integer var2 = (Integer)var3.next();
         if (var2.equals(var1)) {
            return true;
         }
      }

      return false;
   }

   public static String getString(UUID var0) {
      if (!event.containsKey(var0)) {
         return "null";
      } else {
         StringBuilder var1 = new StringBuilder();
         var1.append(String.valueOf(boxes.get(var0)) + "--");
         int var2 = 1;

         for(Iterator var4 = ((List)event.get(var0)).iterator(); var4.hasNext(); ++var2) {
            Integer var3 = (Integer)var4.next();
            var1.append(String.valueOf(var3) + (var2 == ((List)event.get(var0)).size() ? "" : "-"));
         }

         return var1.toString();
      }
   }

   public static void readFromString(UUID var0, String var1) {
      if (var1.equalsIgnoreCase("null")) {
         load(Bukkit.getPlayer(var0));
         boxes.put(var0, 0);
         event.put(var0, new ArrayList());
      } else {
         int var2 = Integer.parseInt(var1.split("--")[0]);
         ArrayList var3 = new ArrayList();
         if (var1.split("--").length > 1) {
            String var4 = var1.split("--")[1];
            if (var4 != null) {
               String[] var8;
               int var7 = (var8 = var4.split("-")).length;

               for(int var6 = 0; var6 < var7; ++var6) {
                  String var5 = var8[var6];
                  var3.add(Integer.parseInt(var5));
               }
            }
         }

         boxes.put(var0, var2);
         event.put(var0, var3);
      }
   }

   public static List<TrailEvent> getTrailsList(Player var0) {
      ArrayList var1 = new ArrayList();
      Iterator var3 = getList(var0).iterator();

      while(var3.hasNext()) {
         int var2 = (Integer)var3.next();
         TrailEvent var4 = (TrailEvent)EventTrails.i.get(var2);
         if (var4 != null) {
            var1.add(var4);
         }
      }

      return var1;
   }

   public static void addChest(Player var0, int var1) {
      load(var0);
      int var2 = (Integer)boxes.get(var0.getUniqueId());
      boxes.put(var0.getUniqueId(), var2 + var1);
      if (DataManager.getData(var0).isLoaded()) {
         save(var0);
      }

   }

   public static boolean payChest(Player var0) {
      load(var0);
      if (!DataManager.getData(var0).isLoaded()) {
         return false;
      } else if (temp.get(var0.getUniqueId()) != null && (Integer)temp.get(var0.getUniqueId()) != 0) {
         temp.put(var0.getUniqueId(), (Integer)temp.get(var0.getUniqueId()) - 1);
         return true;
      } else if ((Integer)boxes.get(var0.getUniqueId()) > 0) {
         boxes.put(var0.getUniqueId(), (Integer)boxes.get(var0.getUniqueId()) - 1);
         if (DataManager.getData(var0).isLoaded()) {
            save(var0);
         }

         return true;
      } else {
         return false;
      }
   }

   public static boolean canpayChest(Player var0) {
      load(var0);
      if (!DataManager.getData(var0).isLoaded()) {
         return false;
      } else if (temp.get(var0.getUniqueId()) != null && (Integer)temp.get(var0.getUniqueId()) != 0) {
         return true;
      } else {
         return (Integer)boxes.get(var0.getUniqueId()) > 0;
      }
   }

   public static int getChestAmount(Player var0) {
      load(var0);
      UUID var1 = var0.getUniqueId();
      return (Integer)boxes.get(var1) + (temp.get(var1) == null ? 0 : (Integer)temp.get(var1));
   }

   public static void save(Player var0) {
      if (event.containsKey(var0.getUniqueId())) {
         List var1 = (List)event.get(var0.getUniqueId());
         if (st == StorageType.Config) {
            YamlConfiguration var2 = YamlConfiguration.loadConfiguration(f);
            var2.set(var0.getUniqueId().toString() + ".Unlocked", var1);
            var2.set(var0.getUniqueId().toString() + ".Chests", boxes.get(var0.getUniqueId()));

            try {
               var2.save(f);
            } catch (Exception var4) {
               var4.printStackTrace();
            }
         } else if (st == StorageType.DB && DataManager.getData(var0).isLoaded()) {
            MySqlManager.savePlayer(var0.getUniqueId());
         }

      }
   }

   public static void load(Player var0) {
      UUID var1 = var0.getUniqueId();
      if (!event.containsKey(var1)) {
         if (st == StorageType.Config) {
            YamlConfiguration var2 = YamlConfiguration.loadConfiguration(f);
            List<Integer> var3 = var2.getIntegerList(var1.toString() + ".Unlocked");
            if (var3 == null) {
               var3 = new ArrayList();
            }

            Integer var4 = var2.getInt(var1.toString() + ".Chests");
            event.put(var1, var3);
            boxes.put(var1, var4);
         } else if (st == StorageType.DB) {
            event.put(var1, new ArrayList());
            boxes.put(var1, 0);
            DataManager.getData(var0);
         }

      }
   }

   public static void unload(Player var0) {
      UUID var1 = var0.getUniqueId();
      if (event.containsKey(var1)) {
         if (DataManager.getData(var0).isLoaded()) {
            save(var0);
         }

         event.remove(var1);
         boxes.remove(var1);
      }
   }

   public static void Justunload(UUID var0) {
      if (event.containsKey(var0)) {
         event.remove(var0);
         boxes.remove(var0);
      }
   }
}
