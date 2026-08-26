package structures;

/** Small B-tree-page simulation: sorted page insertion, lookup and explicit split for report/demo traces. */
public class BTreePageIndex {
    private final String[] keys;
    private int size;

    public BTreePageIndex(int pageCapacity) {
        if (pageCapacity < 2) {
            throw new IllegalArgumentException("capacity >= 2");
        }
        keys = new String[pageCapacity];
    }

    public boolean add(String key) {
        if (size == keys.length) {
            return false;
        }
        int index = size;
        while (index > 0 && keys[index - 1].compareTo(key) > 0) {
            keys[index] = keys[index - 1];
            index--;
        }
        if (index > 0 && keys[index - 1].equals(key)) {
            return true;
        }
        keys[index] = key;
        size++;
        return true;
    }

    public boolean contains(String key) {
        int low = 0;
        int high = size - 1;
        while (low <= high) {
            int middle = (low + high) / 2;
            int comparison = keys[middle].compareTo(key);
            if (comparison == 0) {
                return true;
            }
            if (comparison < 0) {
                low = middle + 1;
            } else {
                high = middle - 1;
            }
        }
        return false;
    }

    public String splitTrace() {
        if (size < 2) {
            return "Page has too few keys to split";
        }
        int middle = size / 2;
        return "split page at separator " + keys[middle] + ": left=" + middle
                + " keys, right=" + (size - middle - 1) + " keys";
    }

    public int size() {
        return size;
    }
}
