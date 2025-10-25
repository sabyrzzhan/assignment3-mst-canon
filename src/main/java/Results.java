
import java.util.*;
class MSTResult{ List<Edge> edges; long totalCost; MSTResult(List<Edge> e,long c){edges=e;totalCost=c;} }
class ResultRecord{
  Graph g; MSTResult p; Metrics pm; double pms; MSTResult k; Metrics km; double kms;
  ResultRecord(Graph g,MSTResult p,Metrics pm,double pms,MSTResult k,Metrics km,double kms){
    this.g=g; this.p=p; this.pm=pm; this.pms=pms; this.k=k; this.km=km; this.kms=kms;
  }
}
