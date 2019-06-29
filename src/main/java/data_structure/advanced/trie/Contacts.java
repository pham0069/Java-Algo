package data_structure.advanced.trie;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/contacts/problem
 *
 * Our Contacts application must perform two types of operations:
 * 1. add 'name', where 'name' is a string denoting a contact name. This must store name as a new contact in the application.
 * 2. find 'partial', where 'partial' is a string denoting a partial name for the application to search. It must count
 * the number of contacts starting with partial and print the count on a new line.
 * Given N sequential add and find operations, perform them in order.
 *
 * Input Format:
 * The first line contains a single integer, N, denoting the number of operations to perform. Each line  of the N subsequent
 * lines contains an operation in one of the two forms defined above.
 * Constraints:
 * 1 <= n <= 10^5; 1 <= |'name'| <= 21; 1 <= |'partial'| <= 21
 * It is guaranteed that 'name' and 'partial' contain lowercase English letters only. The input doesn't have any duplicate
 * for the add operation.
 * Output Format:
 * For each find find operation, print the number of contact names starting with on a new line.
 *
 * Sample Input:
 4
 add hack
 add hackerrank
 find hac
 find hak
 *
 * Sample Output:
 2
 0
 */
public class Contacts {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        TrieWithDescendantCount trie = new TrieWithDescendantCount();
        while (n-- > 0){
            String query = sc.next();
            String s = sc.next();
            if (query.equals("add"))
                trie.insert(s);
            else if (query.equals("del"))
                trie.delete(s);
            else if (query.equals("find"))
                System.out.println(trie.countPrefix(s));
        }
    }
}

/**
 * Enhanced trie whose nodes additionally maintain the descendants variable, i.e.
 * number of complete words that are descendants of this node, when new string is
 * inserted into the trie.
 * Enhanced trie is efficient for find query above.
 * Time complexity to add is O(s.length)
 * Time complexity to countPrefix is O(1)
 */
class TrieWithDescendantCount {
    private final Node root = new Node();
    public void insert(String s){
        Node node = root;
        if (isWord(s))
            return;
        for (char c : s.toCharArray()){
            node.descendants++;
            node.children.putIfAbsent(c, new Node());
            node = node.children.get(c);
        }
        node.isCompleteWord = true;
        node.descendants++;
    }

    public int countPrefix(String s){
        Node node = root;
        for (char c : s.toCharArray()){
            if (!node.children.containsKey(c))
                return 0;// s is not a prefix of any name
            node = node.children.get(c);
        }
        return node.descendants;
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
        if (!isWord(s))
            return false;
        Node parentNode = root, node = root;
        for (char c : s.toCharArray()){
            node.descendants--;
            parentNode = node;
            node = node.children.get(c);
        }
        if (!node.children.isEmpty()){
            node.isCompleteWord = false;
            node.descendants --;
        } else{
            parentNode.children.remove(s.charAt(s.length()-1));
        }
        return true;
    }

    class Node{
        Map<Character, Node> children;
        boolean isCompleteWord;
        int descendants;
        public Node(){
            children = new HashMap<>();
            isCompleteWord = false;
            descendants = 0;
        }
    }
}