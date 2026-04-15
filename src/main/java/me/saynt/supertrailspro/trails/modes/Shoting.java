package me.saynt.supertrailspro.trails.modes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import me.saynt.supertrailspro.SuperTrails;
import me.saynt.supertrailspro.data.DataManager;
import me.saynt.supertrailspro.data.PlayerData;
import me.saynt.supertrailspro.trails.TrailParticle;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Shoting extends Mode implements Listener {
   public static HashMap<Player, Location> l = new HashMap();
   public static HashMap<Player, Integer> l2 = new HashMap();
   public int livetime;
   public double speed;
   List<Player> rm = new ArrayList();

   public Shoting(String var1, ItemStack var2, int var3) {
      super(var1, var2, var3);
      this.updateValues();
   }

   public void tick() {
   }

   public void spawn(PlayerData var1, TrailParticle var2) {
      Player var3 = var1.getPlayer();
      if (var3 != null) {
         try {
            if (!l.containsKey(var3) || !l2.containsKey(var3)) {
               return;
            }

            int var4 = (Integer)l2.get(var3);
            Location var5 = (Location)l.get(var3);
            var2.getEff().display(0.2F, 0.2F, 0.2F, 0.0F, 4, var5.clone().add(var5.clone().getDirection().multiply((double)var4 * this.speed)), 30.0D);
            if (var4 >= this.livetime) {
               l.remove(var3);
               l2.remove(var3);
               return;
            }

            l2.put(var3, var4 + 1);
         } catch (Exception var6) {
            l.remove(var3);
            l2.remove(var3);
         }

      }
   }

   public void shot(Player var1, Location var2) {
      l2.put(var1, 2);
      l.put(var1, var2);
   }

   @EventHandler
   public void onHit(PlayerInteractEvent var1) {
      if (DataManager.getData(var1.getPlayer()).getMode() == 3) {
         if (var1.getAction() == Action.LEFT_CLICK_AIR) {
            this.shot(var1.getPlayer(), var1.getPlayer().getLocation().clone().add(0.0D, 1.0D, 0.0D));
         }

      }
   }

   public void updateValues() {
      this.livetime = SuperTrails.p.getConfig().getInt("Modes.Shooter.LiveTime");
      this.speed = SuperTrails.p.getConfig().getDouble("Modes.Shooter.Speed");
   }
}
