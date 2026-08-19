public class RequestQueue<T> {

    private class Node {
        T value;
        Node next;
        Node(T value) { this.value = value; }
    }

    private Node head, tail;
    private int size;

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(T value) {
        Node node = new Node(value);
        if (isEmpty()) {
            head = tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    public T dequeue() {
        if (isEmpty()) return null;
        T value = head.value;
        head = head.next;
        if (head == null) tail = null;
        size--;
        return value;
    }

    public T peek() {
        if (isEmpty()) return null;
        return head.value;
    }
}