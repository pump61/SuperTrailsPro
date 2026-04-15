package me.saynt.supertrailspro.events;

import java.util.List;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class Pickup implements Listener {
   @EventHandler
   public void onPickup(PlayerPickupItemEvent var1) {
      try {
         Item var2 = var1.getItem();
         if (var2 != null && var2.getItemStack().getItemMeta().hasDisplayName()) {
            String var6 = var2.getItemStack().getItemMeta().getDisplayName();
            if (var6.contains("§r§rSuperTrails=")) {
               var1.setCancelled(true);
               var2.remove();
            }
         } else if (var2 != null && var2.getItemStack().getItemMeta().hasLore()) {
            List var3 = var2.getItemStack().getItemMeta().getLore();
            String var4 = var3.size() >= 1 ? (String)var3.get(0) : "";
            if (var4.contains("§r§rSuperTrails=")) {
               var1.setCancelled(true);
               var2.remove();
            }
         }
      } catch (Exception var5) {
      }

   }

   @EventHandler
   public void onHopperPickup(InventoryPickupItemEvent var1) {
      try {
         Item var2 = var1.getItem();
         if (var2 != null && var2.getItemStack().getItemMeta().hasDisplayName()) {
            String var6 = var2.getItemStack().getItemMeta().getDisplayName();
            if (var6.contains("§r§rSuperTrails=")) {
               var1.setCancelled(true);
               var2.remove();
            }
         } else if (var2 != null && var2.getItemStack().getItemMeta().hasLore()) {
            List var3 = var2.getItemStack().getItemMeta().getLore();
            String var4 = var3.size() >= 1 ? (String)var3.get(0) : "";
            if (var4.contains("§r§rSuperTrails=")) {
               var1.setCancelled(true);
               var2.remove();
            }
         }
      } catch (Exception var5) {
      }

   }
}
