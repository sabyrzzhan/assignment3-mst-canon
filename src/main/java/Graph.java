
import java.util.*;
class Edge{ int u,v,w; Edge(int u,int v,int w){this.u=u;this.v=v;this.w=w;} }
class Graph{
  int id=-1; int n;
  List<Edge> edges = new ArrayList<>();
  List<List<Edge>> adj;
  Graph(int n){ this.n=n; adj=new ArrayList<>(n); for(int i=0;i<n;i++) adj.add(new ArrayList<>()); }
  void addEdge(int u,int v,int w){ Edge e=new Edge(u,v,w); edges.add(e); adj.get(u).add(e); adj.get(v).add(e); }
}
