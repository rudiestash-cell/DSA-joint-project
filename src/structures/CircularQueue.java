package structures;

/** Fixed-capacity FIFO queue with wrap-around indices. */
public class CircularQueue<T> {
    private final Object[] elements;
    private int front;
    private int rear;
    private int size;

    public CircularQueue(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("capacity must be positive");
        }
        elements = new Object[capacity];
    }

    public boolean enqueue(T value) {
        if (isFull()) {
            return false;
        }
        elements[rear] = value;
        rear = (rear + 1) % elements.length;
        size++;
        return true;
    }

    @SuppressWarnings("unchecked")
    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("empty queue");
        }
        T value = (T) elements[front];
        elements[front] = null;
        front = (front + 1) % elements.length;
        size--;
        return value;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == elements.length;
    }

    public int size() {
        return size;
    }
}
