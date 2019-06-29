package data_structure.advanced.implementation;

/**
 * https://www.geeksforgeeks.org/lazy-propagation-in-segment-tree/
 * https://www.geeksforgeeks.org/lazy-propagation-in-segment-tree-set-2/?ref=rp
 *
 * https://github.com/mission-peace/interview/blob/master/src/com/interview/tree/SegmentTreeMinimumRangeQuery.java
 *
 */
public class SegmentTreeLazyPropagation {
    int[] arr;
    int[] tree;
    int[] lazy;

    SegmentTreeLazyPropagation(int arr[]) {
        this.arr = arr;
        // allocate memory for tree
        int treeSize = getTreeSize(arr.length);
        this.tree = new int[treeSize];
        this.lazy = new int[treeSize];

        constructTree(0, arr.length-1, 0);
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
        tree[root] = Math.min(tree[root*2+1], tree[root*2+2]);
    }

    public int queryRangeLazy(int start, int end) {
        return queryRangeLazy(start, end, 0, arr.length - 1, 0);
    }
    
    private int queryRangeLazy(int start, int end, int low, int high, int root) {
        if(low > high) {
            return Integer.MAX_VALUE;
        }

        //make sure all propagation is done at pos. If not update tree
        //at pos and mark its children for lazy propagation.
        if (lazy[root] != 0) {
            propagateLazy(root, lazy[root]);
        }

        //no overlap
        if(start > high || end < low){
            return Integer.MAX_VALUE;
        }

        //total overlap
        if(start <= low && end >= high){
            return tree[root];
        }

        //partial overlap
        int mid = (low+high)/2;
        return Math.min(queryRangeLazy(start, end, low, mid, 2 * root + 1),
                queryRangeLazy(start, end, mid + 1, high, 2 * root + 2));
    }
    
    public void updateRangeLazy(int start, int end, int delta) {
        updateRangeLazy(start, end, delta, 0, arr.length - 1, 0);
    }

    private void updateRangeLazy(int start, int end, int delta, int low, int high, int root) {
        if(low > high) {
            return;
        }

        // Update tree root if necessary and push the lazy propagation to its children
        if(lazy[root] != 0) {
            propagateLazy(root, lazy[root]);
        }

        //no overlap
        if(start > high || end < low) {
            return;
        }

        //total overlap
        if(start <= low && end >= high) {
            propagateLazy(root, delta);
            return;
        }

        //otherwise partial overlap so look both left and right.
        int mid = (low + high)/2;
        updateRangeLazy(start, end, delta, low, mid, 2*root+1);
        updateRangeLazy(start, end, delta, mid+1, high, 2*root+2);
        tree[root] = Math.min(tree[2*root + 1], tree[2*root + 2]);
    }

    private void propagateLazy(int root, int delta) {
        tree[root] += delta;
        if (2*root+2 < tree.length) { // not leaf node
            lazy[2*root + 1] += delta;
            lazy[2*root + 2] += delta;
        }
        lazy[root] = 0;
    }

    public static void main(String[] args) {
        SegmentTreeLazyPropagation tree = new SegmentTreeLazyPropagation(new int[]{0,3,4,2,1,6,-1});
        System.out.println(tree.queryRangeLazy(0, 3));
        System.out.println(tree.queryRangeLazy(1, 5));
        System.out.println(tree.queryRangeLazy(1, 6));
        tree.updateRangeLazy(2,2, 1);
        System.out.println(tree.queryRangeLazy(1, 3));
        tree.updateRangeLazy(3,5, -2);
        System.out.println(tree.queryRangeLazy(5, 6));
        System.out.println(tree.queryRangeLazy(0, 3));

    }
}
