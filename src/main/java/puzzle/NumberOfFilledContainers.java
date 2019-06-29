package puzzle;

/**
 * https://www.geeksforgeeks.org/number-of-containers-that-can-be-filled-in-the-given-time/?ref=leftbar-rightbar
 *
 *
 * Given a number N and a time X unit, the task is to find the number of containers that are filled completely in X unit
 * if containers are arranged in pyramid fashion as shown below.
 */

public class NumberOfFilledContainers {
    public static void main(String[] args) {
        int n = 4;
        int x = 8;

        int k = getNumberOfFullRowsFilled(x, n);

        // number of containers from row 1 to k
        int numberOfContainersFilledInFullRows = k*(k+1)/2;
        int total = numberOfContainersFilledInFullRows;

        // number of containers that might be fully filled in row k+1
        // in 1 time unit, (k-1) containers are filled with 1/k volume
        // 2 outmost containers are filled with 1/2k volume
        // so it takes k additional time units to fill those (k-1) inner containers
        if (x-numberOfContainersFilledInFullRows >= k) {
            total += (k-1);
        }

        System.out.println(total);
    }

    /**
     * k(k+1) <= 2x
     * @param x
     */
    static int getNumberOfFullRowsFilled(int x, int n) {
        int k = (int) Math.sqrt(2*x);
        if (k*(k+1) > 2*x)
            k -=1;
        return Math.min(n, k);
    }
}
