package me.saynt.supertrailspro.spawn;

import java.lang.reflect.Method;
import me.saynt.supertrailspro.ServerVersion;
import me.saynt.supertrailspro.ServerVersionsEnum;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class BlockPacketer {
   static Class pla;
   static Class mat;
   static Class data;
   static Class location;
   static Method msendchange;
   static Method mcreatebdata;

   public static void prepare() {
      if (ServerVersion.higherThanOrEqual(ServerVersionsEnum.S116)) {
         try {
            pla = Class.forName("org.bukkit.entity.Player");
            mat = Class.forName("org.bukkit.Material");
            data = Class.forName("org.bukkit.block.data.BlockData");
            location = Class.forName("org.bukkit.Location");
            mcreatebdata = mat.getDeclaredMethod("createBlockData");
            msendchange = pla.getDeclaredMethod("sendBlockChange", location, data);
         } catch (Exception var1) {
            var1.printStackTrace();
         }

      }
   }

   public static void sendChange(Location var0, Material var1, byte var2, Player var3) {
      if (var1 != null && (var1 == Material.OAK_LOG || var1 == Material.WHITE_STAINED_GLASS || var1 == Material.DARK_OAK_LEAVES || var1 == Material.OAK_LEAVES || var1 == Material.WHITE_TERRACOTTA || var1 == Material.WHITE_WOOL)) {
         var3.sendBlockChange(var0, var1, var2);
      } else {
         if (!ServerVersion.higherThanOrEqual(ServerVersionsEnum.S116)) {
            var3.sendBlockChange(var0, var1, var2);
         } else {
            try {
               Object var4 = mcreatebdata.invoke(var1, (Object[])null);
               msendchange.invoke(var3, var0, var4);
            } catch (Exception var5) {
               var5.printStackTrace();
            }
         }

      }
   }
}
