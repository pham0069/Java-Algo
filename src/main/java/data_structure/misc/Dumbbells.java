package data_structure.misc;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Dumbbells {
    static Map<Integer, List<Combination>> result = new HashMap<>(800);
    static DecimalFormat format = new DecimalFormat("0.00");
    static int n = 4;
    static int m = 10;

    //out of 502
    //static int[] w = {87, 113, 183, 249};       //502
    //static int[] w = {97, 113, 183, 247};     //456
    //static int[] w = {101, 127, 181, 257};    //451
    //static int[] w = {100, 125, 185, 255};    //300
    //static int[] w = {100, 125, 200, 250};    //76
    static int[] w = {200, 400, 600, 800};    //32

    public static void main(String args[]) {
        int tries = 0;
        for (int a = 0; a <= n; a++) {
            for (int b = 0; b <= n; b++) {
                for (int c = 0; c <= n; c++) {
                    if ((a+b+c) > m)
                        break;

                    for (int d = 0; d <= n; d++) {
                        int s = (a+b+c+d);
                        if (s == 0)
                            continue;
                        if (s > m)
                            break;

                        tries ++;
                        Combination combination = new Combination(a, b, c, d);
                        if (!result.containsKey(combination.weight)) {
                            result.put(combination.weight, new ArrayList<>());
                        }

                        result.get(combination.weight).add(combination);
                    }
                }
            }
        }

        System.out.println("Number of combinations = " + tries);
        System.out.println("Number of weight levels = " + result.size());
        int i = 1;
        Map<Integer, List<Combination>> sorted = new TreeMap<>(result);

        for (List<Combination> list: sorted.values()) {
            System.out.println(String.format("%d)\t %s", i, list.get(0)));
            i++;
        }
    }

    static class Combination {
        int a;
        int b;
        int c;
        int d;
        int items;
        int weight;

        Combination(int a, int b, int c, int d){
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
            this.items = a+b+c+d;
            this.weight = w[0]*a + w[1]*b + w[2]*c + w[3]*d;
        }

        public String toString() {
            return String.format("%s\t\t%d\t%d-%d-%d-%d", format.format(weight/100.0), items, a, b, c, d);
        }
    }
}
