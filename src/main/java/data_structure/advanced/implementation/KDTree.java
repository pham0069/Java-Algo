package data_structure.advanced.implementation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * https://www.geeksforgeeks.org/k-dimensional-tree/
 * https://www.geeksforgeeks.org/k-dimensional-tree-set-2-find-minimum/
 * https://www.geeksforgeeks.org/k-dimensional-tree-set-3-delete/
 *
 */
public class KDTree {
    /**
     * Node for tree
     */
    static class Node {
        public int point[];
        public Node left, right;

        Node(int ... point) {
            this.point = point;
        }

        public boolean equals(Node that) {
            if (this == that) {
                return true;
            }
            if (that == null) {
                return false;
            }
            if (this.point.length != that.point.length) {
                return false;
            }
            for (int i = 0; i < point.length; i++) {
                if (point[i] != that.point[i]) {
                    return false;
                }
            }
            return true;
        }

        public String toString() {
            return Arrays.toString(point);
        }

        public long getSquareDistance(Node node) {
            long sum = 0;
            for (int i = 0; i < point.length; i++) {
                sum += square(point[i] - node.point[i]);
            }
            return sum;
        }

        private long square(int a) {
            return (long)a*a;
        }
    }

    //====================================================================

    static void printTree(Node root) {
        preOrderTraversal(root);
        System.out.println();
    }

    static void preOrderTraversal(Node node) {
        if (node == null) {
            return;
        }
        System.out.print(node + " ");
        preOrderTraversal(node.left);
        preOrderTraversal(node.right);
    }

    //====================================================================

    /*
     * Insert node to a KD tree with root
     */
    static Node insert(Node root, Node node) {
        int dim = 0;
        Node cur = root;
        int k = root.point.length;
        while(true) {
            int key = cur.point[dim];
            if (node.point[dim] >= key) {
                if (cur.right == null) {
                    cur.right = node;
                    break;
                } else {
                    cur = cur.right;
                    dim = (dim+1) % k;
                }
            } else {
                if (cur.left == null) {
                    cur.left = node;
                    break;
                } else {
                    cur = cur.left;
                    dim = (dim+1) % k;
                }
            }
        }

        return root;
    }

    //====================================================================

    /*
     * Search if a KD tree with root has a given node
     */
    static boolean search(Node root, Node node) {
        int dim = 0;
        Node cur = root;
        int k = root.point.length;
        while(true) {
            if (cur.equals(node)) {
                return true;
            }
            int key = cur.point[dim];
            if (node.point[dim] >= key) {
                if (cur.right == null) {
                    return false;
                } else {
                    cur = cur.right;
                    dim = (dim+1) % k;
                }
            } else {
                if (cur.left == null) {
                    return false;
                } else {
                    cur = cur.left;
                    dim = (dim+1) % k;
                }
            }
        }
    }

    static void testSearch() {
        Node root = new Node (3, 6);
        insert(root, new Node (17, 15));
        insert(root, new Node (13, 15));
        insert(root, new Node (6, 12));
        insert(root, new Node (9, 1));
        insert(root, new Node (2, 7));
        insert(root, new Node (10, 19));

        System.out.println(search(root, new Node (10, 19)));
        System.out.println(search(root, new Node (17, 15)));
        System.out.println(search(root, new Node (9, 1)));
        System.out.println(search(root, new Node (2, 7)));
        System.out.println(search(root, new Node (10, 18)));
        System.out.println(search(root, new Node (14, 15)));
    }

    //====================================================================

    static Node delete(Node root, Node node) {
        return delete(root, node, 0);
    }

