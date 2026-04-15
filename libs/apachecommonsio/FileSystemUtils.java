package libs.apachecommonsio;

import java.io.BufferedReader;
import java.io.File;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

public class FileSystemUtils {
   private static final FileSystemUtils INSTANCE = new FileSystemUtils();
   private static final int INIT_PROBLEM = -1;
   private static final int OTHER = 0;
   private static final int WINDOWS = 1;
   private static final int UNIX = 2;
   private static final int POSIX_UNIX = 3;
   private static final int OS;
   private static final String DF;

   static {
      boolean var0 = false;
      String var1 = "df";

      int var4;
      try {
         String var2 = System.getProperty("os.name");
         if (var2 == null) {
            throw new IOException("os.name not found");
         }

         if ((var2 = var2.toLowerCase(Locale.ENGLISH)).indexOf("windows") != -1) {
            var4 = 1;
         } else if (var2.indexOf("linux") == -1 && var2.indexOf("mpe/ix") == -1 && var2.indexOf("freebsd") == -1 && var2.indexOf("irix") == -1 && var2.indexOf("digital unix") == -1 && var2.indexOf("unix") == -1 && var2.indexOf("mac os x") == -1) {
            if (var2.indexOf("sun os") == -1 && var2.indexOf("sunos") == -1 && var2.indexOf("solaris") == -1) {
               var4 = var2.indexOf("hp-ux") == -1 && var2.indexOf("aix") == -1 ? 0 : 3;
            } else {
               var4 = 3;
               var1 = "/usr/xpg4/bin/df";
            }
         } else {
            var4 = 2;
         }
      } catch (Exception var3) {
         var4 = -1;
      }

      OS = var4;
      DF = var1;
   }

   /** @deprecated */
   @Deprecated
   public static long freeSpace(String var0) {
      return INSTANCE.freeSpaceOS(var0, OS, false, -1L);
   }

   public static long freeSpaceKb(String var0) {
      return freeSpaceKb(var0, -1L);
   }

   public static long freeSpaceKb(String var0, long var1) {
      return INSTANCE.freeSpaceOS(var0, OS, true, var1);
   }

   public static long freeSpaceKb() {
      return freeSpaceKb(-1L);
   }

   public static long freeSpaceKb(long var0) {
      return freeSpaceKb((new File(".")).getAbsolutePath(), var0);
   }

   long freeSpaceOS(String var1, int var2, boolean var3, long var4) {
      if (var1 == null) {
         throw new IllegalArgumentException("Path must not be empty");
      } else {
         switch(var2) {
         case 0:
            throw new IllegalStateException("Unsupported operating system");
         case 1:
            return var3 ? this.freeSpaceWindows(var1, var4) / 1024L : this.freeSpaceWindows(var1, var4);
         case 2:
            return this.freeSpaceUnix(var1, var3, false, var4);
         case 3:
            return this.freeSpaceUnix(var1, var3, true, var4);
         default:
            throw new IllegalStateException("Exception caught when determining operating system");
         }
      }
   }

   long freeSpaceWindows(String var1, long var2) {
      if ((var1 = FilenameUtils.normalize(var1, false)).length() > 0 && var1.charAt(0) != '"') {
         var1 = "\"" + var1 + "\"";
      }

      String[] var4 = new String[]{"cmd.exe", "/C", "dir /a /-c " + var1};
      List var5 = this.performCommand(var4, Integer.MAX_VALUE, var2);

      for(int var6 = var5.size() - 1; var6 >= 0; --var6) {
         String var7 = (String)var5.get(var6);
         if (var7.length() > 0) {
            return this.parseDir(var7, var1);
         }
      }

      throw new IOException("Command line 'dir /-c' did not return any info for path '" + var1 + "'");
   }

   long parseDir(String var1, String var2) {
      int var5 = 0;
      int var6 = 0;

      char var3;
      int var4;
      for(var4 = var1.length() - 1; var4 >= 0; --var4) {
         var3 = var1.charAt(var4);
         if (Character.isDigit(var3)) {
            var6 = var4 + 1;
            break;
         }
      }

      while(var4 >= 0) {
         var3 = var1.charAt(var4);
         if (!Character.isDigit(var3) && var3 != ',' && var3 != '.') {
            var5 = var4 + 1;
            break;
         }

         --var4;
      }

      if (var4 < 0) {
         throw new IOException("Command line 'dir /-c' did not return valid info for path '" + var2 + "'");
      } else {
         StringBuilder var7 = new StringBuilder(var1.substring(var5, var6));

         for(int var8 = 0; var8 < var7.length(); ++var8) {
            if (var7.charAt(var8) == ',' || var7.charAt(var8) == '.') {
               var7.deleteCharAt(var8--);
            }
         }

         return this.parseBytes(var7.toString(), var2);
      }
   }

