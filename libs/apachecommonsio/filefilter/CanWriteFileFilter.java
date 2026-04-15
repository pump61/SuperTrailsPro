package libs.apachecommonsio.filefilter;

import java.io.File;
import java.io.Serializable;

public class CanWriteFileFilter extends AbstractFileFilter implements Serializable {
   public static final IOFileFilter CAN_WRITE = new CanWriteFileFilter();
   public static final IOFileFilter CANNOT_WRITE;

   static {
      CANNOT_WRITE = new NotFileFilter(CAN_WRITE);
   }

   protected CanWriteFileFilter() {
   }

   public boolean accept(File var1) {
      return var1.canWrite();
   }
}
