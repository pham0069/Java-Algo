package algorithm.search;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * https://www.hackerrank.com/challenges/maximizing-mission-points/problem
 *
 * Xander Cage has a list of cities he can visit on his new top-secret mission.
 * He represents each city as a tuple of (latitude, longitude, height, points).
 * The values of latitude, longitude, and height are distinct across all cities.
 *
 * We define a mission as a sequence of cities, c1, c2 ,c3, ... ck, that he visits.
 * We define the total points of such a mission to be the sum of the points of all the cities in his mission list.
 *
 * Being eccentric, he abides by the following rules on any mission:
 *
 * 1. He can choose the number of cities he will visit (if any).
 * 2. He can start the mission from any city.
 * 3. He visits cities in order of strictly increasing height.
 * 4. The absolute difference in latitude between adjacent visited cities in his mission must be at most d1.
 * 5. The absolute difference in longitude between adjacent visited cities in his mission must be at most d2.
 * Given d1, d2, and the definitions for n cities, find and print the maximum possible total points that Xander can earn on a mission.
 *
 * Constraints
 * 1<= n <= 2*10^5
 * 1 <= d1, d2 <= 2*10^5
 * 1 <= latitude, longitude, height <= 2*10^5
 * -2*10^5 <= points <= 2*10^5
 *
 * Some approaches
 * 1. DP
 * Maintain an array mp[]
 * where mp[i] is the max points for a mission that ends with city[i]
 * O(n^2) -> timeout
 *
 * 2. KD tree, Oct tree, Quad tree ?
 * Only search for prev cities that are in interested neighborhood
 * Can use grid
 *
 * 3. Editorial
 * Basic Idea (divide and conquer):
 * dp(i) = best score I can obtain when my subsequence has ith point as the last one.
 *
 * a) Solve for the left half. After this step dp(i) for all i in the left half is complete.
 * b) Merge the subproblems.
 * What this means is that we calculate dp(i) for all i in the right half,
 * assuming that the previous adjacent point (if any) was necessarily from the left half.
 * After this step, dp(i) for all i in right half is only partially complete.
 * (We are yet to process the cases when i is in right half and so is the previous point)
 * c) Solve for the right half. Now dp(i) is calculated for the whole range
 *
 * How to do step 2 (merging) ?
 * - Sort the points (in left and right) by their x co-ordinates,
 * and iterate over the points in the right half in increasing order.
 *
 * - For a point in the right half:
 * Remove all points in left half which are at a distance greater than d1 behind it,
 * and add all points in the right half which are at a distance less than or equal to d1 ahead of it.
 * In other words, only points (of the left half) which are within absolute distance (x - coordinate only) X of this point (of the right half), are "active".
 * Among all active points, query the one which has y - coordinate within d2 , and has maximum value.
 *
 * - For a point in the left half: maintain a segment tree which supports point updates and range max queries:
 * i = longitude
 * To add it, set Y[i] = DP[i]
 * To remove it, set Y[i] = -infinity
 */
public class MaximizingMissionPoints {
    static class City implements Comparable<City> {
        int latitude;
        int longitude;
        int height;
        int points;
        long bestScore;

        City(int latitude, int longitude, int height, int points) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.height = height;
            this.points = points;
            this.bestScore = points;
        }

