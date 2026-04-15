package me.saynt.stserver.packets;

public class AuthenticationPacket extends Packet {
   private static final long serialVersionUID = 4843862264221273479L;
   public String key;
   public int port;
   public int server_version;
   public int plugin_version;

   public AuthenticationPacket(String var1, int var2, int var3, int var4) {
      super(PacketType.AUTHENTICATION);
      this.key = var1;
      this.port = var2;
      this.server_version = var3;
      this.plugin_version = var4;
   }
}
