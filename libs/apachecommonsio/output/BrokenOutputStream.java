package libs.apachecommonsio.output;


import java.io.OutputStream;

public class BrokenOutputStream extends OutputStream {
   private final IOException exception;

   public BrokenOutputStream(IOException var1) {
      this.exception = var1;
   }

   public BrokenOutputStream() {
      this(new IOException("Broken output stream"));
   }

   public void write(int var1) {
      throw this.exception;
   }

   public void flush() {
      throw this.exception;
   }

   public void close() {
      throw this.exception;
   }
}
