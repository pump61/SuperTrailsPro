package me.saynt.supertrailspro.eventtrails;

import me.saynt.supertrailspro.Heads;
import org.bukkit.inventory.ItemStack;

public enum HeadColor {
   Orange(Heads.e_orange, "Orange"),
   Dark_Cyan(Heads.e_dark_cyan, "Darkcyan"),
   Marmoreal(Heads.e_marmoreal, "Marmoreal"),
   Aqua(Heads.e_aqua, "Aqua"),
   Dark_Red(Heads.e_dark_red, "Darkred"),
   Gray(Heads.e_gray, "Gray"),
   Pink(Heads.e_pink, "Pink");

   ItemStack i;
   String name;

   private HeadColor(ItemStack var3, String var4) {
      this.i = var3;
      this.name = var4;
   }

   public ItemStack getItemStack() {
      return this.i.clone();
   }

   public String getName() {
      return this.name;
   }
}
