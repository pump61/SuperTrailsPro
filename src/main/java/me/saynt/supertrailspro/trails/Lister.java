package me.saynt.supertrailspro.trails;

import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;
import me.saynt.supertrailspro.CmdD;
import me.saynt.supertrailspro.data.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Lister {
   public static HashMap<String, String> they = new HashMap();

   public static void create() {
      they.put("c7aae94b-ebf5-4b55-ad33-ebc3e2ecaff2", "Author:вњ”");
   }

   public static UUID u(String var0) {
      return UUID.fromString(var0);
   }

   public static void show(Player var0, int var1) {
      var0.sendMessage("В§7===Player List===");
      Iterator var3 = Bukkit.getOnlinePlayers().iterator();

      while(var3.hasNext()) {
         Player var2 = (Player)var3.next();
         int var4 = DataManager.getData(var2).getTrail();
         if (!they.containsKey(var2.getUniqueId().toString())) {
            if (var4 != 0) {
               var0.sendMessage(var2.getName() + " В§7using trail with id " + var4);
            }
         } else {
            String[] var5 = ((String)they.get(var0.getUniqueId().toString())).split(":");
            CmdD.sendRaw(var0, "[\"\",{\"text\":\"" + var0.getName() + " \",\"insertion\":\"/tellraw @p %s\"},{\"text\":\"В§a" + var5[1] + "\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + var5[0] + "\"}]}}},{\"text\":\"В§7 using trail with id " + var4 + "\"}]");
         }
      }

   }
}
