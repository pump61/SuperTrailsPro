package me.saynt.supertrailspro.eventtrails;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import me.saynt.supertrailspro.STUtils;
import me.saynt.supertrailspro.data.DataManager;
import me.saynt.supertrailspro.data.EventData;
import me.saynt.supertrailspro.lang.L;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class TimedEvent implements Listener {
   static int timer;
   static int it = 0;
   static boolean on = false;
   static int boxes;
   static List<UUID> lucky = new ArrayList();

   public static void start(int var0, int var1) {
      timer = var0;
      it = 0;
      boxes = var1;
      Iterator var3 = Bukkit.getOnlinePlayers().iterator();

      Player var2;
      while(var3.hasNext()) {
         var2 = (Player)var3.next();
         EventData var4 = DataManager.getData(var2).getEventData();
         lucky.add(var2.getUniqueId());
         var4.addChests(var1);
         var4.save();
      }

      on = true;
      if (STUtils.optionb("BroadcastEventMessages")) {
         var3 = Bukkit.getOnlinePlayers().iterator();

         while(var3.hasNext()) {
            var2 = (Player)var3.next();
            String[] var7;
            int var6 = (var7 = L.get(var2, "Event.Messages.Start").replaceAll("!a!", String.valueOf(var1)).split("/n")).length;

            for(int var5 = 0; var5 < var6; ++var5) {
               String var8 = var7[var5];
               var2.sendMessage(var8);
            }
         }
      }

   }

   public static void stop() {
      on = false;
      timer = 0;
      it = 0;
      boxes = 0;
      EventDataManager.clearTemp();
      lucky.clear();
      if (STUtils.optionb("BroadcastEventMessages")) {
         Iterator var1 = Bukkit.getOnlinePlayers().iterator();

         while(var1.hasNext()) {
            Player var0 = (Player)var1.next();
            String[] var5;
            int var4 = (var5 = L.get(var0, "Event.Messages.End").split("/n")).length;

            for(int var3 = 0; var3 < var4; ++var3) {
               String var2 = var5[var3];
               var0.sendMessage(var2);
            }
         }
      }

   }

   public static Integer read(String var0) {
      try {
         if (var0.startsWith("M")) {
            return Integer.parseInt(var0.replaceAll("M", "")) * 60;
         } else if (var0.startsWith("H")) {
            return Integer.parseInt(var0.replaceAll("H", "")) * 3600;
         } else {
            return var0.startsWith("D") ? Integer.parseInt(var0.replaceAll("D", "")) * 86400 : Integer.parseInt(var0);
         }
      } catch (Exception var2) {
         return -1;
      }
   }

   public static boolean working() {
      return on;
   }

   public static void tick() {
      if (working()) {
         ++it;
         if (it == 10) {
            it = 0;
            --timer;
         }

         if (timer == 0) {
            stop();
         }

      }
   }

   @EventHandler
   public void J(PlayerJoinEvent var1) {
      if (working()) {
         Player var2 = var1.getPlayer();
         if (!lucky.contains(var2.getUniqueId())) {
            lucky.add(var2.getUniqueId());
            EventData var3 = DataManager.getData(var2).getEventData();
            var3.addChests(boxes);
            var3.save();
         }
      }
   }
}
