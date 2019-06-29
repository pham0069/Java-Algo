package data_structure.advanced.implementation;

import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * https://www.geeksforgeeks.org/segment-tree-set-1-sum-of-given-range/
 * https://www.geeksforgeeks.org/segment-tree-set-2-range-maximum-query-node-update
 * https://www.geeksforgeeks.org/range-sum-and-update-in-array-segment-tree-using-stack/?ref=rp
 *
 */
public class SegmentTree {
    Operation op;
    int[] arr;
    int[] tree;

    SegmentTree(Operation op, int arr[]) {
        this.op = op;
        this.arr = arr;
        // allocate memory for tree
        this.tree = new int[getTreeSize(arr.length)];

        //constructTree(0, arr.length-1, 0);
        constructTreeStack();
    }

    private int getTreeSize(int n) {
        int m = (int) Math.ceil(Math.log(n)/Math.log(2));
        return 2*(int)Math.pow(2, m) - 1;
    }

    /**
     * The array range [low..high]
     * should be populated into the tree rooted at index root
     * @param low
     * @param high
     * @param root
     */
    private void constructTree(int low, int high, int root) {
        if (low == high) {
            tree[root] = arr[low];
            return;
        }

        int mid = (low+high)/2;
        constructTree(low, mid, root*2+1); // left tree
        constructTree(mid+1, high, root*2+2); // right tree
        // update root based on children
        tree[root] = op.perform(tree[root*2+1], tree[root*2+2]);
    }

    /**
     * Given an array range [start..end]
     * return the result of query operation
     * @param start
     * @param end
     * @return
     */
    private int query(int start, int end) throws Exception {
        if (start > end || start < 0 || end >= arr.length) {
            throw new Exception("Invalid query");
        }
        return query(start, end, 0, arr.length-1, 0);
    }

    private int query(int start, int end, int low, int high, int root) {
        // note that if low == high, only 2 cases: no overlap or total overlap

        // no overlap
        if (start > high || end < low) {
            return op.getDummyValue();
        }

        // total overlap
        if (start <= low && end >= high) {
            return tree[root];
        }

        // partial overlap
        int mid = (low+high)/2;
        int left = query(start, end, low, mid, root*2+1);
        int right = query(start, end, mid+1, high, root*2+2);
        return op.perform(left, right);
    }

    private void update(int index, int newVal) {
        if (arr[index] == newVal) {
            return;
        }

        this.arr[index] = newVal;
        update(index, newVal, 0, arr.length-1, 0);
    }

    /**
     * Not so optimized for min query
     * The update happens from leaf node upwards to the root
     * If the min value does not change in some internal nodes,
     * we can stop the update on the up-path to the root
     *
     * But in our implementation here, we still continue all the way
     * Because this flow is common for both sum and min query (for simplification)
     * For sum query, the update needs all the way to the root
     * @param index
     * @param newVal
     * @param low
     * @param high
     * @param root
     */
    private void update(int index, int newVal, int low, int high, int root) {
        // index not in this range -> no update
        if (index < low || index > high) {
            return;
        }

        if (low == high) {
            if (low == index) {
                tree[root] = newVal;
            }
            return;
        }

        // low <= index <= high
        int mid = (low+high)/2;
        if (index <= mid) {
            update(index, newVal, low, mid, root * 2 + 1);
        } else {
            update(index, newVal, mid + 1, high, root * 2 + 2);
        }
        tree[root] = op.perform(tree[root*2+1], tree[root*2+2]);
    }

    //==================================================================

    //Alternative to recursive solution above is using stack

    /**
     * each node represents a node at index in segment tree
     * low and high is the range boundary of the array
     */
    @AllArgsConstructor
    static class Node {
        int index;
        int low;
        int high;
    }

    /**
     * Example n = 4
     * Tree looks like  0
     *                 /  \
     *                1   2
     *               / \ / \
     *              3  4 5  6
     * Stack = {0}
     * - top = 0, add its children, stack = {1, 2, 0}
     * - top = 1, add its children, stack = {3, 4, 1, 2, 0}
     * - top = 3 can update directly as leaf node and pop, stack =  {4, 1, 2, 0}
     * - top = 4 can update directly as leaf node and pop, stack =  {1, 2, 0}
     * - top = 1, already visited (i.e its children hv been added to the stack and updated)
     * -> can update 1 now with 3, 4 and pop
     * - top = 2, similarly, stack = {5, 6, 2, 0}
     * - top = 5, update 5, stack = {6, 2, 0}
     * - top = 6, update 6, stack = {2, 0}
     * - top = 2, update 2, stack = {0}
     * - top = 0, update,
     *
     */
    private void constructTreeStack() {
        Stack<Node> stack = new Stack<>();
        Set<Node> visited = new HashSet<>();
        stack.push(new Node(0, 0, arr.length-1));
        Node cur;
        int mid;
        while (!stack.isEmpty()) {
            cur = stack.peek();
            // update leaf node
            if (cur.low == cur.high) {
                tree[cur.index] = arr[cur.low];
                stack.pop();
            // update internal node that already has its children updated
            } else if (visited.contains(cur)){
                tree[cur.index] = op.perform(tree[cur.index*2+1], tree[cur.index*2+2]);
                stack.pop();
            // insert children for further process later
            } else {
                mid = (cur.low + cur.high) / 2;
                stack.push(new Node(cur.index * 2 + 2, mid + 1, cur.high));
                stack.push(new Node(cur.index * 2 + 1, cur.low, mid));
                visited.add(cur); // mark visited here
            }
        }
    }

    //==================================================================




    public static void main(String[] args) throws Exception {
        //testMinQuery();
        testSumQuery();
    }

    static void testMinQuery() throws Exception {
        SegmentTree minTree = new SegmentTree(new MinOperation(), new int[]{-1, 3, 4, 1, 2, 0});
        System.out.println(minTree.query(2, 4));
        System.out.println(minTree.query(0, 4));
        System.out.println(minTree.query(3, 5));

        minTree.update(0, 5);
        System.out.println(minTree.query(0, 4));
        System.out.println(minTree.query(0, 2));
    }

    static void testSumQuery() throws Exception {
        SegmentTree sumTree = new SegmentTree(new SumOperation(), new int[]{-1, 3, 4, 1, 2, 0});
        System.out.println(sumTree.query(2, 4));
        System.out.println(sumTree.query(0, 4));
        System.out.println(sumTree.query(3, 5));

        sumTree.update(2, 5);
        System.out.println(sumTree.query(2, 4));
        System.out.println(sumTree.query(0, 4));
        System.out.println(sumTree.query(3, 5));
    }
}

interface Operation {
    int perform(int a, int b);
    int getDummyValue();
}

class SumOperation implements Operation {
    @Override
    public int perform(int a, int b) {
        return a + b;
    }

    @Override
    public int getDummyValue() {
        return 0;
    }
}

class MinOperation implements Operation {
    @Override
    public int perform(int a, int b) {
        return Math.min(a, b);
    }

    @Override
    public int getDummyValue() {
        return Integer.MAX_VALUE;
    }
}
