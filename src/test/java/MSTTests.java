import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.util.Random;

public class MSTTests {

  @Test
  @DisplayName(" Маленький ручной граф — проверка точного MST")
  void smallGraphManual() {
    Graph g = new Graph(4);
    g.addEdge(0, 1, 10);
    g.addEdge(0, 2, 6);
    g.addEdge(0, 3, 5);
    g.addEdge(1, 3, 15);
    g.addEdge(2, 3, 4);

    Metrics pm = new Metrics();
    Metrics km = new Metrics();
    MSTResult p = MSTPrim.mst(g, pm);
    MSTResult k = MSTKruskal.mst(g, km);

    // Проверяем количество рёбер и стоимость
    assertEquals(3, p.edges.size());
    assertEquals(3, k.edges.size());
    assertEquals(p.totalCost, k.totalCost);
    assertEquals(19, p.totalCost); // известный результат
  }

  @Test
  @DisplayName("Случайные графы — равенство Prim и Kruskal")
  void randomGraphsEquality() {
    Random rng = new Random(123);
    for (int i = 0; i < 10; i++) {
      Graph g = GraphGenerator.generateConnectedGraph(50, 200, rng);
      Metrics pm = new Metrics();
      Metrics km = new Metrics();
      MSTResult p = MSTPrim.mst(g, pm);
      MSTResult k = MSTKruskal.mst(g, km);
      assertEquals(p.totalCost, k.totalCost, "Costs differ on graph " + i);
      assertEquals(g.n - 1, p.edges.size());
      assertEquals(g.n - 1, k.edges.size());
    }
  }

  @Test
  @DisplayName(" Производительность — большой граф")
  void largeGraphPerformance() {
    Random rng = new Random(999);
    Graph g = GraphGenerator.generateConnectedGraph(800, 1600, rng);
    Metrics pm = new Metrics();
    Metrics km = new Metrics();

    long start = System.nanoTime();
    MSTResult p = MSTPrim.mst(g, pm);
    long primTime = System.nanoTime() - start;

    start = System.nanoTime();
    MSTResult k = MSTKruskal.mst(g, km);
    long krTime = System.nanoTime() - start;

    assertEquals(p.totalCost, k.totalCost);
    System.out.printf("Large graph: Prim=%.3f ms, Kruskal=%.3f ms%n",
            primTime / 1_000_000.0, krTime / 1_000_000.0);
  }

  @Test
  @DisplayName(" Несвязный граф — MST только для связной компоненты")
  void disconnectedGraphTest() {
    Graph g = new Graph(5);
    g.addEdge(0, 1, 4);
    g.addEdge(1, 2, 3);
    // Вершины 3 и 4 не соединены
    Metrics pm = new Metrics();
    MSTResult p = MSTPrim.mst(g, pm);
    // Алгоритм не должен упасть
    assertTrue(p.edges.size() <= g.n - 1);
  }

  @Test
  @DisplayName("⃣Сравнение сложности — количество операций")
  void operationsCount() {
    Random rng = new Random(55);
    Graph g = GraphGenerator.generateConnectedGraph(100, 400, rng);
    Metrics pm = new Metrics();
    Metrics km = new Metrics();
    MSTResult p = MSTPrim.mst(g, pm);
    MSTResult k = MSTKruskal.mst(g, km);
    assertEquals(p.totalCost, k.totalCost);
    assertTrue(pm.operationsCount() > 0);
    assertTrue(km.operationsCount() > 0);
    System.out.println("Prim ops=" + pm.operationsCount() + ", Kruskal ops=" + km.operationsCount());
  }
}
