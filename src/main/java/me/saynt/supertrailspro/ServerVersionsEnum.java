package me.saynt.supertrailspro;

public enum ServerVersionsEnum {
   S18(1, "1.8"),
   S19(2, "1.9"),
   S110(3, "1.10"),
   S111(4, "1.11"),
   S112(5, "1.12"),
   S113(6, "1.13"),
   S114(7, "1.14"),
   S115(8, "1.15"),
   S116(9, "1.16"),
   S117(10, "1.17"),
   S118(11, "1.18"),
   S119(12, "1.19"),
   S120(13, "1.20"),
   S121(14, "1.21"),
   S261(15, "26.1");

   int v;
   public String s;

   private ServerVersionsEnum(int var3, String var4) {
      this.v = var3;
      this.s = var4;
   }

   public int getVersionID() {
      return this.v;
   }
}