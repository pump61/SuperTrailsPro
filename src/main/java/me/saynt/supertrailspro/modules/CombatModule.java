package me.saynt.supertrailspro.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.Map.Entry;
import me.saynt.supertrailspro.STUtils;
import me.saynt.supertrailspro.SuperTrails;
import me.saynt.supertrailspro.data.DataManager;
import me.saynt.supertrailspro.data.PlayerData;
import me.saynt.supertrailspro.trails.Task;
import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CombatModule extends TaskModule implements Listener {
   HashMap<UUID, Long> time = new HashMap();
   boolean on = false;
   boolean event = false;
   public List<UUID> torm = new ArrayList();
   int i = 0;

   public void a() {
      if (STUtils.optionb("HideInCombat")) {
         if (!this.event) {
            Bukkit.getPluginManager().registerEvents(this, SuperTrails.p);
         }

         Task.registerTaskModuble(this);
         this.event = true;
         this.on = true;
      }
   }

   public void tick() {
      Iterator var2 = this.time.entrySet().iterator();

      while(var2.hasNext()) {
         Entry var1 = (Entry)var2.next();
         UUID var3 = (UUID)var1.getKey();
         Long var4 = (Long)var1.getValue();
         if (var4 > 0L) {
            this.time.put(var3, var4 - 1L);
         } else {
            this.torm.add(var3);
         }
      }

      var2 = this.torm.iterator();

      while(var2.hasNext()) {
         UUID var5 = (UUID)var2.next();
         this.time.remove(var5);
      }

      this.torm.clear();
      if (this.i > 10) {
         var2 = Bukkit.getOnlinePlayers().iterator();

         while(var2.hasNext()) {
            Player var6 = (Player)var2.next();
            PlayerData var7 = DataManager.getData(var6);
            if (this.isInCombat(var6)) {
               var7.setHidden(true, HideReason.COMBAT);
            } else {
               var7.setHidden(false, HideReason.COMBAT);
            }
         }
      }

      this.i = this.i > 10 ? 0 : ++this.i;
   }

   @EventHandler(
      ignoreCancelled = false,
      priority = EventPriority.HIGHEST
   )
   public void Damaged(EntityDamageByEntityEvent var1) {
      if (this.on) {
         if (!var1.isCancelled()) {
            if ((var1.getDamager() instanceof Player || var1.getDamager() instanceof Arrow) && var1.getEntity() instanceof Player) {
               Player var3;
               if (var1.getDamager() instanceof Player) {
                  Player var2 = (Player)var1.getDamager();
                  var3 = (Player)var1.getEntity();
                  this.time.put(var3.getUniqueId(), SuperTrails.p.getConfig().getLong("Options.HideInCombatTime"));
               } else {
                  Arrow var4 = (Arrow)var1.getDamager();
                  var3 = (Player)var1.getEntity();
                  if (var4.getShooter() instanceof Player) {
                     this.time.put(var3.getUniqueId(), SuperTrails.p.getConfig().getLong("Options.HideInCombatTime"));
                  }
               }
            }

         }
      }
   }

   public boolean isInCombat(Player var1) {
      return this.time.containsKey(var1.getUniqueId());
   }

   public void z() {
      this.on = false;
      Task.unregisterModule(this);
      this.a();
   }
}
