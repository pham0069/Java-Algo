package data_structure.hash;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * You are given an array composed of digits (0, 1..., 9)
 * Find the longest sub-array where you can re-arrange the
 * elements to form a palindrome
 *
 * Example. {1, 1, 2, 2}, {1, 1, 2, 2, 3} can form palindromes (1221, 12321)
 * {1, 1, 2, 3} cannot form any palindrome
 */

/**
 * A subarray can create a palindrome if each digit in the array has even count,
 * except at most 1 digit
 *
 * We can present the appearance frequency of digits at each array index as a BitSet
 * where the ith bit represents the count of digit i. Bit is set to 0 if number of digit
 * is even, and 1 otherwise
 * Example. {1, 1, 2, 2} has binary count = 0000000010 at index 0 (one digit 1), 0000000000
 * at index 1 (two digits 1)
 *
 * Two BitSets different from each other in at most 1 bit would have same oddity (both odd or
 * even) in the counts of all digits, except for at most 1 digit
 * If the binary counts at array index i and j satisfy such condition, the subarray from
 * index (i+1) to j can create a palindrome
 *
 */
public class LongestSubarrayWithPalindrome {
    public static final int NUMBER_OF_DIGITS = 10;
    public static final BitSet INIT_COUNT = new BitSet(NUMBER_OF_DIGITS);

    public static void main(String[] args){
        int[] array = {2, 2, 2, 1, 2, 1, 8};
        getLongestSubarray(array);
    }

    /**
     * Time complexity = O(n) to traverse array in 1 loop
     * Space complexity = O(n) to maintain map binaryCountToSmallestIndexMap
     */
    public static int getLongestSubarray(int[] array){
        BitSet prevBinaryCount = INIT_COUNT;
        Map<BitSet, Integer> binaryCountToSmallestIndexMap = new HashMap<>();
        binaryCountToSmallestIndexMap.put(INIT_COUNT, -1);
        int maxLength = 0, lowerIndex = 0, upperIndex = 0;

        for (int index = 0; index < array.length; index++){
            //update binaryCount[i] from binaryCount[i-1] by flipping bit array[i]
            BitSet currentBinaryCount = (BitSet) prevBinaryCount.clone();
            currentBinaryCount.flip(array[index]);
            prevBinaryCount = currentBinaryCount;

            //find the possible smallest palindrome index to get the longest palindrome's length
            List<BitSet> palindromeBitSets = getPalindromeBitSets(currentBinaryCount);
            for (BitSet binaryCount : palindromeBitSets){
                Integer smallestIndex = binaryCountToSmallestIndexMap.get(binaryCount);
                if (smallestIndex != null) {
                    int length = index - smallestIndex;
                    if (length > maxLength) {
                        maxLength = length;
                        lowerIndex = smallestIndex;
                        upperIndex = index;
                    }
                }
            }

            //put the smallest index of this binary count to the map
            binaryCountToSmallestIndexMap.putIfAbsent(currentBinaryCount, index);
        }

        System.out.println("Longest subarray that can form palindrome is from "
                            + (lowerIndex+1) + " to " + upperIndex);
        System.out.println("Longest palindrome length is " + maxLength);
        return maxLength;
    }

    /**
     * Get all the BitSets that are different from the given BitSet at most 1 bit.
     */
    private static List<BitSet> getPalindromeBitSets(BitSet bitSet){
        List<BitSet> palindromeBitSets = new ArrayList<>();
        palindromeBitSets.add(bitSet);
        for (int i = 0; i < NUMBER_OF_DIGITS; i++){
            BitSet flipAtOneBit = (BitSet) bitSet.clone();
            flipAtOneBit.flip(i);
            palindromeBitSets.add(flipAtOneBit);
        }
        return palindromeBitSets;
    }

}
