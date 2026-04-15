package me.saynt.supertrailspro.inventories;

import me.saynt.supertrailspro.P;
import me.saynt.supertrailspro.SuperTrails;
import me.saynt.supertrailspro.lang.L;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class Gui implements Cloneable {
   boolean linkedName = true;
   String name;
   String id;
   Inventory inv;
   GuiItem[] git = new GuiItem[54];
   Player p;
   boolean cancel = true;
   protected static int BACKITEM_SLOT;
   public String openPermission;
   public String noPermissionMessage;
   Inventory ays = null;

   static {
      BACKITEM_SLOT = SuperTrails.p.getConfig().getInt("Options.Inventory.BackItemSlot");
   }

   public Gui(String var1) {
      this.id = var1;
   }

   public String getName() {
      return this.linkedName ? L.get(this.p, "MenuNames." + this.name) : this.name;
   }

   public Gui(String var1, String var2) {
      this.name = var2;
   }

   public Gui clone() {
      try {
         return (Gui)super.clone();
      } catch (CloneNotSupportedException var2) {
         var2.printStackTrace();
         return null;
      }
   }

   public void resetItems() {
      this.git = new GuiItem[54];
   }

   public int getInventorySize() {
      return 54;
   }

   public void open(Player var1) {
      this.p = var1;
      if (this.openPermission != null && !P.has(var1, this.openPermission)) {
         L.msg(var1, this.noPermissionMessage);
      } else {
         this.resetItems();
         this.loot();
         this.inv = Bukkit.createInventory((InventoryHolder)null, this.getInventorySize(), this.getName());
         this.updateInventory(false);
         var1.openInventory(this.inv);
         GuiManager.open.put(var1, this);
      }
   }

   public void updateInventory() {
      this.updateInventory(true);
   }

   public void updateInventory(boolean var1) {
      ItemStack[] var2 = new ItemStack[this.getInventorySize()];

      for(int var3 = 0; var3 < this.git.length; ++var3) {
         if (var3 < var2.length) {
            var2[var3] = this.git[var3] == null ? null : this.git[var3].generate(this.p);
         }
      }

      this.inv.setContents(var2);
      if (var1) {
         this.p.updateInventory();
      }

   }

   public void setPermission(String var1, String var2) {
      this.openPermission = var1;
      this.noPermissionMessage = var2;
   }

   public String getPermission() {
      return this.openPermission;
   }

   public String getPermissionMessageLink() {
      return this.noPermissionMessage;
   }

   public void lines() {
      this.upline();
      this.downline();
   }

   public void areyousure() {
      this.resetItems();
      GuiItem var1 = new GuiItem(new ItemStack(Material.OAK_SIGN), "Menu.AYS.SignItem.Name", "Menu.AYS.SignItem.Lore");
      GuiItem var2 = new GuiItem(new ItemStack(Material.EMERALD_BLOCK), "Menu.AYS.YesItem.Name", "Menu.AYS.YesItem.Lore");
      GuiItem var3 = new GuiItem(new ItemStack(Material.REDSTONE_BLOCK), "Menu.AYS.NoItem.Name", "Menu.AYS.NoItem.Lore");
      this.setItem(13, var1);
      this.setItem(29, var3);
      this.setItem(33, var2);
      this.recreateInventory();
      this.ays = this.inv;
   }

   public boolean isAYSDialouge() {
      return this.ays == null ? false : this.ays.equals(this.inv);
   }

   public void AYSConfirmEvent() {
   }

   public void AYSDenyEvent() {
   }

   public void recreateInventory() {
      this.inv = Bukkit.createInventory((InventoryHolder)null, 54, this.getName());
      this.updateInventory(false);
      this.p.openInventory(this.inv);
      GuiManager.open.put(this.p, this);
   }

   public void backItem(int var1) {
      GuiItem var2 = new GuiItem("Options.Inventory.BackItem", "Menu.Back");
      this.setItem(var1, var2);
   }

   public void backItem() {
      this.backItem(BACKITEM_SLOT);
   }

   public void resetButton(int var1) {
      GuiItem var2 = new GuiItem("Options.Inventory.RemoveItem", "Menu.Clear");
      this.setItem(var1, var2);
   }

   public void upline() {
      GuiItem var1 = new GuiItem(new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1, (short)15), " ");

      for(int var2 = 0; var2 < 9; ++var2) {
         this.setItem(var2, var1);
      }

   }

   public void downline() {
      GuiItem var1 = new GuiItem(new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1, (short)15), " ");

      for(int var2 = 45; var2 < 54; ++var2) {
         this.setItem(var2, var1);
      }

   }

   public void leftline() {
      GuiItem var1 = new GuiItem(new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1, (short)15), " ");

      for(int var2 = 0; var2 < 6; ++var2) {
         this.setItem(9 * var2, var1);
      }

   }

   public void rightline() {
      GuiItem var1 = new GuiItem(new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1, (short)15), " ");

      for(int var2 = 0; var2 < 6; ++var2) {
         this.setItem(9 * var2 + 8, var1);
      }

   }

   public void setItem(int var1, GuiItem var2) {
      if (var1 >= 0) {
         if (var1 < this.git.length) {
            this.git[var1] = var2;
         }
      }
   }

   public void clickA(InventoryClickEvent var1) {
      if (this.cancel) {
         var1.setCancelled(true);
      }

      if (this.isAYSDialouge()) {
         if (var1.getRawSlot() == 33) {
            this.AYSConfirmEvent();
         } else if (var1.getRawSlot() == 29) {
            this.AYSDenyEvent();
         }

      } else {
         this.click(var1);
         Player var2 = (Player)var1.getWhoClicked();
         var2.updateInventory();
      }
   }

   public void click(InventoryClickEvent var1) {
   }

   public void loot() {
   }
}
