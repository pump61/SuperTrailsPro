package me.saynt.supertrailspro.wings;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import me.saynt.supertrailspro.PluginMessages;
import me.saynt.supertrailspro.STUtils;
import me.saynt.supertrailspro.SuperTrails;
import me.saynt.supertrailspro.trails.TrailWings;
import me.saynt.supertrailspro.trails.Wings;
import me.saynt.supertrailspro.trails.WingsPattern;
import org.bukkit.configuration.ConfigurationSection;

public class CustomWingsLoader {
   static int id = 300;
   public static HashMap<Integer, Wings> wings = new HashMap();

   public static void a() {
      File var0 = new File(SuperTrails.p.getDataFolder() + "/wings/models");
      var0.mkdirs();
      ConfigurationSection var1 = SuperTrails.p.getConfig().getConfigurationSection("Wings");
      Iterator var3 = var1.getKeys(false).iterator();

      while(var3.hasNext()) {
         String var2 = (String)var3.next();
         String var4 = var1.getString(var2 + ".File");
         File var5 = new File(var0, var4);
         if (var5.exists() && var5.isFile()) {
            File var6 = null;
            if (var1.getString(var2 + ".NormalMap") != null) {
               String var7 = var1.getString(var2 + ".NormalMap");
               var6 = new File(var0, var7);
            }

            int var14 = var1.getInt(var2 + ".Slot");
            String var8 = var1.getString(var2 + ".Item");
            String var9 = var1.getString(var2 + ".PostFX");
            boolean var10 = var1.getBoolean(var2 + ".Movement");

            try {
               if (id > 399) {
                  PluginMessages.Error("You reached wings limit. Cannot dedicate id " + id + " for wings " + var2);
                  throw new Exception();
               }

               TrailWings var11 = new TrailWings(id, var2, var5, var9, var14, STUtils.readItemStack(var8), var10);
               WingsManager.addWings(id, var11);
            } catch (Exception var12) {
               var12.printStackTrace();
            }

            HashMap var13 = CustomWings.convertWings(var5, var2);
            registerWings(var2, var8, var14, var13);
            if (var6 != null) {
               registerNormal(var6, var2);
            }
         }
      }

      b();
   }

   public static void b() {
      File var0 = new File(SuperTrails.p.getDataFolder() + "/wings/patterns");
      var0.mkdirs();
      ConfigurationSection var1 = SuperTrails.p.getConfig().getConfigurationSection("WingsPatterns");
      Iterator var3 = var1.getKeys(false).iterator();

      while(var3.hasNext()) {
         String var2 = (String)var3.next();
         String var4 = var1.getString(var2 + ".File");
         File var5 = new File(var0, var4);
         if (var5.exists() && var5.isFile()) {
            HashMap var6 = CustomWings.convertPattern(var5);
            int var7 = var1.getInt(var2 + ".Slot");
            String var8 = var1.getString(var2 + ".Item");
            String var9 = var1.getString(var2 + ".PostFX");

            try {
               WingsPattern var10 = new WingsPattern(0, var2, var5, STUtils.readItemStack(var8), var7, var9);
               WingsManager.addPattern(var2, var10);
            } catch (Exception var11) {
               var11.printStackTrace();
            }

            registerPattern(var2, var6, var7, var8);
         }
      }

   }

   public static void registerPattern(String var0, HashMap<String, Integer[]> var1, int var2, String var3) {
      CustomWings.patterns.put(var0, var1);
      CustomWings.patternslot.put(var0, var2);
      CustomWings.patternItem.put(var0, STUtils.readItemStack(var3));
   }

   public static void registerNormal(File var0, String var1) {
      PluginMessages.Debug("Registering normal map" + var1 + " (" + var0.getPath() + ")");
      HashMap var2 = CustomWings.convertWingsNormalMap(var0);
      CustomWings.normal_maps.put(var1, var2);
      PluginMessages.Debug("Registration complete");
   }

   public static void registerWings(String var0, String var1, int var2, HashMap<String, Integer> var3) {
      CustomWings.wings.put(var0, var3);
      CustomWings.it.put(var0, STUtils.readItemStack(var1));
      CustomWings.slots.put(var0, var2);
      CustomWings.ids.put(id, var0);
      wings.put(id, new Wings(id, var0));
      ++id;
   }

   public static void reload() {
      id = 300;
      CustomWings.patternItem.clear();
      CustomWings.patterns.clear();
      CustomWings.patternslot.clear();
      WingsManager.wings.clear();
      WingsManager.patterns.clear();
      CustomWings.wings.clear();
      CustomWings.it.clear();
      CustomWings.slots.clear();
      CustomWings.ids.clear();
      wings.clear();
      a();
   }
}
