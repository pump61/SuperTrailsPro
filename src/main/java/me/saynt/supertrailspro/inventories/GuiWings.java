package me.saynt.supertrailspro.inventories;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import me.saynt.supertrailspro.ColoredHeads;
import me.saynt.supertrailspro.Heads;
import me.saynt.supertrailspro.P;
import me.saynt.supertrailspro.PluginMessages;
import me.saynt.supertrailspro.STUtils;
import me.saynt.supertrailspro.data.DataManager;
import me.saynt.supertrailspro.data.Keeper;
import me.saynt.supertrailspro.data.PlayerData;
import me.saynt.supertrailspro.eventtrails.EventColor;
import me.saynt.supertrailspro.eventtrails.EventMenu;
import me.saynt.supertrailspro.eventtrails.TrailEvent;
import me.saynt.supertrailspro.lang.L;
import me.saynt.supertrailspro.modules.SelectSounds;
import me.saynt.supertrailspro.modules.SoundType;
import me.saynt.supertrailspro.trails.TrailType;
import me.saynt.supertrailspro.trails.TrailWings;
import me.saynt.supertrailspro.trails.WingsPattern;
import me.saynt.supertrailspro.wings.WingsManager;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GuiWings extends Gui {
   int stage = 0;
   TrailWings select_wings;
   int select_id;
   boolean select_pattern;
   int select_c1;
   int select_c2;
   int select_c3;
   WingsPattern selected_pattern;
   int keeper = 0;
   int page = 0;
   int last_page_wings = 0;
   int last_pat_page = 0;
   int pat_page = 0;
   int keeperEdit = -1;

   public GuiWings() {
      super("Wings");
   }

   public String getName() {
      if (this.stage == 0) {
         return L.get(this.p, "MenuNames.Wings1");
      } else if (this.stage == 1) {
         return L.get(this.p, "MenuNames.Wings2");
      } else if (this.stage == 2) {
         return L.get(this.p, "MenuNames.Wings3");
      } else if (this.stage == 3) {
         return L.get(this.p, "MenuNames.Wings4");
      } else if (this.stage == 4) {
         return L.get(this.p, "MenuNames.Wings5");
      } else {
         return this.stage == 5 ? L.get(this.p, "MenuNames.WingsPattern") : L.get(this.p, "MenuNames.Wings");
      }
   }

   public void loot() {
      this.git = new GuiItem[54];
      if (this.stage == 0) {
         this.wingslist(true);
      } else if (this.stage == 1) {
         this.CoP();
      } else if (this.stage >= 2 && this.stage <= 4) {
         this.openColor();
      } else if (this.stage == 5) {
         this.patternlist();
      }

   }

   public void wingslist(boolean var1) {
      this.downline();
      if (this.keeperEdit == -1 && P.has(this.p, "trails.keeper") && DataManager.getData(this.p).isLoaded()) {
         this.keeper();
      }

      Iterator var3 = WingsManager.wings.entrySet().iterator();

      while(true) {
         while(true) {
            TrailWings var4;
            int var5;
            do {
               if (!var3.hasNext()) {
                  GuiItem var8;
                  if (this.page == 0) {
                     this.backItem(45);
                  } else {
                     var8 = new GuiItem(new ItemStack(Material.ARROW), "Menu.Prev");
                     this.setItem(45, var8);
                  }

                  if (this.last_page_wings > this.page) {
                     var8 = new GuiItem(new ItemStack(Material.ARROW), "Menu.Next");
                     this.setItem(53, var8);
                  }

                  return;
               }

               Entry var2 = (Entry)var3.next();
               var4 = (TrailWings)var2.getValue();
               var5 = var4.slot;
               this.updateW(var5);
            } while(!this.inRange(var5, this.page));

            GuiItem var6 = new GuiItem(var4.is, "Wings." + var4.getName(), "WingsLore." + var4.getName());
            if (STUtils.optionb("HideNoPermissionTrails") && !P.has(this.p, "trails.wings." + var4.getName())) {
               GuiItem var7 = new GuiItem(new ItemStack(Material.INK_SAC, 1, (short)8), "System.NoWingsPermission");
               this.setItem(var4.slot - this.page * 45, var7);
            } else {
               this.setItem(var4.slot - this.page * 45, var6);
               var6.command = var4;
               var6.setPermission("trails.wings." + var4.getName());
               var6.addPlaceholder("!permission!", P.gePermissionsMessageLink(this.p, "trails.wings." + var4.getName(), "Menu.Access", "Menu.NoPermission"));
            }
         }
      }
   }

   public void updateW(int var1) {
      int var2 = this.getPage(var1);
      if (var2 > this.last_page_wings) {
         this.last_page_wings = var2;
      }

   }

   public void updateP(int var1) {
      int var2 = this.getPage(var1);
      if (var2 > this.last_pat_page) {
         this.last_pat_page = var2;
      }

   }

   public int getPage(int var1) {
      int var2 = var1 / 45;
      return var2;
   }

   public boolean inRange(int var1, int var2) {
      return this.getPage(var1) == var2;
   }

   public void keeper() {
      PlayerData var1 = DataManager.getData(this.p);
      if (var1.isLoaded()) {
         for(int var2 = 47; var2 < 52; ++var2) {
            if (this.keeper == 0) {
               Keeper var3 = var1.getKeeper(var2 - 47);
               this.setItem(var2, var3.getItem(this.p));
            } else if (this.keeper == var2 - 46) {
               this.setItem(var2, new GuiItem(new ItemStack(Material.GOLDEN_PICKAXE), "Menu.Keeper.BuildIt.Name", "Menu.Keeper.BuildIt.Lore"));
            } else {
               this.setItem(var2, (new GuiItem(new ItemStack(Material.BEDROCK), " ")).linked(false));
            }
         }

      }
   }

   public void patternlist() {
      Iterator var2 = WingsManager.patterns.entrySet().iterator();

      while(true) {
         while(true) {
            WingsPattern var3;
            int var4;
            do {
               if (!var2.hasNext()) {
                  this.downline();
                  GuiItem var7;
                  if (this.pat_page != 0) {
                     var7 = new GuiItem(new ItemStack(Material.ARROW), "Menu.Prev");
                     this.setItem(45, var7);
                  }

                  if (this.last_pat_page > this.pat_page) {
                     var7 = new GuiItem(new ItemStack(Material.ARROW), "Menu.Next");
                     this.setItem(53, var7);
                  }

                  return;
               }

               Entry var1 = (Entry)var2.next();
               var3 = (WingsPattern)var1.getValue();
               var4 = var3.getSlot();
               this.updateP(var4);
            } while(this.getPage(var4) != this.pat_page);

            GuiItem var5 = new GuiItem(var3.getItem(), "Pattern." + var3.getName(), "PatternLore." + var3.getName());
            if (STUtils.optionb("HideNoPermissionTrails") && !P.has(this.p, "trails.pattern." + var3.getName())) {
               GuiItem var6 = new GuiItem(new ItemStack(Material.INK_SAC, 1, (short)8), "System.NoPatternPermission");
               this.setItem(var3.getSlot() - this.pat_page * 45, var6);
            } else {
               this.setItem(var3.getSlot() - this.pat_page * 45, var5);
               var5.command = var3;
               var5.setPermission("trails.pattern." + var3.getName());
               var5.addPlaceholder("!permission!", P.gePermissionsMessageLink(this.p, "trails.pattern." + var3.getName(), "Menu.Access", "Menu.NoPermission"));
            }
         }
      }
   }

   public void CoP() {
      GuiItem var1 = new GuiItem(new ItemStack(Material.COD, 1, (short)3), "RainAndWings.Pattern");
      GuiItem var2 = new GuiItem(Heads.hred, "RainAndWings.Color");
      this.setItem(21, var2);
      this.setItem(23, var1);
      this.downline();
   }

   public void AYSConfirmEvent() {
      PlayerData var1 = DataManager.getData(this.p);
      Keeper var2 = var1.getKeeper(this.keeperEdit);
      var2.reset();
      this.leaveKeeper();
      this.stage = 0;
      this.open(this.p);
      this.save();
   }

   public void AYSDenyEvent() {
      this.ays = null;
      this.editKeeper(this.keeperEdit + 47);
   }

   public void getNextStage() {
      if (this.keeperEdit != -1) {
         this.editKeeper(this.keeperEdit + 47);
      }

      if (this.stage == 0) {
         if (this.select_wings.hasAnyColor()) {
            this.stage = 1;
            this.open(this.p);
         } else {
            this.end();
         }
      } else if (this.stage == 1) {
         if (this.select_wings.hasColor1()) {
            this.stage = 2;
            this.open(this.p);
         } else if (this.select_wings.hasColor2()) {
            this.stage = 3;
            this.open(this.p);
         } else if (this.select_wings.hasColor3()) {
            this.stage = 4;
            this.open(this.p);
         } else {
            this.end();
         }
      } else if (this.stage == 2) {
         if (this.select_wings.hasColor2()) {
            this.stage = 3;
            this.open(this.p);
         } else if (this.select_wings.hasColor3()) {
            this.stage = 4;
            this.open(this.p);
         } else {
            this.end();
         }
      } else if (this.stage == 3) {
         if (this.select_wings.hasColor3()) {
            this.stage = 4;
            this.open(this.p);
         } else {
            this.end();
         }
      } else if (this.stage == 4) {
         this.end();
      } else if (this.stage == 5) {
         this.end();
      }

   }

   public void end() {
      PlayerData var1 = DataManager.getData(this.p);
      if (this.keeper == 0) {
         if (!this.select_pattern) {
            if (this.select_c1 == 0) {
               this.select_c1 = 1;
            }

            if (this.select_c2 == 0) {
               this.select_c2 = 1;
            }

            if (this.select_c3 == 0) {
               this.select_c3 = 1;
            }

            var1.setWings(this.select_wings.getId(), this.select_c1, this.select_c2, this.select_c3);
            SelectSounds.play(SoundType.Wings, this.p);
            this.p.closeInventory();
            this.save();
         } else {
            var1.setPattenWings(this.select_wings.getId(), this.selected_pattern.getName());
            SelectSounds.play(SoundType.Wings, this.p);
            this.p.closeInventory();
            this.save();
         }
      } else {
         Keeper var2 = var1.getKeeper(this.keeper - 1);
         var2.setA(this.select_wings.getId());
         var2.setB(this.select_c1);
         var2.setC(this.select_c2);
         var2.setD(this.select_c3);
         if (this.selected_pattern != null) {
            var2.setE(this.selected_pattern.getName());
         }

         this.keeper = 0;
         this.stage = 0;
         this.resetGui();
         this.save();
         this.open(this.p);
      }

   }

   public void openKeeper(int var1) {
      this.stage = 10;
      this.open(this.p);
   }

   public void updateKeeper() {
      PlayerData var1 = DataManager.getData(this.p);
      Keeper var2 = var1.getKeeper(this.keeperEdit);
      int var3 = this.select_wings == null ? 0 : this.select_wings.getId();
      String var4 = this.selected_pattern == null ? null : this.selected_pattern.getName();
      var2.setA(var3);
      var2.setB(this.select_c1);
      var2.setC(this.select_c2);
      var2.setD(this.select_c3);
      var2.setE(var4);
   }

   public void editKeeper(int var1) {
      this.resetItems();
      this.stage = 10;
      boolean var2 = false;
      PlayerData var3 = DataManager.getData(this.p);
      Keeper var4 = var3.getKeeper(var1 - 47);
      this.lines();
      this.leftline();
      this.rightline();
      this.setItem(4, var4.getItem(this.p));
      TrailWings var5 = var4.getWings();
      WingsPattern var6 = var4.getPattern();
      int var7 = var4.getColor(0);
      int var8 = var4.getColor(1);
      int var9 = var4.getColor(2);
      if (this.keeperEdit == -1) {
         this.keeperEdit = var1 - 47;
         var2 = true;
         this.select_wings = var5;
         this.selected_pattern = var6;
         this.select_c1 = var7;
         this.select_c2 = var8;
         this.select_c3 = var9;
      }

      ItemStack var10 = this.select_wings != null && this.select_wings.is != null ? this.select_wings.is : new ItemStack(Material.STONE_BUTTON);
      String var11 = this.select_wings == null ? "Menu.Keeper.Undefined" : "Wings." + this.select_wings.getName();
      GuiItem var12 = (new GuiItem(var10, "Menu.Keeper.WingsChange.Name", "Menu.Keeper.WingsChange.Lore")).addPlaceholder("!wingsname!", L.get(this.p, var11));
      GuiItem[] var13 = this.genColorIS(this.select_c1, this.select_c2, this.select_c3);
      GuiItem var14 = this.genPatIS();
      GuiItem var15 = new GuiItem(new ItemStack(Material.LAVA_BUCKET), "Menu.Keeper.Remove.Name", "Meu.Keeper.Remove.Lore");
      GuiItem var16 = new GuiItem(new ItemStack(Material.WHITE_STAINED_GLASS, 1, (short)14), "Menu.Keeper.Cancel.Name", "Menu.Keeper.Cancel.Lore");
      GuiItem var17 = new GuiItem(new ItemStack(Material.EMERALD_BLOCK), "Menu.Keeper.Save.Name", "Menu.Keeper.Save.Lore");
      this.setItem(20, var12);
      this.setItem(22, var13[0]);
      this.setItem(23, var13[1]);
      this.setItem(24, var13[2]);
      this.setItem(32, var14);
      this.setItem(46, var16);
      this.setItem(49, var17);
      this.setItem(52, var15);
      this.updateInventory();
   }

   public boolean hasNextPage() {
      return this.last_page_wings > this.page;
   }

   public boolean hasNextPatternPage() {
      return this.last_pat_page > this.pat_page;
   }

   public GuiItem genPatIS() {
      if (this.selected_pattern != null && this.selected_pattern.getItem() != null) {
         String var1 = this.selected_pattern == null ? "Menu.Keeper.Undefined" : "Pattern." + this.selected_pattern.getName();
         GuiItem var2 = (new GuiItem(this.selected_pattern.getItem(), "Menu.Keeper.PatternChange.Name", "Menu.Keeper.PatternChange.Lore")).addPlaceholder("!pattern!", L.get(this.p, var1));
         return var2;
      } else {
         return (new GuiItem(new ItemStack(Material.COD, 1, (short)3), "Menu.Keeper.PatternChange.Name", "Menu.Keeper.PatternChange.Lore")).addPlaceholder("!pattern!", L.get(this.p, "Menu.Keeper.Undefined"));
      }
   }

   public GuiItem[] genColorIS(int... var1) {
      GuiItem[] var2 = new GuiItem[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         ItemStack var4;
         if (this.selected_pattern != null) {
            var4 = new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1, (short)14);
            GuiItem var5 = new GuiItem(var4, "Menu.Keeper.ResetPattern.Name", "Menu.Keeper.ResetPattern.Lore");
            var2[var3] = var5;
         } else {
            ColoredHeads var7 = ColoredHeads.getFromID(var1[var3]);
            if (var7 == null) {
               var4 = new ItemStack(Material.INK_SAC, 1, (short)8);
            } else {
               var4 = var7.getItem();
            }

            GuiItem var6 = (new GuiItem(var4, "Menu.Keeper.ColorChange.Name", "Menu.Keeper.ColorChange.Lore")).addPlaceholder("!color!", L.get(this.p, var7 == null ? "Menu.Keeper.Undefined" : var7.getLink()));
            var2[var3] = var6;
         }
      }

      return var2;
   }

   public void leaveKeeper() {
      this.resetGui();
      this.keeperEdit = -1;
   }

   public void resetGui() {
      this.select_wings = null;
      this.select_c1 = 0;
      this.select_c2 = 0;
      this.select_c3 = 0;
      this.select_pattern = false;
      this.selected_pattern = null;
      this.keeperEdit = -1;
   }

   public void save() {
      PlayerData var1 = DataManager.getData(this.p);
      var1.save();
      PluginMessages.Debug("triggered");
   }

   public void openColor() {
      GuiItem var1 = (new GuiItem(Heads.hwhite.clone(), "RainAndWings.White")).cmd(1);
      GuiItem var2 = (new GuiItem(Heads.hblack.clone(), "RainAndWings.Black")).cmd(2);
      GuiItem var3 = (new GuiItem(Heads.hred.clone(), "RainAndWings.Red")).cmd(3);
      GuiItem var4 = (new GuiItem(Heads.hyellow.clone(), "RainAndWings.Yellow")).cmd(4);
      GuiItem var5 = (new GuiItem(Heads.hgreen.clone(), "RainAndWings.Green")).cmd(5);
      GuiItem var6 = (new GuiItem(Heads.hblue.clone(), "RainAndWings.Blue")).cmd(6);
      GuiItem var7 = (new GuiItem(Heads.hpurple.clone(), "RainAndWings.Purple")).cmd(7);
      this.downline();
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

   public void click(InventoryClickEvent var1) {
      if (var1.getSlot() < 54 || var1.getClickedInventory().equals(this.inv)) {
         GuiItem var2;
         if (this.stage == 0) {
            if (var1.getRawSlot() < 0 || var1.getRawSlot() >= this.git.length) {
               return;
            }

            var2 = this.git[var1.getRawSlot()];
            if (!var2.hasPermission(this.p)) {
               L.msg(this.p, "System.NoWingsPermission");
               return;
            }

            if (var1.getRawSlot() == 45) {
               if (this.page == 0) {
                  GuiManager.open(this.p, "Main");
                  return;
               }

               --this.page;
               this.open(this.p);
               return;
            }

            if (this.hasNextPage() && var1.getRawSlot() == 53) {
               ++this.page;
               this.open(this.p);
               return;
            }

            if (var2 == null) {
               return;
            }

            if (P.has(this.p, "trails.keeper") && DataManager.getData(this.p).isLoaded() && this.keeperEdit == -1 && this.keeper == 0 && var1.getRawSlot() >= 47 && var1.getRawSlot() <= 51) {
               if (var1.getClick() == ClickType.RIGHT) {
                  this.editKeeper(var1.getRawSlot());
                  return;
               }

               Keeper var7 = DataManager.getData(this.p).getKeeper(var1.getRawSlot() - 47);
               if (var7.isEmpty()) {
                  this.keeper = var1.getRawSlot() - 46;
                  this.open(this.p);
                  return;
               }

               var7.setWings(this.p);
               SelectSounds.play(SoundType.Wings, this.p);
               this.p.closeInventory();
               return;
            }

            if (var2.command != null) {
               TrailWings var6 = (TrailWings)var2.command;
               this.select_wings = var6;
               this.select_id = var6.getId();
               this.getNextStage();
            }
         } else if (this.stage == 1) {
            if (var1.getRawSlot() == 21) {
               this.select_pattern = false;
               this.getNextStage();
            } else if (var1.getRawSlot() == 23) {
               this.select_pattern = true;
               this.stage = 5;
               this.open(this.p);
            }
         } else {
            int var3;
            if (this.stage == 2) {
               var2 = this.git[var1.getRawSlot()];
               if (var2 == null || var2.command == null) {
                  return;
               }

               var3 = (Integer)var2.command;
               this.select_c1 = var3;
               this.getNextStage();
            } else if (this.stage == 3) {
               var2 = this.git[var1.getRawSlot()];
               if (var2 == null || var2.command == null) {
                  return;
               }

               var3 = (Integer)var2.command;
               this.select_c2 = var3;
               this.getNextStage();
            } else if (this.stage == 4) {
               var2 = this.git[var1.getRawSlot()];
               if (var2 == null || var2.command == null) {
                  return;
               }

               var3 = (Integer)var2.command;
               this.select_c3 = var3;
               this.getNextStage();
            } else if (this.stage == 5) {
               var2 = this.git[var1.getRawSlot()];
               if (!var2.hasPermission(this.p)) {
                  L.msg(this.p, "System.NoPatternPermission");
                  return;
               }

               if (var1.getRawSlot() == 45) {
                  if (this.pat_page == 0) {
                     return;
                  }

                  --this.pat_page;
                  this.open(this.p);
                  return;
               }

               if (this.hasNextPatternPage() && var1.getRawSlot() == 53) {
                  ++this.pat_page;
                  this.open(this.p);
                  return;
               }

               if (var2 == null) {
                  return;
               }

               if (var2.command != null) {
                  WingsPattern var5 = (WingsPattern)var2.command;
                  this.selected_pattern = var5;
                  this.getNextStage();
               }
            } else if (this.stage == 10 && this.keeperEdit != -1) {
               int var4 = var1.getRawSlot();
               if (var4 == 20) {
                  this.stage = 0;
                  this.open(this.p);
               } else if (var4 >= 22 && var4 <= 24) {
                  if (this.selected_pattern == null) {
                     this.stage = var4 - 20;
                     this.open(this.p);
                  } else {
                     this.selected_pattern = null;
                     this.editKeeper(this.keeperEdit + 47);
                  }
               } else if (var4 == 32) {
                  this.stage = 5;
                  this.open(this.p);
               } else if (var4 == 46) {
                  this.leaveKeeper();
                  this.stage = 0;
                  this.open(this.p);
               } else if (var4 == 49) {
                  this.updateKeeper();
                  this.leaveKeeper();
                  this.stage = 0;
                  this.save();
                  this.open(this.p);
               } else if (var4 == 52) {
                  this.areyousure();
               }
            }
         }

      }
   }
}
