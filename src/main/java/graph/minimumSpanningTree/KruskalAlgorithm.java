package graph.minimumSpanningTree;

import graph.disjointSet.DisjointSet;
import graph.weightedGraph.Edge;
import graph.weightedGraph.UndirectedGraph;
import graph.weightedGraph.Vertex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

/**
 * https://www.geeksforgeeks.org/kruskals-minimum-spanning-tree-algorithm-greedy-algo-2/
 *https://github.com/mission-peace/interview/blob/master/src/com/interview/graph/KruskalMST.java
 *
 * Kruskal's algorithm to find MST is a Greedy Algorithm. The Greedy Choice is to pick the smallest weight edge that
 * does not cause a cycle in the MST constructed so far.
 *
 * Algorithm:
 * 1. Sort all the edges in non-decreasing order of their weight.
 * 2. Pick the smallest edge. Check if it forms a cycle with the spanning tree formed so far. If cycle is not formed,
 * include this edge. Else, discard it. This step uses Union-Find algorithm to detect cycle.
 * 3. Repeat step#2 until there are (V-1) edges in the spanning tree.
 *
 * Time complexity is O(ElogE) or O(ElogV). Sorting of edges takes O(ELogE) time. After sorting, we iterate through all
 * edges and apply find-union algorithm. The find and union operations can take at most O(LogV) time. So overall
 * complexity is O(ELogE + ELogV) time. The value of E can be at most O(V^2), so O(LogV) are O(LogE) same. Therefore,
 * overall time complexity is O(ElogE) or O(ElogV)
 * ---------------------------------------------------------------------------------------------------------------------
 * https://math.stackexchange.com/questions/1251953/the-output-of-kruskals-algorithm-is-a-spanning-tree
 *
 * To prove the correctness of Kruskal's algorithm, we need to prove that it generates a spanning tree and that
 * this spanning tree is minimal. Let 𝑆 be the subgraph of 𝐺 which is the output of Kruskal's algorithm.
 *
 * First we show that it generates a spanning tree. A spanning tree must be connected and be acyclic.
 * 𝑆 must be connected (and contain all vertices of 𝐺), because otherwise the algorithm wouldn't have terminated.
 * Furthermore, 𝑆 must be acyclic, because each time an edge is added, that edge must not create cycles. Hence,
 * Kruskal's algorithm produces a spanning tree.
 *
 * Next we show that this spanning tree is minimal. Assume towards a contradiction that 𝑆 is not minimal. Then there
 * must exist a minimum spanning tree (MST) with edges that are not in 𝑆. Pick the MST with the least number of
 * edges that are not in 𝑆 and call this 𝑇.
 *
 * Now consider the edge 𝑒 which was the first edge to be added to 𝑆 by Kruskal's algorithm and which is not in 𝑇.
 * Then 𝑇∪{𝑒} must contain a circuit, and since 𝑆 has no circuits, one of the edges in the circuit must not be in 𝑆.
 * Call this edge 𝑓. Then 𝑇′=(𝑇∪{𝑒})∖{𝑓}) is also a spanning tree.
 *
 * Since 𝑇 is a MST, we have that 𝑤(𝑒)≥𝑤(𝑓). Also, since 𝑒 was chosen by Kruskal's algorithm, it must be of minimal
 * weight, so 𝑤(𝑒)≤𝑤(𝑓), which in turn implies that 𝑤(𝑒)=𝑤(𝑓). Hence, 𝑇′ is also a MST, but it has one more edge in
 * common with 𝑆 than does 𝑇, but we had chosen 𝑇 such that it had the most number of edges in common with 𝑆, so we
 * arrive at a contradiction. We conclude that 𝑆 is minimal.
 */
public class KruskalAlgorithm {
    static class EdgeComparator implements Comparator<Edge<Integer, Integer>> {
        @Override
        public int compare(Edge<Integer, Integer> edge1, Edge<Integer, Integer> edge2) {
            if (edge1.getWeight() <= edge2.getWeight()) {
                return -1;
            } else {
                return 1;
            }
        }
    }

    static List<Edge<Integer, Integer>> findMST(UndirectedGraph<Integer, Integer> graph) {
        List<Edge<Integer, Integer>> edges = graph.getEdges();
        EdgeComparator edgeComparator = new EdgeComparator();

        // sort all edges in non-decreasing order
        Collections.sort(edges, edgeComparator);
        DisjointSet disjointSet = new DisjointSet();

        for (Vertex<Integer> vertex : graph.getVertices()) {
            disjointSet.makeSet(vertex.getIdentifier());
        }

        List<Edge<Integer, Integer>> mstEdges = new ArrayList<>();
        int mstNumberOfEdges = graph.getVertices().size()-1;

        for (Edge<Integer,Integer> edge : edges) {
            if (mstEdges.size() == mstNumberOfEdges)
                break;
            int v1 = edge.getVertex1().getIdentifier();
            int v2 = edge.getVertex2().getIdentifier();
            DisjointSet.Node root1 = disjointSet.findSet(v1);
            DisjointSet.Node root2 = disjointSet.findSet(v2);
            if (root1 != root2) {
                mstEdges.add(edge);
                disjointSet.union(v1, v2);
            }
        }
        return mstEdges;
    }

    public static void main(String args[]) {
        UndirectedGraph<Integer, Integer> graph = new UndirectedGraph<>();
        IntStream.rangeClosed(1, 7).boxed().forEach(graph::addVertex);

        graph.addEdge(1, 2, 4);
        graph.addEdge(1, 3, 1);
        graph.addEdge(2, 5, 1);
        graph.addEdge(2, 6, 3);
        graph.addEdge(2, 4, 2);
        graph.addEdge(6, 5, 2);
        graph.addEdge(6, 4, 3);
        graph.addEdge(4, 7, 2);
        graph.addEdge(3, 4, 5);
        graph.addEdge(3, 7, 8);
        List<Edge<Integer, Integer>> mstEdges = findMST(graph);
        for (Edge<Integer, Integer> edge : mstEdges) {
            System.out.println(edge.getVertex1() + " " + edge.getVertex2());
        }
    }
}
