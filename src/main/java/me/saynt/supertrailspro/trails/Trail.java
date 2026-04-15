package me.saynt.supertrailspro.trails;

import me.saynt.supertrailspro.ServerVersion;
import me.saynt.supertrailspro.ServerVersionsEnum;

public class Trail {
   protected int id;
   protected String name;
   protected ServerVersionsEnum version;
   protected boolean support = false;

   public Trail(int var1, String var2, ServerVersionsEnum var3) {
      this.id = var1;
      this.name = var2;
      this.version = var3;
      if (ServerVersion.higherThanOrEqual(var3)) {
         this.support = true;
      }

   }

   public Trail(int var1, String var2) {
      this.id = var1;
      this.name = var2;
      this.support = true;
   }

   public boolean isSupported() {
      return this.support;
   }

   public String getName() {
      return this.name;
   }

   public ServerVersionsEnum getVersion() {
      return this.version;
   }

   public int getId() {
      return this.id;
   }

   public TrailType getType() {
      return TrailType.Empty;
   }

   public String getPermission() {
      return null;
   }
}
