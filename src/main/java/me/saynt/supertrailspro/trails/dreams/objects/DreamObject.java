package me.saynt.supertrailspro.trails.dreams.objects;

import me.saynt.supertrailspro.trails.dreams.DreamTrail;
import org.bukkit.Location;

public class DreamObject {
   private Location loc;
   private DreamTrail trail;
   private Object[] packets;
   private Object[] destroyPackets;

   public DreamObject(Location var1, DreamTrail var2) {
      this.loc = var1;
      this.trail = var2;
      this.create();
   }

   public Location getLocation() {
      return this.loc;
   }

   public DreamTrail getTrail() {
      return this.trail;
   }

   public Object[] getPackets() {
      return this.packets;
   }

   public Object[] getDestroyPackets() {
      return this.destroyPackets;
   }

   public void move(Location var1) {
   }

   private void create() {
   }
}
