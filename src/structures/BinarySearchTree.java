package structures;

import java.util.Comparator;

public class BinarySearchTree<T> {
    private class Node {
        T v;
        Node l,r;
        Node(T x) {
            v=x;
        }
    }private Node root;
    private final Comparator<T> cmp;
    public BinarySearchTree(Comparator<T> c) {
        cmp=c;
    }public void insert(T x) {
        root=insert(root,x);
    }private Node insert(Node n,T x) {
        if(n==null)return new Node(x);
        if(cmp.compare(x,n.v)<0)n.l=insert(n.l,x);
        else if(cmp.compare(x,n.v)>0)n.r=insert(n.r,x);
        return n;
    }public boolean contains(T x) {
        Node n=root;
        while(n!=null) {
            int c=cmp.compare(x,n.v);
            if(c==0)return true;
            n=c<0?n.l:n.r;
        }return false;
    }public void inorder(DynamicArray<T> out) {
        walk(root,out);
    }private void walk(Node n,DynamicArray<T> o) {
        if(n!=null) {
            walk(n.l,o);
            o.add(n.v);
            walk(n.r,o);
        }
    }
}
