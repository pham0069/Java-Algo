package graph.unweightedGraph;

import java.util.Map;
import java.util.Set;

public interface UnweightedGraph<V> extends Graph<V> {
    default boolean isWeighted() {
        return false;
    }
    void addEdge(V v1, V v2);
    void removeEdge(V v1, V v2);
    Map<V, Set<V>> getAdjacency();
}
