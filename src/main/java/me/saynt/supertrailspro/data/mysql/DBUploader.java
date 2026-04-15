package me.saynt.supertrailspro.data.mysql;

import java.util.Iterator;
import java.util.UUID;
import me.saynt.supertrailspro.data.ConfigStorage;
import me.saynt.supertrailspro.data.PlayerData;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class DBUploader extends Thread {
   Player issued;
   PlayerData[] pds;

   public DBUploader(Player var1) {
      this.issued = var1;
      YamlConfiguration var2 = YamlConfiguration.loadConfiguration(ConfigStorage.f);
      int var3 = var2.getKeys(false).size();
      this.pds = new PlayerData[var3];
      int var4 = 0;

      for(Iterator var6 = var2.getKeys(false).iterator(); var6.hasNext(); ++var4) {
         String var5 = (String)var6.next();
         PlayerData var7 = new PlayerData(UUID.fromString(var5), false, true);
         var7.fromJson(var2.getString(var5));
         this.pds[var4] = var7;
      }

   }

   public void run() {
      this.sendMessage("§aUpload started, please wait");
      MySql.uploadPlayers(this.pds, 5, this.issued);
   }

   public void sendMessage(String var1) {
      if (this.issued != null && this.issued.isOnline()) {
         this.issued.sendMessage(var1);
      }

   }
}
