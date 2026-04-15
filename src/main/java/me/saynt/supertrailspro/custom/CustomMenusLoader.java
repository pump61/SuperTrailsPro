package me.saynt.supertrailspro.custom;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.saynt.supertrailspro.ServerVersion;
import me.saynt.supertrailspro.ServerVersionsEnum;
import me.saynt.supertrailspro.SuperTrails;
import me.saynt.supertrailspro.inventory.ClickTypes;
import me.saynt.supertrailspro.inventory.InvMenu;
import me.saynt.supertrailspro.inventory.MenuType;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class CustomMenusLoader implements Listener {
   public static List<InvMenu> invs = new ArrayList();

   public static void load() {
      File var0 = new File(SuperTrails.p.getDataFolder() + "/custominventories/");
      if (var0.exists()) {
         var0.delete();
      }

   }

   public static void unload() {
      invs.clear();
   }

   public static InvMenu loadFromFile(File var0) {
      try {
         YamlConfiguration var1 = YamlConfiguration.loadConfiguration(var0);
         String var2 = var1.getString("ID");
         String var3 = var1.getString("Name");
         InvMenu var4 = new InvMenu(var2, var3, MenuType.Unknown, false);
         Iterator var6 = var1.getConfigurationSection("Items").getKeys(false).iterator();

         while(var6.hasNext()) {
            String var5 = (String)var6.next();
            String var7 = var1.getString("Items." + var5 + ".Name");
            String var8 = var1.getString("Items." + var5 + ".Lore");
            String var9 = var1.getString("Items." + var5 + ".Command");
            String var10 = var1.getString("Items." + var5 + ".Material");
            byte var11 = (byte)Integer.parseInt(var1.getString("Items." + var5 + ".Data"));
            int var12 = var1.getInt("Items." + var5 + ".Slot");
            String var13 = var1.getString("Items." + var5 + ".Permission");
            if (var8 != null) {
               if (var13 != null) {
                  var4.addClickableItemWithLore(var12, new ItemStack(Material.getMaterial(var10), 1, var11), var7, var8, var9, var13);
               } else {
                  var4.addClickableItemWithLore(var12, new ItemStack(Material.getMaterial(var10), 1, var11), var7, var8, var9);
               }
            } else {
               var4.addClickableItem(var12, new ItemStack(Material.getMaterial(var10), 1, var11), var7, var9);
               if (var13 != null) {
                  var4.addPerm(var12, var13);
               }
            }
         }

         return var4;
      } catch (Exception var14) {
         var14.printStackTrace();
         return null;
      }
   }

   public static void open(Player var0, String var1) {
      Iterator var3 = invs.iterator();

      while(var3.hasNext()) {
         InvMenu var2 = (InvMenu)var3.next();
         if (var2.getID().equalsIgnoreCase(var1)) {
            var2.open(var0);
            return;
         }
      }

      var0.sendMessage("§7Menu not found");
   }

   public static InvMenu getMenu(String var0) {
      Iterator var2 = invs.iterator();

      while(var2.hasNext()) {
         InvMenu var1 = (InvMenu)var2.next();
         if (var1.isMenu(var0)) {
            return var1;
         }
      }

      return null;
   }

   @EventHandler
   public void onClick(InventoryClickEvent var1) {
      try {
         String var2 = ServerVersion.higherThanOrEqual(ServerVersionsEnum.S114) ? var1.getView().getTitle() : var1.getView().getTitle();
         InvMenu var3 = getMenu(var2);
         if (var3 != null) {
            var1.setCancelled(true);
            var3.Click((Player)var1.getWhoClicked(), var1.getRawSlot(), ClickTypes.LEFT);
         }
      } catch (Exception var4) {
      }

   }
}
