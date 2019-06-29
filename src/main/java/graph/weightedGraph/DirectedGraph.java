package graph.weightedGraph;

public class DirectedGraph <T, W> extends WeightedGraphImpl<T, W> {
    @Override
    public void addEdge(T v1, T v2, W w) {
        Edge<T, W> edge = new Edge<>(v1, v2, w);
        getEdges().add(edge);
        addAdjacency(edge);
    }
}
