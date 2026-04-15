package me.saynt.supertrailspro.community2;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import me.saynt.stserver.packets.Packet;
import me.saynt.supertrailspro.community2.listener.PacketListener;
import org.bukkit.entity.Player;

public class CommunityThread extends Thread {
   static Player cause;
   static Packet input;
   static boolean works = true;
   Socket s;
   ObjectOutputStream out;
   ObjectInputStream in;
   int tries = 0;
   boolean debug = false;

   public void run() {
      while(works) {
         try {
            if (input == null) {
               synchronized(this) {
                  this.wait();
               }
            }
         } catch (InterruptedException var6) {
            var6.printStackTrace();
            input = null;
            cause = null;
         }

         if (input != null) {
            Player var1 = cause;
            Packet var2 = input;
            input = null;
            if (!this.isConnected()) {
               this.reconnect();
            }

            try {
               this.out.writeObject(var2);
               Packet var3 = (Packet)this.in.readObject();
               PacketListener var4 = var3.getType().getListener();
               if (var4 != null) {
                  var4.read(var3, var1);
               }
            } catch (SocketException var7) {
               if (this.debug) {
                  var7.printStackTrace();
               }

               if (this.tries < 3) {
                  this.reconnect();
                  ++this.tries;
               } else {
                  this.tries = 0;
                  this.closeconnections();
               }
            } catch (Exception var8) {
               if (this.debug) {
                  var8.printStackTrace();
               }

               this.closeconnections();
            }

            cause = null;
            this.tries = 0;
         }
      }

      CommunityManager.community = null;
   }

   public synchronized void sendPacket(Packet var1) {
      input = var1;
      synchronized(this) {
         this.notify();
      }
   }

   public synchronized void sendPacket(Packet var1, Player var2) {
      input = var1;
      cause = var2;
      synchronized(this) {
         this.notify();
      }
   }

   public void kill() {
      works = false;
      input = null;
      cause = null;
      synchronized(this) {
         this.notify();
      }
   }

   public boolean isConnected() {
      return this.s != null && this.out != null && this.in != null;
   }

   public void reconnect() {
      this.closeconnections();
      this.connect();
   }

   public void connect() {
      try {
         this.s = new Socket("services.supertrails.pro", 15151);
         this.out = new ObjectOutputStream(this.s.getOutputStream());
         this.in = new ObjectInputStream(this.s.getInputStream());
         this.pm("§aConnected");
      } catch (UnknownHostException var3) {
         this.pm("§cUnknown host. Are you online?");
      } catch (ConnectException var4) {
         boolean var2 = check("services.supertrails.pro");
         this.pm("§7Community service currently down or your host provider blocks outgoing connection.");
         this.pm("§7Community Host - " + (var2 ? "§a§lReachable (over HTTP)" : "§c§lUnreachable"));
      } catch (Exception var5) {
      }

   }

   public void pm(String var1) {
      if (cause != null) {
         cause.sendMessage(var1);
      }

   }

   public static boolean check(String var0) {
      try {
         URL var1 = new URL("http://" + var0);
         HttpURLConnection var2 = (HttpURLConnection)var1.openConnection();
         var2.setRequestMethod("GET");
         var2.setConnectTimeout(5000);
         var2.connect();
         return var2.getResponseCode() == 200 || var2.getResponseCode() == 403;
      } catch (Exception var3) {
         return false;
      }
   }

   public void closeconnections() {
      try {
         if (this.out != null) {
            this.out.close();
         }

         if (this.in != null) {
            this.in.close();
         }

         if (this.s != null) {
            this.s.close();
         }

         this.out = null;
         this.in = null;
         this.s = null;
      } catch (Exception var2) {
         if (this.debug) {
            var2.printStackTrace();
         }
      }

   }
}
