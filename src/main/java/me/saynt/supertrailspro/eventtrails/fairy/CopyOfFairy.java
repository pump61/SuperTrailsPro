package me.saynt.supertrailspro.eventtrails.fairy;

import me.saynt.supertrailspro.STUtils;
import me.saynt.supertrailspro.eventtrails.ParticleColors;
import me.saynt.supertrailspro.wings.ColoredParticle;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class CopyOfFairy {
   private Player p;
   private Location current;
   private ParticleColors color;
   double x = (double)STUtils.r(-10, 10) * 0.1D;
   double z = (double)STUtils.r(-10, 10) * 0.1D;

   public CopyOfFairy(Player var1, ParticleColors var2) {
      this.p = var1;
      this.color = var2;
      this.current = var1.getLocation().clone().add((double)STUtils.r(-10, 10) * 0.1D, 2.5D, (double)STUtils.r(-10, 10) * 0.1D);
   }

   public void lifetick() {
      if (this.p != null) {
         if (this.current.distance(this.p.getLocation()) > 40.0D) {
            this.current = this.p.getLocation().clone().add((double)STUtils.r(-10, 10) * 0.1D, 2.5D, (double)STUtils.r(-10, 10) * 0.1D);
         }

         if (this.current.distance(this.p.getLocation()) > 4.0D) {
            if (this.x == -50.0D && this.z == -50.0D) {
               this.resetTo();
            }

            float[] var1 = FairyUtils.lookAt(this.current, this.p.getLocation().clone().add(this.x, 2.2D, this.z));
            this.current.setPitch(var1[1]);
            this.current.setYaw(var1[0]);
         } else {
            this.x = -50.0D;
            this.z = -50.0D;
         }

         this.current.add(this.current.getDirection().multiply(0.3D));
         this.spawn();
      }
   }

   private void resetTo() {
      this.x = (double)STUtils.r(-20, 20) * 0.1D;
      this.z = (double)STUtils.r(-20, 20) * 0.1D;
   }

   private void spawn() {
      ColoredParticle.spawn(this.current, this.color.getColor());
      ColoredParticle.spawn(this.current, this.color.getColor());
      ColoredParticle.spawn(this.current, this.color.getColor());
   }
}
