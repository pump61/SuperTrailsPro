package me.saynt.supertrailspro.community2;

import java.util.TimerTask;
import me.saynt.stserver.packets.ImAlive;

public class CommunityAlive extends TimerTask {
   public void run() {
      try {
         CommunityManager.community.sendPacket(new ImAlive());
      } catch (Exception var2) {
      }

   }
}
