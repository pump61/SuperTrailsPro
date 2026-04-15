package me.saynt.supertrailspro.trails;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import me.saynt.supertrailspro.data.DataManager;
import me.saynt.supertrailspro.data.PlayerData;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Tab {
   public static List<String> st = new ArrayList();
   public static List<String> event = new ArrayList();
   public static List<String> trailsid = new ArrayList();
   public static List<String> trails = new ArrayList();
   public static List<Integer> ids = new ArrayList();

   public static void prepare() {
      createNames();
      st.add("event");
      st.add("login");
      event.add("giveall");
      event.add("clear");
      event.add("give");
      event.add("addchests");
      event.add("timed");
   }

   public static List<String> getList(@Nullable String var0, List<String> var1) {
      ArrayList var2;
      if (var0 != null && !var0.equalsIgnoreCase("")) {
         var2 = new ArrayList();
         var0 = var0.toLowerCase();
         Iterator var4 = var1.iterator();

         String var3;
         while(var4.hasNext()) {
            var3 = (String)var4.next();
            if (var3.toLowerCase().startsWith(var0)) {
               var2.add(var3);
            }
         }

         if (var2.size() == 0) {
            var4 = var1.iterator();

            while(var4.hasNext()) {
               var3 = (String)var4.next();
               if (var3.toLowerCase().contains(var0)) {
                  var2.add(var3);
               }
            }
         }

         return var2;
      } else {
         var2 = new ArrayList(var1);
         Collections.sort(var2);
         return var2;
      }
   }

   public static void createNames() {
      trails.add("NONE");
      ids.add(0);
      trails.add("REMOVE");
      ids.add(0);
      Iterator var1 = TrailsUtil.ptr.entrySet().iterator();

      Entry var0;
      while(var1.hasNext()) {
         var0 = (Entry)var1.next();
         ids.add((Integer)var0.getKey());
         trails.add(((TrailParticle)var0.getValue()).getName().toUpperCase());
      }

      var1 = TrailsUtil.pbl.entrySet().iterator();

      while(var1.hasNext()) {
         var0 = (Entry)var1.next();
         ids.add((Integer)var0.getKey());
         trails.add("BLOCKS_" + ((TrailBlocks)var0.getValue()).getName().toUpperCase());
      }

   }

   public static void setFromCMD(String var0, Player var1, CommandSender var2) {
      try {
         int var3 = Integer.parseInt(var0);
         if (!ids.contains(var3)) {
            var2.sendMessage("§cTrail by that ID not exists!");
         }

         TrailsUtil.SetTrail(var1, var3, var2);
      } catch (Exception var5) {
         int var4 = trails.indexOf(var0.toUpperCase());
         if (var4 == -1) {
            var2.sendMessage("§cTrail by that name not exists!");
            return;
         }

         TrailsUtil.SetTrail(var1, (Integer)ids.get(var4), var2);
      }

   }

   public static void setFromCMD(String var0, Player var1) {
      try {
         int var2 = Integer.parseInt(var0);
         if (!ids.contains(var2)) {
            var1.sendMessage("§cTrail by that ID not exists!");
         }

         TrailsUtil.SetTrail(var1, var2);
      } catch (Exception var4) {
         int var3 = trails.indexOf(var0.toUpperCase());
         if (var3 == -1) {
            var1.sendMessage("§cTrail by that name not exists!");
            return;
         }

         TrailsUtil.SetTrail(var1, (Integer)ids.get(var3));
      }

   }

   public static void setWingsTag(String var0, Player var1, CommandSender var2, boolean var3) {
      String var4 = "";
      String[] var8;
      int var7 = (var8 = var0.split(";")).length;

      for(int var6 = 0; var6 < var7; ++var6) {
         String var5 = var8[var6];

         try {
            PlayerData var9 = DataManager.getData(var1);
            String var10 = var5.split("=")[0];
            String var11 = var5.split("=")[1];
            if (var10.equalsIgnoreCase("id")) {
               var9.setTrail(Integer.parseInt(var11));
            } else if (var10.equalsIgnoreCase("mode")) {
               var9.setMode(Integer.parseInt(var11));
            } else if (var10.equalsIgnoreCase("pattern")) {
               var9.setPattern(var11);
            } else if (var10.equalsIgnoreCase("w1")) {
               var9.setWingsColor(0, Integer.parseInt(var11));
            } else if (var10.equalsIgnoreCase("w2")) {
               var9.setWingsColor(1, Integer.parseInt(var11));
            } else if (var10.equalsIgnoreCase("w3")) {
               var9.setWingsColor(2, Integer.parseInt(var11));
            } else if (var10.equalsIgnoreCase("lang")) {
               var9.setLang(Integer.parseInt(var11));
            } else {
               if (!var10.equalsIgnoreCase("rain")) {
                  var4 = var4 + "§e" + var5 + "(NoTagFound)§7;";
                  continue;
               }

               var9.setRainItemStack(new ItemStack(Material.getMaterial(var11)));
            }

            var4 = var4 + "§a" + var5 + "§7;";
            var9.save();
         } catch (Exception var12) {
            var4 = var4 + "§c" + var5 + "(Error)§7;";
         }
      }

      if (!var3) {
         var2.sendMessage(var4);
      }

   }
}