    static Node delete(Node root, Node node, int dim) {
        Node prev = null;
        Node cur = root;
        int k = root.point.length;
        while(true) {
            if (cur.equals(node)) {
                if (cur.left == null && cur.right == null){
                    if (prev == null) {
                        return null;
                    }
                    if (prev.left == cur) {
                        prev.left = null;
                    } else {
                        prev.right = null;
                    }
                    return root;
                } else if (cur.right != null) {
                    Node min = findMin(cur.right, (dim+1)%k);
                    cur.point = min.point;
                    delete(cur.right, min, (dim+1)%k);
                    return root;
                } else { // i.e. cur.left != null
                    Node min = findMin(cur.left, (dim+1)%k);
                    cur.point = min.point;
                    Node leftSubtree = delete(cur.left, min, (dim+1)%k);
                    cur.left = null;
                    cur.right = leftSubtree;
                    return root;
                }
            }
            int key = cur.point[dim];
            if (node.point[dim] >= key) {
                if (cur.right == null) {
                    // not found
                    return root;
                } else {
                    prev = cur;
                    cur = cur.right;
                    dim = (dim+1) % k;
                }
            } else {
                if (cur.left == null) {
                    // not found
                    return root;
                } else {
                    prev = cur;
                    cur = cur.left;
                    dim = (dim+1) % k;
                }
            }
        }
    }

    static void testDelete() {
        Node root = new Node (new int[]{30, 40});
        insert(root, new Node (new int[]{5, 25}));
        insert(root, new Node (new int[]{10, 12}));
        insert(root, new Node (new int[]{70, 70}));
        insert(root, new Node (new int[]{50, 30}));
        insert(root, new Node (new int[]{35, 45}));

        root = delete(root, new Node(new int[] {30, 40}));
        printTree(root);

        root = delete(root, new Node(new int[] {70, 70}));
        printTree(root);
    }

    //====================================================================

    static Node findMin(Node root, int dimension) {
        return findMin(root, dimension, 0);
    }

    /**
     * If nodeDim = d, then the minimum can’t be in the right subtree,
     * so recurse on just the left subtree
     * If no left subtree, then current node is the min for tree rooted at this node.
     * If nodeDim ≠ d, then minimum could be in either current node, left subtree, right subtree
     *
     * So recurse on both subtrees (unlike in 1-d structures, often have to explore several paths down the tree)
     *
     */
    static Node findMin(Node node, int dimension, int nodeDim) {
        int k = node.point.length;
        if (dimension == nodeDim) {
            if (node.left == null) {
                return node;
            } else {
                return findMin(node.left, dimension, (nodeDim+1)%k);
            }
        } else {
            Node minNode = node;

            if (node.left != null) {
                Node leftMin = findMin(node.left, dimension, (nodeDim + 1)%k);
                if (leftMin.point[dimension] < minNode.point[dimension]) {
                    minNode = leftMin;
                }
            }
            if (node.right != null) {
                Node rightMin = findMin(node.right, dimension, (nodeDim + 1)%k);
                if (rightMin.point[dimension] < minNode.point[dimension]) {
                    minNode = rightMin;
                }
            }
            return minNode;
        }
    }

    static void testFindMin() {
        Node root = new Node (51, 75);
        insert(root, new Node (25, 40));
        insert(root, new Node (10, 30));
        insert(root, new Node (35, 90));
        insert(root, new Node (50, 50));
        insert(root, new Node (1, 10));
        insert(root, new Node (70, 70));
        insert(root, new Node (55, 1));
        insert(root, new Node (60, 80));

        System.out.println(findMin(root, 0));
        System.out.println(findMin(root, 1));
    }

    //====================================================================

    /**
     * Find a node in the tree that is nearest to a given location
     */

    static class NearestNeighbor {
        Node node;
        long squareDist;
        @Override
        public String toString() {
            return node + "/" + squareDist;
        }
    }

    static NearestNeighbor findNearestNeighbor(Node root, Node target) {
        NearestNeighbor nn = new NearestNeighbor();
        findNearestNeighbor(root, target, 0, nn);
        return nn;
    }

