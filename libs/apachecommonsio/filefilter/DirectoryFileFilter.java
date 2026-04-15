package libs.apachecommonsio.filefilter;

import java.io.File;
import java.io.Serializable;

public class DirectoryFileFilter extends AbstractFileFilter implements Serializable {
   public static final IOFileFilter DIRECTORY;
   public static final IOFileFilter INSTANCE;

   static {
      INSTANCE = DIRECTORY = new DirectoryFileFilter();
   }

   protected DirectoryFileFilter() {
   }

   public boolean accept(File var1) {
      return var1.isDirectory();
   }
}
