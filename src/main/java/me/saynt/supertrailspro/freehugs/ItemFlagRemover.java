package me.saynt.supertrailspro.freehugs;

import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemFlagRemover {
   public static ItemStack removeFlags(ItemStack var0) {
      ItemMeta var1 = var0.getItemMeta();
      var1.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES});
      var0.setItemMeta(var1);
      return var0;
   }

   public static ItemStack removeFlagsRecord(ItemStack var0) {
      ItemMeta var1 = var0.getItemMeta();
      var1.addItemFlags(ItemFlag.values());
      var0.setItemMeta(var1);
      return var0;
   }
}
