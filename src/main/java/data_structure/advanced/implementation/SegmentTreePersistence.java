package data_structure.advanced.implementation;

/**
 * https://www.geeksforgeeks.org/persistent-segment-tree-set-1-introduction/
 *
 * Given an array A[] and different point update operations.
 * Considering each point operation to create a new version of the array.
 * We need to answer Q queries, each has a format:
 * (v, l, r) : output the sum of elements in range l to r just after the v-th update.
 *
 * Constraints:
 * Q <= 100
 */
public class SegmentTreePersistence {
    static final int MAX_QUERIES = 100;

    class Node {
        int val;
        Node left, right;
    }

    // version[i] store the segment tree's root of version i
    private final Node[] version = new Node[MAX_QUERIES];
    private final int len;
    private int currentVersion = 0;

    SegmentTreePersistence(int[] arr) {
        len = arr.length;
        Node root = new Node();
        constructTree(arr, 0, len-1, root);
        version[currentVersion] = root;
    }

    void constructTree(int[] arr, int low, int high, Node root) {
        if (low == high) {
            root.val = arr[low];
            return;
        }

        int mid = (low + high)/2;
        Node left = new Node();
        Node right = new Node();
        constructTree(arr, low, mid, left);
        constructTree(arr, mid+1, high, right);

        root.left = left;
        root.right = right;
        root.val = left.val + right.val;
    }

    void update(int index, int newVal) {
        Node updatedRoot = update(index, newVal, 0, len-1, version[currentVersion]);
        version[++currentVersion] = updatedRoot;
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
        if (index < low || index > high){
            return root;
        }

        int mid = (low+high)/2;
        Node updatedChild = new Node();
        Node updatedRoot = new Node();
        if (index <= mid) {
            updatedChild = update(index, newVal, low, mid, root.left);
            updatedRoot.left = updatedChild;// left child updated
            updatedRoot.right = root.right; // right child not changed
        } else {
            updatedChild = update(index, newVal, mid+1, high, root.right);
            updatedRoot.left = root.left;// left child unchanged
            updatedRoot.right = updatedChild; // right child updated

        }
        updatedRoot.val = updatedRoot.left.val + updatedRoot.right.val;
        return updatedRoot;
    }

    int sumQuery(int start, int end, int v) throws Exception{
        if (v > currentVersion) {
            throw new Exception("Invalid version");
        }
        return sumQuery(start, end, 0, len-1, version[v]);
    }

    int sumQuery(int start, int end, int low, int high, Node root) {
        // no overlap
        if (start > high || end < low) {
            return 0;
        }

        // total overlap
        if (start<= low && high <= end) {
            return root.val;
        }

        //partial overlap
        int mid = (low+high)/2;
        int leftSum = sumQuery(start, end, low, mid, root.left);
        int rightSum = sumQuery(start, end, mid+1, high, root.right);
        return leftSum + rightSum;
    }

    public static void main(String[] args) throws Exception {
        SegmentTreePersistence tree = new SegmentTreePersistence(new int[]{1, 2, 3, 4});
        tree.update(1, 5);
        tree.update(3, 10);
        tree.update(1, 8);

        for (int i = 0; i <= tree.currentVersion; i++) {
            System.out.println(i + " " + tree.sumQuery(0, 2, i));
            System.out.println(i + " " + tree.sumQuery(1, 3, i));
            System.out.println(i + " " + tree.sumQuery(2, 3, i));
        }

    }

}
