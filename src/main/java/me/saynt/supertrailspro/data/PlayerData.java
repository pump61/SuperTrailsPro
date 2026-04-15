package me.saynt.supertrailspro.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import me.saynt.supertrailspro.PluginMessages;
import me.saynt.supertrailspro.STUtils;
import me.saynt.supertrailspro.StorageType;
import me.saynt.supertrailspro.SuperTrails;
import me.saynt.supertrailspro.eventtrails.EventUtils;
import me.saynt.supertrailspro.eventtrails.fairy.FairyUtils;
import me.saynt.supertrailspro.modules.HideReason;
import me.saynt.supertrailspro.particlelib.ParticleEffect;
import me.saynt.supertrailspro.trails.Task;
import me.saynt.supertrailspro.trails.modes.Modes;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class PlayerData {
   private UUID uuid;
   private Player p;
   protected int trail = 0;
   protected int mode;
   private int lang;
   protected int W1;
   protected int W2;
   protected int W3;
   private boolean loaded;
   private List<HideReason> hide;
   protected String pattern;
   public int tick;
   protected ItemStack rain;
   private Keeper[] keeper;
   private EventData event;

   public PlayerData(UUID var1) {
      this.mode = SuperTrails.p.getConfig().getInt("Options.DefaultMode");
      this.lang = 0;
      this.W1 = 0;
      this.W2 = 0;
      this.W3 = 0;
      this.loaded = true;
      this.hide = new ArrayList();
      this.tick = 0;
      this.keeper = new Keeper[5];
      this.event = new EventData(this);
      this.uuid = var1;
      this.updatePlayer();
      DataManager.p.put(var1, this);
      PluginMessages.Debug("" + var1);
      if (DataManager.st == StorageType.Config) {
         try {
            EventUtils.loadEventDataFromConfig(this);
         } catch (Exception var3) {
            var3.printStackTrace();
            PluginMessages.Error("Fuck my life ERROR");
         }
      }

   }

   public PlayerData(UUID var1, boolean var2) {
      this.mode = SuperTrails.p.getConfig().getInt("Options.DefaultMode");
      this.lang = 0;
      this.W1 = 0;
      this.W2 = 0;
      this.W3 = 0;
      this.loaded = true;
      this.hide = new ArrayList();
      this.tick = 0;
      this.keeper = new Keeper[5];
      this.event = new EventData(this);
      this.uuid = var1;
      this.updatePlayer();
      DataManager.p.put(var1, this);
      this.setLoaded(var2);
      PluginMessages.Debug(var1 + " m2");
      if (DataManager.st == StorageType.Config) {
         try {
            EventUtils.loadEventDataFromConfig(this);
         } catch (Exception var4) {
            var4.printStackTrace();
            PluginMessages.Error("Fuck my life ERROR");
         }
      }

   }

   public PlayerData(UUID var1, boolean var2, boolean var3) {
      this.mode = SuperTrails.p.getConfig().getInt("Options.DefaultMode");
      this.lang = 0;
      this.W1 = 0;
      this.W2 = 0;
      this.W3 = 0;
      this.loaded = true;
      this.hide = new ArrayList();
      this.tick = 0;
      this.keeper = new Keeper[5];
      this.event = new EventData(this);
      if (!var3) {
         this.uuid = var1;
         this.updatePlayer();
         DataManager.p.put(var1, this);
         this.setLoaded(var2);
         PluginMessages.Debug(var1 + " m2");
      } else {
         this.uuid = var1;
         this.updatePlayer();
         EventUtils.loadEventDataFromConfig(this);
      }

   }

   public PlayerData(UUID var1, String var2) {
      this.mode = SuperTrails.p.getConfig().getInt("Options.DefaultMode");
      this.lang = 0;
      this.W1 = 0;
      this.W2 = 0;
      this.W3 = 0;
      this.loaded = true;
      this.hide = new ArrayList();
      this.tick = 0;
      this.keeper = new Keeper[5];
      this.event = new EventData(this);
      this.uuid = var1;
      this.updatePlayer();
      if (DataManager.st == StorageType.Config) {
         try {
            EventUtils.loadEventDataFromConfig(this);
         } catch (Exception var4) {
            var4.printStackTrace();
            PluginMessages.Error("Fuck my life ERROR");
         }
      }

      if (var2 != null) {
         this.fromJson(var2);
      }

      DataManager.p.put(var1, this);
      PluginMessages.Debug(var1 + "m 3");
   }

   public void generateRainFromString(String var1) {
      try {
         String[] var2 = var1.split(";");
         this.trail = Integer.parseInt(var2[0]);
         this.mode = Integer.parseInt(var2[1]);
         if (this.mode > Modes.listmodes.size()) {
            this.mode = 0;
         }

         this.lang = Integer.parseInt(var2[2]);
         ItemStack var3 = new ItemStack(Material.getMaterial(var2[3]), 1, Byte.parseByte(var2[4]));
         this.rain = var3;
         if (var3.getType() == Material.AIR) {
            this.clear();
         }

         this.W3 = Integer.parseInt(var2[5]);
      } catch (Exception var4) {
         PluginMessages.Error("Failed to load " + this.uuid + " data");
         this.clear();
      }

   }

   public boolean isLoaded() {
      return this.loaded;
   }

   private void updatePlayer() {
      this.p = Bukkit.getPlayer(this.uuid);
   }

   public Location getLocation() {
      if (this.p == null || !this.p.isOnline()) {
         this.updatePlayer();
      }

      return this.p == null ? null : this.p.getLocation();
   }

   public Player getPlayer() {
      if (this.p == null || !this.p.isOnline()) {
         this.updatePlayer();
      }

      return this.p != null && this.p.isOnline() ? this.p : null;
   }

   public void setLoaded(boolean var1) {
      this.loaded = var1;
   }

   public Keeper getKeeper(int var1) {
      Keeper var2 = this.keeper[var1];
      if (var2 == null) {
         var2 = new Keeper();
         this.keeper[var1] = var2;
      }

      return var2;
   }

   public EventData getEventData() {
      return this.event;
   }

   public void setEventData(EventData var1) {
      this.event = var1;
   }

   public void generateFromString(String var1) {
      try {
         if (var1.startsWith("{")) {
            this.fromJson(var1);
         } else {
            String[] var2 = var1.split(";");
            this.trail = Integer.parseInt(var2[0]);
            if (this.trail == 201) {
               this.generateRainFromString(var1);
               return;
            }

            this.mode = Integer.parseInt(var2[1]);
            if (this.mode > Modes.listmodes.size()) {
               this.mode = 0;
            }

            this.lang = Integer.parseInt(var2[2]);
            this.W1 = Integer.parseInt(var2[3]);
            if (this.W1 != 50) {
               this.W2 = Integer.parseInt(var2[4]);
               this.W3 = Integer.parseInt(var2[5]);
            } else {
               this.pattern = var2[4];
            }
         }

         if (this.trail <= 300 && this.trail >= 399) {
            this.tick = Task.currenttick < 2 && Task.currenttick >= 0 ? Task.currenttick++ : 0;
         }
      } catch (Exception var3) {
         PluginMessages.Error("Failed to load " + this.uuid + " data");
         this.clear();
      }

   }

   public boolean isEmpty() {
      return this.trail == 0 && this.mode == SuperTrails.p.getConfig().getInt("Options.DefaultMode") && this.lang == 0 && this.allKeepersEmpty() && this.getEventData().isEmpty();
   }

   public void updateTick() {
      this.tick = Task.currenttick < 2 && Task.currenttick >= 0 ? Task.currenttick++ : 0;
   }

   public boolean allKeepersEmpty() {
      for(int var1 = 0; var1 < 5; ++var1) {
         Keeper var2 = this.getKeeper(var1);
         if (!var2.isEmpty()) {
            return false;
         }
      }

      return true;
   }

   public void autoTick() {
      this.tick = Task.currenttick < 2 && Task.currenttick >= 0 ? Task.currenttick++ : 0;
   }

   public void reset() {
      this.mode = SuperTrails.p.getConfig().getInt("Options.DefaultMode");
      this.lang = 0;
      this.W1 = 0;
      this.W2 = 0;
      this.W3 = 0;
      this.pattern = null;
      this.rain = null;
   }

   public void clear() {
      this.uuid = null;
      this.mode = SuperTrails.p.getConfig().getInt("Options.DefaultMode");
      this.lang = 0;
      this.W1 = 0;
      this.W2 = 0;
      this.W3 = 0;
      this.pattern = null;
      this.rain = null;
      DataManager.removePlayer(this.uuid);
   }

   public String getPattern() {
      return this.pattern;
   }

   public boolean needsPlayer() {
      return true;
   }

   public String getPaternString() {
      return this.trail + ";" + this.mode + ";" + this.lang + ";" + this.W1 + ";" + this.pattern;
   }

   public String getString() {
      return this.toJson();
   }

   public String getRainString() {
      if (this.rain == null) {
         this.trail = 0;
         return this.getString();
      } else {
         return this.trail + ";" + this.mode + ";" + this.lang + ";" + this.rain.getType().name() + ";" + this.rain.getData().getData() + ";" + this.W3;
      }
   }

   public void save() {
      PluginMessages.Debug(this.uuid + " " + this.toJson());
      if (this.uuid != null) {
         DataManager.savePlayerData(this.uuid);
      }

   }

   public UUID getUUID() {
      return this.uuid;
   }

   public int getTrail() {
      return this.trail;
   }

   public int getMode() {
      return this.mode;
   }

   public int getLang() {
      return this.lang;
   }

   public int[] getWings() {
      return new int[]{this.W1, this.W2, this.W3};
   }

   public ItemStack getRainItem() {
      return this.rain;
   }

   public ParticleEffect.OrdinaryColor getRainCloudColor() {
      return STUtils.intToColor(this.W3);
   }

   public boolean isHidden() {
      return this.hide.size() > 0;
   }

   public void setTrail(int var1) {
      this.trail = var1;
      if (var1 >= 431 && var1 <= 437) {
         FairyUtils.setFairy(Bukkit.getPlayer(this.uuid), this, var1);
      }

      this.save();
   }

   public void setMode(int var1) {
      this.mode = var1;
      this.save();
   }

   public void setLang(int var1) {
      this.lang = var1;
      this.save();
   }

   public void setPattenWings(int var1, String var2) {
      this.trail = var1;
      this.W1 = 50;
      this.pattern = var2;
      this.save();
      this.updateTick();
   }

   public void setWings(int var1, int var2, int var3, int var4) {
      this.W1 = var2 == 0 ? 1 : var2;
      this.W2 = var3 == 0 ? 1 : var3;
      this.W3 = var4 == 0 ? 1 : var4;
      this.trail = var1;
      this.pattern = null;
      this.save();
      this.updateTick();
   }

   public void setWings(int var1, int[] var2) {
      if (var2.length == 3) {
         this.setWings(var1, var2[0], var2[1], var2[2]);
         this.save();
         this.updateTick();
      }
   }

   public void setWingsColor(int var1, int var2) {
      if (var1 == 0) {
         this.W1 = var2;
      }

      if (var1 == 1) {
         this.W2 = var2;
      }

      if (var1 == 2) {
         this.W3 = var2;
      }

   }

   public void setPattern(String var1) {
      this.pattern = var1;
   }

   public void runWings() {
      if (this.W1 == 0) {
         this.W1 = 1;
      }

      if (this.W2 == 0) {
         this.W2 = 1;
      }

      if (this.W3 == 0) {
         this.W3 = 1;
      }

   }

   public void resetWings() {
      this.W1 = 0;
      this.W3 = 0;
      this.W2 = 0;
      this.pattern = null;
      this.save();
   }

   public void resetRains() {
      this.resetWings();
      this.rain = null;
   }

   public void setRain(ItemStack var1, int var2) {
      this.rain = var1.clone();
      this.W3 = var2;
      this.trail = 201;
   }

   public void setRainColor(int var1) {
      this.W3 = var1;
   }

   public void setRainItemStack(ItemStack var1) {
      this.rain = var1;
   }

   public void setHidden(boolean var1, HideReason var2) {
      if (var1 && !this.hide.contains(var2)) {
         this.hide.add(var2);
      } else if (!var1) {
         this.hide.remove(var2);
      }

   }

   public String toJson() {
      try {
         JSONObject var1 = new JSONObject();
         var1.put("id", this.trail);
         var1.put("mode", this.mode);
         var1.put("w1", this.W1);
         var1.put("w2", this.W2);
         var1.put("w3", this.W3);
         var1.put("pattern", this.pattern);
         if (this.rain != null) {
            JSONObject var2 = new JSONObject();
            String materialName = this.rain.getType().name();
            byte var4 = this.rain.getData().getData();
            var2.put("itemID", materialName);
            var2.put("dataID", (long) var4);
         }

         var1.put("keeper", this.keeperToJson());
         return var1.toJSONString();
      } catch (Exception var5) {
         PluginMessages.Error("User " + this.getUUID() + " data is corrupted");
         PluginMessages.Error("Please send this error to developer");
         var5.printStackTrace();
         return "{\"error\":\"Could not convert player\"}";
      }
   }

   public void fromJson(String var1) {
      try {
         if (!var1.startsWith("{")) {
            this.generateFromString(var1);
            return;
         }

         JSONParser var2 = new JSONParser();
         JSONObject var3 = (JSONObject)var2.parse(var1);
         this.trail = STUtils.getField(var3, "id");
         this.mode = STUtils.getField(var3, "mode");
         this.W1 = STUtils.getField(var3, "w1");
         this.W2 = STUtils.getField(var3, "w2");
         this.W3 = STUtils.getField(var3, "w3");
         this.pattern = (String)var3.get("pattern");
         if (this.trail == 201) {
            try {
               JSONObject var4 = (JSONObject)var3.get("rain");
               String matName = (String) var4.get("itemID");
               Material var5 = Material.getMaterial(matName);
               byte var6 = (byte)STUtils.getField(var4, "dataID");
               PluginMessages.Debug(var6 + " BYTE");
               if (var5 == null) {
                  this.trail = 0;
                  return;
               }

               ItemStack var7 = new ItemStack(var5, 1, var6);
               this.rain = var7;
            } catch (Exception var8) {
               if (PluginMessages.debug) {
                  var8.printStackTrace();
               }
            }
         }

         this.keeperFromJson((JSONObject)var3.get("keeper"));
      } catch (Exception var9) {
         if (PluginMessages.debug) {
            var9.printStackTrace();
         }
      }

   }

   public JSONObject keeperToJson() {
      JSONObject var1 = new JSONObject();

      for(int var2 = 0; var2 < 5; ++var2) {
         var1.put((long)var2, this.getKeeper(var2).toJson());
      }

      return var1;
   }

   public void keeperFromJson(JSONObject var1) {
      PluginMessages.Debug(var1.toJSONString());
      if (var1 != null) {
         for(int var2 = 0; var2 < 5; ++var2) {
            JSONObject var3 = (JSONObject)var1.get(String.valueOf(var2));
            if (var3 != null) {
               PluginMessages.Debug(var3.toJSONString());
            }

            this.keeper[var2] = (new Keeper()).fromJson(var3);
         }

      }
   }
}
