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
import me.saynt.supertrailspro.STUtils;
import me.saynt.supertrailspro.data.DataManager;
import me.saynt.supertrailspro.data.PlayerData;
import me.saynt.supertrailspro.particlelib.ParticleEffect;
import me.saynt.supertrailspro.wings.ColoredParticle;
import me.saynt.supertrailspro.wings.WingsManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TrailWings extends Trail {
   public BufferedImage img;
   public int[][] inline;
   public WingsPostFX[] fx = new WingsPostFX[0];
   boolean Movement = false;
   public int slot;
   public ItemStack is;
   boolean hasColor1 = false;
   boolean hasColor2 = false;
   boolean hasColor3 = false;
   public ParticleEffect.OrdinaryColor color = null;

   public TrailWings(int var1, String var2, File var3, String var4, int var5, ItemStack var6, boolean var7) {
      super(var1, var2);
      this.process(var3);
      this.parseFX(var4);
      this.Movement = var7;
      this.slot = var5;
      this.is = var6;
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
            this.checkColors();
         } catch (Exception var4) {
            PluginMessages.Error("Error while pocessing wings " + var1.getName());
            var4.printStackTrace();
            throw new RuntimeException(var4); // RuntimeException não precisa ser declarada
         }

         this.findinline();
      }
   }

   public void checkColors() {
      for(int var1 = 0; var1 < this.img.getHeight(); ++var1) {
         for(int var2 = 0; var2 < this.img.getWidth(); ++var2) {
            PixelType var3 = this.getType(var2, var1);
            if (var3 == PixelType.Red) {
               this.hasColor1 = true;
            } else if (var3 == PixelType.Green) {
               this.hasColor2 = true;
            } else if (var3 == PixelType.Blue) {
               this.hasColor3 = true;
            }
         }
      }

   }

   public ItemStack getItem() {
      return this.is;
   }

   public void parseFX(String var1) {
      if (var1 != null) {
         var1 = var1.replaceAll(" ", "");
         ArrayList var2 = new ArrayList();
         String[] var6;
         int var5 = (var6 = var1.split(",")).length;

         for(int var4 = 0; var4 < var5; ++var4) {
            String var3 = var6[var4];
            WingsPostFX var7 = WingsManager.findFX(var3);
            PluginMessages.Debug(var3 + " = " + var7);
            if (var7 != null) {
               var2.add(var7);
            }
         }

         this.fx = (WingsPostFX[])var2.toArray(new WingsPostFX[var2.size()]);
      }
   }

   public boolean hasPermission(Player var1) {
      return this.name == null ? true : P.has(var1, "trails.wings." + this.name);
   }

   private void findinline() {
      ArrayList var1 = new ArrayList();

      int var2;
      for(var2 = 0; var2 < 14; ++var2) {
         for(int var3 = 0; var3 < 14; ++var3) {
            PixelType var4 = this.getType(var2, var3);
            if (var4 != PixelType.None && (this.getType(var2 - 1, var3) == PixelType.None || this.getType(var2 + 1, var3) == PixelType.None || this.getType(var2, var3 - 1) == PixelType.None || this.getType(var2, var3 + 1) == PixelType.None)) {
               var1.add(new Integer[]{var2, var3});
            }
         }
      }

      this.inline = new int[var1.size()][2];

      for(var2 = 0; var2 < var1.size(); ++var2) {
         int[] var5 = new int[]{((Integer[])var1.get(var2))[0], ((Integer[])var1.get(var2))[1]};
         this.inline[var2] = var5;
      }

   }

   public void display(Location var1, PlayerData var2) {
      if (var1 != null) {
         Location var3 = var1.clone();
         var3.setPitch(0.0F);
         var3.add(var3.getDirection().multiply(-0.3D));
         if (this.Movement) {
            this.displayWing(var3, var2, (float)(WingsManager.getWings() * -1));
            this.displayWing(var3, var2, (float)WingsManager.getWings());
         } else {
            this.displayWing(var3, var2, -120.0F);
            this.displayWing(var3, var2, 120.0F);
         }

      }
   }

   public void display(Location var1, Player var2) {
      if (var1 != null) {
         Location var3 = var1.clone();
         var3.setPitch(0.0F);
         var3.add(var3.getDirection().multiply(-0.3D));
         PlayerData var4 = DataManager.getData(var2);
         if (this.Movement) {
            this.displayWing(var3, var4, (float)(WingsManager.getWings() * -1));
            this.displayWing(var3, var4, (float)WingsManager.getWings());
         } else {
            this.displayWing(var3, var4, -120.0F);
            this.displayWing(var3, var4, 120.0F);
         }

      }
   }

   public void displayWing(Location var1, PlayerData var2, float var3) {
      for(int var4 = 0; var4 < 14; ++var4) {
         Location var5 = var1.clone().add(0.0D, 0.2D * (double)(14 - var4), 0.0D);
         var5.setYaw(var5.getYaw() - var3);

         for(int var6 = 0; var6 < 14; ++var6) {
            Location var7 = var5.clone();
            var7.add(var5.getDirection().multiply((double)var6 * 0.2D));
            this.spawn(var7, var2, var6, var4);
         }
      }

   }

   public void spawn(Location var1, PlayerData var2, int var3, int var4) {
      try {
         this.color = null;
         ParticleEffect.OrdinaryColor var5 = STUtils.intToColor(var2.getWings()[0]);
         ParticleEffect.OrdinaryColor var6 = STUtils.intToColor(var2.getWings()[1]);
         ParticleEffect.OrdinaryColor var7 = STUtils.intToColor(var2.getWings()[2]);
         if (var5 != null && var6 != null && var7 != null) {
            PixelType var8 = this.getType(var3, var4);
            if (var8 == PixelType.Red) {
               this.color = var5;
            } else if (var8 == PixelType.Green) {
               this.color = var6;
            } else if (var8 == PixelType.Blue) {
               this.color = var7;
            } else {
               if (var8 == PixelType.Flame) {
                  ParticleEffect.FLAME.display(0.0F, 0.0F, 0.0F, 0.0F, 0, var1, 25.0D);
                  return;
               }

               if (var8 == PixelType.Witch) {
                  ParticleEffect.SPELL_WITCH.display(0.0F, 0.0F, 0.0F, 0.0F, 0, var1, 25.0D);
                  return;
               }

               if (var8 == PixelType.Drip) {
                  ParticleEffect.DRIP_LAVA.display(0.0F, 0.0F, 0.0F, 0.0F, 0, var1, 25.0D);
                  return;
               }

               if (var8 == PixelType.Crit) {
                  ParticleEffect.CRIT_MAGIC.display(0.0F, 0.0F, 0.0F, 0.0F, 0, var1, 25.0D);
                  return;
               }
            }

            if (this.color != null && var8 != PixelType.None) {
               WingsPostFX[] var12;
               int var11 = (var12 = this.fx).length;

               for(int var10 = 0; var10 < var11; ++var10) {
                  WingsPostFX var9 = var12[var10];
                  var9.getPixel(this, this.color, var3, var4);
               }

               ColoredParticle.spawn(var1, this.color);
            }
         }
      } catch (Exception var13) {
      }
   }

   private boolean isOut(int var1, int var2) {
      return var1 >= 14 || var2 >= 14 || var1 < 0 || var2 < 0;
   }

   public PixelType getType(int var1, int var2) {
      if (this.isOut(var1, var2)) {
         return PixelType.None;
      } else {
         Color var3 = new Color(this.img.getRGB(var1, var2));
         int var4 = var3.getRed();
         int var5 = var3.getBlue();
         int var6 = var3.getGreen();
         if (var4 == 255 && var5 == 0 && var6 == 0) {
            return PixelType.Red;
         } else if (var4 == 0 && var5 == 255 && var6 == 0) {
            return PixelType.Blue;
         } else if (var4 == 0 && var5 == 0 && var6 == 255) {
            return PixelType.Green;
         } else if (var4 == 255 && var6 == 255 && var5 == 0) {
            return PixelType.Flame;
         } else if (var4 == 255 && var6 == 0 && var5 == 255) {
            return PixelType.Witch;
         } else if (var4 == 255 && var6 == 128 && var5 == 64) {
            return PixelType.Drip;
         } else {
            return var4 == 0 && var6 == 255 && var5 == 255 ? PixelType.Crit : PixelType.None;
         }
      }
   }

   public boolean hasColor1() {
      return this.hasColor1;
   }

   public boolean hasColor2() {
      return this.hasColor2;
   }

   public boolean hasColor3() {
      return this.hasColor3;
   }

   public boolean hasAnyColor() {
      return this.hasColor1 || this.hasColor2 || this.hasColor3;
   }

   public boolean isValid(boolean var1, boolean var2, boolean var3) {
      if (!var1 && var1 != this.hasColor1) {
         return false;
      } else if (!var2 && var2 != this.hasColor2) {
         return false;
      } else {
         return var3 || var3 == this.hasColor3;
      }
   }

   public String getPermission() {
      return "trails.wings." + this.name;
   }
}
