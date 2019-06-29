package data_structure.queue;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/down-to-zero-ii/problem
 *
 * You are given Q queries. Each query consists of a single number N. You can perform any of the 2 operations on N in
 * each move:
 * 1: If we take 2 integers a and b where N = a x b, (a, b != 1), then we can change N = max(a, b)
 * 2: Decrease the value of N by 1.
 *
 * Determine the minimum number of moves required to reduce the value of N to 0.
 *
 * Sample Input
 2
 3
 4
 * Sample Output
 3
 3
 * Explanation
 * For test case 1, We only have one option that gives the minimum number of moves. Follow 3 -> 2 -> 2 -> 0. Hence, 3
 * moves.
 * For the case 2, we can either go 4 -> 3 -> 2 -> 1 -> 0 or 4 -> 2 -> 1 -> 0. The 2nd option is more optimal. Hence, 3
 * moves.
 *
 * 1 approach is to use dynamic programming. We process all number m from 1 to n, store min moves for each number m to
 * reduce to 0 (bottom-up). This works but may unnecessarily process some number m not in optimal path for n to reach 0.
 *
 * Another approach is BFS top-down starting from n instead. We use a queue to traverse all numbers reachable from n. We
 * maintain number of steps taken to reach this output in an array. Keep doing until 0 is reached.
 * For example, n = 12, queue initially = {12}
 * Dequeue 12, add numbers reachable from 12 in 1 step: 11 (op 2), 6, 4 (op 1)
 * Dequeue 10, add numbers reachable from 11 in 1 step: 10 (op 2)
 * Dequeue 6, add numbers reachable from 6 in 1 step: 5 (op 2), 3 (op 1)
 * Dequeue 4, add numbers reachable from 4 in 1 step: 3 not counted as already added to queue, 2 (op 1)
 * ... Keep doing until 0 is added to queue.
 */
public class DownToZero {
    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner sc = new Scanner(System.in);
        int q = sc.nextInt();

        for (int i = 0; i < q; i++){
            int n = sc.nextInt();
            System.out.println(downToZero(n));
        }
    }

    static int downToZero(int n){
        Deque<Integer> queue = new ArrayDeque<>();
        queue.add(n);
        int[] distance = new int[n+1];//distance[i] is number of steps taken to reduce from n to i
        while (!queue.isEmpty()){
            int top = queue.removeFirst();
            if (top == 0){
                break;
            }
            if (distance[top-1] == 0){
                distance[top-1] = distance[top]+1;
                queue.add(top-1);
            }
            for (int i = 2; i * i <= top; i++){
                if (top%i ==0){
                    int factor = top/i;
                    if (distance[factor]== 0){
                        distance[factor] = distance[top]+1;
                        queue.add(factor);
                    }
                }
            }

        }
        return distance[0];
    }
}
