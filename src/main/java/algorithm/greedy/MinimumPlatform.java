package algorithm.greedy;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

/**
 * https://practice.geeksforgeeks.org/problems/minimum-platforms/0
 * https://www.geeksforgeeks.org/minimum-number-platforms-required-railwaybus-station/
 * https://www.techiedelight.com/minimum-number-of-platforms-needed-avoid-delay-arrival-train/
 * https://www.hackerrank.com/contests/kilobyte-uz-contest1/challenges/trains-and-platforms
 *
 *
 *
 * Given arrival and departure times of all trains that reach a railway station. Your task is to find the minimum number
 * of platforms required for the railway station so that no train waits.
 * Note: Consider that all the trains arrive on the same day and leave on the same day. Also, arrival and departure
 * times must not be same for a train.
 *
 * We are given two arrays which represent arrival and departure times of trains that stop
 *
 * Example 0 :
 * Input:  arr[]  = {9:00,  9:40, 9:50,  11:00, 15:00, 18:00}
 * dep[]  = {9:10, 12:00, 11:20, 11:30, 19:00, 20:00}
 * Output: 3
 * Explanation: There are at-most three trains at a time (time between 11:00 to 11:20)
 *
 * Sample Input:
 4
 6
 900 940 950 1100 1500 1800
 910 1200 1120 1130 1900 2000
 2
 1100 1102
 1101 1103
 4
 1105 1100 1101 1103
 1106 1110 1102 1104
 10
 900 1000 1100 1200 1300 1400 1500 1600 1700 1800
 910 1010 1110 1210 1310 1410 1510 1810 1710 1810
 * Sample Output:
 3
 1
 2
 2
 * ---------------------------------------------------------------------------------------------------------------------
 * Method 1: Sort arrival time and departure time
 * The idea is to merge arrival and departure time of trains and consider them in sorted order
 * We maintain a counter to count number of trains present at the station at any point of time. The counter also
 * represents the number of platforms needed at that time.
 * If train is scheduled to arrive next, we increase the counter by 1 and update minimum platforms needed if count is
 * more than minimum platforms needed so far
 * If train is scheduled to depart next, we decrease the counter by 1
 * Suppose two trains are scheduled to arrive and depart at the same time, we need two platforms to ensure their safety.
 * In that case, we arrive the train first. If the supposition is otherwise, i.e. we only need one platform, then we
 * depart the train first.
 * Time complexity is O(nlogn).
 *
 * This is greedy algorithm as we always try to schedule the earliest event first
 *
 * Method 2: Stack
 * Similar to find local minimum
 *
 */

public class MinimumPlatform {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        while (t-- > 0) {
            int n = sc.nextInt();
            int[] arrival = new int[n];
            int[] departure = new int[n];
            for (int i = 0; i < n; i++)
                arrival[i] = Integer.parseInt(sc.next());
            for (int i = 0; i < n; i++)
                departure[i] = Integer.parseInt(sc.next());
            //System.out.println(findMinPlatform(arrival, departure, n));
            System.out.println(findMinPlatform2(arrival, departure, n));
        }
    }

    static int findMinPlatform(int arrival[], int departure[], int n) {
        Arrays.sort(arrival);
        Arrays.sort(departure);
        int platforms = 0;
        int count = 0;//count how many platforms are required at each moment of arrival or depature
        int i = 0, j = 0;
        //run as if merging arrival and departure
        while (i < n) {
            //if train i is scheduled to arrive next
            //in case train i arrives at same time as train j departs, consider as i arrives first
            if (arrival[i] <= departure[j]) {
                platforms = Math.max(platforms, ++count);
                i++;
            }
            // if train j is schedule to depart next
            else {
                count--;
                j++;
            }
        }

        return platforms;
    }

    static int findMinPlatform2(int arrival[], int departure[], int n) {
        Arrays.sort(arrival);
        Arrays.sort(departure);
        Stack<Integer> stack = new Stack<>();
        int platforms = 0;
        int i = 0, j = 0;
        while (i < n && j < n) {
            if (arrival[i] <= departure[j]){
                stack.push(arrival[i]);
                i++;
            } else{
                stack.pop();
                j++;
            }
            platforms = Math.max(platforms, stack.size());
        }
        return platforms;
    }

}
