package me.saynt.supertrailspro.trails.modes;

import java.util.Random;

public class MathUtils {
   public static final float nanoToSec = 1.0E-9F;
   public static final float FLOAT_ROUNDING_ERROR = 1.0E-6F;
   public static final float PI = 3.1415927F;
   public static final float PI2 = 6.2831855F;
   public static final float SQRT_3 = 1.73205F;
   public static final float E = 2.7182817F;
   private static final int SIN_BITS = 14;
   private static final int SIN_MASK = 16383;
   private static final int SIN_COUNT = 16384;
   private static final float radFull = 6.2831855F;
   private static final float degFull = 360.0F;
   private static final float radToIndex = 2607.5945F;
   private static final float degToIndex = 45.511112F;
   public static final float radiansToDegrees = 57.295776F;
   public static final float radDeg = 57.295776F;
   public static final float degreesToRadians = 0.017453292F;
   public static final float degRad = 0.017453292F;
   private static final int ATAN2_BITS = 7;
   private static final int ATAN2_BITS2 = 14;
   private static final int ATAN2_MASK = 16383;
   private static final int ATAN2_COUNT = 16384;
   static final int ATAN2_DIM = (int)Math.sqrt(16384.0D);
   private static final float INV_ATAN2_DIM_MINUS_1;
   public static final Random random;
   private static final int BIG_ENOUGH_INT = 16384;
   private static final double BIG_ENOUGH_FLOOR = 16384.0D;
   private static final double CEIL = 0.9999999D;
   private static final double BIG_ENOUGH_CEIL = 16384.999999999996D;
   private static final double BIG_ENOUGH_ROUND = 16384.5D;

   static {
      INV_ATAN2_DIM_MINUS_1 = 1.0F / (float)(ATAN2_DIM - 1);
      random = new Random();
   }

   public static final float sin(float var0) {
      return MathUtils.Sin.table[(int)(var0 * 2607.5945F) & 16383];
   }

   public static final float cos(float var0) {
      return MathUtils.Sin.table[(int)((var0 + 1.5707964F) * 2607.5945F) & 16383];
   }

   public static final float sinDeg(float var0) {
      return MathUtils.Sin.table[(int)(var0 * 45.511112F) & 16383];
   }

   public static final float cosDeg(float var0) {
      return MathUtils.Sin.table[(int)((var0 + 90.0F) * 45.511112F) & 16383];
   }

   public static final float atan2(float var0, float var1) {
      float var2;
      float var3;
      if (var1 < 0.0F) {
         if (var0 < 0.0F) {
            var0 = -var0;
            var3 = 1.0F;
         } else {
            var3 = -1.0F;
         }

         var1 = -var1;
         var2 = -3.1415927F;
      } else {
         if (var0 < 0.0F) {
            var0 = -var0;
            var3 = -1.0F;
         } else {
            var3 = 1.0F;
         }

         var2 = 0.0F;
      }

      float var4 = 1.0F / ((var1 < var0 ? var0 : var1) * INV_ATAN2_DIM_MINUS_1);
      if (var4 == Float.POSITIVE_INFINITY) {
         return ((float)Math.atan2((double)var0, (double)var1) + var2) * var3;
      } else {
         int var5 = (int)(var1 * var4);
         int var6 = (int)(var0 * var4);
         return (MathUtils.Atan2.table[var6 * ATAN2_DIM + var5] + var2) * var3;
      }
   }

   public static final int random(int var0) {
      return random.nextInt(var0 + 1);
   }

   public static final int random(int var0, int var1) {
      return var0 + random.nextInt(var1 - var0 + 1);
   }

   public static final boolean randomBoolean() {
      return random.nextBoolean();
   }

   public static final boolean randomBoolean(float var0) {
      return random() < var0;
   }

   public static final float random() {
      return random.nextFloat();
   }

   public static final float random(float var0) {
      return random.nextFloat() * var0;
   }

   public static final float random(float var0, float var1) {
      return var0 + random.nextFloat() * (var1 - var0);
   }

   public static int nextPowerOfTwo(int var0) {
      if (var0 == 0) {
         return 1;
      } else {
         --var0;
         var0 |= var0 >> 1;
         var0 |= var0 >> 2;
         var0 |= var0 >> 4;
         var0 |= var0 >> 8;
         var0 |= var0 >> 16;
         return var0 + 1;
      }
   }

   public static boolean isPowerOfTwo(int var0) {
      return var0 != 0 && (var0 & var0 - 1) == 0;
   }

   public static int clamp(int var0, int var1, int var2) {
      if (var0 < var1) {
         return var1;
      } else {
         return var0 > var2 ? var2 : var0;
      }
   }

   public static short clamp(short var0, short var1, short var2) {
      if (var0 < var1) {
         return var1;
      } else {
         return var0 > var2 ? var2 : var0;
      }
   }

   public static float clamp(float var0, float var1, float var2) {
      if (var0 < var1) {
         return var1;
      } else {
         return var0 > var2 ? var2 : var0;
      }
   }

   public static int floor(float var0) {
      return (int)((double)var0 + 16384.0D) - 16384;
   }

   public static int floorPositive(float var0) {
      return (int)var0;
   }

   public static int ceil(float var0) {
      return (int)((double)var0 + 16384.999999999996D) - 16384;
   }

   public static int ceilPositive(float var0) {
      return (int)((double)var0 + 0.9999999D);
   }

   public static int round(float var0) {
      return (int)((double)var0 + 16384.5D) - 16384;
   }

   public static int roundPositive(float var0) {
      return (int)(var0 + 0.5F);
   }

   public static boolean isZero(float var0) {
      return Math.abs(var0) <= 1.0E-6F;
   }

   public static boolean isZero(float var0, float var1) {
      return Math.abs(var0) <= var1;
   }

   public static boolean isEqual(float var0, float var1) {
      return Math.abs(var0 - var1) <= 1.0E-6F;
   }

   public static boolean isEqual(float var0, float var1, float var2) {
      return Math.abs(var0 - var1) <= var2;
   }

   public static boolean isFinite(double var0) {
      return !Double.isNaN(var0) && !Double.isInfinite(var0);
   }

   private static class Atan2 {
      static final float[] table = new float[16384];

      static {
         for(int var0 = 0; var0 < MathUtils.ATAN2_DIM; ++var0) {
            for(int var1 = 0; var1 < MathUtils.ATAN2_DIM; ++var1) {
               float var2 = (float)var0 / (float)MathUtils.ATAN2_DIM;
               float var3 = (float)var1 / (float)MathUtils.ATAN2_DIM;
               table[var1 * MathUtils.ATAN2_DIM + var0] = (float)Math.atan2((double)var3, (double)var2);
            }
         }

      }
   }

   private static class Sin {
      static final float[] table = new float[16384];

      static {
         int var0;
         for(var0 = 0; var0 < 16384; ++var0) {
            table[var0] = (float)Math.sin((double)(((float)var0 + 0.5F) / 16384.0F * 6.2831855F));
         }

         for(var0 = 0; var0 < 360; var0 += 90) {
            table[(int)((float)var0 * 45.511112F) & 16383] = (float)Math.sin((double)((float)var0 * 0.017453292F));
         }

      }
   }
}
