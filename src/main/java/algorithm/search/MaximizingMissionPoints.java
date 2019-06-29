package algorithm.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

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
 */
public class MaximizingMissionPoints {
    static class City {
        int latitude;
        int longitude;
        int height;
        int points;

        City(int latitude, int longitude, int height, int points) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.height = height;
            this.points = points;
        }
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

    static class SegmentTree {
        int len;
        int[] tree;

        SegmentTree(int len) {
            this.len = len;
            this.tree = new int[getTreeSize(len)];
            for (int i = 0; i < tree.length; i++) {
                tree[i] = Integer.MIN_VALUE;
            }
        }

        private int getTreeSize(int n) {
            int m = (int) Math.ceil(Math.log(n) / Math.log(2));
            return 2 * (int) Math.pow(2, m) - 1;
        }

        private int query(int start, int end) {
            return query(start, end, 0, len - 1, 0);
        }

        private int query(int start, int end, int low, int high, int root) {
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
            int left = query(start, end, low, mid, root * 2 + 1);
            int right = query(start, end, mid + 1, high, root * 2 + 2);
            return Math.max(left, right);
        }

        private void update(int index, int newVal) {
            update(index, newVal, 0, len - 1, 0);
        }

        private void update(int index, int newVal, int low, int high, int root) {
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

    static void merge(int low, int high, int[]mp, City[] cities, SegmentTree tree, int d1, int d2) {
        int mid = (low + high) / 2;

        Pair[] left = new Pair[mid-low+1];
        Pair[] right = new Pair[high-mid];
        for (int i = low; i <= mid; ++i) {
            left[i-low] = new Pair(cities[i].latitude, i);
        }

        for (int i = mid + 1; i <= high; ++i) {
            right[i-mid-1] = new Pair(cities[i].latitude, i);
        }

        Arrays.sort(left);
        Arrays.sort(right);

        int left_l = 0;
        int left_r = -1;
        // for a point in the right half, find a point in the left half
        // such that its difference in latitude and longitude <= d1 and d2 respectively
        for (Pair pair : right) {
            int i = pair.second;
            int x = pair.first;

            while (left_r + 1 < left.length && left[left_r + 1].first - x <= d1) {
                ++left_r;
                int who = left[left_r].second;
                tree.update(cities[who].longitude, mp[who]);
            }

            while (left_l < left.length && x - left[left_l].first > d1) {
                int who = left[left_l].second;
                tree.update(cities[who].longitude, Integer.MIN_VALUE);
                ++left_l;
            }

            int yLow = Math.max(1, cities[i].longitude - d2);
            int yHigh = Math.min(Integer.MAX_VALUE, cities[i].longitude + d2);
            mp[i] = Math.max(mp[i], cities[i].points + tree.query(yLow, yHigh));
        }

        while (left_l <= left_r) {
            int who = left[left_l].second;
            tree.update(cities[who].longitude, Integer.MIN_VALUE);
            ++left_l;
        }
    }

    static void solve(int low, int high, int[]mp, City[] cities, SegmentTree tree, int d1, int d2) {
        if (low == high) {
            mp[low] = Math.max(mp[low], cities[low].points);
            return;
        }
        int mid = (low + high) / 2;
        solve(low, mid, mp, cities, tree, d1, d2);
        merge(low, high, mp, cities, tree, d1, d2);
        solve(mid + 1, high, mp, cities, tree, d1, d2);
    }

    static int maxPoints(int d1, int d2, City[] cities) {
        Comparator<City> heightComp = Comparator.comparing((City c) -> c.height);
        Arrays.sort(cities, heightComp);

        SegmentTree tree = new SegmentTree(cities.length);
        int mp[] = new int[cities.length];
        solve(0, cities.length-1, mp, cities, tree, d1, d2);


        int maxPoints = Integer.MIN_VALUE;
        for (int i = 0; i < mp.length; i++) {
            maxPoints = Math.max(maxPoints, mp[i]);
        }

        return maxPoints;
    }


    //=============================================================================

    // O(n^2) timeout overflow
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

        for (int nItr = 0; nItr < n; nItr++) {
            String[] latitudeLongitude = scanner.nextLine().split(" ");

            int latitude = Integer.parseInt(latitudeLongitude[0]);

            int longitude = Integer.parseInt(latitudeLongitude[1]);

            int height = Integer.parseInt(latitudeLongitude[2]);

            int points = Integer.parseInt(latitudeLongitude[3]);

            cities[nItr] = new City(latitude, longitude, height, points);
        }

        System.out.println(maxPoints(d1, d2, cities));

        scanner.close();
    }
}
