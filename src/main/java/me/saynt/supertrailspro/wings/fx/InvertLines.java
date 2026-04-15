package me.saynt.supertrailspro.wings.fx;

import java.util.Arrays;
import me.saynt.supertrailspro.particlelib.ParticleEffect;
import me.saynt.supertrailspro.trails.Task;
import me.saynt.supertrailspro.trails.TrailWings;
import me.saynt.supertrailspro.trails.WingsPattern;
import me.saynt.supertrailspro.trails.WingsPostFX;

public class InvertLines extends WingsPostFX {
   public InvertLines(String var1) {
      super(var1);
   }

   public void getPixel(TrailWings var1, ParticleEffect.OrdinaryColor var2, int var3, int var4) {
      if (this.line(var4)) {
         int var5 = 255 - var2.red;
         int var6 = 255 - var2.green;
         int var7 = 255 - var2.blue;
         var1.color = new ParticleEffect.OrdinaryColor(var5, var6, var7);
      }

   }

   public void getPixel(TrailWings var1, WingsPattern var2, ParticleEffect.OrdinaryColor var3, int var4, int var5) {
      if (this.line(var5)) {
         int var6 = 255 - var3.red;
         int var7 = 255 - var3.green;
         int var8 = 255 - var3.blue;
         var2.color = new ParticleEffect.OrdinaryColor(var6, var7, var8);
      }

   }

   public boolean line(int var1) {
      if (var1 % 2 == 0) {
         return Task.invert;
      } else {
         return !Task.invert;
      }
   }

   public int getm(int var1, int var2) {
      int var3 = (int)((double)var1 - (double)(var2 * var1) / 100.0D);
      return var3 < 10 ? 10 : var3;
   }

   public int get(int var1, int var2) {
      int var3 = (int)((double)var1 + (double)(var2 * var1) / 100.0D);
      return var3 > 255 ? 255 : var3;
   }

   public boolean isInArray(TrailWings var1, int var2, int var3) {
      int[][] var7;
      int var6 = (var7 = var1.inline).length;

      for(int var5 = 0; var5 < var6; ++var5) {
         int[] var4 = var7[var5];
         if (Arrays.equals(var4, new int[]{var2, var3})) {
            return true;
         }
      }

      return false;
   }
}
