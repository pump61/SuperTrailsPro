package me.saynt.supertrailspro.data;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Pages {
   public static String getPage(String var0) {
      try {
         URL var1 = new URL("https://" + var0);
         URLConnection var2 = var1.openConnection();
         InputStream var3 = var2.getInputStream();
         BufferedReader var4 = new BufferedReader(new InputStreamReader(var3));
         return var4.readLine();
      } catch (Exception e) {
         return null;
      }
   }
}