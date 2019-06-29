package algorithm.search;

import java.util.Arrays;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/x-and-his-shots/problem
 *
 * A cricket match is going to be held. The field is represented by a 1D plane. A cricketer, Mr. X has N favorite shots.
 * Each shot has a particular range. The range of the ith shot is from A_i to B_i. That means his favorite shot can be
 * anywhere in this range. Each player on the opposite team can field only in a particular range. Player i can field
 * from C_i to D_i. You are given the favorite shots of Mr. X and the range of M players. S_i represents the strength of
 * each player i.e. the number of shots player i can stop.  A player can stop the  shot if the range overlaps with the
 * player's fielding range. Your task is to find sum of S_i for 0 <= i < M
 *
 * Input Format: the first line consists of two space separated integers, N and M.
 * Each of the next N lines contains two space separated integers.
 * The i_th line contains A_i and B_i.
 * Each of the next M lines contains two space separated integers.
 * The i_th line contains C_i and D_i.
 *
 * Output Format:You need to print the sum of the strengths of all the players
 * Constraints: 1<= N, M <= 10^5, 1<= A_i, B_i, C_i, D_i <= 10^8
 *
 * Sample Input:
 4 4
 1 2
 2 3
 4 5
 6 7
 1 5
 2 3
 4 7
 5 7
 * Sample output: 9
 * Player 1 can stop the 1st, 2nd and 3rd shot so the strength is 3.
 * Player 2 can stop the 1st and 2nd shot so the strength is 2.
 * Player 3 can stop the 3rd and 4th shot so the strength is 2.
 * Player 4 can stop the 3rd and 4th shot so the strength is 2.
 * The sum of the strengths of all the players is 3+2+2+2 = 9
 *
 * The brute force way is checking if each field overlaps with each shot.
 * The time complexity is O(m*n)
 *
 * A shot (a, b) is out of range of a field (c, d) if b < c, i.e. shot upper range < min field
 * or a > d, i.e. shot lower range > max field
 * If we maintain 2 BSTs, 1 stores all the shorts' lower ranges, 1 stores all the shots' upper range,
 * we can easily get list S1 of shots whoese lower range larger than a given max field, and list S2 of
 * shots whose upper range smaller than a given min field. The upper range of any shot in S1 < c < d
 * < the lower range of any shot in S2, thus S1 and S2 should be mutual exclusive.
 * Therefore total number of shots that are non-overlapping with the given field is |S1| + \S2|
 * Number of shots overlapping with the given field (or player's strength) could be deduced from the
 * number of non-overlapping ranges with the player's field
 * The time complexity is O(nlogn + mlogn)
 *
 */
public class MrXAndHisShots {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[] lowerShot = new int[n];
        int[] upperShot = new int[n];
        for (int i = 0; i < n; i++){
            lowerShot[i] = sc.nextInt();
            upperShot[i] = sc.nextInt();
        }
        Arrays.sort(lowerShot);
        Arrays.sort(upperShot);

        int total = 0;
        for (int i = 0; i < m; i++) {
            int minField = sc.nextInt();
            int maxField = sc.nextInt();
            int upperShotLowerThanMinField = getNumberOfElementsLowerThanKey(upperShot,  minField);
            int lowerShotHigherThanMaxField = getNumberOfElementsHigherThanKey(lowerShot, maxField);
            int strength = n - upperShotLowerThanMinField - lowerShotHigherThanMaxField;
            total += strength;
        }

        System.out.println(total);
    }

    /**
     * find the number of elements in a sorted array that are smaller than the given key
     */
    public static int getNumberOfElementsLowerThanKey(int[] a, int key){
        return getNumberOfElementsLowerThanKey(a, 0, a.length-1, key);
    }

    /**
     * find the number of elements in a sorted array that are larger than the given key
     */
    public static int getNumberOfElementsHigherThanKey(int[] a, int key) {
        return getNumberOfElementsHigherThanKey(a, 0, a.length-1, key);
    }

    public static int getNumberOfElementsLowerThanKey(int[] a, int start, int end, int key){
        if (a[start] >= key)
            return 0;
        if (a[end] < key)
            return end-start+1;
        int s = start;
        while(start+1 < end){
            int mid = (start+end)/2;
            if (a[mid] < key)
                start = mid;
            else
                end = mid;
        }
        //finally end is the minimum index: a[end] >= key
        //i.e. a[s] to a[end-1] < key
        return end-s;
    }
    public static int getNumberOfElementsHigherThanKey(int[] a, int start, int end, int key){
        if (a[end] <= key)
            return 0;
        if (a[start] > key)
            return end-start+1;
        int e = end;
        while(start+1 < end){
            int mid = (start+end)/2;
            if (a[mid] <= key)
                start = mid;
            else
                end = mid;
        }
        //finally end is the minimum index: a[end] > key
        //i.e. a[end] to a[e] > key
        return e - end + 1;
    }
}

/** Discusser suggests using 2 Fenwick trees to store the occurrence of shots' lower and upper ranges respectively
 * Number of upper ranges smaller than a given min field can be obtained by querying BIT's sum
 * The space complexity is O(10^8) and time complexity is O(m*log10^8). It could be worse than the previous solution
 */
