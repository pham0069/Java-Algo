package data_structure.stack;

import java.util.Scanner;
import java.util.Stack;

/**
 * https://www.hackerrank.com/challenges/simple-text-editor/problem
 *
 * In this challenge, you must implement a simple text editor. Initially, your editor contains an empty string, S. You
 * must perform Q operations of the following 4 types:
 * 1. append(W) - Append string W to the end of S.
 * 2. delete(k) - KDTreeDelete the last k characters of S.
 * 3. print(k) - Print the k-th character of S.
 * 4. undo() - undo the last (not previously undone) operation of type 1 or 2 to bring S to the state it was prior to
 * that operation
 *
 *
 */
public class SimpleTextEditor {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int q = sc.nextInt();
        StringBuffer buffer = new StringBuffer();
        Stack<String> stack = new Stack<>();
        stack.push(buffer.toString());

        for (int i = 0; i < q; i++){
            int query = sc.nextInt();
            switch(query){
                case 1:
                    String a = sc.next();
                    buffer.append(a);
                    stack.push(buffer.toString());
                    break;
                case 2:
                    int k = sc.nextInt();
                    int l = buffer.length();
                    buffer.delete(l-k, l);
                    stack.push(buffer.toString());
                    break;
                case 3:
                    k = sc.nextInt();
                    System.out.println(buffer.charAt(k-1));
                    break;
                case 4:
                    stack.pop();
                    buffer = new StringBuffer(stack.peek());
                    break;
            }
        }
    }
}
