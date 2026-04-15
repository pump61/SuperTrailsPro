package me.saynt.supertrailspro.modules;

import java.util.Iterator;
import java.util.UUID;
import java.util.Map.Entry;
import me.saynt.supertrailspro.STUtils;
import me.saynt.supertrailspro.data.DataManager;
import me.saynt.supertrailspro.data.PlayerData;
import me.saynt.supertrailspro.trails.Task;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class InvisHide extends TaskModule {
   int i = 0;

   public void a() {
      if (STUtils.optionb("DetectInvisPotion")) {
         Task.registerTaskModuble(this);
      }
   }

   public void tick() {
      try {
         if (this.i > 10) {
            Iterator var2 = DataManager.p.entrySet().iterator();

            while(var2.hasNext()) {
               Entry var1 = (Entry)var2.next();
               Player var3 = Bukkit.getPlayer((UUID)var1.getKey());
               if (var3 != null) {
                  PlayerData var4 = DataManager.getData(var3);
                  if (this.isVanished(var3)) {
                     var4.setHidden(true, HideReason.INVIS);
                  } else {
                     var4.setHidden(false, HideReason.INVIS);
                  }
               }
            }
         }

         this.i = this.i > 10 ? 0 : ++this.i;
      } catch (Exception var5) {
         var5.printStackTrace();
      }

   }

   boolean isVanished(Player var1) {
      return var1.hasPotionEffect(PotionEffectType.INVISIBILITY);
   }

   public void z() {
      Task.unregisterModule(this);
      this.i = 0;
      this.a();
   }
}
