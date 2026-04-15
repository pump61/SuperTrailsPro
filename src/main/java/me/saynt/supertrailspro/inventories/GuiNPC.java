package me.saynt.supertrailspro.inventories;

import java.util.ArrayList;
import java.util.Iterator;
import me.saynt.supertrailspro.data.NPCData;
import me.saynt.supertrailspro.data.NPCManager;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GuiNPC extends Gui {
   GuiItem[] blocks = new GuiItem[54];
   int page = 0;
   NPCData npcinfo = null;

   public GuiNPC() {
      super("NPCManager");
   }

   public String getName() {
      return "NPC Manager";
   }

   public int getInventorySize() {
      return 54;
   }

   public void loot() {
      if (NPCManager.isON) {
         this.downline();
         int var1 = 0;

         for(Iterator var3 = NPCManager.npcs.iterator(); var3.hasNext(); ++var1) {
            NPCData var2 = (NPCData)var3.next();
            if (var1 > 44) {
               break;
            }

            GuiItem var4 = this.getNPCItem(var2);
            this.setItem(var1, var4);
         }

      }
   }

   public GuiItem getNPCItem(NPCData var1) {
      GuiItem var2 = new GuiItem(new ItemStack(Material.PLAYER_HEAD));
      var2.linked(false);
      var2.lore = " ";

      try {
         NPC var3 = var1.getNPC();
         if (var3 == null) {
            var2.setName("§cNPC entity unavailable");
            return var2;
         } else {
            String var4 = var3.getName();
            if (var4 == null) {
               var4 = "Unnamed";
            }

            var4 = "§f" + var4;
            Location var5 = var3.getStoredLocation();
            String var6 = var5 == null ? "§eUnable to get location" : var5.getBlockX() + " " + var5.getBlockY() + " " + var5.getBlockZ();
            var2.setName(var4);
            ArrayList var7 = new ArrayList();
            var7.add("§7Location: " + var6);
            if (var5 != null) {
               var7.add("§aClick to teleport to this NPC");
            } else {
               var7.add("§cYou cannot teleport to this NPC");
            }

            var2.setUnlinkedLore(var7);
            var2.cmd(var1);
            return var2;
         }
      } catch (Exception var8) {
         var8.printStackTrace();
         var2.setName("§cNPC entity unavailable");
         return var2;
      }
   }

   public void click(InventoryClickEvent var1) {
      int var2 = var1.getRawSlot();
      if (var2 == BACKITEM_SLOT) {
         GuiManager.open(this.p, "Main");
      } else if (var2 < this.git.length) {
         GuiItem var3 = this.git[var2];
         if (var3 != null) {
            NPCData var4 = (NPCData)var3.command;
            if (var4 != null) {
               NPC var5 = var4.getNPC();
               if (var5 != null) {
                  Location var6 = var5.getStoredLocation();
                  if (var6 != null) {
                     Player var7 = (Player)var1.getWhoClicked();
                     var7.teleport(var6);
                  }
               }
            }
         }
      }
   }
}
