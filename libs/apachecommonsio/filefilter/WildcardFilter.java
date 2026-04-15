package libs.apachecommonsio.filefilter;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import libs.apachecommonsio.FilenameUtils;

/** @deprecated */
@Deprecated
public class WildcardFilter extends AbstractFileFilter implements Serializable {
   private final String[] wildcards;

   public WildcardFilter(String var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("The wildcard must not be null");
      } else {
         this.wildcards = new String[]{var1};
      }
   }

   public WildcardFilter(String[] var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("The wildcard array must not be null");
      } else {
         this.wildcards = new String[var1.length];
         System.arraycopy(var1, 0, this.wildcards, 0, var1.length);
      }
   }

   public WildcardFilter(List<String> var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("The wildcard list must not be null");
      } else {
         this.wildcards = (String[])var1.toArray(new String[var1.size()]);
      }
   }

   public boolean accept(File var1, String var2) {
      if (var1 != null && (new File(var1, var2)).isDirectory()) {
         return false;
      } else {
         String[] var6;
         int var5 = (var6 = this.wildcards).length;

         for(int var4 = 0; var4 < var5; ++var4) {
            String var3 = var6[var4];
            if (FilenameUtils.wildcardMatch(var2, var3)) {
               return true;
            }
         }

         return false;
      }
   }

   public boolean accept(File var1) {
      if (var1.isDirectory()) {
         return false;
      } else {
         String[] var5;
         int var4 = (var5 = this.wildcards).length;

         for(int var3 = 0; var3 < var4; ++var3) {
            String var2 = var5[var3];
            if (FilenameUtils.wildcardMatch(var1.getName(), var2)) {
               return true;
            }
         }

         return false;
      }
   }
}
