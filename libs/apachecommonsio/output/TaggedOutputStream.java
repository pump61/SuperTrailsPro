package libs.apachecommonsio.output;


import java.io.OutputStream;
import java.io.Serializable;
import java.util.UUID;
import libs.apachecommonsio.TaggedIOException;

public class TaggedOutputStream extends ProxyOutputStream {
   private final Serializable tag = UUID.randomUUID();

   public TaggedOutputStream(OutputStream var1) {
      super(var1);
   }

   public boolean isCauseOf(Exception var1) {
      return TaggedIOException.isTaggedWith(var1, this.tag);
   }

   public void throwIfCauseOf(Exception var1) {
      TaggedIOException.throwCauseIfTaggedWith(var1, this.tag);
   }

   protected void handleIOException(IOException var1) {
      throw new TaggedIOException(var1, this.tag);
   }
}
