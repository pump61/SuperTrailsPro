package me.saynt.supertrailspro.community2;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import me.saynt.stserver.packets.AuthenticationPacket;
import me.saynt.stserver.packets.Packet;
import me.saynt.stserver.packets.RequestPacket;
import me.saynt.supertrailspro.ServerVersion;
import me.saynt.supertrailspro.SuperTrails;
import me.saynt.supertrailspro.data.threads.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CommunityManager {
   public static CommunityThread community;
   public static CommunityAlive alive;
   public static Timer t;

   public static void load() {
      if (SuperTrails.key != null) {
         create();
         scheduleAuth();
      }

   }

   public static void create() {
      community = new CommunityThread();
      community.start();
   }

   public static void scheduleAuth() {
      community.sendPacket(generateAuthPacket());
   }

   public static void save() {
      (new Timer()).schedule(new TimerTask() {
         public void run() {
            SuperTrails.p.saveConfig();
         }
      }, 5000L);
   }

   public static AuthenticationPacket generateAuthPacket() {
      return new AuthenticationPacket(SuperTrails.key, Bukkit.getPort(), ServerVersion.getCurrent().getVersionID(), UpdateChecker.v);
   }

   public static void success() {
      String var0 = SuperTrails.key;
      SuperTrails.p.getConfig().set("LicenseKey", var0);
      save();
      if (alive == null) {
         alive = new CommunityAlive();
         t = new Timer(true);
         t.scheduleAtFixedRate(alive, 1L, 10000L);
      }
   }

   public static void fail() {
      SuperTrails.p.getConfig().set("LicenseKey", (Object)null);
      save();
      if (alive != null || t != null) {
         if (t != null) {
            t.cancel();
         }

         if (alive != null) {
            alive.cancel();
         }

         t = null;
         alive = null;
      }

   }

   public static void login(Player var0, String var1) {
      sendPacket(var0, generateAuthPacket());
   }

   public static void command(Player var0, String[] var1) {
      ArrayList var2 = new ArrayList();
      var2.add("cmd");

      for(int var3 = 1; var3 < var1.length; ++var3) {
         var2.add(var1[var3]);
      }

      RequestPacket var4 = new RequestPacket(var2);
      sendPacket(var0, var4);
   }

   public static void sendPacket(Packet var0) {
      if (community == null) {
         create();
      }

      community.sendPacket(var0);
   }

   public static void sendPacket(Player var0, Packet var1) {
      if (community == null) {
         create();
      }

      community.sendPacket(var1, var0);
   }
}
