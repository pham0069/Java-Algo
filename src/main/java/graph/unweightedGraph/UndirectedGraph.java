package graph.unweightedGraph;

public class UndirectedGraph<V> extends UnweightedGraphImpl<V> {
    @Override
    public void addEdge(V v1, V v2){
        adjacency.get(v1).add(v2);
        adjacency.get(v2).add(v1);
    }
    @Override
    public void removeEdge(V v1, V v2){
        adjacency.get(v1).remove(v2);
        adjacency.get(v2).remove(v1);
    }

    public static UndirectedGraph<Integer> one() {
        UndirectedGraph<Integer> graph = new UndirectedGraph<>();
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addEdge(0, 1);
        graph.addEdge(0, 3);
        graph.addEdge(1, 2);
        return graph;
    }
}
