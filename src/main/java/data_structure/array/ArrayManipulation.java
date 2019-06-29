package data_structure.array;

/**
 * https://www.hackerrank.com/challenges/crush/problem
 * https://www.geeksforgeeks.org/binary-indexed-tree-range-updates-point-queries/
 *
 * You are given a list x of size n, initialized with zeroes. You have to perform m
 * operations on the list x and output the maximum of final values of all the elements in the list.
 *
 * For every operation, you are given three integers a, b and k  and you have to add value k to all
 * the elements ranging from index a to b(both inclusive). For example, consider a list x = {0, 0, 0}
 * of size 3. After performing the update (a, b, k) = (1, 2, 30), the new list would be {0, 30, 30}
 * Note the index of the list starts from ZERO.
 *
 */
public class ArrayManipulation {
    public static void main(String[] args){
        int n = 5;
        Query[] queries = {
                new Query(0, 1, 100),
                new Query(1, 4, 100),
                new Query(2, 3, 100)
        };
        long max = getMaxAfterManipulations(n, queries);
        assert max==200;
    }
    /**
     * We don't really need to update all the values involved in the operations
     * We maintain the difference between the current element and previous element in an array, diff
     * For a query (a, b, k), we add k to diff[a] to show that x[a] is larger than x[a-1] by k,
     * subtract k from the diff[b+1] to show that x[b+1] is smaller than x[b] by k, and no need
     * to update any diff from index (a+1) to b since all of them are added by k
     *
     * Time complexity = O(m) in worst case
     * Space complexity = O(n)
     */
    public static long getMaxAfterManipulations(int n, Query[] queries){
        long[] cumulativeDiff = new long[n];
        //maintain the cumulative difference between consecutive elements
        for (int i = 0; i < queries.length; i++){
            Query query = queries[i];
            cumulativeDiff[query.a] += query.k;
            if (query.b < n-1)
                cumulativeDiff[query.b+1] -= query.k;
        }
        long max = Long.MIN_VALUE;
        //get the final values after all queries and find the max value
        long finalVal = 0;
        for (int i = 0; i < n; i++){
            finalVal += cumulativeDiff[i];
            if (finalVal > max)
                max = finalVal;
        }
        return max;
    }
    /**
     * Naive solution is to store the updated values after queries and get the maximum element afterwards
     * Time complexity = O(n*m) in worst case
     * Space complexity = O(n)
     */
    public static long getMaxAfterManipulations_Naive(int n, Query[] queries){
        long[] array = new long[n];
        for (int i = 0; i < queries.length; i++){
            Query query = queries[i];
            for (int j = query.a; j <= query.b; j++){
                array[j] += query.k;
            }
        }
        long max = Long.MIN_VALUE;
        for (int i = 0; i < n; i++){
            if (array[i] > max)
                max = array[i];
        }
        return max;
    }

    static class Query{
        int a, b, k;
        Query(int a, int b, int k){
            this.a = a;
            this.b = b;
            this.k = k;
        }
    }
}
