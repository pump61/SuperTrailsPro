package me.saynt.supertrailspro.particlelib;

import java.util.ArrayList;
import java.util.List;
import me.saynt.supertrailspro.STUtils;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Lib17NMS implements ParticleLib {

   // Resolvidos em runtime para suportar renomeações entre versões do MC
   private static final Particle DUST_PARTICLE  = resolveParticle("DUST", "REDSTONE");
   private static final Particle ITEM_PARTICLE  = resolveParticle("ITEM", "ITEM_CRACK");

   private static Particle resolveParticle(String... names) {
      for (String name : names) {
         try { return Particle.valueOf(name); } catch (IllegalArgumentException ignored) {}
      }
      return null;
   }

   List<Object> materials = new ArrayList<>();

   public void initialize() {
      createNMSStack("BONE_MEAL");
      createNMSStack("RED_DYE");
      createNMSStack("YELLOW_DYE");
      createNMSStack("GREEN_DYE");
      createNMSStack("BLUE_DYE");
      createNMSStack("PURPLE_DYE");
      createNMSStack("INK_SAC");
      createNMSStack("BONE_MEAL");
      createNMSStack("BONE_MEAL");
      createNMSStack("BONE_MEAL");
   }

   public void createNMSStack(String name) {
      try {
         Material mat = Material.getMaterial(name);
         if (mat != null) materials.add(new ItemStack(mat));
      } catch (Exception e) {
         e.printStackTrace();
      }
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
         final Class<?> dataType = particle.getDataType();
         // Para tipos desconhecidos (ex: Particle.Spell no Paper 1.21+), tenta construir via reflexão
         final Object extraData;
         if (dataType == Void.class || dataType == Color.class) {
            extraData = null;
         } else {
            Object tmp = null;
            // Tenta construir com Color (DUST, etc)
            try { tmp = dataType.getConstructor(Color.class).newInstance(Color.WHITE); } catch (Exception ignored) { }
            // Tenta construir com int, int, int (EntityEffect no Paper 1.21.4+)
            if (tmp == null) {
               try { tmp = dataType.getConstructor(int.class, int.class, int.class).newInstance(255, 255, 255); } catch (Exception ignored) { }
            }
            // Tenta primeiro construtor disponível sem argumentos
            if (tmp == null) {
               try { tmp = dataType.getDeclaredConstructors()[0].newInstance(new Object[dataType.getDeclaredConstructors()[0].getParameterCount()]); } catch (Exception ignored) { }
            }
            extraData = tmp;
         }
         for (Player p : world.getPlayers()) {
            if (p.getLocation().distance(loc) < 20.0D) {
               if (dataType == Void.class) {
                  p.spawnParticle(particle, loc, count, offX, offY, offZ, fSpeed);
               } else if (dataType == Color.class) {
                  p.spawnParticle(particle, loc, count, 0, 0, 0, Color.WHITE);
               } else if (extraData != null) {
                  p.spawnParticle(particle, loc, count, 0, 0, 0, extraData);
               } else {
                  // Tipo de dado desconhecido: tenta sem dado extra (fallback)
                  try {
                     p.spawnParticle(particle, loc, count, offX, offY, offZ, fSpeed);
                  } catch (Exception ignored) { }
               }
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
               if (DUST_PARTICLE != null) p.spawnParticle(DUST_PARTICLE, loc, count, 0, 0, 0, dust);
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
               if (ITEM_PARTICLE != null) p.spawnParticle(ITEM_PARTICLE, loc, count, offX, offY, offZ, speed, item);
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