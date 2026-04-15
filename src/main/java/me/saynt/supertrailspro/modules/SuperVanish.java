package me.saynt.supertrailspro.modules;

import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;
import java.util.Map.Entry;
import me.saynt.supertrailspro.STUtils;
import me.saynt.supertrailspro.data.DataManager;
import me.saynt.supertrailspro.data.PlayerData;
import me.saynt.supertrailspro.trails.Task;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;

public class SuperVanish extends TaskModule {
   int i = 0;

   public void a() {
      if (STUtils.optionb("SuperVanish")) {
         Task.registerTaskModuble(this);
      }
   }

   public void tick() {
      try {
         if (this.i > 5) {
            Iterator var2 = ((HashMap)DataManager.p.clone()).entrySet().iterator();

            while(var2.hasNext()) {
               Entry var1 = (Entry)var2.next();
               Player var3 = Bukkit.getPlayer((UUID)var1.getKey());
               if (var3 != null) {
                  PlayerData var4 = DataManager.getData(var3);
                  if (this.isVanished(var3)) {
                     var4.setHidden(true, HideReason.VANISH);
                  } else {
                     var4.setHidden(false, HideReason.VANISH);
                  }
               }
            }
         }

         this.i = this.i > 5 ? 0 : ++this.i;
      } catch (Exception var5) {
         var5.printStackTrace();
      }

   }

   private boolean isVanished(Player var1) {
      Iterator var3 = var1.getMetadata("vanished").iterator();

      while(var3.hasNext()) {
         MetadataValue var2 = (MetadataValue)var3.next();
         if (var2.asBoolean()) {
            return true;
         }
      }

      return false;
   }

   public void z() {
      Task.unregisterModule(this);
      this.i = 0;
      this.a();
   }
}
