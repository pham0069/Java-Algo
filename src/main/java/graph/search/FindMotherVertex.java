package graph.search;

import graph.unweightedGraph.DirectedGraph;
import graph.unweightedGraph.Graph;

import java.util.HashSet;
import java.util.Set;

/**
 * https://www.geeksforgeeks.org/find-a-mother-vertex-in-a-graph/
 *
 * A mother vertex in a graph G = (V,E) is a vertex v such that all other vertices in G can be reached by a path from v.
 * There can be more than one mother vertices in a graph. We need to output anyone of them.
 *
 * How to find mother vertex?
 * Case 1:- Undirected Connected Graph : In this case, all the vertices are mother vertices as we can reach to all the
 * other nodes in the graph.
 * Case 2:- Undirected/Directed Disconnected Graph : In this case, there is no mother vertices as we cannot reach to all
 * the other nodes in the graph.
 * Case 3:- Directed Connected Graph : In this case, we have to find a vertex -v in the graph such that we can reach to
 all the other nodes in the graph through a directed path.
 *
 * Method 1: A trivial approach will be to perform a DFS/BFS on all the vertices and find whether we can reach all the
 * vertices from that vertex. This approach takes O(V(E+V)) time, which is very inefficient for large graphs.
 * Method 2: Based on Kosaraju's strongly connected component algo
 * If there exist mother vertex (or vertices), then one of the mother vertices is the last finished vertex in DFS.
 * (Or a mother vertex has the maximum finish time in DFS traversal).
 * A vertex is said to be finished in DFS if a recursive call for its DFS is over, i.e., all descendants of the vertex
 * have been visited.
 * ---------------------------------------------------------------------------------------------------------------------
 * How does the above idea work?
 * Let the last finished vertex be v. Basically, we need to prove that there cannot be an edge from another vertex u to
 * v if u is not another mother vertex (Or there cannot exist a non-mother vertex u such that u-→v is an edge). There
 * can be two possibilities.
 * Recursive DFS call is made for u before v. If an edge u-→v exists, then v must have finished before u because v is
 * reachable through u and a vertex finishes after all its descendants.
 * Recursive DFS call is made for v before u. In this case also, if an edge u-→v exists, then either v must finish
 * before u (which contradicts our assumption that v is finished at the end) OR u should be reachable from v (which
 * means u is another mother vertex).
 * ---------------------------------------------------------------------------------------------------------------------
 * Algorithm :
 * 1. Do DFS traversal of the given graph. While doing traversal keep track of last finished vertex ‘v’. This step takes
 * O(V+E) time.
 * 2. If there exist mother vertex (or vetices), then v must be one (or one of them). Check if v is a mother vertex by
 * doing DFS/BFS from v. This step also takes O(V+E) time.
 */
public class FindMotherVertex {
    public static void main(String[] args) {
        Graph<Integer> graph = DirectedGraph.one();
        findMotherVertex(graph);
    }

    static <V> void findMotherVertex(Graph<V> graph) {
        V lastVertex = getLastVisitedVertexAfterDepthFirstSearch(graph);
        if (lastVertex != null && isMotherVertex(graph, lastVertex))
            System.out.println("Mother vertex is " + lastVertex);
    }

    static <V> V getLastVisitedVertexAfterDepthFirstSearch(Graph<V> graph) {
        Set<V> visited = new HashSet<>();
        Set<V> vertices = graph.getVertices();
        V lastVertex = null;
        for (V vertex : vertices) {
            if (!visited.contains(vertex)) {
                DepthFirstSearch.depthFirstSearch(graph, visited, vertex);
                lastVertex = vertex;
            }
        }
        return lastVertex;
    }

    static <V> boolean isMotherVertex(Graph<V> graph, V vertex) {
        Set<V> visited = new HashSet<>();
        int numberOfVertices = graph.getVertices().size();
        DepthFirstSearch.depthFirstSearch(graph, visited, vertex);
        return visited.size() == numberOfVertices;
    }
}
