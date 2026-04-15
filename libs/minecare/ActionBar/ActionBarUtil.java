package libs.minecare.ActionBar;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import me.kvq.supertrailspro.ServerVersion;
import me.kvq.supertrailspro.ServerVersionsEnum;
import me.kvq.supertrailspro.particlelib.ReflectionUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ActionBarUtil {
   private static final String BV = Bukkit.getServer().getClass().getPackage().getName().substring(23);
   private static boolean initialised = false;
   private static Constructor<?> chatSer;
   private static Constructor<?> packetChat;
   private static Method getPlayerHandle;
   private static Field playerConnection;
   private static Method sendPacket;

   static {
      try {
         chatSer = ReflectionUtils.getConstructor(Class.forName("net.minecraft.server." + BV + ".ChatComponentText"), String.class);
         packetChat = Class.forName("net.minecraft.server." + BV + ".PacketPlayOutChat").getConstructor(Class.forName("net.minecraft.server." + BV + ".IChatBaseComponent"), Byte.TYPE);
         getPlayerHandle = Class.forName("org.bukkit.craftbukkit." + BV + ".entity.CraftPlayer").getDeclaredMethod("getHandle");
         playerConnection = Class.forName("net.minecraft.server." + BV + ".EntityPlayer").getDeclaredField("playerConnection");
         sendPacket = Class.forName("net.minecraft.server." + BV + ".PlayerConnection").getDeclaredMethod("sendPacket", Class.forName("net.minecraft.server." + BV + ".Packet"));
         initialised = true;
      } catch (ReflectiveOperationException var1) {
         var1.printStackTrace();
         Bukkit.getServer().getLogger().warning("Cannot initialise Action Bar Utils (Blame fillpant)");
         initialised = false;
      }

   }

   public static void DisplayTime(Player var0, int var1, int var2) {
      String var3 = "" + var1;
      String var4 = "" + var2;
      if (var1 < 10) {
         var3 = "0" + var1;
      }

      if (var2 < 10) {
         var4 = "0" + var2;
      }

      sendActionBar("§e§lEvent " + var3 + ":" + var4, var0);
   }

   public static boolean isInitialised() {
      return initialised;
   }

   public static boolean sendActionBar(String var0, Player... var1) {
      if (ServerVersion.getCurrent() == ServerVersionsEnum.S112 && !initialised) {
         return false;
      } else {
         try {
            Object var2 = chatSer.newInstance(var0);
            Object var3 = packetChat.newInstance(var2, 2);
            sendPacket(var3, var1);
         } catch (ReflectiveOperationException var4) {
            var4.printStackTrace();
            initialised = false;
         }

         return initialised;
      }
   }

   private static void sendPacket(Object var0, Player... var1) {
      Player[] var5 = var1;
      int var4 = var1.length;

      for(int var3 = 0; var3 < var4; ++var3) {
         Player var2 = var5[var3];
         Object var6 = getPlayerHandle.invoke(var2);
         Object var7 = playerConnection.get(var6);
         sendPacket.invoke(var7, var0);
      }

   }
}
