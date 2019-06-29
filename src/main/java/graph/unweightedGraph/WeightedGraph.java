package graph.unweightedGraph;

import java.util.Map;

public interface WeightedGraph <V, W> extends Graph<V> {
    default boolean isWeighted(){
        return false;
    }
    void addEdge(V v1, V v2, W w);
    void removeEdge(V v1, V v2);
    Map<V, Map<V, W>> getAdjacency();
}
