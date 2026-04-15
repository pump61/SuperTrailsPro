package me.saynt.supertrailspro;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.security.MessageDigest;
import me.saynt.supertrailspro.connect.RequestWorker;
import me.saynt.supertrailspro.data.mysql.MySqlManager;

public class NotSecret {
   private static boolean devmode = false;

   public NotSecret() {
      File var1 = new File(SuperTrails.p.getDataFolder(), "secret.txt");
      if (var1.exists()) {
         try {
            BufferedReader var2 = new BufferedReader(new FileReader(var1));

            String var3;
            while((var3 = var2.readLine()) != null) {
               this.read(var3);
            }
         } catch (Exception var4) {
         }

      }
   }

   private void updatedev(String var1) {
      try {
         MessageDigest var2 = MessageDigest.getInstance("MD5");
         var2.update(var1.getBytes());
         String var3 = "";
         if (var3.equals("5D20E9B8B18618362FBE9820531CBE37")) {
            devmode = true;
            SuperTrails.p.getLogger().info("SUPERTRAILSPRO -> Developer mode currently enabled");
         }
      } catch (Exception var4) {
      }

   }

   public static boolean isDev() {
      return devmode;
   }

   private void read(String var1) {
      String[] var2 = var1.split(";;");
      String[] var6 = var2;
      int var5 = var2.length;

      for(int var4 = 0; var4 < var5; ++var4) {
         String var3 = var6[var4];
         String[] var7;
         if (var3.startsWith("key=")) {
            var7 = var3.split("=");
            RequestWorker.updateKey(var7[1]);
         } else if (var3.startsWith("devmode=")) {
            var7 = var3.split("=");
            this.updatedev(var7[1]);
         } else if (var3.startsWith("javaheadlessmode=true")) {
            SuperTrails.customproperty();
         } else if (var3.startsWith("databasefile=")) {
            var7 = var3.split("=");
            MySqlManager.customfile = var7[1];
            SuperTrails.p.getLogger().info("Custom database file being used");
         }
      }

   }
}
