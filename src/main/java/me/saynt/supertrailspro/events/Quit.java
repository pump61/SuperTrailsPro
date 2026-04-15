package me.saynt.supertrailspro.events;

import me.saynt.supertrailspro.data.DataManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class Quit implements Listener {
   @EventHandler
   public void PlayerQuit(PlayerQuitEvent var1) {
      DataManager.unregisterPlayer(var1.getPlayer());
   }
}
