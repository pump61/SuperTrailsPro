package me.saynt.supertrailspro.particlelib;

import java.util.List;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface ParticleLib {
   void createNMSStack(String var1);
   void play(ParticleEffect var1, Location var2, float var3, float var4, float var5, float var6, int var7);
   void playColoredEffect(ParticleEffect.OrdinaryColor var1, Location var2, int var3);
   void playItemCrack(Object var1, Location var2, float var3, float var4, float var5, float var6, int var7);
   void sendAll(Location var1, Object var2);
   void send(Player var1, Object var2); // removida exceção NMS
   List<Object> getNMSItems();
}