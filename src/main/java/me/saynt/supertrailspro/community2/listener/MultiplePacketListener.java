package me.saynt.supertrailspro.community2.listener;

import java.util.Iterator;
import java.util.List;
import me.saynt.stserver.packets.MultiplePackets;
import me.saynt.stserver.packets.Packet;
import org.bukkit.entity.Player;

public class MultiplePacketListener extends PacketListener {
   public void read(Packet var1, Player var2) {
      List var3 = ((MultiplePackets)var1).getPackets();
      Iterator var5 = var3.iterator();

      while(var5.hasNext()) {
         Packet var4 = (Packet)var5.next();
         PacketListener var6 = var4.getType().getListener();
         if (var6 != null) {
            var6.read(var1, var2);
         }
      }

   }
}
