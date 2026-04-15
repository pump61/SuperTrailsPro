package me.saynt.supertrailspro;

import java.util.ArrayList;
import org.bukkit.Bukkit;

public class PluginMessages {
   private static final String ANSI_RESET = "\u001b[0m";
   private static final String ANSI_YELLOW = "\u001b[33m";
   private static final String ANSI_CYAN = "\u001b[36m";
   public static boolean debug = false;

   public static void Error(String var0) {
      try {
         SuperTrails.p.getLogger().warning(var0);
      } catch (Exception var3) {
         // logger not available yet
      }

   }

   public static void Debug(String var0) {
      if (debug) {
         Bukkit.broadcastMessage("§aDEBUG > " + var0);
         SuperTrails.p.getLogger().info("DEBUG > " + var0);
      }
   }

   public static String getRDM() {
      ArrayList var0 = new ArrayList();
      var0.add("That wasn't me!");
      var0.add("You're awesome!");
      var0.add("Hey, how are doing today?");
      var0.add("Santa isn't real, sorry");
      var0.add("Take me to the Mars!");
      var0.add("Achoo!");
      var0.add("42");
      var0.add("");
      var0.add("1.21 Giga Watts!?!");
      var0.add("Thanks for your support!");
      var0.add("More WINGS!");
      var0.add(":P");
      var0.add("We got more messages that nobody reads. What an awesome update!");
      var0.add("You're a whiskas Harry!");
      var0.add("Customizable icons!");
      var0.add("Wings in 3D. Welcome to 2019!");
      var0.add("WOW");
      var0.add("Fun fact: This message is useless");
      var0.add("Hey Vsauce! Michael here.");
      var0.add("Aw man");
      var0.add("No pigs included");
      var0.add("Wake the f up samurai!");
      var0.add("Now with Citizens support!");
      var0.add("Press F for Bees");
      var0.add("Snowier than snow");
      var0.add("You can be stung by bee in game now. Do not attempt irl, it's painful!");
      var0.add("We got BEES");
      var0.add("OOOF");
      var0.add("New graphics engine can render twice more bees than before");
      var0.add("Hide your flowers!");
      var0.add("Glowing squids incoming");
      var0.add("Axolotl not so cute");
      var0.add("Dial-up connecting sounds");
      var0.add("Looking for nearest 5G tower...");
      var0.add("Face mask DLC downloaded");
      var0.add("Red is Impostor, I saw him vent");
      var0.add("Caves update!");
      return (String)var0.get(SuperTrails.r(0, var0.size() - 1));
   }
}
