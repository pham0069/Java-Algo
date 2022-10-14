package algorithm.string;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * https://www.hackerrank.com/challenges/two-characters/problem?isFullScreen=true
 *
 * Given a string, remove characters until the string is made up of any two alternating characters. When you choose a
 * character to remove, all instances of that character must be removed. Determine the longest string possible that
 * contains just two alternating letters.
 *
 * Example
 * s = 'abaacdabd'
 *
 * Delete a, to leave bcdbd. Now, remove the character c to leave the valid string bdbd with a length of 4. Removing
 * either b or d at any point would not result in a valid string. Return 4.
 *
 * Given a string s, convert it to the longest possible string t made up only of alternating characters. Return the
 * length of string t. If no string t can be formed, return 0.
 *
 * Function Description
 *
 * Complete the alternate function in the editor below.
 *
 * alternate has the following parameter(s):
 *
 * string s: a string
 * Returns.
 *
 * int: the length of the longest valid string, or  if there are none
 * Input Format
 *
 * The first line contains a single integer that denotes the length of .
 * The second line contains string .
 *
 * Constraints
 * 1 <=  length of s <= 1000
 * s[i] in ascii[a-z]
 *
 * Sample Input
 * 10
 * beeabeefeab
 *
 * Sample output
 * 5
 */
public class TwoCharacters {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int l = Integer.parseInt(bufferedReader.readLine().trim());

        String s = bufferedReader.readLine();

        int result = getLongestAlternateString(s);
        System.out.println(result);

    }

    static int getLongestAlternateString(String s) {
        Map<Character, List<Integer>> charPosMap = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            charPosMap.computeIfAbsent(s.charAt(i), k -> new ArrayList<>())
                    .add(i);
        }

        int maxLength = 0;
        List<Character> allChars = new ArrayList<>(charPosMap.keySet());
        char first, second;
        List<Integer> firstIndices, secondIndices;

        for (int i = 0; i < allChars.size(); i++) {
            for (int j = i+1; j < allChars.size(); j++) {
                first =  allChars.get(i);
                second = allChars.get(j);
                firstIndices = charPosMap.get(first);
                secondIndices = charPosMap.get(second);
                if (canFormAlternateString(firstIndices, secondIndices)) {
                    System.out.println(first + "  " + second + " " + firstIndices.size() + " " + secondIndices.size()
                    + " " + firstIndices + " " + secondIndices);
                    maxLength = Math.max(maxLength, firstIndices.size() + secondIndices.size());
                }
            }
        }
        return maxLength;
    }

    static boolean canFormAlternateString(List<Integer> firstIndices, List<Integer> secondIndices) {
        if (Math.abs(firstIndices.size() - secondIndices.size()) >= 2) {
            return false;
        }

        int i = 0, j = 0;
        Boolean isFirst = null;
        while(i < firstIndices.size() && j < secondIndices.size()) {
            if (firstIndices.get(i) < secondIndices.get(j)) {
                if (isFirst != null && isFirst) {
                    return false;
                }
                isFirst = true;
                i++;
            }  else {
                if (isFirst != null && !isFirst) {
                    return false;
                }
                isFirst = false;
                j++;
            }
        }
        if (i == firstIndices.size() && j < secondIndices.size()-1) {
            return false;
        }
        if (j == secondIndices.size() && i < firstIndices.size()-1) {
            return false;
        }
        return true;
    }
}
