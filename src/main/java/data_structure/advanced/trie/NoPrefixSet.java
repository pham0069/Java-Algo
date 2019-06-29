package data_structure.advanced.trie;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/no-prefix-set/problem
 *
 * Given N strings, each string contains only lowercase letters from a-j (both inclusive).
 * The set of  strings is said to be GOOD SET if no string is prefix of another, otherwise it is BAD SET.
 * If two strings are identical, they are considered prefixes of each other.
 * For example, aab, abcde, aabcd is BAD SET because aab is prefix of aabcd.
 *
 * Input Format: First line contains N, the number of strings in the set.
 * Then next N lines follow, where  line contains  string.
 * Constraints: 1 <= N <= 10^5, 1 <= string length <= 60
 * Output Format: output GOOD SET if the set is valid. Else, output BAD SET followed by the first string
 * for which the condition fails.
 *
 * Sample Input
 7
 aab
 defgab
 abcde
 aabcde
 cedaaa
 bbbbbbbbbb
 jabjjjad
 * Sample Output
 BAD SET
 aabcde
 */

public class NoPrefixSet {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        TrieWithPrefixCheck trie = new TrieWithPrefixCheck();
        while (n-- > 0){
            String s = sc.next();
            if (trie.insertWithPrefixCheck(s)){
                System.out.println("BAD SET");
                System.out.println(s);
                System.exit(0);
            }
        }
        System.out.println("GOOD SET");
    }
}

class TrieWithPrefixCheck{
    class Node{
        Map<Character, Node> children;
        boolean isCompleteWord;
        public Node(){
            children = new HashMap<>();
            isCompleteWord = false;
        }
    }
    private final Node root = new Node();

    /**
     * insert string to trie and check prefix at the same time
     * return true if s is prefix of another word or s has another word as prefix
     */
    public boolean insertWithPrefixCheck(String s){
        Node node = root;
        boolean hasPrefix = false;
        boolean isPrefix = true;
        for (char c : s.toCharArray()){
            if (node.children.containsKey(c)){
                node = node.children.get(c);
                if (node.isCompleteWord)
                    hasPrefix = true;
            }
            else{
                node.children.putIfAbsent(c, new Node());
                node = node.children.get(c);
                isPrefix = false;
            }
        }
        node.isCompleteWord = true;
        return (isPrefix || hasPrefix);
    }
}


