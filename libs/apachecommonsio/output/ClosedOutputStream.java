package libs.apachecommonsio.output;


import java.io.OutputStream;

public class ClosedOutputStream extends OutputStream {
   public static final ClosedOutputStream CLOSED_OUTPUT_STREAM = new ClosedOutputStream();

   public void write(int var1) {
      throw new IOException("write(" + var1 + ") failed: stream is closed");
   }
}
