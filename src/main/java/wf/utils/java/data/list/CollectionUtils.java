package wf.utils.java.data.list;



import wf.utils.java.misc.annotation.Nullable;
import wf.utils.java.object.ObjectUtils;

import java.util.*;

public class CollectionUtils {

    static final float DEFAULT_LOAD_FACTOR = 0.75F;

    public CollectionUtils() {
    }

    public static boolean isEmpty(@Nullable Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(@Nullable Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static <K, V> HashMap<K, V> newHashMap(int expectedSize) {
        return new HashMap(computeMapInitialCapacity(expectedSize), 0.75F);
    }

    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(int expectedSize) {
        return new LinkedHashMap(computeMapInitialCapacity(expectedSize), 0.75F);
    }

    private static int computeMapInitialCapacity(int expectedSize) {
        return (int)Math.ceil((double)expectedSize / 0.75);
    }

    public static List<?> arrayToList(@Nullable Object source) {
        return Arrays.asList(ObjectUtils.toObjectArray(source));
    }

    public static <E> void mergeArrayIntoCollection(@Nullable Object array, Collection<E> collection) {
        E[] arr = (E[]) ObjectUtils.toObjectArray(array);
        Collections.addAll(collection, arr);
    }

    public static <K, V> void mergePropertiesIntoMap(@Nullable Properties props, Map<K, V> map) {
        String key;
        Object value;
        if (props != null) {
            for(Enumeration<?> en = props.propertyNames(); en.hasMoreElements(); map.put((K) key, (V) value)) {
                key = (String)en.nextElement();
                value = props.get(key);
                if (value == null) {
                    value = props.getProperty(key);
                }
            }
        }

    }

    public static boolean contains(@Nullable Iterator<?> iterator, Object element) {
        if (iterator != null) {
            while(iterator.hasNext()) {
                Object candidate = iterator.next();
                if (ObjectUtils.nullSafeEquals(candidate, element)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean contains(@Nullable Enumeration<?> enumeration, Object element) {
        if (enumeration != null) {
            while(enumeration.hasMoreElements()) {
                Object candidate = enumeration.nextElement();
                if (ObjectUtils.nullSafeEquals(candidate, element)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean containsInstance(@Nullable Collection<?> collection, Object element) {
        if (collection != null) {
            Iterator var2 = collection.iterator();

            while(var2.hasNext()) {
                Object candidate = var2.next();
                if (candidate == element) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean containsAny(Collection<?> source, Collection<?> candidates) {
        return findFirstMatch(source, candidates) != null;
    }

    @Nullable
    public static <E> E findFirstMatch(Collection<?> source, Collection<E> candidates) {
        if (!isEmpty(source) && !isEmpty(candidates)) {
            Iterator var2 = candidates.iterator();

            Object candidate;
            do {
                if (!var2.hasNext()) {
                    return null;
                }

                candidate = var2.next();
            } while(!source.contains(candidate));

            return (E) candidate;
        } else {
            return null;
        }
    }

    @Nullable
    public static <T> T findValueOfType(Collection<?> collection, @Nullable Class<T> type) {
        if (isEmpty(collection)) {
            return null;
        } else {
            T value = null;
            Iterator var3 = collection.iterator();

            while(true) {
                Object element;
                do {
                    if (!var3.hasNext()) {
                        return value;
                    }

                    element = var3.next();
                } while(type != null && !type.isInstance(element));

                if (value != null) {
                    return null;
                }

                value = (T) element;
            }
        }
    }

    @Nullable
    public static Object findValueOfType(Collection<?> collection, Class<?>[] types) {
        if (!isEmpty(collection) && !ObjectUtils.isEmpty(types)) {
            Class[] var2 = types;
            int var3 = types.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                Class<?> type = var2[var4];
                Object value = findValueOfType(collection, type);
                if (value != null) {
                    return value;
                }
            }

            return null;
        } else {
            return null;
        }
    }

    public static boolean hasUniqueObject(Collection<?> collection) {
        if (isEmpty(collection)) {
            return false;
        } else {
            boolean hasCandidate = false;
            Object candidate = null;
            Iterator var3 = collection.iterator();

            while(var3.hasNext()) {
                Object elem = var3.next();
                if (!hasCandidate) {
                    hasCandidate = true;
                    candidate = elem;
                } else if (candidate != elem) {
                    return false;
                }
            }

            return true;
        }
    }

    @Nullable
    public static Class<?> findCommonElementType(Collection<?> collection) {
        if (isEmpty(collection)) {
            return null;
        } else {
            Class<?> candidate = null;
            Iterator var2 = collection.iterator();

            while(var2.hasNext()) {
                Object val = var2.next();
                if (val != null) {
                    if (candidate == null) {
                        candidate = val.getClass();
                    } else if (candidate != val.getClass()) {
                        return null;
                    }
                }
            }

            return candidate;
        }
    }

    @Nullable
    public static <T> T firstElement(@Nullable Set<T> set) {
        if (isEmpty((Collection)set)) {
            return null;
        } else if (set instanceof SortedSet) {
            SortedSet<T> sortedSet = (SortedSet)set;
            return sortedSet.first();
        } else {
            Iterator<T> it = set.iterator();
            T first = null;
            if (it.hasNext()) {
                first = it.next();
            }

            return first;
        }
    }

    @Nullable
    public static <T> T firstElement(@Nullable List<T> list) {
        return isEmpty((Collection)list) ? null : list.get(0);
    }

    @Nullable
    public static <T> T lastElement(@Nullable Set<T> set) {
        if (isEmpty((Collection)set)) {
            return null;
        } else if (set instanceof SortedSet) {
            SortedSet<T> sortedSet = (SortedSet)set;
            return sortedSet.last();
        } else {
            Iterator<T> it = set.iterator();

            Object last;
            for(last = null; it.hasNext(); last = it.next()) {

            }

            return (T) last;
        }
    }

    @Nullable
    public static <T> T lastElement(@Nullable List<T> list) {
        return isEmpty((Collection)list) ? null : list.get(list.size() - 1);
    }

    public static <A, E extends A> A[] toArray(Enumeration<E> enumeration, A[] array) {
        ArrayList<A> elements = new ArrayList();

        while(enumeration.hasMoreElements()) {
            elements.add(enumeration.nextElement());
        }

        return elements.toArray(array);
    }






}
