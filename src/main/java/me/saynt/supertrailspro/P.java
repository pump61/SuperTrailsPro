package me.saynt.supertrailspro;

import me.saynt.supertrailspro.lang.L;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class P {
   public static boolean hasWings(Player var0, String var1, boolean var2) {
      boolean var3 = has(var0, var1);
      if (!var3 && var2) {
         var0.sendMessage(L.get(var0, "System.NoWingsPermission"));
      }

      return var3;
   }

   public static boolean hasPattern(Player var0, String var1, boolean var2) {
      boolean var3 = has(var0, var1);
      if (!var3 && var2) {
         var0.sendMessage(L.get(var0, "System.NoPatternPermission"));
      }

      return var3;
   }

   public static String gePermissionsMessageLink(Player var0, String var1, String var2, String var3) {
      return has(var0, var1) ? var2 : var3;
   }

   public static boolean admin(CommandSender var0) {
      return !(var0 instanceof Player) ? true : ((Player)var0).hasPermission("trails.admin");
   }

   public static boolean has(Player var0, String var1) {
      var1 = var1.toLowerCase();
      if (var1.equals("trails.mode.default")) {
         return true;
      } else if (var0.hasPermission(var1)) {
         return true;
      } else if (var0.hasPermission("trails.admin")) {
         return true;
      } else if (var0.hasPermission("trails.alltrails")) {
         return var1.startsWith("trails.particle.") || var1.startsWith("trails.block.") || var1.startsWith("trails.wings.") || var1.startsWith("trails.pattern.") || var1.startsWith("trails.mode.") || var1.equalsIgnoreCase("trails.rains") || var1.equalsIgnoreCase("trails.keeper");
      } else if (var1.startsWith("trails.particle.") && var0.hasPermission("trails.allparticles")) {
         return true;
      } else if (var1.startsWith("trails.block.") && var0.hasPermission("trails.allblocks")) {
         return true;
      } else if (var1.startsWith("trails.wings.") && var0.hasPermission("trails.allwings")) {
         return true;
      } else if (var1.startsWith("trails.pattern.") && var0.hasPermission("trails.allpatterns")) {
         return true;
      } else if (var1.startsWith("trails.mode.") && var0.hasPermission("trails.allmodes")) {
         return true;
      } else if (var1.startsWith("trails.rain.") && var0.hasPermission("trails.rains")) {
         return true;
      } else {
         return var1.startsWith("trails.community.") && var0.hasPermission("trails.community.full");
      }
   }
}
