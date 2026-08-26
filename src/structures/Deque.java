package structures;

public class Deque<T> {
    private final DoublyLinkedList<T> data = new DoublyLinkedList<>();

    public void addFront(T value) { data.addFirst(value); }
    public void addRear(T value) { data.addLast(value); }
    public T removeFront() { return data.removeFirst(); }
    public T removeRear() { return data.removeLast(); }
    public boolean isEmpty() { return data.isEmpty(); }
    public int size() { return data.size(); }
}
