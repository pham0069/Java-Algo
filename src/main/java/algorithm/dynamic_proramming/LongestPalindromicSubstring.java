package algorithm.dynamic_proramming;

import java.util.stream.IntStream;

/**
 * https://www.geeksforgeeks.org/longest-palindrome-substring-set-1/
 * https://www.geeksforgeeks.org/longest-palindromic-substring-set-2/
 * https://www.geeksforgeeks.org/longest-palindromic-substring-set-3/
 *
 * Given a string, find the longest substring which is palindrome
 * For example, if the given string is “forgeeksskeegfor”, the output should be “geeksskeeg”.
 *
 * Method 1 - Brute force
 * Check all substrings if they are palindrome and get the length of the longest one
 * Time complexity is O(n^3). Space complexity is O(1)
 *
 * Method 2 - Dynamic programming
 * The time complexity is reduced if we store the result of subproblems
 * Use a 2D boolean table[][] such that table[i][j] = true if the substring(s, i, j) is palindrome
 * and = false otherwise. Hence, table[i][j] = true if table[i+1][j-1] = true and char at i = char at j
 * We can fill the table in bottom up manner
 * Time complexity is O(n^2). Space complexity is O(n^2)
 *
 * Method 3 - Check every palindromic center
 * A palindrome with odd-length is centered at a char
 * A palindrome with even-length is centered at 2 chars
 * By checking all positions if it could be center of palindrom, we can keep track of max length palindrome
 * Time complexity is O(n^2). Space complexity is O(1)
 **/
public class LongestPalindromicSubstring {
    public static void main(String[] args) {
        String s = "forgeeksskeegfor";
        System.out.println(getLongestPalindromicSubstring2(s));
        System.out.println(getLongestPalindromicSubstring3(s));
    }

    private static String getLongestPalindromicSubstring2(String s) {
        int n = s.length();
        boolean[][] isPalindrome = new boolean[n][n];
        int maxLength = 1, maxStart = 0;
        IntStream.range(0, n).boxed().forEach(i -> isPalindrome[i][i] = true);
        IntStream.range(0, n-1).boxed().forEach(i -> isPalindrome[i][i+1] = s.charAt(i)==s.charAt(i+1));
        for (int length = 3; length <= n; length++) {
            for (int start = 0; start <= n-length; start++) {
                if (isPalindrome[start+1][start+length-2] && s.charAt(start) == s.charAt(start+length-1)) {
                    isPalindrome[start][start + length - 1] = true;
                    if (length > maxLength) {
                        maxLength = length;
                        maxStart = start;
                    }
                }
            }
        }
        return s.substring(maxStart, maxStart + maxLength);
    }

    private static String getLongestPalindromicSubstring3(String s) {
        int n = s.length();
        int maxLength = 1, maxStart = 0, distance;
        for (int center = 0; center < n; center++){
            //odd-length palindrome centered at center
            distance = 0;
            while (center>=distance && center<n-distance) {
                if (s.charAt(center-distance) != s.charAt(center+distance))
                    break;
                distance ++;
            }
            if (2*distance+1 > maxLength) {
                maxLength = 2*distance+1;
                maxStart = center-distance;
            }

            //even-length palindrome centered at center and center+1
            distance = 0;
            while(center>=distance && center<n-distance-1) {
                if (s.charAt(center-distance) != s.charAt(center+1+distance))
                    break;
                distance ++;
            }
            if (2*distance > maxLength) {
                maxLength = 2*distance;
                maxStart = center-distance+1;
            }

        }
        return s.substring(maxStart, maxStart + maxLength);
    }
}
