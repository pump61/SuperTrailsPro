package me.saynt.supertrailspro.wings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import me.saynt.supertrailspro.trails.Task;
import me.saynt.supertrailspro.trails.TrailWings;
import me.saynt.supertrailspro.trails.WingsPattern;
import me.saynt.supertrailspro.trails.WingsPostFX;
import me.saynt.supertrailspro.wings.fx.BlackInline;
import me.saynt.supertrailspro.wings.fx.DarkInline;
import me.saynt.supertrailspro.wings.fx.GoldenFX;
import me.saynt.supertrailspro.wings.fx.InvertLines;
import me.saynt.supertrailspro.wings.fx.Rainbow;
import me.saynt.supertrailspro.wings.fx.RainbowInline;
import me.saynt.supertrailspro.wings.fx.WhiteInLine;

public class WingsManager {
   public static HashMap<Integer, TrailWings> wings = new HashMap();
   public static HashMap<String, WingsPostFX> ef = new HashMap();
   public static HashMap<String, WingsPattern> patterns = new HashMap();
   public static List<WingsPostFX> fx = new ArrayList();

   public static void addWings(int var0, TrailWings var1) {
      wings.put(var0, var1);
   }

   public static void addPattern(String var0, WingsPattern var1) {
      patterns.put(var0, var1);
   }

   public static WingsPattern getPattern(String var0) {
      return (WingsPattern)patterns.get(var0);
   }

   public static int getWings() {
      int var0 = Task.ttciks >= 4 ? 4 - (Task.ttciks - 4) : Task.ttciks;
      return 7 * var0 + 120;
   }

   public static void loadFX() {
      fx.add(new BlackInline("BlackInline"));
      fx.add(new DarkInline("DarkInline"));
      fx.add(new RainbowInline("RainbowInline"));
      fx.add(new Rainbow("Rainbow"));
      fx.add(new GoldenFX("Gold"));
      fx.add(new InvertLines("InvertLines"));
      fx.add(new WhiteInLine("WhiteInline"));
   }

   public static WingsPostFX findFX(String var0) {
      Iterator var2 = fx.iterator();

      while(var2.hasNext()) {
         WingsPostFX var1 = (WingsPostFX)var2.next();
         if (var1.name.equalsIgnoreCase(var0)) {
            return var1;
         }
      }

      return null;
   }
}
