package me.saynt.supertrailspro.wings;

import java.awt.image.BufferedImage;
import java.io.File;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import org.jetbrains.annotations.Nullable;
import javax.imageio.ImageIO;
import me.saynt.supertrailspro.STUtils;
import me.saynt.supertrailspro.data.PlayerData;
import me.saynt.supertrailspro.particlelib.ParticleEffect;
import me.saynt.supertrailspro.trails.TrailWings;
import me.saynt.supertrailspro.trails.TrailsUtil;
import me.saynt.supertrailspro.trails.WingsPattern;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CustomWings {
   public static HashMap<String, HashMap<String, Integer>> wings = new HashMap();
   public static HashMap<Integer, String> ids = new HashMap();
   public static HashMap<String, Integer> slots = new HashMap();
   public static HashMap<String, ItemStack> it = new HashMap();
   public static HashMap<String, HashMap<String, Integer[]>> patterns = new HashMap();
   public static HashMap<String, Integer> patternslot = new HashMap();
   public static HashMap<String, ItemStack> patternItem = new HashMap();
   public static HashMap<String, HashMap<String, Integer>> normal_maps = new HashMap();
   public static HashMap<String, WingsInfo> info = new HashMap();

   public static void preset() {
      CustomWingsLoader.a();
      STUtils.setColors();
   }

   public static HashMap<String, Integer> convertWings(File var0, String var1) {
      HashMap var2 = new HashMap();
      WingsInfo var3 = new WingsInfo(var1);
      File var4 = var0;
      BufferedImage var5 = null;

      try {
         var5 = ImageIO.read(var4);
      } catch (Exception var12) {
         var12.printStackTrace();
      }

      int var6 = 0;
      int var7 = 0;

      while(var6 <= 13) {
         int var8 = var5.getRGB(var7, var6);
         int var9 = (var8 & 16711680) >> 16;
         int var10 = (var8 & '\uff00') >> 8;
         int var11 = var8 & 255;
         if (var9 == 255 && var10 == 0 && var11 == 0) {
            var2.put(var6 + ";" + var7, 1);
            var3.setRed(true);
         } else if (var9 == 0 && var10 == 255 && var11 == 0) {
            var2.put(var6 + ";" + var7, 2);
            var3.setGreen(true);
         } else if (var9 == 0 && var10 == 0 && var11 == 255) {
            var2.put(var6 + ";" + var7, 3);
            var3.setBlue(true);
         } else if (var9 == 255 && var10 == 255 && var11 == 0) {
            var2.put(var6 + ";" + var7, 4);
         } else if (var9 == 255 && var10 == 0 && var11 == 255) {
            var2.put(var6 + ";" + var7, 5);
         } else if (var9 == 255 && var10 == 128 && var11 == 64) {
            var2.put(var6 + ";" + var7, 6);
         } else if (var9 == 0 && var10 == 255 && var11 == 255) {
            var2.put(var6 + ";" + var7, 7);
         }

         ++var7;
         if (var7 > 13) {
            ++var6;
            var7 = 0;
         }
      }

      info.put(var1, var3);
      return var2;
   }

   public static HashMap<String, Integer> convertWingsNormalMap(File var0) {
      HashMap var1 = new HashMap();
      File var2 = var0;
      BufferedImage var3 = null;

      try {
         var3 = ImageIO.read(var2);
      } catch (Exception var10) {
         var10.printStackTrace();
      }

      int var4 = 0;
      int var5 = 0;

      while(var4 <= 13) {
         int var6 = var3.getRGB(var5, var4);
         int var7 = (var6 & 16711680) >> 16;
         int var8 = (var6 & '\uff00') >> 8;
         int var9 = var6 & 255;
         if (var7 != 0) {
            var1.put(var4 + ";" + var5, var7);
         }

         ++var5;
         if (var5 > 13) {
            ++var4;
            var5 = 0;
         }
      }

      return var1;
   }

   public static double getHeightAt(HashMap<String, Integer> var0, int var1, int var2) {
      if (var0.containsKey(var1 + ";" + var2)) {
         int var3 = (Integer)var0.get(var1 + ";" + var2);
         var3 -= 100;
         return 0.003D * (double)var3 * -1.0D;
      } else {
         return 0.0D;
      }
   }

   public static HashMap<String, Integer[]> convertPattern(File var0) {
      HashMap var1 = new HashMap();
      File var2 = var0;
      BufferedImage var3 = null;

      try {
         var3 = ImageIO.read(var2);
      } catch (Exception var10) {
         var10.printStackTrace();
      }

      int var4 = 0;
      int var5 = 0;

      while(var4 <= 13) {
         int var6 = var3.getRGB(var5, var4);
         int var7 = (var6 & 16711680) >> 16;
         int var8 = (var6 & '\uff00') >> 8;
         int var9 = var6 & 255;
         if (var7 < 5) {
            var7 = 5;
         }

         if (var8 < 5) {
            var8 = 5;
         }

         if (var9 < 5) {
            var9 = 5;
         }

         var1.put(var4 + ";" + var5, new Integer[]{var7, var8, var9});
         ++var5;
         if (var5 > 13) {
            ++var4;
            var5 = 0;
         }
      }

      return var1;
   }

   public static void Display(PlayerData var0, int var1, int[] var2) {
      TrailWings var3 = (TrailWings)TrailsUtil.getFromID(var1);
      var3.display(var0.getLocation(), var0);
   }

   public static void Display(Player var0, HashMap<String, Integer> var1, int var2, int var3, int var4, @Nullable HashMap<String, Integer> var5) {
      if (var2 != 0 && var3 != 0 && var4 != 0) {
         Location var6 = var0.getLocation().clone();
         var6.setPitch(0.0F);
         var6.add(var6.getDirection().multiply(-0.3D));
         var6.setPitch(0.0F);
         var6.setYaw(var6.getYaw() - 120.0F);
         ParticleEffect.OrdinaryColor var7 = STUtils.intToColor(var2);
         ParticleEffect.OrdinaryColor var8 = STUtils.intToColor(var3);
         ParticleEffect.OrdinaryColor var9 = STUtils.intToColor(var4);
         Iterator var11 = var1.entrySet().iterator();

         Entry var10;
         String var12;
         Integer var13;
         int var14;
         int var15;
         int var16;
         Location var17;
         while(var11.hasNext()) {
            var10 = (Entry)var11.next();
            var12 = (String)var10.getKey();
            var13 = (Integer)var10.getValue();
            var14 = Integer.parseInt(var12.split(";")[0]);
            var15 = Integer.parseInt(var12.split(";")[1]);
            var16 = 14 - var14;
            var17 = var6.clone().add(0.0D, 0.2D * (double)var16, 0.0D);
            var17.add(var17.getDirection().multiply((double)var15 * 0.2D));
            if (var5 != null && var13 != 0) {
               var17.setYaw(var0.getLocation().getYaw());
               var17.setPitch(0.0F);
               var17.add(var17.getDirection().multiply(getHeightAt(var5, var14, var15)));
            }

            if (var13 == 1) {
               ColoredParticle.spawn(var17, var7);
            } else if (var13 == 2) {
               ColoredParticle.spawn(var17, var8);
            } else if (var13 == 3) {
               ColoredParticle.spawn(var17, var9);
            } else if (var13 == 4) {
               ParticleEffect.FLAME.display(0.0F, 0.0F, 0.0F, 0.0F, 0, var17, 25.0D);
            } else if (var13 == 5) {
               ParticleEffect.REDSTONE.display(0.0F, 0.0F, 0.0F, 0.0F, 0, var17, 25.0D);
            } else if (var13 == 6) {
               ParticleEffect.DRIP_LAVA.display(0.0F, 0.0F, 0.0F, 0.0F, 0, var17, 25.0D);
            } else if (var13 == 7) {
               ParticleEffect.CRIT_MAGIC.display(0.0F, 0.0F, 0.0F, 0.0F, 0, var17, 25.0D);
            }
         }

         var6.setYaw(var0.getLocation().clone().getYaw() + 120.0F);
         var11 = var1.entrySet().iterator();

         while(var11.hasNext()) {
            var10 = (Entry)var11.next();
            var12 = (String)var10.getKey();
            var13 = (Integer)var10.getValue();
            var14 = Integer.parseInt(var12.split(";")[0]);
            var15 = Integer.parseInt(var12.split(";")[1]);
            var16 = 14 - var14;
            var17 = var6.clone().add(0.0D, 0.2D * (double)var16, 0.0D);
            var17.add(var17.getDirection().multiply((double)var15 * 0.2D));
            if (var13 == 1) {
               ColoredParticle.spawn(var17, var7);
            } else if (var13 == 2) {
               ColoredParticle.spawn(var17, var8);
            } else if (var13 == 3) {
               ColoredParticle.spawn(var17, var9);
            } else if (var13 == 4) {
               ParticleEffect.FLAME.display(0.0F, 0.0F, 0.0F, 0.0F, 0, var17, 25.0D);
            } else if (var13 == 5) {
               ParticleEffect.REDSTONE.display(0.0F, 0.0F, 0.0F, 0.0F, 0, var17, 25.0D);
            } else if (var13 == 6) {
               ParticleEffect.DRIP_LAVA.display(0.0F, 0.0F, 0.0F, 0.0F, 0, var17, 25.0D);
            } else if (var13 == 7) {
               ParticleEffect.CRIT_MAGIC.display(0.0F, 0.0F, 0.0F, 0.0F, 0, var17, 25.0D);
            }
         }

      }
   }

   public static Integer[] getColorAt(HashMap<String, Integer[]> var0, int var1, int var2) {
      return var0.containsKey(var1 + ";" + var2) ? (Integer[])var0.get(var1 + ";" + var2) : new Integer[]{255, 255, 255};
   }

   public static void Display(PlayerData var0, int var1, String var2) {
      String var3 = STUtils.IdToWings(var1);
      TrailWings var4 = (TrailWings)TrailsUtil.getFromID(var1);
      WingsPattern var5 = WingsManager.getPattern(var2);
      if (var5 != null && var4 != null) {
         var5.display(var4, var0.getLocation(), var0);
      }
   }

   public static void Display(Player var0, HashMap<String, Integer> var1, HashMap<String, Integer[]> var2) {
      Location var3 = var0.getLocation().clone();
      var3.setPitch(0.0F);
      var3.add(var3.getDirection().multiply(-0.3D));
      var3.setPitch(0.0F);
      var3.setYaw(var3.getYaw() - 120.0F);
      Iterator var5 = var1.entrySet().iterator();

      while(true) {
         Entry var4;
         String var6;
         int var7;
         int var8;
         int var9;
         Integer[] var12;
         while(var5.hasNext()) {
            var4 = (Entry)var5.next();
            var6 = (String)var4.getKey();
            var7 = Integer.parseInt(var6.split(";")[0]);
            var8 = Integer.parseInt(var6.split(";")[1]);
            var9 = 14 - var7;
            Location var10 = var3.clone().add(0.0D, 0.2D * (double)var9, 0.0D);
            var10.add(var10.getDirection().multiply((double)var8 * 0.2D));
            Integer var11 = (Integer)var4.getValue();
            if (var11 != 1 && var11 != 2 && var11 != 3) {
               if (var11 == 4) {
                  ParticleEffect.FLAME.display(0.0F, 0.0F, 0.0F, 0.0F, 0, var10, 25.0D);
               } else if (var11 == 5) {
                  ParticleEffect.SPELL_WITCH.display(0.0F, 0.0F, 0.0F, 0.0F, 0, var10, 25.0D);
               } else if (var11 == 6) {
                  ParticleEffect.DRIP_LAVA.display(0.0F, 0.0F, 0.0F, 0.0F, 0, var10, 25.0D);
               } else if (var11 == 7) {
                  ParticleEffect.CRIT_MAGIC.display(0.0F, 0.0F, 0.0F, 0.0F, 0, var10, 25.0D);
               }
            } else {
               var12 = getColorAt(var2, var7, var8);
               ColoredParticle.spawn(var10, var12[0], var12[1], var12[2]);
            }
         }

         var3.setYaw(var0.getLocation().clone().getYaw() + 120.0F);
         var5 = var1.entrySet().iterator();

         while(true) {
            while(var5.hasNext()) {
               var4 = (Entry)var5.next();
               var6 = (String)var4.getKey();
               var7 = Integer.parseInt(var6.split(";")[0]);
               var8 = Integer.parseInt(var6.split(";")[1]);
               var9 = 14 - var7;
               Integer var13 = (Integer)var4.getValue();
               Location var14 = var3.clone().add(0.0D, 0.2D * (double)var9, 0.0D);
               var14.add(var14.getDirection().multiply((double)var8 * 0.2D));
               if (var13 != 1 && var13 != 2 && var13 != 3) {
                  if (var13 == 4) {
                     ParticleEffect.FLAME.display(0.0F, 0.0F, 0.0F, 0.0F, 0, var14, 25.0D);
                  } else if (var13 == 5) {
                     ParticleEffect.SPELL_WITCH.display(0.0F, 0.0F, 0.0F, 0.0F, 0, var14, 25.0D);
                  } else if (var13 == 6) {
                     ParticleEffect.DRIP_LAVA.display(0.0F, 0.0F, 0.0F, 0.0F, 0, var14, 25.0D);
                  } else if (var13 == 7) {
                     ParticleEffect.CRIT_MAGIC.display(0.0F, 0.0F, 0.0F, 0.0F, 0, var14, 25.0D);
                  }
               } else {
                  var12 = getColorAt(var2, var7, var8);
                  ColoredParticle.spawn(var14, var12[0], var12[1], var12[2]);
               }
            }

            return;
         }
      }
   }
}
