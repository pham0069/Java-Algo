package algorithm.implementation;

import java.util.ArrayList;
import java.util.List;

public class DigitSum {
    static long[] factorial = new long[21];

    public static void main(String[] args) {
        int n = 2;float f = (float)1.2;
        int m = 4;
        Combination c = getCombination(n, m);
        System.out.println(c);

        fillFactorial();

        System.out.println(getNumberOfNumbersFromCombination(c, n));
    }

    static void fillFactorial() {
        factorial[0] = 1;
        for (int i = 1; i <= 20; i++) {
            factorial[i] = factorial[i-1]*i;
        }
    }

    static class Set {
        int[] selection = new int[10];
        Set() {}
        Set(Set copy) {
            for (int i = 0; i < copy.selection.length; i++) {
                selection[i] = copy.selection[i];
            }
        }
        void setDigit(int digit, int count) {
            selection[digit] = count;
        }
        int getDigitCount(int digit) {
            return selection[digit];
        }

        public String toString() {
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i <= 9; i++) {
                if (selection[i] > 0) {
                    for (int j = 0; j < selection[i]; j++) {
                        buffer.append(i);
                    }
                }
            }
            buffer.append("\n");
            return buffer.toString();
        }
    }

    static class Combination {
        List<Set> sets = new ArrayList<>();
        void add(Set set) {
            sets.add(set);
        }

        public String toString() {
            return sets.toString();
        }
    }

    static Combination getCombination(int n, int m) { // number of digits = n <=20, count = m <= 30
        Combination[][][] table = new Combination[m+1][n+1][10];
        int sum, digitNum, min;

        // sum = 0, min = 0, digitNum = any digit
        for (digitNum = 1; digitNum <= n; digitNum++) {
            Combination combination = new Combination();
            Set set = new Set();
            set.setDigit(0, digitNum);
            combination.add(set);

            table[0][digitNum][0] = combination;
        }

        // sum = 0, digitNum = 0, min = any digit
        for (min = 0; min <= 9; min++) {
            Combination combination = new Combination();
            combination.add(new Set());

            table[0][0][min] = combination;
        }

        for(sum = 1; sum <= m; sum++) {
            for (digitNum = 1; digitNum <= n; digitNum++) {
                for (min = 9; min >= 0; min--) {
                    if (sum < min) {
                        continue;
                    }
                    Combination combination = null;

                    // create new set with a digit = min
                    if (table[sum-min][digitNum-1][min] != null) {
                        combination = new Combination();

                        for (Set copy : table[sum-min][digitNum-1][min].sets) {
                            Set set = new Set(copy);
                            set.setDigit(min, copy.getDigitCount(min)+1);
                            combination.add(set);
                        }
                    }

                    // copy set with min digit larger than current min
                    int prev = min+1;
                    if (prev <= 9 && table[sum][digitNum][prev] != null) {
                        for (Set copy : table[sum][digitNum][prev].sets) {
                            if (combination == null) {
                                combination = new Combination();
                            }
                            combination.add(new Set(copy));
                        }
                    }



                    table[sum][digitNum][min] = combination;
                }
            }
        }

        return table[m][n][0];
    }

    static long getNumberOfNumbersFromCombination(Combination combination, int n) {
        long result = 0;
        for (Set set : combination.sets) {
            result += getNumberOfNumbersFromSet(set, n);
        }
        return result;
    }

    static long getNumberOfNumbersFromSet(Set set, int n) {
        long all = getNumberOfPermutations(set, n);
        if (set.selection[0] == 0) {
            return all;
        } else {
            Set excludeOneZero = new Set(set);
            excludeOneZero.selection[0] = set.selection[0]-1;
            return all - getNumberOfPermutations(excludeOneZero, n-1);
        }
    }

    static long getNumberOfPermutations(Set set, int n) {
        long result = factorial[n];
        for (int i = 0; i <= 9; i++) {
            result /= factorial[set.selection[i]];
        }
        return result;
    }
}
