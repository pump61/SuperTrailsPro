package me.saynt.supertrailspro.API;

public class SuperTrailsWings {
   int id;
   String pattern;
   SuperTrailsColor[] colors = new SuperTrailsColor[3];

   public SuperTrailsWings(int var1, SuperTrailsColor var2, SuperTrailsColor var3, SuperTrailsColor var4) {
      this.id = var1;
      this.colors[0] = var2;
      this.colors[1] = var3;
      this.colors[2] = var4;
   }

   public SuperTrailsWings(int var1, String var2) {
      this.id = var1;
      this.pattern = var2;
   }

   protected boolean hasPattern() {
      return this.pattern != null;
   }

   protected SuperTrailsColor[] getColors() {
      return this.colors;
   }

   protected String getPattern() {
      return this.pattern;
   }

   protected int getTrailID() {
      return this.id;
   }
}
