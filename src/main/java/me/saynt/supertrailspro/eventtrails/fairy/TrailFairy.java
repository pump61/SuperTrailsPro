package me.saynt.supertrailspro.eventtrails.fairy;

import me.saynt.supertrailspro.eventtrails.ParticleColors;
import me.saynt.supertrailspro.eventtrails.Rarity;
import me.saynt.supertrailspro.eventtrails.TrailEvent;
import me.saynt.supertrailspro.trails.TrailType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class TrailFairy extends TrailEvent {
   ParticleColors color;

   public TrailFairy(int var1, String var2, ParticleColors var3, Rarity var4) {
      super(var1, var2, new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1, var3.getbyte()), var4);
      this.color = var3;
   }

   public TrailType getType() {
      return TrailType.Event_Fairy;
   }

   public ParticleColors getColor() {
      return this.color;
   }
}
