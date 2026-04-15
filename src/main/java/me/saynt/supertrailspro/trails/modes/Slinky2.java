package me.saynt.supertrailspro.trails.modes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import me.saynt.supertrailspro.data.DataManager;
import me.saynt.supertrailspro.data.PlayerData;
import me.saynt.supertrailspro.inventory.RainInventory;
import me.saynt.supertrailspro.trails.TrailParticle;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Slinky2 extends Mode implements Listener {
   public static HashMap<Player, Location> l = new HashMap();
   public static HashMap<Player, Integer> l2 = new HashMap();
   List<Player> rm = new ArrayList();
   private double[] h = new double[]{1.4D, 1.4D, 1.3D, 1.2D, 1.1D, 1.0D, 0.9D, 0.8D, 0.7D, 0.6D, 0.5D, 0.4D, 0.3D, 0.2D, 0.1D, 0.1D, 0.2D, 0.3D, 0.4D, 0.5D, 0.6D, 0.7D, 0.8D, 0.8D, 0.8D, 0.8D, 0.7D, 0.6D, 0.5D, 0.4D, 0.3D, 0.2D, 0.1D, 0.0D, 0.1D, 0.2D, 0.3D, 0.4D, 0.5D, 0.5D, 0.5D, 0.5D, 0.5D, 0.4D, 0.3D, 0.2D, 0.1D, 0.0D};
   private double[] a = new double[]{1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 5.0D, 6.0D, 6.0D, 6.0D, 7.0D, 7.0D, 7.0D, 7.0D, 8.0D, 8.0D, 8.0D, 9.0D, 9.0D, 10.0D, 10.0D, 11.0D, 12.0D, 13.0D, 14.0D, 15.0D, 16.0D, 17.0D, 18.0D, 19.0D, 19.0D, 20.0D, 20.0D, 21.0D, 21.0D, 21.0D, 22.0D, 23.0D, 24.0D, 25.0D, 26.0D, 27.0D, 28.0D, 29.0D, 30.0D, 31.0D, 32.0D, 32.0D, 33.0D};

   public Slinky2(String var1, ItemStack var2, int var3) {
      super(var1, var2, var3);
   }

   public void tick() {
   }

   public double getHeight(int var1) {
      return this.h[var1];
   }

   public double getAdd(int var1) {
      return this.a[var1] * 0.2D;
   }

   public boolean isThrow(Player var1) {
      return !l.containsKey(var1) || !l2.containsKey(var1);
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
            double var6 = this.getHeight(var4);
            double var8 = this.getAdd(var4);
            var5.setPitch(0.0F);
            Location var10 = var5.clone().add(0.0D, var6, 0.0D).add(var5.getDirection().multiply(var8));
            var2.getEff().display(0.0F, 0.0F, 0.0F, 0.0F, 1, var10, 30.0D);
            var10 = null;
            if (var4 > this.a.length - 1) {
               l.remove(var3);
               l2.remove(var3);
               return;
            }

            l2.put(var3, var4 + 1);
         } catch (Exception var11) {
            l.remove(var3);
            l2.remove(var3);
         }

      }
   }

   public void shot(Player var1, Location var2) {
      l2.put(var1, 0);
      l.put(var1, var2);
   }

   @EventHandler
   public void onHit(PlayerInteractEvent var1) {
      if (DataManager.getData(var1.getPlayer()).getMode() == 4) {
         if (var1.getAction() == Action.LEFT_CLICK_AIR && this.isThrow(var1.getPlayer())) {
            this.shot(var1.getPlayer(), var1.getPlayer().getLocation().clone().add(0.0D, 0.0D, 0.0D));
         }

      }
   }

   public boolean isBlocked(String var1) {
      int var2 = Modes.getIdFromCmd(var1);
      return var2 == 0 ? false : this.isBlocked(var2);
   }

   public boolean isBlocked(int var1) {
      return RainInventory.contains(this.blocked, var1);
   }
}
