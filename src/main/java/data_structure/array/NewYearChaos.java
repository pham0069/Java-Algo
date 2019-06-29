package data_structure.array;

import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/new-year-chaos/problem?h_l=interview&playlist_slugs%5B%5D%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D%5B%5D=arrays
 * It is New Year's Day and people are in line for the Wonderland rollercoaster ride.
 * Each person wears a sticker indicating their initial position in the queue from 1 to n.
 * Any person can bribe the person directly in front of them to swap positions,
 * but they still wear their original sticker.
 * One person can bribe at most two others.
 *
 * Determine the minimum number of bribes that took place to get to a given queue order.
 * Print the number of bribes, or, if anyone has bribed more than two people, print Too chaotic.
 *
 * Example
 * q = [1, 2, 3, 5, 4, 6, 7, 8]
 *
 * If person 5 bribes person 4, the queue will look like above.
 * Only 1 bribe is required. Print 1.
 *
 * q = [4, 1, 2, 3]
 * Person 4 had to bribe 3 people to get to the current position. Print Too chaotic.
 *
 * Input Format
 *
 * The first line contains an integer , the number of test cases.
 *
 * Each of the next  pairs of lines are as follows:
 * - The first line contains an integer , the number of people in the queue
 * - The second line has  space-separated integers describing the final state of the queue.
 *
 * ==============================================================================================
 * Let say q = [1, 2, 5, 3, 7, 8, 6, 4]
 * How many swaps (i.e. bribes) needed to convert from original queue (1-8) to this state?
 * We can try backwards, i.e. convert from this queue to original order
 * This is equivalent to sorting. Bubble sort is very useful here.
 * For each index, if the number is not in place, we bubble swaps from its current position to the position it should be
 * For our queue,
 * - 1, 2 are in place
 * - 3 is not in place, need 1 swap with 5 to get in place
 * - 4 is not in place, need to swap with 6, then 8, then 7, then 5 to get in place
 * - and so on
 * Below is the backwards swap needed and the queue state after each swap
 *                          (Bribing, Bribed)
 * 1, 2, 5, 3, 7, 8, 6, 4
 *                          5, 3
 * 1, 2, 3, 5, 7, 8, 6, 4
 *                          6, 4
 * 1, 2, 3, 5, 7, 8, 4, 6
 *                          8, 4
 * 1, 2, 3, 5, 7, 4, 8, 6
 *                          7, 4
 * 1, 2, 3, 5, 4, 7, 8, 6
 *                          5, 4
 * 1, 2, 3, 4, 5, 7, 8, 6
 *                          8, 6
 * 1, 2, 3, 4, 5, 7, 6, 8
 *                          7, 6
 * 1, 2, 3, 4, 5, 6, 7, 8
 *
 *
 * Prove this bubble sort is optimal way to attain the current queue?
 *
 *
 * From this intuitive example, we can draw some observation
 * - (1) if a person is more than 2 spaces ahead of the position it start off, it has to bribe more than 2 times
 * in this case we can print too chaotic (5 bribed 2 times and end up in position 3)
 * - Note that a person can be any number of spaces behind its original position since it can get bribed any number of times
 * (4 has been bribed 4 times and end up in end of queue)
 * - (2) from the queue, we can quickly say that 5 has bribed 4 at some point, since it's originally behind 4
 * but currently in front of 4
 *
 * Generally inferred, person Q has bribed person P (and no chaotic) if
 * - Q > P
 * - Q's current position >= P's original position -1 (from (1))
 * - Q's current position <= P's current position -1 (from (2))
 *
 * In other words, that means P has received bribes from Q
 * if Q in the index's range of P's original position-1 and P's current position -1 and Q > P
 *
 * For e.g. person Q = 5 has bribed P = 4 -> Q's current position should be btw indexes 4-1 = 3 and  8-1 = 7
 *
 *
 * -> person Q has bribed those people P currently on its right, but originally on its left
 * For e.g. 5 has bribed 3 and 4, since 3, 4 < 5 but currently stays behind 5
 *
 * https://www.youtube.com/watch?v=gse_yUUVzvU
 *
 * Several approaches
 * 1. Do bubble sort from the back and keep track of the swaps states
 * Number of bribes is number of swaps
 * 2. For each value, count number of values on its right and smaller than it
 * Total number of such counts is number of bribes
 * This requires 2 loops, O(n^2), exceed time limit
 * 3. Alternative to 1, without tracing swaps???
 * 4. Based on the observation above, to count number of bribes a person received
 * We search from a person's original index -1 to a person's current index -1
 * instead of from 0 to its current index - 1
 */
