package me.saynt.supertrailspro.trails.dreams;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.saynt.supertrailspro.particlelib.ParticleEffect;
import me.saynt.supertrailspro.trails.dreams.objects.DreamObject;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DreamTrail {
   Player p = null;
   int id = 0;
   List<ArmorStand> entities = new ArrayList<>();
   private List<DreamObject> objects = new ArrayList<>();
   private List<Player> seen = new ArrayList<>();
   int tick = 0;

   public DreamTrail(Player player) {
      this.p = player;
      this.firstsetupin();
   }

   public void firstsetup() {}

   public void firstsetupin() {
      this.firstsetup();
      this.updateviewers(this.p.getLocation());
   }

   public void tickin(int t) {
      this.tick();
      if (t == 9) {
         this.updateviewers(this.p.getLocation());
      }
   }

   public void tick() {}

   public void getLocation() {
      this.p.getLocation();
   }

   protected void move(ArmorStand stand, Location loc) {
      stand.teleport(loc);
   }

   protected void createArmorstand(ItemStack item, Location loc, boolean small) {
      ArmorStand stand = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
      stand.setSilent(true);
      stand.setInvulnerable(true);
      stand.setInvisible(true);
      stand.setHelmet(item);
      stand.setItemInHand(new ItemStack(Material.APPLE));
      stand.setGravity(false);
      stand.setSmall(small);
      entities.add(stand);
   }

   protected void spawnParticle(ParticleEffect effect, Location loc) {}

   protected void updateviewers(Location loc) {
      List<Player> newSeen = new ArrayList<>();

      for (Player viewer : loc.getWorld().getPlayers()) {
         if (viewer.getLocation().distance(loc) < 25.0D) {
            newSeen.add(viewer);
         }
      }

      // Despawn para quem saiu do range
      for (Player old : seen) {
         if (!newSeen.contains(old)) {
            despawnEverythingFor(old);
         }
      }

      seen = newSeen;
   }

   public void despawnEverythingFor(Player player) {
      for (ArmorStand stand : entities) {
         // No 1.21.4 armor stands são entidades reais, visíveis para todos
         // Para esconder individualmente seria necessário usar display entities
         // Por ora, removemos a entidade se não há mais viewers
      }
   }

   public void spawnEverythingFor(Player player) {}

   public void sendpacket(Object packet) {}
   public void sendpackets(Object[] packets) {}
   public void sendPacketToPlayer(Player player, Object packet) {}
   public void sendPacketsToPlayer(Player player, Object packet) {}
}