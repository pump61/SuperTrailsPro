package me.saynt.supertrailspro.lang;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.configuration.file.FileConfiguration;

public class LangCache {
   private HashMap<String, String> data = new HashMap();
   private HashMap<String, List<String>> datalist = new HashMap();
   int id = 0;

   public LangCache(int var1) {
      this.id = var1;
   }

   public String getString(String var1) {
      String var2 = (String)this.data.get(var1);
      if (var2 == null) {
         String var3 = ((FileConfiguration)LanguageManager.Langs.get(this.id)).getString(var1);
         if (var3 != null) {
            var3 = L.convertColors(var3);
         }

         this.data.put(var1, var3);
         return var3;
      } else {
         return var2;
      }
   }

   public List<String> getStringList(String var1) {
      List var2 = (List)this.datalist.get(var1);
      if (var2 == null) {
         List var3 = ((FileConfiguration)LanguageManager.Langs.get(this.id)).getStringList(var1);
         if (var3 != null) {
            var3 = L.convertColors(var3);
         }

         this.datalist.put(var1, var3);
         return var3;
      } else {
         return new ArrayList(var2);
      }
   }

   public void set(String var1, Object var2) {
      ((FileConfiguration)LanguageManager.Langs.get(this.id)).set(var1, var2);
   }

   public void save(File var1) {
      try {
         ((FileConfiguration) LanguageManager.Langs.get(this.id)).save(var1);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public void reset() {
      this.data.clear();
      this.datalist.clear();
   }
}
