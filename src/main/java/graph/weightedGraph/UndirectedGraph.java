package graph.weightedGraph;

/**
 * Weighted graph represented by a list of vertices and list of edges
 * @param <T> class of vertex identifier
 * @param <W> class of weight
 */
public class UndirectedGraph<T, W> extends WeightedGraphImpl<T, W> {
    @Override
    public void addEdge(T v1, T v2, W w) {
        getEdges().add(new Edge<>(v1, v2, w));
        addAdjacency(new Edge<>(v1, v2, w));
        addAdjacency(new Edge<>(v2, v1, w));
    }

}
