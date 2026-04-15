package me.saynt.supertrailspro.trails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WingOffset {
   private HashMap<Integer, List<Integer>> positions = new HashMap();
   private HashMap<String, Integer> color = new HashMap();

   public void add(int var1, int var2, int var3) {
      if (this.positions.containsKey(var2)) {
         ((List)this.positions.get(var2)).add(var1);
      } else {
         this.positions.put(var2, new ArrayList());
      }

      this.color.put(var2 + ":" + var1, var3);
   }

   public List<Integer> getForX(int var1) {
      return (List)(this.positions.containsKey(var1) ? (List)this.positions.get(var1) : new ArrayList());
   }

   public int getColorFor(int var1, int var2) {
      return this.color.containsKey(var1 + ":" + var2) ? (Integer)this.color.get(var1 + ":" + var2) : 0;
   }
}
