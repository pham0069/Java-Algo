package data_structure.queue;

import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/truck-tour/problem
 *
 * Suppose there is a circle. There are N petrol pumps on that circle. Petrol pumps are numbered 0 to N-1(both inclusive).
 * You have two pieces of information corresponding to each of the petrol pump: (1) the amount of petrol that particular
 * petrol pump will give, and (2) the distance from that petrol pump to the next petrol pump.
 *
 * Initially, you have a tank of infinite capacity carrying no petrol. You can start the tour at any of the petrol pumps.
 * Calculate the first point from where the truck will be able to complete the circle. Consider that the truck will stop
 * at each of the petrol pumps. The truck will move one kilometer for each litre of the petrol.
 *
 * Input Format
 * The first line will contain the value of N.
 * The next N lines will contain a pair of integers each, i.e. the amount of petrol that petrol pump will give and the
 * distance between that petrol pump and the next petrol pump.
 * Output Format
 * An integer which will be the smallest index of the petrol pump from which we can start the tour.

 * Sample Input
 3
 1 5
 10 3
 3 4
 * Sample Output
 1
 * Explanation
 * We can start the tour from the second petrol pump.
 * =====================================================================================================================
 * Observation
 * Let say we start from station s and can reach furthest to station (t-1), i.e. when the truck is on the way to station
 * t, it runs out of petrol
 * Denote P(i, j) as total amount of petrol that a truck can collect when travelling from station i to station j
 * Denote D(i, j) as total distance that a truck from station i to station j
 * It is straightforwards to deduce that
 * P(s, t) < D(s, t) since the truck cannot reach station t (1)
 * P(s, u) >= D(s, u) for all s < u < t, since the truck can reach all stations before t (2)
 * From (1) and (2), we can further deduce that if we start from any station q between s and t, the truck cannot reach
 * station t either (3). We have:
 * P(u, t) = P(s, t) - P(s, u)
 * D(u, t) = D(s, t) - D(s, u) > P(s, t) - D(s, u) (from (1)) > P(s, t) - P(s, u) (from (2)) = P(u, t)
 * P(u, t) < D(u, t), thus we can conclude (3).
 *
 * Approach
 * Based on the observation, we can traverse each station at most twice to get the starting point of the tour. First we
 * pick any station i as the starting point and traverse the circle until the truck has to stop at station j due to lack
 * of petrol. Next, we pick j as the next starting point and repeat the same process. We call all the stations from i to
 * j as visited. The loop stops when the truck could finish the tour. If starting point is reset to some point that we
 * already visit, we can conclude that the tour cannot be made
 */
public class TruckTour {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] petrol = new int[n];
        int[] distance = new int[n];

        for (int i = 0; i < n; i++){
            petrol[i] = sc.nextInt();
            distance[i] = sc.nextInt();
        }

        int start = 0, end = start;
        //be careful: using int for petrols and distances might cause overflow, wrong value
        long totalPetrols = 0, totalDistances = 0;
        while(true){
            boolean finish = false;
            while(totalPetrols >= totalDistances) {
                totalPetrols += petrol[end];
                totalDistances += distance[end];

                if (start == (end+1)%n){
                    finish = true;
                    break;
                } else{
                    end = (end +1)%n;
                }
            }

            if (finish) {
                System.out.println(start);
                break;
            } else if (end < start) {
                System.out.println("Cannot tour");//this means we cross back station 0 again but tour is not finished
                break;
            } else {
                start = end; //reset starting index to the furthest index
                totalPetrols = 0;
                totalDistances = 0;
            }
        }
    }
}
