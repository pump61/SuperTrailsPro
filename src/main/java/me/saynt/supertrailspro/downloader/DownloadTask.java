package me.saynt.supertrailspro.downloader;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Iterator;
import me.saynt.supertrailspro.Reload;
import me.saynt.supertrailspro.SuperTrails;

public class DownloadTask extends Thread {
   public void run() {
      try {
         broadcaster("§7[SuperTrails] §eDownload started!...");
         int var1 = 1;

         for(Iterator var3 = Dl.links.iterator(); var3.hasNext(); ++var1) {
            String var2 = (String)var3.next();
            broadcaster("§7[SuperTrails] §eDownloading (" + var1 + "/" + Dl.links.size() + ")");
            String var4 = var2.split("=")[0];
            String var5 = var2.split("=")[1];
            URL var6 = new URL(var5);
            ReadableByteChannel var7 = Channels.newChannel(var6.openStream());
            FileOutputStream var8 = new FileOutputStream(new File(SuperTrails.p.getDataFolder(), "/languages/" + var4 + ".yml"));
            var8.getChannel().transferFrom(var7, 0L, Long.MAX_VALUE);
         }

         broadcaster("§7[SuperTrails] §aDownload finished!");
         broadcaster("§7[SuperTrails] §eInitializing...");
         Reload.a();
         broadcaster("§7[SuperTrails] §aFinished!");
         Dl.toDL = 0;
         Dl.links.clear();
      } catch (Exception var9) {
         broadcaster("§7[SuperTrails] §cDownload failed. Try again later");
      }

   }

   public static void broadcaster(String var0) {
      Dl.pl.sendMessage(var0);
   }
}
