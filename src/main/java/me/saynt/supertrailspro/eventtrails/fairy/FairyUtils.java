package me.saynt.supertrailspro.eventtrails.fairy;

import java.util.HashMap;
import me.saynt.supertrailspro.PluginMessages;
import me.saynt.supertrailspro.data.DataManager;
import me.saynt.supertrailspro.data.PlayerData;
import me.saynt.supertrailspro.trails.TrailType;
import me.saynt.supertrailspro.trails.TrailsUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class FairyUtils {
   private static HashMap<Player, Fairy> f = new HashMap();

   public static Fairy getPlayerFairy(Player var0) {
      Fairy var1 = null;
      var1 = hasFairy(var0) ? readFairy(var0) : createPlayerFairy(var0);
      return var1;
   }

   public static boolean hasFairy(Player var0) {
      return f.containsKey(var0);
   }

   public static Fairy readFairy(Player var0) {
      return (Fairy)f.get(var0);
   }

   public static Fairy createPlayerFairy(Player var0) {
      TrailFairy var1 = TrailsUtil.getRTrail(var0).getType() == TrailType.Event_Fairy ? (TrailFairy)TrailsUtil.getRTrail(var0) : null;
      if (var1 == null) {
         return null;
      } else {
         Fairy var2 = new Fairy(var0, var1.getColor());
         PluginMessages.Debug(var1.getId() + " " + var1.getColor().getName() + " PD" + DataManager.getData(var0).getTrail());
         f.put(var0, var2);
         return var2;
      }
   }

   public static void updateFairy(Player var0) {
      getPlayerFairy(var0).lifetick();
   }

   public static void removeFairy(Player var0) {
      if (hasFairy(var0)) {
         f.remove(var0);
      }
   }

   public static void setFairy(Player var0, int var1) {
      removeFairy(var0);
      DataManager.getData(var0).setTrail(var1);
      DataManager.getData(var0).save();
      updateFairy(var0);
   }

   public static void setFairy(Player var0, PlayerData var1, int var2) {
      removeFairy(var0);
      var1.save();
      updateFairy(var0);
   }

   public static float[] lookAt(Location var0, Location var1) {
      double var2 = var1.getX() - var0.getX();
      double var4 = var1.getY() - var0.getY();
      double var6 = var1.getZ() - var0.getZ();
      float var8 = 0.0F;
      float var9 = 0.0F;
      if (var2 != 0.0D) {
         if (var2 < 0.0D) {
            var8 = 4.712389F;
         } else {
            var8 = 1.5707964F;
         }

         var8 -= (float)Math.atan(var6 / var2);
      } else if (var6 < 0.0D) {
         var8 = 3.1415927F;
      }

      double var10 = Math.sqrt(Math.pow(var2, 2.0D) + Math.pow(var6, 2.0D));
      var9 = (float)(-Math.atan(var4 / var10));
      var8 = -var8 * 180.0F / 3.1415927F;
      var9 = var9 * 180.0F / 3.1415927F;
      return new float[]{var8, var9};
   }

   public static float normalizeYaw(double var0) {
      boolean var2 = false;
      if (var0 < 0.0D) {
         var0 *= -1.0D;
         var2 = true;
      }

      if (var0 <= 180.0D) {
         return (float)((int)(var2 ? var0 * -1.0D : var0));
      } else {
         double var3 = Math.floor(var0 / 180.0D);
         return (float)(var2 ? 180.0D - (var0 - var3 * 180.0D) : (180.0D - (var0 - var3 * 180.0D)) * -1.0D);
      }
   }

   public static int getBestRotation(float var0, float var1) {
      if (var0 - var1 < 5.0F && var0 - var1 > -5.0F) {
         return 0;
      } else {
         var0 = (float)(Math.floor((double)var0) / 9.0D);
         var1 = (float)(Math.floor((double)var1) / 9.0D);
         if (Math.abs(var0 - var1) < 20.0F) {
            return var0 < var1 ? 2 : 1;
         } else {
            return var0 < var1 ? 1 : 2;
         }
      }
   }
}
