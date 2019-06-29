package algorithm.pattern_search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * https://www.geeksforgeeks.org/algorithms-gq/pattern-searching/
 * https://www.geeksforgeeks.org/searching-for-patterns-set-1-naive-pattern-searching/
 *
 * Given a text txt of length n and a pattern pat of length m.
 * Print all occurrences of pat in txt. You may assume that n > m.
 *
 * For example,
 * Input: txt[] = "THIS IS A TEST TEXT", pat[] = "TEST"
 * Output: Pattern found at index 10
 * Input:  txt[] =  "AABAACAADAABAABA", pat[] =  "AABA"
 * Output: Pattern found at index 0, 9 and 12
 *
 * Naive approach is sliding the pattern over text one by one and check for a match.
 * If a match is found, then slides by 1 again to check for subsequent matches.
 *
 * The best case occurs when the first character of the pattern is not present in text at all.
 * For example, txt = "AABCCAADDEE", pat = "FAA". Number of comparisons is n.
 * The worst case is either 1) When all characters of the text and pattern are same. For example,
 * txt[] = "AAAAAAAAAAAAAAAAAA", pat[] = "AAAAA"; or 2) When only the last character is different.
 * For example, txt[] = "AAAAAAAAAAAAAAAAAB", pat[] = "AAAAB". Number of comparisons is m*(n-m+1).
 * The worst case time complexity is O(m*n)
 * Although strings which have repeated characters are not likely to appear in English text,
 * they may well occur in other applications (for example, in binary texts).
 */
public class NaivePatternSearch {
    public static void main(String[] args){
        String[][] textAndPatternPairs = {
                {"THIS IS A TEST TEXT", "TEST"},
                {"AABAACAADAABAABA", "AABA"},
                {"abababcababc", "abab"},
                {"caaabd", "aa"}
        };
        for (int i = 0; i < textAndPatternPairs.length; i++){
            String text = textAndPatternPairs[i][0];
            String pattern = textAndPatternPairs[i][1];
            List<Integer> occurrences = getPatternOccurrences(text, pattern);
            System.out.println("Occurrences of \""+ pattern +"\" in \"" + text + "\"");
            occurrences.forEach(o -> System.out.println(o));
        }
    }
    private static List<Integer> getPatternOccurrences(String text, String pattern){
        if (text.length() < pattern.length())
            return Collections.emptyList();
        List<Integer> occurrences = new ArrayList<>();
        int n = text.length();
        int m = pattern.length();
        for (int i = 0; i <= n-m; i++){
            boolean match = true;
            for (int j = 0; j < m; j++)
                if (pattern.charAt(j) != text.charAt(i+j)) {
                    match = false;
                    break;
                }
            if (match)
                occurrences.add(i);
        }
        return occurrences;
    }
}
