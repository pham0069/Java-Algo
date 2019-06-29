package algorithm.sort3;

import java.io.IOException;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/fraudulent-activity-notifications/problem
 *
 * HackerLand National Bank has a simple policy for warning clients about possible fraudulent account activity.
 * If the amount spent by a client on a particular day is greater than or equal to 2 times the client's median spending
 * for a trailing number of days, they send the client a notification about potential fraud.
 * The bank doesn't send the client any notifications until they have at least that trailing number of prior days' transaction data.
 *
 * Given the number of trailing days d and a client's total daily expenditures for a period of n days,
 * find and print the number of times the client will receive a notification over all n days
 *
 * For example,
 * d = 3
 * expenditures = [10, 20, 30, 40, 50]
 * On the first three days, they just collect spending data.
 * On day 4, we have trailing expenditures of [10, 20, 30]. The median is 20 and the day's expenditure is 40.
 * Because 40 >= 20x2, there will be a notice.
 * On day 5, our trailing expenditures are [20, 30, 40] and the day's expenditure is 50.
 * This is less 30x2 so no notice will be sent.
 * Over the period, there was one notice sent.
 *
 *
 * Constraints
 * 1 <= n <= 2*10^5
 * 1 <= d <= n
 * 0<= expenditure[i] <= 200
 *
 * Some approaches
 * 1. Use countSort[201] due to expenditure is limited to 200
 * Each median check is O(201) which is constant
 *
 * Note that d <=n -> if use loop O(d) for each median check (i.e. O(n))
 * The total cost could become O(n^2), leading to time exceed limit
 *
 * 2. Use 2 priority queues to maintain the median value of a window
 * Each median check takes O(1) but requires O(log d) to maintain the queue
 *
 * 3. Use a sorted link list to maintain sorted expenditure for each window of d days
 * When we move to the next day, we use binary search to find the value to remove
 * and also find the position to add the next expenditure
 * Each median check also takes O(1) but requires O(log d) to maintain the sorting
 *
 */
public class FraudulentActivityNotifications {

    static class MedianCounter {
        PriorityQueue<Integer> lower = new PriorityQueue<>(Comparator.reverseOrder());
        PriorityQueue<Integer> upper = new PriorityQueue<>();

        Integer getTwiceMedian() {
            if (lower.isEmpty()) {
                return null;
            }
            if (lower.size() > upper.size()) {
                return lower.peek()*2;
            } else {
                return lower.peek() + upper.peek();
            }
        }

        void insert (int n) {
            if (lower.isEmpty() || upper.isEmpty()) {
                lower.add(n);
            } else if (lower.peek() >= n) {
                lower.add(n);
            } else if (upper.peek() <= n) {
                upper.add(n);
            // median low < n < median high, i.e. n should take over the top of either queue
            // pick the queue based on queue size to maintain balance
            } else if (lower.size() > upper.size()) {
                upper.add(n);
            } else {
                lower.add(n);
            }

            adjust();
        }

        void remove (int n) {
            if (!lower.isEmpty() && lower.peek() >= n) {
                lower.remove(n);
            } else if (!upper.isEmpty() && upper.peek() <= n) {
                upper.remove(n);
            }

            adjust();
        }

        void adjust() {
            if (lower.size() < upper.size()) {
                //move the top of upper to lower
                int n = upper.remove();
                lower.add(n);
            } else if (lower.size() >= upper.size()+2) {
                int n = lower.remove();
                upper.add(n);
            }
        }
    }

    // Complete the activityNotifications function below.
    static int activityNotifications2(int[] expenditure, int d) {
        MedianCounter counter = new MedianCounter();
        for (int i = 0; i < d; i++) {
            counter.insert(expenditure[i]);
        }
        int notifications = 0;
        for (int i = d; i < expenditure.length; i++) {
            if (expenditure[i] >= counter.getTwiceMedian()) {
                notifications++;
            }

            counter.remove(expenditure[i - d]);
            counter.insert(expenditure[i]);
        }

        return notifications;
    }

    static int getTwiceMedian(int[] freq, int d) {
        int count = 0;
        for (int i = 0; i < freq.length; i++) {
            if (freq[i] > 0) {
                count += freq[i];
                if (count >= (d+1)/2) {
                    if (d %2 == 1) {
                        return i*2;
                    } else {
                        if (count < (d+1)/2+1) {
                            int j =i+1;
                            while (freq[j] == 0) {
                                j++;
                            }
                            return i+j;
                        } else {
                            return i*2;
                        }
                    }
                }
            }
        }
        return 0;
    }

    static int activityNotifications(int[] expenditure, int d) {
        int[] freq = new int[201];
        for (int i = 0; i < d; i++) {
            freq[expenditure[i]] ++;
        }
        int notifications = 0;
        for (int i = d; i < expenditure.length; i++) {
            if (expenditure[i] >= getTwiceMedian(freq, d)) {
                notifications++;
            }

            freq[expenditure[i - d]]--;
            freq[expenditure[i]]++;
        }

        return notifications;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        String[] nd = scanner.nextLine().split(" ");

        int n = Integer.parseInt(nd[0]);

        int d = Integer.parseInt(nd[1]);

        int[] expenditure = new int[n];

        String[] expenditureItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < n; i++) {
            int expenditureItem = Integer.parseInt(expenditureItems[i]);
            expenditure[i] = expenditureItem;
        }

        int result = activityNotifications(expenditure, d);

        System.out.println(result);

        scanner.close();

    }

    static void testMedianCounter() {
        MedianCounter counter = new MedianCounter();
        counter.insert(4);
        counter.insert(5);
        counter.insert(100);
        counter.insert(8);
        counter.insert(2);
        counter.insert(43);
        counter.insert(29);
        counter.insert(64);
        System.out.println(counter.getTwiceMedian());

    }
}
