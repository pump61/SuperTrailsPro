package me.saynt.supertrailspro.community;

import org.bukkit.entity.Player;

public class RequestManager {
   public static String RCkey = null;

   public static void sendRequest(Player var0, String var1) {
      CommunityThread var2 = new CommunityThread();
      CommunityThread.pe = var0;
      CommunityThread.re = var1;
      var2.start();
   }

   public static void listener(Player var0, String var1) {
      if (var0 != null && var1 != null) {
         String var2 = var1.split("!!!")[0];
         if (!var2.equals("none")) {
            if (var2.equals("login")) {
               LoginListener.a(var0, var1);
            } else if (var2.equals("inv")) {
               InventoryListener.openInv(var0, var1);
            } else if (var2.equals("invcmd")) {
               InventoryListener.addCmds(var0, var1);
            } else if (var2.equals("msg")) {
               if (var0 != null) {
                  var0.sendMessage(var1.split("!!!")[1]);
               }
            } else if (var2.equals("sendw")) {
               InventoryListener.openSender(var0);
            } else if (var2.equals("loadw")) {
               FileSender.startLoading(var0, var1);
            } else if (var2.equals("setlogin")) {
               InstantAccess.setlogin(var0, var1);
            } else if (var2.equals("faillogin")) {
               InstantAccess.fail(var0, var1);
            } else {
               var0.sendMessage("§7§l[SuperTrails Community] §cAction not supported! Try to update plugin.");
            }

         }
      }
   }
}
