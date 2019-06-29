package data_structure.advanced.fenwick_tree;

import java.util.Scanner;

/**
 * https://www.geeksforgeeks.org/binary-indexed-tree-range-update-range-queries/
 *
 * Given an array arr[0..n-1]. There are two operations to be performed on the array
 * 1. UPDATE l r val : Add ‘val’ to all the elements in the array from [l, r].
 * 2. SUM k : Find sum of all elements in the array from 0 to k
 * Initially all the elements in the array are 0.
 * Queries can be in any order, i.e., there can be many updates before point query.
 *
 * Consider an update query(l, r, val) followed by sum query(k)
 * There are 3 possible cases:
 * 1. 0<k<l: no change in sum(k)
 * 2. l<=k<=r: sum(k) is incremented by val*(k-(l-1))
 * 3. k>r: sum(k) is incremented by val*(r-(l-1))
 *
 * We can maintain 2 BITs:
 * BIT1 stores the cumulative element difference (like RangeUpdateAndPointQuery),
 * update(l, r, val) -> decrease BIT1 (l, +val) and (r+1, -val)
 * BIT2 stores the cumulative sum difference
 * update(l, r, val) -> decrease BIT2 (l, -val*(l-1)) and (r+1, val*r)
 *
 * sum(k) could be calculated by delta = sum BIT1(k) *k + sum BIT2(k)
 *
 * This can be proved briefly as follows:
 * 0. Originally, all elements arrays are zeros, BIT1 and BIT2 are all zeros
 * -> sum(k) = 0 = sum BIT1(k) *k + sum BIT2(k)
 * 1. After the first update (l, r, val): we have 2 cases
 *
 * a. 0<k<l:
 * delta (sum BIT1(k)) = delta (sum BIT2(k)) = 0
 * -> delta = 0*k - 0 = 0
 * b. l<=k<=r:
 * delta (sum BIT1(k)) = val (as the kth element is incremented by val)
 * delta (sum BIT2(k)) = -val*(l-1)
 * -> delta = val*k + (-val)*(l-1) = val*(k-(l-1))
 * c. k>r:
 * delta(sum BIT1(k)) = 0 (as kth element is not changed)
 * delta(sum BIT2(k)) = val*r - val*(l-1) (only these two elements updated in BIT2)
 * -> delta = 0*k + val*r-val*(l-1) = val*(r-(l-1))
 *
 * => after this update, sum(k) = sum BIT1(k) *k + sum BIT2(k) holds
 * By reduction, we can infer that this is true after any number of updates
 */
public class RangeUpdateAndRangeQuery {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        try{
            int n = sc.nextInt();
            FenwickTree cumulativeDiff = new FenwickTree(n);
            FenwickTree cumulativeSumDiff = new FenwickTree(n);
            int m = sc.nextInt();
            for (int i = 0; i < m; i++) {
                String op = sc.next();
                if (op.equals("UPDATE")){
                    int l = sc.nextInt();
                    int r = sc.nextInt();
                    int val = sc.nextInt();
                    cumulativeDiff.updateValueBy(l, val);
                    cumulativeDiff.updateValueBy(r+1, -val);
                    cumulativeSumDiff.updateValueBy(l, -val*(l-1));
                    cumulativeSumDiff.updateValueBy(r+1, val*r);
                }
                else if (op.equals("GET")){
                    int k = sc.nextInt();
                    long rangeSum = cumulativeDiff.getSum(k)*k + cumulativeSumDiff.getSum(k);
                    System.out.println(rangeSum);
                }
            }
        }
        catch(Exception e){}
    }
}