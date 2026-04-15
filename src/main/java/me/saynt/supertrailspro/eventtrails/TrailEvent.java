package me.saynt.supertrailspro.eventtrails;

import me.saynt.supertrailspro.trails.Trail;
import org.bukkit.inventory.ItemStack;

public class TrailEvent extends Trail {
   ItemStack item;
   Rarity r;

   public TrailEvent(int var1, String var2, ItemStack var3, Rarity var4) {
      super(var1, var2);
      this.item = var3;
      this.r = var4;
   }

   public ItemStack getItemStack() {
      return this.item;
   }

   public Rarity getRarity() {
      return this.r;
   }
}
