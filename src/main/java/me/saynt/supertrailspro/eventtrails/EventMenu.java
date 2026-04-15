package me.saynt.supertrailspro.eventtrails;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.saynt.supertrailspro.STUtils;
import me.saynt.supertrailspro.ServerVersion;
import me.saynt.supertrailspro.ServerVersionsEnum;
import me.saynt.supertrailspro.eventtrails.fairy.FairyUtils;
import me.saynt.supertrailspro.inventory.InvControl;
import me.saynt.supertrailspro.lang.L;
import me.saynt.supertrailspro.modules.SelectSounds;
import me.saynt.supertrailspro.modules.SoundType;
import me.saynt.supertrailspro.modules.SoundsConverter;
import me.saynt.supertrailspro.trails.TrailType;
import me.saynt.supertrailspro.trails.TrailsUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class EventMenu {
   public static boolean enabled = STUtils.optionb("EventTrails");
   public static List<String> names1 = new ArrayList();
   public static List<String> names3 = new ArrayList();
   public static List<String> names2 = new ArrayList();
   static int[] slots = new int[]{10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34};
   static int[] slots2 = new int[]{10, 11, 12, 13, 14, 15, 16};
   static String r;

   static {
      r = ServerVersion.higherThanOrEqual(ServerVersionsEnum.S113) ? "" : "§r";
   }

   public static void openChest(Player var0) {
      String var1 = null;
      var1 = ServerVersion.higherThanOrEqual(ServerVersionsEnum.S113) ? L.get(var0, "MenuNames.EventOpen") : L.get(var0, "MenuNames.EventOpen") + "§r";
      if (names2.contains(var1)) {
         if (EventDataManager.canpayChest(var0)) {
            Inventory var2 = Bukkit.createInventory((InventoryHolder)null, 54, var1);

            for(int var3 = 0; var3 < 54; ++var3) {
               var2.setItem(var3, STUtils.getPlayerIS(var0, new ItemStack(Material.CHEST), "Event.OpenChest.Name", "Event.OpenChest.Lore"));
            }

            var0.openInventory(var2);
         }
      }
   }

   public static void openned(Player var0, int var1) {
      String var2 = ServerVersion.higherThanOrEqual(ServerVersionsEnum.S113) ? L.get(var0, "MenuNames.EventOpen") : L.get(var0, "MenuNames.EventOpen") + "§r";
      if (names2.contains(var2)) {
         if (EventDataManager.payChest(var0)) {
            var0.playSound(var0.getLocation(), SoundsConverter.CHEST_OPEN.toSound(), 1.0F, 1.0F);
            Inventory var3 = Bukkit.createInventory((InventoryHolder)null, 54, var2);

            for(int var4 = 0; var4 < 54; ++var4) {
               var3.setItem(var4, new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1, (short)8));
            }

            TrailEvent var5 = winrdm(var0);
            if (var5 == null) {
               var3.setItem(var1, STUtils.getPlayerIS(var0, new ItemStack(Material.PAPER), "Event.Empty.Name", (String)null));
            } else {
               var3.setItem(var1, STUtils.getPlayerISEvent(var0, var5.getItemStack(), "Event." + var5.getName() + ".Name", "Event." + var5.getName() + ".Lore", var5));
            }

            var0.openInventory(var3);
         }
      }
   }

   public static TrailEvent winrdm(Player var0) {
      int var1 = STUtils.r(1, 20);
      Rarity var2 = null;
      if (var1 <= 15) {
         var2 = Rarity.COMMON;
      } else if (var1 <= 19) {
         var2 = Rarity.RARE;
      } else if (var1 == 20) {
         var2 = Rarity.EPIC;
      }

      int var3 = var2.getInts().length;
      int var4 = var2.getInts()[STUtils.r(0, var3 - 1)];
      if (EventDataManager.has(var0, var4)) {
         return null;
      } else {
         EventDataManager.addNew(var0, var4);
         if (var2 == Rarity.RARE) {
            var0.playSound(var0.getLocation(), SoundsConverter.LEVEL_UP.toSound(), 1.0F, 1.0F);
         }

         if (var2 == Rarity.EPIC) {
            var0.playSound(var0.getLocation(), SoundsConverter.ORB_PICKUP.toSound(), 1.0F, 1.0F);
         }

         return (TrailEvent)EventTrails.i.get(var4);
      }
   }

   public static void open(Player var0) {
      String var1 = ServerVersion.higherThanOrEqual(ServerVersionsEnum.S113) ? L.get(var0, "MenuNames.Event") : L.get(var0, "MenuNames.Event") + "§r";
      if (names1.contains(var1)) {
         Inventory var2 = Bukkit.createInventory((InventoryHolder)null, 54, var1);
         List var3 = EventDataManager.getTrailsList(var0);
         var2.setItem(49, STUtils.getPlayerIS(var0, new ItemStack(Material.ARROW, 1, (short)0), "Menu.Back", (String)null));
         var2.setItem(26, STUtils.getPlayerIS(var0, new ItemStack(Material.ARROW, 1, (short)0), "Menu.Next", (String)null));
         var2.setItem(4, STUtils.getPlayerISEventChest(var0, new ItemStack(Material.CHEST, 1, (short)0), "Event.Chest.Name", "Event.Chest.Lore"));
         int[] var7;
         int var6 = (var7 = slots).length;

         for(int var5 = 0; var5 < var6; ++var5) {
            int var4 = var7[var5];
            var2.setItem(var4, STUtils.getPlayerIS(var0, new ItemStack(Material.INK_SAC, 1, (short)8), "Event.Nothing.Name", (String)null));
         }

         Iterator var9 = var3.iterator();

         while(var9.hasNext()) {
            TrailEvent var8 = (TrailEvent)var9.next();
            if (var8.getType() == TrailType.Event_Confetti) {
               var2.setItem(slots[var8.getId() - 401], STUtils.getPlayerISEvent(var0, var8.getItemStack(), "Event." + var8.getName() + ".Name", "Event." + var8.getName() + ".Lore", var8));
            } else if (var8.getType() == TrailType.Event_Spin) {
               var2.setItem(slots[var8.getId() - 404], STUtils.getPlayerISEvent(var0, var8.getItemStack(), "Event." + var8.getName() + ".Name", "Event." + var8.getName() + ".Lore", var8));
            } else if (var8.getType() == TrailType.Event_Color) {
               var2.setItem(slots[var8.getId() - 407], STUtils.getPlayerISEvent(var0, var8.getItemStack(), "Event." + var8.getName() + ".Name", "Event." + var8.getName() + ".Lore", var8));
            }
         }

         var0.openInventory(var2);
      }
   }

   public static void open2(Player var0) {
      String var1 = ServerVersion.higherThanOrEqual(ServerVersionsEnum.S113) ? L.get(var0, "MenuNames.EventSecond") + " (2)" : L.get(var0, "MenuNames.EventSecond") + "§5";
      if (names3.contains(var1)) {
         Inventory var2 = Bukkit.createInventory((InventoryHolder)null, 54, var1);
         List var3 = EventDataManager.getTrailsList(var0);
         var2.setItem(49, STUtils.getPlayerIS(var0, new ItemStack(Material.ARROW, 1, (short)0), "Menu.Back", (String)null));
         var2.setItem(18, STUtils.getPlayerIS(var0, new ItemStack(Material.ARROW, 1, (short)0), "Menu.Prev", (String)null));
         var2.setItem(4, STUtils.getPlayerISEventChest(var0, new ItemStack(Material.CHEST, 1, (short)0), "Event.Chest.Name", "Event.Chest.Lore"));
         int[] var7;
         int var6 = (var7 = slots2).length;

         for(int var5 = 0; var5 < var6; ++var5) {
            int var4 = var7[var5];
            var2.setItem(var4, STUtils.getPlayerIS(var0, new ItemStack(Material.INK_SAC, 1, (short)8), "Event.Nothing.Name", (String)null));
         }

         Iterator var9 = var3.iterator();

         while(var9.hasNext()) {
            TrailEvent var8 = (TrailEvent)var9.next();
            if (var8.getType() == TrailType.Event_Fairy) {
               var2.setItem(slots2[var8.getId() - 431], STUtils.getPlayerISEvent(var0, var8.getItemStack(), "Event." + var8.getName() + ".Name", "Event." + var8.getName() + ".Lore", var8));
            }
         }

         var0.openInventory(var2);
      }
   }

   @EventHandler
   public void onClk(InventoryClickEvent var1) {
      String var2;
      try {
         var2 = ServerVersion.higherThanOrEqual(ServerVersionsEnum.S114) ? var1.getView().getTitle() : var1.getView().getTitle();
      } catch (Exception var6) {
         return;
      }

      int var3;
      Player var4;
      int var5;
      if (names1.contains(var2)) {
         var1.setCancelled(true);
         var3 = var1.getRawSlot();
         var4 = (Player)var1.getWhoClicked();
         var5 = 0;
         if (var1.getRawSlot() >= 10 && var1.getRawSlot() <= 16) {
            var5 = 391 + var3;
         }

         if (var1.getRawSlot() >= 19 && var1.getRawSlot() <= 25) {
            var5 = 392 + var3;
         }

         if (var1.getRawSlot() >= 28 && var1.getRawSlot() <= 34) {
            return;
         }

         if (var1.getRawSlot() == 4) {
            openChest(var4);
         }

         if (var1.getRawSlot() == 49) {
            InvControl.getMenu("Trails: Select Type").open(var4);
         }

         if (var1.getRawSlot() == 26) {
            open2(var4);
            return;
         }

         if (var5 != 0 && EventDataManager.has(var4, var5)) {
            TrailsUtil.SetTrail(var4, var5);
            var4.closeInventory();
            SelectSounds.play(SoundType.Event, var4);
         }
      }

      if (names2.contains(var2)) {
         var1.setCancelled(true);
         if (var1.getRawSlot() < 54) {
            if (var1.getCurrentItem() != null && var1.getCurrentItem().getType() == Material.CHEST) {
               Player var7 = (Player)var1.getWhoClicked();
               openned(var7, var1.getRawSlot());
            } else {
               open((Player)var1.getWhoClicked());
            }
         }
      }

      if (names3.contains(var2)) {
         var1.setCancelled(true);
         var3 = var1.getRawSlot();
         var4 = (Player)var1.getWhoClicked();
         var5 = 0;
         if (var1.getRawSlot() == 4) {
            openChest(var4);
         }

         if (var1.getRawSlot() == 49) {
            InvControl.getMenu("Trails: Select Type").open(var4);
         }

         if (var1.getRawSlot() >= 10 && var1.getRawSlot() <= 16) {
            var5 = 421 + var3;
         }

         if (var1.getRawSlot() == 18) {
            open(var4);
            return;
         }

         if (var5 != 0 && EventDataManager.has(var4, var5)) {
            FairyUtils.setFairy(var4, var5);
            var4.closeInventory();
            SelectSounds.play(SoundType.Event, var4);
         }
      }

   }

   public static int getAmount(Player var0) {
      return EventDataManager.getChestAmount(var0);
   }
}
