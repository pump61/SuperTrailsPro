package me.saynt.supertrailspro.community;

import java.io.DataInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import me.saynt.supertrailspro.SuperTrails;
import org.bukkit.entity.Player;

public class CommunityThread extends Thread {
   public static String re = null;
   public static Player pe = null;
   public static String rt = null;

   public void run() {
      if (re != null && pe != null) {
         if (SuperTrails.key == null) {
            pe.sendMessage("§cPlease login with /supertrails login <secret_key>");
            pe.sendMessage("§cDon't have secret key ? SuperTrails Instant Access let you get your key automatically within 5 minutes.");
            pe.sendMessage("§eType /supertrails instant <SpigotName>");
         } else {
            String var1 = re;
            Player var2 = pe;
            Socket var3 = null;

            try {
               var3 = new Socket();
               var3.connect(new InetSocketAddress("auth.thekvq.xyz", 17514), 5000);
               ObjectOutputStream var4 = new ObjectOutputStream(var3.getOutputStream());
               new DataInputStream(var3.getInputStream());
               ObjectInputStream var6 = new ObjectInputStream(var3.getInputStream());
               var4.writeObject(SuperTrails.key + "+" + var1);
               var4.flush();
               Object var7 = null;

               try {
                  while ((var7 = var6.readObject()) != null) {
                     RequestManager.listener(pe, (String) var7);
                  }
               } catch (Exception var11) {}

               var4.close();
               var3.close();
            } catch (Exception var12) {
               var2.sendMessage("§cCommunity Service is down or your connection is blocked by firewall.");
               try {
                  if (var3 != null) var3.close();
               } catch (Exception var10) {}
            }
         }
      }
   }
}