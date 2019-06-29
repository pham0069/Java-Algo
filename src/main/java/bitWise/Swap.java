package bitWise;

/**
 * https://www.geeksforgeeks.org/swap-two-numbers-without-using-temporary-variable/
 * Problem with below methods:
 *
 * 1) The multiplication and division based approach doesn’t work if one of the numbers is 0
 * as the product becomes 0 irrespective of the other number.
 *
 * 2) Both Arithmetic solutions may cause arithmetic overflow.
 * If x and y are too large, addition and multiplication may go out of integer range.
 *
 * 3) When we use pointers to variable and make a function swap,
 * all of the methods fail when both pointers point to the same variable.
 * Let’s take a look what will happen in this case if both are pointing to the same variable.
 *
 * // Bitwise XOR based method
 * x = x ^ x; // x becomes 0
 * x = x ^ x; // x remains 0
 * x = x ^ x; // x remains 0
 *
 * // Arithmetic based method
 * x = x + x; // x becomes 2x
 * x = x – x; // x becomes 0
 * x = x – x; // x remains 0
 *
 * Since x, y point to same content, when x becomes 0, y also becomes 0
 * Run swapPitfall() for demo
 */
public class Swap {
    public static void main(String[] args) {
        int[] a = {10};
        swapPitfall(a, a);
        int[] b = {100};
        fixSwapPitfall(b, b);
    }
    static void arithmeticSwap(int x, int y) {
        x += y;
        y = x - y;
        x -= y;
    }

    static void arithmeticSwap2(int x, int y) {
        x = x * y;
        y = x / y;
        x = x / y;
    }

    static void xorSwap(int x, int y) {
        x ^= y;
        y ^= x;
        x ^= y;
    }

    /**
     * If xp and yp are same arrays, xp[0] changes -> yp[0] changes
     * As a result, xp[0] and yp[0] turns 0 ultimately
     * @param xp
     * @param yp
     */
    static void swapPitfall(int[] xp, int[] yp) {
        System.out.println("Before swaps " + xp[0] + " " + yp[0]);
        xp[0] = xp[0] ^ yp[0];
        yp[0] = xp[0] ^ yp[0];
        xp[0] = xp[0] ^ yp[0];
        System.out.println("After swaps " + xp[0] + " " + yp[0]);
    }

    static void fixSwapPitfall(int[] xp, int[] yp) {
        System.out.println("Before swaps " + xp[0] + " " + yp[0]);
        try {
            // check if two addresses are same
            if (xp == yp)
                return;
            xp[0] = xp[0] ^ yp[0];
            yp[0] = xp[0] ^ yp[0];
            xp[0] = xp[0] ^ yp[0];
        } finally {
            System.out.println("After swaps " + xp[0] + " " + yp[0]);
        }
    }
}
