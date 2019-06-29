package data_structure.heap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class BinaryMinHeap<T, W extends Comparable> {
    private List<Node> allNodes = new ArrayList<>();
    private Map<T, Integer> nodePositions = new HashMap<>();

    class Node {
        T key;
        W weight;
        Node(T key, W weight) {
            this.key = key;
            this.weight = weight;
        }
        Node(Node node) {
            this.key = node.key;
            this.weight = node.weight;
        }
    }

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

    private void swap(Node node1, Node node2) {
        T key = node1.key;
        W weight = node1.weight;

        node1.key = node2.key;
        node1.weight = node2.weight;

        node2.key = key;
        node2.weight = weight;
    }

    private void updatePositionMap(Node node1, Node node2, int position1, int position2) {
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

    private Node extractMinNode() {
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
