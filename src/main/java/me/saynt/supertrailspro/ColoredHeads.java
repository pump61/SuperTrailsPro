package me.saynt.supertrailspro;

import org.bukkit.inventory.ItemStack;

public enum ColoredHeads {
   WHITE(Heads.hwhite, 1, "RainAndWings.White"),
   RED(Heads.hred, 3, "RainAndWings.Red"),
   YELLOW(Heads.hyellow, 4, "RainAndWings.Yellow"),
   GREEN(Heads.hgreen, 5, "RainAndWings.Green"),
   BLUE(Heads.hblue, 6, "RainAndWings.Blue"),
   PURPLE(Heads.hpurple, 7, "RainAndWings.Purple"),
   BLACK(Heads.hblack, 2, "RainAndWings.Black"),
   GRAY(Heads.e_gray, 11, "RainAndWings.Gray"),
   AQUA(Heads.e_aqua, 12, "RainAndWings.Aqua"),
   MARMOREAL(Heads.e_marmoreal, 13, "RainAndWings.Marmoreal"),
   DARKCYAN(Heads.e_dark_cyan, 14, "RainAndWings.Darkcyan"),
   PINK(Heads.e_pink, 15, "RainAndWings.Pink"),
   DARKRED(Heads.e_dark_red, 16, "RainAndWings.Darkred"),
   ORANGE(Heads.e_orange, 17, "RainAndWings.Orange");

   ItemStack is;
   int id;
   String link;

   private ColoredHeads(ItemStack var3, int var4, String var5) {
      this.is = var3;
      this.id = var4;
      this.link = var5;
   }

   public static ColoredHeads getFromID(int var0) {
      ColoredHeads[] var4;
      int var3 = (var4 = values()).length;

      for(int var2 = 0; var2 < var3; ++var2) {
         ColoredHeads var1 = var4[var2];
         if (var1.getID() == var0) {
            return var1;
         }
      }

      return null;
   }

   public ItemStack getItem() {
      return this.is.clone();
   }

   public int getID() {
      return this.id;
   }

   public String getLink() {
      return this.link;
   }
}
