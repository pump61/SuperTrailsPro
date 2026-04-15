package me.saynt.supertrailspro.connect;

public enum ResponseType {
   Empty,
   Check,
   Read,
   File,
   Error;

   String name = null;

   private ResponseType() {
   }

   private ResponseType(String var3) {
      this.name = var3;
   }

   public static ResponseType getByName(String var0) {
      if (var0 == null) {
         return Empty;
      } else {
         ResponseType[] var4;
         int var3 = (var4 = values()).length;

         for(int var2 = 0; var2 < var3; ++var2) {
            ResponseType var1 = var4[var2];
            if (var1.name != null && var1.name.equals(var0)) {
               return var1;
            }
         }

         return Empty;
      }
   }
}
