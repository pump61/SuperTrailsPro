package me.saynt.supertrailspro.modules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Modules {
   public static List<Module> mm = new ArrayList();

   public static void load() {
      SelectSounds.a();
      (new SuperVanish()).a();
      (new BannedWorlds()).a();
      (new MovementHide()).a();
      (new InvisHide()).a();
      (new CombatModule()).a();
      (new SpectatorModule()).a();
   }

   public static void unload() {
      SelectSounds.z();
      Iterator var1 = mm.iterator();

      while(var1.hasNext()) {
         Module var0 = (Module)var1.next();
         var0.z();
      }

   }
}
