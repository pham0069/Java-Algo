package data_structure.array;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/minimum-swaps-2/problem?h_l=interview&playlist_slugs%5B%5D%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D%5B%5D=arrays&h_r=next-challenge&h_v=zen
 * You are given an unordered array consisting of consecutive integers in [1, 2, 3, ..., n] without any duplicates.
 * You are allowed to swap any two elements. You need to find the minimum number of swaps required to sort the array in ascending order.
 *
 * For example, given the array arr = [7, 1, 3, 2, 4, 5, 6],  we perform the following steps:
 * i   arr                     swap (indices)
 * 0   [7, 1, 3, 2, 4, 5, 6]   swap (0,3)
 * 1   [2, 1, 3, 7, 4, 5, 6]   swap (0,1)
 * 2   [1, 2, 3, 7, 4, 5, 6]   swap (3,4)
 * 3   [1, 2, 3, 4, 7, 5, 6]   swap (4,5)
 * 4   [1, 2, 3, 4, 5, 7, 6]   swap (5,6)
 * 5   [1, 2, 3, 4, 5, 6, 7]
 *
 * It took 5 swaps to sort the array.
 *
 * Need to find clusters of k indices such that (to simplify, consider 1-based index here)
 * arr[i1] = i2
 * arr[i2] = i3
 * ...
 * arr[ik] = i1
 * Such a cluster would need (k-1) swaps to put the value into place
 *
 * Total number of swaps is sum of number of cluster swaps
 *
 * Alternatively, can do swaps directly on array to keep track number of swaps needed
 */
public class MinimumSwaps2 {

    // Complete the minimumSwaps function below.
    static int minimumSwaps(int[] arr) {
        int swaps = 0;
        boolean[] visited = new boolean[arr.length];
        int entry, count, current;
        for (int i = 0; i < arr.length; i++) {
            if (visited[i]|| arr[i] == i+1) {
                continue;
            }
            entry = i;
            current = arr[entry]-1;
            visited[entry] = true;
            visited[current] = true;
            count = 2;
            while(arr[current]!= entry+1) {
                current = arr[current]-1;
                visited[current] = true;
                count ++;
            }
            swaps += count-1;
        }
        return swaps;
    }

    // actual swaps
    static int minimumSwaps2(int[] a) {
        int swap=0;
        for(int i=0;i<a.length;i++){
            if(i+1!=a[i]){
                int t=i;
                while(a[t]!=i+1){
                    t++;
                }
                // swap a[t] and a[i]
                int temp=a[t];
                a[t]=a[i];
                a[i]=temp;
                /**
                 * a[t]=a[i];
                 * a[i]=i+1;
                 */
                swap++;
            }
        }
        return swap;

    }

    public static void main(String[] args) {
        System.out.println(minimumSwaps(new int[]{4, 3, 1, 2}));
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main2(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int[] arr = new int[n];

        String[] arrItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < n; i++) {
            int arrItem = Integer.parseInt(arrItems[i]);
            arr[i] = arrItem;
        }

        int res = minimumSwaps(arr);

        bufferedWriter.write(String.valueOf(res));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }

}
