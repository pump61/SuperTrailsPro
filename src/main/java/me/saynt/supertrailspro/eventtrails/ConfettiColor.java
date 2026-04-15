package me.saynt.supertrailspro.eventtrails;

import me.saynt.supertrailspro.particlelib.PlayParticle;

public enum ConfettiColor {
   White(15, "White", "BONE_MEAL", PlayParticle.getNMSItems().get(0)),
   Red(1, "Red", "ROSE_RED", PlayParticle.getNMSItems().get(1)),
   Yellow(11, "Yellow", "DANDELION_YELLOW", PlayParticle.getNMSItems().get(2)),
   Green(10, "Green", "CACTUS_GREEN", PlayParticle.getNMSItems().get(3)),
   Blue(4, "Blue", "LAPIS_LAZULI", PlayParticle.getNMSItems().get(4)),
   Purple(5, "Purple", "PURPLE_DYE", PlayParticle.getNMSItems().get(5)),
   Black(0, "Black", "INK_SAC", PlayParticle.getNMSItems().get(6));

   int colorId;
   String colorName;
   String itemname;
   Object nms;

   private ConfettiColor(int var3, String var4, String var5, Object var6) {
      this.colorId = var3;
      this.colorName = var4;
      this.itemname = var5;
      this.nms = var6;
   }

   public int getID() {
      return this.colorId;
   }

   public String getItemName() {
      return this.itemname;
   }

   public Object getNMS() {
      return this.nms;
   }

   public String getName() {
      return this.colorName;
   }
}
