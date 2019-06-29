package algorithm.sort3;

import algorithm.sort.Utils;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * https://practice.geeksforgeeks.org/problems/sorting-elements-of-an-array-by-frequency/0
 *
 * Given an array of integers, sort the array according to frequency of elements.
 * That is elements that have higher frequency come first.
 * If frequencies of two elements are same, then smaller number comes first.
 *
 */
public class SortElementByFrequency {
    public static void main(String[] args) {
        int[] array = {5, 5, 4, 6, 4, 9, 2, 9, 9};
        sortFrequency(array);
    }
    static void sortFrequency(int[] array) {
        Map<Integer, Node> map = new HashMap<>();
        for (int i = 0; i < array.length; i++) {
            Node node = map.get(array[i]);
            if (node == null) {
                node = new Node(array[i]);
                map.put(array[i], node);
            }
            node.incrementCount();
        }
        List<Node> nodes = new ArrayList<>(map.values());
        Collections.sort(nodes, new CountComparator());
        int index = 0;
        for (Node node: nodes) {
            for (int i = 0; i < node.count; i++) {
                array[index] = node.value;
                ++index;
            }
        }
        System.out.println(Arrays.toString(array));
    }

    static class Node {
        int value;
        int count;
        Node(int value) {
            this.value = value;
            this.count = 0;
        }
        void incrementCount() {
            ++count;
        }
    }
    static class CountComparator implements Comparator<Node> {
        @Override
        public int compare(@NonNull Node n1, @NonNull Node n2){
            if (n1.count==n2.count)
                return n1.value - n2.value;
            return n2.count - n1.count;
        }
    }
}
