package graph.minimumSpanningTree;

import graph.weightedGraph.Edge;
import graph.weightedGraph.UndirectedGraph;
import graph.weightedGraph.Vertex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * https://www.geeksforgeeks.org/prims-mst-for-adjacency-list-representation-greedy-algo-6/
 * https://github.com/mission-peace/interview/blob/master/src/com/interview/graph/PrimMST.java
 *
 * Proof of Prim algorithm is similar to Kruskal algorithm
 * Time complexity is O(ElogV)
 *
 */
public class PrimAlgorithm2 {
    public static void main(String[] args) {
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

    static List<Edge<Integer, Integer>> findMST(UndirectedGraph<Integer, Integer> graph) {
        //initialise distance to MST of all vertices to infinite value
        BinaryMinHeap<Vertex<Integer>, Integer> minHeap = new BinaryMinHeap<>();
        for (Vertex<Integer> v : graph.getVertices()) {
            minHeap.add(v, Integer.MAX_VALUE);
        }

        //initialise the vertex to start building MST
        Vertex<Integer> startingVertex = graph.getVertices().iterator().next();
        minHeap.decrease(startingVertex, 0);

        Map<Vertex<Integer>, Edge<Integer, Integer>> vertexToEdge = new HashMap<>();
        List<Edge<Integer, Integer>> result = new ArrayList<>();
        while (!minHeap.isEmpty()) {
            Vertex<Integer> current = minHeap.extractMinKey();
            Edge<Integer, Integer> spanningTreeEdge = vertexToEdge.get(current);
            if (spanningTreeEdge != null) {
                result.add(spanningTreeEdge);
            }

            for (Edge<Integer, Integer> edge : graph.getEdges(current)) {
                Vertex<Integer> adjacent = getVertexForEdge(current, edge);
                if (minHeap.containsData(adjacent)
                        && minHeap.getWeight(adjacent) > edge.getWeight()) {
                    //decrease the value of adjacent vertex to this edge weight.
                    minHeap.decrease(adjacent, edge.getWeight());
                    //add vertex->edge mapping in the graph.
                    vertexToEdge.put(adjacent, edge);
                }
            }
        }
        return result;
    }

    private static Vertex<Integer> getVertexForEdge(Vertex<Integer> v, Edge<Integer, Integer> e){
        return e.getVertex1().equals(v) ? e.getVertex2() : e.getVertex1();
    }


    //================================================================================

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
