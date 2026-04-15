package me.saynt.supertrailspro.community2;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import me.saynt.stserver.packets.FileDescription;
import me.saynt.stserver.packets.FilePacket;
import me.saynt.supertrailspro.P;
import me.saynt.supertrailspro.STUtils;
import me.saynt.supertrailspro.SuperTrails;
import me.saynt.supertrailspro.community2.listener.MenuListener;
import me.saynt.supertrailspro.lang.LanguageManager;
import me.saynt.supertrailspro.wings.CustomWings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class CommunityUploader {
   public static void openPatternMenu(Player var0) {
      if (P.has(var0, "trails.community.upload")) {
         Inventory var1 = Bukkit.createInventory((InventoryHolder)null, 54, "Upload patterns");
         Iterator var3 = CustomWings.patternslot.entrySet().iterator();

         while(var3.hasNext()) {
            Entry var2 = (Entry)var3.next();

            try {
               String var4 = LanguageManager.getLanguageFile(0).getString("Pattern." + (String)var2.getKey());
               ArrayList var5 = new ArrayList();
               var5.add(" ");
               var5.add("§eClick to upload");
               var1.setItem((Integer)var2.getValue(), STUtils.getItem(((ItemStack)CustomWings.patternItem.get(var2.getKey())).getType(), 0, var4, (List)var5));
            } catch (Exception var6) {
               var6.printStackTrace();
            }
         }

         var0.openInventory(var1);
         MenuListener.invs.put(var0, var1);
      } else {
         var0.sendMessage("§cYou're not permitted to do that.");
      }

   }

   public static void listenPattern(InventoryClickEvent var0) {
      if (P.has((Player)var0.getWhoClicked(), "trails.community.upload")) {
         var0.setCancelled(true);
         int var1 = var0.getRawSlot();
         String var2 = getFromValue(CustomWings.patternslot, var1);
         if (var2 != null) {
            String var3 = LanguageManager.getLanguageFile(0).getString("Pattern." + var2);
            String var4 = SuperTrails.p.getConfig().getString("WingsPatterns." + var2 + ".File");
            File var5 = new File(SuperTrails.p.getDataFolder(), "wings/patterns/" + var4);
            createAndSendPacket((Player)var0.getWhoClicked(), var5, var3, FileDescription.ToServerPattern);
         }
      }
   }

   public static void openWingsMenu(Player var0) {
      if (P.has(var0, "trails.community.upload")) {
         Inventory var1 = Bukkit.createInventory((InventoryHolder)null, 54, "Upload wings");
         Iterator var3 = CustomWings.slots.entrySet().iterator();

         while(var3.hasNext()) {
            Entry var2 = (Entry)var3.next();

            try {
               String var4 = LanguageManager.getLanguageFile(0).getString("Wings." + (String)var2.getKey());
               ArrayList var5 = new ArrayList();
               var5.add(" ");
               var5.add("§eClick to upload");
               var1.setItem((Integer)var2.getValue(), STUtils.getItem(((ItemStack)CustomWings.it.get(var2.getKey())).getType(), 0, var4, (List)var5));
            } catch (Exception var6) {
               var6.printStackTrace();
            }
         }

         var0.openInventory(var1);
         MenuListener.invs.put(var0, var1);
      } else {
         var0.sendMessage("§cYou're not permitted to do that.");
      }

   }

   public static void listenWings(InventoryClickEvent var0) {
      if (P.has((Player)var0.getWhoClicked(), "trails.community.upload")) {
         var0.setCancelled(true);
         int var1 = var0.getRawSlot();
         String var2 = getFromValue(CustomWings.slots, var1);
         if (var2 != null) {
            String var3 = LanguageManager.getLanguageFile(0).getString("Wings." + var2);
            String var4 = SuperTrails.p.getConfig().getString("Wings." + var2 + ".File");
            File var5 = new File(SuperTrails.p.getDataFolder(), "wings/models/" + var4);
            createAndSendPacket((Player)var0.getWhoClicked(), var5, var3, FileDescription.ToServerWings);
         }
      }
   }

   public static String getFromValue(HashMap<String, Integer> var0, int var1) {
      Iterator var3 = var0.entrySet().iterator();

      while(var3.hasNext()) {
         Entry var2 = (Entry)var3.next();
         if ((Integer)var2.getValue() == var1) {
            return (String)var2.getKey();
         }
      }

      return null;
   }

   public static void createAndSendPacket(Player var0, File var1, String var2, FileDescription var3) {
      FilePacket var4 = new FilePacket(var1, var3, var1.getName(), var2);
      CommunityManager.sendPacket(var0, var4);
   }
}
