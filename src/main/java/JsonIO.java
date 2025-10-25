
import java.util.*;
class JsonIO{
  static String toInputJson(List<Graph> gs){
    StringBuilder sb=new StringBuilder(); sb.append("{\n  \"graphs\": [\n");
    for(int i=0;i<gs.size();i++){
      Graph g=gs.get(i);
      sb.append("    {\n");
      sb.append("      \"id\": ").append(g.id).append(",\n");
      sb.append("      \"nodes\": [");
      for(int v=0;v<g.n;v++){ if(v>0) sb.append(", "); if(v<26) sb.append("\"").append((char)('A'+v)).append("\""); else sb.append("\"N").append(v).append("\""); }
      sb.append("],\n");
      sb.append("      \"edges\": [\n");
      for(int j=0;j<g.edges.size();j++){
        Edge e=g.edges.get(j);
        String from=e.u<26? String.valueOf((char)('A'+e.u)) : "N"+e.u;
        String to  =e.v<26? String.valueOf((char)('A'+e.v)) : "N"+e.v;
        sb.append("        {\"from\": \"").append(from).append("\", \"to\": \"").append(to).append("\", \"weight\": ").append(e.w).append("}");
        if(j+1<g.edges.size()) sb.append(",");
        sb.append("\n");
      }
      sb.append("      ]\n    }");
      if(i+1<gs.size()) sb.append(",");
      sb.append("\n");
    }
    sb.append("  ]\n}\n"); return sb.toString();
  }
  static String toOutputJson(List<ResultRecord> rs){
    StringBuilder sb=new StringBuilder(); sb.append("{\n  \"results\": [\n");
    for(int i=0;i<rs.size();i++){
      ResultRecord r=rs.get(i);
      sb.append("    {\n");
      sb.append("      \"graph_id\": ").append(r.g.id).append(",\n");
      sb.append("      \"input_stats\": { \"vertices\": ").append(r.g.n).append(", \"edges\": ").append(r.g.edges.size()).append(" },\n");
      sb.append("      \"prim\": {\n        \"mst_edges\": [\n");
      for(int j=0;j<r.p.edges.size();j++){
        Edge e=r.p.edges.get(j);
        String from=e.u<26? String.valueOf((char)('A'+e.u)) : "N"+e.u;
        String to  =e.v<26? String.valueOf((char)('A'+e.v)) : "N"+e.v;
        sb.append("          {\"from\": \"").append(from).append("\", \"to\": \"").append(to).append("\", \"weight\": ").append(e.w).append("}");
        if(j+1<r.p.edges.size()) sb.append(",");
        sb.append("\n");
      }
      sb.append("        ],\n        \"total_cost\": ").append(r.p.totalCost).append(",\n");
      sb.append("        \"operations_count\": ").append(r.pm.operationsCount()).append(",\n");
      sb.append(String.format(java.util.Locale.US,"        \"execution_time_ms\": %.3f\n", r.pms));
      sb.append("      },\n");
      sb.append("      \"kruskal\": {\n        \"mst_edges\": [\n");
      for(int j=0;j<r.k.edges.size();j++){
        Edge e=r.k.edges.get(j);
        String from=e.u<26? String.valueOf((char)('A'+e.u)) : "N"+e.u;
        String to  =e.v<26? String.valueOf((char)('A'+e.v)) : "N"+e.v;
        sb.append("          {\"from\": \"").append(from).append("\", \"to\": \"").append(to).append("\", \"weight\": ").append(e.w).append("}");
        if(j+1<r.k.edges.size()) sb.append(",");
        sb.append("\n");
      }
      sb.append("        ],\n        \"total_cost\": ").append(r.k.totalCost).append(",\n");
      sb.append("        \"operations_count\": ").append(r.km.operationsCount()).append(",\n");
      sb.append(String.format(java.util.Locale.US,"        \"execution_time_ms\": %.3f\n", r.kms));
      sb.append("      }\n    }");
      if(i+1<rs.size()) sb.append(",");
      sb.append("\n");
    }
    sb.append("  ]\n}\n"); return sb.toString();
  }
}
