package libs.apachecommonsio.output;

import java.io.File;
import java.io.FileOutputStream;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import libs.apachecommonsio.FileUtils;
import libs.apachecommonsio.IOUtils;

public class FileWriterWithEncoding extends Writer {
   private final Writer out;

   public FileWriterWithEncoding(String var1, String var2) {
      this(new File(var1), var2, false);
   }

   public FileWriterWithEncoding(String var1, String var2, boolean var3) {
      this(new File(var1), var2, var3);
   }

   public FileWriterWithEncoding(String var1, Charset var2) {
      this(new File(var1), var2, false);
   }

   public FileWriterWithEncoding(String var1, Charset var2, boolean var3) {
      this(new File(var1), var2, var3);
   }

   public FileWriterWithEncoding(String var1, CharsetEncoder var2) {
      this(new File(var1), var2, false);
   }

   public FileWriterWithEncoding(String var1, CharsetEncoder var2, boolean var3) {
      this(new File(var1), var2, var3);
   }

   public FileWriterWithEncoding(File var1, String var2) {
      this(var1, var2, false);
   }

   public FileWriterWithEncoding(File var1, String var2, boolean var3) {
      this.out = initWriter(var1, var2, var3);
   }

   public FileWriterWithEncoding(File var1, Charset var2) {
      this(var1, var2, false);
   }

   public FileWriterWithEncoding(File var1, Charset var2, boolean var3) {
      this.out = initWriter(var1, var2, var3);
   }

   public FileWriterWithEncoding(File var1, CharsetEncoder var2) {
      this(var1, var2, false);
   }

   public FileWriterWithEncoding(File var1, CharsetEncoder var2, boolean var3) {
      this.out = initWriter(var1, var2, var3);
   }

   private static Writer initWriter(File var0, Object var1, boolean var2) {
      if (var0 == null) {
         throw new NullPointerException("File is missing");
      } else if (var1 == null) {
         throw new NullPointerException("Encoding is missing");
      } else {
         boolean var3 = var0.exists();
         FileOutputStream var4 = null;
         OutputStreamWriter var5 = null;

         try {
            var4 = new FileOutputStream(var0, var2);
            var5 = var1 instanceof Charset ? new OutputStreamWriter(var4, (Charset)var1) : (var1 instanceof CharsetEncoder ? new OutputStreamWriter(var4, (CharsetEncoder)var1) : new OutputStreamWriter(var4, (String)var1));
            return var5;
         } catch (Exception var7) {
            IOUtils.closeQuietly((Writer)var5);
            IOUtils.closeQuietly((OutputStream)var4);
            if (!var3) {
               FileUtils.deleteQuietly(var0);
            }

            throw var7;
         } catch (RuntimeException var8) {
            IOUtils.closeQuietly((Writer)var5);
            IOUtils.closeQuietly((OutputStream)var4);
            if (!var3) {
               FileUtils.deleteQuietly(var0);
            }

            throw var8;
         }
      }
   }

   public void write(int var1) {
      this.out.write(var1);
   }

   public void write(char[] var1) {
      this.out.write(var1);
   }

   public void write(char[] var1, int var2, int var3) {
      this.out.write(var1, var2, var3);
   }

   public void write(String var1) {
      this.out.write(var1);
   }

   public void write(String var1, int var2, int var3) {
      this.out.write(var1, var2, var3);
   }

   public void flush() {
      this.out.flush();
   }

   public void close() {
      this.out.close();
   }
}
