package algorithm.pattern_search;

/**
 * https://www.geeksforgeeks.org/searching-for-patterns-set-5-finite-automata/
 * https://www.geeksforgeeks.org/pattern-searching-set-5-efficient-constructtion-of-finite-automata/
 *
 * In FA based algorithm, we preprocess the pattern and build a 2D array that represents a Finite Automata.
 * Construction of the FA is the main tricky part of this algorithm. Once the FA is built, the searching is simple. In
 * search, we simply need to start from the first state of the automata and the first character of the text. At every
 * step, we consider next character of text, look for the next state in the built FA and move to a new state. If we
 * reach the final state, then the pattern is found in the text.
 *
 * The time complexity of the search process is O(n).
 * The time complexity of constructing the finite automata is O(m^3*NO_OF_CHARS)
 *
 */
public class FiniteAutomata {
    static int NO_OF_CHARS = 256;
    static int getNextState(char[] pattern, int m, int state, int x) {
        // If the character c is same as next character in pattern,then simply increment state
        if(state < m && x == pattern[state])
            return state + 1;

        int nextState, i;

        // ns finally contains the longest prefix which is also suffix in "pattern[0..state-1]x"
        // Start from the largest possible value and stop when you find a prefix which is also suffix
        // i.e. pattern[0..state-1]x = pattern[state-nextState-1..nextState-1]
        for (nextState = state; nextState > 0; nextState--) {
            if (isSuffixPrefix(pattern, x, state-1, nextState-1))
                return nextState;
        }

        return 0;
    }

    /**
     * Check if pattern[0..j-1] = pattern[i-j+1..i] && pattern[j] = x
     * i.e patter[0..j] = pattern[i-j+1..i]x
     * @param pattern
     * @param x
     * @param i index before the endOfSuffix
     * @param j index at the endOfPrefix
     * @return
     */
    static boolean isSuffixPrefix(char[] pattern, int x, int i, int j) {
        if (pattern[j] == x) {
            int startOfSuffix = i-j+1;
            for (int k = 0; k < j; k++)
                if (pattern[k] != pattern[startOfSuffix+k])
                    return false;
            return true;
        }
        return false;
    }

    /* Build TF table which represents Finite Automata for a given pattern */
    static void computeTransitionFunction_naive(char[] pattern, int m, int TF[][]) {
        int state, x;
        for (state = 0; state <= m; ++state)
            for (x = 0; x < NO_OF_CHARS; ++x)
                TF[state][x] = getNextState(pattern, m, state, x);
    }

    static void computeTransitionFunction_efficient(char[] pattern, int m, int TF[][]) {
        int i, longestPrefixAsSuffix = 0, x;
        // Fill entries in first row
        for (x = 0; x < NO_OF_CHARS; x++) {
            TF[0][x] = 0;
        }
        TF[0][pattern[0]] = 1;

        // Fill entries in other rows
        for (i = 1; i <= m; i++) {
            // Copy values from row at index lps
            for (x = 0; x < NO_OF_CHARS; x++) {
                TF[i][x] = TF[longestPrefixAsSuffix][x];
            }

            if (i < m) {
                // Update the entry corresponding to this character
                TF[i][pattern[i]] = i + 1;
                // Update lps for next row to be filled
                longestPrefixAsSuffix = TF[longestPrefixAsSuffix][pattern[i]];
            }
        }
    }

    /* Prints all occurrences of pattern in text */
    static void search(char[] pattern, char[] text) {
        int m = pattern.length;
        int n = text.length;

        int[][] TF = new int[m+1][NO_OF_CHARS];

        computeTransitionFunction_efficient(pattern, m, TF);

        // Process txt over FA.
        int i, state = 0;
        for (i = 0; i < n; i++) {
            state = TF[state][text[i]];
            if (state == m)
                System.out.println("Pattern found at index " + (i-m+1));
        }
    }

    public static void main(String[] args) {
        //char[] text = "AABAACAADAABAAABAA".toCharArray();
        //char[] pattern = "AABA".toCharArray();
        char[] pattern = "on".toCharArray();
        char[] text = "London".toCharArray();
        search(pattern, text);
    }

}
