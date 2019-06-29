package algorithm.search;

import java.util.Arrays;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/bike-racers/problem
 *
 * There are N bikers present in a city (shaped as a grid) having M bikes.
 * Location (of bike/biker) is denoted as coordinates (xi, yi)
 * All the bikers want to participate in the HackerRace competition,
 * but unfortunately only K bikers can be accommodated in the race.
 * Jack is organizing the HackerRace and wants to start the race as soon as possible.
 * He can instruct any biker to move towards any bike in the city.
 * In order to minimize the time to start the race,
 * Jack instructs the bikers in such a way that the first K bikes are acquired in the minimum time.
 *
 * Every biker moves with a unit speed and one bike can be acquired by only one biker.
 * A biker can proceed in any direction.
 * Consider distance between bikes and bikers as Euclidean distance.
 *
 * Jack would like to know the square of required time to start the race as soon as possible.
 *
 * Constraints
 * - 1 <= N <= 250
 * - 1 <= M <= 250
 * - 1 <= K <= min(N, M)
 * - 0 <= xi, yi <= 10^7
 *
 *
 * This is bipartite matching
 * 1 set is N bikers
 * 1 set is M bikes
 * calculate M*N distances btw each bike and biker
 * Find k connections between 2 sets that minimize the sum
 *
 * Editorial
 *
 *
 * Subproblem 1 First try to solve a problem, Given N bikes and M bikers and time T, How many bikers (at max) will be get a bike ? while each bikers can have only one bike and one bike can be accommodated by only one biker.
 *
 * Solution : Make a bipartite graph, Having N node in one partite and having M nove in other partite and connect a node X from first partite to a node Y of second partite iff bike Y is reachable from biker X is given amount of time T.
 *
 * Value of maxium bipartite matching is the solution of subproblem 1
 *
 * Now using this subproblem we can check whether in given amount of Time whether K bikers can get bike or not.
 *
 * Now define a function f in given manner
 *
 * f(T) = true if K bikers can get in bike in square_root(T) time.
 * else false.
 *
 * as it is very obvious that if f(T) = true then f(T1) = true for all T1>=T.
 *
 * and f(0) = false and f(10^16) = true. as all co-ordinates X,Y <= 10^7
 * Now we can use classical binary search to get the value of exact value of T.
 *
 * Subproblem 2 : Binary search can solved using the classical method.
 *
 *
 *
 */
public class BikeRacers {
    static class Point {
        int x;
        int y;
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        long getSquareDistance(Point that) {
            return (that.x-x)*1L*(that.x-x) + (that.y-y)*1L*(that.y-y);
        }
    }

    static long bikeRacers(Point[] bikers, Point[] bikes, int k) {
        long[][] squareDistance = new long[bikers.length][bikes.length];
        long min = Long.MAX_VALUE, max = Long.MIN_VALUE;
        for (int i = 0; i < bikers.length; i++) {
            for (int j = 0; j < bikes.length; j++) {
                squareDistance[i][j] = bikers[i].getSquareDistance(bikes[j]);
                min = Math.min(squareDistance[i][j], min);
                max = Math.max(squareDistance[i][j], max);
            }
        }

        return binarySearch(min, max, k, squareDistance);
    }

    // can improve by using mid value in squareDistance list, instead of mean of low and high
    static long binarySearch(long low, long high, int k, long[][] squareDistance) {
        if (low == high) {
            return low;
        }
        long mid = (low + high)/2;
        int match = bipartiteMatch(mid, squareDistance);
        return match >= k ?
                binarySearch(low, mid, k, squareDistance) :
                binarySearch(mid+1, high, k, squareDistance);
    }

    static int bipartiteMatch(long distanceAllowed, long[][] squareDistance) {
        int n = squareDistance.length;
        int m = squareDistance[0].length;

        //
        int[] bikerMap = new int[m];
        Arrays.fill(bikerMap, -1);

        int match = 0;
        for(int biker = 0; biker < n; ++biker) {
            boolean[] isBikerVisited = new boolean[n];
            // try to match a bike to this biker
            if(dfs(biker, distanceAllowed, squareDistance, isBikerVisited, bikerMap)) {
                match++;
            }
        }
        return match;
    }

    // this recursive method will try to reassign taken bike to another biker if necessary
    static boolean dfs(int biker, long distanceAllowed, long[][] squareDistance, boolean[] isBikerVisited, int[] bikerMap) {
        // biker has been visited in dfs, not consider again
        if(isBikerVisited[biker])
            return false;
        isBikerVisited[biker] = true;

        for(int bike = 0; bike < squareDistance[0].length; ++bike) {
            int prevBiker = bikerMap[bike];
            if(squareDistance[biker][bike] <= distanceAllowed) {
                // bike is not taken or can reassign the prevBiker to another bike
               if (prevBiker == -1 || dfs(prevBiker, distanceAllowed, squareDistance, isBikerVisited, bikerMap)) {
                   bikerMap[bike] = biker;
                   return true;
               }
            }
        }
        return false;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String[] nmk = scanner.nextLine().split(" ");

        int n = Integer.parseInt(nmk[0].trim());

        int m = Integer.parseInt(nmk[1].trim());

        int k = Integer.parseInt(nmk[2].trim());

        Point[] bikers = new Point[n];
        Point[] bikes = new Point[m];

        String[] coordinate;
        int x, y;

        for (int i = 0; i < n; i++) {
            coordinate = scanner.nextLine().split(" ");
            x = Integer.parseInt(coordinate[0].trim());
            y = Integer.parseInt(coordinate[1].trim());
            bikers[i] = new Point(x, y);
        }

        for (int i = 0; i < m; i++) {
            coordinate = scanner.nextLine().split(" ");
            x = Integer.parseInt(coordinate[0].trim());
            y = Integer.parseInt(coordinate[1].trim());
            bikes[i] = new Point(x, y);
        }

        long result = bikeRacers(bikers, bikes, k);

        System.out.println(result);
    }
}
