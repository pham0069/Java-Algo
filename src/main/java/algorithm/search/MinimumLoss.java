package algorithm.search;

import java.io.IOException;
import java.util.NavigableSet;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * Lauren has a chart of distinct projected prices for a house over the next several years.
 * She must buy the house in one year and sell it in another, and she must do so at a loss.
 * She wants to minimize her financial loss.
 *
 * Example
 * price = [5, 10, 3]
 * Buy at 5, sell at 3, min loss = 2
 *
 * Find the max on the right < itself
 */
public class MinimumLoss {
//    public static void main(String[] args) {
//        System.out.println(minimumLoss(new long[] {5, 10, 3}));
//    }
    static long minimumLoss(long[] price) {
        NavigableSet<Long> right = new TreeSet<>();
        long minLoss = Long.MAX_VALUE;
        right.add(price[price.length-1]);
        for (int i = price.length - 2; i >= 0; i--) {
            Long lower = right.lower(price[i]);
            if (lower != null) {
                minLoss = Math.min(minLoss, price[i] - lower);
            }
            right.add(price[i]);
        }
        return minLoss;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        long[] price = new long[n];

        String[] priceItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < n; i++) {
            long priceItem = Long.parseLong(priceItems[i]);
            price[i] = priceItem;
        }

        System.out.println(minimumLoss(price));

        scanner.close();
    }

}
