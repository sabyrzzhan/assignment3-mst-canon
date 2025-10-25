
import java.util.*;
class TestRunner{
  static void runAll(List<ResultRecord> rs){
    int pass=0, fail=0;
    for(ResultRecord r: rs){
      boolean ok=true;
      ok &= assertTrue(r.p.totalCost==r.k.totalCost,"equal cost g"+r.g.id);
      ok &= assertTrue(r.p.edges.size()==r.g.n-1,"prim |E|=V-1 g"+r.g.id);
      ok &= assertTrue(r.k.edges.size()==r.g.n-1,"kruskal |E|=V-1 g"+r.g.id);
      if(ok) pass++; else fail++;
    }
    System.out.println("TESTS summary: pass="+pass+", fail="+fail);
  }
  static boolean assertTrue(boolean c,String msg){ if(!c){ System.out.println("[FAIL] "+msg); return false;} return true; }
}
