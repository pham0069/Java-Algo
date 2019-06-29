package algorithm.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * https://www.hackerrank.com/challenges/sorted-subsegments/problem
 *
 * Consider an array A = {a0...an-1} of n integers.
 * We perform Q queries of the following type on A:
 * Sort all the elements in the subsegment a[l_i..r_i].
 *
 * Given A, can you find and print the value at index k (where 0 <= k <= n)
 * after performing Q queries?
 *
 * Constraints
 * 1 <= n, q <= 75000
 * 0 <= k <= n-1
 * -10^9 <= a_i <= 10^9
 * 0 <= l_i <= r_i < n
 *
 * Editorial
 *
 * Let B_ij be the value in cell j of array  after the first i queries.
 *
 * Now, let's binary search the answer. Assume we want to know if B_qi >= x after all the queries.
 *
 * It's easy to see that all the numbers in array A can be split into two parts:
 * those which are strictly less than x and those which are greater than or equal to x.
 * Let's simulate the sorting on subsegments, but instead of maintaining B_ij, let's maintain C_ij = B_ij >=x.
 *
 * Now, to understand if B_ij >=x , we need to determine if C_ij is true or false.
 *
 * What happens with C_ij, when we perform query (i+1)?
 * Let's look at subsegment C_i(l_i+1)... C_i(r_i+1).
 * It's easy to see that all the false values move to the left and all the true values move to the right after this query.
 *
 * Let's store C_ij in a segment tree, which allows us to get the number of false values on subsegment
 * and enables us to assign some value to a subsegment. To perform query i, we must:
 *
 * 1. Define count to be the number of false values in subsegment (l_i, r_i).
 * 2. Assign the value false to all the values in subsegment (l_i, l_i+ count-1).
 * 3. Assign the value true to all the values in subsegment (l_i+count, r_i).
 * 4. After simulating q queries, we check C_qk to see if element at index k >= x or not
 *
 * In this way, we do binary search for x, until the result converge to single value
 *
 * The total complexity is O(n*logn^2).
 */
public class SortedSubsegments {

    // build a segment tree to count the number of elements >= key in a range
    static class SegmentTree {
        public SegmentTree left, right;
        int count, start, end;
        int pushVal; // use for lazy propagation

        SegmentTree(int start, int end, boolean[] greaterOrEqualToKey) {
            this.start = start;
            this.end = end;
            this.pushVal = -1;

            // build tree recursively
            if (start != end) {
                int mid = (start+end) >> 1;
                left = new SegmentTree(start, mid, greaterOrEqualToKey);
                right = new SegmentTree(mid+1, end, greaterOrEqualToKey);
                count = left.count + right.count;
            } else {
                count = greaterOrEqualToKey[start] ? 1 : 0;
            }
        }

        // find the sum of all elements from s to e
        public int count(int s, int e) {
            if (start == s && end == e) {
                return count;
            }
            push();
            int mid = (start + end) >> 1;
            if (mid >= e) {
                return left.count(s, e); // totally on left
            } else if (mid < s) {
                return right.count(s,e); // totally on right
            } else {
                return left.count(s,mid)+right.count(mid+1,e); // on both side
            }
        }

        public int size() {
            return end-start+1;
        }

        public void push() {
            if (left == null) return;
            if (pushVal == -1) return;
            left.count = pushVal == 1 ? left.size() : 0;
            left.pushVal = pushVal;
            right.count = pushVal == 1 ? right.size() : 0;
            right.pushVal = pushVal;
            pushVal = -1;
        }

        // update all the elements from s to e to val
        public void set(int s, int e, int val) {
            if (s > e) return;
            if (start == s && end == e) {
                this.pushVal = val;
                this.count = val == 1 ? this.size() : 0;
                return;
            }
            push();
            int mid = (start+end) >> 1;
            if (mid >= e) {
                left.set(s, e, val);
            } else if (mid < s) {
                right.set(s,e,val);
            } else {
                left.set(s,mid,val);
                right.set(mid+1,e,val);
            }
            join();
        }

        private void join() {
            if (left == null) return;
            this.count = left.count + right.count;
        }
    }

    static int sortedSubsegments(int k, int[] a, int[] l, int[] r) {
        List<Integer> sortedUnique = getSortedUniqueArray(a);

        int low = 0, high = sortedUnique.size()-1, mid, key;
        int q = l.length;

        // binary search which index that a[k] becomes after q queries
        while (low < high) {
            mid = (low+high+1)/2;
            key = sortedUnique.get(mid);
            boolean[] ge = isGreaterOrEqualToKey(a, key);
            SegmentTree root = new SegmentTree(0, a.length-1, ge);
            for (int i = 0; i < q; i++) {
                int numberOfItemsGreaterOrEqualToKey = root.count(l[i], r[i]);
                root.set(l[i], r[i], 0);
                // items geq to key should be positioned at the right of range after this query
                root.set(r[i]-numberOfItemsGreaterOrEqualToKey+1, r[i], 1);
            }
            int y = root.count(k, k);
            if (y == 1) {  //a[k] >= key
                low = mid;
            } else {
                high = mid-1; //a[k] < key
            }
        }

        return sortedUnique.get(low);
    }

    static boolean[] isGreaterOrEqualToKey(int[] a, int key) {
        boolean[] ge = new boolean[a.length];
        for (int i = 0; i < a.length; i++) {
            ge[i] = a[i] >= key;
        }
        return ge;
    }

    static List<Integer> getSortedUniqueArray(int[] a) {
        Set<Integer> unique = new HashSet<>();
        for (int i = 0; i < a.length; i++) {
            unique.add(a[i]);
        }
        List<Integer> sortedUnique = new ArrayList<>(unique);
        Collections.sort(sortedUnique);
        return sortedUnique;
    }

    public static void main(String[] args) throws IOException {
        InputReader in = new InputReader(System.in);
        //InputReader in = new InputReader(SortedSubsegments.class.getResourceAsStream("SortedSubsegment-test"));
        int n = in.nextInt();
        int q = in.nextInt();
        int k = in.nextInt();

        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
        }

        int[] l = new int[q];
        int[] r = new int[q];
        for (int i = 0; i < q; i++) {
            l[i] = in.nextInt();
            r[i] = in.nextInt();
        }

        int result = sortedSubsegments(k, a, l, r);

        System.out.println(result);
    }


    static class InputReader {
        BufferedReader reader;
        StringTokenizer tokenizer;

        InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }
    }
}
