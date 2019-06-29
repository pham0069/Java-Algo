package algorithm.dynamic_proramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * There are 100 different types of caps each having a unique id from 1 to 100.
 * Also, there are ‘n’ persons each having a collection of a variable number of caps.
 * One day all of these persons decide to go in a party wearing a cap but to look unique they decided that none of them
 * will wear the same type of cap.
 * So, count the total number of arrangements or ways such that none of them is wearing the same type of cap.
 * ---------------------------------------------------------------------------------------------------------------------
 * Constraints: 1 <= n <= 10
 * Input format:
 * The first line contains the value of n, next n lines contain collections of all the n persons.
 * Output format:
 * Print out the number of ways modulo 1000000007
 *
 * Sample input:
 3
 5 100 1
 2
 5 100
 *
 * Sample output:
 4
 * Explanation: All valid possible ways are (5, 2, 100), (100, 2, 5), (1, 2, 5) and  (1, 2, 100)
 * ---------------------------------------------------------------------------------------------------------------------
 * Note that to split a string into tokens separated by whitespace (space, tab...) use \\s+
 * \s will match any space including tabs but \ require escaping hence it become \\s, but it's not greedy yet.
 * So we added +, which will match 1 or more occurrence, so it become greedy.
 * ---------------------------------------------------------------------------------------------------------------------
 * A Simple Solution is to try all possible combinations. Start by picking the first element from the first set, marking
 * it as visited and recur for remaining sets. It is basically a Backtracking based solution.
 *
 * A better solution is to use Bitmasking and DP. Let us first introduce Bitmasking.
 * Suppose we have a collection of elements which are numbered from 1 to N.
 * If we want to represent a subset of this set then it can be encoded by a sequence of N bits (we usually call this
 * sequence a “mask”). In our chosen subset the i-th element belongs to it if and only if the i-th bit of the mask is
 * set i.e., it equals to 1. For example, the mask 10000101 means that the subset of the set [1… 8] consists of elements
 * 1, 3 and 8.
 *
 * We know that for a set of N elements there are total 2^N subsets thus 2^N masks are possible, one representing each
 * subset. Each mask is, in fact, an integer number written in binary notation.
 *
 *
 */
public class CapCombination {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();
        List<List<Integer>> capCollection = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            String line = sc.nextLine().trim();
            List<Integer> caps = Arrays.stream(line.split("\\s+"))
                    .map(Integer::new)
                    .collect(Collectors.toList());
            capCollection.add(caps);
        }

        backtrackCount(n, capCollection);
    }

    static void bitMaskingCount(int n, List<List<Integer>> capCollection) {

    }

    static void backtrackCount(int n, List<List<Integer>> capCollection) {
        Set<Integer> capCombination = new HashSet<>();
        Count count = new Count();
        recursiveBacktrack(0, n, capCollection, capCombination, count);
        System.out.println(count.value);
    }

    static void recursiveBacktrack(int i, int n, List<List<Integer>> capCollection, Set<Integer> capCombination,
                                      Count count) {
        if (i == n) {
            count.increment();
            return;
        }
        List<Integer> caps = capCollection.get(i);
        for (int capType : caps) {
            if (! capCombination.contains(capType)) {
                capCombination.add(capType);
                recursiveBacktrack(i+1, n, capCollection, capCombination, count);
                capCombination.remove(capType);
            }
        }
    }

    static class Count {
        int value = 0;
        void increment() {
            value +=1;
            value %=1000000007;
        }
    }

}
