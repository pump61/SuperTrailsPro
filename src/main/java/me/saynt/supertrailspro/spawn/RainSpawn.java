package me.saynt.supertrailspro.spawn;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.saynt.supertrailspro.STUtils;
import me.saynt.supertrailspro.ServerVersion;
import me.saynt.supertrailspro.ServerVersionsEnum;
import me.saynt.supertrailspro.SuperTrails;
import me.saynt.supertrailspro.data.PlayerData;
import me.saynt.supertrailspro.particlelib.ParticleEffect;
import me.saynt.supertrailspro.wings.ColoredParticle;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RainSpawn implements RainsInterface {
   List<Item> rain = new ArrayList();
   List<Item> toclear = new ArrayList();
   int pause = 0;
   static int z = 0;
   public static String itemname;

   static {
      itemname = ServerVersion.higherThanOrEqual(ServerVersionsEnum.S113) ? "пїЅ0SuperTrailsRainItem" : "пїЅrпїЅrSuperTrails=";
   }

   public void tick() {
      ++this.pause;
      if (this.pause == 2) {
         this.pause = 0;
         if (this.rain.size() != 0) {
            Iterator var2 = this.rain.iterator();

            Item var1;
            while(var2.hasNext()) {
               var1 = (Item)var2.next();
               if (var1.getTicksLived() > 4) {
                  var1.remove();
                  this.toclear.add(var1);
               }
            }

            var2 = this.toclear.iterator();

            while(var2.hasNext()) {
               var1 = (Item)var2.next();
               this.rain.remove(var1);
            }

            this.toclear.clear();
         }
      }
   }

   public void spawn(PlayerData var1) {
      try {
         z = z > 5000 ? (z = 0) : ++z;
         ItemStack var2 = var1.getRainItem();
         if (var2 == null || var2.getType() == Material.AIR) {
            var1.resetRains();
            var1.setTrail(0);
            return;
         }

         var2 = var2.clone();
         ParticleEffect.OrdinaryColor var3 = var1.getRainCloudColor();
         Location var4 = var1.getLocation().clone().add(0.0D, 4.0D, 0.0D);
         ItemMeta var5 = var2.getItemMeta();
         if (var5 == null) {
            var1.resetRains();
            var1.setTrail(0);
            return;
         }

         if (!STUtils.optionb("NoRainItemNames")) {
            var5.setDisplayName(itemname + z);
         } else {
            var5.setLore(this.getArrayString(itemname + z));
         }

         var2.setItemMeta(var5);
         var2.setAmount(1);

         for(int var6 = 1; var6 < 11; ++var6) {
            int var7 = SuperTrails.r(-5, 5);
            int var8 = SuperTrails.r(-5, 5);
            int var9 = SuperTrails.r(-5, 5);

            try {
               ColoredParticle.spawn(var4.clone().add((double)var7 * 0.1D, (double)var8 * 0.1D, (double)var9 * 0.1D), var3);
            } catch (Exception var11) {
               var1.clear();
            }
         }

         Location var13 = var1.getLocation().clone().add(0.0D, 3.0D, 0.0D);
         Item var14 = var1.getLocation().getWorld().dropItemNaturally(var13, var2);
         this.rain.add(var14);
         var13.setPitch(-90.0F);
         var14.setPickupDelay(10000);
         var14.setItemStack(var2);
         var14.setTicksLived(1);
         var14.setVelocity(var13.getDirection().multiply(-1));
      } catch (Exception var12) {
         var12.printStackTrace();
         var1.clear();
      }

   }

   public void removeAll() {
      Iterator var2 = this.rain.iterator();

      while(var2.hasNext()) {
         Item var1 = (Item)var2.next();
         var1.remove();
      }

      this.rain.clear();
   }

   public ArrayList<String> getArrayString(String var1) {
      ArrayList var2 = new ArrayList();
      var2.add(var1);
      return var2;
   }
}
