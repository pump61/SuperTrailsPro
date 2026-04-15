package me.saynt.supertrailspro.trails;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import me.saynt.supertrailspro.PluginMessages;
import me.saynt.supertrailspro.STUtils;
import me.saynt.supertrailspro.ServerVersion;
import me.saynt.supertrailspro.ServerVersionsEnum;
import me.saynt.supertrailspro.SuperTrails;
import me.saynt.supertrailspro.data.DataManager;
import me.saynt.supertrailspro.data.PlayerData;
import me.saynt.supertrailspro.eventtrails.TimedEvent;
import me.saynt.supertrailspro.eventtrails.fairy.Fairy;
import me.saynt.supertrailspro.inventory.InvControl;
import me.saynt.supertrailspro.modules.TaskModule;
import me.saynt.supertrailspro.spawn.BlockSpawn;
import me.saynt.supertrailspro.trails.modes.Modes;
import me.saynt.supertrailspro.wings.ButterflyWings;
import me.saynt.supertrailspro.wings.CustomWings;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Task {
   public static List<Long> prev = new ArrayList();
   public static long lastMS = 0L;
   public static long minMS = 0L;
   public static long maxMS = 0L;
   public static int tick = 0;
   public static int blocktick = 0;
   public static int wingz = 0;
   public static Fairy fr = null;
   public static List<String> toopen = new ArrayList();
   public static int currenttick = 0;
   public static int currentticka = 0;
   public static int lasttick = 0;
   public static int ttciks = 0;
   public static List<TaskModule> modules = new ArrayList();
   public static int rainbowticks = 0;
   public static boolean invert = false;
   public static boolean lock = false;
   public static int ticks_skipped = 0;

   public static void run() {
      for(int var0 = 1; var0 < 11; ++var0) {
         prev.add(-1L);
      }

      ButterflyWings.setWing();
      BukkitRunnable var3 = new BukkitRunnable() {
         public void run() {
            Modes.tick();
            Rains.getI().tick();
            TimedEvent.tick();
            if (Task.fr != null) {
               Task.fr.lifetick();
            }

            Task.bt();
            if (Task.blocktick == 5) {
               BlockSpawn.next();
            }

            try {
               Iterator var2 = DataManager.p.entrySet().iterator();

               while(true) {
                  Entry var1;
                  PlayerData var3;
                  int var5;
                  do {
                     do {
                        Player var4;
                        do {
                           do {
                              do {
                                 if (!var2.hasNext()) {
                                    return;
                                 }

                                 var1 = (Entry)var2.next();
                                 var3 = (PlayerData)var1.getValue();
                              } while(var3 == null);
                           } while(var1.getKey() == null);

                           var4 = var3.getPlayer();
                        } while(var4 == null && var3.needsPlayer());

                        var5 = var3.getTrail();
                     } while(var5 == 0);
                  } while(var5 >= 300 && var5 <= 399);

                  if (!var3.isHidden()) {
                     TrailsUtil.spawnTrail((PlayerData)var1.getValue(), TrailsUtil.getFromID(var5));
                  }
               }
            } catch (Exception var6) {
               if (PluginMessages.debug) {
                  var6.printStackTrace();
               }
            }

         }
      };
      var3.runTaskTimer(SuperTrails.p, 1L, 2L);
      BukkitRunnable var1 = new BukkitRunnable() {
         public void run() {
            Iterator var2;
            if (!Task.lock) {
               try {
                  if (SuperTrails.p.getConfig().getBoolean("Options.PreventGhostParticles")) {
                     Task.lock = true;
                  }

                  var2 = DataManager.p.entrySet().iterator();

                  label235:
                  while(true) {
                     PlayerData var3;
                     Player var4;
                     do {
                        Entry var1;
                        do {
                           do {
                              if (!var2.hasNext()) {
                                 break label235;
                              }

                              var1 = (Entry)var2.next();
                              var3 = (PlayerData)var1.getValue();
                           } while(var3 == null);
                        } while(var1.getKey() == null);

                        var4 = var3.getPlayer();
                     } while(var4 == null && var3.needsPlayer());

                     int var5 = var3.getTrail();
                     if (var5 >= 300 && var5 <= 400 && var3.tick == Task.currenttick) {
                        int var6 = var3.getWings()[0];
                        if (var6 != 50) {
                           if (!var3.isHidden()) {
                              CustomWings.Display(var3, var5, var3.getWings());
                           }
                        } else if (!var3.isHidden()) {
                           CustomWings.Display(var3, var5, var3.getPattern());
                        }
                     }
                  }
               } catch (Exception var12) {
                  if (PluginMessages.debug) {
                     var12.printStackTrace();
                  }
               } finally {
                  Task.lock = false;
               }
            } else {
               ++Task.ticks_skipped;
            }

            if (ServerVersion.higherThanOrEqual(ServerVersionsEnum.S113) && !STUtils.optionb("ForceHighParticleSpawnRate")) {
               ++Task.currentticka;
               if (Task.currentticka > 5) {
                  Task.currentticka = 0;
               }

               if (STUtils.isEven(Task.currentticka)) {
                  Task.currenttick = Task.currentticka / 2;
               } else {
                  Task.currenttick = -1;
               }
            } else {
               ++Task.currenttick;
               if (Task.currenttick > 2) {
                  Task.currenttick = 0;
               }
            }

            if (Task.currenttick == 0) {
               Task.ttciks = Task.ttciks >= 7 ? 0 : Task.ttciks + 1;
               Task.rainbowticks = Task.rainbowticks >= 13 ? 0 : Task.rainbowticks + 1;
               Task.invert = !Task.invert;
            }

            if (Task.toopen != null && Task.toopen.size() > 0) {
               try {
                  var2 = Task.toopen.iterator();

                  while(var2.hasNext()) {
                     String var14 = (String)var2.next();
                     String var15 = var14.split(";")[0];
                     String var16 = var14.split(";")[1];
                     InvControl.open(var15, var16);
                  }
               } catch (Exception var11) {
               }

               Task.toopen.clear();
            }

         }
      };
      var1.runTaskTimerAsynchronously(SuperTrails.p, 1L, 1L);
      BukkitRunnable var2 = new BukkitRunnable() {
         public void run() {
            Iterator var2 = Task.modules.iterator();

            while(var2.hasNext()) {
               TaskModule var1 = (TaskModule)var2.next();
               var1.tick();
            }

         }
      };
      var2.runTaskTimerAsynchronously(SuperTrails.p, 1L, 1L);
   }

   public static void bt() {
      if (blocktick >= 5) {
         blocktick = 0;
      } else {
         ++blocktick;
      }

   }

   public static void reg(long var0, long var2) {
      lastMS = var2 - var0;
      if (lastMS > maxMS || maxMS == 0L) {
         maxMS = lastMS;
      }

      prev.add(lastMS);
      if (prev.size() > 10) {
         prev.remove(0);
      }

   }

   public static String getColor(long var0) {
      if (var0 < 0L) {
         return "В§7";
      } else if (var0 < 100L) {
         return "В§a";
      } else {
         return var0 < 250L ? "В§e" : "В§c";
      }
   }

   public static void displayStats(Player var0) {
      var0.sendMessage("Max tick = " + getColor(maxMS) + maxMS + "ms");
      var0.sendMessage("Last tick = " + getColor(lastMS) + lastMS + "ms");
      var0.sendMessage("Last records");
      String var1 = "";

      long var2;
      for(Iterator var4 = prev.iterator(); var4.hasNext(); var1 = var1 + getColor(var2) + "в– ") {
         var2 = (Long)var4.next();
      }

      var0.sendMessage(var1);
   }

   public static void FUCKMYLIFEWHYBUKKITSOFUCKINGBUGGY() {
      Iterator var1 = Bukkit.getWorlds().iterator();

      while(var1.hasNext()) {
         World var0 = (World)var1.next();
         Iterator var3 = var0.getEntities().iterator();

         while(var3.hasNext()) {
            Entity var2 = (Entity)var3.next();
            if (var2 instanceof Item) {
               Item var4 = (Item)var2;
               if (var4 != null && var4.getItemStack() != null && var4.getItemStack().hasItemMeta() && var4.getItemStack().getItemMeta().hasDisplayName() && var4.getItemStack().getItemMeta().getDisplayName().toString().contains("§r§fRain_ItId=")) {
                  var4.remove();
               }
            }
         }
      }

   }

   public static void registerTaskModuble(TaskModule var0) {
      modules.add(var0);
   }

   public static void unregisterModule(TaskModule var0) {
      modules.remove(var0);
   }
}
