package algorithm.string;

/**
 * https://www.hackerrank.com/challenges/hackerrank-in-a-string/problem?isFullScreen=true
 *
 * We say that a string contains the word hackerrank if a subsequence of its characters spell the word hackerrank. Remeber that a subsequence maintains the order of characters selected from a sequence.
 *
 * More formally, let  be the respective indices of h, a, c, k, e, r, r, a, n, k in string . If  is true, then  contains hackerrank.
 *
 * For each query, print YES on a new line if the string contains hackerrank, otherwise, print NO.
 *
 * Example
 *
 * This contains a subsequence of all of the characters in the proper order. Answer YES
 *
 *
 * This is missing the second 'r'. Answer NO.
 *
 *
 * There is no 'c' after the first occurrence of an 'a', so answer NO.
 *
 * Function Description
 *
 * Complete the hackerrankInString function in the editor below.
 *
 * hackerrankInString has the following parameter(s):
 *
 * string s: a string
 * Returns
 *
 * string: YES or NO
 * Input Format
 *
 * The first line contains an integer , the number of queries.
 * Each of the next  lines contains a single query string .
 *
 * Constraints
 *
 * Sample Input 0
 *
 * 2
 * hereiamstackerrank
 * hackerworld
 * Sample Output 0
 *
 * YES
 * NO
 */
public class HackerRankInAString {
    public static void main(String[] args) {
        System.out.println(hackerrankInString("rhbaasdndfsdskgbfefdbrsdfhuyatrjtcrtyytktjjt"));
    }
    public static String hackerrankInString(String s) {
        // Write your code here
        String key = "hackerrank";
        return contain(s, key) ? "YES" : "NO";
    }

    static boolean contain(String text, String key) {
        if (key.isEmpty()) {
            return true;
        }
        char c = key.charAt(0);
        if (text.indexOf(c) < 0) {
            return false;
        }

        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == c) {
                if (contain(text.substring(i+1), key.substring(1))) {
                    return true;
                }
            }
        }

        return false;
    }

}
