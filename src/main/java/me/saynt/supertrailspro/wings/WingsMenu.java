package me.saynt.supertrailspro.wings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import me.saynt.supertrailspro.Heads;
import me.saynt.supertrailspro.P;
import me.saynt.supertrailspro.STUtils;
import me.saynt.supertrailspro.ServerVersion;
import me.saynt.supertrailspro.ServerVersionsEnum;
import me.saynt.supertrailspro.data.DataManager;
import me.saynt.supertrailspro.data.PlayerData;
import me.saynt.supertrailspro.eventtrails.EventDataManager;
import me.saynt.supertrailspro.eventtrails.EventMenu;
import me.saynt.supertrailspro.eventtrails.TrailEvent;
import me.saynt.supertrailspro.inventory.InvControl;
import me.saynt.supertrailspro.inventory.InvMenu;
import me.saynt.supertrailspro.lang.L;
import me.saynt.supertrailspro.modules.SelectSounds;
import me.saynt.supertrailspro.modules.SoundType;
import me.saynt.supertrailspro.trails.Task;
import me.saynt.supertrailspro.trails.TrailType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WingsMenu {
   public static List<String> name1 = new ArrayList();
   public static List<String> name2 = new ArrayList();
   public static List<String> color3 = new ArrayList();
   public static List<String> color4 = new ArrayList();
   public static List<String> color5 = new ArrayList();
   public static List<String> pattern = new ArrayList();
   static String r;
   static int[] f;

   static {
      r = ServerVersion.higherThanOrEqual(ServerVersionsEnum.S113) ? "" : "§r";
      f = new int[]{28, 29, 30, 31, 32, 33, 34};
   }

   public static void Open(Player var0) {
      if (var0 != null) {
         ;
      }
   }

   public static void WingsSelector(Player var0) {
      String var1 = L.get(var0, "MenuNames.Wings1") + r;
      if (name1.contains(var1)) {
         Inventory var2 = Bukkit.createInventory((InventoryHolder)null, 54, var1);
         Iterator var4 = CustomWings.slots.entrySet().iterator();

         while(true) {
            while(var4.hasNext()) {
               Entry var3 = (Entry)var4.next();
               ItemStack var5 = (ItemStack)CustomWings.it.get(var3.getKey());
               if (STUtils.optionb("HideNoPermissionTrails") && !P.has(var0, "trails.wings." + (String)var3.getKey())) {
                  var2.setItem((Integer)var3.getValue(), InvMenu.getBlocked(var0));
               } else {
                  List var6 = L.getLore(var0, "WingsLore." + (String)var3.getKey());
                  ItemMeta var7 = var5.getItemMeta();
                  var7.setDisplayName(L.get(var0, "Wings." + (String)var3.getKey()));
                  var6 = InvControl.replacePerm(var0, "trails.wings." + (String)var3.getKey(), var6);
                  var7.setLore(var6);
                  var5.setItemMeta(var7);
                  var2.setItem((Integer)var3.getValue(), var5);
               }
            }

            var2.setItem(49, STUtils.getPlayerIS(var0, InvControl.getI("Options.Inventory.BackItem"), "Menu.Back", "Menu.BackLore"));
            var0.openInventory(var2);
            return;
         }
      }
   }

   public static void clickreader1(Player var0, int var1) {
      Iterator var3 = CustomWings.slots.entrySet().iterator();

      while(var3.hasNext()) {
         Entry var2 = (Entry)var3.next();
         int var4 = (Integer)var2.getValue();
         if (var4 == var1) {
            if (!P.hasWings(var0, "trails.wings." + (String)var2.getKey(), true)) {
               return;
            }

            int var5 = STUtils.WingNameToId((String)var2.getKey());
            PlayerData var6 = DataManager.getData(var0);
            var6.resetWings();
            var6.setTrail(var5);
            WingsInfo var7 = (WingsInfo)CustomWings.info.get(var2.getKey());
            if (var7 != null && !var7.hasAnyColors()) {
               var6.runWings();
               DataManager.getData(var0).save();
               var0.closeInventory();
               DataManager.getData(var0).tick = Task.currenttick < 2 && Task.currenttick >= 0 ? Task.currenttick++ : 0;
               SelectSounds.play(SoundType.Wings, var0);
               return;
            }

            ColorOrPattern(var0);
         }
      }

   }

   public static void ColorOrPattern(Player var0) {
      String var1 = L.get(var0, "MenuNames.Wings2") + r;
      if (name2.contains(var1)) {
         Inventory var2 = Bukkit.createInventory((InventoryHolder)null, 54, var1);
         ItemStack var3 = Heads.hred.clone();
         STUtils.setName(var3, L.get(var0, "RainAndWings.Color"));
         var2.setItem(21, var3);
         var2.setItem(23, STUtils.setName(new ItemStack(Material.COD, 1, (short)3), L.get(var0, "RainAndWings.Pattern")));
         var2.setItem(49, STUtils.getPlayerIS(var0, InvControl.getI("Options.Inventory.BackItem"), "Menu.Back", "Menu.BackLore"));
         var0.openInventory(var2);
      }
   }

   public static void clickreader2(Player var0, int var1) {
      if (var1 == 21) {
         PlayerData var2 = DataManager.getData(var0);
         String var3 = STUtils.IdToWings(var2.getTrail());
         if (var3 == null) {
            Colors(var0, 0);
         }

         WingsInfo var4 = (WingsInfo)CustomWings.info.get(var3);
         if (var4.hasRed()) {
            Colors(var0, 0);
         } else if (var4.hasGreen()) {
            Colors(var0, 1);
         } else if (var4.hasBlue()) {
            Colors(var0, 2);
         }
      }

      if (var1 == 23) {
         Patterns(var0);
         DataManager.getData(var0).setWingsColor(0, 50);
      }

   }

   public static boolean hasCLR(int var0, String var1) {
      if (var0 == 0) {
         return color3.contains(var1);
      } else if (var0 == 1) {
         return color4.contains(var1);
      } else {
         return var0 == 2 ? color5.contains(var1) : false;
      }
   }

   public static void Colors(Player var0, int var1) {
      String var2 = L.get(var0, "MenuNames.Wings" + (var1 + 3)) + r;
      if (hasCLR(var1, var2)) {
         Inventory var3 = Bukkit.createInventory((InventoryHolder)null, 54, var2);
         var3.setItem(19, STUtils.setName(Heads.hwhite.clone(), L.get(var0, "RainAndWings.White")));
         var3.setItem(20, STUtils.setName(Heads.hblack.clone(), L.get(var0, "RainAndWings.Black")));
         var3.setItem(21, STUtils.setName(Heads.hred.clone(), L.get(var0, "RainAndWings.Red")));
         var3.setItem(22, STUtils.setName(Heads.hyellow.clone(), L.get(var0, "RainAndWings.Yellow")));
         var3.setItem(23, STUtils.setName(Heads.hgreen.clone(), L.get(var0, "RainAndWings.Green")));
         var3.setItem(24, STUtils.setName(Heads.hblue.clone(), L.get(var0, "RainAndWings.Blue")));
         var3.setItem(25, STUtils.setName(Heads.hpurple.clone(), L.get(var0, "RainAndWings.Purple")));
         var3.setItem(49, STUtils.getPlayerIS(var0, InvControl.getI("Options.Inventory.BackItem"), "Menu.Back", "Menu.BackLore"));
         if (EventMenu.enabled) {
            int[] var7;
            int var6 = (var7 = new int[]{28, 29, 30, 31, 32, 33, 34}).length;

            for(int var5 = 0; var5 < var6; ++var5) {
               int var4 = var7[var5];
               var3.setItem(var4, STUtils.getPlayerIS(var0, new ItemStack(Material.INK_SAC, 1, (short)8), "Event.Nothing.Name", (String)null));
            }

            List var8 = EventDataManager.getTrailsList(var0);
            Iterator var10 = var8.iterator();

            while(var10.hasNext()) {
               TrailEvent var9 = (TrailEvent)var10.next();
               if (var9.getType() == TrailType.Event_Color) {
                  var3.setItem(f[var9.getId() - 421], STUtils.getPlayerISEvent(var0, var9.getItemStack(), "Event.UnlockedColor.Name", "Event.UnlockedColor.Lore", var9));
               }
            }
         }

         var0.openInventory(var3);
      }
   }

   public static void read(Player var0, int var1, int var2) {
      PlayerData var3 = DataManager.getData(var0);
      String var4 = STUtils.IdToWings(var3.getTrail());
      WingsInfo var5 = (WingsInfo)CustomWings.info.get(var4);
      if (var2 >= 19 && var2 <= 25) {
         if (var1 == 1) {
            DataManager.getData(var0).setWingsColor(0, var2 - 18);
            if (var5.hasGreen()) {
               Colors(var0, 1);
            } else if (var5.hasBlue()) {
               Colors(var0, 2);
            } else {
               var3.runWings();
               var3.save();
               var3.autoTick();
               var0.closeInventory();
            }
         } else if (var1 == 2) {
            DataManager.getData(var0).setWingsColor(1, var2 - 18);
            if (var5.hasBlue()) {
               Colors(var0, 2);
            } else {
               var3.runWings();
               var3.save();
               var3.autoTick();
               var0.closeInventory();
            }
         } else if (var1 == 3) {
            var3.setWingsColor(2, var2 - 18);
            var3.runWings();
            var3.save();
            var0.closeInventory();
            DataManager.getData(var0).tick = Task.currenttick < 2 && Task.currenttick >= 0 ? Task.currenttick++ : 0;
            SelectSounds.play(SoundType.Wings, var0);
         }
      } else if (var2 >= 28 && var2 <= 34) {
         int var6 = var2 - 17;
         int var7 = var2 + 393;
         if (!EventDataManager.has(var0, var7)) {
            return;
         }

         if (var1 == 1) {
            DataManager.getData(var0).setWingsColor(0, var6);
            if (var5.hasGreen()) {
               Colors(var0, 1);
            } else if (var5.hasBlue()) {
               Colors(var0, 2);
            } else {
               var3.runWings();
               var3.save();
               var3.autoTick();
               var0.closeInventory();
            }
         } else if (var1 == 2) {
            DataManager.getData(var0).setWingsColor(1, var6);
            if (var5.hasBlue()) {
               Colors(var0, 2);
            } else {
               var3.runWings();
               var3.save();
               var3.autoTick();
               var0.closeInventory();
            }
         } else if (var1 == 3) {
            DataManager.getData(var0).setWingsColor(2, var6);
            var3.runWings();
            DataManager.getData(var0).save();
            DataManager.getData(var0).tick = Task.currenttick < 2 && Task.currenttick >= 0 ? Task.currenttick++ : 0;
            var0.closeInventory();
            SelectSounds.play(SoundType.Wings, var0);
         }
      }

   }

   public static void Patterns(Player var0) {
      String var1 = L.get(var0, "MenuNames.WingsPattern") + r;
      if (pattern.contains(var1)) {
         Inventory var2 = Bukkit.createInventory((InventoryHolder)null, 54, var1);
         Iterator var4 = CustomWings.patternslot.entrySet().iterator();

         while(var4.hasNext()) {
            Entry var3 = (Entry)var4.next();
            String var5 = L.get(var0, "Pattern." + (String)var3.getKey());
            List var6 = L.getLore(var0, "PatternLore." + (String)var3.getKey());
            ItemStack var7 = ((ItemStack)CustomWings.patternItem.get(var3.getKey())).clone();
            var7 = STUtils.setName(var7, var5);
            ItemMeta var8 = var7.getItemMeta();
            var6 = InvControl.replacePerm(var0, "trails.pattern." + (String)var3.getKey(), var6);
            var8.setLore(var6);
            var7.setItemMeta(var8);
            var2.setItem((Integer)var3.getValue(), var7);
         }

         var2.setItem(49, STUtils.getPlayerIS(var0, InvControl.getI("Options.Inventory.BackItem"), "Menu.Back", "Menu.BackLore"));
         var0.openInventory(var2);
      }
   }

   public static void click(Player var0, int var1) {
      Iterator var3 = CustomWings.patternslot.entrySet().iterator();

      while(var3.hasNext()) {
         Entry var2 = (Entry)var3.next();
         int var4 = (Integer)var2.getValue();
         if (var4 == var1) {
            if (!P.hasPattern(var0, "trails.pattern." + (String)var2.getKey(), true)) {
               return;
            }

            DataManager.getData(var0).setPattern((String)var2.getKey());
            var0.closeInventory();
            DataManager.getData(var0).save();
            SelectSounds.play(SoundType.Wings, var0);
         }
      }

   }
}
