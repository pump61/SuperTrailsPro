package me.saynt.supertrailspro.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import me.saynt.supertrailspro.P;
import me.saynt.supertrailspro.STUtils;
import me.saynt.supertrailspro.lang.L;
import me.saynt.supertrailspro.lang.LanguageManager;
import me.saynt.supertrailspro.trails.modes.Modes;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InvMenu {
   protected String name;
   protected String displayName;
   protected HashMap<Integer, ItemStack> qa = new HashMap();
   protected HashMap<Integer, ItemStack> qd = new HashMap();
   protected HashMap<Integer, String> lore = new HashMap();
   protected HashMap<Integer, String> qcmd = new HashMap();
   protected HashMap<Integer, String> qperm = new HashMap();
   protected HashMap<Integer, ItemStack> stati = new HashMap();
   protected int size = 54;
   protected List<String> MenuNames = new ArrayList();
   protected MenuType mtype;

   public static ItemStack getBlocked(Player var0) {
      ItemStack var1 = new ItemStack(Material.INK_SAC, 1, (short)8);
      String var2 = L.get(var0, "Menu.NoAccess");
      ItemMeta var3 = var1.getItemMeta();
      var3.setDisplayName(var2);
      var1.setItemMeta(var3);
      return var1;
   }

   public InvMenu(String var1, String var2) {
      this.mtype = MenuType.Unknown;
      InvControl.registerMenu(var1, this);
      this.name = var1;
      this.displayName = var2;
      this.getNames();
   }

   public InvMenu(String var1, String var2, MenuType var3) {
      this.mtype = MenuType.Unknown;
      InvControl.registerMenu(var1, this);
      this.name = var1;
      this.mtype = var3;
      this.displayName = var2;
      this.getNames();
   }

   public InvMenu(String var1, String var2, MenuType var3, boolean var4) {
      this.mtype = MenuType.Unknown;
      if (var4) {
         InvControl.registerMenu(var1, this);
      }

      this.name = var1;
      this.mtype = var3;
      this.displayName = var2;
      this.getNames();
   }

   public void addLanguage(String var1) {
      this.MenuNames.add(var1.replaceAll("&", "§"));
   }

   public boolean isMenu(String var1) {
      return this.MenuNames.contains(var1);
   }

   public void getNames() {
      Iterator var2 = LanguageManager.Langs.entrySet().iterator();

      while(var2.hasNext()) {
         Entry var1 = (Entry)var2.next();
         FileConfiguration var3 = (FileConfiguration)var1.getValue();
         if (var3.getString("MenuNames." + this.displayName) != null) {
            this.addLanguage(var3.getString("MenuNames." + this.displayName));
         }
      }

   }

   public String getID() {
      return this.name;
   }

   public void setSize(int var1) {
      this.size = var1;
   }

   public void open(Player var1) {
      String var2 = L.get(var1, "MenuNames." + this.displayName);
      if (this.isMenu(var2)) {
         Inventory var3 = Bukkit.createInventory((InventoryHolder)null, this.size, var2);
         Iterator var5 = this.qa.entrySet().iterator();

         while(true) {
            Entry var4;
            int var6;
            ItemStack var7;
            ItemMeta var8;
            String var9;
            String var10;
            while(true) {
               if (!var5.hasNext()) {
                  var5 = this.stati.entrySet().iterator();

                  while(var5.hasNext()) {
                     var4 = (Entry)var5.next();
                     var3.setItem((Integer)var4.getKey(), (ItemStack)var4.getValue());
                  }

                  var1.openInventory(var3);
                  return;
               }

               var4 = (Entry)var5.next();
               var6 = (Integer)var4.getKey();
               var7 = null;
               var7 = (ItemStack)var4.getValue();
               var7 = var7.clone();
               var8 = var7.getItemMeta();
               var9 = L.get(var1, var8.getDisplayName());
               if (this.mtype != MenuType.Particles || !var8.getDisplayName().equalsIgnoreCase("Menu.Modes")) {
                  break;
               }

               var10 = Modes.replaceMD(var1, var9);
               if (var10 != null) {
                  var9 = var10;
                  break;
               }
            }

            if (this.lore.containsKey(var6)) {
               new ArrayList();
               List var11 = L.getLore(var1, (String)this.lore.get(var6));
               if (this.qperm.containsKey(var6)) {
                  var11 = InvControl.replacePerm(var1, (String)this.qperm.get(var6), var11, this.getMenuType(), (String)this.qcmd.get(var6));
               }

               var8.setLore(var11);
            }

            var8.setDisplayName(var9);
            var7.setItemMeta(var8);
            if (var7 != null) {
               var3.setItem(var6, var7);
            }

            var10 = (String)this.qperm.get(var4.getKey());
            if (var10 != null && STUtils.optionb("HideNoPermissionTrails") && !P.has(var1, var10)) {
               var3.setItem(var6, getBlocked(var1));
            }
         }
      }
   }

   public void addClickableItem(int var1, ItemStack var2, String var3, List<String> var4, List<String> var5, String var6, String var7) {
      if (var1 >= 0) {
         ItemStack var8 = var2.clone();
         ItemMeta var9 = var8.getItemMeta();
         var9.setDisplayName(var3);
         var9.setLore(var4);
         ItemStack var10 = var2.clone();
         ItemMeta var11 = var10.getItemMeta();
         var11.setDisplayName(var3);
         var11.setLore(var5);
         this.qa.put(var1, var8);
         this.qd.put(var1, var10);
         this.qcmd.put(var1, var7);
      }
   }

   public void addClickableItem(int var1, Material var2, String var3, List<String> var4, List<String> var5, String var6, String var7) {
      if (var1 >= 0) {
         this.addClickableItem(var1, new ItemStack(var2), var3, var4, var5, var6, var7);
      }
   }

   public void addClickableItem(int var1, ItemStack var2, String var3, String var4) {
      if (var1 >= 0) {
         ItemStack var5 = var2.clone();
         ItemMeta var6 = var5.getItemMeta();
         var6.setDisplayName(var3);
         var5.setItemMeta(var6);
         this.qa.put(var1, var5);
         this.qcmd.put(var1, var4);
      }
   }

   public void addClickableItemWithLore(int var1, ItemStack var2, String var3, String var4, String var5) {
      if (var1 >= 0) {
         ItemStack var6 = var2.clone();
         ItemMeta var7 = var6.getItemMeta();
         var7.setDisplayName(var3);
         var6.setItemMeta(var7);
         this.qa.put(var1, var6);
         this.lore.put(var1, var4);
         this.qcmd.put(var1, var5);
      }
   }

   public void addClickableItemWithLore(int var1, ItemStack var2, String var3, String var4, String var5, String var6) {
      if (var1 >= 0) {
         this.addClickableItemWithLore(var1, var2, var3, var4, var5);
         this.qperm.put(var1, var6);
      }
   }

   public void addPerm(int var1, String var2) {
      if (var1 >= 0) {
         this.qperm.put(var1, var2);
      }
   }

   public void addStaticItem(int var1, ItemStack var2, String var3) {
      if (var1 >= 0) {
         this.stati.put(var1, var2);
         this.qcmd.put(var1, var3);
      }
   }

   public void Click(Player var1, int var2, ClickTypes var3) {
      String var4 = (String)this.qcmd.get(var2);
      if (this.qcmd.get(var2) != null) {
         if (this.qperm.get(var2) != null && !P.has(var1, (String)this.qperm.get(var2))) {
            var1.sendMessage(L.get(var1, "System.CmdNoPermissionTrail"));
            var1.closeInventory();
            return;
         }

         ItemCommandReader.runCMD(var1, var4, var3);
      }

   }

   public void remove(int var1) {
      this.qa.remove(var1);
   }

   public MenuType getMenuType() {
      return this.mtype;
   }
}
