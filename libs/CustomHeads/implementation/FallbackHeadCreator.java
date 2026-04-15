package libs.CustomHeads.implementation;

import com.mojang.authlib.GameProfile;
import libs.CustomHeads.IHeadCreator;
import org.bukkit.Material;
import org.bukkit.block.Skull;
import org.bukkit.inventory.ItemStack;

public class FallbackHeadCreator implements IHeadCreator {
   public ItemStack createItemStack(String var1, ItemStack var2) {
      return new ItemStack(Material.PLAYER_HEAD, 1, (short)3);
   }

   public ItemStack createItemStack(String var1) {
      return new ItemStack(Material.PLAYER_HEAD, 1, (short)3);
   }

   public GameProfile createGameProfile(String var1) {
      return null;
   }

   public boolean updateSkull(Skull var1, String var2) {
      return false;
   }
}
