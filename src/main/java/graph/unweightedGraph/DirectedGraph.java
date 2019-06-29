package graph.unweightedGraph;

import java.util.stream.IntStream;

public class DirectedGraph<V> extends UnweightedGraphImpl<V> {
    @Override
    public void addEdge(V v1, V v2){
        adjacency.get(v1).add(v2);
    }
    @Override
    public void removeEdge(V v1, V v2){
        adjacency.get(v1).remove(v2);
    }

    public static DirectedGraph<Integer> one() {
        DirectedGraph<Integer> graph = new DirectedGraph<>();
        IntStream.range(0, 7).boxed().forEach(graph::addVertex);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(4, 1);
        graph.addEdge(5, 6);
        graph.addEdge(6, 4);
        graph.addEdge(6, 0);
        return graph;
    }

    public static DirectedGraph<Integer> acyclicDirectedGraph() {
        DirectedGraph<Integer> graph = new DirectedGraph<>();
        IntStream.range(0, 6).boxed().forEach(graph::addVertex);
        graph.addEdge(2, 3);
        graph.addEdge(3, 1);
        graph.addEdge(4, 0);
        graph.addEdge(4, 1);
        graph.addEdge(5, 0);
        graph.addEdge(5, 2);
        return graph;
    }

    public static DirectedGraph<Integer> acyclicDirectedGraph2() {
        DirectedGraph<Integer> graph = new DirectedGraph<>();
        IntStream.range(0, 4).boxed().forEach(graph::addVertex);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 3);
        return graph;
    }
}
