package data_structure.stack;

import java.util.Stack;

/**
 * https://www.geeksforgeeks.org/check-for-balanced-parentheses-in-an-expression/
 *
 * Given an expression string exp , write a program to examine whether the pairs and the orders of “{“,”}”,”(“,”)”,”[“,”]”
 * are correct in exp.
 *
 * Example 1: input exp = “[()]{}{[()()]()}”
 * Output: print true
 *
 * Example 2: input exp = “[(])”
 * Output: print false
 *
 */
public class BalancedParentheses {
    public static void main(String[] args) {
        String s = "[()]{}{[()()]()}" ;
        System.out.println(areBalancedParenthesis(s));
    }
    static boolean areBalancedParenthesis(String s) {
        Stack<Character> stack = new Stack<>();

        for(char c: s.toCharArray()) {
            if (isOpenBracket(c)) {
                stack.push(c);
            } else if (isCloseBracket(c)) {
                // If we see an ending parenthesis without a pair then return false
                if (stack.isEmpty()) {
                    return false;
                }// if the popped element is not a pair parenthesis of character then there is a mismatch
                else if (!isMatchingPair(stack.pop(), c)) {
                    return false;
                }
            } else {
                return false;
            }

        }

        //If there is something left in expression then there is a starting parenthesis without a closing parenthesis
        if (stack.isEmpty())
            return true;
        else
            return false;
    }
    static boolean isMatchingPair(char c1, char c2) {
        if (c1 == '(' && c2 == ')')
            return true;
        else if (c1 == '{' && c2 == '}')
            return true;
        else if (c1 == '[' && c2 == ']')
            return true;
        else
            return false;
    }

    static boolean isOpenBracket(char c) {
        return c == '{' ||
                c == '(' ||
                c == '[';
    }

    static boolean isCloseBracket(char c) {
        return c == '}' ||
                c == ')' ||
                c == ']';
    }


}
