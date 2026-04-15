package me.saynt.supertrailspro.community2.listener;

import me.saynt.stserver.packets.MessagePacket;
import me.saynt.stserver.packets.Packet;
import org.bukkit.entity.Player;

public class MessageListener extends PacketListener {
   public void read(Packet var1, Player var2) {
      MessagePacket var3 = (MessagePacket)var1;
      if (var2 != null) {
         var2.sendMessage(var3.msg);
      }

   }
}
