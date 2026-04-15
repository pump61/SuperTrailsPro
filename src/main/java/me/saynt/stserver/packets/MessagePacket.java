package me.saynt.stserver.packets;

public class MessagePacket extends Packet {
   private static final long serialVersionUID = 5571706350297519003L;
   public String msg;

   public MessagePacket(String var1) {
      super(PacketType.MESSAGE);
      this.msg = var1;
   }
}
