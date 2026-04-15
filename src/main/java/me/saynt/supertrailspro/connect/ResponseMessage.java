package me.saynt.supertrailspro.connect;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ResponseMessage {
   ResponseType type;
   String message;
   long cachetime = 0L;
   long recievedtime;

   public ResponseMessage(ResponseType var1, String var2) {
      this.type = var1;
      this.message = var2;
      this.recievedtime = System.currentTimeMillis();
   }

   public void setCacheTime(long var1) {
      this.cachetime = var1;
   }

   public static ResponseMessage read(String var0) {
      try {
         JSONObject var1 = (JSONObject)(new JSONParser()).parse(var0);
         String var2 = (String)var1.get("Type");
         String var3 = (String)var1.get("Message");
         String var4 = (String)var1.get("Cache");
         ResponseMessage var5 = new ResponseMessage(ResponseType.getByName(var2), var3);
         if (var4 != null) {
            long var6 = Long.parseLong(var4);
            var5.setCacheTime(var6);
         }

         return var5;
      } catch (Exception var8) {
         var8.printStackTrace();
         return new ResponseMessage(ResponseType.Error, "Unable to read data");
      }
   }
}
