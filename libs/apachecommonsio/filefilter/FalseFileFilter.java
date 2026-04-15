package libs.apachecommonsio.filefilter;

import java.io.File;
import java.io.Serializable;

public class FalseFileFilter implements IOFileFilter, Serializable {
   public static final IOFileFilter FALSE;
   public static final IOFileFilter INSTANCE;

   static {
      INSTANCE = FALSE = new FalseFileFilter();
   }

   protected FalseFileFilter() {
   }

   public boolean accept(File var1) {
      return false;
   }

   public boolean accept(File var1, String var2) {
      return false;
   }
}
