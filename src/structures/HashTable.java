package structures;

/** Separate-chaining hash table;
 each bucket is a custom linked list. */
public class HashTable<K,V> {
    private class Entry {
        K k;
        V v;
        Entry next;
        Entry(K k,V v) {
            this.k=k;
            this.v=v;
        }
    }private Entry[] buckets;
    private int size,collisions;
    @SuppressWarnings("unchecked")public HashTable(int capacity) {
        buckets=(Entry[])new HashTable.Entry[capacity];
    }private int slot(K k) {
        return (k==null?0:(k.hashCode()&0x7fffffff)%buckets.length);
    }public void put(K k,V v) {
        int i=slot(k);
        if(buckets[i]!=null)collisions++;
        for(Entry e=buckets[i];e!=null;e=e.next)if(eq(e.k,k)) {
            e.v=v;
            return;
        }Entry e=new Entry(k,v);
        e.next=buckets[i];
        buckets[i]=e;
        size++;
    }public V get(K k) {
        for(Entry e=buckets[slot(k)];e!=null;e=e.next)if(eq(e.k,k))return e.v;
        return null;
    }public V remove(K k) {
        int i=slot(k);
        Entry p=null,e=buckets[i];
        while(e!=null) {
            if(eq(e.k,k)) {
                if(p==null)buckets[i]=e.next;
                else p.next=e.next;
                size--;
                return e.v;
            }p=e;
            e=e.next;
        }return null;
    }private boolean eq(Object a,Object b) {
        return a==null?b==null:a.equals(b);
    }public int size() {
        return size;
    }public int collisions() {
        return collisions;
    }
}
