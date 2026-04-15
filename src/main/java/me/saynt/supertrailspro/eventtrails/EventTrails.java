package me.saynt.supertrailspro.eventtrails;

import java.util.HashMap;
import me.saynt.supertrailspro.eventtrails.fairy.TrailFairy;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class EventTrails {
   public static HashMap<Integer, TrailEvent> i = new HashMap();
   public static HashMap<Integer, String> colornames = new HashMap();

   public static void load() {
      i.put(401, new ConfettiTrail(401, "Confetti", new ItemStack(Material.INK_SAC, 1, (byte)ConfettiColor.Red.getID()), new ConfettiColor[]{ConfettiColor.Red, ConfettiColor.Yellow, ConfettiColor.Green}, Rarity.COMMON));
      i.put(402, new ConfettiTrail(402, "Confetti", new ItemStack(Material.INK_SAC, 1, (byte)ConfettiColor.Purple.getID()), new ConfettiColor[]{ConfettiColor.White, ConfettiColor.Black, ConfettiColor.Purple}, Rarity.COMMON));
      i.put(403, new ConfettiTrail(403, "Confetti", new ItemStack(Material.INK_SAC, 1, (byte)ConfettiColor.Blue.getID()), new ConfettiColor[]{ConfettiColor.White, ConfettiColor.Red, ConfettiColor.Blue}, Rarity.COMMON));
      i.put(404, new ConfettiTrail(404, "Confetti", new ItemStack(Material.INK_SAC, 1, (byte)ConfettiColor.Green.getID()), new ConfettiColor[]{ConfettiColor.Green, ConfettiColor.Blue, ConfettiColor.Black}, Rarity.COMMON));
      i.put(405, new ConfettiTrail(405, "Confetti", new ItemStack(Material.INK_SAC, 1, (byte)ConfettiColor.White.getID()), new ConfettiColor[]{ConfettiColor.Red, ConfettiColor.Yellow, ConfettiColor.White}, Rarity.RARE));
      i.put(406, new ConfettiTrail(406, "Confetti", new ItemStack(Material.INK_SAC, 1, (byte)ConfettiColor.Yellow.getID()), new ConfettiColor[]{ConfettiColor.Black, ConfettiColor.Yellow, ConfettiColor.Purple}, Rarity.RARE));
      i.put(407, new ConfettiTrail(407, "Confetti", new ItemStack(Material.INK_SAC, 1, (byte)ConfettiColor.Black.getID()), new ConfettiColor[]{ConfettiColor.Red, ConfettiColor.Green, ConfettiColor.Black}, Rarity.EPIC));
      i.put(411, new ColoredCircle(411, "ColorSpin", ParticleColors.White.getItemStack(), ParticleColors.White, Rarity.COMMON));
      i.put(412, new ColoredCircle(412, "ColorSpin", ParticleColors.Red.getItemStack(), ParticleColors.Red, Rarity.COMMON));
      i.put(413, new ColoredCircle(413, "ColorSpin", ParticleColors.Yellow.getItemStack(), ParticleColors.Yellow, Rarity.COMMON));
      i.put(414, new ColoredCircle(414, "ColorSpin", ParticleColors.Green.getItemStack(), ParticleColors.Green, Rarity.COMMON));
      i.put(415, new ColoredCircle(415, "ColorSpin", ParticleColors.Blue.getItemStack(), ParticleColors.Blue, Rarity.RARE));
      i.put(416, new ColoredCircle(416, "ColorSpin", ParticleColors.Purple.getItemStack(), ParticleColors.Purple, Rarity.RARE));
      i.put(417, new ColoredCircle(417, "ColorSpin", ParticleColors.Black.getItemStack(), ParticleColors.Black, Rarity.EPIC));
      i.put(421, new EventColor(421, "Color", HeadColor.Gray, Rarity.COMMON));
      i.put(422, new EventColor(422, "Color", HeadColor.Aqua, Rarity.COMMON));
      i.put(423, new EventColor(423, "Color", HeadColor.Marmoreal, Rarity.COMMON));
      i.put(424, new EventColor(424, "Color", HeadColor.Dark_Cyan, Rarity.RARE));
      i.put(425, new EventColor(425, "Color", HeadColor.Pink, Rarity.RARE));
      i.put(426, new EventColor(426, "Color", HeadColor.Dark_Red, Rarity.EPIC));
      i.put(427, new EventColor(427, "Color", HeadColor.Orange, Rarity.EPIC));
      i.put(431, new TrailFairy(431, "Fairy", ParticleColors.Green, Rarity.COMMON));
      i.put(432, new TrailFairy(432, "Fairy", ParticleColors.Blue, Rarity.RARE));
      i.put(433, new TrailFairy(433, "Fairy", ParticleColors.Purple, Rarity.RARE));
      i.put(434, new TrailFairy(434, "Fairy", ParticleColors.Red, Rarity.EPIC));
      i.put(435, new TrailFairy(435, "Fairy", ParticleColors.Black, Rarity.EPIC));
      i.put(436, new TrailFairy(436, "Fairy", ParticleColors.White, Rarity.EPIC));
      i.put(437, new TrailFairy(437, "Fairy", ParticleColors.Yellow, Rarity.EPIC));
   }
}
