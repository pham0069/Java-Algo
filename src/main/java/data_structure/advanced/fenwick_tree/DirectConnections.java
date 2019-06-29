package data_structure.advanced.fenwick_tree;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/direct-connections/submissions/code/30097066
 *
 * Enter-View (EV) is a linear, street-like country. By linear, we mean all the cities of the country are placed on a
 * single straight line - the x-axis. Thus every city's position can be defined by a single coordinate x_i, the distance
 * from the left borderline of the country. You can treat all cities as single points.
 *
 * Unfortunately, the dictator of telecommunication of EV doesn't know anything about the modern telecom technologies,
 * except for peer-to-peer connections. Even worse, his thoughts on peer-to-peer connections are extremely faulty: he
 * believes that, if P_i people are living in city i, there must be at least P_i cables from city i to every other city
 * of EV - this way he can guarantee no congestion will ever occur!
 *
 * Mr. Treat hires you to find out how much cable they need to implement this telecommunication system, given the
 * coordination of the cities and their respective population. Note that the connections between the cities can be shared.
 *
 * Input Format: A number T is given in the first line and then comes T blocks, each representing a scenario.
 * Each scenario consists of three lines. The first line indicates N, the number of cities. The second line indicates
 * the coordinates of the N cities (may not in order). The third line contains the population of each of the cities.
 *
 * Output Format: For each scenario of the input, write the length of cable needed in a single line modulo 1,000,000,007.
 * Constraints: 1 <= T <= 20, 1 <= N <= 2x10^5, P_i <= 10^4
 * Border to border length of the country <= 10^9
 *
 * Sample Input
 2
 3
 1 3 6
 10 20 30
 5
 5 55 555 55555 555555
 3333 333 333 33 35
 * Sample Output
 280
 463055586
 */
public class DirectConnections {
    private static final int MOD = 1000000007;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();
        for (int i = 0; i < t; i++) {
            int n = scanner.nextInt();
            Country[] countries = new Country[n];
            for (int j = 0; j < n; j++) {
                countries[j] = new Country();
                countries[j].index = j;
                countries[j].location = scanner.nextInt();
            }
            for (int j = 0; j < n; j++) {
                countries[j].population = scanner.nextInt();
            }
            System.out.println(getTotalCableLength(countries));
        }
    }

    public static long getTotalCableLength(Country[] countries){
        int n = countries.length;
        FenwickTree locationTree = new FenwickTree(n);
        FenwickTree countTree = new FenwickTree(n);
        Map<Integer, Integer> countryIndexToLocationIndexMap = new HashMap<>();
        Arrays.sort(countries, (c1, c2) -> c1.location - c2.location);
        for (int i = 0; i < countries.length; i++){
            locationTree.updateValue(i, countries[i].location);
            countTree.updateValue(i, 1);
            countryIndexToLocationIndexMap.put(countries[i].index, i);
        }
        Arrays.sort(countries, (c1, c2) -> c2.population - c1.population);
        long totalCableLength = 0;
        for (int i = 0; i < countries.length; i++){
            int locationIndex = countryIndexToLocationIndexMap.get(countries[i].index);
            long numberOfCountriesOnTheRight = countTree.getSum(locationIndex, n-1);
            long numberOfCountriesOnTheLeft = countTree.getSum(locationIndex);
            long locationSumOfCountriesOnTheRight = locationTree.getSum(locationIndex, n-1);
            long locationSumOfCountriesOnTheLeft = locationTree.getSum(locationIndex);
            long distancesToOtherCountries = locationSumOfCountriesOnTheRight - locationSumOfCountriesOnTheLeft
                    +countries[i].location*(numberOfCountriesOnTheLeft-numberOfCountriesOnTheRight);

            long cableLengthToOtherCountries = countries[i].population * (distancesToOtherCountries%MOD);
            totalCableLength = (totalCableLength+cableLengthToOtherCountries)%MOD;

            locationTree.updateValue(locationIndex, 0);
            countTree.updateValue(locationIndex, 0);
        }
        return totalCableLength;
    }
    static class Country{
        int index;
        int location;
        int population;
    }
}
