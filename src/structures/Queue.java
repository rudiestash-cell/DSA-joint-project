package structures;

public class Queue<T> {
    private final DoublyLinkedList<T> data=new DoublyLinkedList<>();
    public void enqueue(T x) {
        data.addLast(x);
    }public T dequeue() {
        return data.removeFirst();
    }public T peek() {
        return data.get(0);
    }public boolean isEmpty() {
        return data.isEmpty();
    }public int size() {
        return data.size();
    }
}
