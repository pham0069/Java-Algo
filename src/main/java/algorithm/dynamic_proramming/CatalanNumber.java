package algorithm.dynamic_proramming;

/**
 * https://www.geeksforgeeks.org/program-nth-catalan-number/
 *
 * Catalan numbers are a sequence of numbers that occurs in many interesting counting problems like following:
 * 1) Count the number of expressions containing n pairs of parentheses which are correctly matched.
 * For n = 3, possible expressions are ((())), ()(()), ()()(), (())(), (()()).
 * 2) Count the number of possible Binary Search Trees with n keys
 * 3) Count the number of full binary trees (i.e. each node has 2 or 0 child nodes)
 *
 * The first few Catalan numbers for n = 0, 1, 2, 3, … are 1, 1, 2, 5, 14, 42, 132, 429, 1430, 4862, …
 * Catalan numbers satisfy the following recursive formula.
 * C[0] = 1
 * C[n+1] = Sigma (C[i]*C[n-i]) for i=0..n
 */
public class CatalanNumber {
    public static void main(String[] args){
        System.out.println(getCatalanNumber(7)) ;
    }
    //iterative
    private static long getCatalanNumber(int n){
        long[] catalans = new long[n+1];
        catalans[0] = 1;
        for (int i = 1; i <= n; i++){
            for (int j = 0; j < i; j++)
                catalans[i] += catalans[j]*catalans[i-1-j];
        }
        return catalans[n];
    }
}
