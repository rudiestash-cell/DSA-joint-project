package structures;

import java.util.Comparator;

/** Array-backed min heap; reverse the comparator for max-priority behaviour. */
public class BinaryHeap<T> {
    private Object[] elements = new Object[16];
    private int size;
    private final Comparator<T> comparator;

    public BinaryHeap(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void insert(T value) {
        if (size == elements.length) {
            Object[] resized = new Object[elements.length * 2];
            for (int index = 0; index < size; index++) {
                resized[index] = elements[index];
            }
            elements = resized;
        }
        elements[size] = value;
        siftUp(size++);
    }

    @SuppressWarnings("unchecked")
    public T extract() {
        if (size == 0) {
            throw new IllegalStateException("empty heap");
        }
        T result = (T) elements[0];
        elements[0] = elements[--size];
        elements[size] = null;
        siftDown(0);
        return result;
    }

    @SuppressWarnings("unchecked")
    private void siftUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (comparator.compare((T) elements[index], (T) elements[parent]) >= 0) {
                return;
            }
            swap(index, parent);
            index = parent;
        }
    }

    @SuppressWarnings("unchecked")
    private void siftDown(int index) {
        while (2 * index + 1 < size) {
            int child = 2 * index + 1;
            if (child + 1 < size
                    && comparator.compare((T) elements[child + 1], (T) elements[child]) < 0) {
                child++;
            }
            if (comparator.compare((T) elements[index], (T) elements[child]) <= 0) {
                return;
            }
            swap(index, child);
            index = child;
        }
    }

    private void swap(int first, int second) {
        Object value = elements[first];
        elements[first] = elements[second];
        elements[second] = value;
    }
}
