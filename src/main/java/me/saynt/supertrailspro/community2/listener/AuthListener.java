package me.saynt.supertrailspro.community2.listener;

import javax.annotation.Nullable;
import me.saynt.stserver.packets.AuthResponeType;
import me.saynt.stserver.packets.AuthenticationResponePacket;
import me.saynt.stserver.packets.Packet;
import me.saynt.supertrailspro.community2.CommunityManager;
import org.bukkit.entity.Player;

public class AuthListener extends PacketListener {
   public void read(Packet var1, @Nullable Player var2) {
      AuthenticationResponePacket var3 = (AuthenticationResponePacket)var1;
      AuthResponeType var4 = var3.Respone;
      if (var2 != null) {
         var2.sendMessage(var3.Message);
      }

      if (var4 == AuthResponeType.Success) {
         CommunityManager.success();
      } else if (var4 == AuthResponeType.Fail) {
         CommunityManager.fail();
      } else if (var4 != AuthResponeType.Error) {
         AuthResponeType var10000 = AuthResponeType.Maintenance;
      }

   }
}
