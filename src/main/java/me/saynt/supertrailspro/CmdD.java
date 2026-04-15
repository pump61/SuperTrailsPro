package me.saynt.supertrailspro;

import java.io.File;
import java.io.FileWriter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import me.saynt.supertrailspro.data.DataManager;
import me.saynt.supertrailspro.data.EventData;
import me.saynt.supertrailspro.data.PlayerData;
import me.saynt.supertrailspro.data.threads.UpdateChecker;
import me.saynt.supertrailspro.downloader.Dl;
import me.saynt.supertrailspro.eventtrails.EventTrails;
import me.saynt.supertrailspro.eventtrails.TimedEvent;
import me.saynt.supertrailspro.lang.L;
import me.saynt.supertrailspro.lang.LanguageManager;
import me.saynt.supertrailspro.spawn.NewBlock;
import me.saynt.supertrailspro.trails.Task;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CmdD {
   public static String servicename = null;
   public static String l1 = "§8Connecting...";
   public static String l2 = " ";
   public static List<String> strHome = new ArrayList();
   public static String s = "";

   public static void Menu(Player var0, int var1) {
      String var2 = applyColor(L.get(var0, "System.Home_Page"), 0, var1);
      String var3 = applyColor(L.get(var0, "System.Commands_Page"), 1, var1);
      String var4 = applyColor(L.get(var0, "System.Updates_Page"), 2, var1);
      String var5 = applyColor(L.get(var0, "System.Usage_Page"), 3, var1);
      String var6 = applyColor(L.get(var0, "System.Languages_Page"), 4, var1);
      String var7 = applyColorC(L.get(var0, "System.Community_Page"), 6, var1);
      String var8 = applyColor("Tester", 5, var1);
      var0.sendMessage(" ");
      if (UpdateChecker.isUpAvalible()) {
         var4 = var4 + "§c (" + UpdateChecker.not() + ")";
      }

      var0.sendMessage(" ");
      if (Dl.toDL > 0) {
         var6 = var6 + "§c (" + Dl.toDL + ")";
      }

      var0.sendMessage("§7============");
      if (ServerVersion.higherThanOrEqual(ServerVersionsEnum.S116)) {
         var0.sendMessage(Chat.getColor("#f26e0a") + "§lS" + Chat.getColor("#ff9700") + "§luper" + Chat.getColor("#f26e0a") + "§lT" + Chat.getColor("#ff9700") + "§lrails" + Chat.getColor("#f26e0a") + "§lPro §8- " + Chat.getColor("#63BAAB") + "§l" + SuperTrails.p.getDescription().getVersion());
      } else {
         var0.sendMessage("§6§lS§e§luper§6§lT§e§lrails§6§lPro §8- §a§l" + SuperTrails.p.getDescription().getVersion());
      }

      if (var0.isOp()) {
         sendRaw(var0, "[\"\",{\"text\":\"" + var2 + "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/supertrails\"}},{\"text\":\" | \",\"color\":\"dark_gray\"},{\"text\":\"" + var3 + "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/supertrails cmds\"},\"color\":\"none\"},{\"text\":\" | \",\"color\":\"dark_gray\"},{\"text\":\"" + var4 + "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/supertrails updates\"},\"color\":\"none\"},{\"text\":\" | \",\"color\":\"dark_gray\"},{\"text\":\"" + var6 + "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/supertrails lang\"},\"color\":\"none\"}]");
      } else {
         sendRaw(var0, "[\"\",{\"text\":\"" + var2 + "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/supertrails\"}},{\"text\":\" | \",\"color\":\"dark_gray\"},{\"text\":\"" + var3 + "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/supertrails cmds\"},\"color\":\"none\"}]");
      }

      var0.sendMessage("§8>>>");
   }

   public static String applyColor(String var0, int var1, int var2) {
      return var1 == var2 ? "§e" + var0 : "§7" + var0;
   }

   public static String applyColorC(String var0, int var1, int var2) {
      if (SuperTrails.key == null) {
         return "§8" + var0;
      } else {
         return var1 == var2 ? "§e" + var0 : "§7" + var0;
      }
   }

   public static void sendRaw(Player var0, String var1) {
      String var2 = var0.getName();
      Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "tellraw " + var2 + " " + var1);
   }

   public static void sendHome(Player var0) {
      Menu(var0, 0);
      var0.sendMessage("§7 ");
      var0.sendMessage("§7Eula-friendly. Advanced trails plugin.");
      var0.sendMessage("§7Amazing plugin for your survival, creative, lobby servers.");
      var0.sendMessage(" ");
      var0.sendMessage("§7============");
   }

   public static void sendU(Player var0) {
      Menu(var0, 2);
      var0.sendMessage("§7Update checker is disabled in current plugin build");
      var0.sendMessage(" ");
      var0.sendMessage(" ");
      var0.sendMessage(" ");
      var0.sendMessage("§7============");
   }

   public static void sendCmds(Player var0) {
      Menu(var0, 1);
      var0.sendMessage("§6/trails §f- §l" + L.get(var0, "System.Trails_CMD"));
      var0.sendMessage("§6/trailsid §f- §l" + L.get(var0, "System.TrailsID_CMD"));
      if (var0.isOp()) {
         var0.sendMessage("§a/supertrails §f- §l" + L.get(var0, "System.SuperTrails_CMD"));
      } else {
         var0.sendMessage("§7/supertrails §f- §l" + L.get(var0, "System.SuperTrails_Player_CMD"));
      }

      var0.sendMessage("§8>>>");
      var0.sendMessage("§7============");
   }

   public static void sendUsage(Player var0) {
      Menu(var0, 3);
      Task.displayStats(var0);
      var0.sendMessage("§7============");
   }

   public static void sendLang(Player var0) {
      Menu(var0, 4);
      var0.sendMessage("§7" + LanguageManager.getSize() + " languages loaded.");
      var0.sendMessage("§7Default language: " + LanguageManager.getLanguageFile(0).getString("LanguageName"));
      sendRaw(var0, "{\"text\":\"[Download languages]\",\"bold\":true,\"color\":\"gold\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://drive.google.com/file/d/1YuKqa3ljeFlBIDzJHT2vqQfg_0B355xp/view?usp=sharing\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":[{\"text\":\"External link\",\"color\":\"gray\"}]}}");
      var0.sendMessage("§8>>>");
      var0.sendMessage("§7============");
   }

   public static void testers(Player var0) {
      Menu(var0, 5);
      var0.sendMessage("§7Sorry, this menu no longer enabled");
      var0.sendMessage("");
      var0.sendMessage("");
      var0.sendMessage("");
      endLine(var0);
   }

   public static void dl(Player var0) {
      Menu(var0, 6);
      if (Dl.toDL > 0) {
         sendRaw(var0, "[\"\",{\"text\":\"§c§l" + Dl.toDL + " NEW §e§l[Download new languages now]" + "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/supertrails downloads dlnew\"}}]");
         var0.sendMessage("§7Click on message above to download");
      } else {
         var0.sendMessage("§7[Language Pack] §lвњ“");
         var0.sendMessage("§7There is no new languages for you at this moment.");
      }

      var0.sendMessage("");
      var0.sendMessage("§8Test feature");
      endLine(var0);
   }

   public static void endLine(Player var0) {
      var0.sendMessage("§7============");
   }

   public static void showFixes(Player var0) {
      var0.sendMessage("§7Sorry, this menu no longer enabled");
      var0.sendMessage("");
      var0.sendMessage("");
      var0.sendMessage("");
      var0.sendMessage("§8----------");
   }

   public static void sett(Player var0) {
   }

   public static void add(String var0) {
      int var1 = NewBlock.getMaterial(var0).getId();
      s = s + var0 + " = " + var1;
   }

   public static void dev(Player var0) {
      add("TUBE_CORAL");
      add("TUBE_CORAL_BLOCK");
      add("BRAIN_CORAL");
      add("BRAIN_CORAL_BLOCK");
      add("BUBBLE_CORAL");
      add("BUBBLE_CORAL_BLOCK");
      add("FIRE_CORAL");
      add("FIRE_CORAL_BLOCK");
      add("HORN_CORAL_BLOCK");
      add("HORN_CORAL");
      add("KELP");
      add("KELP_PLANT");
      add("DRIED_KELP");
      add("DRIED_KELP_BLOCK");
      FileWriter var1 = null;

      try {
         var1 = new FileWriter(new File(SuperTrails.p.getDataFolder(), "ids.txt"));
      } catch (Exception var5) {
         var5.printStackTrace();
      }

      try {
         var1.write(s);
      } catch (Exception var4) {
         var4.printStackTrace();
      }

      try {
         var1.close();
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }

   public static void event(CommandSender var0, String[] var1) {
      if (var0 instanceof ConsoleCommandSender || ((Player)var0).hasPermission("trails.admin.event")) {
         boolean var2 = var0 instanceof Player;
         if (var1.length == 1) {
            if (var2) {
               Menu((Player)var0, 999);
            }

            var0.sendMessage("/supertrails event giveall !player!");
            var0.sendMessage("/supertrails event clear !player!");
            var0.sendMessage("/supertrails event give !player! !trail_id!");
            var0.sendMessage("/supertrails event addchests !player! !boxanount!");
            var0.sendMessage("/supertrails event timed !seconds! !boxamount!");
         }

         Player var3;
         if (var1.length > 2) {
            if (var1[1].equalsIgnoreCase("giveall")) {
               var3 = Bukkit.getPlayer(var1[2]);
               if (var3 == null) {
                  var0.sendMessage("§cCan't find that player");
                  return;
               }

               Iterator var5 = EventTrails.i.entrySet().iterator();

               while(var5.hasNext()) {
                  Entry var4 = (Entry)var5.next();
                  PlayerData var6 = DataManager.getData(var3);
                  var6.getEventData().add((Integer)var4.getKey());
                  var6.getEventData().save();
                  var0.sendMessage("§aSuccessfully added!");
               }

               var0.sendMessage("§aSuccessfully added!");
            }

            if (var1[1].equalsIgnoreCase("clear")) {
               var3 = Bukkit.getPlayer(var1[2]);
               PlayerData var7 = DataManager.getData(var3);
               var7.getEventData().resetAll();
               var7.getEventData().save();
               var0.sendMessage("§aSuccessfully removed!");
            }
         }

         if (var1.length > 3) {
            int var9;
            EventData var10;
            if (var1[1].equalsIgnoreCase("give")) {
               var3 = Bukkit.getPlayer(var1[2]);
               var9 = Integer.parseInt(var1[3]);
               var10 = DataManager.getData(var3).getEventData();
               var10.add(var9);
               var10.save();
               var0.sendMessage("§aSuccessfully added!");
            }

            if (var1[1].equalsIgnoreCase("addchests")) {
               var3 = Bukkit.getPlayer(var1[2]);
               var9 = Integer.parseInt(var1[3]);
               var10 = DataManager.getData(var3).getEventData();
               var10.addChests(var9);
               var10.save();
               var0.sendMessage("§aSuccessfully added!");
            }

            if (var1[1].equalsIgnoreCase("timed")) {
               int var8 = TimedEvent.read(var1[2]);
               if (var8 == -1) {
                  var0.sendMessage("Wrong time value");
                  return;
               }

               var9 = Integer.parseInt(var1[3]);
               TimedEvent.start(var8, var9);
               var0.sendMessage("§aSuccessfully started!");
            }
         }

      }
   }

   public static void reload(CommandSender var0) {
      LanguageManager.reload();
      SuperTrails.p.reloadConfig();
      var0.sendMessage("§aLanguages and config was reloaded. Note: Some options can not be reloaded/updated while server running.");
   }
}
