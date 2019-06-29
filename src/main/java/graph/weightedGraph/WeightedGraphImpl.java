package graph.weightedGraph;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public abstract class WeightedGraphImpl<T, W> {
    @Getter
    private List<Vertex<T>> vertices = new ArrayList<>();
    private Map<T, List<Edge<T, W>>> adjacency = new HashMap<>();
    @Getter private List<Edge<T, W>> edges = new ArrayList<>();

    public void addVertex(T v) {
        Vertex<T> vertex = new Vertex<T>(v);
        vertices.add(vertex);
    }

    public abstract void addEdge(T v1, T v2, W w);

    protected void addAdjacency(Edge<T, W> e) {
        adjacency.putIfAbsent(e.getVertex1().getIdentifier(), new ArrayList<>());
        adjacency.get(e.getVertex1().getIdentifier()).add(e);
    }

    public List<Edge<T, W>> getEdges(Vertex<T> vertex) {
        return adjacency.get(vertex.getIdentifier());
    }

    public List<Vertex<T>> getAdjacency(Vertex<T> vertex) {
        return adjacency.get(vertex.getIdentifier()).stream()
                .map(Edge::getVertex2)
                .collect(Collectors.toList());
    }
}
