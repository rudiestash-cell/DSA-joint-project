package structures;

public class DisjointSet {
    private final int[] parent,rank;
    public DisjointSet(int n) {
        parent=new int[n];
        rank=new int[n];
        for(int i=0;i<n;i++)parent[i]=i;
    }public int find(int x) {
        if(parent[x]!=x)parent[x]=find(parent[x]);
        return parent[x];
    }public boolean union(int a,int b) {
        a=find(a);
        b=find(b);
        if(a==b)return false;
        if(rank[a]<rank[b])parent[a]=b;
        else {
            parent[b]=a;
            if(rank[a]==rank[b])rank[a]++;
        }return true;
    }
}
