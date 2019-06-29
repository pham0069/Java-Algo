package graph.unweightedGraph;

import java.util.Set;

public interface Graph<V> {
    boolean isDirected();
    boolean isWeighted();
    void addVertex(V v);
    void removeVertex(V v);
    Set<V> getVertices();
    Set<V> getAdjacency(V v);
}
