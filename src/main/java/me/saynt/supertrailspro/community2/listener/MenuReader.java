package me.saynt.supertrailspro.community2.listener;

import java.util.Iterator;
import me.saynt.stserver.packets.InventoryPacket;
import me.saynt.stserver.packets.ItemPacket;
import me.saynt.supertrailspro.Heads;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MenuReader {
   public static Inventory createMenu(InventoryPacket var0) {
      Inventory var1 = Bukkit.createInventory((InventoryHolder) null, 54, var0.name);
      for (ItemPacket var2 : var0.items) {
         var1.setItem(var2.slot, generateItemStack(var2));
      }
      return var1;
   }

   public static ItemStack generateItemStack(ItemPacket var0) {
      ItemStack var1;
      if (var0.head) {
         var1 = Heads.skull(var0.headimage);
      } else {
         Material mat = Material.valueOf(var0.ItemID);
         if (mat == null) mat = Material.STONE;
         var1 = new ItemStack(mat, var0.amount);
      }
      ItemMeta var3 = var1.getItemMeta();
      if (var3 != null) {
         var3.setDisplayName(var0.displayname);
         if (var0.lore != null) var3.setLore(var0.lore);
         var1.setItemMeta(var3);
      }
      return var1;
   }
}