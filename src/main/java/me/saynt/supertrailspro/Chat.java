package me.saynt.supertrailspro;

import java.lang.reflect.Method;
import net.md_5.bungee.api.ChatColor;

public class Chat {
   public static ChatColor getColor(String var0) {
      try {
         Class var1 = ChatColor.class;
         Method var2 = var1.getMethod("of", String.class);
         Object var3 = var2.invoke((Object)null, var0);
         return (ChatColor)var3;
      } catch (Exception var4) {
         return ChatColor.WHITE;
      }
   }
}
