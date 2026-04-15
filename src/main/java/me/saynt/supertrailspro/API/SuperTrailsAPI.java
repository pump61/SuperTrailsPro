package me.saynt.supertrailspro.API;

import me.saynt.supertrailspro.data.DataManager;
import me.saynt.supertrailspro.data.PlayerData;
import org.bukkit.entity.Player;

public class SuperTrailsAPI {
   public static void setTrail(Player var0, int var1) {
      PlayerData var2 = DataManager.getData(var0);
      var2.setTrail(var1);
   }

   public int getTrail(Player var1) {
      return DataManager.getData(var1).getTrail();
   }

   public static PlayerData getPlayerData(Player var0) {
      return DataManager.getData(var0);
   }

   public static void openGui(SuperTrailsGui var0) {
   }

   public static void setWings(Player var0, SuperTrailsWings var1) {
      if (var0 != null) {
         PlayerData var2 = getPlayerData(var0);
         if (var2 != null) {
            if (var1.hasPattern()) {
               var2.setPattenWings(var1.getTrailID(), var1.getPattern());
            } else {
               SuperTrailsColor[] var3 = var1.getColors();
               if (var3 != null && var3.length == 3) {
                  var2.setWings(var1.getTrailID(), var3[0].getColorID(), var3[1].getColorID(), var3[2].getColorID());
               }
            }
         }
      }
   }
}
