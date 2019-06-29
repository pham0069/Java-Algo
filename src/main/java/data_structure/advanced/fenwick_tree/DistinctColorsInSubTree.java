package data_structure.advanced.fenwick_tree;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * https://www.geeksforgeeks.org/querying-the-number-of-distinct-colors-in-a-subtree-of-a-colored-tree-using-bit/
 * Given a rooted tree T, with N nodes, each node has a color.
 * There are M colors in total.
 * Respond to Q queries of the following type:
 *
 * COLORS u – Print the number of distinct colored nodes under the subtree rooted under u
 *
 */
public class DistinctColorsInSubTree {
    public static void main(String[] args){
        Node root = buildTree();
        List<Visit> tour = depthFirstSearch(root);
        int rootReturnTime = tour.get(0).returnTime;
        int[] flattenColors = new int[rootReturnTime+1];
        Map<Integer, Visit> idToVisitMap = new HashMap<>();
        Set<Integer> colors = new HashSet<>();
        for (Visit visit : tour){
            flattenColors[visit.visitTime] = visit.node.color;
            flattenColors[visit.returnTime] = visit.node.color;
            idToVisitMap.put(visit.node.id, visit);
            colors.add(visit.node.color);
        }
        List<List<Integer>> colorFrequencyList = new ArrayList<>();
        for (Integer color : colors){
            int n = flattenColors.length;
            List<Integer> colorFrequencies = new ArrayList<>();
            int frequency = 0;
            for (int i = 0; i < n; i ++) {
                if (flattenColors[i] == color) {
                    frequency++;
                }
                colorFrequencies.add(frequency);
            }
            colorFrequencyList.add(colorFrequencies);
        }

        Integer[] queries = {9, 2, 5, 3, 1, 7, 8};
        for (Integer queryId : queries){
            Visit visit = idToVisitMap.get(queryId);
            int numberOfDistinctColors = 0;
            for(List<Integer> colorFrequencies : colorFrequencyList){
                long numberOfNodesWithColor = colorFrequencies.get(visit.returnTime) - colorFrequencies.get(visit.visitTime);
                if (numberOfNodesWithColor > 0)
                    numberOfDistinctColors ++;
            }
            System.out.println(numberOfDistinctColors);
        }

    }
    private static Node buildTree(){
        Node node1 = new Node(1, 2);
        Node node2 = new Node(2, 3);
        Node node3 = new Node(3, 3);
        Node node4 = new Node(4, 4);
        Node node5 = new Node(5, 1);
        Node node6 = new Node(6, 3);
        Node node7 = new Node(7, 4);
        Node node8 = new Node(8, 3);
        Node node9 = new Node(9, 2);
        Node node10 = new Node(10, 1);
        Node node11 = new Node(11, 1);
        node1.addChildren(Arrays.asList(node2, node3));
        node2.addChildren(Arrays.asList(node4, node5, node6));
        node3.addChildren(Arrays.asList(node7, node8));
        node7.addChildren(Arrays.asList(node9, node10, node11));
        return node1;
    }
    private static List<Visit> depthFirstSearch(Node root){
        List<Visit> tour = new ArrayList<>();
        visit(root, new Time(), tour);
        return tour;
    }

    private static void visit(Node node, Time time, List<Visit> tour){
        Visit visit = new Visit(node, time.val);
        tour.add(visit);
        for (Node child : node.getChildren()){
            time.increment();
            visit(child, time, tour);
        }
        time.increment();
        visit.returnTime = time.val;
    }

    static class Time{
        int val = 0;
        public void increment(){
            val ++;
        }
    }

    static class Visit{
        Node node;
        int visitTime;
        int returnTime;
        public Visit(Node node, int visitTime){
            this.node = node;
            this.visitTime = visitTime;
        }
    }

    static class Node{
        int id;
        int color;
        @Getter List<Node> children;
        public Node(int id, int color){
            this.id = id;
            this.color = color;
            children = new ArrayList<>();
        }
        public void addChild(Node child){
            children.add(child);
        }
        public void addChildren(Collection<Node> children){
            this.children.addAll(children);
        }
    }
}
