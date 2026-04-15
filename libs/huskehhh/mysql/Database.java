package libs.huskehhh.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public abstract class Database {
   protected Connection connection = null;

   protected Database() {
   }

   public abstract Connection openConnection();

   public boolean checkConnection() {
      return this.connection != null && !this.connection.isClosed();
   }

   public Connection getConnection() {
      return this.connection;
   }

   public boolean closeConnection() {
      if (this.connection == null) {
         return false;
      } else {
         this.connection.close();
         return true;
      }
   }

   public ResultSet querySQL(String var1) {
      if (!this.checkConnection()) {
         this.openConnection();
      }

      Statement var2 = this.connection.createStatement();
      ResultSet var3 = var2.executeQuery(var1);
      return var3;
   }

   public int updateSQL(String var1) {
      if (!this.checkConnection()) {
         this.openConnection();
      }

      Statement var2 = this.connection.createStatement();
      int var3 = var2.executeUpdate(var1);
      return var3;
   }
}
