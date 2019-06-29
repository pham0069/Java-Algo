package data_structure.advanced.implementation;

import java.util.Scanner;

/**
 * https://www.topcoder.com/community/data-science/data-science-tutorials/binary-indexed-trees/
 *
 * Fenwick Tree or Binary Indexed Tree is a data structure
 * to efficiently execute two types of queries on a numeric array
 * 1. Update: modify the value of array element
 * 2. Range-sum: sum of the array elements within a range
 *
 * Straightforward solution is to maintain array content. Update takes O(1)
 * but range-sum takes O(n) on average
 * Another naive solution is to maintain a sum array where sum[i] = sum of
 * array elements indexed from 0 to i. Range-sum could be done in O(1)
 * but update could take up to O(n).
 * Segment-tree can be used but Fenwick Tree is more space-efficient.
 *
 * Space complexity is O(n)
 * Time complexity to create the tree is O(nlogn)
 * Time complexity to update value is O(logn)
 * Time complexity to get range-sum is O(logn)
 *
 * Why BIT works? Any positive integers can be represented as sum of powers of 2
 * For example, 19 can be represented as 16+2+1. Every node of BIT stores sum of
 * n elements where n is a power of 2. Therefore updating or getting range-sum takes
 * only up to logn nodes
 *
 * For example, array = {3, 2, 0, 6, 5, -1, 2}
 * Naive approach is maintaining accumulative sum array = {3, 5, 5, 11, 16, 15, 17}
 *
 *
 */
public class FenwickTree {
    private int[] array;
    private long[] tree;
    public FenwickTree(int n){
        this.array = new int[n];
        this.tree = new long[n + 1];
    }

    public FenwickTree(int[] array){
        this.array = new int[array.length];
        this.tree = new long[array.length + 1];
        buildTree(array);
    }

    /**
     * Build tree array from input whose length is larger than input length by 1
     * tree[0] is dummy node, = 0; tree[i] is sum of array[parent(i)] to parent[i-1]
     *
     * Example: array = {3, 2, 0, 6, 5, -1, 2}
     * tree = {0, 3, 5, 0, 11, 5, 4, 2}
     */
    private void buildTree(int[] input){
        for (int i = 0; i < input.length; i++){
            updateValue(i, input[i]);
        }
    }

    /**
     * Get the sum of elements from array[0] to array[end] (both inclusive)
     * by summing the values of tree[end+1], tree[parent(end)], tree[grandparent(end)]...
     * all the way up to tree[0]
     */
    public long getSum(int end){
        if (end < 0 || end > array.length)
            throw new IndexOutOfBoundsException();
        int index = end + 1;
        long sum = 0;
        while (index > 0){
            sum += tree[index];
            index = getParentIndex(index);
        }
        return sum;
    }
    /**
     * Get the sum of elements from array[start] to array[end] (both inclusive)
     */
    public long getSum(int start, int end){
        if (end < start)
            throw new IndexOutOfBoundsException();
        if (start == 0)
            return getSum(end);
        return getSum(end) - getSum(start-1);
    }

    /**
     * Update the value of array[i] to the given new value
     * by adding the delta between the new and old value to all the
     * affected tree nodes, starting from (i + 1)
     */
    public void updateValue(int arrayIndex, int newValue){
        if (arrayIndex > array.length)
            throw new IndexOutOfBoundsException();
        int delta = newValue - array[arrayIndex];
        updateValueBy(arrayIndex, delta);
    }

    public void updateValueBy(int arrayIndex, int delta){
        if (arrayIndex > array.length)
            throw new IndexOutOfBoundsException();
        array[arrayIndex] += delta;
        int index = arrayIndex+1;
        while(index < tree.length){
            tree[index] += delta;
            index = getNextUpdateIndex(index);
        }
    }

    /**
     * Get parent index: flip the rightmost bit 1
     * in the binary representation of index
     * 1. 2's complement to get minus of index
     * 2. AND this with index
     * 3. Subtract that from index
     */
    private int getParentIndex(int index){
        return index - (index & -index);
    }

    /**
     * Get next index to update value:
     * 1. 2's complement to get minus of index
     * 2. AND this with index
     * 3. Add this to index
     */
    private int getNextUpdateIndex(int index){
        return index + (index & -index);
    }

    public static void main(String[] args){
        test();
    }
    public static void test(){
        int[]  array = {3, 2, 0, 6, 5, -1, 2};
        FenwickTree fenwickTree = new FenwickTree(array);
        assert 3 == fenwickTree.getSum(0);
        assert 5 == fenwickTree.getSum(1);
        assert 5 == fenwickTree.getSum(2);
        assert 11 == fenwickTree.getSum(3);
        assert 16 == fenwickTree.getSum(4);
        assert 15 == fenwickTree.getSum(5);
        assert 17 == fenwickTree.getSum(6);
        fenwickTree.updateValue(0, 5);
        assert 5 == fenwickTree.getSum(0);
        assert 7 == fenwickTree.getSum(1);
        assert 7 == fenwickTree.getSum(2);
        assert 13 == fenwickTree.getSum(3);
        assert 18 == fenwickTree.getSum(4);
        assert 17 == fenwickTree.getSum(5);
        assert 19 == fenwickTree.getSum(6);
    }

    public static void userInput(){
        System.out.println("Please enter array content: \n" +
                "number of elements, followed by array elements\n");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] array = new int[n];
        for (int i = 0; i < n; i++){
            array[i] = sc.nextInt();
        }
        FenwickTree fenwickTree = new FenwickTree(array);
        System.out.println("Please enter input: \n" +
                "U <index> <value> to update array value \n" +
                "S <start> <end> to get sum of elements from start to end \n" +
                "Q to quit");
        char choice;
        do {
            choice = sc.next().charAt(0);
            try {
                switch (choice) {
                    case 'U':
                        fenwickTree.updateValue(sc.nextInt(), sc.nextInt());
                        System.out.println("Updated");
                        break;
                    case 'S':
                        long sum = fenwickTree.getSum(sc.nextInt(), sc.nextInt());
                        System.out.println("Prefix sum is " + sum);
                }
            } catch(IndexOutOfBoundsException e){
                System.out.println("Invalid index input");
            }
        } while (choice!= 'Q');
    }
}
