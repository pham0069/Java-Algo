package algorithm.implementation;

import java.text.DecimalFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Outpost 24
 * The use of units is ubiquitous in science.
 * Physics uses units to distinguish distance (e.g., meters, kilometers, etc.), weight (e.g., kilograms, grams),
 * and many other quantities. Computer scientists have specialized units to describe storage capacity
 * (e.g., kilobytes, megabytes, etc.).
 * You are to write a program to display the conversion factors for a set of units.
 *
 * Specifying the relationship between various units can be done in many different, but equivalent, ways.
 * For example, the units for metric distance can be specified as the group of relationships between pairs for units:
 * 1 km = 1000 m, 1 m = 100 cm, and 1 cm = 10 mm.
 * An alternative set of pairs consists of: 1 km = 100000 cm, 1 km = 1000000 mm, and 1 m = 1000 mm.
 * In either presentation, the same relationship can be inferred: 1 km = 1000 m = 100000 cm = 1000000 mm.
 *
 * For this problem, the units are to be sorted according to their descending size.
 * For example, among the length units cm, km, m, mm, km is considered the biggest unit since 1 km corresponds to a
 * length greater than 1 cm, 1 m, and 1 mm.
 * The remaining units can be sorted similarly. For this set, the sorted order would be: km, m, cm, mm.
 *
 * This problem is limited to unit-systems whose conversion factors are integer multiples.
 * Thus, factors such as 1 inch = 2.54 cm are not considered.
 * Further, the set of units and the provided conversions are given such that all units can be expressed
 * in terms of all other units.
 *
 *
 * Sample input
 4
 km m mm cm
 km = 1000 m
 m = 100 cm
 cm = 10 mm
 4
 m mm cm km
 km = 100000 cm
 km = 1000000 mm
 m = 1000 mm
 6
 MiB Mib KiB Kib B b
 B = 8 b
 MiB = 1024 KiB
 KiB = 1024 B
 Mib = 1048576 b
 Mib = 1024 Kib
 6
 Kib B MiB Mib KiB b
 B = 8 b
 MiB = 1048576 B
 MiB = 1024 KiB
 MiB = 8192 Kib
 MiB = 8 Mib
 0

 * Sample output
 *
 1km = 1000m = 100000cm = 1000000mm
 1km = 1000m = 100000cm = 1000000mm
 1MiB = 8Mib = 1024KiB = 8192Kib = 1048576B = 8388608b
 1MiB = 8Mib = 1024KiB = 8192Kib = 1048576B = 8388608b

 * Sample input
 *
 5
 day week year hour minute
 day = 24 hour
 week = 168 hour
 year = 52 week
 hour = 60 minute
 0


 */
public class Metrics {
    private static DecimalFormat format = new DecimalFormat("#.##");

    static class Conversion {
        private final String unit;
        private Fraction amount;

        Conversion(String unit, Fraction amount) {
            this.unit = unit;
            this.amount = amount;
        }

        Fraction getAmount() {
            return amount;
        }

        void normalize(Fraction normalizer) {
            this.amount = amount.multiply(normalizer);
        }
    }

    static class Fraction implements Comparable<Fraction> {
        int numerator;
        int denominator;

        Fraction(int value) {
            this(value, 1);
        }

        Fraction(int numerator, int denominator) {
            this.numerator = numerator;
            this.denominator = denominator;
        }

        Fraction multiply(Fraction that) {
            int resultNumerator = this.numerator * that.numerator;
            int resultDenominator = this.denominator * that.denominator;
            int gcd = getGcd(resultNumerator, resultDenominator);
            return new Fraction(resultNumerator/gcd, resultDenominator/gcd);
        }

        private int getGcd(int a, int b) {
            return b == 0 ? a : getGcd(b, a%b);
        }

        Fraction reciprocal() {
            return new Fraction(this.denominator, this.numerator);
        }

        @Override
        public int compareTo(Fraction that) {
            return this.numerator * that.denominator - this.denominator * that.numerator;
        }

        public double getValue() {
            return numerator*1.0/denominator;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int n = scanner.nextInt();
            if (n == 0) {
                scanner.nextLine();
                break;
            }

            Map<String, Map<String, Fraction>> units = new HashMap<>();

            // read unit name
            for (int i = 0; i < n; i++) {
                String name = scanner.next();
                units.put(name, new HashMap<>());
            }

            scanner.nextLine();

            // read unit conversion
            for (int i = 0; i < n-1; i++) {
                String[] line = scanner.nextLine().split("=");
                String left = line[0].trim();
                String[] conversion = line[1].trim().split(" ");
                int amount = Integer.parseInt(conversion[0].trim());
                String right = conversion[1].trim();

                units.get(left).put(right, new Fraction(amount));
                units.get(right).put(left, new Fraction(1, amount));
            }

            // print unit conversions
            printUnits(units);
        }

    }

    static void printUnits(Map<String, Map<String, Fraction>> units) {
        List<Conversion> conversions = getSortedConversions(units);
        printConversions(conversions);
    }

    static void printConversions(List<Conversion> conversions) {
        StringBuilder builder = new StringBuilder();
        for (Conversion conversion : conversions) {
            if (builder.length() != 0) {
                builder.append(" = ");
            }
            builder.append(format.format(conversion.amount.getValue())).append(conversion.unit);
        }

        System.out.println(builder.toString());
    }

    // return sorted conversions from the biggest unit to all units
    static List<Conversion> getSortedConversions(Map<String, Map<String, Fraction>> units) {
        List<Conversion> conversions = breadthFirstTraverse(units.keySet().iterator().next(), units);
        Collections.sort(conversions, Comparator.comparing(Conversion::getAmount));
        Fraction normalizer = conversions.get(0).amount.reciprocal();
        conversions.forEach(c -> c.normalize(normalizer));
        return conversions;
    }

    // return the conversions from root unit to other units
    static List<Conversion> breadthFirstTraverse(String root, Map<String, Map<String, Fraction>> units) {
        Deque<Conversion> deque = new ArrayDeque<>();
        List<Conversion> result = new ArrayList<>();
        Set<String> visited = new HashSet<>();

        deque.add(new Conversion(root, new Fraction(1)));
        visited.add(root);

        while (deque.peek() != null) {
            Conversion current = deque.poll();
            result.add(current);
            for (Map.Entry<String, Fraction> entry : units.get(current.unit).entrySet()) {
                String unit = entry.getKey();
                if (!visited.contains(unit)) {
                    deque.add(new Conversion(entry.getKey(), current.amount.multiply(entry.getValue())));
                    visited.add(unit);
                }
            }
        }

        return result;
    }
}
