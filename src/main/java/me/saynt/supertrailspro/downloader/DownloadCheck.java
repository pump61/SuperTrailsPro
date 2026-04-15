package me.saynt.supertrailspro.downloader;

import java.io.File;

import me.saynt.supertrailspro.SuperTrails;
import me.saynt.supertrailspro.data.Pages;

public class DownloadCheck {
   public static void c() {
      try {
         String var0 = Pages.getPage("raw.githubusercontent.com/kvq/SuperTrailsPro/master/downloads");
         String[] var4;
         int var3 = (var4 = var0.split(";")).length;

         for(int var2 = 0; var2 < var3; ++var2) {
            String var1 = var4[var2];
            String var5 = var1.split("=")[0];
            String var6 = var1.split("=")[1];
            if (!isExists(var5)) {
               Dl.links.add(var1);
               ++Dl.toDL;
            }
         }
      } catch (Exception var7) {
      }

   }

   public static boolean isExists(String var0) {
      File[] var4;
      File langDir = new File(SuperTrails.p.getDataFolder(), "/languages/");
      if (!langDir.exists()) return false;
      int var3 = (var4 = langDir.listFiles()).length;
      if (var4 == null) return false;

      for(int var2 = 0; var2 < var3; ++var2) {
         File var1 = var4[var2];
         String var5 = var1.getName().replaceAll(".yml", "");
         if (var5.equalsIgnoreCase(var0)) {
            return true;
         }
      }

      return false;
   }
}
