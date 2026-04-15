package me.saynt.stserver.packets;

import org.bukkit.Bukkit;

public class ImAlive extends Packet {
   private static final long serialVersionUID = 4814151827031387018L;
   public int playersamount = 0;

   public ImAlive() {
      super(PacketType.ALIVE);
      this.playersamount = Bukkit.getOnlinePlayers().size();
   }
}
