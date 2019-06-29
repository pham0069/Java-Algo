package maths.algebra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * https://www.geeksforgeeks.org/sieve-of-eratosthenes/
 * https://www.geeksforgeeks.org/segmented-sieve/
 *
 * Problem
 * Print all prime number smaller or equal to a given number n
 * ---------------------------------------------------------------------------------------------------------------------
 * Simple Eratosthenes sieve:
 * 1. Create a list of consecutive integers from 2 to n: (2, 3, 4, …, n).
 * 2. Initialize p to 2, the first prime number.
 * 3. If p is prime number, mark multiplier of p, i.e. 2p, 3p, ...(n/i)*p as composite
 * Note that some of the multipliers may have already been marked.
 * 4. The smallest number greater than p in the list that is not marked, is the next prime.
 * Repeat step 3.
 *
 * Time complexity to mark composite number is Sigma (n/i) for i is prime under sqrt of n
 * The sum is asymptotic to log(log n)
 * Time complexity to find next prime is O(n) (only traversing the list and check if item is already marked)
 * ---------------------------------------------------------------------------------------------------------------------
 * Segmented sieve:
 *
 * The Sieve of Eratosthenes looks good, but consider the situation when n is large, the Simple Sieve faces following
 * issues:
 * An array of size Θ(n) may not fit in memory
 * The simple Sieve is not cache friendly even for slightly bigger n. The algorithm traverses the array without
 * locality of reference ???
 *
 *
 * The idea of segmented sieve is to divide the range [0..n-1] in different segments and compute primes in all segments
 * one by one. This algorithm first uses Simple Sieve to find primes smaller than or equal to √(n).
 *
 * Segmented Sieve algorithm:
 * 1. Use Simple Sieve to find all primes upto square root of ‘n’ and store these primes in an array “prime[]”. Store
 * the found primes in an array ‘prime[]’.
 * 2. We need all primes in range [0..n-1]. We divide this range in different segments such that size of every segment
 * is at-most √n
 * 3. Do following for every segment [low..high]:
 * Create an array mark[high-low+1]. Here we need only O(x) space where x is number of elements in given range.
 * Iterate through all primes found in step 1. For every prime, mark its multiples in given range [low..high].
 *
 */

public class ErastosthenesSieve {
    public static void main(String[] args){
        simpleSieveOfEratosthenes(100);
        segmentedSieveOfEratosthenes(100);
    }
    // Time complexity is O(n^0.5*log(n))
    // Space complexity is O(n)
    public static void simpleSieveOfEratosthenes(int n){
        Boolean[] prime = new Boolean[n+1];

        // Mark composite number
        for (int i = 2; i <= n; i++) {
            if (prime[i] == null) {
                prime[i] = true;
                for (int j = 2; j <= n / i; j++)
                    prime[i * j] = false;
            }
        }
        IntStream.rangeClosed(2, n)
                .filter(i -> prime[i])
                .forEach(i -> System.out.println(i));
    }

    // Time complexity is O(n^0.5*log(log(n)))
    // Space complexity is O(n)
    public static void simpleSieveOfEratosthenes2(int n){
        boolean[] prime = new boolean[n+1];
        Arrays.fill(prime, 2, n+1,true);

        // Traverse i from 2 to sqrt(n) for optimisation.
        for (int i = 2; i <= n; i++) {
            if (prime[i] == true) {
                prime[i] = true;
                for (int j = 2; j <= n / i; j++)
                    prime[i * j] = false;
            }
        }
        IntStream.rangeClosed(2, n)
                .filter(i -> prime[i])
                .forEach(i -> System.out.println(i));
    }

    // Time complexity is O(n^0.5*log(log(n)))
    // Space complexity is O(n^0.5)
    public static void segmentedSieveOfEratosthenes(int n){
        List<Integer> allPrimes = new ArrayList<>();
        int segmentLen = (int) Math.round(Math.sqrt(n));
        Boolean[] localIsPrime;
        int low = 2, high = low + segmentLen;
        while (high <= n+1) {
            localIsPrime = new Boolean[high-low];
            // Mark composite numbers based on allPrimes in previous segments
            for (int prime : allPrimes) {
                markComposites(low, high, localIsPrime, prime);
            }

            // Find new prime, mark its composites, and add to allPrimes
            for (int i = 0; i < high-low; i++) {
                if (localIsPrime[i] == null) {
                    int prime = low + i;
                    markComposites(low, high, localIsPrime, prime);
                    allPrimes.add(prime);
                }
            }

            // Move to next segment
            if (high == (n+1))
                break;
            low = high;
            high = Math.min(high+segmentLen, n+1);
        }
        allPrimes.forEach(i -> System.out.println(i));
    }

    /**
     * Mark all the composites divisible by prime, from low inclusive to high exclusive
     * @param low lower bound
     * @param high upper bound
     * @param localIsPrime boolean array with length = high-low, localIsPrime[i] indicates if (low+i) is prime or not
     * @param prime prime number whose composite should be marked as not prime
     */
    private static void markComposites(int low, int high, Boolean[] localIsPrime, int prime) {
        int multiplier = (int) Math.ceil(1.0*low/prime) * prime;
        int firstComposite = multiplier;
        if (multiplier == prime && prime < high) {
            localIsPrime[prime-low] = true;
            firstComposite +=prime;
        }
        for (int composite = firstComposite ; composite < high; composite +=prime) {
            localIsPrime[composite - low] = false;
        }
    }
}
