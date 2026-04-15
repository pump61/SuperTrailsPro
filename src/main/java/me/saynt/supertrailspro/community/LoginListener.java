package me.saynt.supertrailspro.community;

import me.saynt.supertrailspro.SuperTrails;
import org.bukkit.entity.Player;

public class LoginListener {
   public static void a(Player var0, String var1) {
      String var2 = var1.split("!!!")[1];
      if (var2.equalsIgnoreCase("success")) {
         var0.sendMessage("§7§l[SuperTrails Community] §aLogged in!");
         SuperTrails.p.getConfig().set("LicenseKey", SuperTrails.key);
         SuperTrails.p.saveConfig();
      } else if (var2.equalsIgnoreCase("fail")) {
         var0.sendMessage("§7§l[SuperTrails Community] §cWrong key!");
         SuperTrails.p.getConfig().set("LicenseKey", (Object)null);
         SuperTrails.p.saveConfig();
         SuperTrails.key = null;
      } else {
         var0.sendMessage("§7§l[SuperTrails Community] §cError: " + var2);
      }

   }
}
