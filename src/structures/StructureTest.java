package structures;

/** Dependency-free tests for custom data structures;
 run with java structures.StructureTest. */
public class StructureTest  {

 private static int checks;
     private static void ok(boolean value,String name) {
        checks++;
        if(!value)throw new AssertionError(name);
    }
 public static void main(String[] args) {

  DynamicArray<Integer> a=new DynamicArray<>();
        for(int i=0;i<20;i++)a.add(i);
        ok(a.size()==20,"dynamic resize");
        ok(a.get(19)==19,"dynamic get");
        a.set(0,99);
        ok(a.remove(0)==99,"dynamic remove");

  DoublyLinkedList<Integer> l=new DoublyLinkedList<>();
        l.addFirst(2);
        l.addFirst(1);
        l.addLast(3);
        l.insertAfter(2,22);
        ok(l.get(2)==22,"list insertAfter");
        ok(l.remove(22),"list remove");
        ok(l.removeLast()==3,"list removeLast");

  Stack<Integer>s=new Stack<>();
        s.push(1);
        s.push(2);
        ok(s.peek()==2,"stack peek");
        ok(s.pop()==2&&s.pop()==1,"stack LIFO");

  Queue<Integer>q=new Queue<>();
        q.enqueue(1);
        q.enqueue(2);
        ok(q.dequeue()==1&&q.dequeue()==2,"queue FIFO");

  CircularQueue<Integer>c=new CircularQueue<>(2);
        ok(c.enqueue(1)&&c.enqueue(2)&&!c.enqueue(3),"queue full");
        ok(c.dequeue()==1&&c.enqueue(3)&&c.dequeue()==2&&c.dequeue()==3,"queue wrap");

  Deque<Integer>d=new Deque<>();
        d.addFront(2);
        d.addFront(1);
        d.addRear(3);
        ok(d.removeFront()==1&&d.removeRear()==3,"deque ends");

  BinaryHeap<Integer>h=new BinaryHeap<>((x,y)->x-y);
        h.insert(3);
        h.insert(1);
        h.insert(2);
        ok(h.extract()==1&&h.extract()==2,"heap order");

  BinarySearchTree<Integer>b=new BinarySearchTree<>((x,y)->x-y);
        b.insert(2);
        b.insert(1);
        b.insert(3);
        ok(b.contains(3)&&!b.contains(4),"bst search");

  HashTable<String,Integer>t=new HashTable<>(1);
        t.put("a",1);
        t.put("b",2);
        ok(t.get("b")==2&&t.collisions()>0,"hash collision");
        ok(t.remove("a")==1&&t.get("a")==null,"hash remove");

  DisjointSet u=new DisjointSet(3);
        ok(u.union(0,1)&&u.find(0)==u.find(1)&&!u.union(0,1),"union find");

  // --- Added: previously-untested structures (AvlTree, BTreePageIndex, CustomSet, CustomMap) ---
  AvlTree<Integer> avl=new AvlTree<>((x,y)->x-y);
        for(int i=1;i<=7;i++)avl.insert(i); // ascending insert order forces rebalancing
        ok(avl.contains(4)&&!avl.contains(99),"avl search");
        ok(avl.height()<=4,"avl stays balanced (height <= ceil(1.44*log2(n+2))-ish bound for n=7)");

  BTreePageIndex page=new BTreePageIndex(4);
        ok(page.add("UG010")&&page.add("UG002")&&page.add("UG005")&&page.add("UG020"),"btree page insert within capacity");
        ok(!page.add("UG099"),"btree page insert rejected when page is full");
        ok(page.contains("UG005")&&!page.contains("UG999"),"btree page sorted lookup");
        ok(page.splitTrace().contains("split page at separator"),"btree page split trace");

  CustomSet<String> set=new CustomSet<>(8);
        set.add("UG001");
        set.add("UG002");
        set.add("UG001"); // duplicate add should not grow size
        ok(set.contains("UG001")&&!set.contains("UG999"),"custom set membership");
        ok(set.size()==2,"custom set ignores duplicate add");
        ok(set.remove("UG001")&&!set.contains("UG001"),"custom set remove");

  CustomMap<String,Integer> map=new CustomMap<>(8);
        map.put("UG001",5);
        map.put("UG002",3);
        map.put("UG001",9); // overwrite existing key
        ok(map.get("UG001")==9&&map.get("UG002")==3,"custom map put/get with overwrite");
        ok(map.remove("UG002")==3&&map.get("UG002")==null,"custom map remove");

  System.out.println("Structure tests passed: "+checks);
    }
}
