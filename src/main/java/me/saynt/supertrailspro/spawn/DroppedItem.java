package me.saynt.supertrailspro.spawn;

import org.bukkit.Location;

public class DroppedItem {
   int e;
   long born;
   Location loc;

   public DroppedItem(int var1, Location var2) {
      this.e = var1;
      this.born = System.currentTimeMillis() / 50L;
      this.loc = var2;
   }

   public long getTicksLived() {
      return System.currentTimeMillis() / 50L - this.born;
   }

   public int getEntityID() {
      return this.e;
   }

   public Location getLocation() {
      return this.loc;
   }
}
