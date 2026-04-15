package libs.apachecommonsio.filefilter;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class AndFileFilter extends AbstractFileFilter implements ConditionalFileFilter, Serializable {
   private final List<IOFileFilter> fileFilters;

   public AndFileFilter() {
      this.fileFilters = new ArrayList();
   }

   public AndFileFilter(List<IOFileFilter> var1) {
      this.fileFilters = var1 == null ? new ArrayList() : new ArrayList(var1);
   }

   public AndFileFilter(IOFileFilter var1, IOFileFilter var2) {
      if (var1 != null && var2 != null) {
         this.fileFilters = new ArrayList(2);
         this.addFileFilter(var1);
         this.addFileFilter(var2);
      } else {
         throw new IllegalArgumentException("The filters must not be null");
      }
   }

   public void addFileFilter(IOFileFilter var1) {
      this.fileFilters.add(var1);
   }

   public List<IOFileFilter> getFileFilters() {
      return Collections.unmodifiableList(this.fileFilters);
   }

   public boolean removeFileFilter(IOFileFilter var1) {
      return this.fileFilters.remove(var1);
   }

   public void setFileFilters(List<IOFileFilter> var1) {
      this.fileFilters.clear();
      this.fileFilters.addAll(var1);
   }

   public boolean accept(File var1) {
      if (this.fileFilters.isEmpty()) {
         return false;
      } else {
         Iterator var3 = this.fileFilters.iterator();

         while(var3.hasNext()) {
            IOFileFilter var2 = (IOFileFilter)var3.next();
            if (!var2.accept(var1)) {
               return false;
            }
         }

         return true;
      }
   }

   public boolean accept(File var1, String var2) {
      if (this.fileFilters.isEmpty()) {
         return false;
      } else {
         Iterator var4 = this.fileFilters.iterator();

         while(var4.hasNext()) {
            IOFileFilter var3 = (IOFileFilter)var4.next();
            if (!var3.accept(var1, var2)) {
               return false;
            }
         }

         return true;
      }
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(super.toString());
      var1.append("(");
      if (this.fileFilters != null) {
         for(int var2 = 0; var2 < this.fileFilters.size(); ++var2) {
            if (var2 > 0) {
               var1.append(",");
            }

            IOFileFilter var3;
            var1.append((var3 = (IOFileFilter)this.fileFilters.get(var2)) == null ? "null" : var3.toString());
         }
      }

      var1.append(")");
      return var1.toString();
   }
}
