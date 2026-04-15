package me.saynt.supertrailspro.inventories;

import java.util.Iterator;
import java.util.Map.Entry;
import me.saynt.supertrailspro.P;
import me.saynt.supertrailspro.PluginMessages;
import me.saynt.supertrailspro.STUtils;
import me.saynt.supertrailspro.SuperTrails;
import me.saynt.supertrailspro.data.DataManager;
import me.saynt.supertrailspro.lang.L;
import me.saynt.supertrailspro.modules.SelectSounds;
import me.saynt.supertrailspro.modules.SoundType;
import me.saynt.supertrailspro.trails.TrailBlocks;
import me.saynt.supertrailspro.trails.TrailsUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GuiBlocks extends Gui {
   GuiItem[] blocks = new GuiItem[54];

   public GuiBlocks() {
      super("Blocks");
      this.setup();
   }

   public String getName() {
      return L.get(this.p, "MenuNames.Blocks");
   }

   public int getInventorySize() {
      return SuperTrails.p.getConfig().getInt("Options.Inventory.BlocksMenuSize");
   }

   public void setup() {
      Iterator var2 = TrailsUtil.pbl.entrySet().iterator();

      while(var2.hasNext()) {
         Entry var1 = (Entry)var2.next();
         int var3 = (Integer)var1.getKey();
         TrailBlocks var4 = (TrailBlocks)var1.getValue();
         if (var4 != null && var4.getSlot() >= 0 && var4.getSlot() < this.getInventorySize()) {
            GuiItem var5 = new GuiItem(var4.getItem(), "BlockMenu." + var4.getName() + ".Name", "BlockMenu." + var4.getName() + ".Lore");
            this.blocks[var4.getSlot()] = var5;
            var5.cmd(var4);
            PluginMessages.Debug("Blocks > " + var4.getName() + "  slot" + var4.getSlot());
         }
      }

   }

   public void loot() {
      GuiItem[] var4;
      int var3 = (var4 = this.blocks).length;

      for(int var2 = 0; var2 < var3; ++var2) {
         GuiItem var1 = var4[var2];
         if (var1 != null) {
            TrailBlocks var5 = (TrailBlocks)var1.command;
            if (var5 != null) {
               if (STUtils.optionb("HideNoPermissionTrails") && !P.has(this.p, "trails.block." + var5.getName())) {
                  GuiItem var6 = new GuiItem(new ItemStack(Material.INK_SAC, 1, (short)8), "Menu.NoAccess");
                  this.setItem(var5.getSlot(), var6);
               } else {
                  var1.addPlaceholder("!permission!", P.has(this.p, "trails.block." + var5.getName()) ? L.get(this.p, "Menu.Access") : L.get(this.p, "Menu.NoAccess"));
                  this.setItem(var5.getSlot(), var1);
               }
            }
         }
      }

      if (STUtils.optionb("Inventory.PaneLines.Blocks")) {
         this.lines();
      }

      this.backItem();
      if (STUtils.optionb("ResetButton.Blocks")) {
         this.resetButton(51);
      }

   }

   public void click(InventoryClickEvent var1) {
      int var2 = var1.getRawSlot();
      if (var2 == BACKITEM_SLOT) {
         GuiManager.open(this.p, "Main");
      } else if (var2 == 51 && STUtils.optionb("ResetButton.Blocks")) {
         DataManager.getData(this.p).setTrail(0);
         this.p.closeInventory();
      } else {
         if (var2 >= 0 && var2 <= 53) {
            GuiItem var3 = this.git[var2];
            if (var3 == null) {
               return;
            }

            TrailBlocks var4 = (TrailBlocks)var3.command;
            if (var4 == null) {
               return;
            }

            if (P.has(this.p, "trails.block." + var4.getName())) {
               DataManager.getData((Player)var1.getWhoClicked()).setTrail(var4.getId());
               this.p.closeInventory();
               SelectSounds.play(SoundType.Blocks, this.p);
            }
         }

      }
   }
}
