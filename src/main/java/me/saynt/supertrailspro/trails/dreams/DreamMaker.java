package me.saynt.supertrailspro.trails.dreams;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import org.bukkit.entity.Player;

public class DreamMaker {
   public static HashMap<Player, DreamTrail> trails = new HashMap();
   static int tick = 0;

   public static void spawn() {
      Iterator var1 = trails.entrySet().iterator();

      while(var1.hasNext()) {
         Entry var0 = (Entry)var1.next();
         ((DreamTrail)var0.getValue()).tickin(tick);
      }

      ++tick;
      if (tick == 10) {
         tick = 0;
      }

   }
}
