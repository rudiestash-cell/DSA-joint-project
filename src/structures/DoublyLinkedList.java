package structures;

public class DoublyLinkedList<T>  {

    private class Node  {
         T value;
         Node prev,next;
         Node(T v) {
            value=v;
        }
    } private Node head,tail;
     private int size;

    public int size() {
        return size;
    } public boolean isEmpty() {
        return size==0;
    }
    public void addFirst(T v) {
         Node n=new Node(v);
         if(head==null)head=tail=n;
         else  {
            n.next=head;
            head.prev=n;
            head=n;
        } size++;
    }
    public void addLast(T v) {
         Node n=new Node(v);
         if(tail==null)head=tail=n;
         else  {
            tail.next=n;
            n.prev=tail;
            tail=n;
        } size++;
    }
    public T removeFirst() {
        if(head==null)throw new IllegalStateException("empty list");
         T v=head.value;
         head=head.next;
        if(head==null)tail=null;
        else head.prev=null;
        size--;
        return v;
    }
    public T removeLast() {
        if(tail==null)throw new IllegalStateException("empty list");
         T v=tail.value;
        tail=tail.prev;
        if(tail==null)head=null;
        else tail.next=null;
        size--;
        return v;
    }
    public void insertAfter(T existing,T value) {
        for(Node n=head;n!=null;n=n.next)if(eq(n.value,existing)) {
            Node x=new Node(value);
            x.next=n.next;
            x.prev=n;
            if(n.next!=null)n.next.prev=x;
            else tail=x;
            n.next=x;
            size++;
            return;
        }throw new IllegalArgumentException("value not found");
    }
    public boolean remove(T value) {
        for(Node n=head;n!=null;n=n.next)if(eq(n.value,value)) {
            if(n.prev!=null)n.prev.next=n.next;
            else head=n.next;
            if(n.next!=null)n.next.prev=n.prev;
            else tail=n.prev;
            size--;
            return true;
        }return false;
    }
    public T get(int index) {
        if(index<0||index>=size)throw new IndexOutOfBoundsException();
        Node n=head;
        while(index-->0)n=n.next;
        return n.value;
    }
    private boolean eq(T a,T b) {
        return a==null?b==null:a.equals(b);
    }
}
