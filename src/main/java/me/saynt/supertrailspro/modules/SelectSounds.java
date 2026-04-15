package me.saynt.supertrailspro.modules;

import me.saynt.supertrailspro.PluginMessages;
import me.saynt.supertrailspro.SuperTrails;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SelectSounds {
   public static Sound particles;
   public static Sound blocks;
   public static Sound rains;
   public static Sound wings;
   public static Sound event;
   public static boolean on = false;

   public static void a() {
      FileConfiguration var0 = SuperTrails.p.getConfig();
      if (var0.getBoolean("Options.SelectSounds.Enable")) {
         try {
            particles = SoundsConverter.get(var0.getString("Options.SelectSounds.Particles")).toSound();
            blocks = SoundsConverter.get(var0.getString("Options.SelectSounds.Blocks")).toSound();
            rains = SoundsConverter.get(var0.getString("Options.SelectSounds.Rains")).toSound();
            wings = SoundsConverter.get(var0.getString("Options.SelectSounds.Wings")).toSound();
            event = SoundsConverter.get(var0.getString("Options.SelectSounds.Event")).toSound();
            on = true;
         } catch (Exception var2) {
            PluginMessages.Error("Failed to load Sound Manager");
         }

      }
   }

   public static void play(SoundType var0, Player var1) {
      if (on && var0 != null && var1 != null) {
         Sound var2 = null;
         if (var0 == SoundType.Particles) {
            var2 = particles;
         }

         if (var0 == SoundType.Blocks) {
            var2 = blocks;
         }

         if (var0 == SoundType.Rains) {
            var2 = rains;
         }

         if (var0 == SoundType.Wings) {
            var2 = wings;
         }

         if (var0 == SoundType.Event) {
            var2 = event;
         }

         if (var2 != null) {
            var1.playSound(var1.getLocation(), var2, 1.0F, 1.0F);
         }

      }
   }

   public static void play(int var0, Player var1) {
      SoundType var2 = null;
      if (var0 > 0 && var0 <= 99) {
         var2 = SoundType.Particles;
      }

      if (var0 >= 100 && var0 <= 199) {
         var2 = SoundType.Blocks;
      }

      if (var0 == 201) {
         var2 = SoundType.Rains;
      }

      if (var0 > 400 && var0 < 499) {
         var2 = SoundType.Event;
      }

      play(var2, var1);
   }

   public static void z() {
      on = false;
      particles = null;
      blocks = null;
      rains = null;
      wings = null;
      event = null;
      a();
   }
}
