package me.saynt.supertrailspro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import javax.annotation.Nullable;
import me.saynt.supertrailspro.eventtrails.ColoredCircle;
import me.saynt.supertrailspro.eventtrails.ConfettiTrail;
import me.saynt.supertrailspro.eventtrails.EventColor;
import me.saynt.supertrailspro.eventtrails.EventDataManager;
import me.saynt.supertrailspro.eventtrails.TrailEvent;
import me.saynt.supertrailspro.eventtrails.fairy.TrailFairy;
import me.saynt.supertrailspro.inventory.InvControl;
import me.saynt.supertrailspro.lang.L;
import me.saynt.supertrailspro.particlelib.ParticleEffect;
import me.saynt.supertrailspro.trails.Trail;
import me.saynt.supertrailspro.trails.TrailType;
import me.saynt.supertrailspro.wings.CustomWings;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.simple.JSONObject;

public class STUtils {
   static HashMap<Integer, ParticleEffect.OrdinaryColor> colors = new HashMap<>();

   public static ItemStack readItemStack(String var0) {
      try {
         // Se começa com letra, é nome de material
         if (!Character.isDigit(var0.charAt(0))) {
            return InvControl.readItemStack(var0);
         } else {
            // Formato legado "id:data" — tenta converter por nome via mapeamento
            String[] var1 = var0.split(":");
            // IDs numéricos não são mais suportados, tenta como nome
            Material mat = Material.getMaterial(var1[0]);
            if (mat == null) {
               PluginMessages.Error("Cannot resolve material from legacy id: " + var0);
               return null;
            }
            return new ItemStack(mat, 1);
         }
      } catch (Exception var4) {
         var4.printStackTrace();
         PluginMessages.Error("Some item id you entered use incorrect format > " + var0);
         return null;
      }
   }

   public static ItemStack idParser(String var0) {
      if (!Character.isDigit(var0.charAt(0))) {
         return InvControl.readItemStack(var0);
      } else {
         String[] var1 = var0.split(":");
         Material mat = Material.getMaterial(var1[0]);
         if (mat == null) return new ItemStack(Material.STONE);
         return new ItemStack(mat, 1);
      }
   }

   public static boolean isEventTrail(Trail var0) {
      if (var0 == null) return false;
      return var0.getType() == TrailType.Event_Color
          || var0.getType() == TrailType.Event_Confetti
          || var0.getType() == TrailType.Event_Fairy
          || var0.getType() == TrailType.Event_Spin;
   }

   public static boolean isNumber(String var0) {
      try {
         Integer.parseInt(var0);
         return true;
      } catch (Exception var2) {
         return false;
      }
   }

   public static int getField(JSONObject var0, String var1) {
      Object val = var0.get(var1);
      if (val == null) return 0;
      if (val instanceof Long) return ((Long) val).intValue();
      if (val instanceof Integer) return (Integer) val;
      // Suporte a String para compatibilidade com novo formato de save
      if (val instanceof String) {
         try { return Integer.parseInt((String) val); } catch (Exception e) { return 0; }
      }
      return 0;
   }

   public static void setColors() {
      colors.put(1,  new ParticleEffect.OrdinaryColor(255, 255, 255));
      colors.put(2,  new ParticleEffect.OrdinaryColor(10, 10, 10));
      colors.put(3,  new ParticleEffect.OrdinaryColor(255, 10, 10));
      colors.put(4,  new ParticleEffect.OrdinaryColor(255, 255, 10));
      colors.put(5,  new ParticleEffect.OrdinaryColor(10, 255, 10));
      colors.put(6,  new ParticleEffect.OrdinaryColor(10, 10, 255));
      colors.put(7,  new ParticleEffect.OrdinaryColor(255, 10, 255));
      colors.put(11, new ParticleEffect.OrdinaryColor(102, 102, 102));
      colors.put(12, new ParticleEffect.OrdinaryColor(143, 225, 249));
      colors.put(13, new ParticleEffect.OrdinaryColor(235, 200, 148));
      colors.put(14, new ParticleEffect.OrdinaryColor(14, 57, 84));
      colors.put(15, new ParticleEffect.OrdinaryColor(239, 103, 103));
      colors.put(16, new ParticleEffect.OrdinaryColor(110, 12, 5));
      colors.put(17, new ParticleEffect.OrdinaryColor(255, 144, 20));
   }

   public static boolean optionb(String var0) {
      return SuperTrails.p.getConfig().getBoolean("Options." + var0);
   }

   public static int WingNameToId(String var0) {
      for (Entry<Integer, String> var1 : CustomWings.ids.entrySet()) {
         if (var1.getValue().equalsIgnoreCase(var0)) {
            return var1.getKey();
         }
      }
      return 0;
   }

   public static String getColorName(int var0) {
      switch (var0) {
         case 1:  return "RainAndWings.White";
         case 2:  return "RainAndWings.Black";
         case 3:  return "RainAndWings.Red";
         case 4:  return "RainAndWings.Yellow";
         case 5:  return "RainAndWings.Green";
         case 6:  return "RainAndWings.Blue";
         case 7:  return "RainAndWings.Purple";
         case 11: return "RainAndWings.Gray";
         case 12: return "RainAndWings.Aqua";
         case 13: return "RainAndWings.Marmoreal";
         case 14: return "RainAndWings.Darkcyan";
         case 15: return "RainAndWings.Pink";
         case 16: return "RainAndWings.Darkred";
         case 17: return "RainAndWings.Orange";
         default: return null;
      }
   }

   public static int ci(String var0) {
      return SuperTrails.p.getConfig().getInt(var0);
   }

