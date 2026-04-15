package me.saynt.supertrailspro.eventtrails;

import me.saynt.supertrailspro.particlelib.ParticleEffect;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum ParticleColors {
   White("White", new ParticleEffect.OrdinaryColor(255, 255, 255), (byte)0),
   Red("Red", new ParticleEffect.OrdinaryColor(255, 10, 10), (byte)14),
   Yellow("Yellow", new ParticleEffect.OrdinaryColor(255, 255, 10), (byte)4),
   Green("Green", new ParticleEffect.OrdinaryColor(10, 255, 10), (byte)5),
   Blue("Blue", new ParticleEffect.OrdinaryColor(10, 10, 255), (byte)11),
   Purple("Purple", new ParticleEffect.OrdinaryColor(255, 10, 255), (byte)10),
   Black("Black", new ParticleEffect.OrdinaryColor(10, 10, 10), (byte)7);

   String name;
   ParticleEffect.OrdinaryColor color;
   byte colorId;

   private ParticleColors(String var3, ParticleEffect.OrdinaryColor var4, byte var5) {
      this.name = var3;
      this.color = var4;
      this.colorId = var5;
   }

   public String getName() {
      return this.name;
   }

   public byte getbyte() {
      return this.colorId;
   }

   public ParticleEffect.OrdinaryColor getColor() {
      return this.color;
   }

   public ItemStack getItemStack() {
      return (new ItemStack(Material.WHITE_STAINED_GLASS, 1, this.colorId)).clone();
   }
}
