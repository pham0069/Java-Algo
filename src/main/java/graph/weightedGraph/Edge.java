package graph.weightedGraph;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode(of = {"vertex1", "vertex2"})
@ToString
public class Edge<T, W> {
    @Getter private Vertex<T> vertex1, vertex2;
    @Getter private W weight;

    public Edge(T v1, T v2, W w){
        this.vertex1 = new Vertex<>(v1);
        this.vertex2 = new Vertex<>(v2);
        this.weight = w;
    }
}
