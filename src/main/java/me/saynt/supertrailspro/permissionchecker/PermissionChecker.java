package me.saynt.supertrailspro.permissionchecker;

import java.util.Iterator;
import me.saynt.supertrailspro.P;
import me.saynt.supertrailspro.STUtils;
import me.saynt.supertrailspro.SuperTrails;
import me.saynt.supertrailspro.trails.Trail;
import me.saynt.supertrailspro.trails.TrailsUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PermissionChecker {
   public static void start() {
      if (STUtils.optionb("PermissionChecker.TimerChecker")) {
         BukkitRunnable var0 = new BukkitRunnable() {
            public void run() {
               Iterator var2 = Bukkit.getOnlinePlayers().iterator();

               while(var2.hasNext()) {
                  Player var1 = (Player)var2.next();
                  PermissionChecker.check(var1);
               }

            }
         };
         var0.runTaskTimer(SuperTrails.p, 1L, 200L);
      }
   }

   public static void check(Player var0) {
      if (var0 != null) {
         Trail var1 = TrailsUtil.getRTrail(var0);
         if (var1 != null && var1.getPermission() != null) {
            if (!P.has(var0, var1.getPermission())) {
               TrailsUtil.SetTrail(var0, 0);
            }

         }
      }
   }
}
