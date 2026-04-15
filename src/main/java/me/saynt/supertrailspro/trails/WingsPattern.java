package me.saynt.supertrailspro.trails;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;

import java.util.ArrayList;
import javax.imageio.ImageIO;
import me.saynt.supertrailspro.P;
import me.saynt.supertrailspro.PluginMessages;
import me.saynt.supertrailspro.data.DataManager;
import me.saynt.supertrailspro.data.PlayerData;
import me.saynt.supertrailspro.particlelib.ParticleEffect;
import me.saynt.supertrailspro.wings.ColoredParticle;
import me.saynt.supertrailspro.wings.WingsManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class WingsPattern {
   public BufferedImage img;
   public int[][] inline;
   public WingsPostFX[] fx = new WingsPostFX[0];
   String name;
   int id;
   ItemStack is;
   int slot;
   Color c;
   public ParticleEffect.OrdinaryColor color;

   public WingsPattern(int var1, String var2, File var3, ItemStack var4, int var5, String var6) {
      this.name = var2;
      this.id = var1;
      this.process(var3);
      this.parseFX(var6);
      this.is = var4;
      this.slot = var5;
   }

   private void process(File var1) {
      if (var1 == null) {
         throw new NullPointerException("Wings file cannot be null");
      } else {
         try {
            BufferedImage var2 = ImageIO.read(var1);
            this.img = new BufferedImage(14, 14, 2);
            Graphics2D var3 = this.img.createGraphics();
            var3.drawImage(var2, 0, 0, (ImageObserver)null);
         } catch (Exception var4) {
            PluginMessages.Error("Error while pocessing pattern " + var1.getName());
            var4.printStackTrace();
            throw new RuntimeException(var4);
         }
      }
   }

   public void display(TrailWings var1, Location var2, PlayerData var3) {
      if (var2 != null) {
         Location var4 = var2.clone();
         var4.setPitch(0.0F);
         var4.add(var4.getDirection().multiply(-0.3D));
         this.displayWing(var1, var4, var3, (float)(var1.Movement ? WingsManager.getWings() * -1 : -120));
         this.displayWing(var1, var4, var3, (float)(var1.Movement ? WingsManager.getWings() : 120));
      }
   }

   public void display(TrailWings var1, Location var2, Player var3) {
      if (var2 != null) {
         Location var4 = var2.clone();
         var4.setPitch(0.0F);
         var4.add(var4.getDirection().multiply(-0.3D));
         PlayerData var5 = DataManager.getData(var3);
         this.displayWing(var1, var4, var5, (float)(var1.Movement ? WingsManager.getWings() * -1 : -120));
         this.displayWing(var1, var4, var5, (float)(var1.Movement ? WingsManager.getWings() : 120));
      }
   }

   public void displayWing(TrailWings var1, Location var2, PlayerData var3, float var4) {
      for(int var5 = 0; var5 < 14; ++var5) {
         Location var6 = var2.clone().add(0.0D, 0.2D * (double)(14 - var5), 0.0D);
         var6.setYaw(var6.getYaw() - var4);

         for(int var7 = 0; var7 < 14; ++var7) {
            PixelType var8 = this.getType(var1, var7, var5);
            if (var8 != PixelType.None) {
               Location var9 = var6.clone();
               var9.add(var6.getDirection().multiply((double)var7 * 0.2D));
               this.spawn(var1, var9, var3, var7, var5);
            }
         }
      }

   }

   public String getName() {
      return this.name;
   }

   public ItemStack getItem() {
      return this.is;
   }

   public int getSlot() {
      return this.slot;
   }

   public boolean hasPermission(Player var1) {
      return this.name == null ? true : P.has(var1, "trails.pattern." + this.name);
   }

   public void parseFX(String var1) {
      if (var1 != null) {
         ArrayList var2 = new ArrayList();
         String[] var6;
         int var5 = (var6 = var1.split(",")).length;

         for(int var4 = 0; var4 < var5; ++var4) {
            String var3 = var6[var4];
            WingsPostFX var7 = WingsManager.findFX(var3);
            if (var7 != null) {
               var2.add(var7);
            }
         }

         this.fx = (WingsPostFX[])var2.toArray(new WingsPostFX[var2.size()]);
      }
   }

   public void spawn(TrailWings var1, Location var2, PlayerData var3, int var4, int var5) {
      try {
         this.c = null;
         this.color = null;
         this.c = new Color(this.img.getRGB(var4, var5));
         this.color = new ParticleEffect.OrdinaryColor(this.c.getRed(), this.c.getGreen(), this.c.getBlue());
         PixelType var6 = var1.getType(var4, var5);
         if (var6 == PixelType.Flame) {
            ParticleEffect.FLAME.display(0.0F, 0.0F, 0.0F, 0.0F, 0, var2, 25.0D);
            return;
         }

         if (var6 == PixelType.Witch) {
            ParticleEffect.SPELL_WITCH.display(0.0F, 0.0F, 0.0F, 0.0F, 0, var2, 25.0D);
            return;
         }

         if (var6 == PixelType.Drip) {
            ParticleEffect.DRIP_LAVA.display(0.0F, 0.0F, 0.0F, 0.0F, 0, var2, 25.0D);
            return;
         }

         if (var6 == PixelType.Crit) {
            ParticleEffect.CRIT_MAGIC.display(0.0F, 0.0F, 0.0F, 0.0F, 0, var2, 25.0D);
            return;
         }

         if (this.color == null) {
            return;
         }

         WingsPostFX[] var10;
         int var9 = (var10 = this.fx).length;

         WingsPostFX var7;
         int var8;
         for(var8 = 0; var8 < var9; ++var8) {
            var7 = var10[var8];
            var7.getPixel(var1, this, this.color, var4, var5);
         }

         var9 = (var10 = var1.fx).length;

         for(var8 = 0; var8 < var9; ++var8) {
            var7 = var10[var8];
            if (!containsFX(this.fx, var7)) {
               var7.getPixel(var1, this, this.color, var4, var5);
            }
         }

         ColoredParticle.spawn(var2, this.color);
      } catch (Exception var11) {
      }

   }

   private boolean isOut(int var1, int var2) {
      return var1 >= 14 || var2 >= 14 || var1 < 0 || var2 < 0;
   }

   private boolean containsFX(WingsPostFX[] arr, WingsPostFX target) {
      for (WingsPostFX item : arr) {
         if (item == target) return true;
      }
      return false;
   }

   private PixelType getType(TrailWings var1, int var2, int var3) {
      if (this.isOut(var2, var3)) {
         return PixelType.None;
      } else {
         Color var4 = new Color(var1.img.getRGB(var2, var3));
         int var5 = var4.getRed();
         int var6 = var4.getBlue();
         int var7 = var4.getGreen();
         if (var5 == 255 && var6 == 0 && var7 == 0) {
            return PixelType.Red;
         } else if (var5 == 0 && var6 == 255 && var7 == 0) {
            return PixelType.Blue;
         } else if (var5 == 0 && var6 == 0 && var7 == 255) {
            return PixelType.Green;
         } else if (var5 == 255 && var7 == 255 && var6 == 0) {
            return PixelType.Flame;
         } else if (var5 == 255 && var7 == 0 && var6 == 255) {
            return PixelType.Witch;
         } else if (var5 == 255 && var7 == 128 && var6 == 64) {
            return PixelType.Drip;
         } else {
            return var5 == 0 && var7 == 255 && var6 == 255 ? PixelType.Crit : PixelType.None;
         }
      }
   }
}
