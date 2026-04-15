package libs.huskehhh.mysql.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import libs.huskehhh.mysql.Database;

public class MySQL extends Database {
   private final String user;
   private final String database;
   private final String password;
   private final String port;
   private final String hostname;

   public MySQL(String var1, String var2, String var3, String var4) {
      this(var1, var2, (String)null, var3, var4);
   }

   public MySQL(String var1, String var2, String var3, String var4, String var5) {
      this.hostname = var1;
      this.port = var2;
      this.database = var3;
      this.user = var4;
      this.password = var5;
   }

   public Connection openConnection() {
      if (this.checkConnection()) {
         return this.connection;
      } else {
         String var1 = "jdbc:mysql://" + this.hostname + ":" + this.port;
         if (this.database != null) {
            var1 = var1 + "/" + this.database;
         }

         Class.forName("com.mysql.jdbc.Driver");
         this.connection = DriverManager.getConnection(var1, this.user, this.password);
         return this.connection;
      }
   }
}
