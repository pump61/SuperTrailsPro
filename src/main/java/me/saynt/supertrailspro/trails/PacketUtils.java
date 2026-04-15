package me.saynt.supertrailspro.trails;

import java.util.Iterator;
import me.saynt.supertrailspro.P;
import me.saynt.supertrailspro.SuperTrails;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PacketUtils {

   // sendPacket individual não é mais necessário com API Bukkit
   // mantido vazio para não quebrar chamadas existentes
   public static void sendPacket(Player player, Object packet) {
      // não utilizado no 1.21.4 — packets NMS foram substituídos por API Bukkit
   }

   public static void sendPacketNearby(Location loc, Object packet, double radius, boolean force) {
      // não utilizado no 1.21.4
   }

   public static void sendPacketNearby(Location loc, Object packet, boolean force) {
      // não utilizado no 1.21.4
   }

   // Retorna um ItemStack Bukkit simples — NMS não é mais necessário
   public static Object createNewNMSItem(String materialName) {
      try {
         org.bukkit.Material mat = org.bukkit.Material.getMaterial(materialName);
         if (mat == null) return null;
         return new ItemStack(mat);
      } catch (Exception e) {
         e.printStackTrace();
         return null;
      }
   }

   public static Object createNewNMSItem(ItemStack stack) {
      return stack;
   }

   // Mantido para compatibilidade mas não é mais necessário
   public static String getFN(String... names) {
      int idx = SuperTrails.currentversion.getVersionID() - 1;
      return idx < names.length ? names[idx] : names[names.length - 1];
   }
}