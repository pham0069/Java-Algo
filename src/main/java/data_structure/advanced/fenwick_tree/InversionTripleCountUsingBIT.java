package data_structure.advanced.fenwick_tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static data_structure.advanced.fenwick_tree.InversionPairCountUsingBIT.Tuple;
/**
 * https://www.geeksforgeeks.org/count-inversions-of-size-three-in-a-give-array/
 *
 * Given an array[] of size n. Three elements at index i, j, k form an inversion triple
 * if a[i] > a[j] >a[k] and i < j < k. Find total number of inversions of size 3.
 * For example, input = {8, 4, 2, 1}
 * Output = 4 since there are four inversions (8,4,2), (8,4,1), (4,2,1) and (8,2,1).
 * Input = {9, 6, 4, 5, 8}
 * Output = 2 since there are two inversions {9, 6, 4} and {9, 6, 5}
 *
 * The naive solution is 3 loops, taking O(n^3)
 * We can improve the performance with the below observation:
 * Let say m(j) = number of index i such that a[i]>a[j] and i < j
 * n(j) = number of index k such that a[j]>a[k] and j < k
 * Number of triple inversion where middle value is a[j] is m(j)*n(j)
 * We can make use of 2 BITs to store m and n respectively
 * Time complexity is O(nlogn)
 */
public class InversionTripleCountUsingBIT {
    public static void main(String[] args){
        //int[] array = {9, 6, 4, 5, 8};
        int[] array = {8, 4, 2, 1};
        System.out.println(getInversionCount(array));
    }
    public static int getInversionCount(int[] array){
        List<Tuple> tuples = new ArrayList<>();
        for (int i = 0; i < array.length; i++){
            tuples.add(new Tuple(array[i], i));
        }
        Collections.sort(tuples);
        int[] smaller = getSmallerCount(tuples);
        int[] greater = getGreaterCount(tuples);
        int inversionCount = 0;
        for (int i = 0; i < array.length; i++){
            inversionCount += smaller[i]*greater[i];
        }
        return inversionCount;
    }

    private static int[] getSmallerCount(List<Tuple> sortedTuples) {
        int n = sortedTuples.size();
        int[] smaller = new int[n];
        FenwickTree bit = new FenwickTree(n);
        //add largest array element's index first
        //can exclude pairs with equal index from inversion count
        for (int i = 0; i < n; i++) {
            int tupleIndex = sortedTuples.get(i).index;
            smaller[i] = (int) bit.getSum(tupleIndex);
            bit.updateValueBy(tupleIndex, 1);
        }
        return smaller;
    }
    private static int[] getGreaterCount(List<Tuple> sortedTuples) {
        int n = sortedTuples.size();
        int[] greater = new int[n];
        FenwickTree bit = new FenwickTree(n);
        //add smallest array element's index first
        //can exclude pairs with equal index from inversion count
        for (int i = n-1; i >=0; i--) {
            int tupleIndex = sortedTuples.get(i).index;
            greater[i] = (int) (bit.getSum(n-1) - bit.getSum(tupleIndex));
            bit.updateValueBy(tupleIndex, 1);
        }
        return greater;
    }

}
