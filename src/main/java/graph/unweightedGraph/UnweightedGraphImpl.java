package graph.unweightedGraph;

import lombok.Getter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class UnweightedGraphImpl<V> implements UnweightedGraph<V> {
    @Getter protected Map<V, Set<V>> adjacency = new HashMap<>();
    @Override
    public boolean isDirected() {
        return false;
    }
    @Override
    public void addVertex(V v) {
        adjacency.putIfAbsent(v, new HashSet<>());
    }
    @Override
    public void removeVertex(V v) {
        adjacency.values().stream()
                .map(e -> e.remove(v));
        adjacency.remove(v);
    }
    @Override
    public Set<V> getVertices() {
        return adjacency.keySet();
    }
    @Override
    public Set<V> getAdjacency(V v){
        return adjacency.get(v);
    }
}
