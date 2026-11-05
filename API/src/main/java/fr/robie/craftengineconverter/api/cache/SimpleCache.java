package fr.robie.craftengineconverter.api.cache;

import java.util.HashMap;
import java.util.Map;

public class SimpleCache<K, V> {
    private final Map<K, V> cache = new HashMap<>();

    public V get(K key) {
        return this.cache.get(key);
    }

    public V getOrDefault(K key, Loader<V> loader) {
        return this.cache.computeIfAbsent(key, (k) -> loader.load());
    }

    public void put(K key, V value) {
        this.cache.put(key, value);
    }

    public boolean containsKey(K key) {
        return this.cache.containsKey(key);
    }

    public interface Loader<V> {
        V load();
    }

    public void clear() {
        this.cache.clear();
    }
}