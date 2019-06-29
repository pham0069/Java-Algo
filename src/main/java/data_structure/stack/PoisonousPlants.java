package data_structure.stack;

import java.util.Scanner;
import java.util.Stack;

/**
 * https://www.hackerrank.com/challenges/poisonous-plants/problem
 * There are n plants in a garden. Each of these plants has been added with some amount of pesticide. After each day, if
 * any plant has more pesticide than the plant on its left, i.e. being weaker than the left one, it dies. You are given
 * the initial values of the pesticide in each plant. Print the number of days after which no plant dies, i.e. the time
 * after which there are no plants with more pesticide content than the plant to their left.
 *
 * Input Format
 * The input contains of an integer n. The next line contains n integers, describing the array p where p_i denotes the
 * amount of pesticide in plant i.
 *
 * Constraints: 1<n<10^5; 1<p_i<10^9
 *
 * Output Format
 * Output an integer equal to the number of days after which no plants die.
 *
 * For example, pesticide levels p = [3, 6, 2, 7, 5]. Using a 1-indexed array, day 1 plants 2 and 4 die leaving p = [3, 2, 5].
 * On day 2, plant 3 of the current array dies leaving p =[3, 2]. As there is no plant with a higher concentration of
 * pesticide than the one to its left, plants stop dying after day 2.
 *
 * Sample Input
 *
 * 7
 * 6 5 8 4 7 10 9
 *
 * Sample Output
 *
 * 2
 * Explanation
 *
 * Initially all plants are alive.
 *
 * Plants = {(6,1), (5,2), (8,3), (4,4), (7,5), (10,6), (9,7)}
 *
 * Plants[k] = (i,j) => jth plant has pesticide amount = i.
 *
 * After the 1st day, 4 plants remain as plants 3, 5, and 6 die.
 *
 * Plants = {(6,1), (5,2), (4,4), (9,7)}
 *
 * After the 2nd day, 3 plants survive as plant 7 dies.
 *
 * Plants = {(6,1), (5,2), (4,4)}
 *
 * After the 2nd day the plants stop dying.
 *
 * =====================================================================================================================
 * complexity is O(n) since each element is removed at most once
 * Divide array into subsequences of consecutive decreasing order. Those elements in each subsequence will take time to die
 */
public class PoisonousPlants {
    static class Node{
        int value;
        int daysToDie;
        public Node(int v, int d){
            value = v;
            daysToDie = d;
        }
        public String toString(){
            return "(" + value + ", " + daysToDie + ")";
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] p = new int[n];

        Stack<Node> stack = new Stack<>();
        int days = 0, max = 0;
        for (int i = 0; i < n; i++){
            p[i] = sc.nextInt();
            if (i == 0){
                stack.push(new Node (p[i], 0));
            }
            else if (p[i] > p[i-1]){
                max = Math.max(max, 1);
                days = Math.max(days, max);
            }
            else {
                while (!stack.isEmpty()){
                    Node top = stack.peek();
                    if (top.value < p[i]){
                        stack.push(new Node(p[i], max +1));
                        days = Math.max(days, max+1);
                        max = 0;
                        break;
                    }
                    else {
                        Node pop = stack.pop();
                        max = Math.max(max, pop.daysToDie);
                    }
                }
                if (stack.isEmpty()){
                    stack.push(new Node(p[i], 0));
                    max = 0;
                }
            }
        }

        System.out.println(days);
    }
}
