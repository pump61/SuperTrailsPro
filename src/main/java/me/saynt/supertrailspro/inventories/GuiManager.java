package me.saynt.supertrailspro.inventories;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import me.saynt.supertrailspro.P;
import me.saynt.supertrailspro.PluginMessages;
import me.saynt.supertrailspro.SuperTrails;
import me.saynt.supertrailspro.lang.L;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class GuiManager implements Listener {
   static HashMap<String, Gui> guis = new HashMap();
   static HashMap<Player, Gui> open = new HashMap();
   static GuiManager mg;

   public static void setupInvs() {
      mg = new GuiManager();
      Bukkit.getPluginManager().registerEvents(mg, SuperTrails.p);
      loadGUI();
   }

   public static void loadGUI() {
      Gui.BACKITEM_SLOT = SuperTrails.p.getConfig().getInt("Options.Inventory.BackItemSlot");
      guis.put("Wings", new GuiWings());
      guis.put("Rains", new GuiRains());
      guis.put("Event", new GuiEvent());
      guis.put("Blocks", new GuiBlocks());
      guis.put("Particles", new GuiParticles());
      guis.put("Main", new GuiMain());
      guis.put("Languages", new GuiLanguages());
      guis.put("NPCManager", new GuiNPC());
   }

   public static void unload() {
      Iterator var1 = open.entrySet().iterator();

      while(var1.hasNext()) {
         Entry var0 = (Entry)var1.next();
         ((Player)var0.getKey()).closeInventory();
      }

      open.clear();
      guis.clear();
   }

   public static void open(Player var0, String var1) {
      Gui var2 = (Gui)guis.get(var1);
      if (var2 != null) {
         if (var2.getPermission() != null && !P.has(var0, var2.getPermission())) {
            L.msg(var0, var2.getPermissionMessageLink());
         } else {
            Gui var3 = var2.clone();
            open.put(var0, var3);
            var3.open(var0);
         }
      } else {
         var0.sendMessage("No menu by this name");
      }
   }

   @EventHandler
   public void c(InventoryClickEvent var1) {
      Player var2 = (Player)var1.getWhoClicked();
      if (open.containsKey(var2)) {
         try {
            Gui var3 = (Gui)open.get(var2);
            var3.clickA(var1);
         } catch (Exception var4) {
            var1.setCancelled(true);
            if (PluginMessages.debug) {
               var4.printStackTrace();
            }
         }
      }

   }

   @EventHandler
   public void d(InventoryCloseEvent var1) {
      Player var2 = (Player)var1.getPlayer();
      if (open.containsKey(var2)) {
         open.remove(var2);
      }

   }
}
