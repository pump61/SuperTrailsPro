package me.saynt.supertrailspro.inventory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
import me.saynt.supertrailspro.lang.L;
import me.saynt.supertrailspro.modules.SelectSounds;
import me.saynt.supertrailspro.modules.SoundType;
import me.saynt.supertrailspro.trails.TrailType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class RainInventory {
   static List<ItemStack> itemslist = new ArrayList();
   static int[] slots = new int[]{10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43};
   public static List<String> names = new ArrayList();
   public static List<String> names2 = new ArrayList();
   static String r;
   static int[] f;

   static {
      r = ServerVersion.higherThanOrEqual(ServerVersionsEnum.S113) ? "" : "§r";
      f = new int[]{28, 29, 30, 31, 32, 33, 34};
   }

   public static void initialize() {
      addItems(new Material[]{Material.ENCHANTING_TABLE, Material.ENDER_CHEST, Material.OAK_BUTTON, Material.APPLE, Material.DIAMOND, Material.COAL, Material.IRON_INGOT, Material.GOLD_INGOT, Material.STRING, Material.FEATHER, Material.WHEAT, Material.BREAD, Material.PORKCHOP, Material.GOLDEN_APPLE, Material.REDSTONE, Material.PAPER, Material.EGG, Material.CAKE, Material.COOKIE, Material.MELON, Material.ENDER_PEARL, Material.GHAST_TEAR, Material.BOW, Material.GOLD_NUGGET, Material.EMERALD, Material.NETHER_STAR, Material.ANVIL});
      addItems(new ItemStack[]{new ItemStack(Material.GOLDEN_APPLE, 1, (short)1)});
   }

   public static void open(Player var0) {
      if (!P.has(var0, "trails.rains")) {
         var0.sendMessage(L.get(var0, "System.CmdNoPermissionTrail"));
      } else {
         String var1 = L.get(var0, "MenuNames.Rain1") + r;
         if (names.contains(var1)) {
            Inventory var2 = Bukkit.createInventory((InventoryHolder)null, 54, var1);
            var2.setItem(49, STUtils.getPlayerIS(var0, InvControl.getI("Options.Inventory.BackItem"), "Menu.Back", "Menu.BackLore"));
            var2.setItem(19, STUtils.setName(Heads.hwhite.clone(), L.get(var0, "RainAndWings.White")));
            var2.setItem(20, STUtils.setName(Heads.hblack.clone(), L.get(var0, "RainAndWings.Black")));
            var2.setItem(21, STUtils.setName(Heads.hred.clone(), L.get(var0, "RainAndWings.Red")));
            var2.setItem(22, STUtils.setName(Heads.hyellow.clone(), L.get(var0, "RainAndWings.Yellow")));
            var2.setItem(23, STUtils.setName(Heads.hgreen.clone(), L.get(var0, "RainAndWings.Green")));
            var2.setItem(24, STUtils.setName(Heads.hblue.clone(), L.get(var0, "RainAndWings.Blue")));
            var2.setItem(25, STUtils.setName(Heads.hpurple.clone(), L.get(var0, "RainAndWings.Purple")));
            if (EventMenu.enabled) {
               int[] var6;
               int var5 = (var6 = f).length;

               for(int var4 = 0; var4 < var5; ++var4) {
                  int var3 = var6[var4];
                  var2.setItem(var3, STUtils.getPlayerIS(var0, new ItemStack(Material.INK_SAC, 1, (short)8), "Event.Nothing.Name", (String)null));
               }

               List var7 = EventDataManager.getTrailsList(var0);
               Iterator var9 = var7.iterator();

               while(var9.hasNext()) {
                  TrailEvent var8 = (TrailEvent)var9.next();
                  if (var8.getType() == TrailType.Event_Color) {
                     var2.setItem(f[var8.getId() - 421], STUtils.getPlayerISEvent(var0, var8.getItemStack(), "Event.UnlockedColor.Name", "Event.UnlockedColor.Lore", var8));
                  }
               }
            }

            var0.openInventory(var2);
         }
      }
   }

   public static void addItems(ItemStack[] var0) {
      ItemStack[] var4 = var0;
      int var3 = var0.length;

      for(int var2 = 0; var2 < var3; ++var2) {
         ItemStack var1 = var4[var2];
         itemslist.add(var1);
      }

   }

   public static void addItems(Material[] var0) {
      Material[] var4 = var0;
      int var3 = var0.length;

      for(int var2 = 0; var2 < var3; ++var2) {
         Material var1 = var4[var2];
         itemslist.add(new ItemStack(var1));
      }

   }

   public static void openItemSelector(Player var0) {
      if (!P.has(var0, "trails.rains")) {
         var0.sendMessage(L.get(var0, "System.CmdNoPermissionTrail"));
      } else {
         String var1 = L.get(var0, "MenuNames.Rain2") + r;
         if (names2.contains(var1)) {
            Inventory var2 = Bukkit.createInventory((InventoryHolder)null, 54, var1);
            int var3 = 0;
            var2.setItem(49, STUtils.getPlayerIS(var0, InvControl.getI("Options.Inventory.BackItem"), "Menu.Back", "Menu.BackLore"));
            Iterator var5 = itemslist.iterator();

            while(var5.hasNext()) {
               ItemStack var4 = (ItemStack)var5.next();
               if (var3 < slots.length) {
                  var2.setItem(slots[var3], var4);
                  ++var3;
               }
            }

            var0.openInventory(var2);
         }
      }
   }

   public static void clickedColor(InventoryClickEvent var0) {
      int var1 = var0.getRawSlot();
      Player var2 = (Player)var0.getWhoClicked();
      if (var1 == 49) {
         InvControl.getMenu("Trails: Select Type").open(var2);
      }

      PlayerData var3;
      int var4;
      if (var1 >= 19 && var1 <= 25) {
         var3 = DataManager.getData(var2);
         var3.resetRains();
         var4 = var1 - 18;
         var3.setRainColor(var4);
         openItemSelector(var2);
      } else if (var1 >= 28 && var1 <= 34) {
         var3 = DataManager.getData(var2);
         var4 = var1 - 17;
         int var5 = var1 + 393;
         if (!EventDataManager.has(var2, var5)) {
            return;
         }

         var3.setRainColor(var4);
         openItemSelector(var2);
      }

   }

   public static void clickedItem(InventoryClickEvent var0) {
      int var1 = var0.getRawSlot();
      Player var2 = (Player)var0.getWhoClicked();
      ItemStack var3 = var0.getCurrentItem();
      if (var1 == 49) {
         InvControl.getMenu("Trails: Select Type").open(var2);
      }

      if (contains(slots, var1)) {
         PlayerData var4 = DataManager.getData(var2);
         var4.setRainItemStack(var3);
         var4.setTrail(201);
         var2.closeInventory();
         SelectSounds.play(SoundType.Rains, var2);
      }

   }

   public static boolean contains(int[] var0, int var1) {
      int[] var5 = var0;
      int var4 = var0.length;

      for(int var3 = 0; var3 < var4; ++var3) {
         int var2 = var5[var3];
         if (var2 == var1) {
            return true;
         }
      }

      return false;
   }
}
