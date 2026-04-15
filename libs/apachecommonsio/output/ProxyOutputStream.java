package libs.apachecommonsio.output;

import java.io.FilterOutputStream;

import java.io.OutputStream;

public class ProxyOutputStream extends FilterOutputStream {
   public ProxyOutputStream(OutputStream var1) {
      super(var1);
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

   public void write(byte[] var1) {
      try {
         int var2 = var1 != null ? var1.length : 0;
         this.beforeWrite(var2);
         this.out.write(var1);
         this.afterWrite(var2);
      } catch (Exception var3) {
         this.handleIOException(var3);
      }

   }

   public void write(byte[] var1, int var2, int var3) {
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
