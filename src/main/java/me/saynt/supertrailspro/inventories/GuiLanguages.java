package me.saynt.supertrailspro.inventories;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import me.saynt.supertrailspro.PluginMessages;
import me.saynt.supertrailspro.lang.L;
import me.saynt.supertrailspro.lang.LanguageManager;
import me.saynt.supertrailspro.trails.TrailsUtil;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GuiLanguages extends Gui {
   GuiItem[] langs = new GuiItem[54];
   static int[] slots = new int[]{10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25};

   public GuiLanguages() {
      super("Languages");
      this.init();
   }

   public String getName() {
      return L.get(this.p, "MenuNames.Languages");
   }

   public void init() {
      int var1 = 0;
      Iterator var3 = LanguageManager.LangsName.entrySet().iterator();

      while(var3.hasNext()) {
         Entry var2 = (Entry)var3.next();

         try {
            if (slots.length >= var1 + 1) {
               ArrayList var4 = new ArrayList();
               var4.add(LanguageManager.getLanguageFile((Integer)var2.getKey()).getString("Menu.LangSelect").replaceAll("&", "§"));
               String var5 = "§f" + ((String)var2.getValue()).replaceAll("&", "§");
               GuiItem var6 = (new GuiItem(new ItemStack(Material.PAPER), var5, var4)).linked(false);
               this.langs[slots[var1]] = var6;
               var6.cmd(var2.getKey());
               continue;
            }

            PluginMessages.Error("You can't create more than 14 languages");
         } catch (Exception var10) {
            var10.printStackTrace();
            continue;
         } finally {
            ++var1;
         }

         return;
      }

   }

   public void loot() {
      int var1 = 0;
      GuiItem[] var5;
      int var4 = (var5 = this.langs).length;

      for(int var3 = 0; var3 < var4; ++var3) {
         GuiItem var2 = var5[var3];
         if (var2 != null) {
            this.setItem(slots[var1], var2);
            ++var1;
         }
      }

      this.backItem();
   }

   public void click(InventoryClickEvent var1) {
      int var2 = var1.getRawSlot();
      if (var2 == BACKITEM_SLOT) {
         GuiManager.open(this.p, "Main");
      } else {
         GuiItem var3 = this.git[var2];
         if (var3 != null) {
            if (var3.command != null) {
               int var4 = (Integer)var3.command;
               TrailsUtil.setlang(this.p, var4, true);
               this.p.closeInventory();
            }
         }
      }
   }
}
