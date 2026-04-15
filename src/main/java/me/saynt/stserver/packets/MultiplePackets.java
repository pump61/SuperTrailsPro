package me.saynt.stserver.packets;

import java.util.List;

public class MultiplePackets extends Packet {
   private static final long serialVersionUID = 3372488466056681970L;
   List<Packet> packets;

   public MultiplePackets(List<Packet> var1) {
      super(PacketType.MULTIPLE);
      this.packets = var1;
   }

   public List<Packet> getPackets() {
      return this.packets;
   }
}
