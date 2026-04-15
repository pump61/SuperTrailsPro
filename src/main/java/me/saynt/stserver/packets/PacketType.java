package me.saynt.stserver.packets;

import java.io.Serializable;
import me.saynt.supertrailspro.community2.listener.AuthListener;
import me.saynt.supertrailspro.community2.listener.FileListener;
import me.saynt.supertrailspro.community2.listener.MenuListener;
import me.saynt.supertrailspro.community2.listener.MessageListener;
import me.saynt.supertrailspro.community2.listener.MultiplePacketListener;
import me.saynt.supertrailspro.community2.listener.PacketListener;

public enum PacketType implements Serializable {
   AUTHENTICATION,
   LOGIN,
   ALIVE,
   REQUEST,
   AUTHENTICATION_RESPONE(new AuthListener()),
   LOGIN_RESPONE,
   STATUS_UPDATE,
   PAGE_DISPLAY(new MenuListener()),
   MESSAGE(new MessageListener()),
   EMPTY,
   FileTransfer(new FileListener()),
   MULTIPLE(new MultiplePacketListener());

   PacketListener l;

   private PacketType() {
   }

   private PacketType(PacketListener var3) {
      this.l = var3;
   }

   public PacketListener getListener() {
      return this.l;
   }
}
