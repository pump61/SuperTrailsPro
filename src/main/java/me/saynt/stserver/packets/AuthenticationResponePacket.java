package me.saynt.stserver.packets;

public class AuthenticationResponePacket extends Packet {
   private static final long serialVersionUID = 7718383719582966497L;
   public AuthResponeType Respone;
   public String Message;

   public AuthenticationResponePacket(AuthResponeType var1, String var2) {
      super(PacketType.AUTHENTICATION_RESPONE);
      this.Respone = var1;
      this.Message = var2;
   }
}
