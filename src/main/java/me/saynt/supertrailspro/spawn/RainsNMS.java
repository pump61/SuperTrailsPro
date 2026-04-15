package me.saynt.supertrailspro.spawn;

import java.util.ArrayList;
import java.util.List;
import me.saynt.supertrailspro.STUtils;
import me.saynt.supertrailspro.SuperTrails;
import me.saynt.supertrailspro.data.PlayerData;
import me.saynt.supertrailspro.wings.ColoredParticle;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class RainsNMS implements RainsInterface {

   List<Item> rain = new ArrayList<>();
   List<Item> toclear = new ArrayList<>();

   public void spawn(PlayerData data) {
      try {
         ItemStack stack = data.getRainItem();
         if (stack == null || stack.getType() == Material.AIR) return;

         Location loc = data.getLocation();
         if (loc == null || loc.getWorld() == null) {
            data.reset();
            return;
         }

         stack = stack.clone();
         Location spawnLoc = loc.clone().add(0, 3, 0);

         Item item = (Item) loc.getWorld().spawnEntity(spawnLoc, EntityType.DROPPED_ITEM);
         item.setItemStack(stack);
         item.setPickupDelay(Integer.MAX_VALUE);
         item.setGravity(true);

         double vx = STUtils.r(-2, 2) * 0.1D;
         double vz = STUtils.r(-2, 2) * 0.1D;
         item.setVelocity(new Vector(vx, -0.5D, vz));

         Location cloudLoc = loc.clone().add(0, 4, 0);
         for (int i = 1; i < 11; i++) {
            int dx = SuperTrails.r(-5, 5);
            int dy = SuperTrails.r(-5, 5);
            int dz = SuperTrails.r(-5, 5);
            try {
               ColoredParticle.spawn(
                  cloudLoc.clone().add(dx * 0.1D, dy * 0.1D, dz * 0.1D),
                  data.getRainCloudColor()
               );
            } catch (Exception e) {
               data.clear();
               return;
            }
         }

         rain.add(item);

      } catch (Exception e) {
         e.printStackTrace();
         data.clear();
      }
   }

   public void tick() {
      for (Item item : rain) {
         if (item.getTicksLived() > 4) {
            item.remove();
            toclear.add(item);
         }
      }
      rain.removeAll(toclear);
      toclear.clear();
   }

   public void removeAll() {
      for (Item item : rain) {
         item.remove();
      }
      rain.clear();
   }

   public void destroyItems(Player player) {
      for (Item item : rain) {
         if (item.getLocation().getWorld() == player.getWorld()
               && item.getLocation().distance(player.getLocation()) <= 40) {
            item.remove();
         }
      }
      rain.removeIf(Item::isDead);
   }
}