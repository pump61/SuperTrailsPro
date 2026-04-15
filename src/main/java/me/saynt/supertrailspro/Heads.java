package me.saynt.supertrailspro;

import java.net.URL;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

public class Heads {
   public static ItemStack hwhite;
   public static ItemStack hred;
   public static ItemStack hyellow;
   public static ItemStack hgreen;
   public static ItemStack hblue;
   public static ItemStack hpurple;
   public static ItemStack hblack;
   public static ItemStack e_orange;
   public static ItemStack e_dark_cyan;
   public static ItemStack e_marmoreal;
   public static ItemStack e_aqua;
   public static ItemStack e_dark_red;
   public static ItemStack e_gray;
   public static ItemStack e_pink;

   static {
      hwhite    = skull("e5a770e7e44b3a1e6c3b83a97ff6997b1f5b26550e9d7aa5d5021a0c2b6ee");
      hred      = skull("97c1f1ead4d531caa4a5b0d69edbce29af789a2550e5ddbd23775be05e2df2c4");
      hyellow   = skull("14c4141c1edf3f7e41236bd658c5bc7b5aa7abf7e2a852b647258818acd70d8");
      hgreen    = skull("361e5b333c2a3868bb6a58b6674a2639323815738e77e053977419af3f77");
      hblue     = skull("c96540ce762125e398ca53d4cd9b668396d0467e128b30da5aa62be9ce060");
      hpurple   = skull("855654b3f1bfb2cdf0f4b52d6360a03d31ddafc710f8afaea99fba667e482df");
      hblack    = skull("9ddebbb062f6a385a91ca05f18f5c0acbe33e2d06ee9e7416cef6ee43dfe2fb");
      e_orange  = skull("fea590b681589fb9b0e8664ee945b41eb3851faf66aaf48525fba169c34270");
      e_dark_cyan = skull("b784c152bfc3e2e313ef23fc46d44fce41e4adad9421bac2da3894511cc03b");
      e_marmoreal = skull("bf76294011cbdcd2e92941dafe6b3726dff02c3e1f84dfa57c6abab6fc33ce6");
      e_aqua    = skull("489ce89526fc12624678f305493aa65da8a1b360546a505d118eb1fad775");
      e_dark_red = skull("51014e4f41d7729928f215555da2eaf158ce83d9e0c9961bef5eb7613d37e");
      e_gray    = skull("f2f085c6b3cb228e5ba81df562c4786762f3c257127e9725c77b7fd301d37");
      e_pink    = skull("607326d31858ea57e7bc55f3e75e6c85b34ff4bfd28088f94f11eb8e0d1cf");
   }

   public static ItemStack skull(String textureHash) {
      ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
      SkullMeta meta = (SkullMeta) skull.getItemMeta();
      if (meta == null) return skull;

      try {
         PlayerProfile profile = Bukkit.createPlayerProfile(UUID.randomUUID());
         PlayerTextures textures = profile.getTextures();
         textures.setSkin(new URL("http://textures.minecraft.net/texture/" + textureHash));
         profile.setTextures(textures);
         meta.setOwnerProfile(profile);
      } catch (Exception e) {
         e.printStackTrace();
      }

      skull.setItemMeta(meta);
      return skull;
   }
}