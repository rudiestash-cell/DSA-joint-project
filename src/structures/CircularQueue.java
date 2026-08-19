public class CircularQueue<T> {

    private T[] items;
    private int front, rear, size, capacity;

    @SuppressWarnings("unchecked")
    public CircularQueue(int capacity) {
        this.capacity = capacity;
        items = (T[]) new Object[capacity];
        front = 0;
        rear = -1;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == capacity;
    }

    public int size() {
        return size;
    }

    public boolean enqueue(T value) {
        if (isFull()) return false;
        rear = (rear + 1) % capacity;
        items[rear] = value;
        size++;
        return true;
    }

    public T dequeue() {
        if (isEmpty()) return null;
        T value = items[front];
        items[front] = null;
        front = (front + 1) % capacity;
        size--;
        return value;
    }

    public T peek() {
        if (isEmpty()) return null;
        return items[front];
    }
}