package me.saynt.supertrailspro.trails.dreams;

import org.bukkit.entity.Player;

public class DreamRunner extends Thread {
   public static boolean running = true;

   public DreamRunner(Player var1) {
      DreamMaker.trails.put(var1, new RainbowTrail(var1));
      this.start();
   }

   public void run() {
      while(running) {
         DreamMaker.spawn();

         try {
            Thread.currentThread();
            Thread.sleep(20L);
         } catch (InterruptedException var2) {
            var2.printStackTrace();
         }
      }

   }
}
