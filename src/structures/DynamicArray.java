package structures;

/** Array-backed list implemented without Java collection classes. */
public class DynamicArray<T>  {

    private Object[] items = new Object[8];
     private int size;

    public int size() {
         return size;
    } public boolean isEmpty() {
         return size==0;
    }
    public void add(T value) {
         ensure(size+1);
         items[size++]=value;
    }
    @SuppressWarnings("unchecked") public T get(int index) {
         check(index);
         return (T)items[index];
    }
    public void set(int index,T value) {
         check(index);
         items[index]=value;
    }
    @SuppressWarnings("unchecked") public T remove(int index) {
         check(index);
         T old=(T)items[index];
         for(int i=index;i<size-1;i++)items[i]=items[i+1];
         items[--size]=null;
         return old;
    }
    public T[] toArray(T[] target) {
         if(target.length<size) throw new IllegalArgumentException("target too small");
         for(int i=0;i<size;i++) target[i]=get(i);
         return target;
    }
    private void ensure(int needed) {
         if(needed<=items.length)return;
         Object[] next=new Object[items.length*2];
         for(int i=0;i<size;i++)next[i]=items[i];
         items=next;
    }
    private void check(int i) {
         if(i<0||i>=size)throw new IndexOutOfBoundsException("index: "+i);
    }
}