   long freeSpaceUnix(String var1, boolean var2, boolean var3, long var4) {
      if (var1.length() == 0) {
         throw new IllegalArgumentException("Path must not be empty");
      } else {
         String var7 = "-";
         if (var2) {
            var7 = var7 + "k";
         }

         if (var3) {
            var7 = var7 + "P";
         }

         String[] var6;
         String[] var8;
         if (var7.length() > 1) {
            var8 = new String[]{DF, var7, null};
            var6 = var8;
            var8[2] = var1;
         } else {
            var8 = new String[]{DF, null};
            var6 = var8;
            var8[1] = var1;
         }

         List var9 = this.performCommand(var6, 3, var4);
         if (var9.size() < 2) {
            throw new IOException("Command line '" + DF + "' did not return info as expected " + "for path '" + var1 + "'- response was " + var9);
         } else {
            String var10 = (String)var9.get(1);
            StringTokenizer var11 = new StringTokenizer(var10, " ");
            String var12;
            if (var11.countTokens() < 4) {
               if (var11.countTokens() != 1 || var9.size() < 3) {
                  throw new IOException("Command line '" + DF + "' did not return data as expected " + "for path '" + var1 + "'- check path is valid");
               }

               var12 = (String)var9.get(2);
               var11 = new StringTokenizer(var12, " ");
            } else {
               var11.nextToken();
            }

            var11.nextToken();
            var11.nextToken();
            var12 = var11.nextToken();
            return this.parseBytes(var12, var1);
         }
      }
   }

   long parseBytes(String var1, String var2) {
      try {
         long var3 = Long.parseLong(var1);
         if (var3 < 0L) {
            throw new IOException("Command line '" + DF + "' did not find free space in response " + "for path '" + var2 + "'- check path is valid");
         } else {
            return var3;
         }
      } catch (NumberFormatException var5) {
         throw new IOExceptionWithCause("Command line '" + DF + "' did not return numeric data as expected " + "for path '" + var2 + "'- check path is valid", var5);
      }
   }

   List<String> performCommand(String[] var1, int var2, long var3) {
      ArrayList var6 = new ArrayList(20);
      Process var7 = null;
      InputStream var8 = null;
      OutputStream var9 = null;
      InputStream var10 = null;
      BufferedReader var11 = null;

      ArrayList var5;
      try {
         Thread var16 = ThreadMonitor.start(var3);
         var7 = this.openProcess(var1);
         var8 = var7.getInputStream();
         var9 = var7.getOutputStream();
         var10 = var7.getErrorStream();
         var11 = new BufferedReader(new InputStreamReader(var8));

         for(String var13 = var11.readLine(); var13 != null && var6.size() < var2; var13 = var11.readLine()) {
            var13 = var13.toLowerCase(Locale.ENGLISH).trim();
            var6.add(var13);
         }

         var7.waitFor();
         ThreadMonitor.stop(var16);
         if (var7.exitValue() != 0) {
            throw new IOException("Command line returned OS error code '" + var7.exitValue() + "' for command " + Arrays.asList(var1));
         }

         if (var6.isEmpty()) {
            throw new IOException("Command line did not return any info for command " + Arrays.asList(var1));
         }

         var5 = var6;
      } catch (InterruptedException var15) {
         InterruptedException var12 = var15;

         try {
            throw new IOExceptionWithCause("Command line threw an InterruptedException for command " + Arrays.asList(var1) + " timeout=" + var3, var12);
         } catch (Throwable var14) {
            IOUtils.closeQuietly(var8);
            IOUtils.closeQuietly(var9);
            IOUtils.closeQuietly(var10);
            IOUtils.closeQuietly((Reader)var11);
            if (var7 != null) {
               var7.destroy();
            }

            throw var14;
         }
      }

      IOUtils.closeQuietly(var8);
      IOUtils.closeQuietly(var9);
      IOUtils.closeQuietly(var10);
      IOUtils.closeQuietly((Reader)var11);
      if (var7 != null) {
         var7.destroy();
      }

      return var5;
   }

   Process openProcess(String[] var1) {
      return Runtime.getRuntime().exec(var1);
   }
}
