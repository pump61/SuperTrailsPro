package me.saynt.supertrailspro.trails.dreams;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RainbowTrail extends DreamTrail {
   public Location[] locs;

   // Materiais modernos equivalentes às 10 cores de stained glass
   public static final Material[] COLORS = {
      Material.RED_STAINED_GLASS,
      Material.ORANGE_STAINED_GLASS,
      Material.YELLOW_STAINED_GLASS,
      Material.LIME_STAINED_GLASS,
      Material.CYAN_STAINED_GLASS,
      Material.BLUE_STAINED_GLASS,
      Material.PURPLE_STAINED_GLASS,
      Material.MAGENTA_STAINED_GLASS,
      Material.PINK_STAINED_GLASS,
      Material.WHITE_STAINED_GLASS
   };

   public static int size = 10;

   public RainbowTrail(Player player) {
      super(player);
   }

   public void firstsetup() {
      locs = new Location[size];
      for (int i = 0; i < size; i++) {
         Location loc = p.getLocation().clone();
         createArmorstand(new ItemStack(COLORS[i]), loc, true);
         locs[i] = loc;
      }
   }

   public void tick() {
      if (locs == null || locs[0] == null) return;

      Location current = p.getLocation();
      if (Math.abs(locs[0].getX() - current.getX()) < 0.2D
            && Math.abs(locs[0].getZ() - current.getZ()) < 0.2D) return;

      updatelocs();
      locs[0] = current.clone();

      for (int i = 0; i < size; i++) {
         if (i < entities.size()) {
            move(entities.get(i), locs[i]);
         }
      }
   }

   public void updatelocs() {
      for (int i = size - 1; i > 0; i--) {
         locs[i] = locs[i - 1];
      }
   }
}