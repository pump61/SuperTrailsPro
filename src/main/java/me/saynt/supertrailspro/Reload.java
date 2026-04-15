package me.saynt.supertrailspro;

import me.saynt.supertrailspro.inventories.GuiManager;
import me.saynt.supertrailspro.lang.LanguageManager;
import me.saynt.supertrailspro.modules.Modules;
import me.saynt.supertrailspro.trails.modes.Modes;
import me.saynt.supertrailspro.wings.CustomWingsLoader;
import org.bukkit.command.CommandSender;

public class Reload {
   static boolean wings = false;
   static boolean langs = false;
   static boolean modules = false;
   static boolean menus = false;
   static boolean modes = false;

   public static void a(CommandSender var0) {
      long var1 = System.currentTimeMillis();
      SuperTrails.p.reloadConfig();

      try {
         CustomWingsLoader.reload();
         wings = true;
      } catch (Exception var9) {
         var9.printStackTrace();
      }

      try {
         LanguageManager.reload();
         langs = true;
      } catch (Exception var8) {
         var8.printStackTrace();
      }

      try {
         Modules.unload();
         modules = true;
      } catch (Exception var7) {
         var7.printStackTrace();
      }

      try {
         GuiManager.loadGUI();
         menus = true;
      } catch (Exception var6) {
         var6.printStackTrace();
      }

      try {
         Modes.reload();
         modes = true;
      } catch (Exception var5) {
         var5.printStackTrace();
      }

      long var3 = System.currentTimeMillis() - var1;
      var0.sendMessage("§8- SuperTrails reload complete -");
      if (!wings) {
         var0.sendMessage("§7Wings & Patterns System - " + get(wings));
      }

      if (!langs) {
         var0.sendMessage("§7Languages - " + get(langs));
      }

      if (!modules) {
         var0.sendMessage("§7Modules/Options - " + get(modules));
      }

      if (!menus) {
         var0.sendMessage("§7GUI - " + get(menus));
      }

      if (!modes) {
         var0.sendMessage("§7Modes - " + get(modes));
      }

      var0.sendMessage("§7Reload took " + getColor(var3) + "ms");
      var0.sendMessage("§cNote: Some changes may require server reload");
   }

   public static void a() {
      SuperTrails.p.reloadConfig();

      try {
         CustomWingsLoader.reload();
      } catch (Exception var4) {
         var4.printStackTrace();
      }

      try {
         LanguageManager.reload();
      } catch (Exception var3) {
         var3.printStackTrace();
      }

      try {
         Modules.unload();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

      try {
         GuiManager.loadGUI();
      } catch (Exception var1) {
         var1.printStackTrace();
      }

   }

   public static void b() {
      SuperTrails.p.reloadConfig();

      try {
         CustomWingsLoader.reload();
         wings = true;
      } catch (Exception var3) {
         var3.printStackTrace();
      }

      try {
         LanguageManager.reload();
         langs = true;
      } catch (Exception var2) {
         var2.printStackTrace();
      }

      try {
         Modules.unload();
         modules = true;
      } catch (Exception var1) {
         var1.printStackTrace();
      }

   }

   public static String get(boolean var0) {
      return var0 ? "§a§lSUCCESS" : "§c§lFAIL";
   }

   public static String getColor(long var0) {
      if (var0 > 80L) {
         return "§c§l" + var0;
      } else {
         return var0 > 40L ? "§e§l" + var0 : "§a§l" + var0;
      }
   }
}
