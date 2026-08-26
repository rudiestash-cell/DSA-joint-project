package structures;

/** Map facade built on the custom HashTable. */
public class CustomMap<K, V> {
    private final HashTable<K, V> table;

    public CustomMap(int capacity) {
        table = new HashTable<>(capacity);
    }

    public void put(K key, V value) {
        table.put(key, value);
    }

    public V get(K key) {
        return table.get(key);
    }

    public V remove(K key) {
        return table.remove(key);
    }

    public int size() {
        return table.size();
    }
}
