package data_structure.misc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * https://www.hackerrank.com/contests/hackerrank-all-womens-codesprint-2019/challenges/alpha-and-beta/problem
 *
 * Our friends Alpha and Beta found a magical treasure of Asgard. It consists of  piles of gold coins. It is magical
 * since if anyone tries to take a pile of  coins, all the other piles of exactly  coins (if they exist) disappear.
 *
 * Alpha and Beta only have one turn each to choose a pile for themselves starting with Alpha. In one turn, a complete
 * pile of gold coins can be chosen and since our friends are smart they will choose the pile with the maximum coins.
 *
 * Find the number of coins Beta will get in his turn.
 *
 * Function Description
 *
 * Complete the alphaBeta function in the editor below. It should return an integer representing the number of coins
 * Beta will get in his turn.
 *
 * alphaBeta has the following parameter(s):
 *
 * pile: an integer array
 *
 * Input Format
 *
 * First line of the input contains a single integer , number of piles.
 * Second line of the input contains  space seperated integers, number of gold coins in each pile.
 * Constraints
 *
 *  (where )
 * Output Format
 *
 * Single integer which is the number of coins Beta will receive in his first turn.
 * Sample Input 0
 *
 * 6
 * 1 2 3 3 2 1
 * Sample Output 0
 *
 * 2
 * Explanation 0
 *
 * Alpha will select a pile of  coins in his turn. So due to magic the other pile of  coins will disappear and Beta will
 * be left with 4 piles:
 *
 * 1 2 2 1
 * Hence Beta will select a pile of  coins.
 *
 * Sample Input 1
 *
 * 5
 * 1 2 3 4 5
 * Sample Output 1
 *
 * 4
 * Explanation 1
 *
 * Alpha will select a pile of  coins in his turn. There are no other piles of  coins so none disappear. Hence Beta
 * will select a pile of  coins.
 *
 * ====================================================================================================================
 * This is equivalent to get the second max number in the list
 * Note that if all piles has same number of coins, Beta will get 0
 */

public class AlphaAndBeta {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> pile = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        int result = alphaBeta(pile);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }

    static int alphaBeta(List<Integer> pile) {
        if (pile.size() == 0)
            return 0;
        int max = pile.stream().max(Comparator.comparingInt(i -> i)).get();
        Optional<Integer> secondMax = pile.stream().filter(i -> i < max)
                .max(Comparator.comparingInt(i -> i));
        return secondMax.orElse(0);
    }
}
