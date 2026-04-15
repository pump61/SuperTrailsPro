package me.saynt.supertrailspro.inventory;

import java.util.HashMap;
import me.saynt.supertrailspro.STUtils;
import me.saynt.supertrailspro.data.DataManager;
import me.saynt.supertrailspro.data.PlayerData;
import me.saynt.supertrailspro.eventtrails.EventMenu;
import me.saynt.supertrailspro.lang.L;
import me.saynt.supertrailspro.modules.SelectSounds;
import me.saynt.supertrailspro.trails.Tab;
import me.saynt.supertrailspro.trails.TrailsUtil;
import me.saynt.supertrailspro.trails.modes.Mode;
import me.saynt.supertrailspro.trails.modes.Modes;
import me.saynt.supertrailspro.wings.WingsMenu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ItemCommandReader {
   public static HashMap<Player, Integer[]> wi = new HashMap();

   public static void runCMD(Player var0, String var1) {
      String[] var2 = var1.split("=;");
      String var3 = var2[0];
      String var4 = null;
      if (var2.length > 1) {
         var4 = var2[1];
      }

      if (!var3.equals("nothing")) {
         if (var3.equals("open")) {
            if (InvControl.list.containsKey(var4)) {
               ((InvMenu)InvControl.list.get(var4)).open(var0);
            }
         } else if (var3.equals("trail")) {
            TrailsUtil.SetTrail(var0, Integer.parseInt(var4));
            SelectSounds.play(Integer.parseInt(var4), var0);
            var0.closeInventory();
         } else if (var3.equals("lang")) {
            TrailsUtil.setlang(var0, Integer.parseInt(var4), true);
         } else if (var3.equals("msg")) {
            var0.sendMessage(var4);
         } else if (var3.equals("openwings")) {
            WingsMenu.WingsSelector(var0);
         } else if (var3.equals("mode") && STUtils.isNumber(var4)) {
            PlayerData var5 = DataManager.getData(var0);
            int var6 = Integer.parseInt(var4) - 1;
            if (Modes.isBlocked(var5.getTrail(), var6)) {
               var0.sendMessage(L.get(var0, "Modes.TrailNotSupported"));
               return;
            }

            var5.setMode(var6);
            DataManager.getData(var0).save();
            InvControl.getMenu("Trails: Particles").open(var0);
         } else if (var3.equals("openrains")) {
            RainInventory.open(var0);
         } else if (var3.equals("openevent")) {
            EventMenu.open(var0);
         } else if (var3.equals("custom")) {
            Tab.setWingsTag(var4, var0, (CommandSender)null, true);
         }

      }
   }

   public static void runCMD(Player var0, String var1, ClickTypes var2) {
      String[] var3 = var1.split("=;");
      String var4 = var3[0];
      String var5 = null;
      if (var3.length > 1) {
         var5 = var3[1];
      }

      if (var4.equals("nextmode")) {
         Mode var6;
         PlayerData var7;
         if (var2 == ClickTypes.LEFT) {
            var6 = Modes.getPrev(var0);
            var7 = DataManager.getData(var0);
            var7.setMode(var6.id - 1);
            var7.save();
            runCMD(var0, "open=;Trails: Particles");
            return;
         }

         if (var2 == ClickTypes.RIGHT) {
            var6 = Modes.getNext(var0);
            var7 = DataManager.getData(var0);
            var7.setMode(0);
            var7.setMode(var6.id - 1);
            var7.save();
            runCMD(var0, "open=;Trails: Particles");
            return;
         }
      }

      runCMD(var0, var1);
   }
}
