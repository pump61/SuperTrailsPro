package me.saynt.supertrailspro.connect;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import me.saynt.supertrailspro.ServerVersion;
import me.saynt.supertrailspro.data.threads.SocketClient;
import me.saynt.supertrailspro.data.threads.UpdateChecker;
import org.bukkit.Bukkit;

public class RequestWorker {
   private String ug = "1884241014";
   private ResponseMessage message;
   private static String key = "key";

   public RequestWorker(String var1, String var2) {
      String var3 = "http://localhost:54222/s";

      try {
         URL var4 = new URL(var3);
         HttpURLConnection var5 = (HttpURLConnection)var4.openConnection();
         var5.setReadTimeout(4000);
         var5.setRequestMethod("POST");
         var5.setRequestProperty("client-info", this.createInfo());
         var5.setRequestProperty("client-data", this.createData());
         var5.setRequestProperty("request-value", var2);
         var5.addRequestProperty("Connection", "close");
         String var6 = "test";
         var5.setDoOutput(true);
         DataOutputStream var7 = new DataOutputStream(var5.getOutputStream());
         var7.writeBytes(var6);
         var7.flush();
         var7.close();
         int var8 = var5.getResponseCode();
         BufferedReader var9 = new BufferedReader(new InputStreamReader(var5.getInputStream()));
         StringBuffer var11 = new StringBuffer();

         String var10;
         while((var10 = var9.readLine()) != null) {
            var11.append(var10);
         }

         var9.close();
         String var12 = var11.toString();
         this.message = ResponseMessage.read(var12);
      } catch (Exception var13) {
         var13.printStackTrace();
      }

   }

   public ResponseMessage getMessage() {
      return this.message;
   }

   private String createInfo() {
      return Bukkit.getIp() + "-" + Bukkit.getIp() + "-" + ServerVersion.getCurrent().getVersionID() + "-" + UpdateChecker.v;
   }

   public static void updateKey(String var0) {
      key = var0;
   }

   private String createData() {
      return SocketClient.u + "-" + SocketClient.ug + "-" + this.ug + "-" + "key";
   }
}
