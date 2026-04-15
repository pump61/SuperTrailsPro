package me.saynt.supertrailspro.community2.listener;

import javax.annotation.Nullable;
import me.saynt.stserver.packets.Packet;
import org.bukkit.entity.Player;

public abstract class PacketListener {
   public abstract void read(Packet var1, @Nullable Player var2);
}
