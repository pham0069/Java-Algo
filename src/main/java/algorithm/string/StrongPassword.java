package algorithm.string;

import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/two-characters/problem
 *
 * The website considers a password to be strong if it satisfies the following criteria:
 * Its length is at least 6.
 * It contains at least one digit.
 * It contains at least one lowercase English character.
 * It contains at least one uppercase English character.
 * It contains at least one special character. The special characters are: !@#$%^&*()-+
 *
 * Find number of min chars to add to a given string to get strong password
 */
public class StrongPassword {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
        String s = sc.next();
        System.out.println(formStrongPassword(s));
    }

    static int formStrongPassword(String s) {
        int addition = 0;
        if (!hasDigit(s))
            addition += 1;
        if (!hasUpperCase(s))
            addition += 1;
        if (!hasLowerCase(s))
            addition += 1;
        if (!hasSpecialChar(s))
            addition += 1;
        addition = Math.max(addition, 6-s.length());
        return addition;
    }

    static boolean hasDigit(String s) {
        return s.matches(".*[0-9].*");
    }

    static boolean hasUpperCase(String s) {
        return s.matches(".*[A-Z].*");
    }

    static boolean hasLowerCase(String s) {
        return s.matches(".*[a-z].*");
    }

    static boolean hasSpecialChar(String s) {
        //Without \\Q and \\E, dash - does not work properly
        return s.matches(".*[\\Q!@#$%^&*()-+\\E].*");
    }
}
