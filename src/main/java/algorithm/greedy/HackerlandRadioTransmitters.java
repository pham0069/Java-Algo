package algorithm.greedy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Hackerland is a one-dimensional city with houses aligned at integral locations along a road.
 * The Mayor wants to install radio transmitters on the roofs of the city's houses. Each
 * transmitter has a fixed range, i.e. it can transmit a signal to all houses within that range.
 *
 * Given a map of Hackerland and the radio transmission range, can you find and print the minimum
 * number of transmitters so that every house is within range of at least one transmitter?
 * Each transmitter MUST be installed on top of an existing house.
 *
 * For example, assume houses are located at x = {1, 5, 2, 3, 7} and the transmission range k = 1
 * 3 antennae at houses 2 and 5 and 7 would provide complete coverage. There is no house at
 * location 6 to cover both 5 and 7. Ranges of coverage, are [1, 2, 3], [5], and [7].
 */
public class HackerlandRadioTransmitters {
    public static void main(String[] args){
        int[] houses = {1, 5, 2, 3, 7};
        int range = 1;
        getMinTransmitters(houses, range);
    }
    /** Use greedy algorithm to place the transmitter at the house as rightmost
     * as possible to cover the range.
     *
     * Time complexity = O(n)
     * Space complexity = O(1)
     */
    public static int getMinTransmitters(int[] houses, int range){
        Arrays.sort(houses);
        int numTransmitters = 0;
        int numHouses = houses.length;
        int i = 0;
        List<Integer> transmitterLocations = new ArrayList<>();
        while (i < numHouses){
            numTransmitters ++;
            //houses[i] is the leftmost uncovered house, i.e. must put a radio transmitter before threshold to cover it
            int threshold = houses[i] + range;
            //get the leftmost house outside the threshold
            while (i < numHouses && houses[i] <= threshold)
                i++;
            //put the radio transmitter at houses[i-1], i.e. all houses to its left are covered now
            transmitterLocations.add(houses[i-1]);
            int coverage = houses[i-1] + range;
            //get the leftmost house outside the coverage of the above transmitter
            while (i < numHouses && houses[i] <= coverage)
                i++;
        }
        System.out.println("Minimum number of transmitters = " + numTransmitters);
        System.out.print("Transmitter locations = ");
        for (int loc : transmitterLocations)
            System.out.print(loc + " ");
        return numTransmitters;
    }
}
