package libs.CustomHeads.implementation;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import java.util.Base64;
import java.util.UUID;
import libs.CustomHeads.CustomHeadApi;
import libs.CustomHeads.IHeadCreator;
import libs.CustomHeads.utils.Reflection;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Skull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomHeadCreator implements IHeadCreator {
   public GameProfile createGameProfile(String var1) {
      GameProfile var2 = new GameProfile(UUID.randomUUID(), (String)null);
      PropertyMap var3 = var2.getProperties();
      if (var3 == null) {
         CustomHeadApi.log("No property map found in GameProfile, can't continue.");
         return null;
      } else {
         byte[] var4 = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", var1).getBytes());
         var3.put("textures", new Property("textures", new String(var4)));
         return var2;
      }
   }

   public ItemStack createItemStack(String var1) {
      GameProfile var2 = this.createGameProfile(var1);
      if (var2 == null) {
         return null;
      } else {
         ItemStack var3 = new ItemStack(Material.PLAYER_HEAD, 1, (short)3);
         ItemMeta var4 = var3.getItemMeta();
         Class var5 = var4.getClass();
         if (!Reflection.set(var5, var4, "profile", var2, "Unable to inject porofile")) {
            return null;
         } else {
            var3.setItemMeta(var4);
            return var3;
         }
      }
   }

   public ItemStack createItemStack(String var1, ItemStack var2) {
      ItemStack var3 = this.createItemStack(var1);
      if (var3 == null) {
         var3 = var2;
      }

      return var3;
   }

   public boolean updateSkull(Skull var1, String var2) {
      if (var1 == null) {
         return false;
      } else {
         GameProfile var3 = this.createGameProfile(var2);
         if (var3 == null) {
            return false;
         } else {
            var1.setSkullType(SkullType.PLAYER);
            Class var4 = var1.getClass();
            if (!Reflection.set(var4, var1, "profile", var3, "Unable to inject porofile")) {
               return false;
            } else {
               var1.update();
               return true;
            }
         }
      }
   }
}
