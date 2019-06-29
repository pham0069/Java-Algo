package algorithm.sort3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/big-sorting/problem
 *
 * Intereesting test case 6 fails in Java 8 due to timeout
 * Even if we dont do sorting (just simply return array in bigSorting()), it still happens
 * What we can do is changing scanner.nextLine() to next()
 *
 * unsorted = ['1', '200', '150', '3'].
 * Return the array ['1', '3', '150', '200'].
 *
 * Each string is a positive number with anywhere from 1 to 10^6 digits
 * Each string is guaranteed to represent a positive integer.
 * There will be no leading zeros.
 *
 */
public class BigSorting {
    public static String[] bigSorting(String[] array) {
        Comparator<String> comparator = (x, y) -> {
            return x.length() == y.length() ?
                    x.compareTo(y) :
                    x.length() - y.length();
        };
        //comparator = new BigComparator();
        Arrays.sort(array, comparator);
        return array;
    }
    static class BigComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            int lenComp = o1.length() - o2.length();
            if (lenComp != 0) {
                return lenComp;
            }

            /*
            for (int i = 0; i < o1.length(); i ++) {
                int charComp = o1.charAt(i) - o2.charAt(i);
                if (charComp != 0) {
                    return charComp;
                }
            }
            return 0;*/
            return o1.compareTo(o2);
        }
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        String[] unsorted = new String[n];

        for (int i = 0; i < n; i++) {
            String unsortedItem = scanner.nextLine(); // --> scanner.next()
            unsorted[i] = unsortedItem;
        }

        String[] result = bigSorting(unsorted);

        for (int i = 0; i < result.length; i++) {
            bufferedWriter.write(result[i]);

            if (i != result.length - 1) {
                bufferedWriter.write("\n");
            }
        }

        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
