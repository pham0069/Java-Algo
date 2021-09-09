package algorithm.implementation;

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
 * Sample input
 *
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


 *Sample output
 *
 1km = 1000m = 100000cm = 1000000mm
 1km = 1000m = 100000cm = 1000000mm
 1MiB = 8Mib = 1024KiB = 8192Kib = 1048576B = 8388608b
 1MiB = 8Mib = 1024KiB = 8192Kib = 1048576B = 8388608b
 */

/**
 * This does not work for 2nd and 3rd test case
 */
public class Metrics2 {
    static class Conversion {
        private final String unit;
        private final long amount;

        Conversion(String unit, long amount) {
            this.unit = unit;
            this.amount = amount;
        }

        long getAmount() {
            return amount;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int n = scanner.nextInt();
            if (n == 0) {
                break;
            }

            Map<String, Map<String, Integer>> units = new HashMap<>();

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
                units.get(left).put(right, amount);
            }

            // print unit conversions
            printUnits(units);
        }

    }

    static void printUnits(Map<String, Map<String, Integer>> units) {
        List<Conversion> conversions = getConversionsFromBiggestUnitToAllUnits(units);
        if (conversions == null) {
            System.out.println("Cannot convert all units to one another");
            return;
        }

        Collections.sort(conversions, Comparator.comparing(Conversion::getAmount));
        printConversions(conversions);
    }

    static void printConversions(List<Conversion> conversions) {
        StringBuilder builder = new StringBuilder();
        for (Conversion conversion : conversions) {
            if (builder.length() != 0) {
                builder.append(" = ");
            }
            builder.append(conversion.amount).append(conversion.unit);
        }

        System.out.println(builder.toString());
    }

    // return the conversion from the biggest unit to all units
    static List<Conversion> getConversionsFromBiggestUnitToAllUnits(Map<String, Map<String, Integer>> units) {
        Set<String> visited = new HashSet<>();
        int n = units.size();
        for (String unit : units.keySet()) {
            if (visited.contains(unit)) {
                continue;
            }
            visited.add(unit);

            List<Conversion> conversions = breadthFirstSearch(unit, units);
            if (conversions.size() == n) {
                return conversions;
            }
        }
        return null;
    }

    // return the conversions from root unit to lower units
    // tree structure ensured from input, skip check on visited
    static List<Conversion> breadthFirstSearch(String root, Map<String, Map<String, Integer>> units) {
        Deque<Conversion> deque = new ArrayDeque<>();
        List<Conversion> result = new ArrayList<>();

        deque.add(new Conversion(root, 1));
        while (deque.peek() != null) {
            Conversion current = deque.poll();
            result.add(current);
            for (Map.Entry<String, Integer> entry : units.get(current.unit).entrySet()) {
                deque.add(new Conversion(entry.getKey(), current.amount*entry.getValue()));
            }
        }

        return result;
    }
}
