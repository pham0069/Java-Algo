package data_structure.array;

import java.util.Arrays;

/**
 * https://www.geeksforgeeks.org/find-the-missing-number/
 *
 * You are given a list of n-1 integers and these integers are in the range of 1 to n. There are no duplicates in list.
 * One of the integers is missing in the list. Write an efficient code to find the missing integer.
 *
 * Method 1: Use sum formula
 * Time complexity is O(n). There can be overflow if n is large.
 * There can be overflow if n is large. In order to avoid Integer Overflow, we can pick one number from known numbers
 * and subtract one number from given numbers. This way we wont have Integer Overflow ever.
 *
 * Method 2: Use XOR
 * 1) XOR all the array elements, let the result of XOR be X1.
 * 2) XOR all numbers from 1 to n, let XOR be X2.
 * 3) XOR of X1 and X2 gives the missing number.
 */
public class MissingNumber {
    public static void main(String[] args) {
        int[] array = {1, 5, 2, 6, 7, 3};
        System.out.println(findMissingNumber(array));
        System.out.println(findMissingNumber2(array));
        System.out.println(findMissingNumber3(array));
    }
    static int findMissingNumber(int[] array) {
        int n = array.length+1;
        int total = n*(n+1)/2;
        int sum = Arrays.stream(array).sum();
        return total - sum;
    }

    /**
     * To avoid overflow, subtract same amount from each number in the array before calculating their sum
     * Let the subtraction amount be n/2
     * if n%2 == 0, subtraction = n/2, total = n*(n+1)/2 - n*n/2 = n/2
     * if n%2 == 1, subtraction =(n-1)/2, total = n*(n+1)/2 - n*n(-1)/2 = n
     */
    static int findMissingNumber2(int[] array) {
        int n = array.length+1;
        int subtraction  = n/2;
        int total = n%2==0 ? n/2: n;
        int sum = Arrays.stream(array)
                .map(i -> i - subtraction)
                .sum();
        return total-sum+subtraction;
    }

    static int findMissingNumber3(int[] array) {
        int n = array.length+1;
        int i, x1 = 0, x2 = 0;
        for (i = 0; i < n-1; i++)
            x1 = x1^array[i];

        for (i = 1; i <= n; i++)
            x2 = x2^i;

        return (x1^x2);
    }
}
