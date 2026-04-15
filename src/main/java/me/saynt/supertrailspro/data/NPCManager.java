package me.saynt.supertrailspro.data;

import java.io.File;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.Map.Entry;
import me.saynt.supertrailspro.CmdD;
import me.saynt.supertrailspro.PluginMessages;
import me.saynt.supertrailspro.SuperTrails;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class NPCManager implements Listener {
   public static File f;
   public static FileConfiguration c;
   public static List<NPCData> npcs;
   public static List<Player> active;
   public static boolean isON;

   static {
      f = new File(SuperTrails.p.getDataFolder(), "npc.yml");
      c = YamlConfiguration.loadConfiguration(f);
      npcs = new ArrayList();
      active = new ArrayList();
      isON = false;
   }

   public static void initialize() {
      if (Bukkit.getPluginManager().isPluginEnabled("Citizens")) {
         Bukkit.getPluginManager().registerEvents(new NPCManager(), SuperTrails.p);
         PluginMessages.Debug("Initialized events");
         loadNPCs();
         isON = true;
      }

   }

   public static NPCData getNPCData(NPC var0) {
      Iterator var2 = npcs.iterator();

      while(var2.hasNext()) {
         NPCData var1 = (NPCData)var2.next();
         if (var1.getNPC() == var0) {
            return var1;
         }
      }

      return null;
   }

   public static NPCData createOrGetNPCData(NPC var0) {
      NPCData var1 = getNPCData(var0);
      return var1 != null ? var1 : new NPCData();
   }

   public static void updateNPC(NPC var0, PlayerData var1) {
      NPCData var2 = createOrGetNPCData(var0);
      Player var3 = var1.getPlayer();
      if (!var2.isEmpty()) {
         unregisterNPC(var2);
         removeNPC(var2);
         if (var3 != null) {
            var3.sendMessage("§cTrail removed");
         }

      } else {
         var2.setNPC(var0);
         var2.cloneFrom(var1);
         registerNPC(var2);
         saveNPC(var2);
         if (var3 != null) {
            var3.sendMessage("§aYour trail coppied to this npc");
            CmdD.sendRaw(var3, "[\"\",{\"text\":\"[Click here to disable NPC mode]\",\"color\":\"gray\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/supertrails npcmodedisable\"}}]");
         }

      }
   }

   public static void saveNPCS() {
      try {
         c.save(f);
      } catch (Exception var1) {
         var1.printStackTrace();
      }

   }

   public static void toggleMode(Player var0) {
      if (active.contains(var0)) {
         active.remove(var0);
         var0.sendMessage("§eYou disabled NPC mode! You can not modify NPCs now.");
      } else {
         active.add(var0);
         var0.sendMessage("§eYou enabled NPC mode! Click on any NPC to apply your trail, click again to remove.");
      }

   }

   public static void disable(Player var0) {
      if (active.contains(var0)) {
         active.remove(var0);
         var0.sendMessage("§eYou disabled NPC mode! You can not modify NPCs now.");
      }

   }

   public static void registerNPC(NPCData var0) {
      if (var0 != null && var0.getNPC() != null) {
         npcs.add(var0);
         UUID var1 = var0.getNPC().getUniqueId();
         DataManager.p.put(var1, var0);
         PluginMessages.Debug("" + var1);
      }
   }

   public static void unregisterNPC(NPCData var0) {
      if (var0 != null && var0.getNPC() != null) {
         npcs.remove(var0);
         UUID var1 = var0.getNPC().getUniqueId();
         removeFromHM(var1);
         PluginMessages.Debug("" + var1);
      }
   }

   public static void removeFromHM(UUID var0) {
      UUID var1 = null;
      Iterator var3 = DataManager.p.entrySet().iterator();

      while(var3.hasNext()) {
         Entry var2 = (Entry)var3.next();
         UUID var4 = (UUID)var2.getKey();
         if (var4 != null && var4.equals(var0)) {
            var1 = var4;
         }
      }

      PluginMessages.Debug(var1.toString());
      DataManager.p.remove(var1);
   }

   @EventHandler
   public void npcclick(NPCRightClickEvent var1) {
      if (active.contains(var1.getClicker())) {
         updateNPC(var1.getNPC(), DataManager.getData(var1.getClicker()));
      }
   }

   public static void saveNPC(NPCData var0) {
      if (var0 != null && var0.getNPC() != null) {
         if (var0.isEmpty()) {
            removeNPC(var0);
         } else {
            c.set(var0.getNPC().getUniqueId().toString(), var0.toJson());
            saveNPCS();
         }
      }
   }

   public static void removeNPC(NPCData var0) {
      if (var0 != null && var0.getNPC() != null) {
         c.set(var0.getNPC().getUniqueId().toString(), (Object)null);
         saveNPCS();
      }
   }

   public static void loadNPCs() {
      Bukkit.getScheduler().runTaskLater(SuperTrails.p, new Runnable() {
         public void run() {
            NPCRegistry var1 = CitizensAPI.getNPCRegistry();
            if (var1 == null) {
               PluginMessages.Error("Unable to hook into Citizens");
            } else {
               Iterator var3 = NPCManager.c.getKeys(false).iterator();

               while(var3.hasNext()) {
                  String var2 = (String)var3.next();
                  NPC var4 = var1.getByUniqueId(UUID.fromString(var2));
                  if (var4 != null) {
                     NPCData var5 = new NPCData();
                     var5.setNPC(var4);
                     var5.fromJson(NPCManager.c.getString(var2));
                     NPCManager.registerNPC(var5);
                  }
               }

            }
         }
      }, 35L);
   }
}
