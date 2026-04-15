package me.saynt.supertrailspro.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import me.saynt.supertrailspro.Heads;
import me.saynt.supertrailspro.P;
import me.saynt.supertrailspro.PluginMessages;
import me.saynt.supertrailspro.STUtils;
import me.saynt.supertrailspro.SuperTrails;
import me.saynt.supertrailspro.data.DataManager;
import me.saynt.supertrailspro.lang.L;
import me.saynt.supertrailspro.lang.LanguageManager;
import me.saynt.supertrailspro.spawn.NewBlock;
import me.saynt.supertrailspro.trails.Task;
import me.saynt.supertrailspro.trails.TrailBlocks;
import me.saynt.supertrailspro.trails.TrailParticle;
import me.saynt.supertrailspro.trails.TrailsUtil;
import me.saynt.supertrailspro.trails.modes.Mode;
import me.saynt.supertrailspro.trails.modes.Modes;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InvControl {
   public static HashMap<String, InvMenu> list = new HashMap<>();
   static int slot = 0;
   static int[] slots = new int[]{10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25};

   protected static void registerMenu(String var0, InvMenu var1) {
      list.put(var0, var1);
   }

   public static void addToOpen(Player var0, String var1) {
      Task.toopen.add(var0.getName() + ";" + var1);
   }

   public static void open(String var0, String var1) {
      Player var2 = Bukkit.getPlayer(var0);
      if (var2 != null && getMenu(var1) != null) {
         getMenu(var1).open(var2);
      }
   }

   public static void open(Player var0, String var1) {
      if (var0 != null && getMenu(var1) != null) {
         getMenu(var1).open(var0);
      }
   }

   public static InvMenu getMenu(String var0) {
      return list.get(var0);
   }

   @EventHandler
   public void Click(InventoryClickEvent var1) {
      String var2;
      try {
         var2 = var1.getView().getTitle();
      } catch (Exception var6) {
         return;
      }

      InvMenu var3 = getMenuFromName(var2);
      if (var3 != null) {
         var1.setCancelled(true);
         Player var4 = (Player) var1.getWhoClicked();
         ClickTypes var5 = ClickTypes.NULL;
         if (var1.getAction() == InventoryAction.PICKUP_ALL) {
            var5 = ClickTypes.LEFT;
         } else if (var1.getAction() == InventoryAction.PICKUP_HALF) {
            var5 = ClickTypes.RIGHT;
         }
         var3.Click(var4, var1.getRawSlot(), var5);
      }
   }

   public static void createMenus() {
      createMain();
      createLang();
      TrailsUtil.presetTrails();
      createPart();
      createBlocks();
   }

   public static void createMain() {
      InvMenu var0 = new InvMenu("Trails: Select Type", "Selector", MenuType.Selector);
      if (STUtils.optionb("Inventory.Particles")) {
         var0.addClickableItemWithLore(getS("Particles"), getI("Options.Inventory.ParticlesItem"), "Menu.Particle", "Menu.ParticleLore", "open=;Trails: Particles");
      }
      if (STUtils.optionb("Inventory.Blocks")) {
         var0.addClickableItemWithLore(getS("Blocks"), getI("Options.Inventory.BlocksItem"), "Menu.Block", "Menu.BlockLore", "open=;Trails: Blocks");
      }
      if (STUtils.optionb("EventTrails")) {
         var0.addClickableItemWithLore(4, new ItemStack(Material.ENDER_CHEST), "Menu.Event", "Menu.EventLore", "openevent");
      }
      if (STUtils.optionb("Inventory.Rains")) {
         var0.addClickableItemWithLore(getS("Rains"), getI("Options.Inventory.RainsItem"), "Menu.Rain", "Menu.RainLore", "openrains");
      }
      if (STUtils.optionb("Inventory.Wings")) {
         var0.addClickableItemWithLore(getS("Wings"), getI("Options.Inventory.WingsItem"), "Menu.Wings", "Menu.WingsLore", "openwings");
      }
      if (SuperTrails.p.getConfig().getBoolean("Options.LanguagesMenu")) {
         var0.addClickableItem(getS("LanguageItem"), new ItemStack(Material.SLIME_BALL), "Menu.Langs", "open=;Trails: Languages");
         var0.addClickableItemWithLore(getS("RemoveItem"), getI("Options.Inventory.RemoveItem"), "Menu.Clear", "Menu.ClearLore", "trail=;0");
      } else {
         var0.addClickableItemWithLore(getS("RemoveItem"), getI("Options.Inventory.RemoveItem"), "Menu.Clear", "Menu.ClearLore", "trail=;0");
      }
      var0.setSize(STUtils.ci("Options.Inventory.MainMenuSize"));
   }

   public static ItemStack getI(String var0) {
      FileConfiguration var1 = SuperTrails.p.getConfig();
      String[] var2 = var1.getString(var0).split(",");
      // data byte ignorado no 1.21.4
      Material mat = Material.getMaterial(var2[0].toUpperCase().trim());
      if (mat == null) mat = Material.STONE;
      return new ItemStack(mat, 1);
   }

   public static ItemStack readItemStack(String var0) {
      PluginMessages.Debug(var0);
      var0 = var0.toLowerCase();
      String[] var1 = var0.split(var0.contains(",") ? "," : ":");
      String var2 = var1[0];
      PluginMessages.Debug(var1[0]);

      // customhead — usa novo método skull()
      if (var0.startsWith("customhead=")) {
         return Heads.skull(var0.split("=")[1]);
      }

      var2 = var2.toUpperCase();
      Material var4 = NewBlock.getMaterial(var2);
      return var4 == null ? null : new ItemStack(var4, 1);
   }

   public static int getS(String var0) {
      FileConfiguration var1 = SuperTrails.p.getConfig();
      return var1.getInt("Options.Inventory." + var0 + "Slot");
   }

   public static void createLang() {
      slot = 0;
      InvMenu var0 = new InvMenu("Trails: Languages", "Languages", MenuType.Languages);

      for (Iterator var2 = LanguageManager.LangsName.entrySet().iterator(); var2.hasNext(); ++slot) {
         Entry var1 = (Entry) var2.next();
         if (slots.length < slot + 1) {
            PluginMessages.Error("You can't create more than 14 languages");
            return;
         }
         ItemStack var3 = new ItemStack(Material.PAPER);
         ItemMeta var4 = var3.getItemMeta();
         var4.setDisplayName("§f§l" + (String) var1.getValue());
         ArrayList<String> var5 = new ArrayList<>();
         var5.add(LanguageManager.getLanguageFile((Integer) var1.getKey()).getString("Menu.LangSelect").replaceAll("&", "§"));
         var4.setLore(var5);
         var3.setItemMeta(var4);
         var0.addStaticItem(slots[slot], var3, "lang=;" + var1.getKey());
      }

      var0.addClickableItemWithLore(STUtils.ci("Options.Inventory.BackItemSlot"), getI("Options.Inventory.BackItem"), "Menu.Back", "Menu.BackLore", "open=;Trails: Select Type");
   }

   public static void createBlocks() {
      InvMenu var0 = new InvMenu("Trails: Blocks", "Blocks", MenuType.Blocks);
      var0.addClickableItemWithLore(STUtils.ci("Options.Inventory.BackItemSlot"), getI("Options.Inventory.BackItem"), "Menu.Back", "Menu.BackLore", "open=;Trails: Select Type");

      for (Entry<Integer, TrailBlocks> entry : TrailsUtil.pbl.entrySet()) {
         Integer var3 = entry.getKey();
         TrailBlocks var4 = entry.getValue();
         String var5 = SuperTrails.p.getConfig().getString("BlockTrails." + var4.getName() + ".Item");
         int var6 = Integer.parseInt(SuperTrails.p.getConfig().getString("BlockTrails." + var4.getName() + ".Slot"));
         if (var6 != 0 && var5 != null) {
            // suporte a formato "MATERIAL_NAME" ou "MATERIAL_NAME:data" (data ignorado)
            Material blockMat = Material.getMaterial(var5.split(":")[0].toUpperCase().trim());
            if (blockMat == null) blockMat = Material.STONE;
            var0.addClickableItemWithLore(
               var6 + 1,
               new ItemStack(blockMat, 1),
               "BlockMenu." + var4.getName() + ".Name",
               "BlockMenu." + var4.getName() + ".Lore",
               "trail=;" + var3,
               "trails.block." + var4.getName()
            );
         }
      }

      var0.setSize(STUtils.ci("Options.Inventory.BlocksMenuSize"));
   }

   public static List<String> replacePerm(Player var0, String var1, List<String> var2, MenuType var3, String var4) {
      if (var4 != null) {
         if (var3 == MenuType.Modes) {
            return replaceModes(var0, var1, var2, Modes.getIdFromCmd(var4));
         }
         if (var3 == MenuType.Particles) {
            return replaceTrailPerms(var0, var1, var2, Modes.getIdFromCmd(var4));
         }
      }
      return replacePerm(var0, var1, var2);
   }

   public static InvMenu getMenuFromName(String var0) {
      for (Entry<String, InvMenu> var1 : list.entrySet()) {
         if (var1.getValue().isMenu(var0)) {
            return var1.getValue();
         }
      }
      return null;
   }

   public static List<String> replacePerm(Player var0, String var1, List<String> var2) {
      String var4 = P.has(var0, var1) ? L.get(var0, "Menu.Access") : L.get(var0, "Menu.NoAccess");
      return L.listReplacer(var2, "!permission!", var4);
   }

   public static List<String> replaceModes(Player var0, String var1, List<String> var2, int var3) {
      int var6 = DataManager.getData(var0).getTrail();
      if (((Mode) Modes.listmodes.get(var3 - 1)).isBlocked(var6)) {
         return L.listReplacer(var2, "!permission!", L.get(var0, "Modes.TrailNotSupported"));
      }
      String var5 = P.has(var0, var1) ? L.get(var0, "Menu.Access") : L.get(var0, "Menu.NoAccess");
      return L.listReplacer(var2, "!permission!", var5);
   }

   public static List<String> replaceTrailPerms(Player var0, String var1, List<String> var2, int var3) {
      int var6 = DataManager.getData(var0).getMode();
      if (Modes.isBlocked(var3, var6)) {
         return L.listReplacer(var2, "!permission!", L.get(var0, "Modes.ModeNotSupported"));
      }
      String var5 = P.has(var0, var1) ? L.get(var0, "Menu.Access") : L.get(var0, "Menu.NoAccess");
      return L.listReplacer(var2, "!permission!", var5);
   }

   public static void createPart() {
      InvMenu var0 = new InvMenu("Trails: Particles", "Particles", MenuType.Particles);
      var0.addClickableItem(4, getI("Options.Inventory.ModesItem"), "Menu.Modes", "nextmode");
      var0.addClickableItemWithLore(STUtils.ci("Options.Inventory.BackItemSlot"), getI("Options.Inventory.BackItem"), "Menu.Back", "Menu.BackLore", "open=;Trails: Select Type");

      for (Entry<Integer, TrailParticle> entry : TrailsUtil.ptr.entrySet()) {
         FileConfiguration var3 = SuperTrails.p.getConfig();
         int var4 = var3.getInt("ParticleTrails." + entry.getValue().getName() + ".Slot");
         if (var4 != 0) {
            var0.addClickableItemWithLore(
               var4 - 1,
               entry.getValue().getIS(),
               "ParticleMenu." + entry.getValue().getName(),
               "Menu.Lore",
               "trail=;" + entry.getKey(),
               "trails.particle." + entry.getValue().getName()
            );
         }
      }

      var0.setSize(STUtils.ci("Options.Inventory.ParticlesMenuSize"));
   }

   public static String RV(Player var0, String var1) {
      return var1.replaceAll("!access!", L.get(var0, ""));
   }
}