package algorithm.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/xor-quadruples/problem
 *
 * We call an quadruple of positive integers, (w, x, y, z), beautiful
 * if the following condition is true:
 *   w XOR x XOR y XOR z != 0
 *
 * Given A, B, C, and D, count the number of beautiful quadruples of the form (w, x, y, z)
 * where the following constraints hold:
 * 1. 1 <= w <= A
 * 2. 1 <= x <= B
 * 3. 1 <= y <= C
 * 4. 1 <= z <= D
 *
 * When you count the number of beautiful quadruples,
 * you should consider two quadruples as same if the following are true:
 * 1. They contain same integers.
 * 2. Number of times each integers occur in the quadruple is same.
 *
 * For example (1, 1, 1, 2)  and (1, 2, 1, 1) should be considered as same.
 *
 * Constraints
 * 1 <= A, B, C, D <= 3000
 * For 50% of time, 1 <= A, B, C, D <= 50
 *
 * z != w XOR x XOR y
 * y XOR z != w XOR x
 *
 * Solution
 * 0. First to remove duplicate permutation,
 * assume a <= b <= c <= d (achieve this by sorting a, b, c, d in ascending order)
 * non-duplicate tuple should satisfy the condition w <= x <= y <= z
 * 1. 3 loops for w, x, y.
 * Find z such that z != w ^ x ^ y
 * O(n^3) fail timeout
 *
 * 2. 2 loops needed to pass time limit
 * First, store list of all pairs (y, z) such that 1 <= y <= c, y <= z <= d and its xor result in a map
 * 2 loops for w, x
 * Find y, z such that y ^z != w ^ x
 * - first calculate number of pairs (y, z) such that x <= y <= c; y <= z <= d (*)
 * if z > c -> number of pairs = (c-x+1)(d-c)
 * if z <= c -> number of pairs where (x <= y = z <= c) is (c-x+1)
 * number of pairs where (y < z) is (c-x)+ (c-x-1)+ ... + 1 = (c-x)(c-x+1)/2
 * -> total number of pairs satisfying (*) is
 * (c-x+1)(d-c) + (c-x+1) + (c-x+1)(c-x)/2 = (c-x+1)(2d+2-c-x)
 *
 * - make use of the map above to exclude the pairs where y ^ z = w ^ x
 * and x <= y <= c, y <= z <= d
 * It is noted that
 * - the list can be stored such that it is sorted by element y (natural as we use loop to build map)
 * - the map building ensures that all pairs in the list has y <= z <= d and y <= c
 * -> we only need to find which pair has y >= x
 * -> can use binary search for this purposee
 *
 * This reduces time complexity to O(n^2)
 *
 */
public class BeautifulQuadruples {
    // timeout, O(n^3)
    static long beautifulQuadruples2(int a, int b, int c, int d) {
        int[] sorted = new int[] {a, b, c, d};
        Arrays.sort(sorted);
        a = sorted[0];
        b = sorted[1];
        c = sorted[2];
        d = sorted[3];

        long total = 0;
        int temp1, temp2 = 0;

        for (int w = 1; w <= a; w++) {
            for (int x = w; x <= b; x++) {
                temp1 = w ^ x;
                for (int y = x; y <= c; y++) {
                    temp2 = temp1 ^ y;
                    total += d-y;
                    if (temp2 < y || temp2 > d) {
                        total += 1;
                    }
                }
            }
        }

        return total;
    }

    static class Pair {
        int first;
        int second;
        Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }

    static long beautifulQuadruples(int a, int b, int c, int d) {
        int[] sorted = new int[] {a, b, c, d};
        Arrays.sort(sorted);
        a = sorted[0];
        b = sorted[1];
        c = sorted[2];
        d = sorted[3];

        long total = 0;
        int xor;
        Map<Integer, List<Pair>> xorMap = new HashMap<>();

        for (int i = 1; i <= c; i ++) {
            for (int j = i; j <= d; j++) {
                xor = i ^j;
                xorMap.computeIfAbsent(xor, k -> new ArrayList<>());
                xorMap.get(xor).add(new Pair(i, j));
            }
        }

        for (int w = 1; w <= a; w++) {
            for (int x = w; x <= b; x++) {
                xor = w ^ x;
                // number of pairs (y, z) such that x <= y <= c; y <= z <= d
                total += (c-x+1)*(2*d+2-c-x)/2;
                // this list sorted by y already
                List<Pair> list = xorMap.get(xor);

                if (list != null) {
                    // find pairs (y, z) in list such that x <= y <= c; y <= z <= d
                    // note that the pairs in the list already guarantee that y <= c && y <= z<=d
                    // we only need to determine the range that y >= x
                    int start = binarySearchGreaterOrEqualToKey(list, x);
                    if (start != -1) {
                        int end = list.size()-1;
                        total -= (end-start+1);
                    }
                }

            }
        }

        return total;
    }

    static int binarySearchGreaterOrEqualToKey(List<Pair> list, int key) {
        return binarySearchGreaterOrEqualToKey(list, 0, list.size()-1, key);
    }

    static int binarySearchLessOrEqualToKey(List<Pair> list, int key) {
        return binarySearchLessOrEqualToKey(list, 0, list.size()-1, key);
    }

    static int binarySearchGreaterOrEqualToKey(List<Pair> list, int start, int end, int key) {
        int low = start, high = end, mid =(low+high)/2;
        if (list.get(high).first < key)
            return -1;
        int foundIndex = -1;
        while (low <= high) {
            if (list.get(mid).first == key) {
                //to ensure returning max index satisfying the condition
                foundIndex = mid;
                break;
            } else if (list.get(mid).first < key) {
                low = mid+1;
            } else {
                high = mid-1;
            }
            mid = (low+high)/2;
        }
        if (foundIndex == -1)
            foundIndex = low;
        //to ensure returning smallest index satisfying the condition
        while (foundIndex > start && list.get(foundIndex - 1).first >= key) {
            foundIndex--;
        }
        return foundIndex;
    }

    static int binarySearchLessOrEqualToKey(List<Pair> list, int start, int end, int key) {
        int low = start, high = end, mid =(low+high)/2;
        if (list.get(low).first > key)
            return -1;
        int foundIndex = -1;
        while (low <= high) {
            if (list.get(mid).first == key) {
                //to ensure returning max index satisfying the condition
                foundIndex = mid;
                break;
            }
            else if (list.get(mid).first < key) {
                low = mid+1;
            } else {
                high = mid-1;
            }
            mid = (low+high)/2;
        }
        if (foundIndex == -1)
            foundIndex = high;
        //to ensure returning largest index satisfying the condition
        while (foundIndex < end && list.get(foundIndex + 1).first <= key) {
            foundIndex++;
        }
        return foundIndex;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String[] abcd = scanner.nextLine().split(" ");

        int a = Integer.parseInt(abcd[0].trim());

        int b = Integer.parseInt(abcd[1].trim());

        int c = Integer.parseInt(abcd[2].trim());

        int d = Integer.parseInt(abcd[3].trim());

        long result = beautifulQuadruples(a, b, c, d);

        System.out.println(result);
    }
}
