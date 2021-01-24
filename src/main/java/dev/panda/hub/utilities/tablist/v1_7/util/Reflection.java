package dev.panda.hub.utilities.tablist.v1_7.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.Bukkit;

public final class Reflection {

   private static final String OBC_PREFIX = Bukkit.getServer().getClass().getPackage().getName();
   private static final String VERSION;
   private static final String NMS_PREFIX;
   private static final Pattern MATCH_VARIABLE;

   private Reflection() {
   }

   public static Reflection.ConstructorInvoker getConstructor(String v1, Class<?>... clazzs) {
      return getConstructor(getClass(v1), clazzs);
   }

   private static String expandVariables(String input) {
      StringBuffer sBuffer = new StringBuffer();

      Matcher matcher;
      String s;
      for(matcher = MATCH_VARIABLE.matcher(input); matcher.find(); matcher.appendReplacement(sBuffer, Matcher.quoteReplacement(s))) {
         String string = matcher.group(1);
         s = "";
         if ("nms".equalsIgnoreCase(string)) {
            s = NMS_PREFIX;
         } else if ("obc".equalsIgnoreCase(string)) {
            s = OBC_PREFIX;
         } else {
            if (!"version".equalsIgnoreCase(string)) {
               throw new IllegalArgumentException(String.valueOf((new StringBuilder()).append("Unknown variable: ").append(string)));
            }

            s = VERSION;
         }

         if (s.length() > 0 && matcher.end() < input.length() && input.charAt(matcher.end()) != '.') {
            s = String.valueOf((new StringBuilder()).append(s).append("."));
         }
      }

      matcher.appendTail(sBuffer);
      return sBuffer.toString();
   }

   public static <T> Reflection.FieldAccessor<T> getField(Class<?> clazz, String input, Class<T> clazz2) {
      return getField(clazz, input, clazz2, 0);
   }

   public static <T> Reflection.FieldAccessor<T> getField(String input, String input2, Class<T> clazz) {
      return getField(getClass(input), input2, clazz, 0);
   }

   public static Reflection.MethodInvoker getMethod(String input, String input2, Class<?>... clazzs) {
      return getTypedMethod(getClass(input), input2, (Class)null, clazzs);
   }

   private static Class<?> getCanonicalClass(String input) {
      try {
         return Class.forName(input);
      } catch (ClassNotFoundException var2) {
         throw new IllegalArgumentException(String.valueOf((new StringBuilder()).append("Cannot find ").append(input)), var2);
      }
   }

   private static <T> Reflection.FieldAccessor<T> getField(Class<?> clazz, String input, Class<T> clazz2, int value) {
      Field[] fields = clazz.getDeclaredFields();
      short length = (short) fields.length;

      for(int i = 0; i < length; ++i) {
         final Field f = fields[i];
         if ((input == null || f.getName().equals(input)) && clazz2.isAssignableFrom(f.getType()) && value-- <= 0) {
            f.setAccessible(true);
            return new Reflection.FieldAccessor<T>() {
               public void set(Object object, Object object2) {
                  try {
                     f.set(object, object2);
                  } catch (IllegalAccessException var4) {
                     throw new RuntimeException("Cannot access reflection.", var4);
                  }
               }

               public T get(Object object3) {
                  try {
                     return (T) f.get(object3);
                  } catch (IllegalAccessException var3) {
                     throw new RuntimeException("Cannot access reflection.", var3);
                  }
               }

               public boolean hasField(Object object4) {
                  return f.getDeclaringClass().isAssignableFrom(object4.getClass());
               }
            };
         }
      }

      if (clazz.getSuperclass() != null) {
         return getField(clazz.getSuperclass(), input, clazz2, value);
      } else {
         throw new IllegalArgumentException(String.valueOf((new StringBuilder()).append("Cannot find field with type ").append(clazz2)));
      }
   }

   public static Class<?> getMinecraftClass(String input) {
      return getCanonicalClass(String.valueOf((new StringBuilder()).append(NMS_PREFIX).append(".").append(input)));
   }

   public static Class<?> getClass(String input) {
      return getCanonicalClass(expandVariables(input));
   }

   public static <T> Reflection.FieldAccessor<T> getField(String input, Class<T> clazz, int value) {
      return getField(getClass(input), clazz, value);
   }

   static {
      NMS_PREFIX = OBC_PREFIX.replace("org.bukkit.craftbukkit", "net.minecraft.server");
      VERSION = OBC_PREFIX.replace("org.bukkit.craftbukkit", "").replace(".", "");
      MATCH_VARIABLE = Pattern.compile("\\{([^\\}]+)\\}");
   }

   public static Reflection.MethodInvoker getTypedMethod(Class<?> clazz, String input, Class<?> clazz2, Class<?>... clazzs) {
      Method[] methods = clazz.getDeclaredMethods();
      long length = methods.length;

      for(int i = 0; i < length; ++i) {
         final Method method = methods[i];
         if ((input == null || method.getName().equals(input)) && (clazz2 == null || method.getReturnType().equals(clazz2)) && Arrays.equals(method.getParameterTypes(), clazzs)) {
            method.setAccessible(true);
            return new Reflection.MethodInvoker() {
               public Object invoke(Object object, Object... objects) {
                  try {
                     return method.invoke(object, objects);
                  } catch (Exception var4) {
                     throw new RuntimeException(String.valueOf((new StringBuilder()).append("Cannot invoke method ").append(method)), var4);
                  }
               }
            };
         }
      }

      if (clazz.getSuperclass() != null) {
         return getMethod(clazz.getSuperclass(), input, clazzs);
      } else {
         throw new IllegalStateException(String.format("Unable to find method %s (%s).", input, Arrays.asList(clazzs)));
      }
   }

   public static Reflection.ConstructorInvoker getConstructor(Class<?> clazz, Class<?>... clazzs) {
      Constructor<?>[] constructors = clazz.getDeclaredConstructors();
      int length = constructors.length;

      for(int i = 0; i < length; ++i) {
         final Constructor<?> constructor = constructors[i];
         if (Arrays.equals(constructor.getParameterTypes(), clazzs)) {
            constructor.setAccessible(true);
            return new Reflection.ConstructorInvoker() {
               public Object invoke(Object... objects) {
                  try {
                     return constructor.newInstance(objects);
                  } catch (Exception var3) {
                     throw new RuntimeException(String.valueOf((new StringBuilder()).append("Cannot invoke constructor ").append(constructor)), var3);
                  }
               }
            };
         }
      }

      throw new IllegalStateException(String.format("Unable to find constructor for %s (%s).", clazz, Arrays.asList(clazzs)));
   }

   public static <T> Reflection.FieldAccessor<T> getField(Class<?> clazz, Class<T> clazz1, int value) {
      return getField(clazz, (String)null, clazz1, value);
   }

   public static Reflection.MethodInvoker getMethod(Class<?> clazz, String input, Class<?>... clazzs) {
      return getTypedMethod(clazz, input, (Class)null, clazzs);
   }

   public static Class<?> getCraftBukkitClass(String input) {
      return getCanonicalClass(String.valueOf((new StringBuilder()).append(OBC_PREFIX).append(".").append(input)));
   }

   public static Class<Object> getUntypedClass(String input) {
      Class<Object> toReturn = (Class<Object>) getClass(input);
      return toReturn;
   }

   public interface MethodInvoker {
      Object invoke(Object var1, Object... var2);
   }

   public interface FieldAccessor<T> {
      void set(Object var1, Object var2);

      boolean hasField(Object var1);

      T get(Object var1);
   }

   public interface ConstructorInvoker {
      Object invoke(Object... var1);
   }
}
