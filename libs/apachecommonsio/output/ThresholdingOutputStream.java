package libs.apachecommonsio.output;


import java.io.OutputStream;

public abstract class ThresholdingOutputStream extends OutputStream {
   private final int threshold;
   private long written;
   private boolean thresholdExceeded;

   public ThresholdingOutputStream(int var1) {
      this.threshold = var1;
   }

   public void write(int var1) {
      this.checkThreshold(1);
      this.getStream().write(var1);
      ++this.written;
   }

   public void write(byte[] var1) {
      this.checkThreshold(var1.length);
      this.getStream().write(var1);
      this.written += (long)var1.length;
   }

   public void write(byte[] var1, int var2, int var3) {
      this.checkThreshold(var3);
      this.getStream().write(var1, var2, var3);
      this.written += (long)var3;
   }

   public void flush() {
      this.getStream().flush();
   }

   public void close() {
      try {
         this.flush();
      } catch (Exception var2) {
      }

      this.getStream().close();
   }

   public int getThreshold() {
      return this.threshold;
   }

   public long getByteCount() {
      return this.written;
   }

   public boolean isThresholdExceeded() {
      return this.written > (long)this.threshold;
   }

   protected void checkThreshold(int var1) {
      if (!this.thresholdExceeded && this.written + (long)var1 > (long)this.threshold) {
         this.thresholdExceeded = true;
         this.thresholdReached();
      }

   }

   protected void resetByteCount() {
      this.thresholdExceeded = false;
      this.written = 0L;
   }

   protected abstract OutputStream getStream();

   protected abstract void thresholdReached();
}
