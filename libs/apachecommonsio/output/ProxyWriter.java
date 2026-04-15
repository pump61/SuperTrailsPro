package libs.apachecommonsio.output;

import java.io.FilterWriter;

import java.io.Writer;

public class ProxyWriter extends FilterWriter {
   public ProxyWriter(Writer var1) {
      super(var1);
   }

   public Writer append(char var1) {
      try {
         this.beforeWrite(1);
         this.out.append(var1);
         this.afterWrite(1);
      } catch (Exception var3) {
         this.handleIOException(var3);
      }

      return this;
   }

   public Writer append(CharSequence var1, int var2, int var3) {
      try {
         this.beforeWrite(var3 - var2);
         this.out.append(var1, var2, var3);
         this.afterWrite(var3 - var2);
      } catch (Exception var5) {
         this.handleIOException(var5);
      }

      return this;
   }

   public Writer append(CharSequence var1) {
      try {
         int var2 = 0;
         if (var1 != null) {
            var2 = var1.length();
         }

         this.beforeWrite(var2);
         this.out.append(var1);
         this.afterWrite(var2);
      } catch (Exception var3) {
         this.handleIOException(var3);
      }

      return this;
   }

   public void write(int var1) {
      try {
         this.beforeWrite(1);
         this.out.write(var1);
         this.afterWrite(1);
      } catch (Exception var3) {
         this.handleIOException(var3);
      }

   }

   public void write(char[] var1) {
      try {
         int var2 = 0;
         if (var1 != null) {
            var2 = var1.length;
         }

         this.beforeWrite(var2);
         this.out.write(var1);
         this.afterWrite(var2);
      } catch (Exception var3) {
         this.handleIOException(var3);
      }

   }

   public void write(char[] var1, int var2, int var3) {
      try {
         this.beforeWrite(var3);
         this.out.write(var1, var2, var3);
         this.afterWrite(var3);
      } catch (Exception var5) {
         this.handleIOException(var5);
      }

   }

   public void write(String var1) {
      try {
         int var2 = 0;
         if (var1 != null) {
            var2 = var1.length();
         }

         this.beforeWrite(var2);
         this.out.write(var1);
         this.afterWrite(var2);
      } catch (Exception var3) {
         this.handleIOException(var3);
      }

   }

   public void write(String var1, int var2, int var3) {
      try {
         this.beforeWrite(var3);
         this.out.write(var1, var2, var3);
         this.afterWrite(var3);
      } catch (Exception var5) {
         this.handleIOException(var5);
      }

   }

   public void flush() {
      try {
         this.out.flush();
      } catch (Exception var2) {
         this.handleIOException(var2);
      }

   }

   public void close() {
      try {
         this.out.close();
      } catch (Exception var2) {
         this.handleIOException(var2);
      }

   }

   protected void beforeWrite(int var1) {
   }

   protected void afterWrite(int var1) {
   }

   protected void handleIOException(IOException var1) {
      throw var1;
   }
}
