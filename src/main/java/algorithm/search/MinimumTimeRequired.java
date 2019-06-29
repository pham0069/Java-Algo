package algorithm.search;

/**
 * https://www.hackerrank.com/challenges/minimum-time-required/problem?h_l=interview&playlist_slugs%5B%5D%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D%5B%5D=search
 * You are planning production for an order.
 * You have a number of machines that each have a fixed number of days to produce an item.
 * Given that all the machines operate simultaneously,
 * determine the minimum number of days to produce the required order.
 *
 * For example, you have to produce goal = 10  items.
 * You have three machines that take machines = (2, 3, 2) days to produce an item.
 * The following is a schedule of items produced:
 *
 * Day Production  Count
 * 2   2               2
 * 3   1               3
 * 4   2               5
 * 6   3               8
 * 8   2              10
 * It takes 8 days to produce 10 items using these machines.
 *
 * First, roughly estimate lower bound for number of days required
 * = goal / total number of items per day that all machines can produce
 * Second, we try to find an upper bound for number of days required
 * by jump search or exponential search.
 * Jump search is more tricky, we need to define the step size.
 * It's better to have this step size adaptive to input (what algo?)
 * So we use exponential search
 * Third, given the range of lower-upper bound of days,
 * we do binary search to find the min number of days to get the goals
 */
public class MinimumTimeRequired {
    static long minTime(long[] machines, long goal) {
        double totalItemsPerDay = 0;
        for (long m : machines) {
            totalItemsPerDay += 1.0/m;
        }

        long start = (long)(goal/totalItemsPerDay);
        if (countItems(machines, start) == goal) {
            return start;
        }

        long end = exponentialSearchForUpperBound(machines, goal, start);
        return binarySearch(machines, goal, start, end);
    }

    // given that for START day, number of items < goal
    // starting from START day, we jump in exponential steps, i.e. 1, 2, 4, 8, 16... steps ahead
    // in order to find the first number of days that can produce number of items > goal
    static long exponentialSearchForUpperBound(long[] machines, long goal, long start) {
        long jump = 1;
        long days = start;
        for (;;jump = jump*2) {
            days += jump;
            if (countItems(machines, days) >= goal) {
                break;
            }
        }
        return days;
    }

    // search for the smallest number in the range start-end that satisfy the condition
    static long binarySearch(long[] machines, long goal, long start, long end) {
        if (end == start+1) {
            return end;
        }
        long mid = (start + end)/2;
        long items = countItems(machines, mid);
        if (items >= goal) {
            return binarySearch(machines, goal, start, mid);
        } else {
            return binarySearch(machines, goal, mid, end);
        }
    }

    static long countItems(long[] machines, long days) {
        long items = 0;
        for (long daysPerItem : machines) {
            items += days/daysPerItem;
        }
        return items;
    }

    public static void main(String[] args) {
        System.out.println(minTime(new long[] {2, 3, 2}, 10));
    }

}
