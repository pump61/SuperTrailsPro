package me.saynt.supertrailspro.particlelib;

import java.util.List;
import me.saynt.supertrailspro.ServerVersion;
import me.saynt.supertrailspro.ServerVersionsEnum;
import org.bukkit.Location;

public class PlayParticle {
   static ParticleLib lib;

   public static void initialize() {
      if (ServerVersion.higherThanOrEqual(ServerVersionsEnum.S117)) {
         Lib17NMS var1 = new Lib17NMS();
         var1.initialize();
         lib = var1;
      } else {
         Particles13 var0 = new Particles13();
         var0.initialize();
         lib = var0;
      }
   }

   public static void createNMSStack(String var0) {
      lib.createNMSStack(var0);
   }

   public static void play(ParticleEffect var0, Location var1, float var2, float var3, float var4, float var5, int var6) {
      lib.play(var0, var1, var2, var3, var4, var5, var6);
   }

   public static void playColoredEffect(ParticleEffect.OrdinaryColor var0, Location var1, int var2) {
      lib.playColoredEffect(var0, var1, var2);
   }

   public static void playItemCrack(Object var0, Location var1, float var2, float var3, float var4, float var5, int var6) {
      lib.playItemCrack(var0, var1, var2, var3, var4, var5, var6);
   }

   public static List<Object> getNMSItems() {
      return lib.getNMSItems();
   }
}
