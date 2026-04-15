package me.saynt.supertrailspro.eventtrails;

import me.saynt.supertrailspro.trails.TrailType;
import org.bukkit.inventory.ItemStack;

public class ColoredCircle extends TrailEvent {
   ParticleColors color;

   public ColoredCircle(int var1, String var2, ItemStack var3, ParticleColors var4, Rarity var5) {
      super(var1, var2, var3, var5);
      this.color = var4;
   }

   public TrailType getType() {
      return TrailType.Event_Spin;
   }

   public ParticleColors getColor() {
      return this.color;
   }
}
