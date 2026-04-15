package me.saynt.supertrailspro.lang;

public enum Language {
   NoPermission("System.CmdNoPermissionTrail"),
   Next("Menu.Next"),
   Previous("Menu.Prev"),
   DatabaseError("Event.DatabaseErrorCatch");

   String s;

   private Language(String var3) {
      this.s = var3;
   }

   public String getString() {
      return this.s;
   }

   public String toString() {
      return this.s;
   }
}
