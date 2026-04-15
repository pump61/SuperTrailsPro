package me.saynt.supertrailspro.trails.modes;

import java.util.ArrayList;
import java.util.List;
import me.saynt.supertrailspro.data.PlayerData;
import me.saynt.supertrailspro.particlelib.ParticleEffect;
import me.saynt.supertrailspro.trails.TrailParticle;
import me.saynt.supertrailspro.wings.ColoredParticle;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class Waves extends Mode {
   int[] blocked = new int[]{24};
   public double radials = 0.10471975511965977D;
   public float radius = 1.0F;
   public int particlesHelix = 3;
   public int particlesBase = 15;
   public float length = 5.0F;
   public float grow = 0.2F;
   public float baseInterval = 10.0F;
   protected int step = 0;
   List<ParticleEffect.OrdinaryColor> colors = new ArrayList();
   int f;

   public Waves(String var1, ItemStack var2, int var3) {
      super(var1, var2, var3);
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
      this.f = this.f < 13 ? this.f + 1 : 0;
      ++this.step;
      if ((float)this.step * this.grow > this.length) {
         this.step = 0;
      }

   }

   public void spawn(PlayerData var1, TrailParticle var2) {
      Location var3 = var1.getLocation();
      var3.setPitch(-90.0F);

      for(int var4 = 0; var4 < this.particlesHelix; ++var4) {
         int var5;
         double var6;
         Vector var8;
         for(var5 = 0; var5 < 2; ++var5) {
            var6 = (double)this.step * this.radials + 3.141592653589793D * (double)var5;
            var8 = new Vector(Math.cos(var6) * (double)this.radius, (double)((float)this.step * this.grow), Math.sin(var6) * (double)this.radius);
            this.drawParticle(var3, var8, var2);
         }

         if ((float)this.step % this.baseInterval == 0.0F) {
            for(var5 = -this.particlesBase; var5 <= this.particlesBase; ++var5) {
               if (var5 != 0) {
                  var6 = (double)this.step * this.radials;
                  var8 = (new Vector(Math.cos(var6), 0.0D, Math.sin(var6))).multiply(this.radius * (float)var5 / (float)this.particlesBase).setY((float)this.step * this.grow);
                  this.drawParticle(var3, var8, var2);
               }
            }
         }
      }

   }

   protected void drawParticle(Location var1, Vector var2, TrailParticle var3) {
      VectorUtils.rotateAroundAxisX(var2, (double)((var1.getPitch() + 90.0F) * 0.017453292F));
      VectorUtils.rotateAroundAxisY(var2, (double)(-var1.getYaw() * 0.017453292F));
      var1.add(var2);
      if (var3.getEff() == ParticleEffect.REDSTONE) {
         ColoredParticle.spawn(var1, (ParticleEffect.OrdinaryColor)this.colors.get(this.f));
      } else {
         var3.getEff().display(var1, 25.0D);
      }

      var1.subtract(var2);
   }
}
