package algorithm.implementation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * https://www.hackerrank.com/challenges/sock-merchant/problem
 *
 */
public class SalesByMatch {
    // Complete the sockMerchant function below.
    static int sockMerchant(int n, int[] ar) {
        Set<Integer> unPair = new HashSet<>();
        int numPairs = 0;
        for (int i : ar) {
            if (unPair.contains(i)) {
                unPair.remove(i);
                numPairs ++;
            } else {
                unPair.add(i);
            }
        }
        return numPairs;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int[] ar = new int[n];

        String[] arItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < n; i++) {
            int arItem = Integer.parseInt(arItems[i]);
            ar[i] = arItem;
        }

        int result = sockMerchant(n, ar);

        System.out.println(result);
        scanner.close();
    }
}
