package me.saynt.supertrailspro.data.threads;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import me.saynt.supertrailspro.CmdD;
import me.saynt.supertrailspro.PluginMessages;
import me.saynt.supertrailspro.SuperTrails;
import org.bukkit.Bukkit;

public class SocketClient {
   static int reco = 0;
   protected static String output = " ";
   public static String u = "680561";
   public static String ug = "1052133564";

   public static void auth() {
      if (reco > 4) {
         ArrayList var10 = new ArrayList();
         var10.add("[\"\",{\"text\":\"§8Unable connect to SuperTrails service\"}]");
         var10.add("[\"\",{\"text\":\"§8  \"}]");
         var10.add("[\"\",{\"text\":\"§f  \"}]");
         var10.add("[\"\",{\"text\":\"§f  \"}]");
         var10.add("[\"\",{\"text\":\"§8=========\"}]");
         CmdD.strHome = var10;
      } else {
         Socket var0 = null;

         try {
            var0 = new Socket();
            var0.connect(new InetSocketAddress("services.supertrails.pro", 17515), 3000);
            DataOutputStream var1 = new DataOutputStream(var0.getOutputStream());
            DataInputStream var2 = new DataInputStream(var0.getInputStream());
            var1.writeByte(1);
            var1.writeUTF("auth__" + SuperTrails.p.getServer().getIp() + "__" + SuperTrails.p.getServer().getPort() + "__" + Bukkit.getVersion() + "__" + SuperTrails.p.getServer().getOnlineMode() + "__" + UpdateChecker.v + "__" + u);
            var1.flush();
            Thread.currentThread();
            String var3 = var2.readUTF();
            PluginMessages.Debug(var3);
            if (var3.startsWith("fail")) {
               output = "fail";
            }

            String[] var7;
            int var6 = (var7 = var3.split("___")).length;

            for(int var5 = 0; var5 < var6; ++var5) {
               String var4 = var7[var5];
               CmdD.strHome.add(var4);
            }

            var0.close();
         } catch (Exception var9) {
            CmdD.l1 = "§8Failed connect to server. Reconnecting...";
            ++reco;
            auth();
            if (var0 != null && !var0.isClosed()) {
               try {
                  var0.close();
               } catch (Exception var8) {
               }
            }

         }
      }
   }
}