    static void findNearestNeighbor(Node root, Node target, int dim, NearestNeighbor nn) {
        System.out.println(root + " " + dim);
        if (root == null) {
            return;
        }

        long squareDist = root.getSquareDistance(target);
        if (nn.node == null || squareDist < nn.squareDist) {
            nn.node = root;
            nn.squareDist = squareDist;
        }

        if (nn.squareDist == 0) {
            return;
        }

        int k = root.point.length;
        int delta = root.point[dim] - target.point[dim];

        /** choose the space partition that the target could have belonged to (if inserted into the root tree)
         * it is the more potential subtree to get the nearest neighbor
         * so it is more likely for us to prune the worse subtree
         */
        Node betterSubtree = delta > 0 ? root.left : root.right;
        Node worseSubtree = delta > 0 ? root.right : root.left;

        if (betterSubtree != null) {
            findNearestNeighbor(betterSubtree, target, (dim + 1) % k, nn);
        }

        /**
         * The accurate minimum distance from target to the worse subtree partition
         * is the distance between the target and one of the 4 corners of the bounded box for that partition
         *
         * however, we can simplify the calculation of this min distance
         * as the distance from the target to the root plane, which is |delta|
         * thus here we compare delta-square with current min distance
         *
         * if the min distance to worse subtree > current min distance,
         * then we can prune the subtree
         *
         * Note that we should findNearestNeighbor in better subtree first
         * so we are more likely
         */
        if (delta * delta >= nn.squareDist) {
            return;
        }
        if (worseSubtree != null) {
            findNearestNeighbor(worseSubtree, target, (dim + 1) % k, nn);
        }
    }

    static void testNearestNeighbor() {
        Node root = new Node (51, 75);
        insert(root, new Node (25, 40));
        insert(root, new Node (10, 30));
        insert(root, new Node (35, 90));
        insert(root, new Node (50, 50));
        insert(root, new Node (1, 10));
        insert(root, new Node (70, 70));
        insert(root, new Node (55, 1));
        insert(root, new Node (60, 80));

        NearestNeighbor nn = findNearestNeighbor(root, new Node(52, 52));
        System.out.println(nn);
    }

    //====================================================================

    /**
     * Given a list of points,
     *
     */
    static class Point {
        int index;
        int[] value;

        Point(int index, int[] value) {
            this.index = index;
            this.value = value;
        }
    }
    static Node build(int[][] points) {
        int n = points.length;
        int k = points[0].length;

        List<Point> pointList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            pointList.add(new Point(i, points[i]));
        }

        int[][] ranks = new int[k][n];
        for (int i = 0; i < k; i++) {
            int sortDim = i;
            Collections.sort(pointList, Comparator.comparing(p -> p.value[sortDim]));
            for (int j = 0; j < n; j++) {
                ranks[i][pointList.get(j).index] = j;
            }
        }

        return insert(ranks, 0, IntStream.range(0, n).toArray(), pointList);
    }

    static Node insert(int[][] ranks, int dim, int[] indices, List<Point> pointList) {
        int n = indices.length;
        int k = ranks.length;

        int[] rank = new int[n];
        Map<Integer, Integer> rankToIndex = new HashMap<>();
        int index;

        for (int i = 0; i < n; i++) {
            index = indices[i];
            rank[i] = ranks[dim][index];
            rankToIndex.put(ranks[dim][index], index);
        }

        int medianIndex = rank[(n-1)/2];
        Node root = new Node(pointList.get(medianIndex).value);

        if (n>2) {
            int[] leftIndices = Arrays.copyOfRange(rank, 0, (n-1)/2);
            Node leftTree = insert(ranks, (dim+1)%k, leftIndices, pointList);
            root.left = leftTree;
        }

        if (n>1) {
            int[] rightIndices = Arrays.copyOfRange(rank, (n - 1) / 2 + 1, n);
            Node rightTree = insert(ranks, (dim + 1) % k, rightIndices, pointList);
            root.right = rightTree;
        }

        return root;
    }

    public static void main(String[] args) {
        int[][] points = new int[][]{ {1, 2}, {3, 4}, {1, 6}, {7, 8}, {2, 9}, {10, 5},
                {20, 13}, {9, 12}, {17, 2}, {4, 9}, {12, 15}, {15, 11}};
        Node root = build(points);
        KDTree.printTree(root);
    }
}
