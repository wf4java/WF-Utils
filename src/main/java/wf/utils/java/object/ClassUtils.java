package wf.utils.java.object;

import wf.utils.java.misc.annotation.Nullable;
import wf.utils.java.data.list.CollectionUtils;
import wf.utils.java.misc.Assert;
import wf.utils.java.object.reflect.ReflectionUtils;

import java.io.*;
import java.lang.reflect.*;
import java.lang.reflect.Proxy;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClassUtils {


    private static final Class<?>[] EMPTY_CLASS_ARRAY = new Class[0];
    private static final Map<Class<?>, Class<?>> primitiveWrapperTypeMap = new IdentityHashMap(9);
    private static final Map<Class<?>, Class<?>> primitiveTypeToWrapperMap = new IdentityHashMap(9);
    private static final Map<String, Class<?>> primitiveTypeNameMap = new HashMap(32);
    private static final Map<String, Class<?>> commonClassCache = new HashMap(64);
    private static final Set<Class<?>> javaLanguageInterfaces;





    public static List<Class<?>> scan(Object o) {
        return scan(o.getClass().getPackage().getName());
    }

    public static List<Class<?>> scan(Class<?> clazz) {
        return scan(clazz.getPackage().getName());
    }


    public static List<Class<?>> scan(String path) {
        try {
            List<Class<?>> classes = new ArrayList<>();
            String pathName = path.replace('.', '/');

            // Get the resources for the given path
            Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(pathName);
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                URI uri = resource.toURI();
                Path dirPath;

                // Handle different URI schemes
                if (uri.getScheme().equals("file")) {
                    dirPath = Paths.get(uri);
                } else {
                    throw new IllegalArgumentException("Unsupported URI scheme: " + uri.getScheme());
                }

                // Walk the file tree to find all .class files
                try(Stream<Path> stream =  Files.walk(dirPath)) {
                    stream
                            .filter(p -> p.toString().endsWith(".class"))
                            .forEach(p -> {
                                try {
                                    // Convert file path to class name
                                    String className = path + "." + dirPath.relativize(p)
                                            .toString()
                                            .replace(File.separatorChar, '.')
                                            .replace(".class", "");
                                    // Load the class and add to the list
                                    classes.add(Class.forName(className));
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                            });
                }

            }

            return classes;
        } catch (IOException | URISyntaxException e) {throw new RuntimeException(e);}
    }
    private static void registerCommonClasses(Class<?>... commonClasses) {
        Class[] var1 = commonClasses;
        int var2 = commonClasses.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            Class<?> clazz = var1[var3];
            commonClassCache.put(clazz.getName(), clazz);
        }

    }

    @Nullable
    public static ClassLoader getDefaultClassLoader() {
        ClassLoader cl = null;

        try {
            cl = Thread.currentThread().getContextClassLoader();
        } catch (Throwable var3) {
        }

        if (cl == null) {
            cl = ClassUtils.class.getClassLoader();
            if (cl == null) {
                try {
                    cl = ClassLoader.getSystemClassLoader();
                } catch (Throwable var2) {
                }
            }
        }

        return cl;
    }

    @Nullable
    public static ClassLoader overrideThreadContextClassLoader(@Nullable ClassLoader classLoaderToUse) {
        Thread currentThread = Thread.currentThread();
        ClassLoader threadContextClassLoader = currentThread.getContextClassLoader();
        if (classLoaderToUse != null && !classLoaderToUse.equals(threadContextClassLoader)) {
            currentThread.setContextClassLoader(classLoaderToUse);
            return threadContextClassLoader;
        } else {
            return null;
        }
    }

    public static Class<?> forName(String name, @Nullable ClassLoader classLoader) throws ClassNotFoundException, LinkageError {
        Assert.notNull(name, "Name must not be null");
        Class<?> clazz = resolvePrimitiveClassName(name);
        if (clazz == null) {
            clazz = (Class)commonClassCache.get(name);
        }

        if (clazz != null) {
            return clazz;
        } else {
            Class elementClass;
            String elementName;
            if (name.endsWith("[]")) {
                elementName = name.substring(0, name.length() - "[]".length());
                elementClass = forName(elementName, classLoader);
                return Array.newInstance(elementClass, 0).getClass();
            } else if (name.startsWith("[L") && name.endsWith(";")) {
                elementName = name.substring("[L".length(), name.length() - 1);
                elementClass = forName(elementName, classLoader);
                return Array.newInstance(elementClass, 0).getClass();
            } else if (name.startsWith("[")) {
                elementName = name.substring("[".length());
                elementClass = forName(elementName, classLoader);
                return Array.newInstance(elementClass, 0).getClass();
            } else {
                ClassLoader clToUse = classLoader;
                if (classLoader == null) {
                    clToUse = getDefaultClassLoader();
                }

                try {
                    return Class.forName(name, false, clToUse);
                } catch (ClassNotFoundException var9) {
                    int lastDotIndex = name.lastIndexOf(46);
                    if (lastDotIndex != -1) {
                        String var10000 = name.substring(0, lastDotIndex);
                        String nestedClassName = var10000 + "$" + name.substring(lastDotIndex + 1);

                        try {
                            return Class.forName(nestedClassName, false, clToUse);
                        } catch (ClassNotFoundException var8) {
                        }
                    }

                    throw var9;
                }
            }
        }
    }

    public static Class<?> resolveClassName(String className, @Nullable ClassLoader classLoader) throws IllegalArgumentException {
        try {
            return forName(className, classLoader);
        } catch (IllegalAccessError var3) {
            throw new IllegalStateException("Readability mismatch in inheritance hierarchy of class [" + className + "]: " + var3.getMessage(), var3);
        } catch (LinkageError var4) {
            throw new IllegalArgumentException("Unresolvable class definition for class [" + className + "]", var4);
        } catch (ClassNotFoundException var5) {
            throw new IllegalArgumentException("Could not find class [" + className + "]", var5);
        }
    }

    public static boolean isPresent(String className, @Nullable ClassLoader classLoader) {
        try {
            forName(className, classLoader);
            return true;
        } catch (IllegalAccessError var3) {
            throw new IllegalStateException("Readability mismatch in inheritance hierarchy of class [" + className + "]: " + var3.getMessage(), var3);
        } catch (Throwable var4) {
            return false;
        }
    }

    public static boolean isVisible(Class<?> clazz, @Nullable ClassLoader classLoader) {
        if (classLoader == null) {
            return true;
        } else {
            try {
                if (clazz.getClassLoader() == classLoader) {
                    return true;
                }
            } catch (SecurityException var3) {
            }

            return isLoadable(clazz, classLoader);
        }
    }

    public static boolean isCacheSafe(Class<?> clazz, @Nullable ClassLoader classLoader) {
        Assert.notNull(clazz, "Class must not be null");

        try {
            ClassLoader target = clazz.getClassLoader();
            if (target == classLoader || target == null) {
                return true;
            }

            if (classLoader == null) {
                return false;
            }

            ClassLoader current = classLoader;

            while(current != null) {
                current = current.getParent();
                if (current == target) {
                    return true;
                }
            }

            while(target != null) {
                target = target.getParent();
                if (target == classLoader) {
                    return false;
                }
            }
        } catch (SecurityException var4) {
        }

        return classLoader != null && isLoadable(clazz, classLoader);
    }

    private static boolean isLoadable(Class<?> clazz, ClassLoader classLoader) {
        try {
            return clazz == classLoader.loadClass(clazz.getName());
        } catch (ClassNotFoundException var3) {
            return false;
        }
    }

    @Nullable
    public static Class<?> resolvePrimitiveClassName(@Nullable String name) {
        Class<?> result = null;
        if (name != null && name.length() <= 7) {
            result = (Class)primitiveTypeNameMap.get(name);
        }

        return result;
    }

    public static boolean isPrimitiveWrapper(Class<?> clazz) {
        Assert.notNull(clazz, "Class must not be null");
        return primitiveWrapperTypeMap.containsKey(clazz);
    }

    public static boolean isPrimitiveOrWrapper(Class<?> clazz) {
        Assert.notNull(clazz, "Class must not be null");
        return clazz.isPrimitive() || isPrimitiveWrapper(clazz);
    }

    public static boolean isPrimitiveArray(Class<?> clazz) {
        Assert.notNull(clazz, "Class must not be null");
        return clazz.isArray() && clazz.getComponentType().isPrimitive();
    }

    public static boolean isPrimitiveWrapperArray(Class<?> clazz) {
        Assert.notNull(clazz, "Class must not be null");
        return clazz.isArray() && isPrimitiveWrapper(clazz.getComponentType());
    }

    public static Class<?> resolvePrimitiveIfNecessary(Class<?> clazz) {
        Assert.notNull(clazz, "Class must not be null");
        return clazz.isPrimitive() && clazz != Void.TYPE ? (Class)primitiveTypeToWrapperMap.get(clazz) : clazz;
    }

    public static boolean isAssignable(Class<?> lhsType, Class<?> rhsType) {
        Assert.notNull(lhsType, "Left-hand side type must not be null");
        Assert.notNull(rhsType, "Right-hand side type must not be null");
        if (lhsType.isAssignableFrom(rhsType)) {
            return true;
        } else {
            Class resolvedWrapper;
            if (lhsType.isPrimitive()) {
                resolvedWrapper = (Class)primitiveWrapperTypeMap.get(rhsType);
                return lhsType == resolvedWrapper;
            } else {
                resolvedWrapper = (Class)primitiveTypeToWrapperMap.get(rhsType);
                return resolvedWrapper != null && lhsType.isAssignableFrom(resolvedWrapper);
            }
        }
    }

    public static boolean isAssignableValue(Class<?> type, @Nullable Object value) {
        Assert.notNull(type, "Type must not be null");
        return value != null ? isAssignable(type, value.getClass()) : !type.isPrimitive();
    }

    public static String convertResourcePathToClassName(String resourcePath) {
        Assert.notNull(resourcePath, "Resource path must not be null");
        return resourcePath.replace('/', '.');
    }

    public static String convertClassNameToResourcePath(String className) {
        Assert.notNull(className, "Class name must not be null");
        return className.replace('.', '/');
    }

    public static String addResourcePathToPackagePath(Class<?> clazz, String resourceName) {
        Assert.notNull(resourceName, "Resource name must not be null");
        String var10000;
        if (!resourceName.startsWith("/")) {
            var10000 = classPackageAsResourcePath(clazz);
            return var10000 + "/" + resourceName;
        } else {
            var10000 = classPackageAsResourcePath(clazz);
            return var10000 + resourceName;
        }
    }

    public static String classPackageAsResourcePath(@Nullable Class<?> clazz) {
        if (clazz == null) {
            return "";
        } else {
            String className = clazz.getName();
            int packageEndIndex = className.lastIndexOf(46);
            if (packageEndIndex == -1) {
                return "";
            } else {
                String packageName = className.substring(0, packageEndIndex);
                return packageName.replace('.', '/');
            }
        }
    }

    public static String classNamesToString(Class<?>... classes) {
        return classNamesToString((Collection)Arrays.asList(classes));
    }

    public static String classNamesToString(@Nullable Collection<Class<?>> classes) {
        if (CollectionUtils.isEmpty(classes)) {
            return "[]";
        } else {
            StringJoiner stringJoiner = new StringJoiner(", ", "[", "]");
            Iterator var2 = classes.iterator();

            while(var2.hasNext()) {
                Class<?> clazz = (Class)var2.next();
                stringJoiner.add(clazz.getName());
            }

            return stringJoiner.toString();
        }
    }

    public static Class<?>[] toClassArray(@Nullable Collection<Class<?>> collection) {
        return !CollectionUtils.isEmpty(collection) ? (Class[])collection.toArray(EMPTY_CLASS_ARRAY) : EMPTY_CLASS_ARRAY;
    }

    public static Class<?>[] getAllInterfaces(Object instance) {
        Assert.notNull(instance, "Instance must not be null");
        return getAllInterfacesForClass(instance.getClass());
    }

    public static Class<?>[] getAllInterfacesForClass(Class<?> clazz) {
        return getAllInterfacesForClass(clazz, (ClassLoader)null);
    }

    public static Class<?>[] getAllInterfacesForClass(Class<?> clazz, @Nullable ClassLoader classLoader) {
        return toClassArray(getAllInterfacesForClassAsSet(clazz, classLoader));
    }

    public static Set<Class<?>> getAllInterfacesAsSet(Object instance) {
        Assert.notNull(instance, "Instance must not be null");
        return getAllInterfacesForClassAsSet(instance.getClass());
    }

    public static Set<Class<?>> getAllInterfacesForClassAsSet(Class<?> clazz) {
        return getAllInterfacesForClassAsSet(clazz, (ClassLoader)null);
    }

    public static Set<Class<?>> getAllInterfacesForClassAsSet(Class<?> clazz, @Nullable ClassLoader classLoader) {
        Assert.notNull(clazz, "Class must not be null");
        if (clazz.isInterface() && isVisible(clazz, classLoader)) {
            return Collections.singleton(clazz);
        } else {
            Set<Class<?>> interfaces = new LinkedHashSet();

            for(Class<?> current = clazz; current != null; current = current.getSuperclass()) {
                Class<?>[] ifcs = current.getInterfaces();
                Class[] var5 = ifcs;
                int var6 = ifcs.length;

                for(int var7 = 0; var7 < var6; ++var7) {
                    Class<?> ifc = var5[var7];
                    if (isVisible(ifc, classLoader)) {
                        interfaces.add(ifc);
                    }
                }
            }

            return interfaces;
        }
    }

    public static Class<?> createCompositeInterface(Class<?>[] interfaces, @Nullable ClassLoader classLoader) {
        Assert.notEmpty(interfaces, "Interface array must not be empty");
        return Proxy.getProxyClass(classLoader, interfaces);
    }

    @Nullable
    public static Class<?> determineCommonAncestor(@Nullable Class<?> clazz1, @Nullable Class<?> clazz2) {
        if (clazz1 == null) {
            return clazz2;
        } else if (clazz2 == null) {
            return clazz1;
        } else if (clazz1.isAssignableFrom(clazz2)) {
            return clazz1;
        } else if (clazz2.isAssignableFrom(clazz1)) {
            return clazz2;
        } else {
            Class<?> ancestor = clazz1;

            do {
                ancestor = ancestor.getSuperclass();
                if (ancestor == null || Object.class == ancestor) {
                    return null;
                }
            } while(!ancestor.isAssignableFrom(clazz2));

            return ancestor;
        }
    }

    public static boolean isJavaLanguageInterface(Class<?> ifc) {
        return javaLanguageInterfaces.contains(ifc);
    }

    public static boolean isStaticClass(Class<?> clazz) {
        return Modifier.isStatic(clazz.getModifiers());
    }

    public static boolean isInnerClass(Class<?> clazz) {
        return clazz.isMemberClass() && !isStaticClass(clazz);
    }

    public static boolean isLambdaClass(Class<?> clazz) {
        return clazz.isSynthetic() && clazz.getSuperclass() == Object.class && clazz.getInterfaces().length > 0 && clazz.getName().contains("$$Lambda");
    }

    /** @deprecated */
    @Deprecated
    public static boolean isCglibProxy(Object object) {
        return isCglibProxyClass(object.getClass());
    }

    /** @deprecated */
    @Deprecated
    public static boolean isCglibProxyClass(@Nullable Class<?> clazz) {
        return clazz != null && isCglibProxyClassName(clazz.getName());
    }

    /** @deprecated */
    @Deprecated
    public static boolean isCglibProxyClassName(@Nullable String className) {
        return className != null && className.contains("$$");
    }

    public static Class<?> getUserClass(Object instance) {
        Assert.notNull(instance, "Instance must not be null");
        return getUserClass(instance.getClass());
    }

    public static Class<?> getUserClass(Class<?> clazz) {
        if (clazz.getName().contains("$$")) {
            Class<?> superclass = clazz.getSuperclass();
            if (superclass != null && superclass != Object.class) {
                return superclass;
            }
        }

        return clazz;
    }

    @Nullable
    public static String getDescriptiveType(@Nullable Object value) {
        if (value == null) {
            return null;
        } else {
            Class<?> clazz = value.getClass();
            if (!Proxy.isProxyClass(clazz)) {
                return clazz.getTypeName();
            } else {
                String prefix = clazz.getName() + " implementing ";
                StringJoiner result = new StringJoiner(",", prefix, "");
                Class[] var4 = clazz.getInterfaces();
                int var5 = var4.length;

                for(int var6 = 0; var6 < var5; ++var6) {
                    Class<?> ifc = var4[var6];
                    result.add(ifc.getName());
                }

                return result.toString();
            }
        }
    }

    public static boolean matchesTypeName(Class<?> clazz, @Nullable String typeName) {
        return typeName != null && (typeName.equals(clazz.getTypeName()) || typeName.equals(clazz.getSimpleName()));
    }

    public static String getShortName(String className) {
        Assert.hasLength(className, "Class name must not be empty");
        int lastDotIndex = className.lastIndexOf(46);
        int nameEndIndex = className.indexOf("$$");
        if (nameEndIndex == -1) {
            nameEndIndex = className.length();
        }

        String shortName = className.substring(lastDotIndex + 1, nameEndIndex);
        shortName = shortName.replace('$', '.');
        return shortName;
    }

    public static String getShortName(Class<?> clazz) {
        return getShortName(getQualifiedName(clazz));
    }


    public static String getClassFileName(Class<?> clazz) {
        Assert.notNull(clazz, "Class must not be null");
        String className = clazz.getName();
        int lastDotIndex = className.lastIndexOf(46);
        String var10000 = className.substring(lastDotIndex + 1);
        return var10000 + ".class";
    }

    public static String getPackageName(Class<?> clazz) {
        Assert.notNull(clazz, "Class must not be null");
        return getPackageName(clazz.getName());
    }

    public static String getPackageName(String fqClassName) {
        Assert.notNull(fqClassName, "Class name must not be null");
        int lastDotIndex = fqClassName.lastIndexOf(46);
        return lastDotIndex != -1 ? fqClassName.substring(0, lastDotIndex) : "";
    }

    public static String getQualifiedName(Class<?> clazz) {
        Assert.notNull(clazz, "Class must not be null");
        return clazz.getTypeName();
    }

    public static String getQualifiedMethodName(Method method) {
        return getQualifiedMethodName(method, (Class)null);
    }

    public static String getQualifiedMethodName(Method method, @Nullable Class<?> clazz) {
        Assert.notNull(method, "Method must not be null");
        String var10000 = (clazz != null ? clazz : method.getDeclaringClass()).getName();
        return var10000 + "." + method.getName();
    }

    public static boolean hasConstructor(Class<?> clazz, Class<?>... paramTypes) {
        return getConstructorIfAvailable(clazz, paramTypes) != null;
    }

    @Nullable
    public static <T> Constructor<T> getConstructorIfAvailable(Class<T> clazz, Class<?>... paramTypes) {
        Assert.notNull(clazz, "Class must not be null");

        try {
            return clazz.getConstructor(paramTypes);
        } catch (NoSuchMethodException var3) {
            return null;
        }
    }

    public static boolean hasMethod(Class<?> clazz, Method method) {
        Assert.notNull(clazz, "Class must not be null");
        Assert.notNull(method, "Method must not be null");
        if (clazz == method.getDeclaringClass()) {
            return true;
        } else {
            String methodName = method.getName();
            Class<?>[] paramTypes = method.getParameterTypes();
            return getMethodOrNull(clazz, methodName, paramTypes) != null;
        }
    }

    public static boolean hasMethod(Class<?> clazz, String methodName, Class<?>... paramTypes) {
        return getMethodIfAvailable(clazz, methodName, paramTypes) != null;
    }

    public static Method getMethod(Class<?> clazz, String methodName, @Nullable Class<?>... paramTypes) {
        Assert.notNull(clazz, "Class must not be null");
        Assert.notNull(methodName, "Method name must not be null");
        if (paramTypes != null) {
            try {
                return clazz.getMethod(methodName, paramTypes);
            } catch (NoSuchMethodException var4) {
                throw new IllegalStateException("Expected method not found: " + var4);
            }
        } else {
            Set<Method> candidates = findMethodCandidatesByName(clazz, methodName);
            if (candidates.size() == 1) {
                return (Method)candidates.iterator().next();
            } else {
                String var10002;
                if (candidates.isEmpty()) {
                    var10002 = clazz.getName();
                    throw new IllegalStateException("Expected method not found: " + var10002 + "." + methodName);
                } else {
                    var10002 = clazz.getName();
                    throw new IllegalStateException("No unique method found: " + var10002 + "." + methodName);
                }
            }
        }
    }

    @Nullable
    public static Method getMethodIfAvailable(Class<?> clazz, String methodName, @Nullable Class<?>... paramTypes) {
        Assert.notNull(clazz, "Class must not be null");
        Assert.notNull(methodName, "Method name must not be null");
        if (paramTypes != null) {
            return getMethodOrNull(clazz, methodName, paramTypes);
        } else {
            Set<Method> candidates = findMethodCandidatesByName(clazz, methodName);
            return candidates.size() == 1 ? (Method)candidates.iterator().next() : null;
        }
    }

    public static int getMethodCountForName(Class<?> clazz, String methodName) {
        Assert.notNull(clazz, "Class must not be null");
        Assert.notNull(methodName, "Method name must not be null");
        int count = 0;
        Method[] declaredMethods = clazz.getDeclaredMethods();
        Method[] var4 = declaredMethods;
        int var5 = declaredMethods.length;

        int var6;
        for(var6 = 0; var6 < var5; ++var6) {
            Method method = var4[var6];
            if (methodName.equals(method.getName())) {
                ++count;
            }
        }

        Class<?>[] ifcs = clazz.getInterfaces();
        Class[] var10 = ifcs;
        var6 = ifcs.length;

        for(int var11 = 0; var11 < var6; ++var11) {
            Class<?> ifc = var10[var11];
            count += getMethodCountForName(ifc, methodName);
        }

        if (clazz.getSuperclass() != null) {
            count += getMethodCountForName(clazz.getSuperclass(), methodName);
        }

        return count;
    }

    public static boolean hasAtLeastOneMethodWithName(Class<?> clazz, String methodName) {
        Assert.notNull(clazz, "Class must not be null");
        Assert.notNull(methodName, "Method name must not be null");
        Method[] declaredMethods = clazz.getDeclaredMethods();
        Method[] var3 = declaredMethods;
        int var4 = declaredMethods.length;

        int var5;
        for(var5 = 0; var5 < var4; ++var5) {
            Method method = var3[var5];
            if (method.getName().equals(methodName)) {
                return true;
            }
        }

        Class<?>[] ifcs = clazz.getInterfaces();
        Class[] var9 = ifcs;
        var5 = ifcs.length;

        for(int var10 = 0; var10 < var5; ++var10) {
            Class<?> ifc = var9[var10];
            if (hasAtLeastOneMethodWithName(ifc, methodName)) {
                return true;
            }
        }

        return clazz.getSuperclass() != null && hasAtLeastOneMethodWithName(clazz.getSuperclass(), methodName);
    }

    public static Method getMostSpecificMethod(Method method, @Nullable Class<?> targetClass) {
        if (targetClass != null && targetClass != method.getDeclaringClass() && isOverridable(method, targetClass)) {
            try {
                if (Modifier.isPublic(method.getModifiers())) {
                    try {
                        return targetClass.getMethod(method.getName(), method.getParameterTypes());
                    } catch (NoSuchMethodException var3) {
                        return method;
                    }
                }

                Method specificMethod = ReflectionUtils.findMethod(targetClass, method.getName(), method.getParameterTypes());
                return specificMethod != null ? specificMethod : method;
            } catch (SecurityException var4) {
            }
        }

        return method;
    }



    private static Method findInterfaceMethodIfPossible(Method method, Class<?> startClass, Class<?> endClass) {
        for(Class<?> current = startClass; current != null && current != endClass; current = current.getSuperclass()) {
            Class<?>[] ifcs = current.getInterfaces();
            Class[] var5 = ifcs;
            int var6 = ifcs.length;
            int var7 = 0;

            while(var7 < var6) {
                Class<?> ifc = var5[var7];

                try {
                    return ifc.getMethod(method.getName(), method.getParameterTypes());
                } catch (NoSuchMethodException var10) {
                    ++var7;
                }
            }
        }

        return method;
    }

    public static boolean isUserLevelMethod(Method method) {
        Assert.notNull(method, "Method must not be null");
        return method.isBridge() || !method.isSynthetic() && !isGroovyObjectMethod(method);
    }

    private static boolean isGroovyObjectMethod(Method method) {
        return method.getDeclaringClass().getName().equals("groovy.lang.GroovyObject");
    }

    private static boolean isOverridable(Method method, @Nullable Class<?> targetClass) {
        if ((method.getModifiers() & 26) != 0) {
            return false;
        } else if ((method.getModifiers() & 5) != 0) {
            return true;
        } else {
            return targetClass == null || getPackageName(method.getDeclaringClass()).equals(getPackageName(targetClass));
        }
    }

    @Nullable
    public static Method getStaticMethod(Class<?> clazz, String methodName, Class<?>... args) {
        Assert.notNull(clazz, "Class must not be null");
        Assert.notNull(methodName, "Method name must not be null");

        try {
            Method method = clazz.getMethod(methodName, args);
            return Modifier.isStatic(method.getModifiers()) ? method : null;
        } catch (NoSuchMethodException var4) {
            return null;
        }
    }

    @Nullable
    private static Method getMethodOrNull(Class<?> clazz, String methodName, Class<?>[] paramTypes) {
        try {
            return clazz.getMethod(methodName, paramTypes);
        } catch (NoSuchMethodException var4) {
            return null;
        }
    }

    private static Set<Method> findMethodCandidatesByName(Class<?> clazz, String methodName) {
        Set<Method> candidates = new HashSet(1);
        Method[] methods = clazz.getMethods();
        Method[] var4 = methods;
        int var5 = methods.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Method method = var4[var6];
            if (methodName.equals(method.getName())) {
                candidates.add(method);
            }
        }

        return candidates;
    }

    static {
        primitiveWrapperTypeMap.put(Boolean.class, Boolean.TYPE);
        primitiveWrapperTypeMap.put(Byte.class, Byte.TYPE);
        primitiveWrapperTypeMap.put(Character.class, Character.TYPE);
        primitiveWrapperTypeMap.put(Double.class, Double.TYPE);
        primitiveWrapperTypeMap.put(Float.class, Float.TYPE);
        primitiveWrapperTypeMap.put(Integer.class, Integer.TYPE);
        primitiveWrapperTypeMap.put(Long.class, Long.TYPE);
        primitiveWrapperTypeMap.put(Short.class, Short.TYPE);
        primitiveWrapperTypeMap.put(Void.class, Void.TYPE);
        Iterator var0 = primitiveWrapperTypeMap.entrySet().iterator();

        while(var0.hasNext()) {
            Map.Entry<Class<?>, Class<?>> entry = (Map.Entry)var0.next();
            primitiveTypeToWrapperMap.put((Class)entry.getValue(), (Class)entry.getKey());
            registerCommonClasses((Class)entry.getKey());
        }

        Set<Class<?>> primitiveTypes = new HashSet(32);
        primitiveTypes.addAll(primitiveWrapperTypeMap.values());
        Collections.addAll(primitiveTypes, boolean[].class, byte[].class, char[].class, double[].class, float[].class, int[].class, long[].class, short[].class);
        Iterator var4 = primitiveTypes.iterator();

        while(var4.hasNext()) {
            Class<?> primitiveType = (Class)var4.next();
            primitiveTypeNameMap.put(primitiveType.getName(), primitiveType);
        }

        registerCommonClasses(Boolean[].class, Byte[].class, Character[].class, Double[].class, Float[].class, Integer[].class, Long[].class, Short[].class);
        registerCommonClasses(Number.class, Number[].class, String.class, String[].class, Class.class, Class[].class, Object.class, Object[].class);
        registerCommonClasses(Throwable.class, Exception.class, RuntimeException.class, Error.class, StackTraceElement.class, StackTraceElement[].class);
        registerCommonClasses(Enum.class, Iterable.class, Iterator.class, Enumeration.class, Collection.class, List.class, Set.class, Map.class, Map.Entry.class, Optional.class);
        Class<?>[] javaLanguageInterfaceArray = new Class[]{Serializable.class, Externalizable.class, Closeable.class, AutoCloseable.class, Cloneable.class, Comparable.class};
        registerCommonClasses(javaLanguageInterfaceArray);
        javaLanguageInterfaces = Arrays.stream(javaLanguageInterfaceArray).collect(Collectors.toSet());
    }

}