public class NewYearChaos {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int t = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int tItr = 0; tItr < t; tItr++) {
            int n = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            int[] q = new int[n];

            String[] qItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int i = 0; i < n; i++) {
                int qItem = Integer.parseInt(qItems[i]);
                q[i] = qItem;
            }

            minimumBribes(q);
        }

        scanner.close();
    }

    // bubble sort to get the swap counts
    // would make effective modification to queue
    public static void minimumBribes(int[] queue) {
        int count = 0, originalPerson, temp;
        for (int i = queue.length-1; i>=0; i--) {
            // original person at index i
            originalPerson = i+1;
            if (queue[i] != originalPerson) {
                if (i-1>= 0 && queue[i-1] == originalPerson) {
                    count ++;
                    // swap queue[i] and queue[i-1]
                    queue[i-1] = queue[i];
                    //queue[i] = originalPerson; // queue[i] in place
                } else if (i-2>= 0 && queue[i-2] == originalPerson) {
                    count += 2;
                    // move queue[i-2] and [i-1] in front by 1
                    queue[i-2] = queue[i-1];
                    queue[i-1] = queue[i];
                    //queue[i] = originalPerson; // queue[i] in place
                } else {
                    System.out.println("Too chaotic");
                    return;
                }
            }
        }
        System.out.println(count);
    }

    // Brute force to count number on the right larger than it
    // 2 loops -> O(n^2) -> exceed time limit
    public static void minimumBribes2(int[] queue) {
        int count = 0;
        for (int i = 0; i < queue.length; i++) {
            if (queue[i] - (i+1) > 2) {
                System.out.println("Too chaotic");
                return;
            } else {
                // count number of bribes that person queue[i] has made
                for (int j = i+1; j < queue.length; j++) {
                    if (queue[i] > queue[j]) {
                        count ++;
                    }
                }
            }
        }
        System.out.println(count);
    }

    public static void minimumBribes3(int[] queue) {
        int count = 0;
        int p1 = 1, p2 = 2, p3 = 3;
        for (int i = 0; i < queue.length; i++) {
            if (queue[i] == p1) {
                p1 = p2;
                p2 = p3;
                p3 ++;
            } else if (queue[i] == p2){
                count ++;
                p2 = p3;
                p3 ++;
            } else if (queue[i] == p3) {
                count += 2;
                p3 ++;
            } else  {
                System.out.println("Too chaotic");
                return;
            }
        }
        System.out.println(count);
    }

    public static void minimumBribes4(int[] queue) {
        int count = 0, originalIndex, currentIndex;
        for (int i = 0; i < queue.length; i++) {
            // person queue[i]'s original index is queue[i]-1, current index is i
            originalIndex = queue[i]-1;
            currentIndex = i;
            if (originalIndex - currentIndex > 2) {
                System.out.println("Too chaotic");
                return;
            } else {
                // count number of bribes person queue[i] has received
                // only need to consider those index between (original index-1) and (current index -1)
                for (int j = Math.max(originalIndex-1, 0); j <= currentIndex-1; j++) {
                    if (queue[j] > queue[i]) {
                        count ++;
                    }
                }
            }
        }
        System.out.println(count);
    }
}
