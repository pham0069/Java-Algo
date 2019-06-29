package algorithm.pattern_search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * https://www.geeksforgeeks.org/pattern-searching-set-4-a-naive-string-matching-algo-question/
 *
 * Given that all the chars in pattern string are unique
 * Find the pattern match in a given text
 *
 * Since all chars in pattern string are unique, the prefix array of pattern
 * should have all zeros. When comparing the chars in text and pattern, if
 * they do not match, simply increment text index and reset pattern index to 0.
 * We dont need to go through complicated logic of KMP algorithm and could be a
 * bit faster than KMP algorithm
 */
public class SpecialPattern {
    public static void main(String[] args){
        String[][] textAndPatternPairs = {
                {"A crowd is crowded", "crowd"},
                {"A bear is bearing a bear", "bear"},
        };
        for (int i = 0; i < textAndPatternPairs.length; i++){
            String text = textAndPatternPairs[i][0];
            String pattern = textAndPatternPairs[i][1];
            List<Integer> occurrences = optimizeNaiveStyle(text, pattern);
            System.out.println("Occurrences of \""+ pattern +"\" in \"" + text + "\"");
            occurrences.forEach(o -> System.out.println(o));
        }
    }
    private static List<Integer> optimizeNaiveStyle(String text, String pattern){
        if (text.length() < pattern.length())
            return Collections.emptyList();
        List<Integer> occurrences = new ArrayList<>();
        int n = text.length();
        int m = pattern.length();
        for (int i = 0; i < n; ){
            boolean match = true;
            int j = 0;
            for ( ; j < m; j++)
                if (pattern.charAt(j) != text.charAt(i+j)) {
                    match = false;
                    break;
                }
            if (match) {
                occurrences.add(i);
                i = i+m;//slide by m instead of 1
            }
            else
                i = i+j+1;//slide by (j+1) instead of 1
        }
        return occurrences;
    }
    private static List<Integer> optimizeKMPStyle(String text, String pattern){
        if (text.length() < pattern.length())
            return Collections.emptyList();
        List<Integer> occurrences = new ArrayList<>();
        int n = text.length();
        int m = pattern.length();
        int i = 0, j = 0;
        for (i = 0; i < n; i++){
            if (text.charAt(i) == pattern.charAt(j)){
                if (j == m-1) {
                    occurrences.add(i-m+1);
                    j = 0;
                } else {
                    j++;
                }
            } else {
                j = 0;
            }
        }
        return occurrences;
    }
}
