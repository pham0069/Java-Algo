package data_structure.hash;

import java.util.HashMap;
import java.util.Map;

/**
 * https://www.geeksforgeeks.org/largest-subarray-with-equal-number-of-0s-and-1s/
 *
 * You are given an array containing only 0s and 1s.
 * Find the largest subarray which contain equal number of 0s and 1s.
 *
 * Examples:
 * Input: arr[] = {1, 0, 1, 1, 1, 0, 0}
 * Output: 1 to 6 (Starting and Ending indexes of output subarray)
 * Input: arr[] = {1, 1, 1, 1}
 * Output: No such subarray
 * Input: arr[] = {0, 0, 1, 1, 0}
 * Output: 0 to 3 Or 1 to 4
 *
 *
 * If you convert all 0s into -1s, the subarray with equal numbers of -1s
 * and 1s will have sum of all elements as 0
 *
 * Let sum(i) be the sum of all elements from the beginning of the
 * array to the element at index i. If sum(i) = sum(j), thesubarray
 * from (i+1) to j would have the elements' sum of zero.
 *
 * To get the longest subarray, we need to find i and j to maximize (j-i)
 */
public class LargestSubarrayWithEqualNumberOfZerosAndOnes {
    public static int INIT_PREFIX_SUM = 0;
    public static void main(String[] args){
        int[] array = {0, 1, 1, 0, 0, 0, 1};
        getLongestSubarray(array);
    }
    /**
     * Time complexity = O(n) to traverse array in 1 loop
     * Space complexity = O(n) to maintain map binaryCountToSmallestIndexMap
     */
    public static int getLongestSubarray(int[] array){
        convertZerosToMinusOnes(array);
        Map<Integer, Integer> prefixSumToSmallestIndexMap = new HashMap<>();
        int prefixSum = INIT_PREFIX_SUM;
        prefixSumToSmallestIndexMap.put(0, -1);
        int maxLength = 0, lowerIndex = 0, upperIndex = 0;

        for (int index = 0; index < array.length; index++){
            //update prefixSum[i] from prefixSum[i-1] by adding array[i]
            prefixSum += array[index];

            //find the possible smallest same-prefixsum index to get the max length
            Integer smallestIndex = prefixSumToSmallestIndexMap.get(prefixSum);
            if (smallestIndex != null){
                int length = index - smallestIndex;
                if (length > maxLength) {
                    maxLength = length;
                    lowerIndex = smallestIndex;
                    upperIndex = index;
                }
            }

            //put the smallest index of this binary count to the map
            prefixSumToSmallestIndexMap.putIfAbsent(prefixSum, index);
        }

        System.out.println("Longest subarray with equal number of 0s and 1s is from "
                + (lowerIndex+1) + " to " + upperIndex);
        System.out.println("Longest subarray length is " + maxLength);
        return maxLength;
    }

    private static void convertZerosToMinusOnes(int[] array){
        for (int i = 0; i < array.length; i++){
            if (array[i] == 0)
                array[i] = -1;
        }
    }
}
