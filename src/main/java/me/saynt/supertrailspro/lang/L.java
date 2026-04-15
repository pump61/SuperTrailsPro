package me.saynt.supertrailspro.lang;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import me.saynt.supertrailspro.Chat;
import me.saynt.supertrailspro.data.DataManager;
import me.saynt.supertrailspro.data.PlayerData;
import me.saynt.supertrailspro.inventory.InvControl;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class L {
   public static String getString(UUID var0, String var1) {
      PlayerData var2 = DataManager.loadOrCreate(var0);
      LangCache var3 = LanguageManager.getLanguageFile(var2.getLang());
      if (var3 != null && var3.getString(var1) != null) {
         return var3.getString(var1).replaceAll("&", "§");
      } else if (LanguageManager.getLanguageFile(0) != null && LanguageManager.getLanguageFile(0).getString(var1) != null) {
         return LanguageManager.getLanguageFile(0).getString(var1).replaceAll("&", "§");
      } else {
         FileConfiguration var4 = LanguageManager.system;
         return var4 != null && var4.getString(var1) != null ? var4.getString(var1).replaceAll("&", "§") : null;
      }
   }

   public static void msg(Player var0, String var1) {
      if (var0 != null && var1 != null) {
         var0.sendMessage(get(var0, var1));
      }
   }

   public static void msg(Player var0, Language var1) {
      msg(var0, var1.getString());
   }

   public static String get(Player var0, Language var1) {
      return get(var0, var1.getString());
   }

   public static String get(UUID var0, String var1) {
      String var2 = getString(var0, var1);
      return var2 != null ? var2 : "§c" + var1;
   }

   public static List<String> getLore(Player var0, String var1) {
      return getLore(var0.getUniqueId(), var1);
   }

   public static List<String> getLore(Player var0, String var1, String var2) {
      List var3 = getLore(var0.getUniqueId(), var1);
      return InvControl.replacePerm(var0, var2, var3);
   }

   public static List<String> getLore(UUID var0, String var1) {
      PlayerData var2 = DataManager.loadOrCreate(var0);
      LangCache var3 = LanguageManager.getLanguageFile(var2.getLang());
      if (var3 != null) {
         List var4 = var3.getStringList(var1);
         if (var4 != null && var4.size() != 0) {
            return listReplacer(var3.getStringList(var1), "&", "§");
         }
      }

      if (LanguageManager.getDefault() != null && LanguageManager.getDefault().getStringList(var1) != null && LanguageManager.getDefault().getStringList(var1).size() != 0) {
         return listReplacer(LanguageManager.getDefault().getStringList(var1), "&", "§");
      } else {
         FileConfiguration var6 = LanguageManager.system;
         if (var6 != null && var6.getStringList(var1) != null && var6.getStringList(var1).size() != 0) {
            return listReplacer(var6.getStringList(var1), "&", "§");
         } else {
            ArrayList var5 = new ArrayList();
            return var5;
         }
      }
   }

   public static List<String> listReplacer(List<String> var0, String var1, String var2) {
      ArrayList var3 = new ArrayList();
      Iterator var5 = var0.iterator();

      while(var5.hasNext()) {
         String var4 = (String)var5.next();
         var3.add(var4.replaceAll(var1, var2));
      }

      return var3;
   }

   public static String readParticleTrailName(Player var0, String var1) {
      return get(var0, "ParticleMenu." + var1);
   }

   public static String get(Player var0, String var1) {
      return get(var0.getUniqueId(), var1);
   }

   public static String convertColors(String var0) {
      Pattern var1 = Pattern.compile(Pattern.quote("[color=") + "(.*?)" + Pattern.quote("]"));
      Matcher var2 = var1.matcher(var0);

      String var3;
      String var4;
      for(var3 = var0; var2.find(); var3 = var3.replaceAll("\\[color=" + var4 + "\\]", Chat.getColor(var4).toString())) {
         var4 = var2.group(1);
      }

      var1 = Pattern.compile(Pattern.quote("{#") + "(.*?)" + Pattern.quote("}"));

      for(var2 = var1.matcher(var3); var2.find(); var3 = var3.replaceAll("\\{#" + var4 + "\\}", Chat.getColor("#" + var4).toString())) {
         var4 = var2.group(1);
      }

      return var3;
   }

   public static List<String> convertColors(List<String> var0) {
      ArrayList var1 = new ArrayList();
      if (var0 == null) {
         return var1;
      } else {
         Iterator var3 = var0.iterator();

         while(var3.hasNext()) {
            String var2 = (String)var3.next();
            var1.add(convertColors(var2));
         }

         return var1;
      }
   }
}
