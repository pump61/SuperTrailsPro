package me.saynt.supertrailspro.wings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.saynt.supertrailspro.particlelib.ParticleEffect;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class Cache {
   private Entity e;
   private Location center;
   private List<Location> posRed = new ArrayList();
   private List<Location> posGreen = new ArrayList();
   private List<Location> posBlue = new ArrayList();
   private boolean wing = false;

   public Cache(Entity var1, Location var2, List<Location> var3, List<Location> var4, List<Location> var5) {
      this.e = var1;
      this.center = var2;
      this.posRed = var3;
      this.posGreen = var4;
      this.posBlue = var5;
   }

   public void AddWing(List<Location> var1, List<Location> var2, List<Location> var3) {
      this.wing = true;
      this.posRed.addAll(var1);
      this.posGreen.addAll(var2);
      this.posBlue.addAll(var3);
   }

   public boolean isDone() {
      return this.wing;
   }

   public Location getLocation() {
      return this.center;
   }

   public boolean isStillThere() {
      Location var1 = this.e.getLocation();
      return var1.equals(this.center);
   }

   public void spawnParticles(ParticleEffect.OrdinaryColor var1, ParticleEffect.OrdinaryColor var2, ParticleEffect.OrdinaryColor var3) {
      Iterator var5 = this.posRed.iterator();

      Location var4;
      while(var5.hasNext()) {
         var4 = (Location)var5.next();
         ParticleEffect.REDSTONE.display(var1, var4, 30.0D);
      }

      var5 = this.posGreen.iterator();

      while(var5.hasNext()) {
         var4 = (Location)var5.next();
         ParticleEffect.REDSTONE.display(var2, var4, 30.0D);
      }

      var5 = this.posBlue.iterator();

      while(var5.hasNext()) {
         var4 = (Location)var5.next();
         ParticleEffect.REDSTONE.display(var3, var4, 30.0D);
      }

   }

   public List<Location> getRed() {
      return this.posRed;
   }

   public List<Location> getGreen() {
      return this.posGreen;
   }

   public List<Location> getBlue() {
      return this.posBlue;
   }
}
