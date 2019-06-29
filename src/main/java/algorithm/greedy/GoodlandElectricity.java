package algorithm.greedy;

import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/pylons/problem
 *
 * Goodland is a country with a number of evenly spaced cities along a line. The distance between adjacent cities is 1
 * unit. There is an energy infrastructure project planning meeting, and the government needs to know the fewest number
 * of power plants needed to provide electricity to the entire list of cities. Determine that number. If it cannot be
 * done, return -1.
 *
 * You are given a list of city data. Cities that may contain a power plant have been labeled 1. Others not suitable
 * for building a plant are labeled 0. Given a distribution range of k, find the lowest number of plants that must be
 * built such that all cities are served. The distribution range limits supply to cities where distance is less than k.
 *
 * For example, you are given k=3 and your city data is arr] [0, 1, 1, 1, 0, 0, 0]. Each city is 1 unit distance from
 * its neighbors, and we'll use 0-based indexing. We see there are 3 cities suitable for power plants, cities 1, 2 and 3.
 * If we build a power plant at arr[2], it can serve arr[0] through arr[4] because those endpoints are at a distance of
 * 2 and 2 < k. To serve arr[6], we would need to be able to build a plant in city 4, 5 or 6. Since none of those is
 * suitable, we must return -1. It cannot be done using the current distribution constraint.
 *
 * Input Format
 *
 * The first line contains two space-separated integers n and k, the number of cities in Goodland and the plants' range
 * constant.
 * The second line contains n space-separated binary integers where each integer indicates suitability for building a
 * plant.
 *
 * Constraints
 * 1 <= k <= n <= 10^5
 * arr[i] in {0, 1}
 * 1 <= k <= n <= 1000 for 40% of the max score
 *
 * Output Format
 *
 * Print a single integer denoting the minimum number of plants that must be built so that all of Goodland's cities
 * have electricity. If this is not possible for the given value of k, print -1.
 *
 * Sample Input
 *
 * 6 2
 * 0 1 1 1 1 0
 * Sample Output
 *
 * 2
 * Explanation
 *
 * Cities indexed 1, 2, 3, and 4 are suitable for power plants. Each plant will have a range of k=2. If we build in
 * 2 cities 1 and 4, then all cities will have electricity.
 *
 */
public class GoodlandElectricity {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();
        int[] hasTower = new int[n];
        for (int i = 0; i < n; i++){
            hasTower[i] = sc.nextInt();
        }
        int onTower, offStart = 0, total = 0;
        int startSearch, endSearch;
        while (offStart < n){
            onTower = -1;
            startSearch = Math.min(n-1, offStart+k-1);
            endSearch =  Math.max(offStart-k+1, 0);
            for (int i = startSearch; i >= endSearch; i--){
                if (hasTower[i] == 1){
                    onTower = i;
                    break;
                }
            }
            if (onTower == -1){
                total = -1;
                break;
            }
            else{
                offStart = onTower+k;
                ++total;
            }
        }

        System.out.println(total);
    }
}
