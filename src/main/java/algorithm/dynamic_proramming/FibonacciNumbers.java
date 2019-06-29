package algorithm.dynamic_proramming;

/**
 * https://www.geeksforgeeks.org/program-for-nth-fibonacci-number/
 *
 * Fibonacci series is defined as below:
 * F0 = 1, F1 = 1
 * F(n) = F(n-1) + F(n-2) for n >= 2
 * Given a number n, calculate the nth Fibonacci number F(n)
 */
public class FibonacciNumbers {
    public static void main(String[] args){
        //System.out.println(getFibonacci1(12));
        //System.out.println(getFibonacci2(12));
        System.out.println(getFibonacci4(12));
        System.out.println(getFibonacci5(12));
    }

    //DP memoization
    private static final int MAX = 100;
    private static Integer[] fibs = new Integer[MAX];
    static{
        fibs[0] = 0;
        fibs[1] = 1;
    }
    public static int getFibonacci1(int n){
        if (fibs[n] == null) {
            fibs[n] = getFibonacci1(n - 1) + getFibonacci1(n - 2);
        }
        return fibs[n];
    }
    //DP tabulation
    public static int getFibonacci2(int n){
        int[] fibs = new int[n+2];
        fibs[0] = 0;
        fibs[1] = 1;
        for (int i = 2; i <= n; i++){
            fibs[i] = fibs[i-1] + fibs[i-2];
        }
        return fibs[n];
    }

    //DP tabulation with optimized space
    public static int getFibonacci3(int n){
        int a = 0, b = 1, c = 0;
        if (n == 0)
            return a;
        for (int i = 2; i <= n; i++){
            c = a + b;
            a = b;
            b = c;
        }
        return b;
    }


    /**
     * Based on the fact that if we n times multiply the matrix F = {{1,1},{1,0}} to itself (i.e. F^n),
     * then we get the (n+1)th Fibonacci number as the element at row and column (0, 0) in the result.
        | 1 1  |^ n =   | F(n+1)  F(n) |
        | 1 0  |        | F(n)   F(n-1)|
     * Time complexity is O(log n)
     */
    public static int getFibonacci4(int n){
        int f[][] = { {1, 1},
                      {1, 0} };
        if (n == 0)
            return 0;
        return power(f, n-1)[0][0];
    }
    private static int[][] multiply(int a[][], int b[][]) {
        int x =  a[0][0]*b[0][0] + a[0][1]*b[1][0];
        int y =  a[0][0]*b[0][1] + a[0][1]*b[1][1];
        int z =  a[1][0]*b[0][0] + a[1][1]*b[1][0];
        int w =  a[1][0]*b[0][1] + a[1][1]*b[1][1];
        return new int[][]{ {x, y}, {z, w} };
    }
    private static int[][] power(int a[][], int n) {
        if (n == 0)
            return new int[][] { {1, 0}, {1, 0} };
        if (n == 1)
            return a;
        int[][] b = power(a, n/2);
        int[][] square = multiply(b, b);
        if (n%2 == 0)
            return square;
        return multiply(a, square);
    }
    /**
     * We can use the following formula to calculate F(n):
     * If n is even, k = n/2:       F(n) = [2*F(k-1) + F(k)]*F(k)
     * If n is odd  k = (n + 1)/2:  F(n) = F(k)*F(k) + F(k-1)*F(k-1)
     */
    private static int getFibonacci5(int n) {
        if (fibs[n] != null)
            return fibs[n];
        int k = (n & 1) == 1? (n + 1) / 2 : n / 2;
        fibs[n] = (n & 1) == 1? (getFibonacci5(k) * getFibonacci5(k) + getFibonacci5(k - 1) * getFibonacci5(k - 1))
                                : (2 * getFibonacci5(k - 1) + getFibonacci5(k)) * getFibonacci5(k);
        return fibs[n];
    }
}
