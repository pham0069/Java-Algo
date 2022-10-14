package algorithm.string;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * https://www.hackerrank.com/challenges/sherlock-and-valid-string/problem?isFullScreen=true
 *
 * Sherlock considers a string to be valid if all characters of the string appear the same number of times. It is also valid if he can remove just  character at  index in the string, and the remaining characters will occur the same number of times. Given a string , determine if it is valid. If so, return YES, otherwise return NO.
 *
 * Example
 *
 * This is a valid string because frequencies are .
 *
 *
 * This is a valid string because we can remove one  and have  of each character in the remaining string.
 *
 *
 * This string is not valid as we can only remove  occurrence of . That leaves character frequencies of .
 *
 * Function Description
 *
 * Complete the isValid function in the editor below.
 *
 * isValid has the following parameter(s):
 *
 * string s: a string
 * Returns
 *
 * string: either YES or NO
 * Input Format
 *
 * A single string .
 *
 * Constraints
 *
 * Each character
 * Sample Input 0
 *
 * aabbcd
 * Sample Output 0
 *
 * NO
 * Explanation 0
 *
 * Given s, we would need to remove two characters, both c and d  aabb or a and b  abcd, to make it valid.
 * We are limited to removing only one character, so  is invalid.
 */
public class SherlockValidString {
    public static boolean isValid(String s) {
        // Write your code here
        Map<Character, Integer> frequency = new HashMap<>();
        for (char c: s.toCharArray()) {
            frequency.putIfAbsent(c, 0);
            frequency.put(c, frequency.get(c) + 1);
        }

        if (frequency.size() < 2) {
            return true;
        } else {
            Iterator<Integer> iter = frequency.values().iterator();
            int first = iter.next();
            int second = iter.next();
            int min = Math.min(first, second);
            int max = Math.max(first, second);
            int subtract = min == 1 ? max : min;

            List<Integer> filtered = frequency.values().stream()
                    .map(i -> (i-subtract))
                    .filter(i -> i != 0)
                    .collect(Collectors.toList());

            boolean isValid = (filtered.isEmpty());
            isValid |= (filtered.size() == 1 && filtered.get(0) == 1);
            isValid |= (filtered.size() == 1 && filtered.get(0) == 1-subtract);
            return isValid;
        }
    }


}
