package me.saynt.supertrailspro;

import me.saynt.supertrailspro.data.threads.UpdateChecker;
import me.saynt.supertrailspro.events.Join;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class Settings {
   public static void a(Player var0, String[] var1) {
      if (var1.length != 0 || var1[0].equalsIgnoreCase("settings")) {
         if (var1.length == 2 && var0.isOp()) {
            if (var1[1].equalsIgnoreCase("nttoggle")) {
               toggleOption("UpdateNotify");
               UpdateChecker.settings(var0);
            } else if (var1[1].equalsIgnoreCase("uptoggle")) {
               toggleOption("UpdateChecker");
               UpdateChecker.settings(var0);
            } else if (var1[1].equalsIgnoreCase("nt")) {
               UpdateChecker.settings(var0);
            } else if (var1[1].equalsIgnoreCase("hide")) {
               Join.up = false;
               var0.sendMessage("§cMessages disabled!");
            }
         }

      }
   }

   public static void toggleOption(String var0) {
      ConfigurationSection var1 = SuperTrails.p.getConfig().getConfigurationSection("Options");
      if (var1 != null && var1.get(var0) != null) {
         if (var1.getBoolean(var0)) {
            var1.set(var0, false);
         } else {
            var1.set(var0, true);
         }

         SuperTrails.p.saveConfig();
      }
   }
}
