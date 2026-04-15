package me.saynt.supertrailspro.trails;

public enum PixelType {
   Red((byte)1),
   Green((byte)2),
   Blue((byte)3),
   Flame((byte)4),
   Witch((byte)5),
   Drip((byte)6),
   Crit((byte)7),
   None((byte)0);

   byte b;

   private PixelType(byte var3) {
      this.b = var3;
   }

   public byte getByte() {
      return this.b;
   }
}
