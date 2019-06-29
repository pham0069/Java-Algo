package data_structure.advanced.segment_tree;

import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/largest-rectangle/problem
 *
 * Skyline Real Estate Developers is planning to demolish a number of old, unoccupied buildings
 * and construct a shopping mall in their place.
 * Your task is to find the largest solid area in which the mall can be constructed.
 *
 * There are a number of buildings in a certain two-dimensional landscape.
 * Each building has a height, given by h[i] where i = 1..n.
 * If you join k adjacent buildings, they will form a solid rectangle of area k*min(h[i], ... h[i+k-1])
 *
 * For example, the heights array h = [3, 2, 3].
 * A rectangle of height 2 and length 3 can be constructed within the boundaries.
 * The area formed is 2*3 = 6.
 *
 *
 * The idea is conquer and divide strategy.
 * Given a building range indexed from low-high, the max-area rectangle should:
 * 1. either include the min-height building: in this case, the rectangle area is maximized when we include all the
 * buildings in the range
 * 2. or exclude the min-height building indexed at min
 * in this case, we can recursively get the max-area rectangle
 * in smaller ranges low-(min-1) and (min+1)-high
 *
 * To support fast query of min value in any array range, we use min query segment tree
 */
public class LargestRectangleUsingSegmentTree {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] height = new int[n];
        for (int i = 0; i < n; i++){
            height[i] = sc.nextInt();
        }
        conquerAndDivide(height);

    }
    static void conquerAndDivide(int[] height){
        MinQuerySegmentTree segmentTree = new MinQuerySegmentTree(height);
        System.out.println(recursiveMaxRect(segmentTree, 0, height.length-1));
    }
    static int recursiveMaxRect(MinQuerySegmentTree tree, int low, int high){
        if (low > high)
            return 0;
        if (low == high)
            return tree.array[low];

        int min = tree.minIndex(low, high);
        int including = tree.array[min]*(high-low+1);
        int maxLeft = recursiveMaxRect(tree, low, min-1);
        int maxRight = recursiveMaxRect(tree, min+1, high);
        return Math.max(Math.max(maxLeft, maxRight), including);
    }

    static class MinQuerySegmentTree{
        int[] array;
        class Node{
            int data;
            int index;

            Node(){
                data = Integer.MAX_VALUE;
            }
            void duplicate(Node n){
                this.data = n.data;
                this.index = n.index;
            }
        }
        Node[] tree;

        MinQuerySegmentTree(int[] array){
            this.array = array;

            //find tree length
            int n = array.length;
            double log = Math.log(n)/Math.log(2);
            int nextPowOfTwo = (int) (Math.pow(2, Math.ceil(log)));
            int l = nextPowOfTwo*2-1;

            //init tree
            tree = new Node[l];
            initTree();
        }
        void initTree(){
            for (int i = 0; i < tree.length; i++){
                tree[i] = new Node();
            }
            recursiveConstruct(0, array.length-1, 0);
        }
        private void recursiveConstruct(int low, int high, int pos){
            if (low == high){
                tree[pos].data = array[low];
                tree[pos].index = low;
                return;
            }

            int mid = (low+high)/2;
            recursiveConstruct(low, mid, 2*pos+1);//build left
            recursiveConstruct(mid+1, high, 2*pos+2);// build right
            if (tree[2*pos+1].data < tree[2*pos+2].data){
                tree[pos].duplicate(tree[2*pos+1]);
            } else{
                tree[pos].duplicate(tree[2*pos+2]);
            }

        }

        int minIndex(int low, int high){
            return recursiveMin(low, high, 0, array.length-1, 0).index;
        }
        private Node recursiveMin(int low, int high, int tlow, int thigh, int pos){
            //note that if tlow==thigh, it can only be either no overlap or total overlap
            //no overlap
            if (low > thigh || high < tlow)
                return null;
            //total overlap
            if (low <= tlow && thigh <= high)
                return tree[pos];
            //partial overlap
            int mid = (tlow+thigh)/2;
            Node minLeft = recursiveMin(low, high, tlow, mid, 2*pos+1);
            Node minRight = recursiveMin(low, high, mid+1, thigh, 2*pos+2);
            if (minLeft == null)
                return minRight;
            if (minRight == null)
                return minLeft;
            if (minLeft.data < minRight.data){
                return minLeft;
            }
            else
                return minRight;
        }
    }
}

