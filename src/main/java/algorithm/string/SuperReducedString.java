package algorithm.string;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * https://www.hackerrank.com/challenges/reduced-string/problem
 * Steve has a string of lowercase characters in range ascii[‘a’..’z’].
 * He wants to reduce the string to its shortest length by doing a series of operations.
 * In each operation he selects a pair of adjacent lowercase letters that match, and he deletes them.
 * For instance, the string aab could be shortened to b in one operation.
 * Steve’s task is to delete as many characters as possible using this method and print the resulting string.
 * If the final string is empty, print Empty String
 *
 * Note
 * List<Character> chars = Arrays.asList(s.toCharArray()) won't work.
 * The RHS statement will produce List<char[]> in place of List<Character>
 * char[] is considered as single argument value when passed to asList
 * The following is possible
 * List<char[]> asList = Arrays.asList(chars, new char[1]);
 *
 *
 * https://github.com/compuwizipiyu/CoreRepo/tree/BotRepo-Dev
 */
public class SuperReducedString {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        StringBuilder builder = new StringBuilder(s);
        int i = 0;
        while (i < builder.length()-1 && builder.length() != 0) {
            if (builder.charAt(i) == builder.charAt(i+1)) {
                builder.delete(i, i + 2);
                i = Math.max(0, i-1);
            } else {
                i = i+1;
            }
        }

        System.out.println(builder.length()==0 ? "Empty String": builder.toString());
    }
}
