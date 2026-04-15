package me.saynt.supertrailspro.wings;

import me.saynt.supertrailspro.ServerVersion;
import me.saynt.supertrailspro.ServerVersionsEnum;
import me.saynt.supertrailspro.particlelib.ParticleEffect;
import me.saynt.supertrailspro.particlelib.PlayParticle;
import org.bukkit.Location;

public class ColoredParticle {
   public static void spawn(Location var0, int var1, int var2, int var3) {
      ParticleEffect.OrdinaryColor var4 = new ParticleEffect.OrdinaryColor(var1, var2, var3);
      if (ServerVersion.higherThanOrEqual(ServerVersionsEnum.S113)) {
         PlayParticle.playColoredEffect(var4, var0, 1);
      } else {
         ParticleEffect.REDSTONE.display(var4, var0, 30.0D);
      }
   }

   public static void spawn(Location var0, ParticleEffect.OrdinaryColor var1) {
      if (ServerVersion.higherThanOrEqual(ServerVersionsEnum.S113)) {
         PlayParticle.playColoredEffect(var1, var0, 1);
      } else {
         ParticleEffect.REDSTONE.display(var1, var0, 30.0D);
      }
   }

   public static void spawn(Location var0, int var1) {
      int[] var2 = readARGB(var1);
      spawn(var0, var2[0], var2[1], var2[2]);
   }

   public static int[] readARGB(int var0) {
      int var1 = var0 >> 16 & 255;
      int var2 = var0 >> 8 & 255;
      int var3 = var0 & 255;
      return new int[]{var1, var2, var3};
   }
}
