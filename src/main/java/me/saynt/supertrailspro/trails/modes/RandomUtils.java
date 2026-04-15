package me.saynt.supertrailspro.trails.modes;

import java.util.Random;
import org.bukkit.Material;
import org.bukkit.util.Vector;

public class RandomUtils {
   public static final Random random = new Random(System.nanoTime());

   private RandomUtils() {
   }

   public static Vector getRandomVector() {
      double var0 = random.nextDouble() * 2.0D - 1.0D;
      double var2 = random.nextDouble() * 2.0D - 1.0D;
      double var4 = random.nextDouble() * 2.0D - 1.0D;
      return (new Vector(var0, var2, var4)).normalize();
   }

   public static Vector getRandomCircleVector() {
      double var0 = random.nextDouble() * 2.0D * 3.141592653589793D;
      double var2 = Math.cos(var0);
      double var4 = Math.sin(var0);
      return new Vector(var2, 0.0D, var4);
   }

   public static Material getRandomMaterial(Material[] var0) {
      return var0[random.nextInt(var0.length)];
   }

   public static double getRandomAngle() {
      return random.nextDouble() * 2.0D * 3.141592653589793D;
   }
}
