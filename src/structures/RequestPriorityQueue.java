public class RequestPriorityQueue<T> {

    private static class Entry<T> {
        int priority;
        T value;
        Entry(int priority, T value) {
            this.priority = priority;
            this.value = value;
        }
    }

    private Entry<T>[] heap;
    private int size;

    @SuppressWarnings("unchecked")
    public RequestPriorityQueue(int capacity) {
        heap = new Entry[Math.max(capacity, 1)];
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(int priority, T value) {
        ensureCapacity();
        heap[size] = new Entry<>(priority, value);
        siftUp(size);
        size++;
    }

    public T removeMin() {
        if (isEmpty()) return null;

        T minValue = heap[0].value;
        heap[0] = heap[size - 1];
        heap[size - 1] = null;
        size--;
        if (size > 0) siftDown(0);
        return minValue;
    }

    public T peek() {
        if (isEmpty()) return null;
        return heap[0].value;
    }

    private void siftUp(int i) {
        while (i > 0) {
            int parent = (i - 1) / 2;
            if (heap[i].priority < heap[parent].priority) {
                swap(i, parent);
                i = parent;
            } else break;
        }
    }

    private void siftDown(int i) {
        while (true) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            int smallest = i;

            if (left < size && heap[left].priority < heap[smallest].priority) smallest = left;
            if (right < size && heap[right].priority < heap[smallest].priority) smallest = right;
            if (smallest == i) break;

            swap(i, smallest);
            i = smallest;
        }
    }

    private void swap(int i, int j) {
        Entry<T> temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    @SuppressWarnings("unchecked")
    private void ensureCapacity() {
        if (size == heap.length) {
            Entry<T>[] bigger = new Entry[heap.length * 2];
            System.arraycopy(heap, 0, bigger, 0, heap.length);
            heap = bigger;
        }
    }
}