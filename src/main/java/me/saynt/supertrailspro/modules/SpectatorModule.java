package me.saynt.supertrailspro.modules;

import java.util.Iterator;
import me.saynt.supertrailspro.STUtils;
import me.saynt.supertrailspro.SuperTrails;
import me.saynt.supertrailspro.data.DataManager;
import me.saynt.supertrailspro.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

public class SpectatorModule implements Listener {
   boolean on = false;

   public void a() {
      if (STUtils.optionb("HideSpectatorTrails")) {
         this.on = true;
         SuperTrails.p.getServer().getPluginManager().registerEvents(this, SuperTrails.p);
         Iterator var2 = Bukkit.getOnlinePlayers().iterator();

         while(var2.hasNext()) {
            Player var1 = (Player)var2.next();
            PlayerData var3 = DataManager.getData(var1);
            var3.setHidden(var1.getGameMode() == GameMode.SPECTATOR, HideReason.SPECTATOR);
         }

      }
   }

   @EventHandler
   public void event(PlayerGameModeChangeEvent var1) {
      if (this.on) {
         PlayerData var2 = DataManager.getData(var1.getPlayer());
         if (var1.getNewGameMode() == GameMode.SPECTATOR) {
            var2.setHidden(true, HideReason.SPECTATOR);
         } else {
            var2.setHidden(false, HideReason.SPECTATOR);
         }

      }
   }

   public void z() {
      this.on = false;
   }
}
