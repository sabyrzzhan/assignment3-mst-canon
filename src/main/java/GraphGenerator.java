
import java.util.*;
class GraphGenerator{
  static Graph generateConnectedGraph(int n,int targetEdges,Random rng){
    Graph g=new Graph(n);
    for(int v=1;v<n;v++){ int u=rng.nextInt(v); int w=1+rng.nextInt(100); g.addEdge(u,v,w); }
    int current=g.edges.size();
    java.util.HashSet<Long> seen=new java.util.HashSet<>();
    for(Edge e:g.edges){ int a=Math.min(e.u,e.v), b=Math.max(e.u,e.v); seen.add((((long)a)<<32) ^ (long)b); }
    while(current<targetEdges){
      int u=rng.nextInt(n), v=rng.nextInt(n); if(u==v) continue;
      int a=Math.min(u,v), b=Math.max(u,v);
      long key=((((long)a)<<32) ^ (long)b);
      if(seen.contains(key)) continue;
      int w=1+rng.nextInt(100); g.addEdge(a,b,w); seen.add(key); current++;
    }
    return g;
  }
}
