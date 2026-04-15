package me.saynt.supertrailspro.community;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import me.saynt.supertrailspro.Reload;
import me.saynt.supertrailspro.STUtils;
import me.saynt.supertrailspro.SuperTrails;
import me.saynt.supertrailspro.lang.L;
import me.saynt.supertrailspro.lang.LanguageManager;
import me.saynt.supertrailspro.wings.CustomWings;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class FileSender {
   public static String toSetup = null;
   public static String toSetupName = null;

   public static void openWings(Player var0) {
      Inventory var1 = Bukkit.createInventory((InventoryHolder) null, 54, "Select wings to send§r");
      int var2 = 9;

      for (Iterator var4 = CustomWings.slots.entrySet().iterator(); var4.hasNext(); ++var2) {
         Entry var3 = (Entry) var4.next();
         ItemStack var5 = (ItemStack) CustomWings.it.get(var3.getKey());
         ItemMeta var6 = var5.getItemMeta();
         var6.setDisplayName(L.get(var0, "Wings." + (String) var3.getKey()));
         ArrayList<String> var7 = new ArrayList<>();
         var7.add(" ");
         var7.add("§eClick here to share");
         var7.add("§7File will be published");
         var7.add("§7you will be able to remove it.");
         var7.add("§8" + (String) var3.getKey());
         var7.add(" ");
         var6.setLore(var7);
         var5.setItemMeta(var6);
         var1.setItem(var2, var5);
      }

      var1.setItem(49, STUtils.getItem(Material.ENDER_EYE, 0, "§7Back"));
      var0.openInventory(var1);
   }

   public static void clickedW(Player var0, ItemStack var1, int var2) {
      if (var2 == 49) {
         RequestManager.sendRequest(var0, "open-main");
      }

      if (var1.hasItemMeta()) {
         ItemMeta var3 = var1.getItemMeta();
         if (var3.hasLore()) {
            List var4 = var3.getLore();
            if (var4.size() >= 5) {
               String var5 = ChatColor.stripColor((String) var4.get(4));
               String var6 = SuperTrails.p.getConfig().getString("Wings." + var5 + ".File");
               if (var6 == null) return;

               File var7 = new File(SuperTrails.p.getDataFolder() + "/wings/models/" + var6);
               if (!var7.exists()) return;

               (new Encoder(var7, var0, var3.getDisplayName())).start();
               RequestManager.sendRequest(var0, "open-main");
            }
         }
      }
   }

   public static void SetupFile(final Player var0) {
      if (toSetup != null) {
         (new BukkitRunnable() {
            public void run() {
               if (var0 != null) {
                  Inventory var1 = Bukkit.createInventory((InventoryHolder) null, 54, "Select wings slot§r§r");
                  for (int var2 = 0; var2 < 54; ++var2) {
                     if (!CustomWings.slots.containsValue(var2)) {
                        var1.setItem(var2, STUtils.getItem(Material.EMERALD_BLOCK, 0, "§aClick to select"));
                     } else {
                        var1.setItem(var2, STUtils.getItem(Material.COAL_BLOCK, 0, "§cThis slot already used"));
                     }
                  }
                  var0.openInventory(var1);
               }
            }
         }).runTaskLater(SuperTrails.p, 1L);
      }
   }

   public static void clickSW(Player var0, int var1) {
      if (CustomWings.slots.containsValue(var1)) {
         var0.sendMessage("§aSlot already used");
      } else {
         if (toSetup == null || toSetupName == null) {
            var0.closeInventory();
            return;
         }

         SuperTrails.p.getConfig().set("Wings." + toSetup.replace(".png", "") + ".File", toSetup);
         SuperTrails.p.getConfig().set("Wings." + toSetup.replace(".png", "") + ".Slot", var1);
         SuperTrails.p.getConfig().set("Wings." + toSetup.replace(".png", "") + ".Item", "288:0");
         SuperTrails.p.saveConfig();
         LanguageManager.getDefault().set("Wings." + toSetup.replace(".png", ""), toSetupName);

         try {
            LanguageManager.getDefault().save(LanguageManager.defaultfile);
         } catch (Exception var3) {
            var3.printStackTrace();
         }

         Reload.a();
         toSetup = null;
         toSetupName = null;
         var0.closeInventory();
         var0.sendMessage("§aWings added");
      }
   }

   public static void startLoading(Player var0, String var1) {
      String[] var2 = var1.split("!!!");
      String var3 = var2[1];
      (new Decoder(new File(SuperTrails.p.getDataFolder() + "/wings/models/" + var3 + ".png"), var2[2], var0, var2[3])).start();
   }

   public static String encodeImage(File var0) {
      try {
         FileInputStream var1 = new FileInputStream(var0);
         byte[] var2 = new byte[(int) var0.length()];
         var1.read(var2);
         var1.close();
         return Base64.getEncoder().encodeToString(var2);
      } catch (Exception var3) {
         var3.printStackTrace();
         return null;
      }
   }

   public static File decodeImage(String var0, File var1) {
      try {
         byte[] var2 = Base64.getDecoder().decode(var0);
         FileOutputStream var3 = new FileOutputStream(var1);
         var3.write(var2);
         var3.close();
         return var1;
      } catch (Exception var4) {
         var4.printStackTrace();
         return null;
      }
   }
}