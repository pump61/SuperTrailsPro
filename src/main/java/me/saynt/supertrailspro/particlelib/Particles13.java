package me.saynt.supertrailspro.particlelib;

import java.util.ArrayList;
import java.util.List;
import me.saynt.supertrailspro.STUtils;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Particles13 implements ParticleLib {

   ArrayList<Object> materials = new ArrayList<>();

   public void initialize() {
      createNMSStack("BONE_MEAL");
      createNMSStack("RED_DYE");
      createNMSStack("YELLOW_DYE");
      createNMSStack("GREEN_DYE");
      createNMSStack("BLUE_DYE");
      createNMSStack("PURPLE_DYE");
      createNMSStack("INK_SAC");
   }

   public void createNMSStack(String name) {
      try {
         org.bukkit.Material mat = org.bukkit.Material.getMaterial(name);
         if (mat != null) materials.add(new ItemStack(mat));
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public void createNewNMSItem(String name) {
      createNMSStack(name);
   }

   public void play(ParticleEffect effect, Location loc, float offX, float offY, float offZ, float speed, int count) {
      if (effect == ParticleEffect.REDSTONE) {
         playColoredEffect(
            new ParticleEffect.OrdinaryColor(STUtils.r(0, 255), STUtils.r(0, 255), STUtils.r(0, 255)),
            loc, count
         );
         return;
      }
      if (effect == ParticleEffect.SNOW_SHOVEL) {
         playColoredEffect(new ParticleEffect.OrdinaryColor(245, 245, 245), loc, count);
         return;
      }
      try {
         Particle particle = NewParticles.getBukkitParticle(effect);
         if (particle == null) return;
         World world = loc.getWorld();
         if (world == null) return;
         if (effect == ParticleEffect.NOTE) speed = 1.0F;
         final float fSpeed = speed;
         for (Player p : world.getPlayers()) {
            if (p.getLocation().distance(loc) < 20.0D) {
               p.spawnParticle(particle, loc, count, offX, offY, offZ, fSpeed);
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public void playColoredEffect(ParticleEffect.OrdinaryColor color, Location loc, int count) {
      try {
         Particle.DustOptions dust = new Particle.DustOptions(
            Color.fromRGB(
               (int)(color.getValueX() * 255),
               (int)(color.getValueY() * 255),
               (int)(color.getValueZ() * 255)
            ), 1.0F
         );
         World world = loc.getWorld();
         if (world == null) return;
         for (Player p : world.getPlayers()) {
            if (p.getLocation().distance(loc) < 20.0D) {
               p.spawnParticle(Particle.REDSTONE, loc, count, 0, 0, 0, dust);
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public void playItemCrack(Object item, Location loc, float offX, float offY, float offZ, float speed, int count) {
      try {
         if (!(item instanceof ItemStack)) return;
         World world = loc.getWorld();
         if (world == null) return;
         for (Player p : world.getPlayers()) {
            if (p.getLocation().distance(loc) < 20.0D) {
               p.spawnParticle(Particle.ITEM_CRACK, loc, count, offX, offY, offZ, speed, item);
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public void sendAll(Location loc, Object packet) {
      // não mais necessário com API Bukkit
   }

   public void send(Player player, Object packet) {
      // não mais necessário com API Bukkit
   }

   public List<Object> getNMSItems() {
      return materials;
   }
}