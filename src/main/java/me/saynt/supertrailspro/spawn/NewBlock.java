package me.saynt.supertrailspro.spawn;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import me.saynt.supertrailspro.PluginMessages;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class NewBlock {
   public static Material getMaterial(String var0) {
      Material var1 = Material.getMaterial(var0);
      if (var1 != null) {
         return var1;
      } else {
         try {
            Class var2 = Class.forName("org.bukkit.Material");
            Method var3 = var2.getDeclaredMethod("getMaterial", String.class);
            Object var4 = var3.invoke((Object)null, var0);
            return (Material)var4;
         } catch (Exception var5) {
            PluginMessages.Error("Some item id you entered use incorrect format > " + var0);
            return null;
         }
      }
   }

   public static Material getMaterialIgnoreErrors(String var0) {
      Material var1 = Material.getMaterial(var0);
      if (var1 != null) {
         return var1;
      } else {
         try {
            Class var2 = Class.forName("org.bukkit.Material");
            Method var3 = var2.getDeclaredMethod("getMaterial", String.class);
            Object var4 = var3.invoke((Object)null, var0);
            return (Material)var4;
         } catch (Exception var5) {
            return null;
         }
      }
   }

   public static void parse(Player var0, String var1) {
      Material var2 = getMaterial(var1);

      try {
         Field var3 = Material.class.getDeclaredField("id");
         var3.setAccessible(true);
         int var4 = (Integer)var3.get(var2);
         var0.sendMessage("Itemid = " + var4);
      } catch (Exception var5) {
         var5.printStackTrace();
      }

      var0.sendBlockChange(var0.getLocation(), var2, (byte)0);
   }

   public static Material readMaterial(Block var0) {
      Material var1 = var0.getType();
      if (var1 != null && var1 != Material.AIR) {
         return var1;
      } else {
         try {
            Class var2 = var0.getClass();
            Method var3 = var2.getDeclaredMethod("getType");
            Material var4 = (Material)var3.invoke(var0, (Object[])null);
            return var4;
         } catch (Exception var5) {
            var5.printStackTrace();
            return Material.AIR;
         }
      }
   }
}
