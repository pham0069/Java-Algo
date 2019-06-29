package minima;

/**
 * https://www.geeksforgeeks.org/google-summer-trainee-engineering-programstep-interview-experience/
 * https://www.geeksforgeeks.org/find-local-minima-array/
 *
 * Given an array of integers, you need to find the local maxima which is greater than the previous and next elements
 * Note that an array might not have a local maxima
 * Example : [1 3 5 4 7 10 6]
 * Output : 5 or 10
 * Explanation : Any of the local maxima can be the output.
 * Here 5 is greater than 3 and 4, 10 is greater than 7 and 6.
 *
 * See PeakElement. Efficient algo takes O(logn)
 *
 * ---------------------------------------------------------------------------------------------------------------------
 * https://www.geeksforgeeks.org/maximum-number-local-extrema/
 * Count number of local minima and/or maxima.
 *
 * Needs to traverse the whole array. Complexity is O(n)
 */
public class LocalMaxima {
    public static void main(String[] args){
        int[] array = {1, 3, 5, 9, 7, 10, 6};
    }
}
