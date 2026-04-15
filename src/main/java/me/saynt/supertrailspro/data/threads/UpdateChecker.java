package me.saynt.supertrailspro.data.threads;


import java.util.Iterator;
import me.saynt.supertrailspro.CmdD;
import me.saynt.supertrailspro.STUtils;
import me.saynt.supertrailspro.SuperTrails;
import me.saynt.supertrailspro.data.Pages;
import me.saynt.supertrailspro.events.Join;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class UpdateChecker {
   public static int v = 16;
   static int last = 0;
   public static String yes = "§a[on] §7[off]";
   public static String no = "§7[on] §c[off]";

   public static void checkupdates() {
      a();
      if (STUtils.optionb("UpdateChecker")) {
         try {
            String var0 = Pages.getPage("raw.githubusercontent.com/kvq/SuperTrailsPro/master/up");
            last = Integer.parseInt(var0.trim());
         } catch (Exception var1) {
            last = -1;
         }

      }
   }

   public static boolean isUpAvalible() {
      return last > v;
   }

   public static void sendMessage(Player var0) {
      if (last == -1) {
         var0.sendMessage("§7You're offline!");
      } else {
         if (isUpAvalible()) {
            int var1 = last - v;
            var0.sendMessage("§7" + var1 + " " + (var1 > 1 ? "versions" : "version") + " behind");
         } else {
            var0.sendMessage("§aYou're using last version");
         }

      }
   }

   public static int not() {
      return last - v;
   }

   public static void sendToOps() {
      if (STUtils.optionb("UpdateNotify") || isUpAvalible()) {
         Join.up = true;
         Iterator var1 = Bukkit.getOnlinePlayers().iterator();

         while(var1.hasNext()) {
            Player var0 = (Player)var1.next();
            if (!var0.isOp()) {
               return;
            }

            sendNotify(var0);
         }

      }
   }

   public static void sendNotify(Player var0) {
      if (var0.isOp()) {
         CmdD.sendRaw(var0, "[\"\",{\"text\":\"================\"},{\"text\":\"[x]\",\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/supertrails settings hide\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Hide until restart\"}]}}},{\"text\":\"=\",\"color\":\"none\"},{\"text\":\"[!]\",\"color\":\"dark_red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/supertrails settings nt\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Notification settings\"}]}}}]");
         var0.sendMessage("§6§lSuperTrailsPro");
         var0.sendMessage("§a§lNew update available");
         var0.sendMessage("§7================");
      }
   }

   public static void settings(Player var0) {
      var0.sendMessage("§7================");
      CmdD.sendRaw(var0, "[\"\",{\"text\":\"§eUpdate notifications \",\"insertion\":\"/tellraw @p %s\"},{\"text\":\"" + ge(STUtils.optionb("UpdateNotify")) + "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/supertrails settings nttoggle\"}}]");
      CmdD.sendRaw(var0, "[\"\",{\"text\":\"§eCheck for updates \",\"insertion\":\"/tellraw @p %s\"},{\"text\":\"" + ge(STUtils.optionb("UpdateChecker")) + "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/supertrails settings uptoggle\"}}]");
      var0.sendMessage("§7================");
   }

   public static String ge(boolean var0) {
      return var0 ? yes : no;
   }

   private static void a() {
      Bukkit.getScheduler().runTaskLater(SuperTrails.p, new Runnable() {
         public void run() {
            SocketClient.output.equals("fail");
         }
      }, 5L);
   }
}
