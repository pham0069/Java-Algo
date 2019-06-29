package data_structure.hash;

/**
 * https://www.geeksforgeeks.org/find-pythagorean-triplet-in-an-unsorted-array/
 *
 * Given an array of integers, write a function that returns true if there is a triplet (a, b, c) that satisfies
 * a^2 = b^2 + c^2.
 *
 * We can solve this in O(n^2) time by sorting the array first.
 * 1) Do square of every element in input array. This step takes O(n) time.
 * 2) Sort the squared array in increasing order. This step takes O(nLogn) time.
 * 3) To find a triplet (a, b, c) such that a2 = b2 + c2, do following.
 * Fix ‘a’ as last element of sorted array.
 * Now search for pair (b, c) in subarray between first element and ‘a’. A pair (b, c) with given sum can be found in
 * O(n) time using meet in middle algorithm discussed in method 1 of this post.
 * If no pair found for current ‘a’, then move ‘a’ one position back and repeat step 3.2.

 */
public class PythagoreanTriplet {
    public static void main(String[] args) {
        int[] array = {2, 10, 3, 6, 4, 9};
    }
}
