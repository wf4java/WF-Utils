package wf.utils.java.object;

import wf.utils.java.misc.annotation.Nullable;
import wf.utils.java.misc.Assert;

import java.io.File;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.time.ZoneId;
import java.time.temporal.Temporal;
import java.util.*;
import java.util.regex.Pattern;

public class ObjectUtils {

    private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];


    public ObjectUtils() {
    }

    public static boolean isCheckedException(Throwable ex) {
        return !(ex instanceof RuntimeException) && !(ex instanceof Error);
    }

    public static boolean isCompatibleWithThrowsClause(Throwable ex, @Nullable Class<?>... declaredExceptions) {
        if (!isCheckedException(ex)) {
            return true;
        } else {
            if (declaredExceptions != null) {
                Class[] var2 = declaredExceptions;
                int var3 = declaredExceptions.length;

                for(int var4 = 0; var4 < var3; ++var4) {
                    Class<?> declaredException = var2[var4];
                    if (declaredException.isInstance(ex)) {
                        return true;
                    }
                }
            }

            return false;
        }
    }

    public static boolean isArray(@Nullable Object obj) {
        return obj != null && obj.getClass().isArray();
    }

    public static boolean isEmpty(@Nullable Object[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(@Nullable Object obj) {
        if (obj == null) {
            return true;
        } else if (obj instanceof Optional) {
            Optional<?> optional = (Optional)obj;
            return !optional.isPresent();
        } else if (obj instanceof CharSequence) {
            CharSequence charSequence = (CharSequence)obj;
            return charSequence.length() == 0;
        } else if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        } else if (obj instanceof Collection) {
            Collection<?> collection = (Collection)obj;
            return collection.isEmpty();
        } else if (obj instanceof Map) {
            Map<?, ?> map = (Map)obj;
            return map.isEmpty();
        } else {
            return false;
        }
    }

    @Nullable
    public static Object unwrapOptional(@Nullable Object obj) {
        if (obj instanceof Optional<?>) {
            if (!((Optional<?>)obj).isPresent()) {
                return null;
            } else {
                Object result = ((Optional<?>)obj).get();
                Assert.isTrue(!(result instanceof Optional), "Multi-level Optional usage not supported");
                return result;
            }
        } else {
            return obj;
        }
    }

    public static boolean containsElement(@Nullable Object[] array, Object element) {
        if (array == null) {
            return false;
        } else {
            Object[] var2 = array;
            int var3 = array.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                Object arrayEle = var2[var4];
                if (nullSafeEquals(arrayEle, element)) {
                    return true;
                }
            }

            return false;
        }
    }

    public static boolean containsConstant(Enum<?>[] enumValues, String constant) {
        return containsConstant(enumValues, constant, false);
    }

    public static boolean containsConstant(Enum<?>[] enumValues, String constant, boolean caseSensitive) {
        Enum[] var3 = enumValues;
        int var4 = enumValues.length;
        int var5 = 0;

        while(true) {
            if (var5 >= var4) {
                return false;
            }

            Enum<?> candidate = var3[var5];
            if (caseSensitive) {
                if (candidate.toString().equals(constant)) {
                    break;
                }
            } else if (candidate.toString().equalsIgnoreCase(constant)) {
                break;
            }

            ++var5;
        }

        return true;
    }

    public static <E extends Enum<?>> E caseInsensitiveValueOf(E[] enumValues, String constant) {
        Enum[] var2 = enumValues;
        int var3 = enumValues.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            E candidate = (E) var2[var4];
            if (candidate.toString().equalsIgnoreCase(constant)) {
                return candidate;
            }
        }

        throw new IllegalArgumentException("Constant [" + constant + "] does not exist in enum type " + enumValues.getClass().getComponentType().getName());
    }

    public static <A, O extends A> A[] addObjectToArray(@Nullable A[] array, @Nullable O obj) {
        return addObjectToArray(array, obj, array != null ? array.length : 0);
    }

    public static <A, O extends A> A[] addObjectToArray(@Nullable A[] array, @Nullable O obj, int position) {
        Class<?> componentType = Object.class;
        if (array != null) {
            componentType = array.getClass().getComponentType();
        } else if (obj != null) {
            componentType = obj.getClass();
        }

        int newArrayLength = array != null ? array.length + 1 : 1;
        A[] newArray = (A[]) Array.newInstance(componentType, newArrayLength);
        if (array != null) {
            System.arraycopy(array, 0, newArray, 0, position);
            System.arraycopy(array, position, newArray, position + 1, array.length - position);
        }

        newArray[position] = obj;
        return newArray;
    }

    public static Object[] toObjectArray(@Nullable Object source) {
        if (source instanceof Object[]) {
            return (Object[]) source;
        } else if (source == null) {
            return EMPTY_OBJECT_ARRAY;
        } else if (!source.getClass().isArray()) {
            throw new IllegalArgumentException("Source is not an array: " + source);
        } else {
            int length = Array.getLength(source);
            if (length == 0) {
                return EMPTY_OBJECT_ARRAY;
            } else {
                Class<?> wrapperType = Array.get(source, 0).getClass();
                Object[] newArray = (Object[])Array.newInstance(wrapperType, length);

                for(int i = 0; i < length; ++i) {
                    newArray[i] = Array.get(source, i);
                }

                return newArray;
            }
        }
    }

    public static boolean nullSafeEquals(@Nullable Object o1, @Nullable Object o2) {
        if (o1 == o2) {
            return true;
        } else if (o1 != null && o2 != null) {
            if (o1.equals(o2)) {
                return true;
            } else {
                return o1.getClass().isArray() && o2.getClass().isArray() ? arrayEquals(o1, o2) : false;
            }
        } else {
            return false;
        }
    }

    private static boolean arrayEquals(Object o1, Object o2) {
        if (o1 instanceof Object[]) {
            if (o2 instanceof Object[]) {
                return Arrays.equals((Object[]) o1, (Object[])  o2);
            }
        }

        if (o1 instanceof boolean[]) {
            if (o2 instanceof boolean[]) {
                return Arrays.equals((boolean[]) o1, (boolean[])  o2);
            }
        }

        if (o1 instanceof byte[]) {
            if (o2 instanceof byte[]) {
                return Arrays.equals((byte[]) o1, (byte[])  o2);
            }
        }

        if (o1 instanceof char[]) {
            if (o2 instanceof char[]) {
                return Arrays.equals((char[]) o1, (char[])  o2);
            }
        }

        if (o1 instanceof double[]) {
            if (o2 instanceof double[]) {
                return Arrays.equals((double[]) o1, (double[])  o2);
            }
        }

        if (o1 instanceof float[]) {
            if (o2 instanceof float[]) {
                return Arrays.equals((float[]) o1, (float[])  o2);
            }
        }

        if (o1 instanceof int[]) {
            if (o2 instanceof int[]) {
                return Arrays.equals((int[]) o1, (int[])  o2);
            }
        }

        if (o1 instanceof long[]) {
            if (o2 instanceof long[]) {
                return Arrays.equals((long[]) o1, (long[])  o2);
            }
        }

        if (o1 instanceof short[]) {
            if (o2 instanceof short[]) {
                return Arrays.equals((short[]) o1, (short[])  o2);
            }
        }

        return false;
    }

    public static int nullSafeHashCode(@Nullable Object obj) {
        if (obj == null) {
            return 0;
        } else {
            if (obj.getClass().isArray()) {
                if (obj instanceof Object[]) {
                    Object[] objects = (Object[])obj;
                    return nullSafeHashCode(objects);
                }

                if (obj instanceof boolean[]) {
                    boolean[] booleans = (boolean[])obj;
                    return nullSafeHashCode(booleans);
                }

                if (obj instanceof byte[]) {
                    byte[] bytes = (byte[])obj;
                    return nullSafeHashCode(bytes);
                }

                if (obj instanceof char[]) {
                    char[] chars = (char[])obj;
                    return nullSafeHashCode(chars);
                }

                if (obj instanceof double[]) {
                    double[] doubles = (double[])obj;
                    return nullSafeHashCode(doubles);
                }

                if (obj instanceof float[]) {
                    float[] floats = (float[])obj;
                    return nullSafeHashCode(floats);
                }

                if (obj instanceof int[]) {
                    int[] ints = (int[])obj;
                    return nullSafeHashCode(ints);
                }

                if (obj instanceof long[]) {
                    long[] longs = (long[])obj;
                    return nullSafeHashCode(longs);
                }

                if (obj instanceof short[]) {
                    short[] shorts = (short[])obj;
                    return nullSafeHashCode(shorts);
                }
            }

            return obj.hashCode();
        }
    }

    public static int nullSafeHashCode(@Nullable Object[] array) {
        if (array == null) {
            return 0;
        } else {
            int hash = 7;
            Object[] var2 = array;
            int var3 = array.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                Object element = var2[var4];
                hash = 31 * hash + nullSafeHashCode(element);
            }

            return hash;
        }
    }

    public static int nullSafeHashCode(@Nullable boolean[] array) {
        if (array == null) {
            return 0;
        } else {
            int hash = 7;
            boolean[] var2 = array;
            int var3 = array.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                boolean element = var2[var4];
                hash = 31 * hash + Boolean.hashCode(element);
            }

            return hash;
        }
    }

    public static int nullSafeHashCode(@Nullable byte[] array) {
        if (array == null) {
            return 0;
        } else {
            int hash = 7;
            byte[] var2 = array;
            int var3 = array.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                byte element = var2[var4];
                hash = 31 * hash + element;
            }

            return hash;
        }
    }

    public static int nullSafeHashCode(@Nullable char[] array) {
        if (array == null) {
            return 0;
        } else {
            int hash = 7;
            char[] var2 = array;
            int var3 = array.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                char element = var2[var4];
                hash = 31 * hash + element;
            }

            return hash;
        }
    }

    public static int nullSafeHashCode(@Nullable double[] array) {
        if (array == null) {
            return 0;
        } else {
            int hash = 7;
            double[] var2 = array;
            int var3 = array.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                double element = var2[var4];
                hash = 31 * hash + Double.hashCode(element);
            }

            return hash;
        }
    }

    public static int nullSafeHashCode(@Nullable float[] array) {
        if (array == null) {
            return 0;
        } else {
            int hash = 7;
            float[] var2 = array;
            int var3 = array.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                float element = var2[var4];
                hash = 31 * hash + Float.hashCode(element);
            }

            return hash;
        }
    }

    public static int nullSafeHashCode(@Nullable int[] array) {
        if (array == null) {
            return 0;
        } else {
            int hash = 7;
            int[] var2 = array;
            int var3 = array.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                int element = var2[var4];
                hash = 31 * hash + element;
            }

            return hash;
        }
    }

    public static int nullSafeHashCode(@Nullable long[] array) {
        if (array == null) {
            return 0;
        } else {
            int hash = 7;
            long[] var2 = array;
            int var3 = array.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                long element = var2[var4];
                hash = 31 * hash + Long.hashCode(element);
            }

            return hash;
        }
    }

    public static int nullSafeHashCode(@Nullable short[] array) {
        if (array == null) {
            return 0;
        } else {
            int hash = 7;
            short[] var2 = array;
            int var3 = array.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                short element = var2[var4];
                hash = 31 * hash + element;
            }

            return hash;
        }
    }

    public static String identityToString(@Nullable Object obj) {
        if (obj == null) {
            return "";
        } else {
            String var10000 = obj.getClass().getName();
            return var10000 + "@" + getIdentityHexString(obj);
        }
    }

    public static String getIdentityHexString(Object obj) {
        return Integer.toHexString(System.identityHashCode(obj));
    }

    public static String getDisplayString(@Nullable Object obj) {
        return obj == null ? "" : nullSafeToString(obj);
    }

    public static String nullSafeClassName(@Nullable Object obj) {
        return obj != null ? obj.getClass().getName() : "null";
    }

    public static String nullSafeToString(@Nullable Object obj) {
        if (obj == null) {
            return "null";
        } else {
            String str;
            if (obj instanceof String) {
                str = (String)obj;
                return str;
            } else if (obj instanceof Object[]) {
                Object[] objects = (Object[])obj;
                return nullSafeToString(objects);
            } else if (obj instanceof boolean[]) {
                boolean[] booleans = (boolean[])obj;
                return nullSafeToString(booleans);
            } else if (obj instanceof byte[]) {
                byte[] bytes = (byte[])obj;
                return nullSafeToString(bytes);
            } else if (obj instanceof char[]) {
                char[] chars = (char[])obj;
                return nullSafeToString(chars);
            } else if (obj instanceof double[]) {
                double[] doubles = (double[])obj;
                return nullSafeToString(doubles);
            } else if (obj instanceof float[]) {
                float[] floats = (float[])obj;
                return nullSafeToString(floats);
            } else if (obj instanceof int[]) {
                int[] ints = (int[])obj;
                return nullSafeToString(ints);
            } else if (obj instanceof long[]) {
                long[] longs = (long[])obj;
                return nullSafeToString(longs);
            } else if (obj instanceof short[]) {
                short[] shorts = (short[])obj;
                return nullSafeToString(shorts);
            } else {
                str = obj.toString();
                return str != null ? str : "";
            }
        }
    }

    public static String nullSafeToString(@Nullable Object[] array) {
        if (array == null) {
            return "null";
        } else {
            int length = array.length;
            if (length == 0) {
                return "{}";
            } else {
                StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
                Object[] var3 = array;
                int var4 = array.length;

                for(int var5 = 0; var5 < var4; ++var5) {
                    Object o = var3[var5];
                    stringJoiner.add(String.valueOf(o));
                }

                return stringJoiner.toString();
            }
        }
    }

    public static String nullSafeToString(@Nullable boolean[] array) {
        if (array == null) {
            return "null";
        } else {
            int length = array.length;
            if (length == 0) {
                return "{}";
            } else {
                StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
                boolean[] var3 = array;
                int var4 = array.length;

                for(int var5 = 0; var5 < var4; ++var5) {
                    boolean b = var3[var5];
                    stringJoiner.add(String.valueOf(b));
                }

                return stringJoiner.toString();
            }
        }
    }

    public static String nullSafeToString(@Nullable byte[] array) {
        if (array == null) {
            return "null";
        } else {
            int length = array.length;
            if (length == 0) {
                return "{}";
            } else {
                StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
                byte[] var3 = array;
                int var4 = array.length;

                for(int var5 = 0; var5 < var4; ++var5) {
                    byte b = var3[var5];
                    stringJoiner.add(String.valueOf(b));
                }

                return stringJoiner.toString();
            }
        }
    }

    public static String nullSafeToString(@Nullable char[] array) {
        if (array == null) {
            return "null";
        } else {
            int length = array.length;
            if (length == 0) {
                return "{}";
            } else {
                StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
                char[] var3 = array;
                int var4 = array.length;

                for(int var5 = 0; var5 < var4; ++var5) {
                    char c = var3[var5];
                    stringJoiner.add("'" + String.valueOf(c) + "'");
                }

                return stringJoiner.toString();
            }
        }
    }

    public static String nullSafeToString(@Nullable double[] array) {
        if (array == null) {
            return "null";
        } else {
            int length = array.length;
            if (length == 0) {
                return "{}";
            } else {
                StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
                double[] var3 = array;
                int var4 = array.length;

                for(int var5 = 0; var5 < var4; ++var5) {
                    double d = var3[var5];
                    stringJoiner.add(String.valueOf(d));
                }

                return stringJoiner.toString();
            }
        }
    }

    public static String nullSafeToString(@Nullable float[] array) {
        if (array == null) {
            return "null";
        } else {
            int length = array.length;
            if (length == 0) {
                return "{}";
            } else {
                StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
                float[] var3 = array;
                int var4 = array.length;

                for(int var5 = 0; var5 < var4; ++var5) {
                    float f = var3[var5];
                    stringJoiner.add(String.valueOf(f));
                }

                return stringJoiner.toString();
            }
        }
    }

    public static String nullSafeToString(@Nullable int[] array) {
        if (array == null) {
            return "null";
        } else {
            int length = array.length;
            if (length == 0) {
                return "{}";
            } else {
                StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
                int[] var3 = array;
                int var4 = array.length;

                for(int var5 = 0; var5 < var4; ++var5) {
                    int i = var3[var5];
                    stringJoiner.add(String.valueOf(i));
                }

                return stringJoiner.toString();
            }
        }
    }

    public static String nullSafeToString(@Nullable long[] array) {
        if (array == null) {
            return "null";
        } else {
            int length = array.length;
            if (length == 0) {
                return "{}";
            } else {
                StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
                long[] var3 = array;
                int var4 = array.length;

                for(int var5 = 0; var5 < var4; ++var5) {
                    long l = var3[var5];
                    stringJoiner.add(String.valueOf(l));
                }

                return stringJoiner.toString();
            }
        }
    }

    public static String nullSafeToString(@Nullable short[] array) {
        if (array == null) {
            return "null";
        } else {
            int length = array.length;
            if (length == 0) {
                return "{}";
            } else {
                StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
                short[] var3 = array;
                int var4 = array.length;

                for(int var5 = 0; var5 < var4; ++var5) {
                    short s = var3[var5];
                    stringJoiner.add(String.valueOf(s));
                }

                return stringJoiner.toString();
            }
        }
    }



    private static boolean isSimpleValueType(Class<?> type) {
        return Void.class != type && Void.TYPE != type && (ClassUtils.isPrimitiveOrWrapper(type) || Enum.class.isAssignableFrom(type) || CharSequence.class.isAssignableFrom(type) || Number.class.isAssignableFrom(type) || Date.class.isAssignableFrom(type) || Temporal.class.isAssignableFrom(type) || ZoneId.class.isAssignableFrom(type) || TimeZone.class.isAssignableFrom(type) || File.class.isAssignableFrom(type) || Path.class.isAssignableFrom(type) || Charset.class.isAssignableFrom(type) || Currency.class.isAssignableFrom(type) || InetAddress.class.isAssignableFrom(type) || URI.class == type || URL.class == type || UUID.class == type || Locale.class == type || Pattern.class == type || Class.class == type);
    }
}
