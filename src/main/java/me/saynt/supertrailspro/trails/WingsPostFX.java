package me.saynt.supertrailspro.trails;

import me.saynt.supertrailspro.particlelib.ParticleEffect;

public abstract class WingsPostFX {
   public String name;

   public WingsPostFX(String var1) {
      this.name = var1;
   }

   public abstract void getPixel(TrailWings var1, ParticleEffect.OrdinaryColor var2, int var3, int var4);

   public abstract void getPixel(TrailWings var1, WingsPattern var2, ParticleEffect.OrdinaryColor var3, int var4, int var5);
}
