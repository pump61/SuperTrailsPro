package me.saynt.stserver.packets;

import java.util.ArrayList;
import java.util.List;

public class InventoryPacket extends Packet {
   private static final long serialVersionUID = -8306532069304622931L;
   public String name;
   public List<ItemPacket> items = new ArrayList();

   public InventoryPacket() {
      super(PacketType.PAGE_DISPLAY);
   }

   public InventoryPacket(String var1) {
      super(PacketType.PAGE_DISPLAY);
      this.name = var1;
   }
}
