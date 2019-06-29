package graph.disjointSet;

import lombok.ToString;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * https://github.com/mission-peace/interview/blob/master/src/com/interview/graph/DisjointSet.java
 *
 * Disjoint set implementation of union with rank
 * To implement path compression, we also need to change parent for child of child
 */
public class DisjointSet {
    private Map<Integer, Node> nodeMap = new HashMap<>();
    private boolean compressPath = false;
    @ToString(of = {"data", "rank"})
    public class Node {
        int data;
        int rank;
        Node parent;

        Node(int data) {
            this.data = data;
            this.rank = 0;
            this.parent = this;
        }
    }

    public void makeSet(int data) {
        Node node = new Node(data);
        nodeMap.put(data, node);
    }

    public Node findSet(int data) {
        Node node = nodeMap.get(data);
        if (node == null)
            return null;
        while (node != node.parent)
            node = node.parent;
        return node;
    }

    public void union(int data1, int data2) {
        Node set1 = findSet(data1);
        Node set2 = findSet(data2);
        if (set1 == null || set2 == null)
            return;
        // if data belong to same set do nothing
        if (set1 == set2)
            return;
        Node parent, child;
        if (set1.rank >= set2.rank) {
            parent = set1;
            child = set2;
        } else {
            parent = set2;
            child = set1;
        }
        child.parent = parent;
        if (parent.rank == child.rank)
            parent.rank++;

        //System.out.println(String.format("Union %d and %d to root %s", data1, data2, parent));
    }

    public static void main(String args[]) {
        DisjointSet ds = new DisjointSet();
        IntStream.rangeClosed(1, 7).forEach(ds::makeSet);

        ds.union(1, 2);
        ds.union(2, 3);
        ds.union(4, 5);
        ds.union(6, 7);
        ds.union(5, 6);
        ds.union(3, 7);

        IntStream.rangeClosed(1, 7).forEach(i -> System.out.println(ds.findSet(i)));
    }
}
