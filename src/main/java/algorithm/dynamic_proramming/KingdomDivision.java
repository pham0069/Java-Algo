package algorithm.dynamic_proramming;

import java.util.*;

/**
 * https://www.hackerrank.com/challenges/kingdom-division/problem
 *
 * King Arthur has a large kingdom that can be represented as a tree, where nodes correspond to cities and edges
 * correspond to the roads between cities. The kingdom has a total of N cities numbered from 1 to N.
 * The King wants to divide his kingdom between his two children, Reggie and Betty, by giving each of them 0 or more
 * cities. However, they don't get along so he must divide the kingdom in such a way that they will not invade each
 * other's cities. The first sibling will invade the second sibling's city if the second sibling has no other cities
 * directly connected to it. Given a map of the kingdom's N cities, find and print the number of ways King Arthur can
 * divide it between his two children such that they will not invade each other. As this answer can be quite large,
 * it must be modulo 10^9+7.
 * ---------------------------------------------------------------------------------------------------------------------
 * Input Format:
 * The first line contains a single integer denoting N (the number of cities in the kingdom). Each of the (N-1)
 * subsequent lines contains two space-separated integers, u and v, describing a road connecting cities u, v.
 * Constraints: 2 <= N <= 10^5, 1 <= u, v <= N
 * It is guaranteed that all cities are connected.
 * 2 <= N<= 20 for 40% of the maximum score.
 * Output Format:
 * Print the number of ways to divide the kingdom such that the siblings will not invade each other, modulo 10^9+7.
 * ---------------------------------------------------------------------------------------------------------------------
 * Sample Input:
 5
 1 2
 1 3
 3 4
 3 5
 * Sample Output
 * 4
 * Explanation
 * There are 4 ways to divide the cities so that they cannot invade each other:
 * 1.  R - 1, 2, 3, 4, 5
 * 2. B - 1, 2, 3, 4, 5
 * 3. R - 1, 2, B - 3, 4, 5
 * 4. R - 3, 4, 5, B - 1, 2
 * ---------------------------------------------------------------------------------------------------------------------
 * Take example of node 1 with 2 children nodes 2 and 3. We call the tree rooted at node 1 as 1-rooted tree
 * 1-rooted tree is good if the color assignment of the tree allows peaceful kingdom division
 * 1-rooted tree is nearly good  if all its subtrees (i.e. the trees rooted at its children nodes, 2 and 3) are good, but
 * the color of the root node 1 spoils the whole tree's good config. This happens when node 1 has different color from
 * the color of all of its children (i.e. 2, 3)
 *
 * Denote G1r as the number of good configurations for node 1 when it is red
 * Denote B1r as the number of nearly good configurations for node 1 when it is red
 * Obviously by symmetry: G1r = G1b, B1r = B1b.
 *
 * If node 1 is red, 1-rooted tree is nearly good if: node 2 and 3 are blue, and 2-rooted and 3-rooted trees are good
 * --> B1r = G2b * G3b
 * If node 1 is red, 1-rooted tree is good in the following cases:
 * 1. If node 2 is red, 2-rooted tree could be either good or nearly good. The connection to red node 1 helps to prevent
 * node 2 from being invaded if 2-rooted tree is nearly good
 * 2. If node 2 is blue, 2-rooted tree must be good. Otherwise, the red node 1 could not save the vulnerability of node 2.
 * In short, 2-rooted tree can be red good, red nearly good and blue good. It is the same for node 3.
 * 3. However, node 2 and node 3 cannot be blue at the same time, otherwise node 1 becomes vulnerable to be invaded
 * --> G1r = (G2b + G2r + B2r) * (G3b + G3r + B3r) - G2b * G3b
 *
 * Since the city map is a tree, we can take any node as the tree root and do DFS to get the tree leaves. The base case
 * is applied to the leaf node where Glr = Glb = 0, Blr = Blb = 1.
 */
public class KingdomDivision {
    private static final int MOD = 1000000007;
    private static boolean[] visited;
    private static Map<Integer, List<Integer>> connectionMap;
    private static long[] good;
    private static long[] nearly;

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        connectionMap = new HashMap<>();
        for (int i = 0; i < n-1; i++){
            int u = sc.nextInt();
            int v = sc.nextInt();
            connectionMap.putIfAbsent(u, new ArrayList<>());
            connectionMap.get(u).add(v);
            connectionMap.putIfAbsent(v, new ArrayList<>());
            connectionMap.get(v).add(u);
        }

        visited = new boolean[n+1];
        good = new long[n+1];
        nearly = new long[n+1];
        int root = 1;
        dfs(root);
        System.out.println((good[root] * 2)%MOD);
    }

    private static void dfs(int currentNode){
        List<Integer> children = new ArrayList<>();
        visited[currentNode] = true;
        for (int node : connectionMap.get(currentNode)){
            if (!visited[node]){
                children.add(node);
                dfs(node);
            }
        }
        if (children.isEmpty()){
            good[currentNode] = 0;
            nearly[currentNode] = 1;
        } else {
            good[currentNode] = 1;
            nearly[currentNode] = 1;
            long totalSubtrees = 1, excludedSubtrees = 1;
            for (int child : children) {
                nearly[currentNode] *= good[child];
                nearly[currentNode] %= MOD;
                totalSubtrees *= (2*good[child] + nearly[child]);
                totalSubtrees %= MOD;
                excludedSubtrees *= good[child];
                excludedSubtrees %= MOD;
            }
            good[currentNode] = (totalSubtrees - excludedSubtrees + MOD) % MOD;
        }

    }

}
