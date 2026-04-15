package me.saynt.supertrailspro.trails.modes;

import java.util.ArrayList;
import java.util.List;
import me.saynt.supertrailspro.SuperTrails;
import me.saynt.supertrailspro.data.PlayerData;
import me.saynt.supertrailspro.particlelib.ParticleEffect;
import me.saynt.supertrailspro.trails.TrailParticle;
import me.saynt.supertrailspro.wings.ColoredParticle;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class Circle extends Mode {
   public int i = 0;
   public int speed;
   public double y;
   public List<ParticleEffect.OrdinaryColor> colors = new ArrayList();
   int[] blocked = new int[0];
   int color = 0;

   public Circle(String var1, ItemStack var2, int var3) {
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
      this.color = this.color < 13 ? this.color + 1 : 0;
      this.i += this.speed;
      if (this.i >= 179) {
         this.i *= -1;
      }

   }

   public void spawn(PlayerData var1, TrailParticle var2) {
      Location var3 = var1.getLocation().clone().add(0.0D, this.y, 0.0D);
      var3.setPitch(0.0F);
      var3.setYaw((float)this.i);
      var3.add(var3.getDirection().multiply(1));
      if (var2.getEff() != ParticleEffect.REDSTONE) {
         var2.getEff().display(0.0F, 0.0F, 0.0F, 0.0F, 3, var3, 30.0D);
      } else {
         ColoredParticle.spawn(var3, (ParticleEffect.OrdinaryColor)this.colors.get(this.color));
      }

   }

   public void updateValues() {
      this.speed = SuperTrails.p.getConfig().getInt("Modes.Circle.MovementSpeed");
      this.y = SuperTrails.p.getConfig().getDouble("Modes.Circle.Y");
   }
}
