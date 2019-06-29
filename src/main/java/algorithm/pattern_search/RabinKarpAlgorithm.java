package algorithm.pattern_search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * https://www.geeksforgeeks.org/searching-for-patterns-set-3-rabin-karp-algorithm/
 * https://www.quora.com/Why-is-the-value-of-q-in-Rabin-Karp-string-calculation-taken-to-be-a-prime-number
 *
 * Rabin-Karp algorithm hashes pattern and all text's substring of length m into integer
 * If the hashes of a substring and pattern are equal, it's a match.
 * Rabin-Karp suggests a hash function as below:
 H (p[0 .. m-1]) = d^(m-1)*p[0] + d^(m-2)*p[1] + ... + d*p[m-2] + p[m-1]
 *
 * This hash function is called rolling hash, since we can quickly computes H(t[k+1.. k+m]) from H(t[k..k+m-1]):
 H(t[k+1 .. k+m]) =  d * (H(t[k .. k+m-1]) – d^(m-1)*t[k]) + t[k + m]
 * which can be done in O(1) instead of O(m) if traversing all chars in t[k+1 .. k+m]
 * If all the arithmetic calculations take O(1), the time complexity of calculating hash for pattern is O(m)
 * and time complexity to search a pattern in text is O(m+n)
 *
 * Time complexity to multiply two n-digit number which is O(n^log3base2) = O(n^1.6),
 * If if m is large, the arithmetic calculations (d^(m-1) *t[k]) would be costly.
 * To reduce the largeness, i.e. multiplication time complexity, we add modular operation to hash function:
 * H (p[0 .. m-1]) = (d^(m-1)*p[0] + d^(m-2)*p[1] + ... + d*p[m-2] + p[m-1]) mod q
 *
 * When adding modular operation, H(t') = H(p) cannot ensure that t' == p.
 * Thus we need to explicitly verify if t' and p match by comparing them char by char.
 * This makes the worst case time complexity be O(m*n), where substring has same hash value as pattern but
 * different string value
 *
 * To avoid collision better, d, q should be selected such that:
 * 1. q is prime, 2. q is large enough and 3. (q, d)= 1, 4. d > number of alphabet chars
 * https://www.quora.com/Why-is-the-value-of-q-in-Rabin-Karp-string-calculation-taken-to-be-a-prime-number
 *
 */
public class RabinKarpAlgorithm {
    public static void main(String[] args ){
        String[][] textAndPatternPairs = {
                {"THIS IS A TEST TEXT", "TEST"},
                {"AABAACAADAABAABA", "AABA"},
                {"abababcababc", "abab"}
        };
        for (int i = 0; i < textAndPatternPairs.length; i++){
            String text = textAndPatternPairs[i][0];
            String pattern = textAndPatternPairs[i][1];
            List<Integer> occurrences = getPatternOccurrences(text, pattern);
            System.out.println("Occurrences of \""+ pattern +"\" in \"" + text + "\"");
            occurrences.forEach(o -> System.out.println(o));
        }
    }

    private final static int d = 256;
    private final static int q = 101;
    private static List<Integer> getPatternOccurrences(String text, String pattern){
        if (text.length() < pattern.length())
            return Collections.emptyList();
        List<Integer> occurrences = new ArrayList<>();
        int n = text.length();
        int m = pattern.length();
        int p = 0; // hash value for pattern
        int t = 0; // hash value for text
        int h = getModularExponent(d, m-1, q);
        int i, j;
        for (i = 0; i < m; i++) {
            p = (d*p + pattern.charAt(i))%q;
            t = (d*t + text.charAt(i))%q;
        }
        for (i = 0; i <= n-m; i++){
            if (p == t) {
                for (j = 0; j < m; j++)
                    if (text.charAt(i+j) != pattern.charAt(j))
                        break;
                if (j == m)
                    occurrences.add(i);
            }
            if ( i < n-m ) {
                t = (d*(t - text.charAt(i)*h) + text.charAt(i+m))%q;
                t = (t < 0)?t+q:t;
            }
        }
        return occurrences;
    }

    /**
     * calculate (base^exponent)%mod
     * @Param exponent and mod > 0
     */
    private static int getModularExponent(int base, int exponent, int mod){
        if (exponent == 0)
            return 1;
        if (exponent == 1)
            return base % mod;
        int half = getModularExponent(base, exponent / 2, mod) % mod;
        if (exponent%2 == 0)
            return (half*half)%mod;
        return (half*half*base)%mod;
    }
}