   // Sobrecarga com int data mantida para compatibilidade, mas data é ignorada no 1.13+
   public static ItemStack getItem(Material var0, int var1, String var2, List<String> var3) {
      ItemStack var4 = new ItemStack(var0, 1);
      ItemMeta var5 = var4.getItemMeta();
      if (var5 != null) {
         var5.setDisplayName(var2);
         var5.setLore(var3);
         var4.setItemMeta(var5);
      }
      return var4;
   }

   public static ItemStack getItem(Material var0, int var1, String var2) {
      return getItem(var0, var1, var2, new ArrayList<>());
   }

   public static ItemStack getItem(Material var0, int var1, String var2, String var3) {
      ArrayList<String> var4 = new ArrayList<>();
      for (String var5 : var3.split(";")) {
         var4.add(var5);
      }
      return getItem(var0, var1, var2, var4);
   }

   public static ItemStack getPlayerIS(Player var0, ItemStack var1, String var2, @Nullable String var3) {
      ItemMeta var4 = var1.getItemMeta();
      if (var4 == null) return var1;
      var4.setDisplayName(L.get(var0, var2));
      if (var3 != null) {
         var4.setLore(L.getLore(var0, var3));
      }
      var1.setItemMeta(var4);
      return var1;
   }

   // idFix mantido por compatibilidade mas não faz mais nada útil
   public static int idFix(int var0) {
      return var0;
   }

   public static boolean isEven(int var0) {
      return var0 % 2 == 0;
   }

   public static ItemStack getPlayerISEvent(Player var0, ItemStack var1, String var2, @Nullable String var3, TrailEvent var4) {
      ItemMeta var5 = var1.getItemMeta();
      if (var5 == null) return var1;
      String var6 = L.get(var0, var2);
      if (var4.getType() == TrailType.Event_Confetti) {
         ConfettiTrail var7 = (ConfettiTrail) var4;
         var6 = var6.replaceAll("!color1!", L.get(var0, "RainAndWings." + var7.getColors()[0].getName()));
         var6 = var6.replaceAll("!color2!", L.get(var0, "RainAndWings." + var7.getColors()[1].getName()));
         var6 = var6.replaceAll("!color3!", L.get(var0, "RainAndWings." + var7.getColors()[2].getName()));
      } else if (var4.getType() == TrailType.Event_Spin) {
         var6 = var6.replaceAll("!color!", L.get(var0, "RainAndWings." + ((ColoredCircle) var4).getColor().getName()));
      } else if (var4.getType() == TrailType.Event_Color) {
         var6 = var6.replaceAll("!color!", L.get(var0, "RainAndWings." + ((EventColor) var4).getColor().getName()));
      } else if (var4.getType() == TrailType.Event_Fairy) {
         var6 = var6.replaceAll("!color!", L.get(var0, "RainAndWings." + ((TrailFairy) var4).getColor().getName()));
      }
      var5.setDisplayName(var6);
      if (var3 != null) {
         List var10 = L.getLore(var0, var3);
         String var8 = L.get(var0, "Event.Rarity." + var4.getRarity().getName());
         var10 = L.listReplacer(var10, "!r!", var8);
         if (var4.getType() == TrailType.Event_Confetti) {
            ConfettiTrail var9 = (ConfettiTrail) var4;
            var10 = L.listReplacer(var10, "!color1!", L.get(var0, "RainAndWings." + var9.getColors()[0].getName()));
            var10 = L.listReplacer(var10, "!color2!", L.get(var0, "RainAndWings." + var9.getColors()[1].getName()));
            var10 = L.listReplacer(var10, "!color3!", L.get(var0, "RainAndWings." + var9.getColors()[2].getName()));
         } else if (var4.getType() == TrailType.Event_Spin) {
            var10 = L.listReplacer(var10, "!color!", L.get(var0, "RainAndWings." + ((ColoredCircle) var4).getColor().getName()));
         } else if (var4.getType() == TrailType.Event_Color) {
            var10 = L.listReplacer(var10, "!color!", L.get(var0, "RainAndWings." + ((EventColor) var4).getColor().getName()));
         } else if (var4.getType() == TrailType.Event_Fairy) {
            var10 = L.listReplacer(var10, "!color!", L.get(var0, "RainAndWings." + ((TrailFairy) var4).getColor().getName()));
         }
         var5.setLore(var10);
      }
      var1.setItemMeta(var5);
      return var1;
   }

   public static ItemStack getPlayerISEventChest(Player var0, ItemStack var1, String var2, @Nullable String var3) {
      ItemMeta var4 = var1.getItemMeta();
      if (var4 == null) return var1;
      String var5 = L.get(var0, var2);
      var5 = var5.replaceAll("!a!", String.valueOf(EventDataManager.getChestAmount(var0)));
      var4.setDisplayName(var5);
      if (var3 != null) {
         List var6 = L.getLore(var0, var3);
         var6 = L.listReplacer(var6, "!a!", String.valueOf(EventDataManager.getChestAmount(var0)));
         var6 = EventDataManager.canpayChest(var0)
            ? L.listReplacer(var6, "!open!", L.get(var0, "Event.Chest.Open"))
            : L.listReplacer(var6, "!open!", L.get(var0, "Event.Chest.CantOpen"));
         var4.setLore(var6);
      }
      var1.setItemMeta(var4);
      return var1;
   }

   public static String IdToWings(int var0) {
      return CustomWings.ids.get(var0);
   }

   public static int r(int var0, int var1) {
      return new Random().nextInt(var1 - var0 + 1) + var0;
   }

   public static ParticleEffect.OrdinaryColor intToColor(int var0) {
      return colors.get(var0);
   }

   public static ItemStack setName(ItemStack var0, String var1) {
      if (var0 == null) return null;
      ItemMeta var2 = var0.getItemMeta();
      if (var2 == null) return var0;
      var2.setDisplayName(var1);
      var0.setItemMeta(var2);
      return var0;
   }
}