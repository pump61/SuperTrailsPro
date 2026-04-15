package me.saynt.supertrailspro.data;

import java.util.UUID;
import me.saynt.supertrailspro.SuperTrails;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;

public class NPCData extends PlayerData {
   private int npcID = 0;
   private NPC npc;

   public NPCData() {
      super((UUID)null);
   }

   public void save() {
   }

   public boolean isEmpty() {
      return this.trail == 0 && this.mode == SuperTrails.p.getConfig().getInt("Options.DefaultMode") && this.rain == null;
   }

   public void setNPC(NPC var1) {
      this.npcID = var1.getId();
      this.npc = var1;
   }

   public Location getLocation() {
      return !this.npc.isSpawned() ? null : this.npc.getStoredLocation();
   }

   public void cloneFrom(PlayerData var1) {
      this.tick = var1.tick;
      this.setTrail(var1.getTrail());
      this.setWingsColor(0, var1.getWings()[0]);
      this.setWingsColor(1, var1.getWings()[1]);
      this.setWingsColor(2, var1.getWings()[2]);
      this.mode = var1.mode;
      this.pattern = var1.pattern;
      this.rain = var1.rain;
   }

   public boolean needsPlayer() {
      return false;
   }

   public NPC getNPC() {
      return this.npc;
   }
}
