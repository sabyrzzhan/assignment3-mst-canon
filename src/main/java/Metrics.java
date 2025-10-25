
class Metrics{
  long comparisons=0, pushes=0, pops=0, unions=0, pathComp=0;
  long operationsCount(){ return comparisons+pushes+pops+unions+pathComp; }
}
