package data_structure.hash;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * You are given an integer array input[] and a sum target. Find if there is any
 * pair of distinct elements whose sum equals to the given target
 *
 * A naive solution is traversing the input in 2 loops and check if any two elements
 * has sum equal to the target. The time complexity is O(n^2)
 *
 * Another solution is sorting the input first. Then use two index, 1 starts from the
 * beginning, 1 starts from the end, to check if the elements at two ends has sum
 * equal to the target. The time complexity is O(nlogn)
 *
 * The third solution is using a hashSet which can check if a value is present in the set
 * in O(1). We can add all the input elements to the set and check if the compatible value
 * (sum-element) is present in the set. The time complexity is O(n)
 */
public class PairWithGivenSum {
    public static void main(String[] args){
        int[] inputs = {2, 1, 5, 9, 0, 8};
        int target = 0;
        System.out.println(hasAnyPairWithSum(inputs, target));
        findAllPairsWithSum(inputs, target).stream()
                .forEach(pair -> System.out.println(pair));
    }
    public static boolean hasAnyPairWithSum(int[] input, int target){
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < input.length; i++){
            if (set.contains(target-input[i]))
                return true;
            set.add(input[i]);
        }
        return false;
    }

    /**
     * Find all unique pairs with sum equal to the target
     * A pair is defined as having two values, the first leq than the second
     * Sort the result in ascending order of the pair's first value
     */
    public static Set<Pair> findAllPairsWithSum(int[] input, int target){
        Set<Pair> result = new TreeSet<>();
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < input.length; i++){
            if (set.contains(target-input[i])) {
                result.add(new Pair(input[i], target-input[i]));
            }
            set.add(input[i]);
        }
        return result;
    }
    static class Pair implements Comparable<Pair>{
        int first;
        int second;
        public Pair(int first, int second){
            if (first <= second) {
                this.first = first;
                this.second = second;
            } else{
                this.first = second;
                this.second = first;
            }

        }
        @Override
        public int compareTo(Pair that){
            if (that==null)
                return 1;
            if (this.first==that.first)
                return this.second-that.second;
            return this.first-that.first;
        }
        @Override
        public String toString(){
            return first + " " + second;
        }
    }
}
