package libs.apachecommonsio.filefilter;

import java.io.File;
import java.io.Serializable;

public class TrueFileFilter implements IOFileFilter, Serializable {
   public static final IOFileFilter TRUE;
   public static final IOFileFilter INSTANCE;

   static {
      INSTANCE = TRUE = new TrueFileFilter();
   }

   protected TrueFileFilter() {
   }

   public boolean accept(File var1) {
      return true;
   }

   public boolean accept(File var1, String var2) {
      return true;
   }
}
