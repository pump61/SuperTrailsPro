package libs.CustomHeads;

import java.util.logging.Level;
import java.util.logging.Logger;
import libs.CustomHeads.implementation.CustomHeadCreator;
import libs.CustomHeads.implementation.FallbackHeadCreator;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomHeadApi extends JavaPlugin {
   private static final Logger s_log = Logger.getLogger("Minecraft.CustomHeadApi");
   private static String s_prefix = null;
   private static final String s_logFormat = "%s %s";
   private static CustomHeadApi s_instance;
   private IHeadCreator m_headCreator;

   public static CustomHeadApi getInstance() {
      return s_instance;
   }

   static String getPrefix() {
      return s_prefix;
   }

   public IHeadCreator getHeadCreator() {
      return this.m_headCreator;
   }

   public static void log(String var0) {
      if (s_log != null && var0 != null && s_prefix != null) {
         s_log.log(Level.INFO, String.format("%s %s", s_prefix, var0));
      }
   }

   public void onEnable() {
      loadConfig0();
      s_instance = this;
      PluginDescriptionFile var1 = this.getDescription();
      s_prefix = String.format("[%s]", var1.getName());

      try {
         this.m_headCreator = new CustomHeadCreator();
         ItemStack var2 = this.m_headCreator.createItemStack("foo.org", new ItemStack(Material.PLAYER_HEAD, 3));
         if (var2 == null) {
            log("Something went wrong, using the fallback head creator. No custom heads available :(");
            log("Send the above message to the author of the plugin.");
            this.m_headCreator = new FallbackHeadCreator();
         }
      } catch (Error var7) {
         log("Something went wrong, using the fallback head creator. No custom heads available :(");
         log("----------------------------------------------------------------");
         log("Message: " + var7.getMessage());
         log("Stack: ");
         StackTraceElement[] var6;
         int var5 = (var6 = var7.getStackTrace()).length;

         for(int var4 = 0; var4 < var5; ++var4) {
            StackTraceElement var3 = var6[var4];
            log(" " + var3.toString());
         }

         log("Send the above message to the author of the plugin.");
         log("----------------------------------------------------------------");
         this.m_headCreator = new FallbackHeadCreator();
      }

      log("Enabled");
   }

   public void onDisable() {
      log("Disabled");
   }
}
