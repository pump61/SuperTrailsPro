package me.saynt.supertrailspro.eventtrails;

import me.saynt.supertrailspro.ServerVersion;
import me.saynt.supertrailspro.ServerVersionsEnum;
import me.saynt.supertrailspro.particlelib.ParticleEffect;
import me.saynt.supertrailspro.particlelib.PlayParticle;
import me.saynt.supertrailspro.trails.modes.Circle;
import me.saynt.supertrailspro.trails.modes.Modes;
import me.saynt.supertrailspro.wings.ColoredParticle;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class EventSpawner {
   public static void confetti(Player var0, ConfettiTrail var1) {
      if (!ServerVersion.higherThanOrEqual(ServerVersionsEnum.S113)) {
         ParticleEffect.ITEM_CRACK.display(new ParticleEffect.ItemData(Material.INK_SAC, (byte)var1.getColors()[0].getID()), 0.0F, 0.0F, 0.0F, 0.1F, 3, var0.getLocation(), 30.0D);
         ParticleEffect.ITEM_CRACK.display(new ParticleEffect.ItemData(Material.INK_SAC, (byte)var1.getColors()[1].getID()), 0.0F, 0.0F, 0.0F, 0.1F, 3, var0.getLocation(), 30.0D);
         ParticleEffect.ITEM_CRACK.display(new ParticleEffect.ItemData(Material.INK_SAC, (byte)var1.getColors()[2].getID()), 0.0F, 0.0F, 0.0F, 0.1F, 3, var0.getLocation(), 30.0D);
      } else {
         PlayParticle.playItemCrack(var1.getColors()[0].getNMS(), var0.getLocation(), 0.0F, 0.0F, 0.0F, 0.1F, 3);
         PlayParticle.playItemCrack(var1.getColors()[1].getNMS(), var0.getLocation(), 0.0F, 0.0F, 0.0F, 0.1F, 3);
         PlayParticle.playItemCrack(var1.getColors()[2].getNMS(), var0.getLocation(), 0.0F, 0.0F, 0.0F, 0.1F, 3);
      }

   }

   public static void spin(Player var0, ColoredCircle var1) {
      Location var2 = var0.getLocation().clone().add(0.0D, 2.2D, 0.0D);
      var2.setPitch(0.0F);
      var2.setYaw((float)((Circle)Modes.listmodes.get(1)).i);
      var2.add(var2.getDirection().multiply(1));
      ColoredParticle.spawn(var2, var1.getColor().getColor());
   }
}
