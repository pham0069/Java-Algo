package data_structure.advanced.fenwick_tree;

import java.util.Scanner;

/**
 * https://www.geeksforgeeks.org/binary-indexed-tree-range-updates-point-queries/
 *
 * Given an array arr[0..n-1]. There are two operations to be performed on the array
 * 1. UPDATE l r val : Add ‘val’ to all the elements in the array from [l, r].
 * 2. GET i : Find element in the array indexed at ‘i’.
 * Initially all the elements in the array are 0.
 * Queries can be in any order, i.e., there can be many updates before point query.
 *
 * Naive method: update all elements in the range upon decrease operation
 * Time complexity for an update is O(n)
 * Time complexity for a get is O(1)
 *
 * Second method is to update only the cumulative difference at index l and r+1.
 * Array element at index i is sum of all cumulative difference from 0 to i.
 * See ArrayManipulation class for more explanations
 * Time complexity for an update is O(1)
 * Time complexity for a get is O(n)
 *
 * We can use BIT to implement the second method to balance the time complexity of both update and get queries
 * Time complexity for an update is 2*logn = O(logn)
 * Time complexity for a get is O(logn)
 *
 */
public class RangeUpdateAndPointQuery {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        try{
            int n = sc.nextInt();
            FenwickTree cumulativeDiff = new FenwickTree(n);
            int m = sc.nextInt();
            for (int i = 0; i < m; i++) {
                String op = sc.next();
                if (op.equals("UPDATE")){
                    int l = sc.nextInt();
                    int r = sc.nextInt();
                    int val = sc.nextInt();
                    cumulativeDiff.updateValueBy(l, val);
                    cumulativeDiff.updateValueBy(r+1, -val);
                }
                else if (op.equals("GET")){
                    int index = sc.nextInt();
                    long element = cumulativeDiff.getSum(index);
                    System.out.println(element);
                }
            }
        }
        catch(Exception e){}
    }
}
