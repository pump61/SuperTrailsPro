package me.saynt.supertrailspro.downloader;

import java.util.ArrayList;
import org.bukkit.entity.Player;

public class Dl {
   public static int toDL = 0;
   static ArrayList<String> links = new ArrayList();
   static Player pl = null;

   public static void startDownload(Player var0) {
      if (links.size() != 0) {
         DownloadTask var1 = new DownloadTask();
         pl = var0;
         var1.start();
      }

   }

   public static void check() {
      DownloadCheck.c();
   }
}
