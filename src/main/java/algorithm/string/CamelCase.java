package algorithm.string;

import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/camelcase/problem
 * Find number of words in a camel case string
 */
public class CamelCase {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        System.out.println(countWords(s));
        test();
    }

    static void test() {
        int i = 'A';
        char c = (char) i;
        System.out.println(c);
    }

    static int countWords(String s) {
        if (s == null || s.isEmpty())
            return 0;
        int count = 1;
        for (int i = 1; i < s.length(); i++) {
            if (isUpperCase(s.charAt(i)))
                count += 1;
        }
        return count;
    }

    static boolean isUpperCase(char c) {
        return Character.isUpperCase(c);
        //return c >=65 && c <= 90;
    }
}
