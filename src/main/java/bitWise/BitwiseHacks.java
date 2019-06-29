package bitWise;

/**
 * https://www.geeksforgeeks.org/bitwise-hacks-for-competitive-programming/
 *
 */
public class BitwiseHacks {
    public static void main(String[] args) {
        System.out.println(isBitSet(21, 3));
    }

    /**
     * Set the bit at kth position in number n
     * k is zero-indexed based
     * 1 << k returns a binary number that has all bits 0, except the bit at kth position
     * When or (|) n with this number, all other bits are same as in n,
     * except the kth bit will be set (as 1 | anything = 1)
     * @param n
     * @param k
     */
    static int setBit(int n, int k) {
        return n | (1 << k);
    }

    /**
     * Clear the bit at kth position in number n
     * ~(1 << k) returns a binary number that has all bits 1, except the bit at kth position
     * When and (&) n with this number, all other bits are same as in n,
     * except the kth bit will be cleared (as 0 & anything = 0)
     * @param n
     * @param k
     */
    static int clearBit(int n, int k) {
        return n & (~(1 << k));
    }

    /**
     * Toggle the bit at kth position in number n
     * When xor (^) n  and 1<<k, all other bits are same as in n,
     * except the kth bit will be toggled (as 1 ^ anything = toggle of it)
     * @param n
     * @param k
     */
    static int toggleBit(int n, int k) {
        return n ^ (1 << k);
    }

    /**
     * Check if the kth bit of number n, is set or not
     * @param n
     * @param k
     * @return
     */
    static boolean isBitSet(int n, int k) {
        return (n & (1 << k)) != 0;
    }

    /**
     * Equivalent to invert every bit of a number
     * i.e. directly using not operator
     * Note that n + ~n = 1...1
     * @param n
     */
    static int get1sComplement(int n) {
        return ~n;
    }

    /**
     *  2’s complement of a number is 1’s complement + 1.
     *  It is equal to 1's complement + 1
     *  Also equal to -n
     * @param n
     * @return
     */
    static int get2sComplement(int n) {
        return -n;
    }

    /**
     * Useful in Binary indexed tree
     * n's binary representation is x...x10...0
     * n-1's binary representation is x...x01...1
     * When and these 2 numbers, we get x...x00...0, exactly what we want
     * @param n
     * @return
     */
    static int stripOffLowestSetBit(int n) {
        return n & (n-1);
    }

    /**
     * n's binary representation is x...x10...0
     * ~n's binary representation is x'...x'01...1
     * ~n+1 = x'...x'10...0
     * thus n & (~n+1) = 10...0
     * Note that ~n+1 = -n
     * @param n
     * @return
     */
    static int getLowestSetBit(int n) {
        return n & (-n);
    }
}
