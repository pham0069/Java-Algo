package algorithm.pattern_search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * https://www.geeksforgeeks.org/searching-for-patterns-set-2-kmp-algorithm/
 * http://www.cs.nthu.edu.tw/~wkhon/algo08-tutorials/tutorial-kmp.pdf
 *
 * KMP (Knuth-Morris-Pratt) algorithm improves the time complexity of pattern search to O(n)
 * It requires preprocessing the pattern to get the longest proper prefix which is also the suffix
 * Imagine you are comparing text's substring starting from index i to pattern. The first j characters
 * match, i.e. t[i..i+j-1] = p[0..j-1], but the (i+1)th characters does not, i.e. t[i+j] != p[j]
 * When this happens in naive search, we break the inner loop and slide text window by 1, i.e. i++
 * This can be optimized since t[i..i+j-1] have been known to match the pattern's prefix. The next
 * candidate for i should be the left-most index i' such that t[i'..i+j-1] also match some pattern's
 * prefix, i.e. t[i'..i+j-1] is the longest suffix of p[0..j-1], which is also prefix of p[0..j-1]
 * This can be known in advance by preprocessing the pattern to get the longest proper prefix which
 * is also the suffix of p[0..j] for 0 <= j < m
 *
 * How to build the longest proper prefix as suffix (lpas) array for a given pattern?
 * Proper prefix is a string prefix which is not the whole string
 * For example, "crow" is proper prefix of "crowd"
 * "crowd" is not proper prefix of "crowd" since it is equal to the string itself
 * Let Π(i) be the length of the longest proper prefix of p[0..i] which is also the suffix of p[0..i],
 * i.e. Π(i) is the largest number meeting that p[0..Π(i)-1) is proper prefix and proper suffix of p[0..i]
 * Suppose we know Π(0)..Π(i), how to calculate Π(i+1)?
 * If p[Π(i)] == p(i+1) then Π(i+1) = Π(i) + 1 (easily proved by contradiction)
 * If p[Π(i)] != p(i+1) then we need to find another pattern prefix p[0..c] that is also suffix of of p[i-Π(i)+1..i]
 * such that p[i+1] == p[c] and c should be the largest
 * Since p[i-Π(i)+1..i] == p[0..Π(i)-1], p[0..c] is suffix of p[0..Π(i)-1].
 * So next candidate (largest) c should be Π(Π(i)-1)-1.
 * If p[i+1] == p[c+1], then Π(i+1) = c+2.
 * If not, we continue to find the next candidate from p[0..c] until reaching 0.
 * Once reached 0 and no match is found, Π(i+1) = 0
 *
 * For example, pattern = "acacabacacabacacac"
 * lpas = {0, 0, 1, 2, 3, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 4}
 * When i= 16 (the index before the last), lpas[i] = 11,
 * p[i+1] = p[17]= 'c' not match with p(lpas(i)) = p(11) = 'b'
 * next candidate c = lpas(10) - 1 = 5 - 1 = 4
 * p[i+1] = p[17]= 'c' not match with p[c+1] = p[5] = 'b'
 * next candidate c = lpas(4) - 1 = 3 - 1 = 2
 * p[i+1] = p[17]= 'c' match with p[c+1] = p[3] = 'c'
 * hence lpas(17) = c+2 = 4
 *
 * Time complexity to preprocess pattern is O(m)?
 * Time complexity to search pattern in text is O(n)?
 */
public class KMPAlgorithm {
    public static void main(String[] args){
        String[][] textAndPatternPairs = {
                {"THIS IS A TEST TEXT", "TEST"},//10
                {"AABAACAADAABAABA", "AABA"},//0, 9, 12
                {"abababcababc", "abab"}//0, 2, 7
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
        int[] lpas = getLongestPrefixAsSuffix(pattern);
        int i = 0, j = 0;
        while (i < n){
            if (text.charAt(i) == pattern.charAt(j)){
                if (j == m-1) {
                    //a match
                    occurrences.add(i - m + 1);
                    //prepare next comparison
                    i ++;
                    j = lpas[j];
                } else{
                    //continue to compare next char in text and pattern
                    i++;
                    j++;
                }
            } else {
                if (j != 0)
                    j = lpas[j-1];//move back to the char after longest prefix
                else
                    i++;//cannot move back j anymore, so start scratch from next text char
            }
        }
        return occurrences;
    }
    private static int[] getLongestPrefixAsSuffix(String pattern){
        int m = pattern.length();
        int[] lpas = new int[m];
        int i = 1, j = 0;
        for(; i < m; i++){
            if (pattern.charAt(i) == pattern.charAt(j)){
                lpas[i] = lpas[i-1] + 1;
                j++;
            } else {
                while (j > 0){
                    j = lpas[j-1];//get next longest prefix at (i-1)
                    if (pattern.charAt(i) == pattern.charAt(j)){
                        lpas[i] = j + 1;
                        break;
                    }
                }
            }
        }
        return lpas;
    }
}
