package wf.utils.java.data.map;



import wf.utils.java.data.set.ArraySet;

import java.util.*;
import java.util.stream.Collectors;


public class ArrayMap<K, V> implements Map<K, V> {
    private final List<Map.Entry<K, V>> entries;



    public ArrayMap() {
        this.entries = new ArrayList<>();
    }


    public ArrayMap(int size) {
        this.entries = new ArrayList<>(size);
    }


    @Override
    public V get(Object key) {
        for (Map.Entry<K, V> entry : entries) {
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        for (Map.Entry<K, V> entry : entries) {
            if (entry.getKey().equals(key)) {
                V oldValue = entry.getValue();
                entry.setValue(value);
                return oldValue;
            }
        }
        entries.add(new Entry<>(key, value));
        return null;
    }

    @Override
    public V remove(Object key) {
        for (int i = 0; i < entries.size(); i++) {
            Map.Entry<K, V> entry = entries.get(i);
            if (entry.getKey().equals(key)) {
                entries.remove(i);
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void putAll(Map<? extends K, ? extends V> m) {
        entries.addAll((Collection<? extends Map.Entry<K, V>>) (Object) m.entrySet());
    }

    @Override
    public void clear() {
        entries.clear();
    }


    @Override
    public Set<K> keySet() {
        return new ArraySet<>(entries.stream().map(Map.Entry::getKey).collect(Collectors.toList()));
    }


    @Override
    public Collection<V> values() {
        return entries.stream().map(Map.Entry::getValue).collect(Collectors.toList());
    }


    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return new ArraySet<>(entries);
    }

    @Override
    public int size() {
        return entries.size();
    }

    @Override
    public boolean isEmpty() {
        return entries.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        for (Map.Entry<K, V> entry : entries)
            if (entry.getKey().equals(key))
                return true;

        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        for (Map.Entry<K, V> entry : entries)
            if (entry.getValue().equals(value))
                return true;

        return false;
    }


    public static class Entry<K, V> implements Map.Entry<K, V> {
        private final K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }
    }
}