package algorithm.dynamic_proramming;

/**
 * https://www.geeksforgeeks.org/dynamic-programming-subset-sum-problem/
 * Given a list of non-negative integers, and a value target, determine if there is a sublist of the given list with sum
 * equal to given target.
 * ---------------------------------------------------------------------------------------------------------------------
 * Sample input: set = {3, 34, 4, 12, 5, 2}, target = 9
 * Sample output:  true
 * Explanation: There is a subset (4, 5) with sum 9.
 * ---------------------------------------------------------------------------------------------------------------------
 * Denote H(input, n, target) as indicator if the sublist of length n (i.e.input[0..n-1]) has a sublist with sum equal
 * to target
 * If subset sum includes the last input element input[n-1], H(input, n, target) = H(input, n-1,target - input[n-1])
 * If subset sum excludes the last input element input[n-1], H(input, n, target) = H(input, n-1, target)
 */
public class SubsetSum {
    public static void main(String[] args){
        int[] input = {3, 34, 4, 12, 5, 2, 4};
        int target = 8;
        System.out.println(hasSubsetSum(input, input.length, target));
        System.out.println(hasSubsetSum2(input, target));
        System.out.println(hasSubsetSum3(input, target));
    }
    //recursive
    public static boolean hasSubsetSum(int[] input, int length, int target){
        if (target == 0)
            return true;
        if (length == 0)
            return false;
        int last = input[length-1];
        if (last > target)
            return hasSubsetSum(input, length-1, target);
        return hasSubsetSum(input, length-1, target) ||
                hasSubsetSum(input, length-1, target - last);
    }
    //iterative
    //Time complexity is O(target*length). Space complexity is O(target*length)
    public static boolean hasSubsetSum2(int[] input, int target){
        int size = input.length;
        boolean[][] subset = new boolean[size+1][target+1];
        for (int sum = 1 ; sum <= target; sum++)
            subset[0][sum] = false;
        for (int len = 0 ; len <= size; len++)
            subset[len][0] = true;
        for (int len = 1 ; len <= size; len++){
            for (int sum = 1; sum <= target; sum++){
                int last = input[len-1];
                if (last > sum)
                    subset[len][sum] = subset[len-1][sum];
                else
                    subset[len][sum] = subset[len-1][sum] || subset[len-1][sum-last];
            }

        }
        return subset[size][target];
    }

    //
    /**
     * Space complexity is O(target)
     * Create a boolean 2D array subset[2][sum+1] and use bottom up manner to fill up this table. The idea behind using
     * only 2 arrays of length (sum+1) is that for filling a row only the values from previous row is required. So
     * alternate rows are used either making the first one as current and second as previous or the first as previous
     * and second as current.
     */
    public static boolean hasSubsetSum3(int[] input, int target){
        int length = input.length;
        boolean[][] subset = new boolean[2][target+1];
        for (int i = 0; i <= length; i++) {
            for (int j = 0; j <= target; j++) {
                // A subset with sum 0 is always possible
                if (j == 0)
                    subset[i% 2][j] = true;
                // If there exists no element no sum is possible
                else if (i == 0)
                    subset[i % 2][j] = false;
                else if (input[i - 1] <= j)
                    subset[i % 2][j] = subset[(i + 1) % 2][j - input[i - 1]] || subset[(i + 1) % 2][j];
                else
                    subset[i % 2][j] = subset[(i + 1) % 2][j];
            }
        }

        return subset[length % 2][target];
    }
}
