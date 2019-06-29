package algorithm.sort3;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * https://www.hackerrank.com/contests/goldman-sachs-womens-codesprint/challenges/stock-exchange-matching-algorithm
 *
 * The Stock Exchange Matching Algorithm works in the following way:
 *
 * It is parametrized by n pairs of parameters (s[i], p[i]) for i = 0..n-1. Each s[i] is unique and if a customer wants
 * to buy M shares, the algorithm will first find the largest s[i] such that s[i] <= m and then find a price p[i] for
 * one share, i.e. a price coupled with the chosen s[i].
 *
 * There are K customers, and the i-th of them has requested to buy exactly Q[i] shares. For each such request print
 * the price of one share, p[i] that the algorithm will give.
 *
 * Solution: sort (s[i], p[i]) by increasing order of s[i]. Find largest s[i] <= given Q using binary search. Return
 * corresponding p[i[
 */
public class StockExchangeMatching {
    public static List<Integer> computePrices(List<Integer> s, List<Integer> p, List<Integer> q) {
        List<Integer> result = new ArrayList<>();
        Map<Integer, Integer> map = IntStream.range(0, s.size())
                .boxed()
                .collect(Collectors.toMap(i -> s.get(i), i -> p.get(i)));
        NavigableSet<Integer> numbers = new TreeSet<>();
        numbers.addAll(s);
        q.stream().forEach(qi -> result.add(map.get(numbers.floor(qi))));
        return result;
    }
}
