package me.saynt.supertrailspro.lang;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import me.saynt.supertrailspro.PluginMessages;
import me.saynt.supertrailspro.ServerVersion;
import me.saynt.supertrailspro.ServerVersionsEnum;
import me.saynt.supertrailspro.SuperTrails;
import me.saynt.supertrailspro.eventtrails.EventMenu;
import me.saynt.supertrailspro.inventory.InvControl;
import me.saynt.supertrailspro.inventory.RainInventory;
import me.saynt.supertrailspro.wings.WingsMenu;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class LanguageManager {
   public static File folderpath = null;
   public static HashMap<Integer, FileConfiguration> Langs = new HashMap();
   public static HashMap<Integer, String> LangsName = new HashMap();
   public static HashMap<Integer, LangCache> cache = new HashMap();
   public static FileConfiguration def;
   public static FileConfiguration system;
   public static File defaultfile;

   public static void LoadLangs() {
      folderpath = new File(SuperTrails.p.getDataFolder(), "/languages");
      folderpath.mkdir();
      English.cloneLanguageFile();
      File var0 = new File(folderpath, "Defaults.file");
      system = YamlConfiguration.loadConfiguration(var0);
      if (folderpath.listFiles() != null) {
         File[] var4;
         int var3 = (var4 = folderpath.listFiles()).length;

         for(int var2 = 0; var2 < var3; ++var2) {
            File var1 = var4[var2];
            if (var1.getName().contains(".yml")) {
               try {
                  YamlConfiguration var5 = new YamlConfiguration();
                  var5.load(new InputStreamReader(new FileInputStream(var1), Charset.forName("UTF-8")));
                  if (var5.contains("LanguageID") && var5.contains("LanguageName")) {
                     RegisterLanguage(var5.getInt("LanguageID"), var5.getString("LanguageName"), var1, var5);
                  } else {
                     PluginMessages.Error("Unable to load language info from " + var1.getName());
                  }
               } catch (Exception var6) {
                  PluginMessages.Error("Failed to read file " + var1.getName());
                  var6.printStackTrace();
               }
            }
         }

         getNames();
      }
   }

   public static void RegisterLanguage(int var0, String var1, File var2, FileConfiguration var3) {
      if (Langs.containsKey(var0)) {
         PluginMessages.Error("Language id must be unique (" + var2.getName() + ")");
      } else {
         Langs.put(var0, var3);
         LangsName.put(var0, var1);
         cache.put(var0, new LangCache(var0));
         if (var0 == 0) {
            defaultfile = var2;
         }

      }
   }

   public static void RegisterDefaultLanguage(String var0, File var1) {
   }

   public static LangCache getLanguageFile(int var0) {
      return Langs.containsKey(var0) ? (LangCache)cache.get(var0) : null;
   }

   public static LangCache getDefault() {
      return getLanguageFile(0);
   }

   public static boolean isExists(int var0) {
      return Langs.containsKey(var0);
   }

   public static void getNames() {
      try {
         Iterator var1 = Langs.entrySet().iterator();

         while(var1.hasNext()) {
            Entry var0 = (Entry)var1.next();
            FileConfiguration var2 = (FileConfiguration)var0.getValue();
            String var3 = ServerVersion.higherThanOrEqual(ServerVersionsEnum.S113) ? "" : "§r";
            if (var2.getString("MenuNames.Rain1") != null) {
               RainInventory.names.add(var2.getString("MenuNames.Rain1").replaceAll("&", "§") + var3);
            }

            if (var2.getString("MenuNames.Rain2") != null) {
               RainInventory.names2.add(var2.getString("MenuNames.Rain2").replaceAll("&", "§") + var3);
            }

            if (var2.getString("MenuNames.Wings1") != null) {
               WingsMenu.name1.add(var2.getString("MenuNames.Wings1").replaceAll("&", "§") + var3);
            }

            if (var2.getString("MenuNames.Wings2") != null) {
               WingsMenu.name2.add(var2.getString("MenuNames.Wings2").replaceAll("&", "§") + var3);
            }

            if (var2.getString("MenuNames.Wings3") != null) {
               WingsMenu.color3.add(var2.getString("MenuNames.Wings3").replaceAll("&", "§") + var3);
            }

            if (var2.getString("MenuNames.Wings4") != null) {
               WingsMenu.color4.add(var2.getString("MenuNames.Wings4").replaceAll("&", "§") + var3);
            }

            if (var2.getString("MenuNames.Wings5") != null) {
               WingsMenu.color5.add(var2.getString("MenuNames.Wings5").replaceAll("&", "§") + var3);
            }

            if (var2.getString("MenuNames.WingsPattern") != null) {
               WingsMenu.pattern.add(var2.getString("MenuNames.WingsPattern").replaceAll("&", "§") + var3);
            }

            if (!ServerVersion.higherThanOrEqual(ServerVersionsEnum.S113)) {
               if (var2.getString("MenuNames.Event") != null) {
                  EventMenu.names1.add(var2.getString("MenuNames.Event").replaceAll("&", "§") + "§r");
               }

               if (var2.getString("MenuNames.EventSecond") != null) {
                  EventMenu.names3.add(var2.getString("MenuNames.EventSecond").replaceAll("&", "§") + "§5");
               }

               if (var2.getString("MenuNames.EventOpen") != null) {
                  EventMenu.names2.add(var2.getString("MenuNames.EventOpen").replaceAll("&", "§") + "§r");
               }
            } else {
               if (var2.getString("MenuNames.Event") != null) {
                  EventMenu.names1.add(var2.getString("MenuNames.Event").replaceAll("&", "§"));
               }

               if (var2.getString("MenuNames.EventSecond") != null) {
                  EventMenu.names3.add(var2.getString("MenuNames.EventSecond").replaceAll("&", "§") + " (2)");
               }

               if (var2.getString("MenuNames.EventOpen") != null) {
                  EventMenu.names2.add(var2.getString("MenuNames.EventOpen").replaceAll("&", "§"));
               }
            }
         }
      } catch (Exception var4) {
         PluginMessages.Error("File error");
      }

   }

   public static void copyfile(InputStream var0, File var1) {
      InputStream var2 = null;
      FileOutputStream var3 = null;

      try {
         var2 = var0;
         var3 = new FileOutputStream(var1);
         byte[] var4 = new byte[1024];

         int var5;
         while((var5 = var2.read(var4)) > 0) {
            var3.write(var4, 0, var5);
         }

         var2.close();
         var3.close();
      } catch (Exception var6) {
         var6.printStackTrace();
      }

   }

   public static void unload() {
      Langs.clear();
      LangsName.clear();
      RainInventory.names.clear();
      RainInventory.names2.clear();
      WingsMenu.name1.clear();
      WingsMenu.name2.clear();
      WingsMenu.color3.clear();
      WingsMenu.color4.clear();
      WingsMenu.color5.clear();
      WingsMenu.pattern.clear();
      EventMenu.names1.clear();
      EventMenu.names2.clear();
      EventMenu.names3.clear();
   }

   public static void reload() {
      unload();
      LoadLangs();
      InvControl.createLang();
      Iterator var1 = cache.values().iterator();

      while(var1.hasNext()) {
         LangCache var0 = (LangCache)var1.next();
         if (var0 != null) {
            var0.reset();
         }
      }

   }

   public static int getSize() {
      return Langs.size();
   }
}
