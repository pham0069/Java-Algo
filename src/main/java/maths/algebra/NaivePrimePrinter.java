package maths.algebra;

/**
 * Bernoulli's formula
 * Sigma (i^p) for i= 1..n = n^(p+1)/(p+1) + 1/2*n^p + Sigma Bk/k!*p^(k-1)&n^(p-k+1) for i=2..p
 * ---------------------------------------------------------------------------------------------------------------------
 * Problem
 * Print all prime number smaller or equal to a given number n
 * ---------------------------------------------------------------------------------------------------------------------
 * The most naive approach to check if n is prime number is checking if n is divisible by any number under n
 * To optimize, we only need to check if n is divisible by any number smaller or equal to square root of n
 * To print out all the primes under a number n using such check, the time complexity is
 * Sigma (1^0.5 + 2^0.5 + ... + n^0.5) = O(n^1.5) - according to Bernoulli's formula
 */
public class NaivePrimePrinter {
    public static void main(String[] args){
        naivePrimesPrint(100);
    }

    public static void naivePrimesPrint(int n){
        for (int i = 3; i < n; i++) {
            if (isPrime2(i))
                System.out.println(i);
        }
    }

    // Time complexity is O(sqrt(n))
    public static boolean isPrime(int n) {
        if (n <= 1)
            return false;
        for (int i = 2; i <= Math.sqrt(n); i++ ){
            if (n%i==0)
                return false;
        }
        return true;
    }
    // Time complexity is still O(sqrt(n)) but a bit faster
    // due to skipping factors divisible by 2 or 3
    public static boolean isPrime2(int n) {
        // Corner cases
        if (n <= 1)
            return false;
        if (n <= 3)
            return true;

        // This is checked so that we can skip 4 numbers in every 6 numbers per loop
        if (n % 2 == 0 || n % 3 == 0)
            return false;

        // Only need to consider odd number that does not divide by 3
        for (int i = 5; i * i <= n; i = i + 6)
            if (n % i == 0 || n % (i + 2) == 0)
                return false;

        return true;
    }


}
