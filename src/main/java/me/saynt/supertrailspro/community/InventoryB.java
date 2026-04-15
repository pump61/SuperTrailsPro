package me.saynt.supertrailspro.community;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

public class InventoryB {
   public static String toBase64(Inventory var0) {
      try {
         ByteArrayOutputStream var1 = new ByteArrayOutputStream();
         BukkitObjectOutputStream var2 = new BukkitObjectOutputStream(var1);
         var2.writeInt(var0.getSize());

         for(int var3 = 0; var3 < var0.getSize(); ++var3) {
            var2.writeObject(var0.getItem(var3));
         }

         var2.close();
         return Base64Coder.encodeLines(var1.toByteArray());
      } catch (Exception var4) {
         throw new IllegalStateException("Unable to save item stacks.", var4);
      }
   }

   public static Inventory fromBase64(String var0) {
      try {
         ByteArrayInputStream var1 = new ByteArrayInputStream(Base64Coder.decodeLines(var0));
         BukkitObjectInputStream var2 = new BukkitObjectInputStream(var1);
         Inventory var3 = Bukkit.getServer().createInventory((InventoryHolder)null, var2.readInt(), "Community BETA§r");

         for(int var4 = 0; var4 < var3.getSize(); ++var4) {
            var3.setItem(var4, (ItemStack)var2.readObject());
         }

         var2.close();
         return var3;
      } catch (Exception var5) {
         return null;
      }
   }
}
