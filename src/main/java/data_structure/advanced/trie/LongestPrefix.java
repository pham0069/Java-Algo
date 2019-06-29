package data_structure.advanced.trie;

import java.util.HashMap;
import java.util.Map;

/**
 * https://www.geeksforgeeks.org/longest-prefix-matching-a-trie-based-solution-in-java/
 *
 * Given a dictionary of words and an input string, find the longest prefix of the string
 * which is also a word in dictionary.
 *
 * For example: Let the dictionary contains the following words: {are, area, base, cat, cater, children, basement}
 * Input = caterer, output = cater
 * input = basemexy, output = base
 * input = child, output = null
 */
public class LongestPrefix {
    public static void main(String[] args){
        String[] dictionary = {"are", "area", "base", "cat", "cater", "children", "basement"};
        MyTrie trie = new MyTrie();
        for (String word : dictionary)
            trie.insert(word);

        String[] inputs = {"caterer", "basemexy", "child"};
        for (String input : inputs)
            System.out.println(trie.getLongestPrefixWord(input));

    }
}
class MyTrie {
    private final Node root = new Node();
    public void insert(String s){
        Node node = root;
        for (char c : s.toCharArray()){
            node.children.putIfAbsent(c, new Node());
            node = node.children.get(c);
        }
        node.isCompleteWord = true;
    }
    public String getLongestPrefixWord(String s){
        Node node = root;
        StringBuilder prefixBuilder = new StringBuilder();
        String longestPrefix = null;
        for (char c : s.toCharArray()){
            node = node.children.get(c);
            if (node == null)
                return longestPrefix;
            prefixBuilder.append(c);
            if (node.isCompleteWord)
                longestPrefix = prefixBuilder.toString();
        }
        return longestPrefix;
    }

    class Node{
        Map<Character, Node> children;
        boolean isCompleteWord;
        public Node(){
            children = new HashMap<>();
            isCompleteWord = false;
        }
    }
}
