package me.saynt.supertrailspro.particlelib;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import me.saynt.supertrailspro.P;
import me.saynt.supertrailspro.ServerVersion;
import me.saynt.supertrailspro.ServerVersionsEnum;
import me.saynt.supertrailspro.SuperTrails;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public enum ParticleEffect {
   EXPLOSION_NORMAL("explode", 0, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   EXPLOSION_LARGE("largeexplode", 1, -1, new ParticleEffect.ParticleProperty[0]),
   EXPLOSION_HUGE("hugeexplosion", 2, -1, new ParticleEffect.ParticleProperty[0]),
   FIREWORKS_SPARK("fireworksSpark", 3, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   WATER_BUBBLE("bubble", 4, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL, ParticleEffect.ParticleProperty.REQUIRES_WATER}),
   WATER_SPLASH("splash", 5, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   WATER_WAKE("wake", 6, 7, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   SUSPENDED("suspended", 7, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.REQUIRES_WATER}),
   SUSPENDED_DEPTH("depthSuspend", 8, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   CRIT("crit", 9, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   CRIT_MAGIC("magicCrit", 10, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   SMOKE_NORMAL("smoke", 11, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   SMOKE_LARGE("largesmoke", 12, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   SPELL("spell", 13, -1, new ParticleEffect.ParticleProperty[0]),
   SPELL_INSTANT("instantSpell", 14, -1, new ParticleEffect.ParticleProperty[0]),
   SPELL_MOB("mobSpell", 15, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.COLORABLE}),
   SPELL_MOB_AMBIENT("mobSpellAmbient", 16, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.COLORABLE}),
   SPELL_WITCH("witchMagic", 17, -1, new ParticleEffect.ParticleProperty[0]),
   DRIP_WATER("dripWater", 18, -1, new ParticleEffect.ParticleProperty[0]),
   DRIP_LAVA("dripLava", 19, -1, new ParticleEffect.ParticleProperty[0]),
   VILLAGER_ANGRY("angryVillager", 20, -1, new ParticleEffect.ParticleProperty[0]),
   VILLAGER_HAPPY("happyVillager", 21, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   TOWN_AURA("townaura", 22, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   NOTE("note", 23, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.COLORABLE}),
   PORTAL("portal", 24, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   ENCHANTMENT_TABLE("enchantmenttable", 25, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   FLAME("flame", 26, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   LAVA("lava", 27, -1, new ParticleEffect.ParticleProperty[0]),
   FOOTSTEP("footstep", 28, -1, new ParticleEffect.ParticleProperty[0]),
   CLOUD("cloud", 29, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   REDSTONE("reddust", 30, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.COLORABLE}),
   SNOWBALL("snowballpoof", 31, -1, new ParticleEffect.ParticleProperty[0]),
   SNOW_SHOVEL("snowshovel", 32, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   SLIME("slime", 33, -1, new ParticleEffect.ParticleProperty[0]),
   HEART("heart", 34, -1, new ParticleEffect.ParticleProperty[0]),
   BARRIER("barrier", 35, 8, new ParticleEffect.ParticleProperty[0]),
   ITEM_CRACK("iconcrack", 36, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL, ParticleEffect.ParticleProperty.REQUIRES_DATA}),
   BLOCK_CRACK("blockcrack", 37, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL, ParticleEffect.ParticleProperty.REQUIRES_DATA}),
   BLOCK_DUST("blockdust", 38, 7, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL, ParticleEffect.ParticleProperty.REQUIRES_DATA}),
   WATER_DROP("droplet", 39, 8, new ParticleEffect.ParticleProperty[0]),
   ITEM_TAKE("take", 40, 8, new ParticleEffect.ParticleProperty[0]),
   MOB_APPEARANCE("mobappearance", 41, 8, new ParticleEffect.ParticleProperty[0]),
   DRAGON_BREATH("dragonbreath", 42, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   END_ROD("endrod", 43, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   DAMAGE("damageindicator", 44, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   FALLING_DUST("fallingdust", 46, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   Spit("spit", 47, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   Totem("totem", 47, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   Soul("soul", 47, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   Soul_Fire("soul_fire", 47, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   NAUTILUS("NAUTILUS", 47, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   LIGHT_SOURCE("LIGHT_SOURCE", 47, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   GOLDEN_SPARKS("GOLDEN_SPARKS"),
   GLOW_SQUID("GLOW_SQUID"),
   PINK("PINK");

   private static final Map<String, ParticleEffect> NAME_MAP = new HashMap();
   private static final Map<Integer, ParticleEffect> ID_MAP = new HashMap();
   private final String name;
   private final int id;
   private final int requiredVersion;
   private final List<ParticleEffect.ParticleProperty> properties;

   static {
      ParticleEffect[] var3;
      int var2 = (var3 = values()).length;

      for(int var1 = 0; var1 < var2; ++var1) {
         ParticleEffect var0 = var3[var1];
         NAME_MAP.put(var0.name, var0);
         ID_MAP.put(var0.id, var0);
      }

   }

   private ParticleEffect(String var3) {
      this(var3, 50, -1);
   }

   private ParticleEffect(String var3, int var4, int var5, ParticleEffect.ParticleProperty... var6) {
      this.name = var3;
      this.id = var4;
      this.requiredVersion = var5;
      this.properties = Arrays.asList(var6);
   }

   public String getName() {
      return this.name;
   }

   public int getId() {
      return this.id;
   }

   public int getRequiredVersion() {
      return this.requiredVersion;
   }

   public boolean hasProperty(ParticleEffect.ParticleProperty var1) {
      return this.properties.contains(var1);
   }

   public boolean isSupported() {
      return true;
   }

   public static ParticleEffect fromName(String var0) {
      Iterator var2 = NAME_MAP.entrySet().iterator();

      while(var2.hasNext()) {
         Entry var1 = (Entry)var2.next();
         if (((String)var1.getKey()).equalsIgnoreCase(var0)) {
            return (ParticleEffect)var1.getValue();
         }
      }

      return null;
   }

   public static ParticleEffect fromId(int var0) {
      Iterator var2 = ID_MAP.entrySet().iterator();

      while(var2.hasNext()) {
         Entry var1 = (Entry)var2.next();
         if ((Integer)var1.getKey() == var0) {
            return (ParticleEffect)var1.getValue();
         }
      }

      return null;
   }

   private static boolean isWater(Location var0) {
      Material var1 = var0.getBlock().getType();
      return var1 == Material.WATER || var1 == Material.WATER;
   }

   private static boolean isLongDistance(Location var0, List<Player> var1) {
      Iterator var3 = var1.iterator();
      if (var3.hasNext()) {
         Player var2 = (Player)var3.next();
         return true;
      } else {
         return false;
      }
   }

   private static boolean isDataCorrect(ParticleEffect var0, ParticleEffect.ParticleData var1) {
      return (var0 == BLOCK_CRACK || var0 == BLOCK_DUST) && var1 instanceof ParticleEffect.BlockData || var0 == ITEM_CRACK && var1 instanceof ParticleEffect.ItemData;
   }

   private static boolean isColorCorrect(ParticleEffect var0, ParticleEffect.ParticleColor var1) {
      return (var0 == SPELL_MOB || var0 == SPELL_MOB_AMBIENT || var0 == REDSTONE) && var1 instanceof ParticleEffect.OrdinaryColor || var0 == NOTE && var1 instanceof ParticleEffect.NoteColor;
   }

   public void display(Location var1, double var2) {
      this.display(0.0F, 0.0F, 0.0F, 0.0F, 3, var1, var2);
   }

   public void display(float var1, float var2, float var3, float var4, int var5, Location var6, double var7) {
      if (ServerVersion.higherThanOrEqual(ServerVersionsEnum.S113)) {
         PlayParticle.play(this, var6, var1, var2, var3, var4, var5);
      } else if (!this.isSupported()) {
         throw new ParticleEffect.ParticleVersionException("This particle effect is not supported by your server version");
      } else if (this.hasProperty(ParticleEffect.ParticleProperty.REQUIRES_DATA)) {
         throw new ParticleEffect.ParticleDataException("This particle effect requires additional data");
      } else if (this.hasProperty(ParticleEffect.ParticleProperty.REQUIRES_WATER) && !isWater(var6)) {
         throw new IllegalArgumentException("There is no water at the center location");
      } else {
         (new ParticleEffect.ParticlePacket(this, var1, var2, var3, var4, var5, var7 > 256.0D, (ParticleEffect.ParticleData)null)).sendTo(var6, var7);
      }
   }

   public void display(float var1, float var2, float var3, float var4, int var5, Location var6, List<Player> var7) {
      this.isSupported();
      if (this.hasProperty(ParticleEffect.ParticleProperty.REQUIRES_DATA)) {
         throw new ParticleEffect.ParticleDataException("This particle effect requires additional data");
      } else if (this.hasProperty(ParticleEffect.ParticleProperty.REQUIRES_WATER) && !isWater(var6)) {
         throw new IllegalArgumentException("There is no water at the center location");
      } else {
         (new ParticleEffect.ParticlePacket(this, var1, var2, var3, var4, var5, isLongDistance(var6, var7), (ParticleEffect.ParticleData)null)).sendTo(var6, var7);
      }
   }

   public void display(float var1, float var2, float var3, float var4, int var5, Location var6, Player... var7) {
      this.display(var1, var2, var3, var4, var5, var6, Arrays.asList(var7));
   }

   public void display(Vector var1, float var2, Location var3, double var4) {
      if (!this.isSupported()) {
         throw new ParticleEffect.ParticleVersionException("This particle effect is not supported by your server version");
      } else if (this.hasProperty(ParticleEffect.ParticleProperty.REQUIRES_DATA)) {
         throw new ParticleEffect.ParticleDataException("This particle effect requires additional data");
      } else if (!this.hasProperty(ParticleEffect.ParticleProperty.DIRECTIONAL)) {
         throw new IllegalArgumentException("This particle effect is not directional");
      } else if (this.hasProperty(ParticleEffect.ParticleProperty.REQUIRES_WATER) && !isWater(var3)) {
         throw new IllegalArgumentException("There is no water at the center location");
      } else {
         (new ParticleEffect.ParticlePacket(this, var1, var2, var4 > 256.0D, (ParticleEffect.ParticleData)null)).sendTo(var3, var4);
      }
   }

   public void display(Vector var1, float var2, Location var3, List<Player> var4) {
      if (!this.isSupported()) {
         throw new ParticleEffect.ParticleVersionException("This particle effect is not supported by your server version");
      } else if (this.hasProperty(ParticleEffect.ParticleProperty.REQUIRES_DATA)) {
         throw new ParticleEffect.ParticleDataException("This particle effect requires additional data");
      } else if (!this.hasProperty(ParticleEffect.ParticleProperty.DIRECTIONAL)) {
         throw new IllegalArgumentException("This particle effect is not directional");
      } else if (this.hasProperty(ParticleEffect.ParticleProperty.REQUIRES_WATER) && !isWater(var3)) {
         throw new IllegalArgumentException("There is no water at the center location");
      } else {
         (new ParticleEffect.ParticlePacket(this, var1, var2, isLongDistance(var3, var4), (ParticleEffect.ParticleData)null)).sendTo(var3, var4);
      }
   }

   public void display(Vector var1, float var2, Location var3, Player... var4) {
      this.display(var1, var2, var3, Arrays.asList(var4));
   }

   public void display(ParticleEffect.ParticleColor var1, Location var2, double var3) {
      if (!this.isSupported()) {
         throw new ParticleEffect.ParticleVersionException("This particle effect is not supported by your server version");
      } else if (!this.hasProperty(ParticleEffect.ParticleProperty.COLORABLE)) {
         throw new ParticleEffect.ParticleColorException("This particle effect is not colorable");
      } else if (!isColorCorrect(this, var1)) {
         throw new ParticleEffect.ParticleColorException("The particle color type is incorrect");
      } else {
         (new ParticleEffect.ParticlePacket(this, var1, var3 > 256.0D)).sendTo(var2, var3);
      }
   }

   public void display(ParticleEffect.ParticleColor var1, Location var2, List<Player> var3) {
      if (!this.isSupported()) {
         throw new ParticleEffect.ParticleVersionException("This particle effect is not supported by your server version");
      } else if (!this.hasProperty(ParticleEffect.ParticleProperty.COLORABLE)) {
         throw new ParticleEffect.ParticleColorException("This particle effect is not colorable");
      } else if (!isColorCorrect(this, var1)) {
         throw new ParticleEffect.ParticleColorException("The particle color type is incorrect");
      } else {
         (new ParticleEffect.ParticlePacket(this, var1, isLongDistance(var2, var3))).sendTo(var2, var3);
      }
   }

   public void display(ParticleEffect.ParticleColor var1, Location var2, Player... var3) {
      this.display(var1, var2, Arrays.asList(var3));
   }

   public void display(ParticleEffect.ParticleData var1, float var2, float var3, float var4, float var5, int var6, Location var7, double var8) {
      if (!this.isSupported()) {
         throw new ParticleEffect.ParticleVersionException("This particle effect is not supported by your server version");
      } else if (!this.hasProperty(ParticleEffect.ParticleProperty.REQUIRES_DATA)) {
         throw new ParticleEffect.ParticleDataException("This particle effect does not require additional data");
      } else if (!isDataCorrect(this, var1)) {
         throw new ParticleEffect.ParticleDataException("The particle data type is incorrect");
      } else {
         (new ParticleEffect.ParticlePacket(this, var2, var3, var4, var5, var6, var8 > 256.0D, var1)).sendTo(var7, var8);
      }
   }

   public void display(ParticleEffect.ParticleData var1, float var2, float var3, float var4, float var5, int var6, Location var7, List<Player> var8) {
      if (!this.isSupported()) {
         throw new ParticleEffect.ParticleVersionException("This particle effect is not supported by your server version");
      } else if (!this.hasProperty(ParticleEffect.ParticleProperty.REQUIRES_DATA)) {
         throw new ParticleEffect.ParticleDataException("This particle effect does not require additional data");
      } else if (!isDataCorrect(this, var1)) {
         throw new ParticleEffect.ParticleDataException("The particle data type is incorrect");
      } else {
         (new ParticleEffect.ParticlePacket(this, var2, var3, var4, var5, var6, isLongDistance(var7, var8), var1)).sendTo(var7, var8);
      }
   }

   public void display(ParticleEffect.ParticleData var1, float var2, float var3, float var4, float var5, int var6, Location var7, Player... var8) {
      this.display(var1, var2, var3, var4, var5, var6, var7, Arrays.asList(var8));
   }

   public void display(ParticleEffect.ParticleData var1, Vector var2, float var3, Location var4, double var5) {
      if (!this.isSupported()) {
         throw new ParticleEffect.ParticleVersionException("This particle effect is not supported by your server version");
      } else if (!this.hasProperty(ParticleEffect.ParticleProperty.REQUIRES_DATA)) {
         throw new ParticleEffect.ParticleDataException("This particle effect does not require additional data");
      } else if (!isDataCorrect(this, var1)) {
         throw new ParticleEffect.ParticleDataException("The particle data type is incorrect");
      } else {
         (new ParticleEffect.ParticlePacket(this, var2, var3, var5 > 256.0D, var1)).sendTo(var4, var5);
      }
   }

   public void display(ParticleEffect.ParticleData var1, Vector var2, float var3, Location var4, List<Player> var5) {
      if (!this.isSupported()) {
         throw new ParticleEffect.ParticleVersionException("This particle effect is not supported by your server version");
      } else if (!this.hasProperty(ParticleEffect.ParticleProperty.REQUIRES_DATA)) {
         throw new ParticleEffect.ParticleDataException("This particle effect does not require additional data");
      } else if (!isDataCorrect(this, var1)) {
         throw new ParticleEffect.ParticleDataException("The particle data type is incorrect");
      } else {
         (new ParticleEffect.ParticlePacket(this, var2, var3, isLongDistance(var4, var5), var1)).sendTo(var4, var5);
      }
   }

   public void display(ParticleEffect.ParticleData var1, Vector var2, float var3, Location var4, Player... var5) {
      this.display(var1, var2, var3, var4, Arrays.asList(var5));
   }

   public static final class BlockData extends ParticleEffect.ParticleData {
      public BlockData(Material var1, byte var2) {
         super(var1, var2);
         if (!var1.isBlock()) {
            throw new IllegalArgumentException("The material is not a block");
         }
      }
   }

   public static final class ItemData extends ParticleEffect.ParticleData {
      public ItemData(Material var1, byte var2) {
         super(var1, var2);
      }
   }

   public static final class NoteColor extends ParticleEffect.ParticleColor {
      private final int note;

      public NoteColor(int var1) {
         if (var1 < 0) {
            throw new IllegalArgumentException("The note value is lower than 0");
         } else if (var1 > 24) {
            throw new IllegalArgumentException("The note value is higher than 24");
         } else {
            this.note = var1;
         }
      }

      public float getValueX() {
         return (float)this.note / 24.0F;
      }

      public float getValueY() {
         return 0.0F;
      }

      public float getValueZ() {
         return 0.0F;
      }
   }

   public static final class OrdinaryColor extends ParticleEffect.ParticleColor {
      public int red;
      public int green;
      public int blue;

      public OrdinaryColor(int var1, int var2, int var3) {
         if (var1 < 0) {
            throw new IllegalArgumentException("The red value is lower than 0");
         } else if (var1 > 255) {
            throw new IllegalArgumentException("The red value is higher than 255");
         } else {
            this.red = var1;
            if (var2 < 0) {
               throw new IllegalArgumentException("The green value is lower than 0");
            } else if (var2 > 255) {
               throw new IllegalArgumentException("The green value is higher than 255");
            } else {
               this.green = var2;
               if (var3 < 0) {
                  throw new IllegalArgumentException("The blue value is lower than 0");
               } else if (var3 > 255) {
                  throw new IllegalArgumentException("The blue value is higher than 255");
               } else {
                  this.blue = var3;
               }
            }
         }
      }

      public ParticleEffect.OrdinaryColor clone() {
         return new ParticleEffect.OrdinaryColor(this.red, this.green, this.blue);
      }

      public int getRed() {
         return this.red;
      }

      public int getGreen() {
         return this.green;
      }

      public int getBlue() {
         return this.blue;
      }

      public float getValueX() {
         return (float)this.red / 255.0F;
      }

      public float getValueY() {
         return (float)this.green / 255.0F;
      }

      public float getValueZ() {
         return (float)this.blue / 255.0F;
      }
   }

   public abstract static class ParticleColor {
      public abstract float getValueX();

      public abstract float getValueY();

      public abstract float getValueZ();
   }

   private static final class ParticleColorException extends RuntimeException {
      private static final long serialVersionUID = 3203085387160737484L;

      public ParticleColorException(String var1) {
         super(var1);
      }
   }

   public abstract static class ParticleData {
      private final Material material;
      private final byte data;
      private final int[] packetData;

      public ParticleData(Material var1, byte var2) {
         this.material = var1;
         this.data = var2;
         this.packetData = new int[]{var1.getId(), var2};
      }

      public Material getMaterial() {
         return this.material;
      }

      public byte getData() {
         return this.data;
      }

      public int[] getPacketData() {
         return this.packetData;
      }

      public String getPacketDataString() {
         return "_" + this.packetData[0] + "_" + this.packetData[1];
      }
   }

   private static final class ParticleDataException extends RuntimeException {
      private static final long serialVersionUID = 3203085387160737484L;

      public ParticleDataException(String var1) {
         super(var1);
      }
   }

   public static final class ParticlePacket {
      private static int version;
      private static Class<?> enumParticle;
      private static Constructor<?> packetConstructor;
      private static Method getHandle;
      private static Field playerConnection;
      private static Method sendPacket;
      private static boolean initialized;
      private final ParticleEffect effect;
      private final float offsetX;
      private final float offsetY;
      private final float offsetZ;
      private final float speed;
      private final int amount;
      private final boolean longDistance;
      private final ParticleEffect.ParticleData data;
      private Object packet;

      public ParticlePacket(ParticleEffect var1, float var2, float var3, float var4, float var5, int var6, boolean var7, ParticleEffect.ParticleData var8) {
         initialize();
         if (var5 < 0.0F) {
            throw new IllegalArgumentException("The speed is lower than 0");
         } else if (var6 < 0) {
            throw new IllegalArgumentException("The amount is lower than 0");
         } else {
            this.effect = var1;
            this.offsetX = var2;
            this.offsetY = var3;
            this.offsetZ = var4;
            this.speed = var5;
            this.amount = var6;
            this.longDistance = var7;
            this.data = var8;
         }
      }

      public ParticlePacket(ParticleEffect var1, Vector var2, float var3, boolean var4, ParticleEffect.ParticleData var5) {
         this(var1, (float)var2.getX(), (float)var2.getY(), (float)var2.getZ(), var3, 0, var4, var5);
      }

      public ParticlePacket(ParticleEffect var1, ParticleEffect.ParticleColor var2, boolean var3) {
         this(var1, var2.getValueX(), var2.getValueY(), var2.getValueZ(), 1.0F, 0, var3, (ParticleEffect.ParticleData)null);
      }

      public static void initialize() {
         if (!initialized) {
            try {
               version = Integer.parseInt(Character.toString(ReflectionUtils.PackageType.getServerVersion().charAt(3)));
               if (ServerVersion.higherThanOrEqual(ServerVersionsEnum.S110)) {
                  version = 8;
               }

               if (version > 7) {
                  enumParticle = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("EnumParticle");
               }

               Class var0 = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass(version < 7 ? "Packet63WorldParticles" : "PacketPlayOutWorldParticles");
               packetConstructor = ReflectionUtils.getConstructor(var0);
               getHandle = ReflectionUtils.getMethod("CraftPlayer", ReflectionUtils.PackageType.CRAFTBUKKIT_ENTITY, "getHandle");
               playerConnection = ReflectionUtils.getField("EntityPlayer", ReflectionUtils.PackageType.MINECRAFT_SERVER, false, "playerConnection");
               sendPacket = ReflectionUtils.getMethod(playerConnection.getType(), "sendPacket", ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("Packet"));
            } catch (Exception var1) {
               throw new ParticleEffect.ParticlePacket.VersionIncompatibleException("Your current bukkit version seems to be incompatible with this library", var1);
            }

            initialized = true;
         }
      }

      public static int getVersion() {
         return version;
      }

      public static boolean isInitialized() {
         return initialized;
      }

      private void initializePacket(Location var1) {
         if (this.packet == null) {
            try {
               this.packet = packetConstructor.newInstance();
               version = 8;
               if (version < 8) {
                  String var2 = this.effect.getName();
                  if (this.data != null) {
                     var2 = var2 + this.data.getPacketDataString();
                  }

                  ReflectionUtils.setValue(this.packet, true, "a", var2);
               } else {
                  ReflectionUtils.setValue(this.packet, true, "a", enumParticle.getEnumConstants()[this.effect.getId()]);
                  ReflectionUtils.setValue(this.packet, true, "j", this.longDistance);
                  if (this.data != null) {
                     ReflectionUtils.setValue(this.packet, true, "k", this.data.getPacketData());
                  }
               }

               ReflectionUtils.setValue(this.packet, true, "b", (float)var1.getX());
               ReflectionUtils.setValue(this.packet, true, "c", (float)var1.getY());
               ReflectionUtils.setValue(this.packet, true, "d", (float)var1.getZ());
               ReflectionUtils.setValue(this.packet, true, "e", this.offsetX);
               ReflectionUtils.setValue(this.packet, true, "f", this.offsetY);
               ReflectionUtils.setValue(this.packet, true, "g", this.offsetZ);
               ReflectionUtils.setValue(this.packet, true, "h", this.speed);
               ReflectionUtils.setValue(this.packet, true, "i", this.amount);
            } catch (Exception var3) {
               throw new ParticleEffect.ParticlePacket.PacketInstantiationException("Packet instantiation failed", var3);
            }
         }
      }

      public void sendTo(Location var1, Player var2) {
         this.initializePacket(var1);
         if (!SuperTrails.permview || P.has(var2, "trails.see")) {
            if (!SuperTrails.toggled.contains(var2)) {
               try {
                  sendPacket.invoke(playerConnection.get(getHandle.invoke(var2)), this.packet);
               } catch (Exception var4) {
                  throw new ParticleEffect.ParticlePacket.PacketSendingException("Failed to send the packet to player '" + var2.getName() + "'", var4);
               }
            }
         }
      }

      public void sendTo(Location var1, List<Player> var2) {
         if (var2.isEmpty()) {
            throw new IllegalArgumentException("The player list is empty");
         } else {
            Iterator var4 = var2.iterator();

            while(var4.hasNext()) {
               Player var3 = (Player)var4.next();
               this.sendTo(var1, var3);
            }

         }
      }

      public void sendTo(Location var1, double var2) {
         if (var2 < 1.0D) {
            throw new IllegalArgumentException("The range is lower than 1");
         } else {
            String var4 = var1.getWorld().getName();
            double var5 = var2 * var2;
            Iterator var8 = Bukkit.getOnlinePlayers().iterator();

            while(var8.hasNext()) {
               Player var7 = (Player)var8.next();
               if (var7.getWorld().getName().equals(var4) && !(var7.getLocation().distanceSquared(var1) > var5)) {
                  this.sendTo(var1, var7);
               }
            }

         }
      }

      public void sendTo(Location var1, double var2, Player var4) {
         if (var2 < 1.0D) {
            throw new IllegalArgumentException("The range is lower than 1");
         } else {
            String var5 = var1.getWorld().getName();
            double var6 = var2 * var2;
            Iterator var9 = Bukkit.getOnlinePlayers().iterator();

            while(var9.hasNext()) {
               Player var8 = (Player)var9.next();
               if (var8.getWorld().getName().equals(var5) && !(var8.getLocation().distanceSquared(var1) > var6)) {
                  this.sendTo(var1, var8);
               }
            }

         }
      }

      private static final class PacketInstantiationException extends RuntimeException {
         private static final long serialVersionUID = 3203085387160737484L;

         public PacketInstantiationException(String var1, Throwable var2) {
            super(var1, var2);
         }
      }

      private static final class PacketSendingException extends RuntimeException {
         private static final long serialVersionUID = 3203085387160737484L;

         public PacketSendingException(String var1, Throwable var2) {
            super(var1, var2);
         }
      }

      private static final class VersionIncompatibleException extends RuntimeException {
         private static final long serialVersionUID = 3203085387160737484L;

         public VersionIncompatibleException(String var1, Throwable var2) {
            super(var1, var2);
         }
      }
   }

   public static enum ParticleProperty {
      REQUIRES_WATER,
      REQUIRES_DATA,
      DIRECTIONAL,
      COLORABLE;
   }

   private static final class ParticleVersionException extends RuntimeException {
      private static final long serialVersionUID = 3203085387160737484L;

      public ParticleVersionException(String var1) {
         super(var1);
      }
   }
}
