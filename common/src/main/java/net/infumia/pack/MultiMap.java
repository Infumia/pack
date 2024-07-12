package net.infumia.pack;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

final class MultiMap<K, V> {

    private final Map<K, Collection<V>> map = new HashMap<>();

    MultiMap() {}

    void put(final K key, final V value) {
        this.map.computeIfAbsent(key, __ -> new HashSet<>()).add(value);
    }

    Collection<V> get(final K key) {
        return this.map.get(key);
    }

    boolean remove(final K key, final V value) {
        final Collection<V> values = this.map.get(key);
        if (values == null) {
            return false;
        }
        final boolean removed = values.remove(value);
        if (values.isEmpty()) {
            this.map.remove(key);
        }
        return removed;
    }

    void clear() {
        this.map.clear();
    }

    Collection<K> keys() {
        return this.map.keySet();
    }
}
