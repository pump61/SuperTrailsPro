package me.saynt.supertrailspro.wings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import me.saynt.supertrailspro.PluginMessages;
import me.saynt.supertrailspro.particlelib.ParticleEffect;
import me.saynt.supertrailspro.trails.Task;
import me.saynt.supertrailspro.trails.WingOffset;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class ButterflyWings {
   public static HashMap<Entity, Cache> c = new HashMap();
   public static WingOffset wi = null;

   public static void a(Location var0, ParticleEffect.OrdinaryColor var1, ParticleEffect.OrdinaryColor var2, ParticleEffect.OrdinaryColor var3) {
      if (Task.lastMS > 200L) {
         PluginMessages.Error("Server overloaded!");
      } else {
         Location var4 = var0.clone();
         var4.setPitch(0.0F);
         var4.add(0.0D, 0.0D, 0.0D);
         var4.add(var4.getDirection().multiply(-0.2D));
         var4.setYaw(var4.getYaw() + 75.0F);
         Display(var4.clone(), var1, var2, var3);
         var4 = var0.clone();
         var4.setPitch(0.0F);
         var4.add(0.0D, 0.0D, 0.0D);
         var4.add(var4.getDirection().multiply(-0.2D));
         var4.setYaw(var4.getYaw() - 75.0F);
         Display(var4.clone(), var1, var2, var3);
      }
   }

   public static void setWing() {
      WingOffset var0 = new WingOffset();
      var0.add(1, 6, 0);
      var0.add(1, 7, 0);
      var0.add(2, 5, 0);
      var0.add(2, 6, 0);
      var0.add(2, 7, 0);
      var0.add(2, 8, 0);
      var0.add(3, 4, 0);
      var0.add(3, 5, 0);
      var0.add(3, 6, 0);
      var0.add(3, 7, 0);
      var0.add(3, 8, 0);
      var0.add(4, 3, 0);
      var0.add(4, 4, 0);
      var0.add(4, 5, 0);
      var0.add(4, 6, 1);
      var0.add(4, 7, 1);
      var0.add(4, 8, 1);
      var0.add(5, 2, 0);
      var0.add(5, 3, 0);
      var0.add(5, 4, 0);
      var0.add(5, 5, 1);
      var0.add(5, 6, 1);
      var0.add(5, 7, 1);
      var0.add(6, 2, 0);
      var0.add(6, 3, 0);
      var0.add(6, 4, 1);
      var0.add(6, 5, 1);
      var0.add(6, 6, 1);
      var0.add(6, 7, 1);
      var0.add(7, 2, 0);
      var0.add(7, 3, 0);
      var0.add(7, 4, 1);
      var0.add(7, 5, 1);
      var0.add(7, 6, 1);
      var0.add(7, 7, 1);
      var0.add(7, 8, 1);
      var0.add(8, 2, 0);
      var0.add(8, 3, 1);
      var0.add(8, 4, 1);
      var0.add(8, 5, 1);
      var0.add(8, 6, 1);
      var0.add(8, 7, 2);
      var0.add(8, 8, 2);
      var0.add(9, 2, 1);
      var0.add(9, 3, 1);
      var0.add(9, 4, 1);
      var0.add(9, 5, 1);
      var0.add(9, 6, 2);
      var0.add(9, 7, 2);
      var0.add(9, 8, 2);
      var0.add(9, 9, 2);
      var0.add(10, 3, 1);
      var0.add(10, 4, 1);
      var0.add(10, 5, 2);
      var0.add(10, 6, 2);
      var0.add(10, 7, 2);
      var0.add(10, 8, 2);
      var0.add(10, 9, 2);
      var0.add(10, 10, 2);
      var0.add(11, 5, 2);
      var0.add(11, 6, 2);
      var0.add(11, 7, 2);
      var0.add(11, 8, 2);
      var0.add(11, 9, 2);
      var0.add(11, 10, 2);
      var0.add(11, 11, 2);
      var0.add(12, 7, 2);
      var0.add(12, 8, 2);
      var0.add(12, 9, 2);
      var0.add(12, 10, 2);
      var0.add(12, 11, 2);
      wi = var0;
   }

   public static Cache BetterMath(Entity var0, Location var1, int var2) {
      long var3 = System.currentTimeMillis();
      if (var2 == 0) {
         var1.setYaw(var1.getYaw() + 75.0F);
      }

      if (var2 == 1) {
         var1.setYaw(var1.getYaw() - 75.0F);
      }

      var1.setPitch(0.0F);
      Location var5 = var1.clone();
      WingOffset var6 = wi;
      Cache var7 = (Cache)c.get(var0);
      if (var7 != null && var7.isDone() && var7.isStillThere()) {
         var7.spawnParticles(getColor(0), getColor(1), getColor(2));
      } else {
         ArrayList var8 = new ArrayList();
         ArrayList var9 = new ArrayList();
         ArrayList var10 = new ArrayList();

         for(int var11 = 1; var11 <= 11; ++var11) {
            var5 = var1.clone();
            var5.add(var5.getDirection().multiply(-0.2D * (double)var11));
            Iterator var13 = var6.getForX(var11).iterator();

            while(var13.hasNext()) {
               int var12 = (Integer)var13.next();
               int var14 = var6.getColorFor(var11, var12);
               if (var14 == 0) {
                  var8.add(var5.clone().add(0.0D, 0.2D * (double)var12, 0.0D));
               }

               if (var14 == 1) {
                  var9.add(var5.clone().add(0.0D, 0.2D * (double)var12, 0.0D));
               }

               if (var14 == 2) {
                  var10.add(var5.clone().add(0.0D, 0.2D * (double)var12, 0.0D));
               }
            }
         }

         if (var2 == 0) {
            c.put(var0, new Cache(var0, var0.getLocation().clone(), var8, var9, var10));
         }

         if (var2 == 1 && var7 != null) {
            var7.AddWing(var8, var9, var10);
         }

         long var15 = System.currentTimeMillis();
         String var16 = "left";
         if (var2 == 1) {
            var16 = "right";
         }

         Bukkit.broadcastMessage(var15 - var3 + "ms take to spawn " + var16 + " wing");
      }

      return null;
   }

   public static ParticleEffect.OrdinaryColor getColor(int var0) {
      if (var0 == 0) {
         return new ParticleEffect.OrdinaryColor(255, 10, 10);
      } else if (var0 == 1) {
         return new ParticleEffect.OrdinaryColor(10, 255, 10);
      } else {
         return var0 == 2 ? new ParticleEffect.OrdinaryColor(10, 10, 255) : new ParticleEffect.OrdinaryColor(255, 10, 10);
      }
   }

   public static Cache Math(Player var0, Location var1, int var2) {
      if (var2 == 0) {
         var1.setYaw(var1.getYaw() + 75.0F);
      }

      if (var2 == 1) {
         var1.setYaw(var1.getYaw() - 75.0F);
      }

      Location var3 = var1.clone();
      ArrayList var4 = new ArrayList();
      ArrayList var5 = new ArrayList();
      ArrayList var6 = new ArrayList();
      var4.add(var3.clone().add(var3.getDirection().multiply(-1.2000000000000002D)));
      var4.add(var3.clone().add(var3.getDirection().multiply(-1.4000000000000001D)));
      var1.add(0.0D, 0.2D, 0.0D);
      var3 = var1.clone();
      var4.add(var3.clone().add(var3.getDirection().multiply(-1.0D)));
      Location var7 = var3.clone().add(var3.getDirection().multiply(-1.2000000000000002D));
      var4.add(var7);
      var4.add(var3.clone().add(var3.getDirection().multiply(-1.4000000000000001D)));
      var4.add(var3.clone().add(var3.getDirection().multiply(-1.6D)));
      var1.add(0.0D, 0.2D, 0.0D);
      var3 = var1.clone();
      var4.add(var3.clone().add(var3.getDirection().multiply(-0.8D)));
      var4.add(var3.clone().add(var3.getDirection().multiply(-1.0D)));
      var4.add(var3.clone().add(var3.getDirection().multiply(-1.2000000000000002D)));
      var4.add(var3.clone().add(var3.getDirection().multiply(-1.4000000000000001D)));
      var4.add(var3.clone().add(var3.getDirection().multiply(-1.6D)));
      var1.add(0.0D, 0.2D, 0.0D);
      var3 = var1.clone();
      var4.add(var3.clone().add(var3.getDirection().multiply(-0.6000000000000001D)));
      var4.add(var3.clone().add(var3.getDirection().multiply(-0.8D)));
      var4.add(var3.clone().add(var3.getDirection().multiply(-1.0D)));
      var5.add(var3.clone().add(var3.getDirection().multiply(-1.2000000000000002D)));
      var5.add(var3.clone().add(var3.getDirection().multiply(-1.4000000000000001D)));
      var5.add(var3.clone().add(var3.getDirection().multiply(-1.6D)));
      var1.add(0.0D, 0.2D, 0.0D);
      var3 = var1.clone();
      var4.add(var3.clone().add(var3.getDirection().multiply(-0.4D)));
      var4.add(var3.clone().add(var3.getDirection().multiply(-0.6000000000000001D)));
      var4.add(var3.clone().add(var3.getDirection().multiply(-0.8D)));
      var5.add(var3.clone().add(var3.getDirection().multiply(-1.0D)));
      var5.add(var3.clone().add(var3.getDirection().multiply(-1.2000000000000002D)));
      var5.add(var3.clone().add(var3.getDirection().multiply(-1.4000000000000001D)));
      var1.add(0.0D, 0.2D, 0.0D);
      var3 = var1.clone();
      var4.add(var3.clone().add(var3.getDirection().multiply(-0.4D)));
      var4.add(var3.clone().add(var3.getDirection().multiply(-0.6000000000000001D)));
      var5.add(var3.clone().add(var3.getDirection().multiply(-0.8D)));
      var5.add(var3.clone().add(var3.getDirection().multiply(-1.0D)));
      var5.add(var3.clone().add(var3.getDirection().multiply(-1.2000000000000002D)));
      var5.add(var3.clone().add(var3.getDirection().multiply(-1.4000000000000001D)));
      var1.add(0.0D, 0.2D, 0.0D);
      var3 = var1.clone();
      var4.add(var3.clone().add(var3.getDirection().multiply(-0.4D)));
      var4.add(var3.clone().add(var3.getDirection().multiply(-0.6000000000000001D)));
      var5.add(var3.clone().add(var3.getDirection().multiply(-0.8D)));
      var5.add(var3.clone().add(var3.getDirection().multiply(-1.0D)));
      var5.add(var3.clone().add(var3.getDirection().multiply(-1.2000000000000002D)));
      var5.add(var3.clone().add(var3.getDirection().multiply(-1.4000000000000001D)));
      var5.add(var3.clone().add(var3.getDirection().multiply(-1.6D)));
      var1.add(0.0D, 0.2D, 0.0D);
      var3 = var1.clone();
      var4.add(var3.clone().add(var3.getDirection().multiply(-0.4D)));
      var5.add(var3.clone().add(var3.getDirection().multiply(-0.6000000000000001D)));
      var5.add(var3.clone().add(var3.getDirection().multiply(-0.8D)));
      var5.add(var3.clone().add(var3.getDirection().multiply(-1.0D)));
      var5.add(var3.clone().add(var3.getDirection().multiply(-1.2000000000000002D)));
      var6.add(var3.clone().add(var3.getDirection().multiply(-1.4000000000000001D)));
      var6.add(var3.clone().add(var3.getDirection().multiply(-1.6D)));
      var1.add(0.0D, 0.2D, 0.0D);
      var3 = var1.clone();
      var5.add(var3.clone().add(var3.getDirection().multiply(-0.4D)));
      var5.add(var3.clone().add(var3.getDirection().multiply(-0.6000000000000001D)));
      var5.add(var3.clone().add(var3.getDirection().multiply(-0.8D)));
      var5.add(var3.clone().add(var3.getDirection().multiply(-1.0D)));
      var6.add(var3.clone().add(var3.getDirection().multiply(-1.2000000000000002D)));
      var6.add(var3.clone().add(var3.getDirection().multiply(-1.4000000000000001D)));
      var6.add(var3.clone().add(var3.getDirection().multiply(-1.6D)));
      var6.add(var3.clone().add(var3.getDirection().multiply(-1.8D)));
      var1.add(0.0D, 0.2D, 0.0D);
      var3 = var1.clone();
      var6.add(var3.clone().add(var3.getDirection().multiply(-2.0D)));
      var5.add(var3.clone().add(var3.getDirection().multiply(-0.6000000000000001D)));
      var5.add(var3.clone().add(var3.getDirection().multiply(-0.8D)));
      var6.add(var3.clone().add(var3.getDirection().multiply(-1.0D)));
      var6.add(var3.clone().add(var3.getDirection().multiply(-1.2000000000000002D)));
      var6.add(var3.clone().add(var3.getDirection().multiply(-1.4000000000000001D)));
      var6.add(var3.clone().add(var3.getDirection().multiply(-1.6D)));
      var6.add(var3.clone().add(var3.getDirection().multiply(-1.8D)));
      var1.add(0.0D, 0.2D, 0.0D);
      var3 = var1.clone();
      var6.add(var3.clone().add(var3.getDirection().multiply(-1.0D)));
      var6.add(var3.clone().add(var3.getDirection().multiply(-1.2000000000000002D)));
      var6.add(var3.clone().add(var3.getDirection().multiply(-1.4000000000000001D)));
      var6.add(var3.clone().add(var3.getDirection().multiply(-1.6D)));
      var6.add(var3.clone().add(var3.getDirection().multiply(-1.8D)));
      var6.add(var3.clone().add(var3.getDirection().multiply(-2.0D)));
      var6.add(var3.clone().add(var3.getDirection().multiply(-2.2D)));
      var1.add(0.0D, 0.2D, 0.0D);
      var3 = var1.clone();
      var6.add(var3.clone().add(var3.getDirection().multiply(-1.4000000000000001D)));
      var6.add(var3.clone().add(var3.getDirection().multiply(-1.6D)));
      var6.add(var3.clone().add(var3.getDirection().multiply(-1.8D)));
      var6.add(var3.clone().add(var3.getDirection().multiply(-2.0D)));
      var6.add(var3.clone().add(var3.getDirection().multiply(-2.2D)));
      return new Cache(var0, var1, var4, var5, var6);
   }

   public static void Display(Location var0, ParticleEffect.OrdinaryColor var1, ParticleEffect.OrdinaryColor var2, ParticleEffect.OrdinaryColor var3) {
      Location var4 = var0.clone();
      ParticleEffect.REDSTONE.display(var1, var4.clone().add(var4.getDirection().multiply(-1.2000000000000002D)), 30.0D);
      ParticleEffect.REDSTONE.display(var1, var4.clone().add(var4.getDirection().multiply(-1.4000000000000001D)), 30.0D);
      var0.add(0.0D, 0.2D, 0.0D);
      var4 = var0.clone();
      ParticleEffect.REDSTONE.display(var1, var4.clone().add(var4.getDirection().multiply(-1.0D)), 30.0D);
      ParticleEffect.REDSTONE.display(var1, var4.clone().add(var4.getDirection().multiply(-1.2000000000000002D)), 30.0D);
      ParticleEffect.REDSTONE.display(var1, var4.clone().add(var4.getDirection().multiply(-1.4000000000000001D)), 30.0D);
      ParticleEffect.REDSTONE.display(var1, var4.clone().add(var4.getDirection().multiply(-1.6D)), 30.0D);
      var0.add(0.0D, 0.2D, 0.0D);
      var4 = var0.clone();
      ParticleEffect.REDSTONE.display(var1, var4.clone().add(var4.getDirection().multiply(-0.8D)), 30.0D);
      ParticleEffect.REDSTONE.display(var1, var4.clone().add(var4.getDirection().multiply(-1.0D)), 30.0D);
      ParticleEffect.REDSTONE.display(var1, var4.clone().add(var4.getDirection().multiply(-1.2000000000000002D)), 30.0D);
      ParticleEffect.REDSTONE.display(var1, var4.clone().add(var4.getDirection().multiply(-1.4000000000000001D)), 30.0D);
      ParticleEffect.REDSTONE.display(var1, var4.clone().add(var4.getDirection().multiply(-1.6D)), 30.0D);
      var0.add(0.0D, 0.2D, 0.0D);
      var4 = var0.clone();
      ParticleEffect.REDSTONE.display(var1, var4.clone().add(var4.getDirection().multiply(-0.6000000000000001D)), 30.0D);
      ParticleEffect.REDSTONE.display(var1, var4.clone().add(var4.getDirection().multiply(-0.8D)), 30.0D);
      ParticleEffect.REDSTONE.display(var1, var4.clone().add(var4.getDirection().multiply(-1.0D)), 30.0D);
      ParticleEffect.REDSTONE.display(var2, var4.clone().add(var4.getDirection().multiply(-1.2000000000000002D)), 30.0D);
      ParticleEffect.REDSTONE.display(var2, var4.clone().add(var4.getDirection().multiply(-1.4000000000000001D)), 30.0D);
      ParticleEffect.REDSTONE.display(var2, var4.clone().add(var4.getDirection().multiply(-1.6D)), 30.0D);
      var0.add(0.0D, 0.2D, 0.0D);
      var4 = var0.clone();
      ParticleEffect.REDSTONE.display(var1, var4.clone().add(var4.getDirection().multiply(-0.4D)), 30.0D);
      ParticleEffect.REDSTONE.display(var1, var4.clone().add(var4.getDirection().multiply(-0.6000000000000001D)), 30.0D);
      ParticleEffect.REDSTONE.display(var1, var4.clone().add(var4.getDirection().multiply(-0.8D)), 30.0D);
      ParticleEffect.REDSTONE.display(var2, var4.clone().add(var4.getDirection().multiply(-1.0D)), 30.0D);
      ParticleEffect.REDSTONE.display(var2, var4.clone().add(var4.getDirection().multiply(-1.2000000000000002D)), 30.0D);
      ParticleEffect.REDSTONE.display(var2, var4.clone().add(var4.getDirection().multiply(-1.4000000000000001D)), 30.0D);
      var0.add(0.0D, 0.2D, 0.0D);
      var4 = var0.clone();
      ParticleEffect.REDSTONE.display(var1, var4.clone().add(var4.getDirection().multiply(-0.4D)), 30.0D);
      ParticleEffect.REDSTONE.display(var1, var4.clone().add(var4.getDirection().multiply(-0.6000000000000001D)), 30.0D);
      ParticleEffect.REDSTONE.display(var2, var4.clone().add(var4.getDirection().multiply(-0.8D)), 30.0D);
      ParticleEffect.REDSTONE.display(var2, var4.clone().add(var4.getDirection().multiply(-1.0D)), 30.0D);
      ParticleEffect.REDSTONE.display(var2, var4.clone().add(var4.getDirection().multiply(-1.2000000000000002D)), 30.0D);
      ParticleEffect.REDSTONE.display(var2, var4.clone().add(var4.getDirection().multiply(-1.4000000000000001D)), 30.0D);
      var0.add(0.0D, 0.2D, 0.0D);
      var4 = var0.clone();
      ParticleEffect.REDSTONE.display(var1, var4.clone().add(var4.getDirection().multiply(-0.4D)), 30.0D);
      ParticleEffect.REDSTONE.display(var1, var4.clone().add(var4.getDirection().multiply(-0.6000000000000001D)), 30.0D);
      ParticleEffect.REDSTONE.display(var2, var4.clone().add(var4.getDirection().multiply(-0.8D)), 30.0D);
      ParticleEffect.REDSTONE.display(var2, var4.clone().add(var4.getDirection().multiply(-1.0D)), 30.0D);
      ParticleEffect.REDSTONE.display(var2, var4.clone().add(var4.getDirection().multiply(-1.2000000000000002D)), 30.0D);
      ParticleEffect.REDSTONE.display(var2, var4.clone().add(var4.getDirection().multiply(-1.4000000000000001D)), 30.0D);
      ParticleEffect.REDSTONE.display(var2, var4.clone().add(var4.getDirection().multiply(-1.6D)), 30.0D);
      var0.add(0.0D, 0.2D, 0.0D);
      var4 = var0.clone();
      ParticleEffect.REDSTONE.display(var1, var4.clone().add(var4.getDirection().multiply(-0.4D)), 30.0D);
      ParticleEffect.REDSTONE.display(var2, var4.clone().add(var4.getDirection().multiply(-0.6000000000000001D)), 30.0D);
      ParticleEffect.REDSTONE.display(var2, var4.clone().add(var4.getDirection().multiply(-0.8D)), 30.0D);
      ParticleEffect.REDSTONE.display(var2, var4.clone().add(var4.getDirection().multiply(-1.0D)), 30.0D);
      ParticleEffect.REDSTONE.display(var2, var4.clone().add(var4.getDirection().multiply(-1.2000000000000002D)), 30.0D);
      ParticleEffect.REDSTONE.display(var3, var4.clone().add(var4.getDirection().multiply(-1.4000000000000001D)), 30.0D);
      ParticleEffect.REDSTONE.display(var3, var4.clone().add(var4.getDirection().multiply(-1.6D)), 30.0D);
      var0.add(0.0D, 0.2D, 0.0D);
      var4 = var0.clone();
      ParticleEffect.REDSTONE.display(var2, var4.clone().add(var4.getDirection().multiply(-0.4D)), 30.0D);
      ParticleEffect.REDSTONE.display(var2, var4.clone().add(var4.getDirection().multiply(-0.6000000000000001D)), 30.0D);
      ParticleEffect.REDSTONE.display(var2, var4.clone().add(var4.getDirection().multiply(-0.8D)), 30.0D);
      ParticleEffect.REDSTONE.display(var2, var4.clone().add(var4.getDirection().multiply(-1.0D)), 30.0D);
      ParticleEffect.REDSTONE.display(var3, var4.clone().add(var4.getDirection().multiply(-1.2000000000000002D)), 30.0D);
      ParticleEffect.REDSTONE.display(var3, var4.clone().add(var4.getDirection().multiply(-1.4000000000000001D)), 30.0D);
      ParticleEffect.REDSTONE.display(var3, var4.clone().add(var4.getDirection().multiply(-1.6D)), 30.0D);
      ParticleEffect.REDSTONE.display(var3, var4.clone().add(var4.getDirection().multiply(-1.8D)), 30.0D);
      var0.add(0.0D, 0.2D, 0.0D);
      var4 = var0.clone();
      ParticleEffect.REDSTONE.display(var3, var4.clone().add(var4.getDirection().multiply(-2.0D)), 30.0D);
      ParticleEffect.REDSTONE.display(var2, var4.clone().add(var4.getDirection().multiply(-0.6000000000000001D)), 30.0D);
      ParticleEffect.REDSTONE.display(var2, var4.clone().add(var4.getDirection().multiply(-0.8D)), 30.0D);
      ParticleEffect.REDSTONE.display(var3, var4.clone().add(var4.getDirection().multiply(-1.0D)), 30.0D);
      ParticleEffect.REDSTONE.display(var3, var4.clone().add(var4.getDirection().multiply(-1.2000000000000002D)), 30.0D);
      ParticleEffect.REDSTONE.display(var3, var4.clone().add(var4.getDirection().multiply(-1.4000000000000001D)), 30.0D);
      ParticleEffect.REDSTONE.display(var3, var4.clone().add(var4.getDirection().multiply(-1.6D)), 30.0D);
      ParticleEffect.REDSTONE.display(var3, var4.clone().add(var4.getDirection().multiply(-1.8D)), 30.0D);
      var0.add(0.0D, 0.2D, 0.0D);
      var4 = var0.clone();
      ParticleEffect.REDSTONE.display(var3, var4.clone().add(var4.getDirection().multiply(-1.0D)), 30.0D);
      ParticleEffect.REDSTONE.display(var3, var4.clone().add(var4.getDirection().multiply(-1.2000000000000002D)), 30.0D);
      ParticleEffect.REDSTONE.display(var3, var4.clone().add(var4.getDirection().multiply(-1.4000000000000001D)), 30.0D);
      ParticleEffect.REDSTONE.display(var3, var4.clone().add(var4.getDirection().multiply(-1.6D)), 30.0D);
      ParticleEffect.REDSTONE.display(var3, var4.clone().add(var4.getDirection().multiply(-1.8D)), 30.0D);
      ParticleEffect.REDSTONE.display(var3, var4.clone().add(var4.getDirection().multiply(-2.0D)), 30.0D);
      ParticleEffect.REDSTONE.display(var3, var4.clone().add(var4.getDirection().multiply(-2.2D)), 30.0D);
      var0.add(0.0D, 0.2D, 0.0D);
      var4 = var0.clone();
      ParticleEffect.REDSTONE.display(var3, var4.clone().add(var4.getDirection().multiply(-1.4000000000000001D)), 30.0D);
      ParticleEffect.REDSTONE.display(var3, var4.clone().add(var4.getDirection().multiply(-1.6D)), 30.0D);
      ParticleEffect.REDSTONE.display(var3, var4.clone().add(var4.getDirection().multiply(-1.8D)), 30.0D);
      ParticleEffect.REDSTONE.display(var3, var4.clone().add(var4.getDirection().multiply(-2.0D)), 30.0D);
      ParticleEffect.REDSTONE.display(var3, var4.clone().add(var4.getDirection().multiply(-2.2D)), 30.0D);
   }
}
