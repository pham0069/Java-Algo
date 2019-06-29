package data_structure.advanced.segment_tree;

import java.util.Arrays;
import java.util.Comparator;

/**
 *
 * https://www.spoj.com/problems/MKTHNUM/
 * https://www.youtube.com/watch?v=TH9n_HVkjQM
 * https://github.com/anudeep2011/programming/blob/master/MKTHNUM.cpp
 *
 * You are working for Macrohard company in data structures department.
 * After failing your previous task about key insertion, you were asked
 * to write a new data structure that would be able to return quickly
 * k-th order statistics in the array segment.
 *
 * That is, given an array a[1 ... n] of different integer numbers,
 * your program must answer a series of questions Q(i, j, k) in the form:
 * "What would be the k-th number in a[i ... j] segment, if this segment was sorted?"
 *
 * For example, consider the array a = (1, 5, 2, 6, 3, 7, 4).
 * Let the question be Q(2, 5, 3).
 * The segment a[2 ... 5] is (5, 2, 6, 3).
 * If we sort this segment, we get (2, 3, 5, 6),
 * the third number is 5, and therefore the answer to the question is 5.
 *
 * Constraints
 * 1 ≤ n ≤ 100000 (array length)
 * 1 ≤ m ≤ 5000 (number of queries)
 *
 * There are many ways to solve this problem.
 * merge sort tree + binary search on array values instead of ranges: O(m * log(n)^3)
 * merge sort tree after index compression + binary search : O(m * log(n) ^2)
 * persistent segment trees : O(m * log(n))
 *
 */
public class KthNum {

    static class Query {
        int start;
        int end;
        int k;
        Query(int start, int end, int k) {
            this.start = start;
            this.end = end;
            this.k = k;
        }
    }

    static class Node {
        int val;
        Node left, right;
    }

    static class PersistentSegmentTree {
        //the root of the initial tree with all-zeros
        private final Node initialRoot;
        //version[i] is the root of the tree when inserting arr[i] in
        private final Node[] version;
        private final int len;

        PersistentSegmentTree(int[] rank) {
            len = rank.length;
            version = new Node[len];
            constructTree(0, len-1, initialRoot=new Node());

            for (int i = 0; i < len; i++) {
                version[i] = update(rank[i], 1, 0, len-1, getPreviousVersionRoot(i));
            }
        }

        Node getPreviousVersionRoot(int i) {
            return i==0?initialRoot:version[i-1];
        }

        //init sum tree with all-zeros
        void constructTree(int low, int high, Node root) {
            if (low == high) {
                return;
            }

            int mid = (low + high)/2;
            Node left = new Node();
            Node right = new Node();
            constructTree(low, mid, left);
            constructTree(mid+1, high, right);

            root.left = left;
            root.right = right;
        }

        private Node update(int index, int newVal, int low, int high, Node root) {
            if (low == high) {
                if (index == low) {
                    // found the leaf node corresponds to the update -> replace original root
                    Node node = new Node();
                    node.val = newVal;
                    return node;
                } else {
                    return root;
                }
            }

            // no change
            if (index < low || index > high) {
                return root;
            }

            int mid = (low + high) / 2;
            Node updatedChild = new Node();
            Node updatedRoot = new Node();
            if (index <= mid) {
                updatedChild = update(index, newVal, low, mid, root.left);
                updatedRoot.left = updatedChild;// left child updated
                updatedRoot.right = root.right; // right child not changed
            } else {
                updatedChild = update(index, newVal, mid + 1, high, root.right);
                updatedRoot.left = root.left;// left child unchanged
                updatedRoot.right = updatedChild; // right child updated

            }
            updatedRoot.val = updatedRoot.left.val + updatedRoot.right.val;
            return updatedRoot;
        }

        /**
         * for the array range [start..end]
         * get the kth-rank number if this range is sorted
         * start, end, k are all 0-index based
         */
        int getKthNum(int start, int end, int k) {
            return getKthNum(0, len - 1, getPreviousVersionRoot(start), version[end], k+1);
        }

        /**
         * rank is 1-index based
         */
        int getKthNum(int low, int high, Node before, Node after, int rank) {
            if (low==high) {
                return low;
            }

            // number of elements in the array range [start..end] inserted on left tree
            int leftCount = after.left.val - before.left.val;
            int mid = (low+high)/2;
            if (leftCount >= rank) {
                return getKthNum(low, mid, before.left, after.left, rank);
            } else {
                return getKthNum(mid+1, high, before.right, after.right, rank-leftCount);
            }
        }
    }

    static class ArrayElement {
        int value;
        int index;
        ArrayElement(int value, int index) {
            this.value = value;
            this.index = index;
        }
    }

    static void testSegmentTree(int[] arr, Query[] queries) {
        ArrayElement[] sorted = sort(arr);
        int[] rank = getRank(sorted);
        PersistentSegmentTree tree = new PersistentSegmentTree(rank);
        for (Query query: queries) {
            int sortedIndex = tree.getKthNum(query.start, query.end, query.k);
            System.out.println(sorted[sortedIndex].value);
        }
    }

    static ArrayElement[] sort(int[] arr) {
        ArrayElement[] sorted = new ArrayElement[arr.length];
        for (int i = 0; i < arr.length; i++) {
            sorted[i] = new ArrayElement(arr[i], i);
        }

        Arrays.sort(sorted, Comparator.comparing(e -> e.value));
        return sorted;
    }

    static int[] getRank(ArrayElement[] sorted) {
        int[] rank = new int[sorted.length];
        for (int i = 0; i < sorted.length; i++) {
            rank[sorted[i].index] = i;
        }
        return rank;
    }

    public static void main(String[] args) {
        testSegmentTree(new int[]{1, 5, 2, 6, 3, 7, 4}, new Query[] {
                new Query(1, 4, 2),
                new Query(0, 6, 3),
                new Query(2, 4, 1),
                new Query(1, 5, 4),
        });
    }

    //=====================================================================


}
