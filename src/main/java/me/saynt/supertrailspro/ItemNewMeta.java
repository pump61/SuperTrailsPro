package me.saynt.supertrailspro;

import java.util.List;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemNewMeta {

   public static ItemStack CreateNew(ItemStack item, String name) {
      return CreateNew(item, name, null);
   }

   public static ItemStack CreateNew(ItemStack item, String name, List<String> lore) {
      ItemStack result = item.clone();
      ItemMeta meta = result.getItemMeta();
      if (meta == null) return result;

      // Suporta códigos de cor com §
      meta.setDisplayName(name);

      if (lore != null && !lore.isEmpty()) {
         meta.setLore(lore);
      }

      result.setItemMeta(meta);
      return result;
   }

   // Mantido para compatibilidade — no 1.21.4 não é mais necessário converter para JSON
   public static String ChatColorToJSON(String text) {
      return text;
   }

   public static String ChatColorToJSON(String text, boolean italic) {
      return text;
   }
}