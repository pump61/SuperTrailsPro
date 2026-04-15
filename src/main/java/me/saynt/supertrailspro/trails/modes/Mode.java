package me.saynt.supertrailspro.trails.modes;

import me.saynt.supertrailspro.SuperTrails;
import me.saynt.supertrailspro.data.PlayerData;
import me.saynt.supertrailspro.inventory.RainInventory;
import me.saynt.supertrailspro.trails.TrailParticle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Mode {
   String name;
   ItemStack is;
   int slot;
   public int id;
   int[] blocked = new int[0];

   public Mode(String var1, ItemStack var2, int var3) {
      this.name = var1;
      this.is = var2;
      this.slot = var3;
      boolean var4 = SuperTrails.p.getConfig().getBoolean("Modes." + var1 + ".Disabled");
      if (!var4) {
         Modes.registerMode(this);
      }

      this.id = Modes.listmodes.size() - 1;
   }

   public void tick() {
   }

   public void spawn(PlayerData var1, TrailParticle var2) {
   }

   public boolean hasPermission(Player var1) {
      return var1.hasPermission("strails.mode." + this.name);
   }

   public void updateValues() {
   }

   public String getName() {
      return this.name;
   }

   public boolean isBlocked(String var1) {
      int var2 = Modes.getIdFromCmd(var1);
      return var2 == 0 ? false : this.isBlocked(var2);
   }

   public boolean isBlocked(int var1) {
      return RainInventory.contains(this.blocked, var1);
   }
}
