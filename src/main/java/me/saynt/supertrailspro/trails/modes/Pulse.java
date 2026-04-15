package me.saynt.supertrailspro.trails.modes;

import me.saynt.supertrailspro.data.PlayerData;
import me.saynt.supertrailspro.trails.TrailParticle;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class Pulse extends Mode {
   public int i = 0;
   int[] blocked = new int[0];

   public Pulse(String var1, ItemStack var2, int var3) {
      super(var1, var2, var3);
   }

   public void tick() {
      if (this.i > 50) {
         this.i = 0;
      }

      ++this.i;
   }

   public void spawn(PlayerData var1, TrailParticle var2) {
      if (this.i == 10 || this.i == 20 || this.i == 30 || this.i == 40 || this.i == 50) {
         Location var3 = var1.getLocation().clone().add(0.0D, 2.2D, 0.0D);

         for(int var4 = -180; var4 <= 180; var4 += 40) {
            Location var5 = var3.clone();
            var5.setPitch(0.0F);
            var5.setYaw((float)var4);
            var5.add(var5.getDirection().multiply((double)(this.i / 10) * 0.2D));
            var2.getEff().display(0.0F, 0.0F, 0.0F, 0.0F, 3, var5, 30.0D);
            var5 = null;
         }

      }
   }
}
