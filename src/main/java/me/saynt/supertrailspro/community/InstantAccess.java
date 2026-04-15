package me.saynt.supertrailspro.community;

import java.util.Random;
import me.saynt.supertrailspro.CmdD;
import me.saynt.supertrailspro.SuperTrails;
import me.saynt.supertrailspro.modules.SoundsConverter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class InstantAccess {
   public static boolean stop = true;
   public static String name;
   public static String key;
   public static String player;
   public static int tries = 0;
   public static BukkitRunnable run;

   public static void run() {
      tries = 0;
      stop = false;
      run = new BukkitRunnable() {
         public void run() {
            Player var1 = Bukkit.getPlayer(InstantAccess.player);
            if (InstantAccess.tries > 15) {
               InstantAccess.fail(Bukkit.getPlayer(InstantAccess.player), "!!!§cNo success. Try again or contact plugin developer dirrectly");
            }

            if (!InstantAccess.stop && var1 != null && InstantAccess.key != null && InstantAccess.name != null && InstantAccess.tries <= 15) {
               SuperTrails.key = "instant";
               RequestManager.sendRequest(var1, "check-" + InstantAccess.name + "-" + InstantAccess.key);
               ++InstantAccess.tries;
            } else {
               InstantAccess.stop = true;
               this.cancel();
               InstantAccess.run = null;
            }
         }
      };
      run.runTaskTimer(SuperTrails.p, 1L, 600L);
   }

   public static void setlogin(Player var0, String var1) {
      String[] var2 = var1.split("!!!");
      SuperTrails.key = var2[1];
      SuperTrails.p.getConfig().set("LicenseKey", SuperTrails.key);
      SuperTrails.p.saveConfig();
      if (var0 != null && var0.isOnline()) {
         var0.playSound(var0.getLocation(), SoundsConverter.ORB_PICKUP.toSound(), 1.0F, 1.0F);
      }

      stop = true;
   }

   public static void fail(Player var0, String var1) {
      String[] var2 = var1.split("!!!");
      stop = true;
      if (var0 != null) {
         var0.sendMessage(var2[1]);
      }

   }

   public static void start(Player var0, String var1) {
      if (var1.equalsIgnoreCase("cancel")) {
         stop = true;
         if (run != null) {
            run.cancel();
         }

         run = null;
         var0.sendMessage("§cCanceled!");
      } else if (!stop) {
         var0.sendMessage("§cAlready started");
         var0.sendMessage("§fConversation ID: §aSTKEY " + key);
      } else {
         if (run != null) {
            run.cancel();
         }

         player = var0.getName();
         key = RandomString(5);
         name = var1;
         run();
         var0.sendMessage("§8======");
         CmdD.sendRaw(var0, "[\"\",{\"text\":\"§6§lSTEP 1: §6Start conversation. Click here!\",\"color\":\"gold\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://www.spigotmc.org/conversations/add?to=kvq\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Open URL\"}]}}}]");
         var0.sendMessage("§7§lSTEP 2: §7Enter Conversation Title : §a§lSTKEY " + key);
         var0.sendMessage("§7§lSTEP 3: §7Enter Conversation Message: §aInstant Access");
         var0.sendMessage("§7§lSTEP 4: §7Click \"Start a Conversation\" button");
         var0.sendMessage("§8======");
         var0.sendMessage("§7* Wait until plugin will process your request");
         var0.sendMessage("§7* Process may take few minutes");
         var0.sendMessage("§7* Do not restart server or plugin!");
         CmdD.sendRaw(var0, "[\"\",{\"text\":\"§7* If nothing happened within 5 minutes after you send message, make sure you \",\"color\":\"gray\"},{\"text\":\"enter valid name \",\"color\":\"gray\",\"underlined\":true,\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"§7You entered: " + name + "\"},{\"text\":\"\\n§7Please check spelling and make sure you have plugin purchased on this account\"}]}}},{\"text\":\"§7and try again or contact developer dirrectly (You may send another message in same conversation)\",\"color\":\"gray\",\"underlined\":false}]");
         var0.sendMessage("§c* /supertrails instant cancel §7 - to cancel");
      }
   }

   public static String RandomString(int var0) {
      char[] var1 = "abcdefghijklmnopqrstuvwxyz1234567890".toUpperCase().toCharArray();
      StringBuilder var2 = new StringBuilder();
      Random var3 = new Random();

      for(int var4 = 0; var4 < var0; ++var4) {
         char var5 = var1[var3.nextInt(var1.length)];
         var2.append(var5);
      }

      String var6 = var2.toString();
      return var6;
   }
}
