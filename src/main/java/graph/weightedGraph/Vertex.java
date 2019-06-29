package graph.weightedGraph;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode @ToString(of = "identifier")
public class Vertex<T> {
    @Getter T identifier;
    public Vertex(T identifier) {
        this.identifier = identifier;
    }
}
