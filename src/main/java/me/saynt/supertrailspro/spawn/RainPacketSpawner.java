package me.saynt.supertrailspro.spawn;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class RainPacketSpawner {
   public static List<Item> items = new ArrayList<>();
   static int i = 0;
   public static Item it = null;
   public static Location locs;

   public static void spawn(Player player) {
      i++;
      if (i == 5) {
         i = 0;
         Location loc = player.getLocation();
         locs = loc;

         Item item = (Item) loc.getWorld().spawnEntity(
            loc.clone().add(0, 4, 0), EntityType.DROPPED_ITEM
         );
         item.setItemStack(new ItemStack(Material.APPLE));
         item.setPickupDelay(Integer.MAX_VALUE);
         item.setVelocity(new Vector(0, -1, 0));
         it = item;
         items.add(item);
      }
   }

   public static void update() {
      if (it != null && !it.isDead()) {
         it.setVelocity(new Vector(0, -1, 0));
      }
   }
}