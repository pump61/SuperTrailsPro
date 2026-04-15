package me.saynt.supertrailspro.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.saynt.supertrailspro.PluginMessages;
import me.saynt.supertrailspro.STUtils;
import me.saynt.supertrailspro.eventtrails.EventUtils;
import me.saynt.supertrailspro.eventtrails.TrailEvent;
import me.saynt.supertrailspro.modules.SoundsConverter;
import me.saynt.supertrailspro.trails.Trail;
import me.saynt.supertrailspro.trails.TrailsUtil;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class EventData {
   private PlayerData pd;
   private int points;
   private int chests;
   private List<TrailEvent> unlocked = new ArrayList();
   private int bonus;

   public EventData(PlayerData var1) {
      this.pd = var1;
   }

   public String toJSON() {
      try {
         JSONObject var1 = new JSONObject();
         JSONArray var2 = new JSONArray();
         var2.addAll(this.unlockedList());
         PluginMessages.Debug(var2.size() + " CK");
         var1.put("u", var2);
         var1.put("c", this.chests);
         var1.put("p", this.points);
         return var1.toJSONString();
      } catch (Exception var3) {
         var3.printStackTrace();
         return null;
      }
   }

   public void fromOld(String var1) {
      try {
         String[] var2 = var1.split("--");
         if (var2.length == 0) {
            return;
         }

         this.chests = Integer.parseInt(var2[0]);
         if (var2.length < 2) {
            return;
         }

         String[] var3 = var2[1].split("-");
         this.setUpTrails(var3);
      } catch (Exception var4) {
         var4.printStackTrace();
      }

   }

   public void fromJSON(String var1) {
      if (var1 != null) {
         if (!var1.startsWith("{")) {
            this.fromOld(var1);
            PluginMessages.Debug("Blyad");
         }

         try {
            JSONParser var2 = new JSONParser();
            JSONObject var3 = (JSONObject)var2.parse(var1);
            this.points = STUtils.getField(var3, "p");
            this.chests = STUtils.getField(var3, "c");
            PluginMessages.Debug(this.points + " " + this.chests);
            PluginMessages.Debug("PIDOR OBJECT " + this);
            this.setUpTrails((JSONArray)var3.get("u"));
         } catch (Exception var4) {
            var4.printStackTrace();
         }

      }
   }

   public void setUpTrails(JSONArray var1) {
      try {
         if (var1 == null) {
            return;
         }

         Iterator var3 = var1.iterator();

         while(var3.hasNext()) {
            Object var2 = var3.next();
            String var4 = (String)var2;
            int var5 = Integer.parseInt(var4);
            Trail var6 = TrailsUtil.getFromID(var5);
            if (STUtils.isEventTrail(var6)) {
               this.unlocked.add((TrailEvent)var6);
            }
         }
      } catch (Exception var7) {
         var7.printStackTrace();
      }

   }

   public boolean isEmpty() {
      return this.points == 0 && this.chests == 0 && this.unlocked.size() == 0;
   }

   public void setUpTrails(String[] var1) {
      try {
         if (var1 == null) {
            return;
         }

         String[] var5 = var1;
         int var4 = var1.length;

         for(int var3 = 0; var3 < var4; ++var3) {
            String var2 = var5[var3];
            int var6 = Integer.parseInt(var2);
            Trail var7 = TrailsUtil.getFromID(var6);
            if (STUtils.isEventTrail(var7)) {
               this.unlocked.add((TrailEvent)var7);
            }
         }
      } catch (Exception var8) {
         var8.printStackTrace();
      }

   }

   public void save() {
      this.pd.save();
   }

   public void setChests(int var1) {
      this.chests = var1;
   }

   public List<TrailEvent> getUnlocked() {
      return this.unlocked;
   }

   public void setUnlocked(List<String> var1) {
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         String var2 = (String)var3.next();

         try {
            PluginMessages.Debug("Readed " + var2);
            TrailEvent var4 = EventUtils.getFromID(Integer.parseInt(var2));
            if (var4 != null) {
               this.unlocked.add(var4);
               PluginMessages.Debug("Added " + var4.getId());
            }

            PluginMessages.Debug(this.unlocked.size() + " F");
         } catch (Exception var5) {
            var5.printStackTrace();
         }
      }

   }

   public ArrayList<String> unlockedList() {
      ArrayList var1 = new ArrayList();

      for(int var2 = 0; var2 < this.unlocked.size(); ++var2) {
         String var3 = String.valueOf(((TrailEvent)this.unlocked.get(var2)).getId());
         if (!var1.contains(var3)) {
            var1.add(var3);
         }
      }

      PluginMessages.Debug(var1.size() + " U");
      return var1;
   }

   public boolean isUnlocked(TrailEvent var1) {
      return var1 == null ? true : this.isUnlocked(var1.getId());
   }

   public boolean isUnlocked(int var1) {
      Iterator var3 = this.unlocked.iterator();

      while(var3.hasNext()) {
         TrailEvent var2 = (TrailEvent)var3.next();
         if (var2.getId() == var1) {
            return true;
         }
      }

      return false;
   }

   public void add(int var1) {
      if (!this.isUnlocked(var1)) {
         Trail var2 = TrailsUtil.getFromID(var1);
         if (STUtils.isEventTrail(var2)) {
            if (!this.unlocked.contains(var2)) {
               this.unlocked.add((TrailEvent)var2);
            }
         }
      }
   }

   public void addChests(int var1) {
      this.chests += var1;
   }

   public void addPoints(int var1) {
      this.points += var1;
   }

   public void reset() {
      this.unlocked = new ArrayList();
   }

   public int getChests() {
      return this.bonus + this.chests;
   }

   public int getPoints() {
      return this.points;
   }

   public int getConvertableAmount() {
      int var1 = this.points / 100;
      return var1;
   }

   public void resetAll() {
      this.points = 0;
      this.chests = 0;
      this.unlocked.clear();
   }

   public void convertToChests(Player var1) {
      int var2 = this.getConvertableAmount();
      if (var2 != 0) {
         this.chests += var2;
         this.points -= var2 * 100;
         var1.playSound(var1.getLocation(), SoundsConverter.NOTE_PLING.toSound(), 1.0F, 1.0F);
         this.save();
      }
   }
}
