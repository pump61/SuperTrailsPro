package me.saynt.supertrailspro.modules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.saynt.supertrailspro.STUtils;
import me.saynt.supertrailspro.SuperTrails;
import me.saynt.supertrailspro.data.DataManager;
import me.saynt.supertrailspro.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class BannedWorlds extends Module implements Listener {
   boolean on = false;
   List<String> banned = new ArrayList();
   boolean event = false;

   public void a() {
      if (STUtils.optionb("BannedWorlds.Enable")) {
         if (!this.event) {
            Bukkit.getPluginManager().registerEvents(this, SuperTrails.p);
         }

         this.banned = new ArrayList();
         Iterator var2 = SuperTrails.p.getConfig().getStringList("Options.BannedWorlds.Worlds").iterator();

         while(var2.hasNext()) {
            String var1 = (String)var2.next();
            this.banned.add(var1.toLowerCase());
         }

         this.checkPlayers();
         this.on = true;
         this.event = true;
      }
   }

   public void checkPlayers() {
      Iterator var2 = Bukkit.getOnlinePlayers().iterator();

      while(var2.hasNext()) {
         Player var1 = (Player)var2.next();
         PlayerData var3 = DataManager.getData(var1);
         if (this.banned.contains(var1.getWorld().getName().toLowerCase())) {
            var3.setHidden(true, HideReason.BANNED_WORLD);
         } else {
            var3.setHidden(false, HideReason.BANNED_WORLD);
         }
      }

   }

   @EventHandler
   public void onWorldChange(PlayerChangedWorldEvent var1) {
      if (this.on) {
         Player var2 = var1.getPlayer();
         PlayerData var3 = DataManager.getData(var2);
         if (this.banned.contains(var2.getWorld().getName().toLowerCase())) {
            var3.setHidden(true, HideReason.BANNED_WORLD);
         } else {
            var3.setHidden(false, HideReason.BANNED_WORLD);
         }

      }
   }

   public void z() {
      this.on = false;
      this.banned.clear();
      this.a();
   }
}
