package graph.topologicalSorting;

import graph.unweightedGraph.DirectedGraph;
import graph.unweightedGraph.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * https://www.geeksforgeeks.org/topological-sorting-indegree-based-solution/
 *
 * Theorem: A DAG G has at least one vertex with in-degree 0 and one vertex with out-degree 0.
 * Proof: There’s a simple proof to the above fact is that a DAG does not contain a cycle which means that all paths
 * will be of finite length. Now let S be the longest path from u(source) to v(destination). Since S is the longest path
 * there can be no incoming edge to u and no outgoing edge from v, if this situation had occurred then S would not have
 * been the longest path => indegree(u) = 0 and outdegree(v) = 0
 *
 * Kahn's Algorithm:
 * 1. Compute in-degree (number of incoming edges) for each of the vertex present in the DAG and initialize the count
 * of visited nodes as 0.
 * 2. Pick all the vertices with in-degree as 0 and add them into a queue (Enqueue operation)
 * 3: Remove a vertex from the queue (Dequeue operation) and then increment count of visited nodes by 1. Decrease
 * in-degree by 1 for all its neighboring nodes. If in-degree of a neighboring nodes is reduced to zero, then add it to
 * the queue.
 * 4. Repeat Step 3 until the queue is empty.
 * 5. If count of visited nodes is not equal to the number of nodes in the graph then the topological sort is not
 * possible for the given graph.
 *
 * There are 2 ways to calculate in-degree of every vertex. Both have time Complexity of O(V+E)
 * 1) Traverse the array of edges and simply increase the counter of the destination node by 1.
 * 2) Traverse the list for every node and then increment the in-degree of all the nodes connected to it by 1.
 */
public class KahnAlgorithm {
    public static void main(String[] args) {
        Graph<Integer> graph = DirectedGraph.acyclicDirectedGraph();
        printTopologicalSorting(graph);
    }

    /**
     * Using binary heap takes O(V+E+ElogV)
     * @param graph
     * @param <V>
     */
    static <V> void printTopologicalSorting(Graph<V> graph) {
        BinaryMinHeap<V, Integer> indegree = getIndegreeHeap(graph);
        while (!indegree.isEmpty()) {
            V vertex = indegree.extractMinKey();
            System.out.print(vertex + " ");
            for(V adjacent : graph.getAdjacency(vertex)) {
                indegree.decreaseBy(adjacent, d -> d-1);
            }
        }
    }

    static <V> Map<V, Integer> getIndegreeMap(Graph<V> graph) {
        Map<V, Integer> indegreeMap = new HashMap<>();
        for (V vertex : graph.getVertices()) {
            indegreeMap.put(vertex, 0);
        }

        for (V vertex : graph.getVertices()) {
            for (V adjacent : graph.getAdjacency(vertex))
                indegreeMap.put(adjacent, indegreeMap.get(adjacent)+1);
        }

        return indegreeMap;

    }

    static <V> BinaryMinHeap<V, Integer> getIndegreeHeap(Graph<V> graph) {
        Map<V, Integer> indegreeMap = getIndegreeMap(graph);
        BinaryMinHeap<V, Integer> indegreeHeap = new BinaryMinHeap<>();
        for (Map.Entry<V, Integer> entry : indegreeMap.entrySet()) {
            indegreeHeap.add(entry.getKey(), entry.getValue());
        }
        return indegreeHeap;
    }

    //========================================================================

    static class Node<T, W extends Comparable> {
        T key;
        W weight;
        Node(T key, W weight) {
            this.key = key;
            this.weight = weight;
        }
        Node(Node<T, W> node) {
            this.key = node.key;
            this.weight = node.weight;
        }
    }

    static class BinaryMinHeap<T, W extends Comparable> {
        private List<Node<T, W>> allNodes = new ArrayList<>();
        private Map<T, Integer> nodePositions = new HashMap<>();



        public boolean isEmpty() {
            return allNodes.isEmpty();
        }

        public boolean containsData(T key) {
            return nodePositions.containsKey(key);
        }

        public void add(T key, W weight) {
            allNodes.add(new Node(key, weight));
            int currentPosition = allNodes.size() - 1;
            nodePositions.put(key, currentPosition);
            traverseUpToCorrectPosition(currentPosition);
        }

