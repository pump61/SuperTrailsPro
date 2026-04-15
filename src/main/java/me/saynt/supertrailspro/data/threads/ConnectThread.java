package me.saynt.supertrailspro.data.threads;

import me.saynt.supertrailspro.SuperTrails;
import me.saynt.supertrailspro.downloader.Dl;

public class ConnectThread extends Thread {
   public void run() {
      Dl.check();

      try {
         sleep(3000L);
      } catch (InterruptedException var6) {
         var6.printStackTrace();
      }

      try {
         SuperTrails.disableOld();
      } catch (Exception var5) {
      }

      try {
         SocketClient.auth();
      } catch (Exception var4) {
      }

      try {
         UpdateChecker.checkupdates();
      } catch (Exception var3) {
      }

      try {
         sleep(3000L);
      } catch (InterruptedException var2) {
         var2.printStackTrace();
      }

      if (UpdateChecker.isUpAvalible()) {
         UpdateChecker.sendToOps();
      }

   }
}
