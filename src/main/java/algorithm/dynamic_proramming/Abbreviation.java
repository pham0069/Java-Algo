package algorithm.dynamic_proramming;

import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/abbr/problem
 *
 * You can perform the following operations on the string, s:
 * 1. Capitalize zero or more of s's lowercase letters.
 * 2. KDTreeDelete all of the remaining lowercase letters in s.
 * Given two strings, a and b, determine if it's possible to make a equal to  b as described.
 * If so, print YES on a new line. Otherwise, print NO.
 * For example, given a = AbcDE and b = ABDE,  we can convert 'b' to 'B' and delete 'c' to match b.
 * If a = AbcDE and b = AFDE, matching is not possible because letters may only be capitalized or discarded, not changed.
 * ---------------------------------------------------------------------------------------------------------------------
 * Input Format:
 * The first line contains a single integer q, the number of queries.
 * Each of the next q pairs of lines is as follows:
 * - The first line of each query contains a single string, a.
 * - The second line of each query contains a single string, b.
 * Constraints:
 * 1 <= q <= 10
 * 1 <= |a|, |b| <= 1000
 * String a consists only of uppercase and lowercase English letters, ascii[A-Za-z].
 * String b consists only of uppercase English letters, ascii[A-Z].
 *
 * Output Format: For each query, print YES on a new line if it's possible to make string  equal to string .
 * Otherwise, print NO.
 * ---------------------------------------------------------------------------------------------------------------------
 * Sample Input 1
 1
 daBcd
 ABC
 gG
 G
 * Sample Output 1
 YES
 YES
 * Explanation: We perform the following operation: 1. Capitalize the letters a and c in  so that  dABCd. 2. KDTreeDelete all
 * the remaining lowercase letters to get ABC. Because we were able to successfully convert a to b, we print YES on a new line.
 * Remove lower g in 'gG' to get 'G'
 * Sample Input 2
 2
 BFZZVHdQYHQEMNEFFRFJTQmNWHFVXRXlGTFNBqWQmyOWYWSTDSTMJRYHjBNTEWADLgHVgGIRGKFQSeCXNFNaIFAXOiQORUDROaNoJPXWZXIAABZKSZYFTDDTRGZXVZZNWNRHMvSTGEQCYAJSFvbqivjuqvuzafvwwifnrlcxgbjmigkms
 BFZZVHQYHQEMNEFFRFJTQNWHFVXRXGTFNBWQOWYWSTDSTMJRYHBNTEWADLHVGIRGKFQSCXNFNIFAXOQORUDRONJPXWZXIAABZKSZYFTDDTRGZXVZZNWNRHMSTGEQCYAJSF
 bBccC
 BBC
 * Sample Ouput 2
 YES
 YES
 * ---------------------------------------------------------------------------------------------------------------------
 */
public class Abbreviation {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int test = sc.nextInt();
        while (test-- > 0){
            String one = sc.next();
            String two = sc.next();
            System.out.println(isConvertibleRecursive(one, two)?"YES":"NO");
        }
    }

    /* Iterative method: failed due to timeout for test case with very long string
     * Even though method is called only once with the same params,
     * it took too many rounds of recursion to finally get to the edge conditions
     * i.e. the stack of recursive method calls was broad
     */
    private static boolean isConvertible(String one, String two){
        int i = 0, j = 0;
        while (i < one.length() && j < two.length()){
            char c1 = one.charAt(i);
            char c2 = two.charAt(j);
            if (Character.isUpperCase(c1)) {
                if (c1 != c2) {
                    return false;
                } else {
                    i++;
                    j++;
                }
            } else {
                char upperC1 = Character.toUpperCase(c1);
                if (upperC1 != c2) {
                    i++;
                } else {
                    if (i == one.length() - 1)
                        if (j == two.length() - 1)
                            return true;
                        else
                            return false;
                    return isConvertible(one.substring(i + 1), two.substring(j + 1))
                            || isConvertible(one.substring(i + 1), two.substring(j));
                }
            }
        }
        if (j == two.length()) {
            for (; i < one.length(); i++){
                if (Character.isUpperCase(one.charAt(i)))
                    return false;
            }
            return true;
        }
        return false;
    }

    /* Recursive method: resolve timeout problem
     * Use bottom up approach (instead of top-down approach) to narrow the method calls stack
     */
    private static boolean isConvertibleRecursive(String one, String two){
        // convertible[i][j] stores whether substring of one, starting from index 0 and of length i
        // can be converted to substring of two, starting from index 0 and of length j
        boolean[][] convertible = new boolean[one.length()+1][two.length()+1];
        for (int i = 0; i <= one.length(); i++)
            convertible[i][0] = true;
        for (int j = 1; j <= two.length(); j++){
            for (int i = j; i <= one.length(); i++){
                char c1 = one.charAt(i-1);
                char c2 = two.charAt(j-1);
                if (Character.isUpperCase(c1)){
                    if (c1 == c2)
                        convertible[i][j] = convertible[i-1][j-1];
                } else if (Character.toUpperCase(c1) != c2) {
                    convertible[i][j] = convertible[i - 1][j];
                } else {
                    convertible[i][j] = convertible[i-1][j-1] || convertible[i-1][j];
                }
            }
        }
        return convertible[one.length()][two.length()];
    }
}
