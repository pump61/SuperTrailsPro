package me.saynt.supertrailspro.trails.modes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.saynt.supertrailspro.P;
import me.saynt.supertrailspro.SuperTrails;
import me.saynt.supertrailspro.data.DataManager;
import me.saynt.supertrailspro.data.PlayerData;
import me.saynt.supertrailspro.inventory.InvMenu;
import me.saynt.supertrailspro.inventory.MenuType;
import me.saynt.supertrailspro.lang.L;
import me.saynt.supertrailspro.trails.Trail;
import me.saynt.supertrailspro.trails.TrailParticle;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Modes {
   public static List<Mode> listmodes = new ArrayList();

   public static void a(PlayerData var0, Trail var1, int var2) {
      if (var1 instanceof TrailParticle) {
         TrailParticle var3 = (TrailParticle)var1;
         if (listmodes.size() >= var2) {
            ((Mode)listmodes.get(var2)).spawn(var0, var3);
         }

      }
   }

   public static void tick() {
      Iterator var1 = listmodes.iterator();

      while(var1.hasNext()) {
         Mode var0 = (Mode)var1.next();
         var0.tick();
      }

   }

   public static void registerMode(Mode var0) {
      listmodes.add(var0);
   }

   public static void b() {
      new Mode("Default", new ItemStack(Material.PAPER), 11);
      new Circle("Circle", new ItemStack(Material.SADDLE), 12);
      new Shield("Magician", new ItemStack(Material.RED_MUSHROOM), 13);
      Bukkit.getServer().getPluginManager().registerEvents(new Shoting("Shooter", new ItemStack(Material.TORCH), 14), SuperTrails.p);
      Bukkit.getServer().getPluginManager().registerEvents(new Slinky("Slinky", new ItemStack(Material.SLIME_BALL), 15), SuperTrails.p);
      createMenu();
      new Pulse("Pulse", new ItemStack(Material.STONE), 16);
      new Helix("Helix", new ItemStack(Material.APPLE), 17);
      new Waves("Dna", new ItemStack(Material.ARROW), 18);
      new Spiral("Spiral", new ItemStack(Material.APPLE), 19);
   }

   public static void reload() {
      Iterator var1 = listmodes.iterator();

      while(var1.hasNext()) {
         Mode var0 = (Mode)var1.next();
         var0.updateValues();
      }

   }

   public static void createMenu() {
      InvMenu var0 = new InvMenu("Trails: Modes", "Modes", MenuType.Modes);
      var0.addClickableItem(49, new ItemStack(Material.ARROW), "Menu.Back", "open=;Trails: Particles");
      Iterator var2 = listmodes.iterator();

      while(var2.hasNext()) {
         Mode var1 = (Mode)var2.next();
         var0.addClickableItemWithLore(var1.slot, var1.is, "Modes." + var1.name, "Modes.Lore", "mode=;1", "trails.mode." + var1.name);
      }

   }

   public static boolean isBlocked(int var0, int var1) {
      try {
         return ((Mode)listmodes.get(var1)).isBlocked(var0);
      } catch (Exception var3) {
         return false;
      }
   }

   public static Mode getNext(Player var0) {
      PlayerData var1 = DataManager.getData(var0);
      int var2 = var1.getMode();

      for(int var3 = 1; listmodes.size() > var3; ++var3) {
         int var4 = var2 + var3 >= listmodes.size() ? var2 + var3 - listmodes.size() : var2 + var3;

         Mode var5;
         try {
            var5 = (Mode)listmodes.get(var4);
         } catch (Exception var7) {
            var1.setMode(0);
            var1.save();
            var5 = (Mode)listmodes.get(var4);
         }

         if (P.has(var0, "trails.mode." + var5.name) && !isBlocked(var1.getTrail(), var4)) {
            return var5;
         }
      }

      return null;
   }

   public static Mode getPrev(Player var0) {
      PlayerData var1 = DataManager.getData(var0);
      int var2 = var1.getMode();

      for(int var3 = 1; listmodes.size() > var3; ++var3) {
         int var4 = var2 - 1 < 0 ? var2 - var3 + listmodes.size() : var2 - var3;

         Mode var5;
         try {
            var5 = (Mode)listmodes.get(var4);
         } catch (Exception var7) {
            var1.setMode(0);
            var1.save();
            var5 = (Mode)listmodes.get(var4);
         }

         if (P.has(var0, "trails.mode." + var5.name) && !isBlocked(var1.getTrail(), var4)) {
            return var5;
         }
      }

      return null;
   }

   public static String replaceMD(Player var0, String var1) {
      Mode var2 = getNext(var0);
      Mode var3 = getPrev(var0);
      Mode var4 = (Mode)listmodes.get(DataManager.getData(var0).getMode());
      if (var3 != null && var2 != null) {
         var1 = var1.replaceAll("!next", L.get(var0, "Modes." + var2.name));
         var1 = var1.replaceAll("!prev", L.get(var0, "Modes." + var3.name));
         var1 = var1.replaceAll("!current", L.get(var0, "Modes." + var4.name));
         return var1;
      } else {
         return null;
      }
   }

   public static int getIdFromCmd(String var0) {
      try {
         String[] var1 = var0.split("=;");
         String var2 = null;
         if (var1.length > 1) {
            var2 = var1[1];
         }

         return Integer.parseInt(var2);
      } catch (Exception var3) {
         return 0;
      }
   }
}
