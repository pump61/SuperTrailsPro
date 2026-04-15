package me.saynt.supertrailspro.trails.modes;

import java.util.ArrayList;
import java.util.List;
import me.saynt.supertrailspro.SuperTrails;
import me.saynt.supertrailspro.data.PlayerData;
import me.saynt.supertrailspro.inventory.RainInventory;
import me.saynt.supertrailspro.particlelib.ParticleEffect;
import me.saynt.supertrailspro.trails.TrailParticle;
import me.saynt.supertrailspro.wings.ColoredParticle;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class Helix extends Mode {
   public int strands = 14;
   public int particles = 40;
   public float radius = 4.0F;
   public float curve = 10.0F;
   public double rotation = 0.7853981633974483D;
   protected float sideRatio = 0.0F;
   int[] blocked = new int[]{24, 33};
   int tickg = 1;
   public List<ParticleEffect.OrdinaryColor> colors = new ArrayList();

   public Helix(String var1, ItemStack var2, int var3) {
      super(var1, var2, var3);
      this.updateValues();
      this.colors.add(new ParticleEffect.OrdinaryColor(255, 10, 10));
      this.colors.add(new ParticleEffect.OrdinaryColor(255, 128, 10));
      this.colors.add(new ParticleEffect.OrdinaryColor(255, 255, 10));
      this.colors.add(new ParticleEffect.OrdinaryColor(130, 230, 100));
      this.colors.add(new ParticleEffect.OrdinaryColor(128, 255, 10));
      this.colors.add(new ParticleEffect.OrdinaryColor(10, 255, 10));
      this.colors.add(new ParticleEffect.OrdinaryColor(10, 255, 128));
      this.colors.add(new ParticleEffect.OrdinaryColor(10, 255, 255));
      this.colors.add(new ParticleEffect.OrdinaryColor(10, 128, 255));
      this.colors.add(new ParticleEffect.OrdinaryColor(10, 10, 255));
      this.colors.add(new ParticleEffect.OrdinaryColor(128, 10, 255));
      this.colors.add(new ParticleEffect.OrdinaryColor(255, 10, 255));
      this.colors.add(new ParticleEffect.OrdinaryColor(255, 100, 180));
      this.colors.add(new ParticleEffect.OrdinaryColor(255, 10, 128));
   }

   public void tick() {
      this.tickg = this.tickg > this.particles ? 1 : this.tickg + 1;
   }

   public void spawn(PlayerData var1, TrailParticle var2) {
      Location var3 = var1.getLocation().add(0.0D, 0.2D, 0.0D);

      for(int var4 = 1; var4 <= this.strands; ++var4) {
         for(int var5 = 1; var5 <= this.particles; ++var5) {
            float var6 = (float)var5 / (float)this.particles;
            double var7 = (double)(this.curve * var6 * 2.0F) * 3.141592653589793D / (double)this.strands + 6.283185307179586D * (double)var4 / (double)this.strands + this.rotation;
            double var9 = Math.cos(var7) * (double)var6 * (double)this.radius;
            double var11 = Math.sin(var7) * (double)var6 * (double)this.radius;
            var3.add(var9, 0.0D, var11);
            if (var2.getEff() == ParticleEffect.REDSTONE) {
               if (var5 == this.tickg) {
                  ColoredParticle.spawn(var3, (ParticleEffect.OrdinaryColor)this.colors.get(var4 - 1));
               }
            } else if (var5 == this.tickg) {
               var2.getEff().display(var3, 25.0D);
            }

            var3.subtract(var9, 0.0D, var11);
         }
      }

   }

   public void updateValues() {
      this.radius = (float)SuperTrails.p.getConfig().getInt("Modes.Helix.Radius");
      this.particles = SuperTrails.p.getConfig().getInt("Modes.Helix.ParticlesAmount");
   }

   public boolean isBlocked(int var1) {
      return RainInventory.contains(this.blocked, var1);
   }
}
