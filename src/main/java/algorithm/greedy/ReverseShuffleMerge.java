package algorithm.greedy;

import java.util.Arrays;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/reverse-shuffle-merge/problem
 * Given a string, A , we define some operations on the string as follows:
 * a. reverse(A) denotes the string obtained by reversing string A. Example: reverse("abc") = "cba"
 * b. shuffle(A) denotes any string that's a permutation of string A. Example: shuffle("god") = {"god", "gdo", "dog",
 * "dgo", "odg", "ogd"}
 * c. merge(A1, A2) denotes any string that's obtained by interspersing the two strings A1 & A2, maintaining the order
 * of characters in both. For example, A1 = "abc" & A2 = "def", one possible result of merge(A1, A2) could be "abcdef",
 * another could be "abdcef", another could be "adebcf" and so on.
 * Given a string s such that s in merge(reverse(A), shuffle(A)) for some string A, find the lexicographically smallest A.
 * For example, s = "abab". We can split it into two strings of "ab". The reverse is "ba"  and we need to find a string
 * to shuffle in to get "ba". The middle two characters match our reverse string, leaving the a and b at the ends. Our
 * shuffle string needs to be ab. Lexicographically ab < ba, so our answer is ab.
 *
 * Input Format
 * A single line containing the string s.
 * Constraints
 * s contains only lower-case English letters, ascii[a-z]
 *
 * Output Format
 * Find and return the string which is the lexicographically smallest valid.
 *
 * Sample Input 0
 eggegg
 * Sample Output 0
 egg
 * Explanation 0
 Split "eggegg" into strings of like character counts: "egg", "egg"
 reverse("egg") = "gge"
 shuffle("egg") can be "egg"
 "eggegg" belongs to the merge of ("gge", "egg")
 The merge is: gge.
 'egg' < 'gge'
 *----------------------------------------------------------------------------------------------------------------------
 * Solution

 If the input string is S and the required answer is A, then the basic idea is as follows :
 1) Store the count of each character (a to z) in S.
 2) Update the count of characters required for A by dividing by 2.
 3) Select each character for A by parsing S from right to left.
 4) One "can" afford to "not select" a character, if its required-count for A will be fulfilled even after leaving it.
 5) Considering point 4, always select smallest character if possible.
 6) If a character can't be left (point 4 invalid), select the smallest character seen so far (even if it is not smallest for the entire string).
 7) Ignore characters not required for A anymore.
 */
public class ReverseShuffleMerge {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String merge = sc.next();
        int n = merge.length();
        int chars[] = new int[n];
        int counts[][] = new int[n][26];
        int requiredCounts[] = new int[26];

        for(int i = 0; i < n; i++) {
            chars[i] = merge.charAt(i) - 'a';
            int countAtIndex[] = i==0?new int[26]: Arrays.copyOf(counts[i-1], 26);
            countAtIndex[chars[i]]++;
            counts[i] = countAtIndex;
        }
        for(int i = 0; i < 26; i++) {
            requiredCounts[i] = counts[n-1][i]/2;
        }

        int selectedChars[] = new int[n/2];
        int selectedCounts[] = new int[26];
        int i = n-1, j = 0, k = -1;
        while (j < n/2){
            int c = chars[i];
            if (isMinCharForSelection(c, requiredCounts, selectedCounts)){
                //System.out.println(i + " " + c);
                selectedChars[j] = c;
                selectedCounts[c] ++;
                j++;
                k = i;
                i--;
            } else {
                int totalRequired = requiredCounts[c];
                int selected = selectedCounts[c];
                int left = counts[i][c];
                if (isRequiredToSelect(totalRequired, selected, left)){
                    int minIndex = getMaxIndexForMinCharSelection(i, k-1, chars, requiredCounts, selectedCounts);
                    //System.out.println("backtrace" + minIndex + " " + chars[minIndex] + " " + i + " " + k);
                    selectedChars[j] = chars[minIndex];
                    selectedCounts[chars[minIndex]] ++;
                    j++;
                    k = minIndex;
                    i = minIndex - 1;
                } else {
                    i--;
                }
            }
        }
        for (i = 0; i < selectedChars.length; i++){
            System.out.print((char)(selectedChars[i]+'a'));
        }
    }

    private static boolean isMinCharForSelection(int c, int[] requiredCounts, int[] selectedCounts){
        if (requiredCounts[c] - selectedCounts[c] == 0)
            return false;
        for (int i = 0; i < c; i++){
            if (requiredCounts[i] - selectedCounts[i] > 0)
                return false;
        }
        return true;
    }

    private static int getMaxIndexForMinCharSelection(int start, int end, int[] chars, int[] requiredCounts, int[] selectedCounts){
        int index = start;
        for (int i = start+1; i <= end; i++){
            int c = chars[i];
            if (c <= chars[index] && requiredCounts[c] - selectedCounts[c] > 0){
                index = i;
            }
        }
        return index;
    }

    private static boolean isRequiredToSelect(int totalRequired, int selected, int left) {
        return totalRequired-selected == left;
    }
}
