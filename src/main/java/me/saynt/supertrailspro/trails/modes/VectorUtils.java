package me.saynt.supertrailspro.trails.modes;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class VectorUtils {
   private VectorUtils() {
   }

   public static final Vector rotateAroundAxisX(Vector var0, double var1) {
      double var7 = Math.cos(var1);
      double var9 = Math.sin(var1);
      double var3 = var0.getY() * var7 - var0.getZ() * var9;
      double var5 = var0.getY() * var9 + var0.getZ() * var7;
      return var0.setY(var3).setZ(var5);
   }

   public static final Vector rotateAroundAxisY(Vector var0, double var1) {
      double var7 = Math.cos(var1);
      double var9 = Math.sin(var1);
      double var3 = var0.getX() * var7 + var0.getZ() * var9;
      double var5 = var0.getX() * -var9 + var0.getZ() * var7;
      return var0.setX(var3).setZ(var5);
   }

   public static final Vector rotateAroundAxisZ(Vector var0, double var1) {
      double var7 = Math.cos(var1);
      double var9 = Math.sin(var1);
      double var3 = var0.getX() * var7 - var0.getY() * var9;
      double var5 = var0.getX() * var9 + var0.getY() * var7;
      return var0.setX(var3).setY(var5);
   }

   public static final Vector rotateVector(Vector var0, double var1, double var3, double var5) {
      rotateAroundAxisX(var0, var1);
      rotateAroundAxisY(var0, var3);
      rotateAroundAxisZ(var0, var5);
      return var0;
   }

   public static final Vector rotateVector(Vector var0, Location var1) {
      return rotateVector(var0, var1.getYaw(), var1.getPitch());
   }

   public static final Vector rotateVector(Vector var0, float var1, float var2) {
      double var3 = Math.toRadians((double)(-1.0F * (var1 + 90.0F)));
      double var5 = Math.toRadians((double)(-var2));
      double var7 = Math.cos(var3);
      double var9 = Math.cos(var5);
      double var11 = Math.sin(var3);
      double var13 = Math.sin(var5);
      double var15 = var0.getX();
      double var17 = var0.getY();
      double var21 = var15 * var9 - var17 * var13;
      double var23 = var15 * var13 + var17 * var9;
      double var19 = var0.getZ();
      double var25 = var19 * var7 - var21 * var11;
      var21 = var19 * var11 + var21 * var7;
      return new Vector(var21, var23, var25);
   }

   public static final double angleToXAxis(Vector var0) {
      return Math.atan2(var0.getX(), var0.getY());
   }
}
