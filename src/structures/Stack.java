package structures;

public class Stack<T> {
    private final DoublyLinkedList<T> data=new DoublyLinkedList<>();
    public void push(T x) {
        data.addFirst(x);
    }public T pop() {
        return data.removeFirst();
    }public T peek() {
        return data.get(0);
    }public boolean isEmpty() {
        return data.isEmpty();
    }public int size() {
        return data.size();
    }
}
