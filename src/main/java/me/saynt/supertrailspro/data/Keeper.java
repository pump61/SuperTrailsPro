package me.saynt.supertrailspro.data;

import me.saynt.supertrailspro.PluginMessages;
import me.saynt.supertrailspro.STUtils;
import me.saynt.supertrailspro.inventories.GuiItem;
import me.saynt.supertrailspro.lang.L;
import me.saynt.supertrailspro.trails.TrailWings;
import me.saynt.supertrailspro.trails.TrailsUtil;
import me.saynt.supertrailspro.trails.WingsPattern;
import me.saynt.supertrailspro.wings.WingsManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Keeper {
   public int wings_id_a;
   public int color_b;
   public int color_c;
   public int color_d;
   public String pattern_e;
   public String mk = "Menu.Keeper.";

   public JSONObject toJson() {
      if (this.wings_id_a == 0) {
         return null;
      } else {
         JSONObject var1 = new JSONObject();
         var1.put("a", this.wings_id_a);
         if (this.pattern_e == null) {
            var1.put("b", this.color_b);
            var1.put("c", this.color_c);
            var1.put("d", this.color_c);
         } else {
            var1.put("e", this.pattern_e);
         }

         return var1;
      }
   }

   public Keeper fromJson(JSONObject var1) {
      new JSONParser();
      if (var1 == null) {
         return this;
      } else {
         this.wings_id_a = this.getField(var1, "a");
         this.color_b = this.getField(var1, "b");
         this.color_c = this.getField(var1, "c");
         this.color_d = this.getField(var1, "d");
         this.pattern_e = (String)var1.get("e");
         PluginMessages.Debug(this.wings_id_a + " " + this.color_b + " " + this.color_c + " " + this.color_d + " " + this.pattern_e);
         return this;
      }
   }

   public int getField(JSONObject var1, String var2) {
      Long var3 = (Long)var1.get(var2);
      return var3 == null ? 0 : var3.intValue();
   }

   public boolean isEmpty() {
      return this.wings_id_a == 0 && this.color_b == 0 && this.color_c == 0 && this.color_d == 0 && this.pattern_e == null;
   }

   public void setA(int var1) {
      this.wings_id_a = var1;
   }

   public void setB(int var1) {
      this.color_b = var1;
   }

   public void setC(int var1) {
      this.color_c = var1;
   }

   public void setD(int var1) {
      this.color_d = var1;
   }

   public void setE(String var1) {
      this.pattern_e = var1;
   }

   public boolean isPattern() {
      return this.pattern_e != null;
   }

   public String getWingsName(Player var1) {
      int var2 = this.wings_id_a;

      try {
         TrailWings var3 = (TrailWings)TrailsUtil.getFromID(var2);
         return var3 == null ? L.get(var1, this.mk + "Undefined") : L.get(var1, "Wings." + var3.getName());
      } catch (Exception var4) {
         return L.get(var1, this.mk + "Undefined");
      }
   }

   public String getColorName(Player var1, int var2) {
      String var3 = STUtils.getColorName(var2);
      return var3 == null ? L.get(var1, this.mk + "Undefined") : L.get(var1, var3);
   }

   public String getPatternName(Player var1) {
      WingsPattern var2 = WingsManager.getPattern(this.pattern_e);
      return var2 == null ? L.get(var1, this.mk + "Undefined") : L.get(var1, "Pattern." + var2.getName());
   }

   public void reset() {
      this.wings_id_a = 0;
      this.color_b = 0;
      this.color_c = 0;
      this.color_d = 0;
      this.pattern_e = null;
   }

   public void setWings(Player var1) {
      PlayerData var2 = DataManager.getData(var1);

      try {
         if (this.wings_id_a == 0) {
            L.msg(var1, "Menu.Keeper.SetUnfinishedError");
            return;
         }

         if (this.pattern_e == null) {
            TrailWings var3 = (TrailWings)TrailsUtil.getFromID(this.wings_id_a);
            if (var3 == null) {
               L.msg(var1, "Menu.Keeper.SetUnfinishedError");
               return;
            }

            if (!var3.isValid(this.color_b != 0, this.color_c != 0, this.color_d != 0)) {
               L.msg(var1, "Menu.Keeper.SetUnfinishedError");
               return;
            }

            if (!var3.hasPermission(var1)) {
               L.msg(var1, "System.NoWingsPermission");
               return;
            }

            var2.setWings(this.wings_id_a, new int[]{this.color_b, this.color_c, this.color_d});
         } else {
            WingsPattern var6 = WingsManager.getPattern(this.pattern_e);
            if (var6 == null) {
               L.msg(var1, "Menu.Keeper.SetUnfinishedError");
               return;
            }

            TrailWings var4 = (TrailWings)TrailsUtil.getFromID(this.wings_id_a);
            if (var4 == null) {
               L.msg(var1, "Menu.Keeper.SetUnfinishedError");
               return;
            }

            if (!var4.hasPermission(var1)) {
               L.msg(var1, "System.NoWingsPermission");
               return;
            }

            if (!var6.hasPermission(var1)) {
               L.msg(var1, "System.NoPatternPermission");
               return;
            }

            var2.setPattenWings(this.wings_id_a, var6.getName());
         }
      } catch (Exception var5) {
         var5.printStackTrace();
      }

   }

   public GuiItem getItem(Player var1) {
      String var2 = null;
      if (this.isEmpty()) {
         var2 = "Menu.Keeper.Empty";
      } else if (this.pattern_e == null) {
         var2 = "Menu.Keeper.ColorWings";
      } else {
         var2 = "Menu.Keeper.PatternWings";
      }

      TrailWings var3 = this.getWings();
      ItemStack var4 = var3 != null && var3.getItem() != null ? var3.getItem() : new ItemStack(Material.OAK_BUTTON);
      GuiItem var5 = new GuiItem(var4, var2 + ".Name", var2 + ".Lore");
      var5.addPlaceholder("!wingsname!", this.getWingsName(var1)).addPlaceholder("!color1!", this.getColorName(var1, this.color_b)).addPlaceholder("!color2!", this.getColorName(var1, this.color_c)).addPlaceholder("!color3!", this.getColorName(var1, this.color_d)).addPlaceholder("!pattern!", this.getPatternName(var1));
      return var5;
   }

   public TrailWings getWings() {
      return this.wings_id_a == 0 ? null : (TrailWings)WingsManager.wings.get(this.wings_id_a);
   }

   public int getColor(int var1) {
      if (var1 == 0) {
         return this.color_b;
      } else if (var1 == 1) {
         return this.color_c;
      } else {
         return var1 == 2 ? this.color_d : 0;
      }
   }

   public WingsPattern getPattern() {
      return !this.isPattern() ? null : WingsManager.getPattern(this.pattern_e);
   }
}
