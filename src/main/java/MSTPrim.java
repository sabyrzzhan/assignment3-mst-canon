
import java.util.*;
class MSTPrim{
  static MSTResult mst(Graph g, Metrics m){
    int n=g.n; boolean[] used=new boolean[n];
    PriorityQueue<int[]> pq=new PriorityQueue<>(Comparator.comparingInt(a->a[2]));
    List<Edge> out=new ArrayList<>(); long cost=0;
    used[0]=true;
    for(Edge e:g.adj.get(0)){ pq.add(new int[]{e.u,e.v,e.w}); m.pushes++; }
    while(!pq.isEmpty() && out.size()<n-1){
      int[] cur=pq.poll(); m.pops++;
      int u=cur[0], v=cur[1], w=cur[2];
      boolean a=used[u], b=used[v]; m.comparisons+=2;
      if(a==b) continue;
      out.add(new Edge(u,v,w)); cost+=w;
      int add=a? v: u; used[add]=true;
      for(Edge e:g.adj.get(add)){
        int x=(e.u==add? e.v: e.u);
        if(!used[x]){ pq.add(new int[]{e.u,e.v,e.w}); m.pushes++; }
        m.comparisons++;
      }
    }
    return new MSTResult(out,cost);
  }
}
