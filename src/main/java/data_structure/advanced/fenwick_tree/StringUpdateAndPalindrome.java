package data_structure.advanced.fenwick_tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * https://www.geeksforgeeks.org/queries-substring-palindrome-formation/
 *
 * Given a string S consisting of only lower-case characters [a-z].
 * There are two type of possible queries:
 * 1. UPDATE L c: update the string's Lth character to character c.
 * 2. QUERY L R: determine if the characters between position L and R
 * of string S can form a palindrome word where 1 <= L, R <= |S|
 *
 * Input Format
 * The first line contains a string S that only consists of lower-case chars
 * The next line contains an integer Q as number of queries
 * The next Q lines will contain either
 * 1. UPDATE L c
 * 2. QUERY  L R
 *
 * Sample input:
 geeksforgeeks
 5
 UPDATE 4 g
 QUERY 1 4
 QUERY 2 3
 UPDATE 10 t
 QUERY 10 11
 *
 * Sample output: true (geegsforgeeks), true(ee), false(te) for 3 queries
 *
 * We can maintain a Fenwick tree for each lower-case character [a-z]
 * The Fenwick tree for character 'a' has the underlying array such that
 * array[i] = 1 if the character at index i in string s is 'a', and = 0 otherwise
 * Use this Fenwick tree to get range sum at index i will result in the number of
 * characters 'a' present in the s's substring from 0 to i inclusively.
 *
 * A string can be rearranged to form a palindrome if all the char counts are even,
 * except at most 1 char. We can get the char count for each char by querying the
 * corresponding char's Fenwick tree.
 *
 * Time complexity is 2logn for update and 26logn for query, i.e. O(q*logn) in total
 * Space complexity is O(n) to store the char count Fenwick trees where n = string length
 */
public class StringUpdateAndPalindrome {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        try{
            String s = sc.next();
            char[] chars = s.toCharArray();
            int q = sc.nextInt();
            List<FenwickTree> charCountTrees = initCharCountsTrees(s);
            for (int i = 0; i < q; i++){
                String op = sc.next();
                if (op.equals("UPDATE")){
                    int l = sc.nextInt() -1;
                    char oldChar = chars[l];
                    char newChar = sc.next().charAt(0);

                    //update char count tree for both old and new chars
                    charCountTrees.get(oldChar-'a').updateValueBy(l, -1);
                    charCountTrees.get(newChar-'a').updateValue(l, 1);
                    chars[l] = newChar;
                }
                else if (op.equals("QUERY")){
                    int l = sc.nextInt() -1;
                    int r = sc.nextInt() -1;
                    System.out.println(canFormAPalindrome(charCountTrees, l, r));
                }
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private static boolean canFormAPalindrome(List<FenwickTree> charCountTrees, int l, int r){
        int numberOfOddCounts = 0;
        for (FenwickTree bit : charCountTrees){
            long charCount = bit.getSum(l, r);
            if (charCount%2 ==1){
                if (numberOfOddCounts > 0){
                    return false;
                }
                numberOfOddCounts ++;
            }
        }
        return true;
    }

    /**
     * Init a Fenwick tree for each lower-case character [a-z]
     */
    private static List<FenwickTree> initCharCountsTrees(String s){
        List<FenwickTree> charCountTrees = new ArrayList<>();
        for (char c = 'a'; c <= 'z'; c++){
            charCountTrees.add(new FenwickTree(s.length()));
        }

        for (int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            FenwickTree bit = charCountTrees.get(c-'a');
            bit.updateValue(i, 1);
        }
        return charCountTrees;
    }
}
/**
 * 'A' = 0x41 (65 in decimal),'Z' = 0x5A (90 in decimal).
 * 'a' = 0x61 (97 in decimal), 'z' = 0x7A (122 in decimal).
 */
