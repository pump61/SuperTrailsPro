package me.saynt.supertrailspro.inventories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import me.saynt.supertrailspro.P;
import me.saynt.supertrailspro.RenameUtils;
import me.saynt.supertrailspro.STUtils;
import me.saynt.supertrailspro.ServerVersion;
import me.saynt.supertrailspro.ServerVersionsEnum;
import me.saynt.supertrailspro.SuperTrails;
import me.saynt.supertrailspro.eventtrails.EventColor;
import me.saynt.supertrailspro.lang.L;
import me.saynt.supertrailspro.lang.Language;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GuiItem {
   boolean linked = true;
   String name;
   String lore;
   List<String> unlinkedLore;
   ItemStack is;
   Object command;
   HashMap<String, String> toChange = new HashMap();
   boolean evencolor = false;
   EventColor color;
   public String permission;
   public List<String> adds = new ArrayList();
   public List<String> unlinkedLores = new ArrayList();
   boolean glowing = false;
   boolean ha = true;

   public GuiItem() {
   }

   public GuiItem(ItemStack var1) {
      this.is = var1;
   }

   public GuiItem(ItemStack var1, String var2) {
      this.is = var1;
      this.name = var2;
   }

   public GuiItem(ItemStack var1, String var2, String var3) {
      this.is = var1;
      this.name = var2;
      this.lore = var3;
   }

   public GuiItem(ItemStack var1, String var2, List<String> var3) {
      this.is = var1;
      this.name = var2;
      this.unlinkedLore = var3;
   }

   public GuiItem(String var1, String var2, String var3) {
      String var4 = SuperTrails.p.getConfig().getString(var1);
      ItemStack var5 = STUtils.readItemStack(var4);
      this.is = var5 != null ? var5 : new ItemStack(Material.AIR);
      this.name = var2;
      this.lore = var3;
   }

   public GuiItem(String var1, String var2) {
      String var3 = SuperTrails.p.getConfig().getString(var1);
      ItemStack var4 = STUtils.readItemStack(var3);
      this.is = var4 != null ? var4 : new ItemStack(Material.AIR);
      this.name = var2;
   }

   public GuiItem(String var1, String var2, List<String> var3) {
      String var4 = SuperTrails.p.getConfig().getString(var1);
      ItemStack var5 = STUtils.readItemStack(var4);
      this.is = var5 != null ? var5 : new ItemStack(Material.AIR);
      this.name = var2;
      this.unlinkedLore = var3;
   }

   public GuiItem(ItemStack var1, Language var2) {
      this.is = var1;
      this.name = var2.getString();
   }

   public void setUnlinkedLore(List<String> var1) {
      this.unlinkedLores = var1;
   }

   public void setItemIS(ItemStack var1) {
      this.is = var1;
   }

   public void setName(String var1) {
      this.name = var1;
   }

   public void setLore(String var1) {
      this.lore = var1;
   }

   public void addAdditionalLore(String var1) {
      this.adds.add(var1);
   }

   public GuiItem linked(boolean var1) {
      this.linked = var1;
      return this;
   }

   public GuiItem setPermission(String var1) {
      this.permission = var1;
      return this;
   }

   public boolean hasPermission(Player var1) {
      return this.permission == null ? true : P.has(var1, this.permission);
   }

   public GuiItem addPlaceholder(String var1, String var2) {
      this.toChange.put(var1, var2);
      return this;
   }

   public List<String> splitLore() {
      return (List)(this.unlinkedLore == null ? new ArrayList() : this.unlinkedLore);
   }

   public GuiItem cmd(Object var1) {
      this.command = var1;
      return this;
   }

   public String ph(String var1) {
      Entry var2;
      for(Iterator var3 = this.toChange.entrySet().iterator(); var3.hasNext(); var1 = var1.replaceAll((String)var2.getKey(), (String)var2.getValue())) {
         var2 = (Entry)var3.next();
      }

      return var1;
   }

   public List<String> phl(List<String> var1) {
      Entry var2;
      for(Iterator var3 = this.toChange.entrySet().iterator(); var3.hasNext(); var1 = L.listReplacer(var1, (String)var2.getKey(), (String)var2.getValue())) {
         var2 = (Entry)var3.next();
      }

      return var1;
   }

   public GuiItem setGlow(boolean var1) {
      this.glowing = var1;
      return this;
   }

   public GuiItem hideAttributes(boolean var1) {
      this.ha = var1;
      return this;
   }

   public ItemStack generate(Player var1) {
      if (this.evencolor) {
         return this.generateWithColors(var1);
      } else {
         try {
            ItemStack var2 = this.is.clone();
            ItemMeta var3 = var2.getItemMeta();
            if (var3 == null) return var2;
            if (this.name != null) {
               String var4 = this.linked ? L.get(var1, this.name) : this.name;
               var4 = this.ph(var4);
               var3.setDisplayName(var4);
            }

            if (this.lore != null) {
               List var8 = this.linked ? L.getLore(var1, this.lore) : this.unlinkedLores;
               Iterator var6 = this.adds.iterator();

               while(var6.hasNext()) {
                  String var5 = (String)var6.next();
                  var8.addAll(L.getLore(var1, var5));
               }

               var8 = this.phl(var8);
               var3.setLore(var8);
            }

            if (this.glowing) {
               var3.addEnchant(Enchantment.DURABILITY, 1, true);
               var3.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
            }

            if (this.ha) {
               var3.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES});
            }

            var2.setItemMeta(var3);
            if (ServerVersion.getCurrent() == ServerVersionsEnum.S116) {
               var2 = RenameUtils.create(var2, var3.getDisplayName(), var3.getLore());
            }

            return var2;
         } catch (Exception var7) {
            var7.printStackTrace();
            return new ItemStack(Material.AIR);
         }
      }
   }

   public ItemStack generateWithColors(Player var1) {
      try {
         ItemStack var2 = this.is.clone();
         ItemMeta var3 = var2.getItemMeta();
         if (var3 == null) return var2;
         String var4 = this.linked ? L.get(var1, this.name) : this.name;
         var4 = var4.replaceAll("!color!", L.get(var1, "RainAndWings." + this.color.getColor().getName()));
         var4 = this.ph(var4);
         if (this.lore != null) {
            List var5 = this.linked ? L.getLore(var1, this.lore) : this.splitLore();
            var5 = L.listReplacer(var5, "!color!", L.get(var1, "RainAndWings." + this.color.getColor().getName()));
            var5 = this.phl(var5);
            var3.setLore(var5);
         }

         if (this.glowing) {
            var3.addEnchant(Enchantment.DURABILITY, 1, true);
            var3.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
         }

         if (this.ha) {
            var3.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES});
         }

         var3.setDisplayName(var4);
         var2.setItemMeta(var3);
         if (ServerVersion.getCurrent() == ServerVersionsEnum.S116) {
            var2 = RenameUtils.create(var2, var3.getDisplayName(), var3.getLore());
         }

         return var2;
      } catch (Exception var6) {
         var6.printStackTrace();
         return new ItemStack(Material.AIR);
      }
   }
}
