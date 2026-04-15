package me.saynt.supertrailspro.trails;

public class Wings extends Trail {
   public Wings(int var1, String var2) {
      super(var1, var2);
   }

   public String getPermission() {
      return "trails.wings." + this.name;
   }
}
