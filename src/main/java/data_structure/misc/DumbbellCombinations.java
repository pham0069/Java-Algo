package data_structure.misc;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DumbbellCombinations {
    static long[] factorial = new long[11];
    static long[] perm4 = new long[5];
    static long[] comb10 = new long[11];
    static Map<BigDecimal, Combination> result = new HashMap<>(800);

    public static void main(String args[]) {
        fillFactorial();
        fillPerm4();
        fillComb10();

        for (int a = 0; a <= 4; a++) {
            for (int b = 0; b <= 4; b++) {
                for (int c = 0; c <= 4; c++) {
                    if ((a+b+c) > 10)
                        break;

                    for (int d = 0; d <= 4; d++) {
                        int s = (a+b+c+d);
                        if (s== 0)
                            continue;
                        if (s > 10)
                            break;

                        Combination combination = new Combination(a, b, c, d);
                        if (!result.containsKey(combination.weight)) {
                            result.put(combination.weight, combination);
                        } else {
                            System.out.println("2 combinations with same total weight " + result.get(combination.weight) + " " + combination);
                            if (result.get(combination.weight).items > combination.items) {
                                result.put(combination.weight, combination);
                            }
                        }
                    }
                }
            }
        }

        long p = 0;
        for (Combination c: result.values()) {
            long arrangements = numberOfArrangements(c);
            System.out.println(String.format("%s\t%d", c, arrangements));
            p+= arrangements;
        }
        System.out.println("Total = " + p);
    }

    static long numberOfArrangements(Combination c){
        return numberOfArrangements(c.a, c.b, c.c, c.d, c.items);
    }

    static long numberOfArrangements(int a, int b, int c, int d, int s){
        return comb10[s]*repeatedPermutation(a, b, c, d)*perm4[a]*perm4[b]*perm4[c]*perm4[d];
    }

    static void fillFactorial() {
        factorial[0] = 1;
        for (int i = 1; i < 11; i++) {
            factorial[i] = factorial[i-1] * i;
        }
    }

    static void fillPerm4() {
        for (int i = 0; i < 5; i++) {
            perm4[i] = permutation(i, 4);
        }
    }

    static void fillComb10() {
        for (int i = 0; i < 11; i++) {
            comb10[i] = combination(i, 10);
        }
    }

    /**
     * Pnm = m*(m-1)*..(m+1-n)
     */
    static long permutation(int n, int m) {
        if (n > m)
            throw new RuntimeException("Are u sure?");
        return factorial[m]/factorial[m-n];
    }

    /**
     * Cnm = m*(m-1)*..(m+1-n)/n!
     */
    static long combination(int n, int m) {
        return permutation(n, m) / factorial[n];
    }

    /**
     * (a+b+c+d)!/(a!b!c!d!)
     */
    static long repeatedPermutation(int a, int b, int c, int d) {
        return factorial[a+b+c+d]/(factorial[a]*factorial[b]*factorial[c]*factorial[d]);
    }

    static class Combination {
        int a;//1
        int b;//1.25
        int c;//1.85
        int d;//2.55
        int items;
        BigDecimal weight;

        Combination(int a, int b, int c, int d){
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
            this.items = a+b+c+d;
            long w = 100*a + 125*b + 185*c + 255*d;
            this.weight = BigDecimal.valueOf(w/100.0);
        }

        public String toString() {
            return String.format("%d-%d-%d-%d/%d/%f", a, b, c, d, items, weight);
        }
    }
}
