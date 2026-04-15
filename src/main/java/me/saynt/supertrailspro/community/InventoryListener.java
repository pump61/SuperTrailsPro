package me.saynt.supertrailspro.community;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import me.saynt.supertrailspro.SuperTrails;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

public class InventoryListener implements Listener {
   public static HashMap<Player, List<String>> commands = new HashMap();

   @EventHandler
   public void onClick(InventoryClickEvent var1) {
   }

   public static void addCmds(Player var0, String var1) {
      String[] var2 = var1.split("!!!");
      String[] var3 = var2[1].split(";;;");
      String[] var7 = var3;
      int var6 = var3.length;

      for(int var5 = 0; var5 < var6; ++var5) {
         String var4 = var7[var5];
         addCommand(var0, var4);
      }

   }

   public static void openInv(final Player var0, String var1) {
      resetCommands(var0);
      final String[] var2 = var1.split("!!!");
      var0.closeInventory();
      (new BukkitRunnable() {
         public void run() {
            Inventory var1 = InventoryB.fromBase64(var2[1]);
            var0.openInventory(var1);
         }
      }).runTaskLater(SuperTrails.p, 1L);
   }

   public static void openSender(final Player var0) {
      var0.closeInventory();
      (new BukkitRunnable() {
         public void run() {
            FileSender.openWings(var0);
         }
      }).runTaskLater(SuperTrails.p, 1L);
   }

   public static void addCommand(Player var0, String var1) {
      if (!commands.containsKey(var0)) {
         ArrayList var2 = new ArrayList();
         var2.add(var1);
         commands.put(var0, var2);
      } else {
         List var3 = (List)commands.get(var0);
         var3.add(var1);
         commands.put(var0, var3);
      }

   }

   public static String getCommand(Player var0, int var1) {
      if (!commands.containsKey(var0)) {
         return null;
      } else {
         Iterator var3 = ((List)commands.get(var0)).iterator();

         while(var3.hasNext()) {
            String var2 = (String)var3.next();
            int var4 = Integer.parseInt(var2.split("=")[0]);
            String var5 = var2.split("=")[1];
            if (var4 == var1) {
               return var5;
            }
         }

         return null;
      }
   }

   public static void resetCommands(Player var0) {
      commands.remove(var0);
   }
}
