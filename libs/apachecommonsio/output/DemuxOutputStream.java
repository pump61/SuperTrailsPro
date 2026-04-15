package libs.apachecommonsio.output;

import java.io.OutputStream;

public class DemuxOutputStream extends OutputStream {
   private final InheritableThreadLocal<OutputStream> m_streams = new InheritableThreadLocal();

   public OutputStream bindStream(OutputStream var1) {
      OutputStream var2 = (OutputStream)this.m_streams.get();
      this.m_streams.set(var1);
      return var2;
   }

   public void close() {
      OutputStream var1 = (OutputStream)this.m_streams.get();
      if (var1 != null) {
         var1.close();
      }

   }

   public void flush() {
      OutputStream var1 = (OutputStream)this.m_streams.get();
      if (var1 != null) {
         var1.flush();
      }

   }

   public void write(int var1) {
      OutputStream var2 = (OutputStream)this.m_streams.get();
      if (var2 != null) {
         var2.write(var1);
      }

   }
}
