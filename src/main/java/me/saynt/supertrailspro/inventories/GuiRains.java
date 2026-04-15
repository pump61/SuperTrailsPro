package me.saynt.supertrailspro.inventories;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.saynt.supertrailspro.Heads;
import me.saynt.supertrailspro.P;
import me.saynt.supertrailspro.PluginMessages;
import me.saynt.supertrailspro.STUtils;
import me.saynt.supertrailspro.SuperTrails;
import me.saynt.supertrailspro.data.DataManager;
import me.saynt.supertrailspro.data.PlayerData;
import me.saynt.supertrailspro.eventtrails.EventColor;
import me.saynt.supertrailspro.eventtrails.EventMenu;
import me.saynt.supertrailspro.eventtrails.TrailEvent;
import me.saynt.supertrailspro.lang.L;
import me.saynt.supertrailspro.lang.Language;
import me.saynt.supertrailspro.modules.SelectSounds;
import me.saynt.supertrailspro.modules.SoundType;
import me.saynt.supertrailspro.trails.TrailType;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GuiRains extends Gui {
   int stage = 0;
   int color = 0;
   ItemStack item;
   public ItemStack[] iz = new ItemStack[36];
   int page = 0;
   int last_page = 0;

   public GuiRains() {
      super("Rains");
      this.init();
   }

   public String getName() {
      if (this.stage == 0) {
         return L.get(this.p, "MenuNames.Rain1");
      } else {
         return this.stage == 1 ? L.get(this.p, "MenuNames.Rain2") : " ";
      }
   }

   public void init() {
      List var1 = SuperTrails.p.getConfig().getStringList("Options.RainItems");
      ArrayList var2 = new ArrayList();
      Iterator var4 = var1.iterator();

      while(var4.hasNext()) {
         String var3 = (String)var4.next();
         String[] var8;
         int var7 = (var8 = var3.split(";")).length;

         for(int var6 = 0; var6 < var7; ++var6) {
            String var5 = var8[var6];
            var2.add(var5);
         }
      }

      this.iz = new ItemStack[var2.size()];

      for(int var9 = 0; var9 < var2.size(); ++var9) {
         ItemStack var10 = STUtils.readItemStack((String)var2.get(var9));
         if (var10 != null && var10.getType() != Material.AIR) {
            this.iz[var9] = var10;
         } else {
            this.iz[var9] = null;
         }
      }

      this.last_page = this.getPage(this.iz.length - 1);
      PluginMessages.Debug(this.iz.length + " itemstacks initialized");
   }

   public boolean hasNextPage() {
      return this.page < this.last_page;
   }

   public void loot() {
      this.resetItems();
      if (STUtils.optionb("Inventory.PaneLines.Particles")) {
         this.lines();
      }

      this.backItem();
      if (this.stage == 0) {
         this.openColor();
      } else if (this.stage == 1) {
         this.ItemList();
      }

   }

   public void nextStage() {
      this.stage = 1;
      this.open(this.p);
   }

   public void openColor() {
      GuiItem var1 = P.has(this.p, "trails.rain.color.white") ? (new GuiItem(Heads.hwhite.clone(), "RainAndWings.White")).cmd(1) : this.getBarrier();
      GuiItem var2 = P.has(this.p, "trails.rain.color.black") ? (new GuiItem(Heads.hblack.clone(), "RainAndWings.Black")).cmd(2) : this.getBarrier();
      GuiItem var3 = P.has(this.p, "trails.rain.color.red") ? (new GuiItem(Heads.hred.clone(), "RainAndWings.Red")).cmd(3) : this.getBarrier();
      GuiItem var4 = P.has(this.p, "trails.rain.color.yellow") ? (new GuiItem(Heads.hyellow.clone(), "RainAndWings.Yellow")).cmd(4) : this.getBarrier();
      GuiItem var5 = P.has(this.p, "trails.rain.color.green") ? (new GuiItem(Heads.hgreen.clone(), "RainAndWings.Green")).cmd(5) : this.getBarrier();
      GuiItem var6 = P.has(this.p, "trails.rain.color.blue") ? (new GuiItem(Heads.hblue.clone(), "RainAndWings.Blue")).cmd(6) : this.getBarrier();
      GuiItem var7 = P.has(this.p, "trails.rain.color.purple") ? (new GuiItem(Heads.hpurple.clone(), "RainAndWings.Purple")).cmd(7) : this.getBarrier();
      this.setItem(19, var1);
      this.setItem(20, var2);
      this.setItem(21, var3);
      this.setItem(22, var4);
      this.setItem(23, var5);
      this.setItem(24, var6);
      this.setItem(25, var7);
      if (EventMenu.enabled) {
         GuiItem var8 = new GuiItem(new ItemStack(Material.INK_SAC, 1, (short)8), "Event.Nothing.Name");
         int[] var12;
         int var11 = (var12 = new int[]{28, 29, 30, 31, 32, 33, 34}).length;

         for(int var10 = 0; var10 < var11; ++var10) {
            int var9 = var12[var10];
            this.setItem(var9, var8);
         }

         PlayerData var15 = DataManager.getData(this.p);
         List var16 = var15.getEventData().getUnlocked();
         Iterator var18 = var16.iterator();

         while(var18.hasNext()) {
            TrailEvent var17 = (TrailEvent)var18.next();
            if (var17.getType() == TrailType.Event_Color) {
               EventColor var13 = (EventColor)var17;
               GuiItem var14 = (new GuiItem(var17.getItemStack(), "Event.UnlockedColor.Name", "Event.UnlockedColor.Lore")).cmd(var13.getDataValue());
               var14.addPlaceholder("!r!", L.get(this.p, "Event.Rarity." + var13.getRarity().getName()));
               var14.evencolor = true;
               var14.color = var13;
               this.setItem(var13.getId() - 393, var14);
            }
         }
      }

   }

   public int getPage(int var1) {
      int var2 = var1 / 36;
      return var2;
   }

   public GuiItem getBarrier() {
      GuiItem var1 = new GuiItem(new ItemStack(Material.BARRIER), " ");
      var1.linked = false;
      return var1;
   }

   public void ItemList() {
      GuiItem var1;
      if (this.hasNextPage()) {
         var1 = new GuiItem(new ItemStack(Material.ARROW), Language.Next);
         this.setItem(53, var1);
      }

      if (this.page > 0) {
         var1 = new GuiItem(new ItemStack(Material.ARROW), Language.Previous);
         this.setItem(45, var1);
      }

      for(int var5 = 0; var5 < this.iz.length && this.getPage(var5) <= this.page; ++var5) {
         if (this.getPage(var5) == this.page) {
            ItemStack var2 = this.iz[var5];
            if (var2 != null) {
               boolean var3 = true;
               if (!P.has(this.p, "trails.rain.item." + ("" + var2.getType()).replaceAll("LEGACY_", "").toLowerCase())) {
                  var3 = false;
               }

               GuiItem var4 = new GuiItem(var2);
               this.setItem(var5 - this.page * 36 + 9, var3 ? var4 : this.getBarrier());
            }
         }
      }

   }

   public void click(InventoryClickEvent var1) {
      int var2 = var1.getRawSlot();
      if (this.git.length > var2) {
         GuiItem var3 = this.git[var2];
         if (var3 != null) {
            if (this.stage == 0) {
               if (var2 == BACKITEM_SLOT) {
                  GuiManager.open(this.p, "Main");
                  return;
               }

               if (var3.command == null) {
                  return;
               }

               int var4 = (Integer)var3.command;
               this.color = var4;
               this.nextStage();
            } else {
               if (var2 == BACKITEM_SLOT) {
                  this.stage = 0;
                  this.open(this.p);
                  return;
               }

               if (var2 == 53 && this.hasNextPage()) {
                  ++this.page;
                  this.loot();
                  this.updateInventory();
                  return;
               }

               if (var2 == 45 && this.page > 0) {
                  --this.page;
                  this.loot();
                  this.updateInventory();
                  return;
               }

               if (var2 > 8 && var2 < 45) {
                  this.item = var1.getCurrentItem();
                  if (this.item != null && this.item.getType() == Material.BARRIER) {
                     L.msg(this.p, "System.CmdNoPermissionTrail");
                     this.p.closeInventory();
                     return;
                  }

                  PlayerData var5 = DataManager.getData(this.p);
                  var5.setRain(this.item, this.color);
                  this.p.closeInventory();
                  var5.save();
                  SelectSounds.play(SoundType.Rains, this.p);
               }
            }

         }
      }
   }
}
