package me.saynt.supertrailspro.inventories;

import me.saynt.supertrailspro.STUtils;
import me.saynt.supertrailspro.SuperTrails;
import me.saynt.supertrailspro.data.DataManager;
import me.saynt.supertrailspro.lang.L;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GuiMain extends Gui {
   int particleslot = this.slot("ParticlesSlot");
   int blocksslot = this.slot("BlocksSlot");
   int rainslot = this.slot("RainsSlot");
   int wingsslot = this.slot("WingsSlot");
   int removeslot = this.slot("RemoveItemSlot");
   int languageslot = this.slot("LanguageItem");
   int eventslot = this.slot("EventSlot");

   public GuiMain() {
      super("Main");
   }

   public int getInventorySize() {
      return this.slot("MainMenuSize");
   }

   public String getName() {
      return L.get(this.p, "MenuNames.Selector");
   }

   public int slot(String var1) {
      return SuperTrails.p.getConfig().getInt("Options.Inventory." + var1);
   }

   public void loot() {
      GuiItem var1 = (new GuiItem("Options.Inventory.ParticlesItem", "Menu.Particle")).cmd("Particles");
      GuiItem var2 = (new GuiItem("Options.Inventory.BlocksItem", "Menu.Block")).cmd("Blocks");
      GuiItem var3 = (new GuiItem("Options.Inventory.RainsItem", "Menu.Rain")).cmd("Rains");
      GuiItem var4 = (new GuiItem("Options.Inventory.WingsItem", "Menu.Wings")).cmd("Wings");
      GuiItem var5;
      if (STUtils.optionb("EventTrails")) {
         var5 = (new GuiItem(new ItemStack(Material.ENDER_CHEST), "Menu.Event")).cmd("Event");
         this.setItem(this.eventslot, var5);
      }

      var5 = new GuiItem("Options.Inventory.RemoveItem", "Menu.Clear");
      if (STUtils.optionb("LanguagesMenu")) {
         GuiItem var6 = (new GuiItem("Options.Inventory.LanguagesItemType", "Menu.Langs")).cmd("Languages");
         this.setItem(this.languageslot, var6);
      }

      this.setItem(this.particleslot, var1);
      this.setItem(this.blocksslot, var2);
      this.setItem(this.rainslot, var3);
      this.setItem(this.wingsslot, var4);
      this.setItem(this.removeslot, var5);
   }

   public void click(InventoryClickEvent var1) {
      int var2 = var1.getRawSlot();
      if (var2 >= 0 && var2 <= 53) {
         GuiItem var3 = this.git[var2];
         if (var3 == null) {
            return;
         }

         if (var2 == this.removeslot) {
            DataManager.getData(this.p).setTrail(0);
            this.p.closeInventory();
            return;
         }

         String var4 = (String)var3.command;
         if (var4 == null) {
            return;
         }

         GuiManager.open(this.p, var4);
      }

   }
}
