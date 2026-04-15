package me.saynt.supertrailspro.inventories;

import java.util.Iterator;
import java.util.Map.Entry;
import me.saynt.supertrailspro.P;
import me.saynt.supertrailspro.STUtils;
import me.saynt.supertrailspro.SuperTrails;
import me.saynt.supertrailspro.data.DataManager;
import me.saynt.supertrailspro.data.PlayerData;
import me.saynt.supertrailspro.lang.L;
import me.saynt.supertrailspro.lang.Language;
import me.saynt.supertrailspro.modules.SelectSounds;
import me.saynt.supertrailspro.modules.SoundType;
import me.saynt.supertrailspro.trails.TrailParticle;
import me.saynt.supertrailspro.trails.TrailsUtil;
import me.saynt.supertrailspro.trails.modes.Mode;
import me.saynt.supertrailspro.trails.modes.Modes;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GuiParticles extends Gui {
   GuiItem[] tr = new GuiItem[54];

   public GuiParticles() {
      super("Particles");
      this.i();
   }

   public String getName() {
      return L.get(this.p, "MenuNames.Particles");
   }

   public int getInventorySize() {
      return SuperTrails.p.getConfig().getInt("Options.Inventory.ParticlesMenuSize");
   }

   public void i() {
      Iterator var2 = TrailsUtil.ptr.entrySet().iterator();

      while(var2.hasNext()) {
         Entry var1 = (Entry)var2.next();
         if (var1.getKey() != null) {
            TrailParticle var3 = (TrailParticle)var1.getValue();
            if (var3 != null) {
               int var4 = var3.getSlot();
               if (var4 > 0 && var4 <= this.getInventorySize()) {
                  GuiItem var5 = new GuiItem(var3.getIS(), "ParticleMenu." + var3.getName(), "Menu.Lore");
                  this.tr[var4 - 1] = var5;
                  var5.cmd(var3);
               }
            }
         }
      }

   }

   public void loot() {
      Mode var1 = (Mode)Modes.listmodes.get(DataManager.getData(this.p).getMode());
      if (var1 == null) {
         var1 = (Mode)Modes.listmodes.get(0);
      }

      GuiItem[] var5;
      int var4 = (var5 = this.tr).length;

      for(int var3 = 0; var3 < var4; ++var3) {
         GuiItem var2 = var5[var3];
         if (var2 != null) {
            TrailParticle var6 = (TrailParticle)var2.command;
            if (this.p != null) {
               if (STUtils.optionb("HideNoPermissionTrails") && !P.has(this.p, "trails.particle." + var6.getName())) {
                  GuiItem var7 = new GuiItem(new ItemStack(Material.INK_SAC, 1, (short)8), "Menu.NoAccess");
                  this.setItem(var6.getSlot() - 1, var7);
               } else {
                  if (var1.isBlocked(var6.getId())) {
                     var2.addPlaceholder("!permission!", L.get(this.p, "Modes.ModeNotSupported"));
                  } else {
                     var2.addPlaceholder("!permission!", P.has(this.p, "trails.particle." + var6.getName()) ? L.get(this.p, "Menu.Access") : L.get(this.p, "Menu.NoAccess"));
                  }

                  this.setItem(var6.getSlot() - 1, var2);
               }
            }
         }
      }

      if (STUtils.optionb("Inventory.PaneLines.Particles")) {
         this.lines();
      }

      this.modechanger();
      this.backItem();
      if (STUtils.optionb("ResetButton.Particles")) {
         this.resetButton(51);
      }

   }

   public void modechanger() {
      if (SuperTrails.p.getConfig().getBoolean("Options.ModeChanger")) {
         try {
            GuiItem var1 = new GuiItem("Options.Inventory.ModesItem", "Menu.Modes");
            PlayerData var2 = DataManager.getData(this.p);
            if (var2.getMode() >= Modes.listmodes.size()) {
               var2.setMode(0);
            }

            Mode var3 = Modes.getNext(this.p);
            Mode var4 = Modes.getPrev(this.p);
            Mode var5 = (Mode)Modes.listmodes.get(DataManager.getData(this.p).getMode());
            if (var3 == null || var4 == null) {
               return;
            }

            var1.addPlaceholder("!prev", L.get(this.p, "Modes." + var4.getName()));
            var1.addPlaceholder("!next", L.get(this.p, "Modes." + var3.getName()));
            var1.addPlaceholder("!current", L.get(this.p, "Modes." + var5.getName()));
            var1.cmd(new Mode[]{var4, var3});
            this.setItem(4, var1);
         } catch (Exception var6) {
            var6.printStackTrace();
         }

      }
   }

   public void click(InventoryClickEvent var1) {
      int var2 = var1.getRawSlot();
      if (var2 >= 0 && var2 <= 53) {
         if (var2 == BACKITEM_SLOT) {
            GuiManager.open(this.p, "Main");
            return;
         }

         if (var2 == 51 && STUtils.optionb("ResetButton.Particles")) {
            DataManager.getData(this.p).setTrail(0);
            this.p.closeInventory();
            return;
         }

         GuiItem var3 = this.git[var2];
         if (var3 == null) {
            return;
         }

         if (var2 == 4 && SuperTrails.p.getConfig().getBoolean("Options.ModeChanger")) {
            Mode[] var7 = (Mode[])var3.command;
            if (var7 != null && var7.length == 2) {
               ClickType var8 = var1.getClick();
               PlayerData var6 = DataManager.getData(this.p);
               if (var8 == ClickType.RIGHT) {
                  var6.setMode(var7[1].id);
               } else {
                  var6.setMode(var7[0].id);
               }

               this.loot();
               this.updateInventory();
               return;
            }

            return;
         }

         TrailParticle var4 = (TrailParticle)var3.command;
         if (var4 == null) {
            return;
         }

         if (!P.has(this.p, "trails.particle." + var4.getName())) {
            L.msg(this.p, Language.NoPermission);
            return;
         }

         Mode var5 = (Mode)Modes.listmodes.get(DataManager.getData(this.p).getMode());
         if (var5.isBlocked(var4.getId())) {
            L.msg(this.p, "Modes.ModeNotSupported");
            return;
         }

         DataManager.getData(this.p).setTrail(var4.getId());
         this.p.closeInventory();
         SelectSounds.play(SoundType.Particles, this.p);
      }

   }
}
