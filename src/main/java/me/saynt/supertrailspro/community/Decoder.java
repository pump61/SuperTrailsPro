package me.saynt.supertrailspro.community;

import java.io.File;
import org.bukkit.entity.Player;

public class Decoder extends Thread {
   public File f;
   public String name;
   public Player p;
   public String encoded;

   public Decoder(File var1, String var2, Player var3, String var4) {
      this.f = var1;
      this.name = var2;
      this.p = var3;
      this.encoded = var4;
   }

   public void run() {
      FileSender.decodeImage(this.encoded, this.f);
      FileSender.toSetup = this.f.getName();
      FileSender.toSetupName = this.name;
      FileSender.SetupFile(this.p);
   }
}
