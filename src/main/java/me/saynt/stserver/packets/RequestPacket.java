package me.saynt.stserver.packets;

import java.util.ArrayList;
import java.util.List;

public class RequestPacket extends Packet {
   private static final long serialVersionUID = 1L;
   List<String> str;
   String player;

   public RequestPacket(String var1) {
      super(PacketType.REQUEST);
      this.str = new ArrayList();
      this.str.add(var1);
   }

   public RequestPacket(List<String> var1) {
      super(PacketType.REQUEST);
      this.str = var1;
   }

   public RequestPacket(String var1, String var2) {
      super(PacketType.REQUEST);
      this.str = new ArrayList();
      this.str.add(var1);
   }

   public RequestPacket(List<String> var1, String var2) {
      super(PacketType.REQUEST);
      this.str = var1;
   }

   public List<String> getRequest() {
      return this.str;
   }
}
