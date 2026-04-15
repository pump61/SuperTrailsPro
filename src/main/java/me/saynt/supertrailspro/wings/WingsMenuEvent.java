package me.saynt.supertrailspro.wings;

import me.saynt.supertrailspro.ServerVersion;
import me.saynt.supertrailspro.ServerVersionsEnum;
import me.saynt.supertrailspro.community.FileSender;
import me.saynt.supertrailspro.inventory.InvControl;
import me.saynt.supertrailspro.inventory.RainInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class WingsMenuEvent implements Listener {
   @EventHandler
   public void onClick(InventoryClickEvent var1) {
      String var2 = null;

      try {
         var2 = ServerVersion.higherThanOrEqual(ServerVersionsEnum.S114) ? var1.getView().getTitle() : var1.getView().getTitle();
      } catch (Exception var4) {
         return;
      }

      Player var3 = (Player)var1.getWhoClicked();
      if (WingsMenu.name1.contains(var2)) {
         var1.setCancelled(true);
         if (var1.getRawSlot() == 49) {
            InvControl.getMenu("Trails: Select Type").open((Player)var1.getWhoClicked());
            return;
         }

         WingsMenu.clickreader1(var3, var1.getSlot());
      } else if (WingsMenu.name2.contains(var2)) {
         var1.setCancelled(true);
         if (var1.getRawSlot() == 49) {
            InvControl.getMenu("Trails: Select Type").open((Player)var1.getWhoClicked());
            return;
         }

         WingsMenu.clickreader2(var3, var1.getSlot());
         if (var1.getRawSlot() == 49) {
            InvControl.getMenu("Trails: Select Type").open((Player)var1.getWhoClicked());
            return;
         }
      } else if (WingsMenu.pattern.contains(var2)) {
         var1.setCancelled(true);
         if (var1.getRawSlot() == 49) {
            InvControl.getMenu("Trails: Select Type").open((Player)var1.getWhoClicked());
            return;
         }

         WingsMenu.click(var3, var1.getSlot());
         if (var1.getRawSlot() == 49) {
            InvControl.getMenu("Trails: Select Type").open((Player)var1.getWhoClicked());
            return;
         }
      } else if (WingsMenu.color3.contains(var2)) {
         var1.setCancelled(true);
         if (var1.getRawSlot() == 49) {
            InvControl.getMenu("Trails: Select Type").open((Player)var1.getWhoClicked());
            return;
         }

         WingsMenu.read(var3, 1, var1.getSlot());
      } else if (WingsMenu.color4.contains(var2)) {
         var1.setCancelled(true);
         if (var1.getRawSlot() == 49) {
            InvControl.getMenu("Trails: Select Type").open((Player)var1.getWhoClicked());
            return;
         }

         WingsMenu.read(var3, 2, var1.getSlot());
      } else if (WingsMenu.color5.contains(var2)) {
         var1.setCancelled(true);
         if (var1.getRawSlot() == 49) {
            InvControl.getMenu("Trails: Select Type").open((Player)var1.getWhoClicked());
            return;
         }

         WingsMenu.read(var3, 3, var1.getSlot());
      } else if (RainInventory.names.contains(var2)) {
         var1.setCancelled(true);
         RainInventory.clickedColor(var1);
      } else if (RainInventory.names2.contains(var2)) {
         var1.setCancelled(true);
         RainInventory.clickedItem(var1);
      } else if (var2.equals("Select wings to send§r")) {
         var1.setCancelled(true);
         FileSender.clickedW(var3, var1.getCurrentItem(), var1.getRawSlot());
      } else if (var2.equals("Select wings slot§r§r")) {
         var1.setCancelled(true);
         FileSender.clickSW(var3, var1.getRawSlot());
      }

   }
}
