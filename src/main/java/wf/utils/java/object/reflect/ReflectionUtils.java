package wf.utils.java.object.reflect;

import wf.utils.java.misc.annotation.Nullable;
import wf.utils.java.data.list.ConcurrentReferenceHashMap;
import wf.utils.java.misc.Assert;

import java.lang.reflect.*;
import java.util.*;

public class ReflectionUtils {

    public static final MethodFilter USER_DECLARED_METHODS = (method) -> !method.isBridge() && !method.isSynthetic() && method.getDeclaringClass() != Object.class;
    public static final FieldFilter COPYABLE_FIELDS = (field) -> !Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers());
    private static final Class<?>[] EMPTY_CLASS_ARRAY = new Class[0];
    private static final Method[] EMPTY_METHOD_ARRAY = new Method[0];
    private static final Field[] EMPTY_FIELD_ARRAY = new Field[0];
    private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
    private static final Map<Class<?>, Method[]> declaredMethodsCache = new ConcurrentReferenceHashMap<>(256);
    private static final Map<Class<?>, Field[]> declaredFieldsCache = new ConcurrentReferenceHashMap<>(256);


    public static Object getValue(Object o, String s){
        try {
            Field field = o.getClass().getDeclaredField(s);
            field.setAccessible(true);
            return field.get(o);
        } catch (NoSuchFieldException | IllegalAccessException e) {throw new RuntimeException(e);}
    }


    public static <T> T getValue(Object o, String s, Class<T> c){
        try {
            Field field = o.getClass().getDeclaredField(s);
            field.setAccessible(true);
            return c.cast(field.get(o));
        } catch (NoSuchFieldException | IllegalAccessException e) {throw new RuntimeException(e);}
    }


    public static void setValue(Object o, String s, Object value){
        try {
            Field field = o.getClass().getDeclaredField(s);
            field.setAccessible(true);
            field.set(o, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {throw new RuntimeException(e);}
    }



    public static void handleReflectionException(Exception ex) {
        if (ex instanceof NoSuchMethodException) {
            throw new IllegalStateException("Method not found: " + ex.getMessage());
        } else if (ex instanceof IllegalAccessException) {
            throw new IllegalStateException("Could not access method or field: " + ex.getMessage());
        } else {
            if (ex instanceof InvocationTargetException) {
                InvocationTargetException invocationTargetException = (InvocationTargetException)ex;
                handleInvocationTargetException(invocationTargetException);
            }

            if (ex instanceof RuntimeException) {
                RuntimeException runtimeException = (RuntimeException)ex;
                throw runtimeException;
            } else {
                throw new UndeclaredThrowableException(ex);
            }
        }
    }

    public static void handleInvocationTargetException(InvocationTargetException ex) {
        rethrowRuntimeException(ex.getTargetException());
    }

    public static void rethrowRuntimeException(Throwable ex) {
        if (ex instanceof RuntimeException) {
            throw (RuntimeException) ex;
        } else if (ex instanceof Error) {
            throw (Error) ex;
        } else {
            throw new UndeclaredThrowableException(ex);
        }
    }

    public static void rethrowException(Throwable throwable) throws Exception {
        if (throwable instanceof Exception) {
            throw (RuntimeException) throwable;
        } else if (throwable instanceof Error) {
            throw (Error) throwable;
        } else {
            throw new UndeclaredThrowableException(throwable);
        }
    }

    public static <T> Constructor<T> accessibleConstructor(Class<T> clazz, Class<?>... parameterTypes) throws NoSuchMethodException {
        Constructor<T> ctor = clazz.getDeclaredConstructor(parameterTypes);
        makeAccessible(ctor);
        return ctor;
    }

    public static void makeAccessible(Constructor<?> ctor) {
        if ((!Modifier.isPublic(ctor.getModifiers()) || !Modifier.isPublic(ctor.getDeclaringClass().getModifiers())) && !ctor.isAccessible()) {
            ctor.setAccessible(true);
        }

    }

    @Nullable
    public static Method findMethod(Class<?> clazz, String name) {
        return findMethod(clazz, name, EMPTY_CLASS_ARRAY);
    }

    @Nullable
    public static Method findMethod(Class<?> clazz, String name, @Nullable Class<?>... paramTypes) {
        Assert.notNull(clazz, "Class must not be null");
        Assert.notNull(name, "Method name must not be null");

        for(Class<?> searchType = clazz; searchType != null; searchType = searchType.getSuperclass()) {
            Method[] methods = searchType.isInterface() ? searchType.getMethods() : getDeclaredMethods(searchType, false);
            Method[] var5 = methods;
            int var6 = methods.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                Method method = var5[var7];
                if (name.equals(method.getName()) && (paramTypes == null || hasSameParams(method, paramTypes))) {
                    return method;
                }
            }
        }

        return null;
    }

    private static boolean hasSameParams(Method method, Class<?>[] paramTypes) {
        return paramTypes.length == method.getParameterCount() && Arrays.equals(paramTypes, method.getParameterTypes());
    }

    @Nullable
    public static Object invokeMethod(Method method, @Nullable Object target) {
        return invokeMethod(method, target, EMPTY_OBJECT_ARRAY);
    }

    @Nullable
    public static Object invokeMethod(Method method, @Nullable Object target, @Nullable Object... args) {
        try {
            return method.invoke(target, args);
        } catch (Exception var4) {
            handleReflectionException(var4);
            throw new IllegalStateException("Should never get here");
        }
    }

    public static boolean declaresException(Method method, Class<?> exceptionType) {
        Assert.notNull(method, "Method must not be null");
        Class<?>[] declaredExceptions = method.getExceptionTypes();
        Class[] var3 = declaredExceptions;
        int var4 = declaredExceptions.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Class<?> declaredException = var3[var5];
            if (declaredException.isAssignableFrom(exceptionType)) {
                return true;
            }
        }

        return false;
    }

    public static void doWithLocalMethods(Class<?> clazz, MethodCallback mc) {
        Method[] methods = getDeclaredMethods(clazz, false);
        Method[] var3 = methods;
        int var4 = methods.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Method method = var3[var5];

            try {
                mc.doWith(method);
            } catch (IllegalAccessException var8) {
                String var10002 = method.getName();
                throw new IllegalStateException("Not allowed to access method '" + var10002 + "': " + var8);
            }
        }

    }

    public static void doWithMethods(Class<?> clazz, MethodCallback mc) {
        doWithMethods(clazz, mc, (MethodFilter)null);
    }

    public static void doWithMethods(Class<?> clazz, MethodCallback mc, @Nullable MethodFilter mf) {
        if (mf != USER_DECLARED_METHODS || clazz != Object.class) {
            Method[] methods = getDeclaredMethods(clazz, false);
            Method[] var4 = methods;
            int var5 = methods.length;

            int var6;
            for(var6 = 0; var6 < var5; ++var6) {
                Method method = var4[var6];
                if (mf == null || mf.matches(method)) {
                    try {
                        mc.doWith(method);
                    } catch (IllegalAccessException var9) {
                        String var10002 = method.getName();
                        throw new IllegalStateException("Not allowed to access method '" + var10002 + "': " + var9);
                    }
                }
            }

            if (clazz.getSuperclass() == null || mf == USER_DECLARED_METHODS && clazz.getSuperclass() == Object.class) {
                if (clazz.isInterface()) {
                    Class[] var10 = clazz.getInterfaces();
                    var5 = var10.length;

                    for(var6 = 0; var6 < var5; ++var6) {
                        Class<?> superIfc = var10[var6];
                        doWithMethods(superIfc, mc, mf);
                    }
                }
            } else {
                doWithMethods(clazz.getSuperclass(), mc, mf);
            }

        }
    }

    public static Method[] getAllDeclaredMethods(Class<?> leafClass) {
        List<Method> methods = new ArrayList(20);
        Objects.requireNonNull(methods);
        doWithMethods(leafClass, methods::add);
        return (Method[])methods.toArray(EMPTY_METHOD_ARRAY);
    }

    public static Method[] getUniqueDeclaredMethods(Class<?> leafClass) {
        return getUniqueDeclaredMethods(leafClass, (MethodFilter)null);
    }

    public static Method[] getUniqueDeclaredMethods(Class<?> leafClass, @Nullable MethodFilter mf) {
        List<Method> methods = new ArrayList(20);
        doWithMethods(leafClass, (method) -> {
            boolean knownSignature = false;
            Method methodBeingOverriddenWithCovariantReturnType = null;
            Iterator var4 = methods.iterator();

            while(var4.hasNext()) {
                Method existingMethod = (Method)var4.next();
                if (method.getName().equals(existingMethod.getName()) && method.getParameterCount() == existingMethod.getParameterCount() && Arrays.equals(method.getParameterTypes(), existingMethod.getParameterTypes())) {
                    if (existingMethod.getReturnType() != method.getReturnType() && existingMethod.getReturnType().isAssignableFrom(method.getReturnType())) {
                        methodBeingOverriddenWithCovariantReturnType = existingMethod;
                        break;
                    }

                    knownSignature = true;
                    break;
                }
            }

            if (methodBeingOverriddenWithCovariantReturnType != null) {
                methods.remove(methodBeingOverriddenWithCovariantReturnType);
            }

            if (!knownSignature && !isCglibRenamedMethod(method)) {
                methods.add(method);
            }

        }, mf);
        return (Method[])methods.toArray(EMPTY_METHOD_ARRAY);
    }

    public static Method[] getDeclaredMethods(Class<?> clazz) {
        return getDeclaredMethods(clazz, true);
    }

    private static Method[] getDeclaredMethods(Class<?> clazz, boolean defensive) {
        Assert.notNull(clazz, "Class must not be null");
        Method[] result = (Method[])declaredMethodsCache.get(clazz);
        if (result == null) {
            try {
                Method[] declaredMethods = clazz.getDeclaredMethods();
                List<Method> defaultMethods = findConcreteMethodsOnInterfaces(clazz);
                if (defaultMethods != null) {
                    result = new Method[declaredMethods.length + defaultMethods.size()];
                    System.arraycopy(declaredMethods, 0, result, 0, declaredMethods.length);
                    int index = declaredMethods.length;

                    for(Iterator var6 = defaultMethods.iterator(); var6.hasNext(); ++index) {
                        Method defaultMethod = (Method)var6.next();
                        result[index] = defaultMethod;
                    }
                } else {
                    result = declaredMethods;
                }

                declaredMethodsCache.put(clazz, result.length == 0 ? EMPTY_METHOD_ARRAY : result);
            } catch (Throwable var8) {
                throw new IllegalStateException("Failed to introspect Class [" + clazz.getName() + "] from ClassLoader [" + clazz.getClassLoader() + "]", var8);
            }
        }

        return result.length != 0 && defensive ? (Method[])result.clone() : result;
    }

    @Nullable
    private static List<Method> findConcreteMethodsOnInterfaces(Class<?> clazz) {
        List<Method> result = null;
        Class[] var2 = clazz.getInterfaces();
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            Class<?> ifc = var2[var4];
            Method[] var6 = ifc.getMethods();
            int var7 = var6.length;

            for(int var8 = 0; var8 < var7; ++var8) {
                Method ifcMethod = var6[var8];
                if (!Modifier.isAbstract(ifcMethod.getModifiers())) {
                    if (result == null) {
                        result = new ArrayList();
                    }

                    result.add(ifcMethod);
                }
            }
        }

        return result;
    }

    public static boolean isEqualsMethod(@Nullable Method method) {
        return method != null && method.getParameterCount() == 1 && method.getName().equals("equals") && method.getParameterTypes()[0] == Object.class;
    }

    public static boolean isHashCodeMethod(@Nullable Method method) {
        return method != null && method.getParameterCount() == 0 && method.getName().equals("hashCode");
    }

    public static boolean isToStringMethod(@Nullable Method method) {
        return method != null && method.getParameterCount() == 0 && method.getName().equals("toString");
    }

    public static boolean isObjectMethod(@Nullable Method method) {
        return method != null && (method.getDeclaringClass() == Object.class || isEqualsMethod(method) || isHashCodeMethod(method) || isToStringMethod(method));
    }

    public static boolean isCglibRenamedMethod(Method renamedMethod) {
        String name = renamedMethod.getName();
        if (!name.startsWith("CGLIB$")) {
            return false;
        } else {
            int i;
            for(i = name.length() - 1; i >= 0 && Character.isDigit(name.charAt(i)); --i) {
            }

            return i > "CGLIB$".length() && i < name.length() - 1 && name.charAt(i) == '$';
        }
    }

    public static void makeAccessible(Method method) {
        if ((!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers())) && !method.isAccessible()) {
            method.setAccessible(true);
        }

    }

    @Nullable
    public static Field findField(Class<?> clazz, String name) {
        return findField(clazz, name, (Class)null);
    }

    @Nullable
    public static Field findField(Class<?> clazz, @Nullable String name, @Nullable Class<?> type) {
        Assert.notNull(clazz, "Class must not be null");
        Assert.isTrue(name != null || type != null, "Either name or type of the field must be specified");

        for(Class<?> searchType = clazz; Object.class != searchType && searchType != null; searchType = searchType.getSuperclass()) {
            Field[] fields = getDeclaredFields(searchType);
            Field[] var5 = fields;
            int var6 = fields.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                Field field = var5[var7];
                if ((name == null || name.equals(field.getName())) && (type == null || type.equals(field.getType()))) {
                    return field;
                }
            }
        }

        return null;
    }

    public static void setField(Field field, @Nullable Object target, @Nullable Object value) {
        try {
            field.set(target, value);
        } catch (IllegalAccessException var4) {
            handleReflectionException(var4);
        }

    }

    @Nullable
    public static Object getField(Field field, @Nullable Object target) {
        try {
            return field.get(target);
        } catch (IllegalAccessException var3) {
            handleReflectionException(var3);
            throw new IllegalStateException("Should never get here");
        }
    }

    public static void doWithLocalFields(Class<?> clazz, FieldCallback fc) {
        Field[] var2 = getDeclaredFields(clazz);
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            Field field = var2[var4];

            try {
                fc.doWith(field);
            } catch (IllegalAccessException var7) {
                String var10002 = field.getName();
                throw new IllegalStateException("Not allowed to access field '" + var10002 + "': " + var7);
            }
        }

    }

    public static void doWithFields(Class<?> clazz, FieldCallback fc) {
        doWithFields(clazz, fc, (FieldFilter)null);
    }

    public static void doWithFields(Class<?> clazz, FieldCallback fc, @Nullable FieldFilter ff) {
        Class<?> targetClass = clazz;

        do {
            Field[] fields = getDeclaredFields(targetClass);
            Field[] var5 = fields;
            int var6 = fields.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                Field field = var5[var7];
                if (ff == null || ff.matches(field)) {
                    try {
                        fc.doWith(field);
                    } catch (IllegalAccessException var10) {
                        String var10002 = field.getName();
                        throw new IllegalStateException("Not allowed to access field '" + var10002 + "': " + var10);
                    }
                }
            }

            targetClass = targetClass.getSuperclass();
        } while(targetClass != null && targetClass != Object.class);

    }

    private static Field[] getDeclaredFields(Class<?> clazz) {
        Assert.notNull(clazz, "Class must not be null");
        Field[] result = (Field[])declaredFieldsCache.get(clazz);
        if (result == null) {
            try {
                result = clazz.getDeclaredFields();
                declaredFieldsCache.put(clazz, result.length == 0 ? EMPTY_FIELD_ARRAY : result);
            } catch (Throwable var3) {
                throw new IllegalStateException("Failed to introspect Class [" + clazz.getName() + "] from ClassLoader [" + clazz.getClassLoader() + "]", var3);
            }
        }

        return result;
    }

    public static void shallowCopyFieldState(final Object src, final Object dest) {
        Assert.notNull(src, "Source for field copy cannot be null");
        Assert.notNull(dest, "Destination for field copy cannot be null");
        if (!src.getClass().isAssignableFrom(dest.getClass())) {
            String var10002 = dest.getClass().getName();
            throw new IllegalArgumentException("Destination class [" + var10002 + "] must be same or subclass as source class [" + src.getClass().getName() + "]");
        } else {
            doWithFields(src.getClass(), (field) -> {
                makeAccessible(field);
                Object srcValue = field.get(src);
                field.set(dest, srcValue);
            }, COPYABLE_FIELDS);
        }
    }

    public static boolean isPublicStaticFinal(Field field) {
        int modifiers = field.getModifiers();
        return Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers);
    }

    public static void makeAccessible(Field field) {
        if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers()) || Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {
            field.setAccessible(true);
        }

    }

    public static void clearCache() {
        declaredMethodsCache.clear();
        declaredFieldsCache.clear();
    }

    @FunctionalInterface
    public interface MethodCallback {
        void doWith(Method method) throws IllegalArgumentException, IllegalAccessException;
    }

    @FunctionalInterface
    public interface MethodFilter {
        boolean matches(Method method);

        default MethodFilter and(MethodFilter next) {
            Assert.notNull(next, "Next MethodFilter must not be null");
            return (method) -> {
                return this.matches(method) && next.matches(method);
            };
        }
    }

    @FunctionalInterface
    public interface FieldCallback {
        void doWith(Field field) throws IllegalArgumentException, IllegalAccessException;
    }

    @FunctionalInterface
    public interface FieldFilter {
        boolean matches(Field field);

        default FieldFilter and(FieldFilter next) {
            Assert.notNull(next, "Next FieldFilter must not be null");
            return (field) -> {
                return this.matches(field) && next.matches(field);
            };
        }
    }


}
