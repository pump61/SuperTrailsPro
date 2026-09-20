package me.saynt.stserver.packets;

import java.io.File;

import java.nio.file.Files;
import org.jetbrains.annotations.Nullable;

public class FilePacket extends Packet {
   private static final long serialVersionUID = 95138872618314958L;
   public FileDescription description;
   public String filename;
   public String displayname;
   byte[] file;
   public String data;

   public FilePacket(File var1, FileDescription var2, @Nullable String var3, @Nullable String var4) {
      super(PacketType.FileTransfer);
      this.readFile(var1);
      this.description = var2;
      this.filename = var3 == null ? var1.getName() : var3;
      // removeExtension substituído por lógica nativa
      String name = var1.getName();
      int dot = name.lastIndexOf('.');
      this.displayname = var4 == null ? (dot > 0 ? name.substring(0, dot) : name) : var4;
   }

   public boolean readFile(File var1) {
      try {
         this.file = Files.readAllBytes(var1.toPath());
         return true;
      } catch (Exception e) {
         e.printStackTrace();
         return false;
      }
   }

   public boolean writeFile(File var1) {
      try {
         var1.getParentFile().mkdirs();
         Files.write(var1.toPath(), this.file);
         return true;
      } catch (Exception e) {
         e.printStackTrace();
         return false;
      }
   }

   public String[] getData() {
      return this.data == null ? new String[0] : this.data.split(";");
   }

   public void addData(String var1) {
      if (this.getData().length == 0) {
         this.data = var1;
      } else {
         this.data = this.data + ";" + var1;
      }
   }

   public void setData(String var1) { this.data = var1; }
   public void resetData() { this.data = null; }
}