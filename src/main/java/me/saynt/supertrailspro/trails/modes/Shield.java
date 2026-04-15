package me.saynt.supertrailspro.trails.modes;

import me.saynt.supertrailspro.SuperTrails;
import me.saynt.supertrailspro.data.PlayerData;
import me.saynt.supertrailspro.inventory.RainInventory;
import me.saynt.supertrailspro.trails.TrailParticle;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class Shield extends Mode {
   int i = 0;
   int[] blocked = new int[]{24};
   double y;
   double multiply = 1.4D;

   public Shield(String var1, ItemStack var2, int var3) {
      super(var1, var2, var3);
      this.updateValues();
   }

   public void tick() {
      ++this.i;
      if (this.i >= 179) {
         this.i *= -1;
      }

   }

   public void spawn(PlayerData var1, TrailParticle var2) {
      Location var3 = var1.getLocation().clone().add(0.0D, this.y, 0.0D);
      var3.setPitch(0.0F);
      var3.setYaw((float)(45 + this.i));
      Location var4 = var3.clone().add(var3.getDirection().multiply(this.multiply));
      var3.setYaw((float)(-45 + this.i));
      Location var5 = var3.clone().add(var3.getDirection().multiply(this.multiply));
      var3.setYaw((float)(135 + this.i));
      Location var6 = var3.clone().add(var3.getDirection().multiply(this.multiply));
      var3.setYaw((float)(-135 + this.i));
      Location var7 = var3.clone().add(var3.getDirection().multiply(this.multiply));
      var2.getEff().display(0.0F, 0.0F, 0.0F, 0.0F, 2, var4, 30.0D);
      var2.getEff().display(0.0F, 0.0F, 0.0F, 0.0F, 2, var5, 30.0D);
      var2.getEff().display(0.0F, 0.0F, 0.0F, 0.0F, 2, var6, 30.0D);
      var2.getEff().display(0.0F, 0.0F, 0.0F, 0.0F, 2, var7, 30.0D);
   }

   public boolean isBlocked(String var1) {
      int var2 = Modes.getIdFromCmd(var1);
      return var2 == 0 ? false : this.isBlocked(var2);
   }

   public boolean isBlocked(int var1) {
      return RainInventory.contains(this.blocked, var1);
   }

   public void updateValues() {
      this.y = SuperTrails.p.getConfig().getDouble("Modes.Magician.Y");
   }
}
