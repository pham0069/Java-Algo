package algorithm.string;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * https://www.hackerrank.com/challenges/richie-rich/problem?isFullScreen=true
 *
 * Palindromes are strings that read the same from the left or right, for example madam or 0110.
 *
 * You will be given a string representation of a number and a maximum number of changes you can make. Alter the string, one digit at a time, to create the string representation of the largest number possible given the limit to the number of changes. The length of the string may not be altered, so you must consider 's left of all higher digits in your tests. For example  is valid,  is not.
 *
 * Given a string representing the starting number, and a maximum number of changes allowed, create the largest palindromic string of digits possible or the string '-1' if it is not possible to create a palindrome under the contstraints.
 *
 * Example
 *
 *
 * Make  replacements to get .
 *
 *
 *
 * Make  replacement to get .
 *
 * Function Description
 *
 * Complete the highestValuePalindrome function in the editor below.
 *
 * highestValuePalindrome has the following parameter(s):
 *
 * string s: a string representation of an integer
 * int n: the length of the integer string
 * int k: the maximum number of changes allowed
 * Returns
 *
 * string: a string representation of the highest value achievable or -1
 * Input Format
 *
 * The first line contains two space-separated integers,  and , the number of digits in the number and the maximum number of changes allowed.
 * The second line contains an -digit string of numbers.
 *
 * Constraints
 *
 * Each character  in the number is an integer where .
 * Output Format
 *
 * Sample Input 0
 *
 * STDIN   Function
 * -----   --------
 * 4 1     n = 4, k = 1
 * 3943    s = '3943'
 * Sample Output 0
 *
 * 3993
 * Sample Input 1
 *
 * 6 3
 * 092282
 * Sample Output 1
 *
 * 992299
 * Sample Input 2
 *
 * 4 1
 * 0011
 * Sample Output 2
 *
 * -1
 * Explanation
 *
 *
 */
public class HighestValuePalindrome {

    public static void main(String[] args) {
        System.out.println(highestValuePalindrome("1231", 4, 3));
        System.out.println(highestValuePalindrome("12321", 5, 1));
    }

    private static String highestValuePalindrome(String s, int n, int k) {
        // If number of changes >= string length, simply modify all digits to '9' to get max value
        if (k >= n) {
            return IntStream.range(0, n).boxed().map(i -> "9").collect(Collectors.joining(""));
        }

        // Get the first and second half of string into array first and second
        // Btw, get the indices that contain the different char, which require at least 1 change to make the string palindrome
        int[] first = new int[n/2];
        int[] second = new int[n/2];
        List<Integer> diffIndex = new ArrayList<>();
        for (int i = 0; i < n/2; i++) {
            first[i] = s.charAt(i) - 48;
            second[i] = s.charAt(n-i-1) - 48;
            if (first[i] != second[i]) {
                diffIndex.add(i);
            }
        }
        if (diffIndex.size() > k) {
            return "-1";
        }

        int residue, nineCount;
        boolean toFix;
        int remainingK = k, remainingDiff = diffIndex.size();
        Map<Integer, Integer> change = new HashMap<>();
        for (int i = 0; i < n/2; i++) {
            residue = remainingK - remainingDiff;
            nineCount = getNumberOfNines(first[i], second[i]);
            toFix = diffIndex.contains(i);

            switch (nineCount) {
                case 2: // no need to fix, already max
                    break;
                case 1: // must fix
                    change.put(i, 9);
                    remainingK -= 1;
                    remainingDiff -= 1;
                    break;
                case 0:
                    if (residue >= 2) {
                        change.put(i, 9);
                        remainingK -= 2;
                        if (toFix) {
                            remainingDiff -= 1;
                        }
                    } else if (residue == 1) {
                        if (toFix) {
                            change.put(i, 9);
                            remainingK -= 2;
                            remainingDiff -=1;
                        }
                    } else if (residue == 0) {
                        if (toFix) {
                            change.put(i, Math.max(first[i], second[i]));
                            remainingK -=1;
                            remainingDiff -=1;
                        }
                    }
                    break;

            }
        }

        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < n/2; i++) {
            if (change.containsKey(i)) {
                buffer.append(change.get(i));
            } else {
                buffer.append(first[i]);
            }
        }

        String firstHalf = buffer.toString();
        String secondHalf = new StringBuilder(firstHalf).reverse().toString();

        if (n%2 == 1) {
            char middle = s.charAt(n/2);
            if (remainingK > 0) {
                middle = '9';
            }
            return firstHalf + middle + secondHalf;
        } else {
            return firstHalf + secondHalf;
        }
    }

    private static int getNumberOfNines(int a, int b) {
        int count = 0;
        if (a == 9) {
            count ++;
        }
        if (b == 9) {
            count ++;
        }
        return count;
    }
}
