package data_structure.queue;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * https://www.hackerrank.com/challenges/queries-with-fixed-length/problem
 *
 * Consider an n-integer sequence, A = {a[0], a[1],... a[n-1]}. We perform a query on A by using an integer, d, to
 * calculate the result of min(m[0], m[1], ..., m[n-d]) where m[i] = max(a[i], a[i+1]...a[i+d-1])
 * Given A and q queries (each query consists of an integer, d), print the result of each query on a new line.
 *
 * Sample Input 0
 5 5
 33 11 44 11 55
 1
 2
 3
 4
 5
 *
 * Sample Output 0
 11
 33
 44
 44
 55
 *
 * Explanation 0
 * For d = 1, the answer is min(max(a[0]), max(a[1]),...) = 11
 * For d = 2, the answer is min(max(a[0], a[1]), max(a[1], a[2]),...) = 33
 * For d = 3, the answer is min(max(a[0], a[1], a[2]), max(a[1], a[2], a[3]),...) = 44
 * For d = 4, the answer is min(max(a[0], a[1], a[2], a[3]), max(a[1], a[2], a[3]),a[4])) = 44
 * For d = 5, the answer is min(max(a[0], a[1], a[2], a[3], a[4])) = 55
 *
 * =====================================================================================================================
 * Approach
 * We can make use of MaximumOfAllKSizedSubarrays to get the maximum of all k-size windows in O(n)
 */
public class QueriesWithFixedLength {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int q = scanner.nextInt();
        int[] array = new int[n];
        IntStream.range(0, n).forEach(i -> array[i] = scanner.nextInt());
        while (q-- > 0) {
            int d = scanner.nextInt();
            printMinOfMaxKSizedWindow(array, d, n);

        }
    }

    static void printMinOfMaxKSizedWindow(int[] array, int k, int n) {
        /** deque stores indexes of array elements that are useful in every window
         * in a way such that array[deque[i]] >= array[deque[j]] for i < j
         */
        Deque<Integer> deque = new ArrayDeque<>(k);
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < k; ++i) {
            while ( (!deque.isEmpty()) && array[i] >= array[deque.getLast()])
                deque.removeLast();
            deque.addLast(i);
        }

        for (int i = k ; i < n; ++i) {
            // The element at the front of the queue is the largest element of previous window
            min = Math.min(min, array[deque.getFirst()]);
            // remove elements from the front outside current window, i.e. largest element of window should be head of queue
            while ( (!deque.isEmpty()) && deque.getFirst() <= i - k)
                deque.removeFirst();
            // remove elements from the rear smaller that current element
            while ( (!deque.isEmpty()) && array[i] >= array[deque.getLast()])
                deque.removeLast();
            deque.addLast(i);
        }

        // Print the maximum element of last window
        min = Math.min(min, array[deque.getFirst()]);
        System.out.println(min);
    }


}
