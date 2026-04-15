package me.saynt.supertrailspro.lang;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import me.saynt.supertrailspro.SuperTrails;
import org.bukkit.configuration.file.FileConfiguration;

public class English {
   static FileConfiguration conf;

   public static InputStream getInputFromJar(String var0) {
      if (var0 == null) {
         throw new IllegalArgumentException("The path can not be null");
      }
      try {
         URL var1 = SuperTrails.class.getResource(var0);
         if (var1 == null) return null;
         URLConnection var2 = var1.openConnection();
         var2.setUseCaches(false);
         return var2.getInputStream();
      } catch (Exception e) {
         e.printStackTrace();
         return null;
      }
   }

   public static void copy(InputStream var0, File var1) {
      try {
         if (var1.exists()) var1.delete();
         File var2 = var1.getParentFile();
         var2.mkdirs();
         if (var2.isDirectory()) {
            if (var1.createNewFile()) {
               byte[] var3 = new byte[1024];
               FileOutputStream var4 = new FileOutputStream(var1);
               int var5;
               while ((var5 = var0.read(var3)) > 0) {
                  var4.write(var3, 0, var5);
               }
               var4.flush();
               var4.close();
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   private static final String[] BUNDLED_LANGUAGES = {
      "English.yml",
      "Portuguese.yml",
      "Spanish.yml",
      "German.yml",
      "Polish.yml",
      "Italian.yml",
      "French.yml"
   };

   public static void cloneLanguageFile() {
      File var1 = new File(SuperTrails.p.getDataFolder(), "/languages/Defaults.file");
      try {
         copy(getInputFromJar("English.yml"), var1);
      } catch (Exception e) {
         e.printStackTrace();
      }
      for (String fileName : BUNDLED_LANGUAGES) {
         try {
            File dest = new File(SuperTrails.p.getDataFolder(), "/languages/" + fileName);
            if (!dest.exists()) {
               InputStream in = getInputFromJar(fileName);
               if (in != null) copy(in, dest);
            }
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
   }
}