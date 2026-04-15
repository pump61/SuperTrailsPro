package me.saynt.supertrailspro.data.mysqlNew;

import java.util.UUID;
import org.bukkit.entity.Player;

public interface SqlService {
   void setup();

   void loadPlayer(UUID var1);

   void clearPlayer(UUID var1);

   void loadPlayer(Player var1);

   void clearPlayer(Player var1);
}
