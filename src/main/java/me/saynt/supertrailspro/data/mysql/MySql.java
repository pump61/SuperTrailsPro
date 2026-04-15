package me.saynt.supertrailspro.data.mysql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;
import me.saynt.supertrailspro.StorageType;
import me.saynt.supertrailspro.SuperTrails;
import me.saynt.supertrailspro.data.DataManager;
import me.saynt.supertrailspro.data.PlayerData;
import me.saynt.supertrailspro.eventtrails.EventDataManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class MySql {
   private static HikariDataSource ds;

   public static void setUpDatabase(String host, String port, String database, String user, String password) {
      HikariConfig config = new HikariConfig();
      config.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false&autoReconnect=true");
      config.setUsername(user);
      config.setPassword(password);
      config.setMaximumPoolSize(10);
      config.setConnectionTimeout(30000);
      ds = new HikariDataSource(config);

      try (Statement st = getConnection().createStatement()) {
         st.executeUpdate("CREATE TABLE IF NOT EXISTS `supertrailspro` (`Player` varchar(64), `Data` varchar(256), `Event` varchar(256))");
         try { st.executeUpdate("ALTER TABLE `supertrailspro` MODIFY Data varchar(256)"); } catch (Exception ignored) {}
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public static Connection getConnection() {
      try {
         return ds.getConnection();
      } catch (Exception e) {
         e.printStackTrace();
         return null;
      }
   }

   public static void loadPlayer(UUID uuid) {
      try (Connection conn = getConnection();
           Statement st = conn.createStatement();
           ResultSet rs = st.executeQuery("SELECT * FROM `supertrailspro` WHERE Player = '" + uuid + "';")) {

         if (!rs.next()) {
            DataManager.getData(uuid).setLoaded(true);
            return;
         }
         String data = rs.getString("Data");
         String event = rs.getString("Event");
         PlayerData pd = DataManager.getData(uuid);
         pd.generateFromString(data);
         pd.getEventData().fromJSON(event);
         pd.setLoaded(true);
      } catch (Exception e) {
         resetConnection(MySqlCommand.Load, uuid);
      }
   }

   public static void savePlayer(UUID uuid) {
      PlayerData pd = DataManager.getData(uuid);
      if (!pd.isLoaded()) return;
      if (pd.isEmpty()) { clearPlayer(uuid); return; }

      try (Connection conn = getConnection();
           Statement st = conn.createStatement()) {
         ResultSet rs = st.executeQuery("SELECT * FROM supertrailspro WHERE Player = '" + uuid + "';");
         if (!rs.next()) {
            st.executeUpdate("INSERT INTO `supertrailspro` (`Player`,`Data`,`Event`) VALUES ('" + uuid + "','" + pd.getString() + "','" + pd.getEventData().toJSON() + "');");
         } else {
            st.executeUpdate("UPDATE `supertrailspro` SET `Data`='" + pd.getString() + "',`Event`='" + pd.getEventData().toJSON() + "' WHERE `Player`='" + uuid + "';");
         }
      } catch (Exception e) {
         resetConnection(MySqlCommand.Save, uuid);
      }
   }

   public static void savePlayerAndUnload(UUID uuid) {
      savePlayer(uuid);
      DataManager.removePlayer(uuid);
      EventDataManager.Justunload(uuid);
   }

   public static void clearPlayer(UUID uuid) {
      try (Connection conn = getConnection();
           Statement st = conn.createStatement()) {
         ResultSet rs = st.executeQuery("SELECT * FROM supertrailspro WHERE Player = '" + uuid + "';");
         if (!rs.next()) return;
         st.executeUpdate("DELETE FROM `supertrailspro` WHERE `Player`='" + uuid + "';");
      } catch (Exception e) {
         resetConnection(MySqlCommand.Remove, uuid);
      }
   }

   public static void resetConnection(MySqlCommand cmd, UUID uuid) {
      try { Thread.sleep(5000L); } catch (InterruptedException ignored) {}
      try {
         if (ds != null && !ds.isClosed()) ds.close();
      } catch (Exception ignored) {}

      ConfigurationSection cfg = SuperTrails.p.getConfig().getConfigurationSection("MYSQL");
      try {
         setUpDatabase(cfg.getString("ip"), cfg.getString("port"), cfg.getString("database"), cfg.getString("login"), cfg.getString("password"));
         DataManager.st = StorageType.DB;
         EventDataManager.st = StorageType.DB;
         if (uuid == null) return;
         MySqlThreads t = new MySqlThreads();
         t.uuid = uuid;
         t.command = cmd;
         t.start();
      } catch (Exception ignored) {}
   }

   public static void uploadPlayers(PlayerData[] players, int retries, Player sender) {
      if (retries <= 0) return;
      try (Connection conn = getConnection();
           Statement st = conn.createStatement()) {
         for (PlayerData pd : players) {
            UUID uuid = pd.getUUID();
            ResultSet rs = st.executeQuery("SELECT * FROM supertrailspro WHERE Player = '" + uuid + "';");
            if (!rs.next()) {
               st.executeUpdate("INSERT INTO `supertrailspro` (`Player`,`Data`,`Event`) VALUES ('" + uuid + "','" + pd.getString() + "','" + pd.getEventData().toJSON() + "');");
            } else {
               st.executeUpdate("UPDATE `supertrailspro` SET `Data`='" + pd.getString() + "',`Event`='" + pd.getEventData().toJSON() + "' WHERE `Player`='" + uuid + "';");
            }
         }
         if (sender != null && sender.isOnline()) sender.sendMessage("§aWoah, data uploaded :)");
      } catch (Exception e) {
         uploadPlayers(players, retries - 1, sender);
         if (sender != null && sender.isOnline() && retries == 1) sender.sendMessage("§cERROR, cannot upload players");
         e.printStackTrace();
      }
   }
}