public class ServiceDeque<T> {

    private class Node {
        T value;
        Node prev, next;
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

    public void addFront(T value) {
        Node node = new Node(value);
        if (isEmpty()) {
            head = tail = node;
        } else {
            node.next = head;
            head.prev = node;
            head = node;
        }
        size++;
    }

    public void addBack(T value) {
        Node node = new Node(value);
        if (isEmpty()) {
            head = tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
        size++;
    }

    public T removeFront() {
        if (isEmpty()) return null;
        T value = head.value;
        head = head.next;
        if (head == null) tail = null; else head.prev = null;
        size--;
        return value;
    }

    public T removeBack() {
        if (isEmpty()) return null;
        T value = tail.value;
        tail = tail.prev;
        if (tail == null) head = null; else tail.next = null;
        size--;
        return value;
    }

    public T peekFront() {
        return isEmpty() ? null : head.value;
    }

    public T peekBack() {
        return isEmpty() ? null : tail.value;
    }
}