        public int compareTo (City that) {
            if (that.bestScore > bestScore) {
                return 1;
            } else {
                return -1; // Never return 0 because we don't want cities to compare equal
            }
        }
    }

    //=============================================================================
    /**
     * Divide the whole map into grids of size d1*d2
     * Given a city's location, those cities within d1-diff in latitude and d2-diff in longitude
     * must be in same grid or +/- 1 from this city's grid
     * We can store the maxPoints achievable of a trip ended at each city in the grid containing the city
     * in the descending order
     * For each city, we dont have to search the whole range from first city to the city previous to this city
     * for the best score. We only need to search those in neighborhood area
     */

    static long maxPoints(City[] cities, int d1, int d2, int minLat, int maxLat, int minLon, int maxLon) {
        return new CityScorer(cities, d1, d2, minLat, maxLat, minLon, maxLon).bestScore();
    }
    static class CityScorer {
        class CitySet extends TreeSet<City> {}
        CitySet[][] grid;
        static final int GRID_MAX = 1000; // Grid size cannot exceed 1000 x 1000

        CityScorer(City[] cities, int d1, int d2, int minLat, int maxLat, int minLon, int maxLon) {
            // sort cities in height order
            Comparator<City> heightComp = Comparator.comparing((City c) -> c.height);
            Arrays.sort(cities, heightComp);

            // if number of grid is small, better no grid division
            int gridX = Math.min( GRID_MAX, (maxLon-minLon+d2)/d2 );
            if (gridX <= 5) gridX = 1;
            int gridY = Math.min( GRID_MAX, (maxLat-minLat+d1)/d1 );
            if (gridY <= 5) gridY = 1;

            grid = new CitySet[gridY][gridX];
            int rectWidth = (maxLon-minLon+gridX)/gridX;
            int rectHeight = (maxLat-minLat+gridY)/gridY;

            for (City c : cities) {
                // Prepare to find the city within the grid; must search +- 1 square in each direction
                int x = (c.longitude-minLon)/rectWidth;
                int y = (c.latitude-minLat)/rectHeight;
                int xBegin = Math.max(x-1, 0);
                int xEnd = Math.min(x+1, gridX-1);
                int yBegin = Math.max(y-1,0);
                int yEnd = Math.min(y+1, gridY-1);
                long best = 0;
                for (int i=yBegin; i <= yEnd; ++i) {
                    for (int j = xBegin; j <= xEnd; ++j) {
                        if (grid[i][j] == null) continue;
                        for (City d : grid[i][j]) { // Find highest scoring city in the grid square
                            if (Math.abs(c.latitude - d.latitude) <= d1 && Math.abs(c.longitude - d.longitude) <= d2) {
                                best = Math.max(best, d.bestScore);
                                break;
                            }
                        }
                    }
                }
                // At this point bestScore is the highest value in adjacent grid squares
                c.bestScore += best;
                if (c.bestScore > bestScore) bestScore = c.bestScore;

                // Add the city to grid[y][x]
                if (grid[y][x] == null) {
                    grid[y][x] = new CitySet();
                }
                grid[y][x].add(c);
            }
        }
        private long bestScore = 0;
        public long bestScore() { return bestScore; }
    }

    //=============================================================================
    static class Pair implements Comparable<Pair> {
        int first;
        int second;
        Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public int compareTo(Pair that) {
            if (this.first == that.first) {
                return this.second - that.second;
            }
            return this.first - that.first;
        }
    }

    //index = city longitude, value = best score of that city
    static class SegmentTree {
        int MAX_LEN = 400_000; // max longitude + max d2
        int len;
        long[] tree;

        SegmentTree() {
            this.len = MAX_LEN;
            this.tree = new long[getTreeSize(len)];
            for (int i = 0; i < tree.length; i++) {
                tree[i] = Integer.MIN_VALUE;
            }
        }

        private int getTreeSize(int n) {
            int m = (int) Math.ceil(Math.log(n) / Math.log(2));
            return 2 * (int) Math.pow(2, m) - 1;
        }

        private long query(int start, int end) {
            return query(start, end, 0, len - 1, 0);
        }

        private long query(int start, int end, int low, int high, int root) {
            // note that if low == high, only 2 cases: no overlap or total overlap

            // no overlap
            if (start > high || end < low) {
                return Integer.MIN_VALUE;
            }

            // total overlap
            if (start <= low && end >= high) {
                return tree[root];
            }

            // partial overlap
            int mid = (low + high) / 2;
            long left = query(start, end, low, mid, root * 2 + 1);
            long right = query(start, end, mid + 1, high, root * 2 + 2);
            return Math.max(left, right);
        }

        private void update(int index, long newVal) {
            update(index, newVal, 0, len - 1, 0);
        }

        private void update(int index, long newVal, int low, int high, int root) {
            // index not in this range -> no update
            if (index < low || index > high) {
                return;
            }

            if (low == high) {
                if (low == index) {
                    tree[root] = newVal;
                }
                return;
            }

            // low <= index <= high
            int mid = (low + high) / 2;
            if (index <= mid) {
                update(index, newVal, low, mid, root * 2 + 1);
            } else {
                update(index, newVal, mid + 1, high, root * 2 + 2);
            }
            tree[root] = Math.max(tree[root * 2 + 1], tree[root * 2 + 2]);
        }
    }

    static void merge(int low, int high, long[]mp, City[] cities, SegmentTree tree, int d1, int d2) {
        int mid = (low + high) / 2;

        // sort left cities in increasing latitude order
        Pair[] left = new Pair[mid-low+1];
        for (int i = low; i <= mid; ++i) {
            left[i-low] = new Pair(cities[i].latitude, i);
        }
        Arrays.sort(left);

        // sort right cities in increasing latitude order
        Pair[] right = new Pair[high-mid];
        for (int i = mid + 1; i <= high; ++i) {
            right[i-mid-1] = new Pair(cities[i].latitude, i);
        }
        Arrays.sort(right);

        int indexLowerBound = 0;
        int indexUpperBound = -1;
        // for a point in the right half, find a point in the left half
        // such that its difference in latitude and longitude <= d1 and d2 respectively
        for (Pair pair : right) {
            int index = pair.second;
            int latitude = pair.first;


            while (indexUpperBound + 1 < left.length && left[indexUpperBound + 1].first - latitude <= d1) {
                ++indexUpperBound;
                int who = left[indexUpperBound].second;
                tree.update(cities[who].longitude, mp[who]);
            }

            while (indexLowerBound < left.length && latitude - left[indexLowerBound].first > d1) {
                int who = left[indexLowerBound].second;
                tree.update(cities[who].longitude, Long.MIN_VALUE);
                ++indexLowerBound;
            }

            int longitudeLowerBound = Math.max(1, cities[index].longitude - d2);
            int longitudeUpperBound = Math.min(Integer.MAX_VALUE, cities[index].longitude + d2);
            mp[index] = Math.max(mp[index], cities[index].points + tree.query(longitudeLowerBound, longitudeUpperBound));
        }

        // reset tree before next merge
        while (indexLowerBound <= indexUpperBound) {
            int who = left[indexLowerBound].second;
            tree.update(cities[who].longitude, Long.MIN_VALUE);
            ++indexLowerBound;
        }
    }

    static void solve(int low, int high, long[]mp, City[] cities, SegmentTree tree, int d1, int d2) {
        if (low == high) {
            mp[low] = Math.max(mp[low], cities[low].points);
            return;
        }
        int mid = (low + high) / 2;
        solve(low, mid, mp, cities, tree, d1, d2);
        merge(low, high, mp, cities, tree, d1, d2);
        solve(mid + 1, high, mp, cities, tree, d1, d2);
    }

    static long maxPoints3(int d1, int d2, City[] cities) {
        Comparator<City> heightComp = Comparator.comparing((City c) -> c.height);
        Arrays.sort(cities, heightComp);

        SegmentTree tree = new SegmentTree();
        long mp[] = new long[cities.length];
        solve(0, cities.length-1, mp, cities, tree, d1, d2);

        return Arrays.stream(mp).max().orElse(0);
    }

    //=============================================================================
    // O(n^2) timeout overflow
    // simply brute-force search for previous cities for best score
    static int maxPoints2(int d1, int d2, City[] cities) {
        Comparator<City> heightComp = Comparator.comparing((City c) -> c.height);
        Arrays.sort(cities, heightComp);
        int mp[] = new int[cities.length];
        int maxPoints = Integer.MIN_VALUE;

        for (int i = 0; i < cities.length; i++) {
            int mpi = cities[i].points;
            for (int j = 0; j < i; j++) {
                if (getLatitudeDelta(cities[i], cities[j]) > d1
                        || getLongitudeDelta(cities[i], cities[j]) > d2) {
                    continue;
                }
                mpi = Math.max(mpi, cities[i].points + mp[j]);
            }
            mp[i] = mpi;
            maxPoints = Math.max(maxPoints, mpi);
        }
        return maxPoints;
    }

    static int getLatitudeDelta(City a, City b) {
        return Math.abs(a.latitude - b.latitude);
    }

    static int getLongitudeDelta(City a, City b) {
        return Math.abs(a.longitude - b.longitude);
    }




    //============================================================

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String[] nD_latD_long = scanner.nextLine().split(" ");

        int n = Integer.parseInt(nD_latD_long[0]);

        int d1 = Integer.parseInt(nD_latD_long[1]);

        int d2 = Integer.parseInt(nD_latD_long[2]);

        City[] cities = new City[n];

        int minLatitude = Integer.MAX_VALUE, minLongitude = Integer.MAX_VALUE;
        int maxLatitude = Integer.MIN_VALUE, maxLongitude = Integer.MIN_VALUE;

        for (int nItr = 0; nItr < n; nItr++) {
            String[] latitudeLongitude = scanner.nextLine().split(" ");

            int latitude = Integer.parseInt(latitudeLongitude[0]);

            int longitude = Integer.parseInt(latitudeLongitude[1]);

            int height = Integer.parseInt(latitudeLongitude[2]);

            int points = Integer.parseInt(latitudeLongitude[3]);

            cities[nItr] = new City(latitude, longitude, height, points);

            minLatitude = Math.min(minLatitude, latitude);
            minLongitude = Math.min(minLongitude, longitude);
            maxLatitude = Math.max(maxLatitude, latitude);
            maxLongitude = Math.max(maxLongitude, longitude);

        }

        System.out.println(maxPoints3(d1, d2, cities));
        //System.out.println(maxPoints(cities, d1, d2, minLatitude, maxLatitude, minLongitude, maxLongitude));

        scanner.close();
    }
}
