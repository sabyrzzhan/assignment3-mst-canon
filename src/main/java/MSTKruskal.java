
import java.util.*;
class MSTKruskal{
  static MSTResult mst(Graph g, Metrics m){
    List<Edge> E=new ArrayList<>(g.edges);
    E.sort((x,y)->{ m.comparisons++; return Integer.compare(x.w,y.w); });
    UnionFind uf=new UnionFind(g.n,m); List<Edge> out=new ArrayList<>(); long cost=0;
    for(Edge e:E){
      if(uf.find(e.u)!=uf.find(e.v)){ uf.union(e.u,e.v); m.unions++; out.add(e); cost+=e.w; if(out.size()==g.n-1) break; }
      m.comparisons++;
    }
    return new MSTResult(out,cost);
  }
}
class UnionFind{
  int[] p,r; Metrics m;
  UnionFind(int n,Metrics m){ this.m=m; p=new int[n]; r=new int[n]; for(int i=0;i<n;i++) p[i]=i; }
  int find(int x){ if(p[x]!=x){ m.pathComp++; p[x]=find(p[x]); } return p[x]; }
  void union(int a,int b){ a=find(a); b=find(b); if(a==b) return; if(r[a]<r[b]){int t=a;a=b;b=t;} p[b]=a; if(r[a]==r[b]) r[a]++; }
}
