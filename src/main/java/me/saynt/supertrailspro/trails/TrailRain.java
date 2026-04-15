package me.saynt.supertrailspro.trails;

import me.saynt.supertrailspro.ServerVersionsEnum;

public class TrailRain extends Trail {
   public TrailRain() {
      super(201, (String)null, (ServerVersionsEnum)null);
   }

   public TrailType getType() {
      return TrailType.Rain;
   }

   public String getPermission() {
      return "trails.rains";
   }
}
