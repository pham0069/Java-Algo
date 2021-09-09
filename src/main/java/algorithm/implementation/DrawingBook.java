package algorithm.implementation;

import java.io.IOException;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/drawing-book/problem
 *
 *
 */
public class DrawingBook {

    static int pageCount(int n, int p) {
        int fromFront = p/2;
        int fromBack = (n%2==0)?(n-p+1)/2 : (n-p)/2;
        return Math.min(fromFront, fromBack);
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])*");

        int p = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])*");

        int result = pageCount(n, p);

        System.out.println(result);

        scanner.close();
    }
}
