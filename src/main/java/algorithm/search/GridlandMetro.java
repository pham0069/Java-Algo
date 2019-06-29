package algorithm.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * https://www.hackerrank.com/challenges/gridland-metro/problem
 *
 * The city of Gridland is represented as an nxm matrix where the rows are numbered from 1 to n and the columns are numbered from 1 to m.
 *
 * Gridland has a network of train tracks that always run in straight horizontal lines along a row.
 * In other words, the start and end points of a train track are (r, c1) and (r, c2),
 * where r represents the row number,
 * c1 represents the starting column, and c2 represents the ending column of the train track.
 *
 * The mayor of Gridland is surveying the city to determine the number of locations where lampposts can be placed.
 * A lamppost can be placed in any cell that is not occupied by a train track.
 *
 * Given a map of Gridland and its k train tracks, find and print the number of cells where the mayor can place lampposts.
 *
 * Note: A train track may overlap other train tracks within the same row.
 *
 * For example, if Gridland's data is the following:
 * r	c1	c2
 * 1	1	4
 * 2	2	4
 * 3	1	2
 * 4	2	3
 *
 * There are five open cells (red) where lampposts can be placed.
 *
 * Constraints:
 * 1 <= n, m <= 10^9
 * 0<= k <= 1000
 * 1 <=r <=n
 * 1 <=c1 <=c2 <=m
 *
 * We try to find all the non-overlapping ranges on each row
 * where each range is formed by 1 or multiple overlap tracks
 * - first, sort the track by start column
 * - second, let's start a new range with a track T
 * Find all the tracks whose start falls between this range
 * Find the track S with largest colEnd among these tracks
 * The range is now extend from T's start to S's end
 * Continue to find other tracks whose start falls between this range
 * Update the new range end accordingly
 * Continue until there's no more new track found added to the range
 *
 * We can implement this algo with checking each track only once.
 * -> The algorithm takes O(klogk + k)
 */
public class GridlandMetro {

    public static void main(String[] args) {
        List<Track> tracks = new ArrayList<>();
        tracks.add(new Track(0, 1, 1, 4));
        tracks.add(new Track(1, 1, 3, 5));
        tracks.add(new Track(1, 1, 2, 6));

        tracks.add(new Track(1, 1, 9, 10));

        System.out.println(lampsPerRow(10, tracks));
    }

    static class Track {
        int index;
        int row;
        int colStart;
        int colEnd;
        Track (int index, int row, int colStart, int colEnd) {
            this.index = index;
            this.row = row;
            this.colStart = colStart;
            this.colEnd = colEnd;
        }
    }

    static long gridlandMetro(int n, int m, int k, int[][] track) {
        Map<Integer, List<Track>> rowToTrack = new HashMap<>();
        for (int i = 0; i < k; i++) {
            Track t = new Track(i, track[i][0], track[i][1], track[i][2]);
            rowToTrack.computeIfAbsent(t.row, (r) -> new ArrayList<>());
            rowToTrack.get(t.row).add(t);
        }

        long totalLamps = 0;
        for (int i : rowToTrack.keySet()) {
            totalLamps += lampsPerRow(m, rowToTrack.get(i));
        }
        totalLamps += ((long)m)*(n-rowToTrack.size());

        /* This cause timeout
         * Note that grid could be very big 10^18
         * iterating through all rows, including the rows with no track is wasted
        long totalLamps = 0;
        for (int i = 1; i <= n; i++) {
            if (rowToTrack.containsKey(i)) {
                totalLamps += lampsPerRow(m, rowToTrack.get(i));
            } else {
                totalLamps += m;
            }
        }
        */

        return totalLamps;
    }

    static int lampsPerRow(int m, List<Track> tracks) {
        Track[] arr = tracks.toArray(new Track[tracks.size()]);
        Arrays.sort(arr, Comparator.comparing((Track t) -> t.colStart));

        int lamps = arr[0].colStart-1;
        int curIndex = 0;

        while (true) {
            int curEnd = arr[curIndex].colEnd;
            while(true) {
                // use startIndex = curIndex, instead of 0 for optimise
                int last = binarySearchLessOrEqualToKey(arr, curIndex, arr.length-1, curEnd);
                if (last <= curIndex) {
                    break;
                }

                for (int i = curIndex+1; i <= last; i++) {
                    curEnd = Math.max(curEnd, arr[i].colEnd);
                }

                curIndex = last;
            }

            if (curIndex == arr.length-1) {
                lamps += m-curEnd;
                break;
            } else {
                curIndex++;
                lamps += arr[curIndex].colStart - curEnd-1; //arr[curIndex].colEnd is ensured > curEnd
            }
        }

        return lamps;
    }

    /**
     * Find the largest index such that element smaller or equal to the key
     * The index is in the inclusive range from start to end
     * Return -1 if no such index is found
     */
    static int binarySearchLessOrEqualToKey(Track[] array, int start, int end, int key) {
        int low = start, high = end, mid =(low+high)/2;
        if (array[low].colStart > key)
            return -1;
        int foundIndex = -1;
        while (low <= high) {
            if (array[mid].colStart == key) {
                //to ensure returning max index satisfying the condition
                foundIndex = mid;
                break;
            }
            else if (array[mid].colStart < key) {
                low = mid+1;
            } else {
                high = mid-1;
            }
            mid = (low+high)/2;
        }
        if (foundIndex == -1)
            foundIndex = high;
        //to ensure returning largest index satisfying the condition
        while (foundIndex < end && array[foundIndex + 1].colStart <= key) {
            foundIndex++;
        }
        return foundIndex;
    }

}
