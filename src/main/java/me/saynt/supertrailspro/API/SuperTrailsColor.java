package me.saynt.supertrailspro.API;

public enum SuperTrailsColor {
   White(1),
   Black(2),
   Red(3),
   Yellow(4),
   Green(5),
   Blue(6),
   Purple(7),
   Gray(11),
   Aqua(12),
   Marmoreal(13),
   Dark_Cyan(14),
   Pink(15),
   Dark_Red(16),
   Orange(17);

   int id;

   private SuperTrailsColor(int var3) {
      this.id = var3;
   }

   protected int getColorID() {
      return this.id;
   }
}
