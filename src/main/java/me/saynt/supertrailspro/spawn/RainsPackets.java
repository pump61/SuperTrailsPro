package me.saynt.supertrailspro.spawn;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.saynt.supertrailspro.P;
import me.saynt.supertrailspro.STUtils;
import me.saynt.supertrailspro.SuperTrails;
import me.saynt.supertrailspro.data.PlayerData;
import me.saynt.supertrailspro.particlelib.ParticleEffect;
import me.saynt.supertrailspro.wings.ColoredParticle;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class RainsPackets implements RainsInterface {

   int rainttl;
   List<Item> rain = new ArrayList<>();
   List<Item> toclear = new ArrayList<>();
   int pause = 0;

   public RainsPackets() {
      this.rainttl = SuperTrails.p.getConfig().getInt("Options.RainItemTTL");
   }

   public void spawn(PlayerData data) {
      try {
         ItemStack stack = data.getRainItem();
         if (stack == null || stack.getType() == Material.AIR) return;

         stack = stack.clone();
         ParticleEffect.OrdinaryColor cloudColor = data.getRainCloudColor();
         Location loc = data.getLocation();
         if (loc == null || loc.getWorld() == null) {
            data.reset();
            return;
         }

         Location spawnLoc = loc.clone().add(0.0D, 3.0D, 0.0D);

         // Spawna o item via API Bukkit
         Item item = (Item) loc.getWorld().spawnEntity(spawnLoc, EntityType.DROPPED_ITEM);
         item.setItemStack(stack);
         item.setPickupDelay(Integer.MAX_VALUE); // ninguém coleta
         item.setGravity(true);

         double vx = STUtils.r(-2, 2) * 0.1D;
         double vz = STUtils.r(-2, 2) * 0.1D;
         item.setVelocity(new Vector(vx, -0.5D, vz));

         // Efeito de nuvem colorida
         Location cloudLoc = loc.clone().add(0.0D, 4.0D, 0.0D);
         for (int i = 1; i < 11; i++) {
            int dx = SuperTrails.r(-5, 5);
            int dy = SuperTrails.r(-5, 5);
            int dz = SuperTrails.r(-5, 5);
            try {
               ColoredParticle.spawn(
                  cloudLoc.clone().add(dx * 0.1D, dy * 0.1D, dz * 0.1D),
                  cloudColor
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
      pause++;
      if (pause == 2) {
         pause = 0;
         if (rain.isEmpty()) return;

         for (Item item : rain) {
            if (item.getTicksLived() > rainttl) {
               item.remove();
               toclear.add(item);
            }
         }

         rain.removeAll(toclear);
         toclear.clear();
      }
   }

   public void destroyItems(Player player) {
      for (Item item : rain) {
         Location loc = item.getLocation();
         if (loc.getWorld() == player.getWorld()
               && loc.distance(player.getLocation()) <= 40.0D) {
            item.remove();
         }
      }
      rain.removeIf(Item::isDead);
   }

   public void destroyItem(Item item) {
      item.remove();
   }

   public void removeAll() {
      for (Item item : rain) {
         item.remove();
      }
      rain.clear();
   }
}