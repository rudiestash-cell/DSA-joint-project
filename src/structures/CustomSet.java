package structures;

/** Set built on the custom separate-chaining HashTable. */
public class CustomSet<T> {
    private final HashTable<T, Boolean> table;

    public CustomSet(int capacity) {
        table = new HashTable<>(capacity);
    }

    public void add(T value) {
        table.put(value, true);
    }

    public boolean contains(T value) {
        return table.get(value) != null;
    }

    public boolean remove(T value) {
        return table.remove(value) != null;
    }

    public int size() {
        return table.size();
    }
}
