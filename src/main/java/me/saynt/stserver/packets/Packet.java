package me.saynt.stserver.packets;

import java.io.Serializable;

public abstract class Packet implements Serializable {
   private static final long serialVersionUID = -6224124659158394718L;
   private PacketType type;

   public Packet(PacketType var1) {
      this.type = var1;
   }

   public PacketType getType() {
      return this.type;
   }
}
