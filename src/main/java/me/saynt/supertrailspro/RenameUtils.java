package me.saynt.supertrailspro;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class RenameUtils {
   private static String nms = "net.minecraft.server.";
   private static String cb = "org.bukkit.craftbukkit.";
   private static String v = getServerVersion();
   private static Class IChatBC;
   private static Class CraftChatMessage;
   private static Class ChatSerializer;
   private static Class ItemStackNMS;
   private static Class CraftItemStack;
   private static Class NBTTagCompound;
   private static Class NBTTagList;
   private static Class NBTTagString;
   private static Class NBTBase;
   private static Method fromString;
   private static Method a;
   private static Method hasTag;
   private static Method getTag;
   private static Method setTag;
   private static Method asNMSCopy;
   private static Method asBukkitCopy;
   private static Method setString;
   private static Method set;
   private static Method add;
   private static Method nbtsa;

   static {
      if (ServerVersion.getCurrent() == ServerVersionsEnum.S116) {
         try {
            NBTTagCompound = Class.forName(nms + v + ".NBTTagCompound");
            IChatBC = Class.forName(nms + v + ".IChatBaseComponent");
            CraftChatMessage = Class.forName(cb + v + ".util.CraftChatMessage");
            ChatSerializer = IChatBC.getClasses()[0];
            fromString = CraftChatMessage.getMethod("fromString", String.class);
            a = ChatSerializer.getMethod("a", IChatBC);
            ItemStackNMS = Class.forName(nms + v + ".ItemStack");
            hasTag = ItemStackNMS.getMethod("hasTag");
            getTag = ItemStackNMS.getMethod("getTag");
            setTag = ItemStackNMS.getMethod("setTag", NBTTagCompound);
            CraftItemStack = Class.forName(cb + v + ".inventory.CraftItemStack");
            asNMSCopy = CraftItemStack.getMethod("asNMSCopy", ItemStack.class);
            setString = NBTTagCompound.getMethod("setString", String.class, String.class);
            NBTBase = Class.forName(nms + v + ".NBTBase");
            set = NBTTagCompound.getMethod("set", String.class, NBTBase);
            asBukkitCopy = CraftItemStack.getMethod("asBukkitCopy", ItemStackNMS);
            NBTTagList = Class.forName(nms + v + ".NBTTagList");
            NBTTagString = Class.forName(nms + v + ".NBTTagString");
            nbtsa = NBTTagString.getMethod("a", String.class);
         } catch (Exception var1) {
            var1.printStackTrace();
         }
      }

   }

   public static ItemStack create(ItemStack var0, String var1, List<String> var2) {
      try {
         Object var3 = asNMSCopy.invoke((Object)null, var0);
         Object var4 = (Boolean)hasTag.invoke(var3) ? getTag.invoke(var3) : NBTTagCompound.newInstance();
         Object var5 = NBTTagCompound.newInstance();
         if (var1 != null && var1.length() > 0) {
            var1 = ChatColorToJSON(var1, true);
            setString.invoke(var5, "Name", "[" + var1 + "]");
         }

         if (var2 != null && var2.size() > 0) {
            List var6 = (List)NBTTagList.newInstance();
            Iterator var8 = var2.iterator();

            while(var8.hasNext()) {
               String var7 = (String)var8.next();
               var6.add(nbtsa.invoke((Object)null, "[" + ChatColorToJSON(var7, true) + "]"));
            }

            set.invoke(var5, "Lore", var6);
         }

         set.invoke(var4, "display", var5);
         setTag.invoke(var3, var4);
         return (ItemStack)asBukkitCopy.invoke((Object)null, var3);
      } catch (Exception var9) {
         var9.printStackTrace();
         return new ItemStack(Material.AIR);
      }
   }

   public static ItemStack applyTo(ItemStack var0, String var1, List<String> var2) {
      ItemStack var3 = create(var0, var1, var2);
      var0.setItemMeta(var3.getItemMeta());
      return var0;
   }

   public static String ChatColorToJSON(String var0) {
      try {
         Object[] var1 = (Object[])fromString.invoke((Object)null, var0);
         String var2 = (String)a.invoke((Object)null, var1[0]);
         var2 = var2.substring(10, var2.length() - 12);
         return var2;
      } catch (Exception var3) {
         var3.printStackTrace();
         return "";
      }
   }

   public static String ChatColorToJSON(String var0, boolean var1) {
      String var2 = ChatColorToJSON(var0);
      if (var1) {
         return var2.contains("\"italic\":true") ? var2.replaceAll("\"italic\":true", "\"italic\":false") : var2.replaceAll("\"color\":", "\"italic\":false,\"color\":");
      } else {
         return var2;
      }
   }

   public static String getServerVersion() {
      return Bukkit.getServer().getClass().getPackage().getName().substring(23);
   }
}
