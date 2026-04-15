package me.saynt.supertrailspro.eventtrails;

import me.saynt.supertrailspro.trails.TrailType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ConfettiTrail extends TrailEvent {
   ItemStack[] drop = null;
   ConfettiColor[] clrs = null;

   public ConfettiTrail(int var1, String var2, ItemStack var3, ConfettiColor[] var4, Rarity var5) {
      super(var1, var2, var3, var5);
      this.setupItem(var4);
      this.clrs = var4;
   }

   public void setupItem(ConfettiColor[] var1) {
      ItemStack[] var2 = new ItemStack[var1.length];
      int var3 = 0;
      ConfettiColor[] var7 = var1;
      int var6 = var1.length;

      for(int var5 = 0; var5 < var6; ++var5) {
         ConfettiColor var4 = var7[var5];
         var2[var3++] = new ItemStack(Material.INK_SAC, 1, (byte)var4.getID());
      }

   }

   public ConfettiColor[] getColors() {
      return this.clrs;
   }

   public TrailType getType() {
      return TrailType.Event_Confetti;
   }
}
