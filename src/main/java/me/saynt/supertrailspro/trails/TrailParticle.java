package me.saynt.supertrailspro.trails;

import me.saynt.supertrailspro.ServerVersionsEnum;
import me.saynt.supertrailspro.SuperTrails;
import me.saynt.supertrailspro.particlelib.ParticleEffect;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

public class TrailParticle extends Trail {
   protected ParticleEffect pef;
   protected ItemStack i;
   protected int slot;

   public TrailParticle(int var1, String var2, ParticleEffect var3, ItemStack var4, ServerVersionsEnum var5) {
      super(var1, var2, var5);
      this.i = var4;
      this.pef = var3;
      this.getConfigValues();
   }

   public TrailParticle(int var1, String var2, ParticleEffect var3, ItemStack var4) {
      super(var1, var2);
      this.i = var4;
      this.pef = var3;
      this.getConfigValues();
   }

   public void getConfigValues() {
      if (this.name != null) {
         FileConfiguration var1 = SuperTrails.p.getConfig();
         this.slot = var1.getInt("ParticleTrails." + this.getName() + ".Slot");
      }
   }

   public boolean isSupported() {
      return this.support;
   }

   public ItemStack getIS() {
      return this.i;
   }

   public ServerVersionsEnum getVersion() {
      return this.version;
   }

   public ParticleEffect getEff() {
      return this.pef;
   }

   public int getSlot() {
      return this.slot;
   }

   public TrailType getType() {
      return TrailType.Particle;
   }

   public String getPermission() {
      return "trails.particle." + this.name;
   }
}
