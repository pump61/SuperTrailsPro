package me.saynt.supertrailspro.community2.listener;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import me.saynt.stserver.packets.FilePacket;
import me.saynt.stserver.packets.Packet;
import me.saynt.supertrailspro.P;
import me.saynt.supertrailspro.Reload;
import me.saynt.supertrailspro.STUtils;
import me.saynt.supertrailspro.SuperTrails;
import me.saynt.supertrailspro.lang.LanguageManager;
import me.saynt.supertrailspro.wings.CustomWings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class FileListener extends PacketListener {
   static FilePacket last_packet;
   static String trail;
   static String texture;

   // Substitui FilenameUtils.removeExtension
   private static String removeExtension(String filename) {
      if (filename == null) return null;
      int dot = filename.lastIndexOf('.');
      return dot > 0 ? filename.substring(0, dot) : filename;
   }

   public void read(Packet var1, Player var2) {
      try {
         FilePacket var3 = (FilePacket) var1;
         String[] var4 = var3.getData();
         String var6 = null;
         trail = null;

         for (String var7 : var4) {
            String var11 = var7.split("=")[0];
            String var12 = var7.split("=")[1];
            if (var11.equalsIgnoreCase("type")) {
               var6 = var12;
            } else if (var11.equalsIgnoreCase("trail")) {
               trail = var12;
            } else if (var11.equalsIgnoreCase("texture")) {
               texture = var12;
            }
         }

         if (last_packet != null && var2 != null) {
            var2.sendMessage("§eAnother download not finished yet");
         }

         last_packet = var3;
         if (var6 != null && var6.equalsIgnoreCase("install") && var2 != null) {
            this.install(var3, var2);
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public static void openPatternMenu(final Player var0) {
      if (!P.has(var0, "trails.community.download")) {
         var0.sendMessage("§cYou're not permitted to do that.");
         return;
      }
      Bukkit.getScheduler().runTask(SuperTrails.p, () -> {
         var0.closeInventory();
         Inventory inv = Bukkit.createInventory((InventoryHolder) null, 54, "Select slot");
         for (int i = 0; i < 54; i++) {
            inv.setItem(i, STUtils.getItem(Material.LIME_STAINED_GLASS_PANE, 0, "§aClick to add pattern to this slot"));
         }
         for (Entry<String, Integer> entry : CustomWings.patternslot.entrySet()) {
            try {
               String name = LanguageManager.getLanguageFile(0).getString("Pattern." + entry.getKey());
               inv.setItem(entry.getValue(), STUtils.getItem(Material.IRON_HORSE_ARMOR, 0, name, new ArrayList<>()));
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
         var0.openInventory(inv);
         MenuListener.invs.put(var0, inv);
      });
   }

   public static void openWingsMenu(final Player var0) {
      if (!P.has(var0, "trails.community.download")) {
         var0.sendMessage("§cYou're not permitted to do that.");
         return;
      }
      Bukkit.getScheduler().runTask(SuperTrails.p, () -> {
         var0.closeInventory();
         Inventory inv = Bukkit.createInventory((InventoryHolder) null, 54, "Select slot");
         for (int i = 0; i < 54; i++) {
            inv.setItem(i, STUtils.getItem(Material.LIME_STAINED_GLASS_PANE, 0, "§aClick to add wings to this slot"));
         }
         for (Entry<String, Integer> entry : CustomWings.slots.entrySet()) {
            try {
               String name = LanguageManager.getLanguageFile(0).getString("Wings." + entry.getKey());
               inv.setItem(entry.getValue(), STUtils.getItem(Material.IRON_HORSE_ARMOR, 0, name, new ArrayList<>()));
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
         var0.openInventory(inv);
         MenuListener.invs.put(var0, inv);
      });
   }

   public static boolean isFree(HashMap<String, Integer> var0, int var1) {
      for (Entry<String, Integer> entry : var0.entrySet()) {
         if (entry.getValue() == var1) return false;
      }
      return true;
   }

   public static void listenMenu(InventoryClickEvent var0) {
      if (!P.has((Player) var0.getWhoClicked(), "trails.community.download")) return;
      var0.setCancelled(true);
      int slot = var0.getRawSlot();
      if (slot < 54 && last_packet != null && trail != null) {
         if (trail.equalsIgnoreCase("wings")) {
            setWings(last_packet, (Player) var0.getWhoClicked(), slot);
         } else if (trail.equalsIgnoreCase("patterns") && isFree(CustomWings.patternslot, slot)) {
            setPattern(last_packet, (Player) var0.getWhoClicked(), slot);
         }
      }
   }

   public static void close() {
      last_packet = null;
      trail = null;
   }

   public static void setPattern(FilePacket var0, Player var1, int var2) {
      String name = removeExtension(var0.filename);
      if (!isFree(CustomWings.patternslot, var2)) return;
      File file = new File(SuperTrails.p.getDataFolder() + "/wings/patterns/" + var0.filename);
      var0.writeFile(file);
      SuperTrails.p.getConfig().set("WingsPatterns." + name + ".File", var0.filename);
      SuperTrails.p.getConfig().set("WingsPatterns." + name + ".Slot", var2);
      SuperTrails.p.getConfig().set("WingsPatterns." + name + ".Item", texture == null ? "160:10" : "CUSTOMHEAD=" + texture);
      SuperTrails.p.getConfig().set("WingsPatterns." + name + ".CommunityID", name);
      SuperTrails.p.saveConfig();
      LanguageManager.getLanguageFile(0).set("Pattern." + name, "§f§l" + var0.displayname);
      try { LanguageManager.getDefault().save(LanguageManager.defaultfile); } catch (Exception e) { e.printStackTrace(); }
      Reload.a();
      if (var1 != null) var1.closeInventory();
      close();
   }

   public static void setWings(FilePacket var0, Player var1, int var2) {
      String name = removeExtension(var0.filename);
      if (!isFree(CustomWings.slots, var2)) return;
      File file = new File(SuperTrails.p.getDataFolder() + "/wings/models/" + var0.filename);
      var0.writeFile(file);
      SuperTrails.p.getConfig().set("Wings." + name + ".File", var0.filename);
      SuperTrails.p.getConfig().set("Wings." + name + ".Slot", var2);
      SuperTrails.p.getConfig().set("Wings." + name + ".Item", "288:0");
      SuperTrails.p.getConfig().set("Wings." + name + ".CommunityID", name);
      SuperTrails.p.saveConfig();
      LanguageManager.getLanguageFile(0).set("Wings." + name, "§f§l" + var0.displayname);
      try { LanguageManager.getDefault().save(LanguageManager.defaultfile); } catch (Exception e) { e.printStackTrace(); }
      Reload.a();
      if (var1 != null) var1.closeInventory();
      close();
   }

   public void install(FilePacket var1, Player var2) {
      if (trail.equalsIgnoreCase("wings")) openWingsMenu(var2);
      if (trail.equalsIgnoreCase("patterns")) openPatternMenu(var2);
   }

   public void installDirrect(FilePacket var1, int var2) {
      if (trail.equalsIgnoreCase("wings")) setWings(var1, null, var2);
      if (trail.equalsIgnoreCase("patterns")) setPattern(var1, null, var2);
   }

   public void preview(FilePacket var1, Player var2) {}
}