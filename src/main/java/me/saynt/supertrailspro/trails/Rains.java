package me.saynt.supertrailspro.trails;

import me.saynt.supertrailspro.ServerVersion;
import me.saynt.supertrailspro.ServerVersionsEnum;
import me.saynt.supertrailspro.spawn.RainsInterface;
import me.saynt.supertrailspro.spawn.RainsNMS;
import me.saynt.supertrailspro.spawn.RainsPackets;

public class Rains {
   public static RainsInterface i;

   public static void initialize() {
      i = (RainsInterface)(ServerVersion.higherThanOrEqual(ServerVersionsEnum.S117) ? new RainsNMS() : new RainsPackets());
   }

   public static RainsInterface getI() {
      return i;
   }
}
