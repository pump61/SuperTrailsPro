package me.saynt.supertrailspro.community2.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import me.saynt.stserver.packets.InventoryPacket;
import me.saynt.stserver.packets.ItemPacket;
import me.saynt.stserver.packets.Packet;
import me.saynt.stserver.packets.RequestPacket;
import me.saynt.supertrailspro.ServerVersion;
import me.saynt.supertrailspro.ServerVersionsEnum;
import me.saynt.supertrailspro.SuperTrails;
import me.saynt.supertrailspro.community2.CommunityManager;
import me.saynt.supertrailspro.community2.CommunityUploader;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class MenuListener extends PacketListener implements Listener {
   public static HashMap<Player, Inventory> invs = new HashMap();
   public static HashMap<Inventory, InventoryPacket> invpacket = new HashMap();
   public static MenuListener m;

   public void read(final Packet var1, @Nullable final Player var2) {
      Bukkit.getScheduler().runTask(SuperTrails.p, new Runnable() {
         public void run() {
            if (var2 != null) {
               var2.closeInventory();
               if (((InventoryPacket)var1).name.equals("--Upload patterns")) {
                  CommunityUploader.openPatternMenu(var2);
               } else if (((InventoryPacket)var1).name.equals("--Upload wings")) {
                  CommunityUploader.openWingsMenu(var2);
               } else {
                  Inventory var1x = MenuReader.createMenu((InventoryPacket)var1);
                  MenuListener.invs.put(var2, var1x);
                  MenuListener.invpacket.put(var1x, (InventoryPacket)var1);
                  var2.openInventory(var1x);
                  var2.updateInventory();
               }
            }
         }
      });
   }

   public static Player findbyinv(Inventory var0) {
      if (invs.size() == 0) {
         return null;
      } else {
         Iterator var2 = invs.entrySet().iterator();

         while(var2.hasNext()) {
            Entry var1 = (Entry)var2.next();
            if (((Inventory)var1.getValue()).equals(var0)) {
               return (Player)var1.getKey();
            }
         }

         return null;
      }
   }

   public static ItemPacket getItemBySlot(int var0, Inventory var1) {
      InventoryPacket var2 = (InventoryPacket)invpacket.get(var1);
      if (var2 == null) {
         return null;
      } else {
         Iterator var4 = var2.items.iterator();

         while(var4.hasNext()) {
            ItemPacket var3 = (ItemPacket)var4.next();
            if (var3.slot == var0) {
               return var3;
            }
         }

         return null;
      }
   }

   @EventHandler
   public void onClick(InventoryClickEvent var1) {
      String var2 = null;

      try {
         var2 = ServerVersion.higherThanOrEqual(ServerVersionsEnum.S114) ? var1.getView().getTitle() : var1.getView().getTitle();
      } catch (Exception var6) {
         return;
      }

      Player var3 = findbyinv(var1.getInventory());
      if (var3 != null) {
         if (var2.equals("Upload patterns")) {
            CommunityUploader.listenPattern(var1);
            return;
         }

         if (var2.equals("Upload wings")) {
            CommunityUploader.listenWings(var1);
            return;
         }

         if (var2.equals("Select slot")) {
            FileListener.listenMenu(var1);
            return;
         }

         var1.setCancelled(true);
         ItemPacket var4 = getItemBySlot(var1.getRawSlot(), var1.getClickedInventory());
         if (var4 == null) {
            return;
         }

         String var5 = var4.command;
         if (var5 != null) {
            issueCommand(var3, var5);
         }
      }

   }

   public static void issueCommand(Player var0, String var1) {
      ArrayList var2 = new ArrayList();
      String[] var6;
      int var5 = (var6 = var1.split(" ")).length;

      for(int var4 = 0; var4 < var5; ++var4) {
         String var3 = var6[var4];
         var2.add(var3);
      }

      RequestPacket var7 = new RequestPacket(var2);
      CommunityManager.sendPacket(var0, var7);
   }

   @EventHandler
   public void onClose(InventoryCloseEvent var1) {
      String var2 = null;

      try {
         var2 = ServerVersion.higherThanOrEqual(ServerVersionsEnum.S114) ? var1.getView().getTitle() : var1.getView().getTitle();
      } catch (Exception var4) {
         return;
      }

      Player var3 = findbyinv(var1.getInventory());
      if (var2.equals("Select slot")) {
         FileListener.close();
      } else {
         if (var3 != null) {
            invs.remove(var3);
            invpacket.remove(var1.getInventory());
         }

      }
   }
}
