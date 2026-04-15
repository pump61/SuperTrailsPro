package me.saynt.supertrailspro.events;

import me.saynt.supertrailspro.PluginMessages;
import me.saynt.supertrailspro.SuperTrails;
import me.saynt.supertrailspro.community.InventoryListener;
import me.saynt.supertrailspro.community2.listener.MenuListener;
import me.saynt.supertrailspro.eventtrails.TimedEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class EventManager {
   public static void RegisterEvents() {
      PluginManager var0 = SuperTrails.p.getServer().getPluginManager();
      Plugin var1 = SuperTrails.p;
      var0.registerEvents(new Join(), var1);
      var0.registerEvents(new Quit(), var1);
      var0.registerEvents(new TimedEvent(), var1);
      var0.registerEvents(new InventoryListener(), var1);
      var0.registerEvents(new MenuListener(), var1);

      try {
         Class.forName("org.bukkit.event.entity.EntityPickupItemEvent");
         var0.registerEvents(new PickupNew(), var1);
         PluginMessages.Debug("Registered new events");
      } catch (ClassNotFoundException var3) {
         var0.registerEvents(new Pickup(), var1);
         PluginMessages.Debug("Registered old events");
      }

   }
}
