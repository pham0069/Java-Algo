package algorithm.dynamic_proramming;

import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/fair-cut/problem
 *
 * Li and Lu have n integers, a1, a2, ... an, that they want to divide fairly between the two of them. They decide that
 * if Li gets k integers with indices {i1, i2, ... ik} (which implies that Lu gets integers with indices J = {1, 2, ...n}
 * \I ), then the measure of unfairness of this division is:
 * f(I) = SIGMA |ai - aj| for all i in I and all j in J
 * Find the minimum measure of unfairness that can be obtained with some division of the set of integers where Li gets
 * exactly k integers.
 * ---------------------------------------------------------------------------------------------------------------------
 * Input Format
 The first line contains two space-separated integers denoting the respective values of n (the number of integers Li
 nd Lu have) and k (the number of integers Li wants).
 The second line contains n space-separated integers describing the respective values of a1, a2, ... an.
 * Constraints
 1 <= k < n <= 3000
 1 <= ai <= 10^9
 For 15% of the test cases, n <= 20
 For 45% of the test cases, n <= 40
 * Output Format
 Print a single integer denoting the minimum measure of unfairness of some division where Li gets k integers.
 * ---------------------------------------------------------------------------------------------------------------------
 * Sample Input 0
 4 2
 4 3 1 2
 * Sample Output 0
 6
 * Explanation 0
 One possible solution for this input is I = {2, 4}, J = {1, 3}. Unfairness = 6
 * Sample Input 1
 4 1
 3 3 3 1
 * Sample Output 1
 2
 * Explanation 1
 The following division of numbers is optimal for this input: I = {3}, J = {3, 3, 1}
 Unfairness = 2
 * ---------------------------------------------------------------------------------------------------------------------
 * Greedy Strategy: The optimal cut is independent of the numbers ai, but only depends on n and k. For example, if
 * there are n = 10 numbers to cut, and Li gets k = 3 numbers, the best cut will be 0001010100, which means we
 * first sort the 10 numbers in ascending order a1<=a2<=...<=a10, and give the 1s to Li and the 0s to Lu.
 * The strategy is to place the 01-string repetition (the "010101" in the example) as close to the middle of the whole
 * string as possible. If 2*k > n, redefine k to be n-k (switching the 0s and 1s).
 * The intuition behind this greedy strategy comes from physics. The unfairness is like an interaction potential to be
 * minimized. The 0s and 1s attract each other and form an ionic crystal in the middle of a positive charge background.
 * 1. Prove that given optimal cut C* for k < n/2, by flipping 0s and 1s of C*,w e get the optimal cut for n-k
 * Given a cut C with k 1s and (n-k) 0s. Denote C' as the cut by flipping 0s and 1s of the cut C
 * C' has (n-k) 1s and k 0s, i.e. C' is a cut for (n-k). Furthermore U(C) = U(C')
 * By contradiction, if C*' is not optimal for (n-k), C* is not optimal for k
 * 2. Focus on the case k < n/2
 * Based on the strategy above, the result should not have any two consecutive 1s
 */
public class FairCut {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++)
            a[i] = sc.nextInt();

    }
}
