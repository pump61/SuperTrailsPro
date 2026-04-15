package me.saynt.supertrailspro.particlelib;

import org.bukkit.Particle;

public enum NewParticles {
   HEART(ParticleEffect.HEART, "HEART"),
   FIREWORKS_SPARK(ParticleEffect.FIREWORKS_SPARK, "FIREWORK"),
   WATER_SPLASH(ParticleEffect.WATER_SPLASH, "SPLASH"),
   SUSPENDED(ParticleEffect.SUSPENDED, "MYCELIUM"),
   CRIT(ParticleEffect.CRIT, "CRIT"),
   CRIT_MAGIC(ParticleEffect.CRIT_MAGIC, "ENCHANTED_HIT"),
   SMOKE_NORMAL(ParticleEffect.SMOKE_NORMAL, "SMOKE"),
   SMOKE_LARGE(ParticleEffect.SMOKE_LARGE, "LARGE_SMOKE"),
   SPELL(ParticleEffect.SPELL, "AMBIENT_ENTITY_EFFECT"),
   SPELL_INSTANT(ParticleEffect.SPELL_INSTANT, "INSTANT_EFFECT"),
   SPELL_MOB(ParticleEffect.SPELL_MOB, "ENTITY_EFFECT"),
   SPELL_MOB_AMBIENT(ParticleEffect.SPELL_MOB_AMBIENT, "AMBIENT_ENTITY_EFFECT"),
   SPELL_WITCH(ParticleEffect.SPELL_WITCH, "WITCH"),
   DRIP_WATER(ParticleEffect.DRIP_WATER, "DRIPPING_WATER"),
   DRIP_LAVA(ParticleEffect.DRIP_LAVA, "DRIPPING_LAVA"),
   VILLAGER_ANGRY(ParticleEffect.VILLAGER_ANGRY, "ANGRY_VILLAGER"),
   VILLAGER_HAPPY(ParticleEffect.VILLAGER_HAPPY, "HAPPY_VILLAGER"),
   TOWN_AURA(ParticleEffect.TOWN_AURA, "MYCELIUM"),
   NOTE(ParticleEffect.NOTE, "NOTE"),
   PORTAL(ParticleEffect.PORTAL, "PORTAL"),
   ENCHANTMENT_TABLE(ParticleEffect.ENCHANTMENT_TABLE, "ENCHANT"),
   FLAME(ParticleEffect.FLAME, "FLAME"),
   LAVA(ParticleEffect.LAVA, "LAVA"),
   CLOUD(ParticleEffect.CLOUD, "CLOUD"),
   SNOWBALL(ParticleEffect.SNOWBALL, "ITEM_SNOWBALL"),
   SLIME(ParticleEffect.SLIME, "ITEM_SLIME"),
   SNOW_SHOVEL(ParticleEffect.SNOW_SHOVEL, "POOF"),
   BARRIER(ParticleEffect.BARRIER, "BARRIER"),
   DRAGON_BREATH(ParticleEffect.DRAGON_BREATH, "DRAGON_BREATH"),
   END_ROD(ParticleEffect.END_ROD, "END_ROD"),
   DAMAGE(ParticleEffect.DAMAGE, "DAMAGE_INDICATOR"),
   SPIT(ParticleEffect.Spit, "SPIT"),
   TOTEM(ParticleEffect.Totem, "TOTEM_OF_UNDYING"),
   REDSTONE(ParticleEffect.REDSTONE, "DUST"),
   SOUL(ParticleEffect.Soul, "SOUL"),
   SOUL_FIRE(ParticleEffect.Soul_Fire, "SOUL_FIRE_FLAME"),
   NAUTILUS(ParticleEffect.NAUTILUS, "NAUTILUS"),
   LIGHT_SOURCE(ParticleEffect.LIGHT_SOURCE, null),
   GOLDEN_SPARKS(ParticleEffect.GOLDEN_SPARKS, "ELECTRIC_SPARK"),
   GLOW_SQUID(ParticleEffect.GLOW_SQUID, "GLOW_SQUID"),
   PINK(ParticleEffect.PINK, "CHERRY_LEAVES");

   public ParticleEffect ef;
   public String bukkitName;

   private NewParticles(ParticleEffect ef, String bukkitName) {
      this.ef = ef;
      this.bukkitName = bukkitName;
   }

   /**
    * Retorna o Particle do Bukkit correspondente ao ParticleEffect.
    * Usado pelo Lib17NMS no 1.17+.
    */
   private static final java.util.Map<String, String> FALLBACKS = new java.util.HashMap<>();
   static {
      FALLBACKS.put("GLOW_SQUID", "GLOW");
      // Paper 1.21.4+ removeu AMBIENT_ENTITY_EFFECT e INSTANT_EFFECT
      FALLBACKS.put("AMBIENT_ENTITY_EFFECT", "ENTITY_EFFECT");
      // WITCH usa Void.class (sem dado extra), mais simples e sempre presente
      FALLBACKS.put("INSTANT_EFFECT", "WITCH");
      // DRAGON_BREATH pode ter sido renomeado em Paper 1.21+
      FALLBACKS.put("DRAGON_BREATH", "PORTAL");
   }

   public static Particle getBukkitParticle(ParticleEffect effect) {
      try {
         NewParticles np = valueOf(effect.name().toUpperCase());
         if (np.bukkitName == null) return null;
         try {
            return Particle.valueOf(np.bukkitName);
         } catch (IllegalArgumentException e) {
            String fallback = FALLBACKS.get(np.bukkitName);
            return fallback != null ? Particle.valueOf(fallback) : null;
         }
      } catch (Exception e) {
         return null;
      }
   }

   public static String convertToID(ParticleEffect effect) {
      Particle p = getBukkitParticle(effect);
      return p != null ? p.name() : null;
   }
}