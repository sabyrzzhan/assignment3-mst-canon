
import java.io.*;
import java.util.*;

public class Main {
  public static void main(String[] args) throws Exception {
    long seed = System.currentTimeMillis();
    String inputFile = "ass_3_input.json";
    String outputFile = "ass_3_output.json";
    String csvFile = "results_summary.csv";
    for (int i=0;i<args.length;i++){
      if ("--seed".equals(args[i]) && i+1<args.length) seed = Long.parseLong(args[++i]);
      else if ("--input".equals(args[i]) && i+1<args.length) inputFile = args[++i];
      else if ("--output".equals(args[i]) && i+1<args.length) outputFile = args[++i];
      else if ("--csv".equals(args[i]) && i+1<args.length) csvFile = args[++i];
    }
    Random rng = new Random(seed);
    List<GraphSpec> specs = Arrays.asList(
      new GraphSpec("small",5,10,30,300),
      new GraphSpec("medium",10,80,300,1000),
      new GraphSpec("large",10,300,1000,2500),
      new GraphSpec("extra",5,1000,3000,5000)
    );
    List<Graph> graphs = new ArrayList<>();
    int gid=1;
    for (GraphSpec s: specs){
      for (int k=0;k<s.count;k++){
        int n = uniform(rng, s.minN, s.maxN);
        int cap = Math.min(s.maxE, n*(n-1)/2);
        int base = n-1;
        int extra = cap>base ? rng.nextInt(cap-base+1) : 0;
        int target = base + extra;
        Graph g = GraphGenerator.generateConnectedGraph(n, target, rng);
        g.id = gid++;
        graphs.add(g);
      }
    }
    try(PrintWriter out = new PrintWriter(new FileWriter(inputFile))){
      out.println(JsonIO.toInputJson(graphs));
    }
    List<ResultRecord> recs = new ArrayList<>();
    for (Graph g: graphs){
      Metrics pm = new Metrics();
      long t0=System.nanoTime();
      MSTResult p = MSTPrim.mst(g, pm);
      long t1=System.nanoTime();
      double pms = (t1-t0)/1_000_000.0;

      Metrics km = new Metrics();
      long t2=System.nanoTime();
      MSTResult k = MSTKruskal.mst(g, km);
      long t3=System.nanoTime();
      double kms = (t3-t2)/1_000_000.0;

      recs.add(new ResultRecord(g,p,pm,pms,k,km,kms));
    }
    TestRunner.runAll(recs);
    try(PrintWriter out = new PrintWriter(new FileWriter(outputFile))){
      out.println(JsonIO.toOutputJson(recs));
    }
    try(PrintWriter out = new PrintWriter(new FileWriter(csvFile))){
      out.println("graph_id,vertices,edges,prim_cost,kruskal_cost,prim_ops,kruskal_ops,prim_ms,kruskal_ms");
      for (ResultRecord r: recs){
        out.printf(java.util.Locale.US,"%d,%d,%d,%d,%d,%d,%d,%.3f,%.3f%n",
          r.g.id, r.g.n, r.g.edges.size(),
          r.p.totalCost, r.k.totalCost,
          r.pm.operationsCount(), r.km.operationsCount(),
          r.pms, r.kms
        );
      }
    }
    try(PrintWriter out = new PrintWriter(new FileWriter("report_notes.txt"))){
      out.println("Report: Intro, Data, Algorithms, Results, Comparison, Tests, Conclusions.");
    }
  }
  static int uniform(Random rng,int a,int b){ return a + rng.nextInt(b-a+1); }
}
class GraphSpec{
  String name; int count; int minN; int maxN; int maxE;
  GraphSpec(String name,int count,int minN,int maxN,int maxE){
    this.name=name; this.count=count; this.minN=minN; this.maxN=maxN; this.maxE=maxE;
  }
}
