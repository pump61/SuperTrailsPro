package me.saynt.supertrailspro.community;

import java.io.File;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

public class Encoder extends Thread {
   public File f;
   public Player p;
   public String name;

   public Encoder(File var1, Player var2, String var3) {
      this.f = var1;
      this.p = var2;
      this.name = var3;
   }

   public void run() {
      String var1 = FileSender.encodeImage(this.f);
      RequestManager.sendRequest(this.p, "filesend-wings-" + ChatColor.stripColor(this.name.replaceAll("-", "")) + "-" + var1);
   }
}
