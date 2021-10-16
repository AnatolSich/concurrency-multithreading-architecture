package com.training.task1;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unused")
public class SynchronizedThreadSafeMap<K, V> implements Map<K, V> {

    private final Set<Map.Entry<K, V>> entrySet = new HashSet<>();

    @Override
    public synchronized int size() {
        return entrySet.size();
    }

    @Override
    public synchronized boolean isEmpty() {
        return entrySet.isEmpty();
    }

    @Override
    public synchronized boolean containsKey(Object key) {
        return false;
    }

    @Override
    public synchronized boolean containsValue(Object value) {
        return false;
    }

    @Override
    public synchronized V get(Object key) {
        for (Map.Entry<K, V> entry : entrySet) {
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public synchronized V put(K key, V value) {
        entrySet.add(new Entry<>(key, value));
        return value;
    }

    @Override
    public synchronized V remove(Object key) {
        return null;
    }

    @Override
    public synchronized void putAll(Map<? extends K, ? extends V> m) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized Set<K> keySet() {
        return Set.of();
    }

    @Override
    public synchronized Collection<V> values() {
        Set<V> values = new HashSet<>();
        for (Map.Entry<K, V> entry : entrySet) {
            values.add(entry.getValue());
        }
        return values;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return entrySet;
    }


    public static class Entry<K, V> implements Map.Entry<K, V> {

        private K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public V setValue(V value) {
            this.value = value;
            return value;
        }
    }
}
