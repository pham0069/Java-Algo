package data_structure.heap;

import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/find-the-running-median/problem
 *
 * The median of a set of integers is the midpoint value of the data set for which an equal number of integers are less
 * than and greater than the value. To find the median, you must first sort your set of integers in non-decreasing order,
 * then:
 *
 * If your set contains an odd number of elements, the median is the middle element of the sorted sample. In the sorted
 * set {1, 2, 3}, 2 is the median.
 * If your set contains an even number of elements, the median is the average of the two middle elements of the sorted
 * sample. In the sorted set {1, 2, 3, 4}, (2+3)/2 = 2.5 is the median.
 * Given an input stream of n integers, you must perform the following task for each i_th integer:
 *
 * 1. Add the i_th integer to a running list of integers.
 * 2. Find the median of the updated list (i.e., for the first element through the i_th element).
 * 3. Print the list's updated median on a new line. The printed value must be a double-precision number scaled to 1
 * decimal place (i.e., 12.3 format).
 * Input Format
 *
 * The first line contains a single integer, n, denoting the number of integers in the data stream.
 * Each line i of the n subsequent lines contains an integer, a_i, to be added to your list.
 *
 * Constraints
 * 1 <= n <= 10^5
 * 0 <= a_i <= 10^5
 *
 * Output Format
 *
 * After each new integer is added to the list, print the list's updated median on a new line as a single
 * double-precision number scaled to 1 decimal place (i.e., 12.3 format).
 *
 * Sample Input
 *
 * 6
 * 12
 * 4
 * 5
 * 3
 * 8
 * 7
 * Sample Output
 *
 * 12.0
 * 8.0
 * 5.0
 * 4.5
 * 5.0
 * 6.0
 * Explanation
 *
 * There are n = 6 integers, so we must print the new median on a new line as each integer is added to the list:
 * list = {12}, median = 12.0
 * list = {12, 4} -> median = (12+4)/2 = 8.0
 * list = {12, 4, 5} -> median = 5
 * list = {12, 4, 5, 3} -> median = (5+4)/2 = 4.5
 * list = {12, 4, 5, 3, 8} -> median = 5.0
 * list = {12, 4, 5, 3, 8, 7} -> median = (5+7)/2 = 6.0
 *
 * Use two heap, one to store max lower half, one to store min upper half
 * Median is or derived from the polled element of these 2 heaps
 */
public class FindTheRunningMedian {
    public static void main(String args[] ) throws Exception {
        useHeap();
    }

    static void useHeap() {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        PriorityQueue<Integer> maxLowerHeap = new PriorityQueue<>((n+2)/2, Collections.reverseOrder());
        PriorityQueue<Integer> minUpperHeap = new PriorityQueue<>();

        if (n<0){
            System.exit(0);
        }

        int[] a = new int[n];
        for (int i = 0; i < n; i++)
            a[i] = sc.nextInt();

        if (n==1){
            System.out.format("%.1f%n", a[0]/1.0);
            System.exit(0);
        }
        if (n>=2){
            System.out.format("%.1f%n", a[0]/1.0);
            System.out.format("%.1f%n", (a[0]+a[1])/2.0);
            maxLowerHeap.add(Math.min(a[0], a[1]));
            minUpperHeap.add(Math.max(a[0], a[1]));
        }
        for (int i = 2; i < n; i++){
            int maxLower = maxLowerHeap.peek();
            int minUpper = minUpperHeap.peek();

            //select the heap to add a[i]
            if (a[i] < maxLower){
                maxLowerHeap.add(a[i]);
            } else if (a[i] > minUpper){
                minUpperHeap.add(a[i]);
            } else{
                if (maxLowerHeap.size() <= minUpperHeap.size())
                    maxLowerHeap.add(a[i]);
                else
                    minUpperHeap.add(a[i]);
            }

            //adjust the heap size
            if (maxLowerHeap.size() - minUpperHeap.size()>1){
                minUpperHeap.add(maxLowerHeap.poll());
            }
            else if (minUpperHeap.size() - maxLowerHeap.size()>1){
                maxLowerHeap.add(minUpperHeap.poll());
            }

            //get the median
            maxLower = maxLowerHeap.peek();
            minUpper = minUpperHeap.peek();
            if (i%2 ==1){//even total number
                System.out.format("%.1f%n", (maxLower+minUpper)/2.0);
            }
            else{//odd total number, choose the median in the bigger heap
                if (maxLowerHeap.size() > minUpperHeap.size())
                    System.out.format("%.1f%n", maxLower/1.0);
                else
                    System.out.format("%.1f%n", minUpper/1.0);
            }
        }
    }
}