        private void swap(Node<T, W> node1, Node<T, W> node2) {
            T key = node1.key;
            W weight = node1.weight;

            node1.key = node2.key;
            node1.weight = node2.weight;

            node2.key = key;
            node2.weight = weight;
        }

        private void updatePositionMap(Node<T, W> node1, Node<T, W> node2, int position1, int position2) {
            T key1 = node1.key;
            T key2 = node2.key;
            nodePositions.remove(key1);
            nodePositions.remove(key2);
            nodePositions.put(key1, position1);
            nodePositions.put(key2, position2);
        }

        public T min() {
            return allNodes.get(0).key;
        }

        public void decreaseBy(T key, Function<W, W> reduction) {
            if (!nodePositions.containsKey(key))
                return;
            int currentPosition = nodePositions.get(key);
            W currentWeight = allNodes.get(currentPosition).weight;
            allNodes.get(currentPosition).weight = reduction.apply(currentWeight);
            traverseUpToCorrectPosition(currentPosition);
        }

        /**
         * decrease go up
         * increase go down
         * @param key
         * @param newWeight
         */
        public void decrease(T key, W newWeight) {
            if (!nodePositions.containsKey(key))
                return;
            int currentPosition = nodePositions.get(key);
            allNodes.get(currentPosition).weight = newWeight;
            traverseUpToCorrectPosition(currentPosition);
        }

        private void traverseUpToCorrectPosition(int currentPosition) {
            int parentPosition = parent(currentPosition);
            while (parentPosition >= 0) {
                Node currentNode = allNodes.get(currentPosition);
                Node parentNode = allNodes.get(parentPosition);
                if (parentNode.weight.compareTo(currentNode.weight) <= 0)
                    break;

                swap(currentNode, parentNode);
                updatePositionMap(currentNode, parentNode, currentPosition, parentPosition);
                currentPosition = parentPosition;
                parentPosition = parent(currentPosition);
            }
        }

        public T extractMinKey() {
            return extractMinNode().key;
        }

        public W getWeight(T key) {
            Integer position = nodePositions.get(key);
            if (position == null)
                return null;
            return allNodes.get(position).weight;
        }

        private Node<T, W> extractMinNode() {
            Node minNode = replaceMinNodeWithLastNode();
            traverseDownToCorrectPosition(0);
            return minNode;
        }

        private Node replaceMinNodeWithLastNode() {
            Node firstNode = allNodes.get(0);
            Node minNode = new Node(firstNode);
            int lastNodeIndex = allNodes.size()-1;
            Node lastNode = allNodes.get(lastNodeIndex);

            //swap first and last node
            swap(firstNode, lastNode);
            updatePositionMap(firstNode, lastNode, 0, lastNodeIndex);

            //remove min node
            allNodes.remove(lastNodeIndex);
            nodePositions.remove(minNode.key);

            return minNode;
        }

        private void traverseDownToCorrectPosition(int currentPosition) {
            int size = allNodes.size();
            int leftChildPosition, rightChildPosition;
            while (true) {
                leftChildPosition = left(currentPosition);
                rightChildPosition = right(currentPosition);
                if (leftChildPosition >= size)
                    break;
                if (rightChildPosition == size)
                    rightChildPosition = leftChildPosition;

                Node leftChild = allNodes.get(leftChildPosition);
                Node rightChild = allNodes.get(rightChildPosition);
                Node currentNode = allNodes.get(currentPosition);
                int smallerPosition = leftChildPosition;
                Node smallerChild = leftChild;
                if (leftChild.weight.compareTo(rightChild.weight) > 0){
                    smallerPosition = rightChildPosition;
                    smallerChild = rightChild;
                }
                if(currentNode.weight.compareTo(smallerChild.weight) > 0){
                    swap(currentNode, smallerChild);
                    updatePositionMap(currentNode, smallerChild, currentPosition, smallerPosition);
                    currentPosition = smallerPosition;
                }else{
                    break;
                }
            }
        }


        private int parent(int node) {
            return (node-1)/2;
        }
        private int left(int node) {
            return node*2+1;
        }
        private int right(int node) {
            return node*2+2;
        }

    }
}
