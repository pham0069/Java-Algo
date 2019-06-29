package algorithm.dynamic_proramming;

/**
 * https://www.geeksforgeeks.org/ugly-numbers/
 * Ugly numbers are numbers whose only prime factors are 2, 3 or 5. The sequence 1, 2, 3, 4, 5, 6, 8, 9, 10, 12, 15, …
 * shows the first 11 ugly numbers. By convention, 1 is included.
 * Given a number n, the task is to find n’th Ugly number.
 */
public class UglyNumbers {
    private static int removeFactor(int a, int b) {
        while(a % b == 0)
            a = a/b;
        return a;
    }
    private static int isUgly(int n) {
        n = removeFactor(n, 2);
        n = removeFactor(n, 3);
        n = removeFactor(n, 5);

        return (n == 1)? 1 : 0;
    }
     /**
      * Traverse all the numbers starting from 1, one by one until we get n ugly numbers
      * To check if a number is ugly, divide number by 2, 3, 5 until the remaining is not divisible
      * by 2, 3, 5 any more. If the result is 1, that number is ugly.
      * Time complexity is O(U) where U is the nth ugly number and space complexity is O(1)
      */
    private static int getUgly(int n) {
        int number = 1;
        int count = 1;
        while (count < n) {
            number++;
            if(isUgly(number) == 1)
                count++;
        }
        return number;
    }

    /**
     * If an ugly number is divisible by 2, then ugly number divides by 2 is another ugly number.
     * Similarly for 3 and 5. Thus we can divide the ugly numbers in 3 groups:
     (1) 1×2, 2×2, 3×2, 4×2, 5×2, 6x2, 8x2, 9x2, 10x2, 12x2, 15x2…
     (2) 1×3, 2×3, 3×3, 4×3, 5×3, 6x3, 8x3, 9x3, 10x3, 12x3, 15x3… …
     (3) 1×5, 2×5, 3×5, 4×5, 5×5, 6x5, 8x5, 9x5, 10x5, 12x5, 15x5… …
     * We can find that every subsequence is the ugly-sequence itself (1, 2, 3, 4, 5, …) multiply 2, 3, 5.
     * Then we use similar merge method as merge sort, to get every ugly number from the three subsequence.
     * Every step we choose the smallest one, and move one step after.
     * Time complexity is O(n) and space complexity is O(n)
     */
    private static int getUglyDP(int n) {
        int[] uglies = new int[n];
        int i2 = 0, i3 = 0, i5 = 0;
        int nextMultipleOfTwo = 2;
        int nextMultipleOfThree = 3;
        int nextMultipleOfFive = 5;
        int nextUgly = 1;

        for(int i = 1; i < n; i++) {
            nextUgly = Math.min(nextMultipleOfTwo,
                                Math.min(nextMultipleOfThree, nextMultipleOfFive));
            uglies[i] = nextUgly;
            if (nextUgly == nextMultipleOfTwo) {
                nextMultipleOfTwo = uglies[++i2]*2;
            }
            if (nextUgly == nextMultipleOfThree) {
                nextMultipleOfThree = uglies[++i3]*3;
            }
            if (nextUgly == nextMultipleOfFive) {
                nextMultipleOfFive = uglies[++i5]*5;
            }
        }
        return uglies[n-1];
    }

    public static void main(String args[]) {
        int n = 155;
        System.out.println(getUgly(n));
        System.out.println(getUglyDP(n));
    }
}
