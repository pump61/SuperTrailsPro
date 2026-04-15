package me.saynt.supertrailspro.wings;

public class WingsInfo {
   public String name;
   public boolean red = false;
   public boolean green = false;
   public boolean blue = false;

   public WingsInfo(String var1) {
      this.name = var1;
   }

   public void setRed(boolean var1) {
      this.red = var1;
   }

   public void setGreen(boolean var1) {
      this.green = var1;
   }

   public void setBlue(boolean var1) {
      this.blue = var1;
   }

   public boolean hasRed() {
      return this.red;
   }

   public boolean hasGreen() {
      return this.green;
   }

   public boolean hasBlue() {
      return this.blue;
   }

   public boolean hasAnyColors() {
      return this.red || this.green || this.blue;
   }
}
