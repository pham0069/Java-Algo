package data_structure.advanced.implementation;

import java.util.HashMap;
import java.util.Map;

/**
 * Trie is an efficient information reTRIEval DS. It can find a word in dictionary, given the prefix word only.
 */
public class Trie {
    private final Node root = new Node();
    public void insert(String s){
        Node node = root;
        for (char c : s.toCharArray()){
            node.children.putIfAbsent(c, new Node());
            node = node.children.get(c);
        }
        node.isCompleteWord = true;
    }

    public boolean isWord(String s){
        Node node = root;
        for (char c : s.toCharArray()){
            if (!node.children.containsKey(c))
                return false;
            node = node.children.get(c);
        }
        return node.isCompleteWord;
    }

    public boolean delete(String s){
        Node parentNode = root, node = root;
        for (char c : s.toCharArray()){
            parentNode = node;
            node = node.children.get(c);
            if (node == null)
                return false;
        }
        if (!node.children.isEmpty()){
            node.isCompleteWord = false;
        } else{
            parentNode.children.remove(s.charAt(s.length()-1));
        }
        return true;
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
