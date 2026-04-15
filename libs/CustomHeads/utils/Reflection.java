package libs.CustomHeads.utils;

import java.lang.reflect.Field;
import libs.CustomHeads.CustomHeadApi;

public class Reflection {
   public static <T> T get(Class<?> var0, Class<T> var1, String var2, String var3) {
      return get(var0, var1, (Object)null, var2, var3);
   }

   public static <T> T get(Object var0, Class<T> var1, String var2, String var3) {
      return get(var0.getClass(), var1, var0, var2, var3);
   }

   public static <T> T get(Class<?> var0, Class<T> var1, Object var2, String var3, String var4) {
      try {
         Field var5 = var0.getDeclaredField(var3);
         var5.setAccessible(true);
         Field var6 = Field.class.getDeclaredField("modifiers");
         boolean var7 = var6.isAccessible();
         if (!var7) {
            var6.setAccessible(true);
         }

         Object var9;
         try {
            var9 = var1.cast(var5.get(var2));
         } finally {
            if (!var7) {
               var6.setAccessible(false);
            }

         }

         return var9;
      } catch (IllegalArgumentException var17) {
         CustomHeadApi.log(var4 + ": unsupported WorldEdit version.");
      } catch (IllegalAccessException var18) {
         CustomHeadApi.log(var4 + ": security exception.");
      } catch (NoSuchFieldException var19) {
         CustomHeadApi.log(var4 + ": unsupported WorldEdit version, field " + var3 + " not found.");
      } catch (SecurityException var20) {
         CustomHeadApi.log(var4 + ": security exception.");
      } catch (ClassCastException var21) {
         CustomHeadApi.log(var4 + ": unsupported WorldEdit version, unable to cast result.");
      }

      return null;
   }

   public static boolean set(Object var0, String var1, Object var2, String var3) {
      return set(var0.getClass(), var0, var1, var2, var3);
   }

   public static boolean set(Class<?> var0, String var1, Object var2, String var3) {
      return set(var0, (Object)null, var1, var2, var3);
   }

   public static boolean set(Class<?> var0, Object var1, String var2, Object var3, String var4) {
      try {
         Field var5 = var0.getDeclaredField(var2);
         boolean var6 = var5.isAccessible();
         Field var7 = Field.class.getDeclaredField("modifiers");
         int var8 = var7.getModifiers();
         boolean var9 = (var8 & 16) == 16;
         if (!var6) {
            var5.setAccessible(true);
         }

         if (var9) {
            var7.setAccessible(true);
            var7.setInt(var5, var8 & -17);
         }

         try {
            var5.set(var1, var3);
         } finally {
            if (var9) {
               var7.setInt(var5, var8 | 16);
            }

            if (!var6) {
               var5.setAccessible(false);
            }

         }

         return true;
      } catch (IllegalArgumentException var17) {
         CustomHeadApi.log(var4 + ": unsupported WorldEdit version.");
      } catch (IllegalAccessException var18) {
         CustomHeadApi.log(var4 + ": security exception.");
      } catch (NoSuchFieldException var19) {
         CustomHeadApi.log(var4 + ": unsupported WorldEdit version, field " + var2 + " not found.");
      } catch (SecurityException var20) {
         CustomHeadApi.log(var4 + ": security exception.");
      }

      return false;
   }

   public static boolean set(Object var0, Field var1, Object var2, String var3) {
      try {
         boolean var4 = var1.isAccessible();
         Field var5 = Field.class.getDeclaredField("modifiers");
         int var6 = var5.getModifiers();
         boolean var7 = (var6 & 16) == 16;
         if (!var4) {
            var1.setAccessible(true);
         }

         if (var7) {
            var5.setAccessible(true);
            var5.setInt(var1, var6 & -17);
         }

         try {
            var1.set(var0, var2);
         } finally {
            if (var7) {
               var5.setInt(var1, var6 | 16);
            }

            if (!var4) {
               var1.setAccessible(false);
            }

         }

         return true;
      } catch (IllegalArgumentException var15) {
         CustomHeadApi.log(var3 + ": unsupported WorldEdit version.");
      } catch (IllegalAccessException var16) {
         CustomHeadApi.log(var3 + ": security exception.");
      } catch (NoSuchFieldException var17) {
         CustomHeadApi.log(var3 + ": unsupported WorldEdit version, field modifiers not found.");
      } catch (SecurityException var18) {
         CustomHeadApi.log(var3 + ": security exception.");
      }

      return false;
   }
}
