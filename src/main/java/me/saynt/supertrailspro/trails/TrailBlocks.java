package me.saynt.supertrailspro.trails;

import me.saynt.supertrailspro.ServerVersionsEnum;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class TrailBlocks extends Trail {
   private Material[] m;
   private byte[] b;
   private int type = 0;
   public int tick = 0;
   private ItemStack is;
   private int slot;

   public TrailBlocks(int var1, String var2, Material[] var3, byte[] var4, int var5, ItemStack var6, int var7, ServerVersionsEnum var8) {
      super(var1, var2, var8);
      this.m = var3;
      this.b = var4;
      this.type = var5;
      this.is = var6;
      this.slot = var7 + 1;
   }

   public TrailBlocks(int var1, String var2, Material[] var3, byte[] var4, int var5, ItemStack var6, int var7) {
      super(var1, var2);
      this.m = var3;
      this.b = var4;
      this.type = var5;
      this.is = var6;
      this.slot = var7 + 1;
   }

   public Material[] getMaterials() {
      return this.m;
   }

   public byte[] getBytes() {
      return this.b;
   }

   public int getMode() {
      return this.type;
   }

   public TrailType getType() {
      return TrailType.Block;
   }

   public int getBlocksType() {
      return this.type;
   }

   public String getPermission() {
      return "trails.block." + this.name;
   }

   public ItemStack getItem() {
      return this.is;
   }

   public int getSlot() {
      return this.slot;
   }

   public void nextTick() {
      ++this.tick;
      if (this.m.length <= this.tick) {
         this.tick = 0;
      }

   }
}
