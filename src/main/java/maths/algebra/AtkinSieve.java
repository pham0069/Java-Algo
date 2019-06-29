package maths.algebra;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * https://www.geeksforgeeks.org/sieve-of-atkin/
 * Sieve of Atkin is modern algorithm to find all primes up to a specified integer n
 * Compared to ancient sieve of Erastosthenes, which marks multiples of primes,
 * it does some preliminary work and mark multiples of square of primes
 *
 * The algorithm treats 2, 3 and 5 as special cases and just adds them to the set of primes to start with.
 * Like Sieve of Eratosthenes, we start with a list of numbers we want to investigate. Suppose we want to
 * find primes <=100, then we make a list for [5, 100].
 * The algorithm talks in terms of modulo-60 remainders. .
 * 1. All numbers with modulo-sixty remainder 1, 13, 17, 29, 37, 41, 49, or 53 have a modulo-twelve remainder of 1 or 5.
 * These numbers are prime if and only if the number of solutions to 4×^2+y^2=n is odd and the number is squarefree.
 * A square free integer is one which is not divisible by any perfect square other than 1.
 * 2. All numbers with modulo-sixty remainder 7, 19, 31, or 43 have a modulo-twelve remainder of 7.
 * These numbers are prime if and only if the number of solutions to 3x^2 + y^2 = n is odd and the number is squarefree.
 * 3. All numbers with modulo-sixty remainder 11, 23, 47, or 59 have a modulo-twelve remainder of 11.
 * These numbers are prime if and only if the number of solutions to 3x^2 – y2 = n is odd and the number is squarefree.

 */
public class AtkinSieve {
    public static void main(String[] args){
        sieveOfAtkin(1000);
    }
    // Time complexity is O(n^0.5*log(n))
    // Space complexity is O(n)
    public static void sieveOfAtkin(int n){
        boolean[] prime = new boolean[n+1];
        int[] easyPrimes = {2, 3};
        Arrays.fill(prime, false);

        // Mark easy prime numbers
        Arrays.stream(easyPrimes).forEach(i -> {
            if (n>=i)
                prime[i] = true;
        });

        // Mark composite number
        for (int x = 1; x*x < n; x++) {
            for (int y = 1; y*y < n; y++) {
                int xSquare = x*x;
                int ySquare = y*y;

                int k = 4*xSquare + ySquare;
                if (k <= n && ((k%12) == 1 || (k%12) == 5))
                    prime[k] ^=true;

                k = 3*xSquare + ySquare;
                if (k <= n && (k%12) == 7)
                    prime[k] ^=true;

                k = 3*xSquare - ySquare;
                if (x> y && k <= n && (k%12) == 11)
                    prime[k] ^=true;

            }
        }

        // Mark all multiples of squares as non-prime
        for (int i = 5; i * i < n; i++) {
            if (prime[i]) {
                for (int j = i*i; j < n; j+=i*i)
                    prime[i] = false;
            }
        }

        // Print primes
        IntStream.rangeClosed(2, n)
                .filter(i -> prime[i])
                .forEach(i -> System.out.println(i));

    }

}
