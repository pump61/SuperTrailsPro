package me.saynt.supertrailspro.spawn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import me.saynt.supertrailspro.P;
import me.saynt.supertrailspro.SuperTrails;
import me.saynt.supertrailspro.trails.TrailBlocks;
import me.saynt.supertrailspro.trails.TrailsUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;

public class BlockSpawn {
   static HashMap<Block, Integer> btime = new HashMap<>();
   static int time = 0;
   static List<Material> ground = new ArrayList<>();

   public static void prepareblocks() {
      BlockPacketer.prepare();
      String raw = SuperTrails.p.getConfig().getString("Options.BlockTrailsGround").toUpperCase();
      String[] parts = raw.split(",");
      for (String part : parts) {
         Material mat = NewBlock.getMaterialIgnoreErrors(part.trim());
         if (mat != null) {
            ground.add(mat);
         }
      }
   }

   public static void next() {
      for (Entry<?, TrailBlocks> entry : TrailsUtil.pbl.entrySet()) {
         entry.getValue().nextTick();
      }
      despawn();
   }

   public static void spawn(TrailBlocks trailBlocks, Player player) {
      int type = trailBlocks.getBlocksType();
      Location loc = player.getLocation().clone();

      if (type == 0) loc.add(0.0D, -1.0D, 0.0D);

      Block block = loc.getBlock();
      if (type != 0 || isAllowed(block)) {
         if (type != 1 || block.getType() == Material.AIR) {
            Material mat = trailBlocks.getMaterials()[trailBlocks.tick];
            BlockData blockData = mat.createBlockData();
            btime.put(block, 4);

            for (Player p : Bukkit.getOnlinePlayers()) {
               if (p.getWorld() == player.getWorld()
                     && p.getLocation().distance(loc) <= 25.0D) {
                  if (SuperTrails.permview && !P.has(player, "trails.see")) return;
                  try {
                     p.sendBlockChange(loc, blockData);
                  } catch (Exception ignored) {}
               }
            }
         }
      }
   }

   public static void despawn() {
      List<Block> toRemove = new ArrayList<>();

      for (Entry<Block, Integer> entry : btime.entrySet()) {
         if (entry.getValue() > 0) {
            btime.put(entry.getKey(), entry.getValue() - 1);
         } else {
            toRemove.add(entry.getKey());
         }
      }

      for (Block block : toRemove) {
         btime.remove(block);
         Material mat = NewBlock.readMaterial(block);
         BlockData blockData = block.getBlockData(); // estado real do bloco

         for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getWorld() == block.getWorld()
                  && p.getLocation().distance(block.getLocation()) <= 25.0D) {
               try {
                  p.sendBlockChange(block.getLocation(), blockData);
               } catch (Exception ignored) {}
            }
         }
      }
   }

   public static boolean isAllowed(Block block) {
      return ground.contains(NewBlock.readMaterial(block));
   }
}