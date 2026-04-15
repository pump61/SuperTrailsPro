package me.saynt.supertrailspro.data.mysql;

import java.util.UUID;

public class MySqlThreads extends Thread {
   public UUID uuid;
   public MySqlCommand command;

   public void run() {
      if (this.uuid != null && this.command != null) {
         if (this.command == MySqlCommand.SetUp) {
            MySqlManager.setUp();
         } else if (this.command == MySqlCommand.Load) {
            MySql.loadPlayer(this.uuid);
         } else if (this.command == MySqlCommand.Save) {
            MySql.savePlayer(this.uuid);
         } else if (this.command == MySqlCommand.Remove) {
            MySql.clearPlayer(this.uuid);
         } else if (this.command == MySqlCommand.Unload) {
            MySql.savePlayerAndUnload(this.uuid);
         }

      }
   }
}
