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
import me.saynt.supertrailspro.trails.TrailType;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MovementHide extends TaskModule implements Listener {
   HashMap<UUID, Long> time = new HashMap();
   boolean on = false;
   boolean event = false;
   public List<UUID> torm = new ArrayList();
   int i = 0;
   boolean a = false;
   boolean b = false;
   boolean c = false;
   boolean d = false;

   public void a() {
      if (STUtils.optionb("HideOnMove.Enable")) {
         FileConfiguration var1 = SuperTrails.p.getConfig();
         this.a = var1.getBoolean("Options.HideOnMove.Particles");
         this.b = var1.getBoolean("Options.HideOnMove.Rains");
         this.c = var1.getBoolean("Options.HideOnMove.Wings");
         this.d = var1.getBoolean("Options.HideOnMove.Event");
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
            int var8 = var7.getTrail();
            if (this.isMoving(var6)) {
               if (this.isHidden(this.getType(var8))) {
                  var7.setHidden(true, HideReason.MOVEMENT);
               }
            } else {
               var7.setHidden(false, HideReason.MOVEMENT);
            }
         }
      }

      this.i = this.i > 10 ? 0 : ++this.i;
   }

   @EventHandler
   public void move(PlayerMoveEvent var1) {
      if (this.on) {
         if (var1.getFrom().getX() != var1.getTo().getX() || var1.getFrom().getY() != var1.getTo().getY() || var1.getFrom().getZ() != var1.getTo().getZ()) {
            Player var2 = var1.getPlayer();
            if (!DataManager.p.containsKey(var2.getUniqueId())) {
               return;
            }

            this.time.put(var2.getUniqueId(), 10L);
         }

      }
   }

   public boolean isMoving(Player var1) {
      return this.time.containsKey(var1.getUniqueId());
   }

   public TrailType getType(int var1) {
      if (var1 > 0 && var1 <= 99) {
         return TrailType.Particle;
      } else if (var1 == 201) {
         return TrailType.Rain;
      } else if (var1 > 299 && var1 < 399) {
         return TrailType.Wings;
      } else {
         return var1 > 400 && var1 < 499 ? TrailType.Event_Color : TrailType.Empty;
      }
   }

   public boolean isHidden(TrailType var1) {
      if (var1 == null) {
         return false;
      } else if (var1 == TrailType.Particle) {
         return this.a;
      } else if (var1 == TrailType.Rain) {
         return this.b;
      } else if (var1 == TrailType.Wings) {
         return this.c;
      } else {
         return var1 == TrailType.Event_Color ? this.d : false;
      }
   }

   public void z() {
      this.on = false;
      this.a = false;
      this.b = false;
      this.c = false;
      this.d = false;
      Task.unregisterModule(this);
      this.a();
   }
}
