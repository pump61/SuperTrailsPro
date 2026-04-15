package libs.huskehhh.mysql.sqlite;

import java.io.File;

import java.sql.Connection;
import java.sql.DriverManager;
import libs.huskehhh.mysql.Database;

public class SQLite extends Database {
   private final String dbLocation;

   public SQLite(String var1) {
      this.dbLocation = var1;
   }

   public Connection openConnection() {
      if (this.checkConnection()) {
         return this.connection;
      } else {
         File var1 = new File("sqlite-db/");
         if (!var1.exists()) {
            var1.mkdirs();
         }

         File var2 = new File(var1, this.dbLocation);
         if (!var2.exists()) {
            try {
               var2.createNewFile();
            } catch (Exception var4) {
               System.out.println("Unable to create database!");
            }
         }

         Class.forName("org.sqlite.JDBC");
         this.connection = DriverManager.getConnection("jdbc:sqlite:" + var1 + "/" + this.dbLocation);
         return this.connection;
      }
   }
}
