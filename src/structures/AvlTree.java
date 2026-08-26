package structures;

import java.util.Comparator;

/** Height-balanced BST: a clear balanced-tree alternative for the indexing module. */
public class AvlTree<T> {
    private class Node {
        T v;
        Node l,r;
        int h=1;
        Node(T x) {
            v=x;
        }
    }private Node root;
    private final Comparator<T> c;
    public AvlTree(Comparator<T> c) {
        this.c=c;
    }public void insert(T x) {
        root=put(root,x);
    }public boolean contains(T x) {
        Node n=root;
        while(n!=null) {
            int d=c.compare(x,n.v);
            if(d==0)return true;
            n=d<0?n.l:n.r;
        }return false;
    }private Node put(Node n,T x) {
        if(n==null)return new Node(x);
        int d=c.compare(x,n.v);
        if(d<0)n.l=put(n.l,x);
        else if(d>0)n.r=put(n.r,x);
        else return n;
        n.h=1+Math.max(h(n.l),h(n.r));
        int b=h(n.l)-h(n.r);
        if(b>1&&c.compare(x,n.l.v)<0)return right(n);
        if(b<-1&&c.compare(x,n.r.v)>0)return left(n);
        if(b>1) {
            n.l=left(n.l);
            return right(n);
        }if(b<-1) {
            n.r=right(n.r);
            return left(n);
        }return n;
    }private int h(Node n) {
        return n==null?0:n.h;
    }private Node right(Node y) {
        Node x=y.l,t=x.r;
        x.r=y;
        y.l=t;
        y.h=1+Math.max(h(y.l),h(y.r));
        x.h=1+Math.max(h(x.l),h(x.r));
        return x;
    }private Node left(Node x) {
        Node y=x.r,t=y.l;
        y.l=x;
        x.r=t;
        x.h=1+Math.max(h(x.l),h(x.r));
        y.h=1+Math.max(h(y.l),h(y.r));
        return y;
    }public int height() {
        return h(root);
    }
}
