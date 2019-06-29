package data_structure.advanced.fenwick_tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Inversion count for an array indicates – how far (or close) the array is
 * from being sorted. If array is already sorted then inversion count is 0.
 * If array is sorted in reverse order that inversion count is the maximum.
 *
 * Two elements a[i] and a[j] form an inversion if a[i] > a[j] and i < j.
 * For simplicity, we may assume that all elements are unique.
 *
 * For example, Input arr[] = {8, 4, 2, 1}
 * Output: 6 as there are six inversions (8,4), (4,2), (8,2), (8,1), (4,1), (2,1).
 *
 *
 * Imagine we have a frequency array where frequency[i] is the number of elements
 * in array input whose value is equal to i
 * number of array elements that are smaller than i is range sum from 0 to (i-1)
 *
 * Given array[j], if we only store the frequency of array elements indexed after j,
 * then the range sum is the inversion count associated with array[j]
 * To get the total inversion count, we can get sum and update the frequency array
 * using Fenwick tree.
 * Time complexity is O(nlog(max)). Space complexity is O(max).
 * This only works for positive-element array and is space-consuming for high max value
 *
 * To improve this solution, we can convert the array value to its (ascending) rank (1..n).
 * For example {8, 4, 2, 1} --> {4, 3, 2, 1}
 * In this way, time complexity is O(nlogn) and space complexity is O(n)
 *
 * Better solution is to sort the array in descending order and add their original index
 * to BIT. Time complexity is also O(nlogn) and space complexity is O(n) and no rank conversion
 * is not needed.
 */
public class InversionPairCountUsingBIT {
    public static void main(String[] args){
        int[] array = {12, 10, 8, 4, 2, 1};
        //System.out.println(getInversionCount(array));
        //System.out.println(getInversionCount2(array));
        System.out.println(getInversionCount3(array));
    }

    /**
     * works for any numeric array and no need to convert to rank
     */
    private static int getInversionCount3(int array[]) {
        List<Tuple> tuples = new ArrayList<>();
        int n = array.length;
        for (int i = 0; i < n; i++){
            tuples.add(new Tuple(array[i], i));
        }
        Collections.sort(tuples);
        int inversionCount = 0;
        FenwickTree bit = new FenwickTree(n);
        for (int i = 0; i < tuples.size(); i ++){
            int tupleIndex = tuples.get(i).index;
            inversionCount += bit.getSum(tupleIndex);
            bit.updateValueBy(tupleIndex, 1);
        }
        return inversionCount;
    }

    /**
     * works for any numeric array
     */
    private static int getInversionCount2(int array[]) {
        return getInversionCount1(convertToRank(array));
    }

    private static int[] convertToRank(int[] array){
        if (array == null || array.length == 0)
            return array;
        Map<Integer, Integer> rankMap = getRankMap(array);
        int[] rank = new int[array.length];
        for (int i = 0; i < array.length; i++){
            rank[i] = rankMap.get(array[i]);
        }
        return rank;
    }

    private static Map<Integer, Integer> getRankMap(int[] array){
        int[] copy = Arrays.copyOf(array, array.length);
        Arrays.sort(copy);
        Map<Integer, Integer> rankMap = new HashMap<>();
        int rank = 1, index = 0, prev = copy[0];
        while (index < copy.length){
            if (copy[index] != prev)
                rank = index+1;
            rankMap.put(copy[index], rank);
            index++;
        }
        return rankMap;
    }

    /**
     * only works for positive-element array
     */
    private static int getInversionCount1(int array[]) {
        int inversionCount = 0;
        int max = getMax(array);

        FenwickTree bit = new FenwickTree(max+1);
        for (int i=array.length-1; i>=0; i--) {
            inversionCount += bit.getSum(array[i]-1);
            bit.updateValueBy(array[i], 1);
        }
        return inversionCount;
    }

    private static int getMax(final int[] array){
        int max = 0;
        for (int i=0; i< array.length; i++)
            if (max < array[i])
                max = array[i];
        return max;
    }

    public static class Tuple implements Comparable<Tuple>{
        int value;
        int index;
        public Tuple(int value, int index){
            this.value = value;
            this.index = index;
        }
        public int compareTo(Tuple that){
            if (that == null)
                return 1;
            if (this.value != that.value)
                return that.value - this.value;
            return that.index - this.index;
        }
    }
}
