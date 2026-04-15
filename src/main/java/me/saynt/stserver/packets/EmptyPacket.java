package me.saynt.stserver.packets;

public class EmptyPacket extends Packet {
   private static final long serialVersionUID = 386243475737379813L;

   public EmptyPacket() {
      super(PacketType.EMPTY);
   }
}
