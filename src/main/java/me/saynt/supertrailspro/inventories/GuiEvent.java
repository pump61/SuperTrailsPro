package me.saynt.supertrailspro.inventories;

import java.util.Iterator;
import java.util.Map.Entry;
import me.saynt.supertrailspro.PluginMessages;
import me.saynt.supertrailspro.STUtils;
import me.saynt.supertrailspro.data.DataManager;
import me.saynt.supertrailspro.data.EventData;
import me.saynt.supertrailspro.data.PlayerData;
import me.saynt.supertrailspro.eventtrails.ColoredCircle;
import me.saynt.supertrailspro.eventtrails.ConfettiTrail;
import me.saynt.supertrailspro.eventtrails.EventColor;
import me.saynt.supertrailspro.eventtrails.EventTrails;
import me.saynt.supertrailspro.eventtrails.EventUtils;
import me.saynt.supertrailspro.eventtrails.Rarity;
import me.saynt.supertrailspro.eventtrails.TrailEvent;
import me.saynt.supertrailspro.eventtrails.fairy.TrailFairy;
import me.saynt.supertrailspro.lang.L;
import me.saynt.supertrailspro.lang.Language;
import me.saynt.supertrailspro.modules.SoundsConverter;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GuiEvent extends Gui {
   int stage = 0;
   PlayerData pd;
   EventData ed;
   long l = 0L;

   public GuiEvent() {
      super("Event");
   }

   public String getName() {
      return L.get(this.p, "MenuNames.Event");
   }

   public void loot() {
      this.pd = DataManager.getData(this.p);
      this.ed = this.pd.getEventData();
      if (!this.pd.isLoaded()) {
         this.Error();
      } else if (this.stage != 0 && this.stage != 1) {
         if (this.stage == 10) {
            this.chestopen();
         }
      } else {
         this.page1();
      }
   }

   public void Error() {
      GuiItem var1 = new GuiItem(new ItemStack(Material.REDSTONE_BLOCK), Language.DatabaseError);

      for(int var2 = 0; var2 < 54; ++var2) {
         this.setItem(var2, var1);
      }

      this.stage = -1;
   }

   public GuiItem getEmpty() {
      return new GuiItem(new ItemStack(Material.INK_SAC, 1, (short)8), "Event.Nothing.Name", "Event.Nothing.Lore");
   }

   public void page1() {
      this.eventItems();
      Iterator var2 = EventTrails.i.entrySet().iterator();

      while(true) {
         while(true) {
            Entry var1;
            int var3;
            do {
               do {
                  if (!var2.hasNext()) {
                     GuiItem var8;
                     if (this.stage == 0) {
                        var8 = new GuiItem(new ItemStack(Material.ARROW), Language.Next);
                        this.setItem(53, var8);
                     }

                     if (this.stage == 1) {
                        var8 = new GuiItem(new ItemStack(Material.ARROW), Language.Previous);
                        this.setItem(45, var8);
                     }

                     this.backItem();
                     return;
                  }

                  var1 = (Entry)var2.next();
                  var3 = (Integer)var1.getKey();
               } while(this.stage == 0 && var3 > 430);
            } while(this.stage == 1 && var3 < 430);

            TrailEvent var4 = (TrailEvent)var1.getValue();
            GuiItem var5 = new GuiItem(var4.getItemStack(), "Event." + var4.getName() + ".Name", "Event." + var4.getName() + ".Lore");
            var5.addPlaceholder("!r!", L.get(this.p, "Event.Rarity." + var4.getRarity().getName()));
            var5.cmd(var4.getId());
            if (var3 >= 401 && var3 <= 407) {
               ConfettiTrail var9 = (ConfettiTrail)var4;
               int var12 = var3 - 391;
               if (!this.ed.isUnlocked(var3)) {
                  this.setItem(var12, this.getEmpty());
               } else {
                  var5.addPlaceholder("!color1!", L.get(this.p, "RainAndWings." + var9.getColors()[0].getName()));
                  var5.addPlaceholder("!color2!", L.get(this.p, "RainAndWings." + var9.getColors()[1].getName()));
                  var5.addPlaceholder("!color3!", L.get(this.p, "RainAndWings." + var9.getColors()[2].getName()));
                  this.setItem(var12, var5);
               }
            } else {
               int var6;
               if (var3 >= 411 && var3 <= 417) {
                  var6 = var3 - 392;
                  if (!this.ed.isUnlocked(var3)) {
                     this.setItem(var6, this.getEmpty());
                  } else {
                     ColoredCircle var11 = (ColoredCircle)var4;
                     var5.addPlaceholder("!color!", L.get(this.p, "RainAndWings." + var11.getColor().getName()));
                     this.setItem(var6, var5);
                  }
               } else if (var3 >= 421 && var3 <= 427) {
                  var6 = var3 - 393;
                  if (!this.ed.isUnlocked(var3)) {
                     this.setItem(var6, this.getEmpty());
                  } else {
                     EventColor var10 = (EventColor)var4;
                     var5.addPlaceholder("!color!", L.get(this.p, "RainAndWings." + var10.getColor().getName()));
                     this.setItem(var6, var5);
                  }
               } else if (var3 >= 431 && var3 <= 437) {
                  var6 = var3 - 421;
                  if (!this.ed.isUnlocked(var3)) {
                     this.setItem(var6, this.getEmpty());
                  } else {
                     TrailFairy var7 = (TrailFairy)var4;
                     var5.addPlaceholder("!color!", L.get(this.p, "RainAndWings." + var7.getColor().getName()));
                     this.setItem(var6, var5);
                  }
               }
            }
         }
      }
   }

   public void chestopen() {
      GuiItem var1 = new GuiItem(new ItemStack(Material.CHEST), "Event.OpenChest.Name", "Event.OpenChest.Lore");

      for(int var2 = 0; var2 <= 53; ++var2) {
         this.setItem(var2, var1);
      }

   }

   public void chestopened(int var1) {
      GuiItem var2 = (new GuiItem(new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1, (short)15), " ")).linked(false);

      for(int var3 = 0; var3 <= 53; ++var3) {
         this.setItem(var3, var2);
      }

      TrailEvent var6 = EventUtils.getRandom();
      if (var6 == null) {
         PluginMessages.Error("EventTrail is NULL. Kinda impossible but you did it!");
      } else {
         GuiItem var4 = new GuiItem(var6.getItemStack(), "Event." + var6.getName() + ".Name", "Event." + var6.getName() + ".Lore");
         EventUtils.addplaceholders(var6, var4, this.p);
         this.ed.addChests(-1);
         if (this.ed.isUnlocked(var6)) {
            var4.addAdditionalLore("Event.Points.LoreAdd");
            int var5 = 0;
            if (var6.getRarity() == Rarity.COMMON) {
               var5 = STUtils.r(5, 30);
            } else if (var6.getRarity() == Rarity.RARE) {
               var5 = STUtils.r(20, 60);
            } else if (var6.getRarity() == Rarity.EPIC) {
               var5 = STUtils.r(50, 250);
            }

            var4.addPlaceholder("!points!", String.valueOf(var5));
            this.ed.addPoints(var5);
         } else {
            this.ed.add(var6.getId());
         }

         this.p.playSound(this.p.getLocation(), SoundsConverter.CHEST_OPEN.toSound(), 1.0F, 1.0F);
         if (var6.getRarity() == Rarity.RARE) {
            this.p.playSound(this.p.getLocation(), SoundsConverter.LEVEL_UP.toSound(), 1.0F, 1.0F);
         }

         if (var6.getRarity() == Rarity.EPIC) {
            this.p.playSound(this.p.getLocation(), SoundsConverter.ORB_PICKUP.toSound(), 1.0F, 1.0F);
         }

         this.ed.save();
         this.setItem(var1, var4);
         this.updateInventory();
      }
   }

   public void eventItems() {
      if (STUtils.optionb("Inventory.PaneLines.Event")) {
         this.lines();
      }

      GuiItem var1 = new GuiItem(new ItemStack(Material.ENDER_CHEST), "Event.Chest.Name", "Event.Chest.Lore");
      var1.addPlaceholder("!a!", String.valueOf(this.ed.getChests()));
      var1.addPlaceholder("!open!", this.ed.getChests() > 0 ? L.get(this.p, "Event.Chest.Open") : L.get(this.p, "Event.Chest.CantOpen"));
      this.setItem(4, var1);
      GuiItem var2 = new GuiItem(new ItemStack(Material.PAPER), "Event.PointsItem.Name", "Event.PointsItem.Lore");
      var2.addPlaceholder("!points!", String.valueOf(this.ed.getPoints())).addPlaceholder("!convert!", this.ed.getPoints() >= 100 ? L.get(this.p, "Event.PointsItem.CanConvert") : L.get(this.p, "Event.PointsItem.CantConvert"));
      this.setItem(50, var2);
      this.backItem();
   }

   public void convertPoints() {
      if (this.ed.getConvertableAmount() != 0) {
         this.ed.convertToChests(this.p);
         this.open(this.p);
      }
   }

   public void click(InventoryClickEvent var1) {
      int var2 = var1.getRawSlot();
      if (this.stage <= 2) {
         if (var2 == BACKITEM_SLOT) {
            GuiManager.open(this.p, "Main");
            return;
         }

         if (var2 == 4) {
            long var5 = System.currentTimeMillis();
            if (this.ed.getChests() == 0) {
               return;
            }

            if (this.l > var5 - 2000L) {
               L.msg(this.p, "Event.Messages.Wait");
               return;
            }

            this.l = var5;
            this.stage = 10;
            this.open(this.p);
            return;
         }

         if (var2 >= this.git.length) {
            return;
         }

         GuiItem var3 = this.git[var2];
         if (var3.command != null) {
            int var4 = (Integer)var3.command;
            if (this.ed.isUnlocked(var4)) {
               this.pd.setTrail(var4);
               this.p.closeInventory();
            }

            return;
         }

         if (var2 == 53 && this.stage == 0) {
            this.stage = 1;
            this.open(this.p);
            return;
         }

         if (var2 == 45 && this.stage == 1) {
            this.stage = 0;
            this.open(this.p);
            return;
         }

         if (var2 == 50) {
            this.convertPoints();
            this.eventItems();
            this.updateInventory();
         }
      } else {
         if (this.stage == 10) {
            if (var2 > 53) {
               return;
            }

            this.chestopened(var2);
            this.stage = 11;
            return;
         }

         if (this.stage == 11) {
            this.stage = 0;
            this.open(this.p);
            return;
         }
      }

   }
}
