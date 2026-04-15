package me.saynt.supertrailspro.events;

import me.saynt.supertrailspro.SuperTrails;
import me.saynt.supertrailspro.data.DataManager;
import me.saynt.supertrailspro.data.threads.UpdateChecker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Join implements Listener {
   public static boolean up = false;

   @EventHandler
   public void PlayerJoin(PlayerJoinEvent var1) {
      DataManager.registerPlayer(var1.getPlayer());
      if (up && var1.getPlayer().isOp()) {
         UpdateChecker.sendNotify(var1.getPlayer());
      }

      if (SuperTrails.p.getConfig().getBoolean("Options.ForceDefaultMode")) {
         DataManager.getData(var1.getPlayer()).setMode(SuperTrails.p.getConfig().getInt("Options.DefaultMode"));
      }

   }
}
