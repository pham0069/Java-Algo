package algorithm.greedy;

import data_structure.advanced.implementation.SegmentTreeLazyPropagation;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * https://www.hackerrank.com/challenges/cloudy-day/problem
 *
 * All year round, the city is covered in clouds. The city has many towns, located on a one-dimensional line. The
 * positions and populations of each town on the number line are known to you. Every cloud covers all towns located at
 * a certain distance from it. A town is said to be in darkness if there exists at least one cloud such that the town is
 * within the cloud's range. Otherwise, it is said to be sunny.
 *
 * The city council has determined that they have enough money to remove exactly one cloud using their latest technology.
 * Thus they want to remove the cloud such that the fewest number of people are left in darkness after the cloud is removed.
 * What is the maximum number of people that will be in a sunny town after removing exactly one cloud?
 *
 * Note: If a town is not covered by any clouds from the beginning, then it is already considered to be sunny, and the
 * population of this town must also be included in the final answer.
 *
 * Complete the function maximumPeople which takes four arrays representing the populations of each town, locations of
 * the towns, locations of the clouds, and the extents of coverage of the clouds respectively, and returns the maximum
 * number of people that will be in a sunny town after removing exactly one cloud.
 * ---------------------------------------------------------------------------------------------------------------------
 * Input Format
 * The first line of input contains a single integer n, the number of towns.
 * The next line contains n space-separated integers p_i. The i_th integer in this line denotes the population of the i_th town.
 * The next line contains n space-separated integers x_i denoting the location of the i_th town on the one dimensional line.
 * The next line consists of a single integer m denoting the number of clouds covering the city.
 * The next line contains m space-separated integers y_i, the i_th of which denotes the location of the i_th cloud on
 * the coordinate axis.
 * The next line consists of m space-separated integers r_i, denoting the range of the i_th cloud.
 * Note: The range of each cloud is computed according to its location, i.e., the cloud is located at position y_i and it
 * covers every town within a distance of r_i from it. In other words, the cloud covers every town with location in the
 * range [y_i-r_i, y_i+r_i] .
 *
 * Constraints
 * 1 <= n <= 2*10^5
 * 1 <= m <= 10*5
 * 1 <= x_i, y_i, r_i, p_i <= 10^9
 *
 * Output Format
 * Print a single integer denoting the maximum number of people that will be in a sunny town by removing exactly one cloud.
 *  ---------------------------------------------------------------------------------------------------------------------
 * Sample Input 0
 2
 10 100
 5 100
 1
 4
 1
 * Sample Output 0
 110
 * Explanation 0
 * In the sample case, there is only one cloud which covers the first town. Our only choice is to remove this sole cloud
 * which will make all towns sunny, and thus, all 110 people will live in a sunny town. As you can see, the only cloud
 * present, is at location 4 on the number line and has a range 1, so it covers towns located at 3, 4, and 5 on the number
 * line. Hence, the first town is covered by this cloud and removing this cloud makes all towns sunny.
 * =====================================================================================================================
 * If a town is covered by no cloud, it is already sunny
 * If a town is covered by at least 2 clouds, it cannot be sunny by removing 1 cloud
 * If a town is covered by a single cloud, removing this cloud could increase the sunny towns
 *
 * We only focus on the town that is covered by single cloud (*). When we remove such a cloud, the additional towns that get
 * sunny are those under the coverage of this cloud only. We can achieve this by processing the towns and clouds to get
 * the map of towns and covered clouds. First we sort towns by location. Then for each cloud, we can quickly get the
 * first town and last town in the range of the cloud by binary search. Then we traverse the town, trying to remove a
 * cloud satisfying (*). Note that when we hit such a town (whose covered cloud could be removed for sunny), we can
 * get the additional sunny population by getting all towns that get sunny when the cloud is removed. After this, we
 * can jump to the town next to the last town in the removal candidate cloud, instead of moving to the the town next
 * to the first town in the removal candidate cloud. In this way, the cost of traversing is O(n).
 * The cost of sorting is O(nlogn). The cost of mapping is O(mlogn). Total cost is O((m+n)logn)
 *
 */
public class CloudyDay {
    public static void main(String[] args) throws IOException {
        //BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(CloudyDay.class.getResourceAsStream("cloudyDay.txt")));

        //read towns and clouds
        int n = Integer.parseInt(bufferedReader.readLine());
        Town[] town = new Town[n];
        String[] split = bufferedReader.readLine().split(" ");
        for (int i = 0; i < n; i++) {
            town[i] = new Town();
            town[i].population = Integer.parseInt(split[i]);
        }
        split = bufferedReader.readLine().split(" ");
        for (int i = 0; i < n; i++) {
            town[i].location = Integer.parseInt(split[i]);
        }
        int m = Integer.parseInt(bufferedReader.readLine());
        int[] cloudLocation = new int[m];
        int[] cloudRange = new int[m];
        split = bufferedReader.readLine().split(" ");
        for (int i = 0; i < m; i++) {
            cloudLocation[i] = Integer.parseInt(split[i]);
        }
        split = bufferedReader.readLine().split(" ");
        for (int i = 0; i < m; i++) {
            cloudRange[i] = Integer.parseInt(split[i]);
        }

        getMaxSunnyPopulation4(n, town, m, cloudLocation, cloudRange);
    }

    /**
     * Fail test 27 (timeout)
     * Tried to keep track of no-cloud towns and single-cloud towns (to avoid traversing list of towns again later)
     * - but more timeout test
     *
     *
     */
    static void getMaxSunnyPopulation(int n, Town[] town, int m, int[] cloudLocation, int[] cloudRange) {
        //sort towns
        Arrays.sort(town, Comparator.comparingInt(Town::getLocation));

        /**
         * Since we dont care about towns that are covered by >= 2 clouds, we dont need to actually store all the
         * clouds covering a town. In case of very large n and m, that is time-consuming and space-occupying.
         * Used to store coveringCloudForTown as Set[], it causes StackOverflow and timeout exception
         * For simplicity, we can assign the singleCoveringCloudForTown value as follows:
         * singleCoveringCloudForTown[j] = null -> town j is not covered by any town
         * singleCoveringCloudForTown[j] = i >= 0 -> town j is covered by single cloud i
         * singleCoveringCloudForTown[j] = -1 -> town j is covered by more than a cloud
         */
        Integer[] singleCoveringCloudForTown = new Integer[n];
        int[] startTownForCloud = new int[m];
        int[] endTownForCloud = new int[m];

        for (int i = 0; i < m; i++) {
            int startOfCloud = cloudLocation[i]-cloudRange[i];
            int lowerTown = binarySearchGreaterOrEqualToKey(town, startOfCloud);
            if (lowerTown == -1) {
                continue;
            }
            int endOfCloud = cloudLocation[i]+cloudRange[i];
            int higherTown = binarySearchLessOrEqualToKey(town, endOfCloud);
            if (higherTown == -1) {
                continue;
            }
            //maybe we can improve here?
            for (int j = lowerTown; j <= higherTown; j++) {
                if (singleCoveringCloudForTown[j] == null) {
                    singleCoveringCloudForTown[j] = i;
                } else if (singleCoveringCloudForTown[j] >= 0) {
                    singleCoveringCloudForTown[j] = -1;
                }
            }
            startTownForCloud[i] = lowerTown;
            endTownForCloud[i] = higherTown;
        }

        long existingSunnyPopulation = 0;
        long maxAdditionalSunnyPopulation = 0;
        /**
         * Traverse the towns to find the cloud that can be removed to increase the additional sunny population
         */
        for (int i = 0; i < n; i++) {
            Integer singleCoveringCloud = singleCoveringCloudForTown[i];
            if (singleCoveringCloud == null) {
                existingSunnyPopulation += town[i].population;
            } else if (singleCoveringCloud >=0 ) {
                int cloudToRemove = singleCoveringCloud;
                long additionalSunnyPopulation = 0;
                for (int j = startTownForCloud[cloudToRemove]; j <= endTownForCloud[cloudToRemove]; j++) {
                    if (singleCoveringCloudForTown[j] >= 0)
                        additionalSunnyPopulation += town[j].population;
                }
                maxAdditionalSunnyPopulation = Math.max(maxAdditionalSunnyPopulation, additionalSunnyPopulation);
                //jump to the last town
                i = endTownForCloud[cloudToRemove];
            }
        }

        System.out.println(existingSunnyPopulation + maxAdditionalSunnyPopulation);
    }

    /**
     * Improve getMaxSunnyPopulation
     * by getting singleCoverCloud by binary search to avoid looping same city multiple times in the cloud location computation
     * Fail even more test for timeout :(
     * But test 27 passes
     */
    static void getMaxSunnyPopulation2(int n, Town[] town, int m, int[] cloudLocation, int[] cloudRange) {
        //sort towns
        Arrays.sort(town, Comparator.comparingInt(Town::getLocation));

        List<Cloud> sortedByStartTown = new ArrayList<>();
        List<Cloud> sortedByEndTown = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            int startOfCloud = cloudLocation[i]-cloudRange[i];
            int lowerTown = binarySearchGreaterOrEqualToKey(town, startOfCloud);
            if (lowerTown == -1) {
                continue;
            }
            int endOfCloud = cloudLocation[i]+cloudRange[i];
            int higherTown = binarySearchLessOrEqualToKey(town, endOfCloud);
            if (higherTown == -1) {
                continue;
            }

            // this means no town within this cloud
            if (higherTown < lowerTown) {
                continue;
            }

            // ignore the clouds that are not covering any cloud
            Cloud cloud = new Cloud(lowerTown, higherTown);
            sortedByStartTown.add(cloud);
            sortedByEndTown.add(cloud);
        }

        // we will get the singleCoveringTown based on sortedByStartTown by using right cloud -1
        // so we need to sort it by start town first, if they tie, use end town to break
        Collections.sort(sortedByStartTown, Comparator.comparing(Cloud::getStartTownIndex).thenComparing(Cloud::getEndTownIndex));
        Collections.sort(sortedByEndTown, Comparator.comparing(Cloud::getEndTownIndex));


        long existingSunnyPopulation = 0;
        long maxAdditionalSunnyPopulation = 0;
        /**
         * Traverse the towns to find the cloud that can be removed to increase the additional sunny population
         */
        for (int i = 0; i < n; i++) {
            Cloud cloud = getSingleCoveringCloud(sortedByStartTown, sortedByEndTown, i);
            if (cloud == NONE_COVERING_CLOUD) {
                existingSunnyPopulation += town[i].population;
            } else if (cloud != MULTI_COVERING_CLOUD) {
                long additionalSunnyPopulation = 0;
                for (int j = cloud.startTownIndex; j <= cloud.endTownIndex; j++) {
                    if (isCoveredBySingleCloud(sortedByStartTown, sortedByEndTown, j)) {
                        additionalSunnyPopulation += town[j].population;
                    }
                }
                maxAdditionalSunnyPopulation = Math.max(maxAdditionalSunnyPopulation, additionalSunnyPopulation);
                //jump to the last town, so in next loop, we will check the next town after this
                i = cloud.endTownIndex;
            }
        }

        System.out.println(existingSunnyPopulation + maxAdditionalSunnyPopulation);
    }

    static final Cloud NONE_COVERING_CLOUD = new Cloud(-1, -1);
    static final Cloud MULTI_COVERING_CLOUD = new Cloud(-1, -1);

    static boolean isCoveredBySingleCloud(List<Cloud> sortedByStartTown, List<Cloud> sortedByEndTown, int location) {
        Cloud cloud = getSingleCoveringCloud(sortedByStartTown, sortedByEndTown, location);
        return cloud != NONE_COVERING_CLOUD && cloud != MULTI_COVERING_CLOUD;
    }
    static Cloud getSingleCoveringCloud(List<Cloud> sortedByStartTown, List<Cloud> sortedByEndTown, int location) {
        int numClouds = sortedByStartTown.size();
        int rightCloud = binarySearchStrictlyGreaterThanKey(sortedByStartTown, location);
        int numberOfRightClouds = rightCloud == -1 ? 0 : (numClouds-rightCloud); // rightCLoud..numClouds-1
        int leftCloud = binarySearchStrictlyLessThanKey(sortedByEndTown, location);
        int numberOfLeftClouds = leftCloud == -1 ? 0 : (leftCloud+1);  // 0..leftCloud

        int numberOfCoveringClouds = numClouds - (numberOfLeftClouds + numberOfRightClouds);
        if (numberOfCoveringClouds == 0) {
            return NONE_COVERING_CLOUD;
        } else if (numberOfCoveringClouds > 1) {
            return MULTI_COVERING_CLOUD;
        } else {
            if (rightCloud == -1) {
                return sortedByStartTown.get(numClouds-1);
            } else {
                return sortedByStartTown.get(rightCloud-1);
            }
        }

    }

    static int binarySearchGreaterOrEqualToKey(Town[] array, int key) {
        return binarySearchGreaterOrEqualToKey(array, 0, array.length-1, key);
    }

    static int binarySearchLessOrEqualToKey(Town[] array, int key) {
        return binarySearchLessOrEqualToKey(array, 0, array.length-1, key);
    }

    /**
     * Find the smallest index such that element larger or equal to the key
     * The index is in the inclusive range from start to end
     * Return -1 if no such index is found
     * @param array
     * @param start
     * @param end
     * @param key
     * @return
     */
    static int binarySearchGreaterOrEqualToKey(Town[] array, int start, int end, int key) {
        int low = start, high = end, mid =(low+high)/2;
        if (array[high].location < key)
            return -1;
        int foundIndex = -1;
        while (low <= high) {
            if (array[mid].location == key) {
                //to ensure returning max index satisfying the condition
                foundIndex = mid;
                break;
            } else if (array[mid].location < key) {
                low = mid+1;
            } else {
                high = mid-1;
            }
            mid = (low+high)/2;
        }
        if (foundIndex == -1)
            foundIndex = low;
        //to ensure returning smallest index satisfying the condition
        while (foundIndex > start && array[foundIndex - 1].location >= key) {
            foundIndex--;
        }
        return foundIndex;
    }

    static int binarySearchLessOrEqualToKey(Town[] array, int start, int end, int key) {
        int low = start, high = end, mid =(low+high)/2;
        if (array[low].location > key)
            return -1;
        int foundIndex = -1;
        while (low <= high) {
            if (array[mid].location == key) {
                //to ensure returning max index satisfying the condition
                foundIndex = mid;
                break;
            }
            else if (array[mid].location < key) {
                low = mid+1;
            } else {
                high = mid-1;
            }
            mid = (low+high)/2;
        }
        if (foundIndex == -1)
            foundIndex = high;
        //to ensure returning largest index satisfying the condition
        while (foundIndex < end && array[foundIndex + 1].location <= key) {
            foundIndex++;
        }
        return foundIndex;
    }

    static class Town {
        int location;
        int population;
        int getLocation() {
            return location;
        }
    }

    static int binarySearchStrictlyGreaterThanKey(List<Cloud> array, int key) {
        if (array.isEmpty()) {
            return -1;
        }
        return binarySearchStartTownStrictlyGreaterThanKey(array, 0, array.size()-1, key);
    }

    static int binarySearchStrictlyLessThanKey(List<Cloud> array, int key) {
        if (array.isEmpty()) {
            return -1;
        }
        return binarySearchEndTownStrictlyLessThanKey(array, 0, array.size()-1, key);
    }

    /**
     * Find the smallest index such that element larger or equal to the key
     * The index is in the inclusive range from start to end
     * Return -1 if no such index is found
     * @param array
     * @param start
     * @param end
     * @param key
     * @return
     */
    static int binarySearchStartTownStrictlyGreaterThanKey(List<Cloud> array, int start, int end, int key) {
        int low = start, high = end, mid =(low+high)/2;
        if (array.get(high).startTownIndex <= key) ////// mod
            return -1;
        int foundIndex = -1;
        while (low <= high) {
            if (array.get(mid).startTownIndex == key) {
                //to ensure returning max index satisfying the condition
                foundIndex = mid;
                break;
            } else if (array.get(mid).startTownIndex < key) {
                low = mid+1;
            } else {
                high = mid-1;
            }
            mid = (low+high)/2;
        }

        //// mod from here
        if (foundIndex == -1) {
            return low;
        }

        //find the largest index with value = key
        while (foundIndex < end && array.get(foundIndex + 1).startTownIndex == key) {
            foundIndex++;
        }
        return foundIndex+1;
    }

    static int binarySearchEndTownStrictlyLessThanKey(List<Cloud> array, int start, int end, int key) {
        int low = start, high = end, mid =(low+high)/2;
        if (array.get(low).endTownIndex >= key) //////
            return -1;
        int foundIndex = -1;
        while (low <= high) {
            if (array.get(mid).endTownIndex == key) {
                //to ensure returning max index satisfying the condition
                foundIndex = mid;
                break;
            }
            else if (array.get(mid).endTownIndex < key) {
                low = mid+1;
            } else {
                high = mid-1;
            }
            mid = (low+high)/2;
        }

        //// mod from here
        if (foundIndex == -1) {
            return high;
        }

        //find the smallest index with value = key
        while (foundIndex > start && array.get(foundIndex - 1).endTownIndex == key) {
            foundIndex--;
        }
        return foundIndex-1;
    }

    static class Cloud {
        int startTownIndex;
        int endTownIndex;
        Cloud(int startTownIndex, int endTownIndex) {
            this.startTownIndex = startTownIndex;
            this.endTownIndex = endTownIndex;
        }
        int getStartTownIndex() {
            return startTownIndex;
        }
        int getEndTownIndex() {
            return endTownIndex;
        }
    }

    /*
     * 2 test cases fail due to timeout :(((
     *
     */
    static void getMaxSunnyPopulation3(int n, Town[] town, int m, int[] cloudLocation, int[] cloudRange) {
        //sort towns
        Arrays.sort(town, Comparator.comparingInt(Town::getLocation));

        Integer[] singleCoveringCloudForTown = new Integer[n];
        int[] startTownForCloud = new int[m];
        int[] endTownForCloud = new int[m];

        Map<Integer, Long> singleCoveringCloudToAccumulatedPopulations = new HashMap<>();
        long maxAdditionalSunnyPopulation = 0;

        for (int i = 0; i < m; i++) {
            int startOfCloud = cloudLocation[i]-cloudRange[i];
            int lowerTown = binarySearchGreaterOrEqualToKey(town, startOfCloud);
            if (lowerTown == -1) {
                continue;
            }
            int endOfCloud = cloudLocation[i]+cloudRange[i];
            int higherTown = binarySearchLessOrEqualToKey(town, endOfCloud);
            if (higherTown == -1) {
                continue;
            }

            for (int j = lowerTown; j <= higherTown; j++) {
                if (singleCoveringCloudForTown[j] == null) {
                    singleCoveringCloudForTown[j] = i;
                    long currentAccumulation = Optional.ofNullable(singleCoveringCloudToAccumulatedPopulations.get(i)).orElse(0L);
                    singleCoveringCloudToAccumulatedPopulations.put(i, currentAccumulation + town[j].population);
                    maxAdditionalSunnyPopulation = Math.max(maxAdditionalSunnyPopulation, singleCoveringCloudToAccumulatedPopulations.get(i));
                } else if (singleCoveringCloudForTown[j] >= 0) {
                    singleCoveringCloudToAccumulatedPopulations.remove(singleCoveringCloudForTown[j]);
                    singleCoveringCloudForTown[j] = -1;
                }
            }
            startTownForCloud[i] = lowerTown;
            endTownForCloud[i] = higherTown;
        }

        long existingSunnyPopulation = 0;

        /**
         * Traverse the towns to find the cloud that can be removed to increase the additional sunny population
         */
        for (int i = 0; i < n; i++) {
            Integer singleCoveringCloud = singleCoveringCloudForTown[i];
            if (singleCoveringCloud == null) {
                existingSunnyPopulation += town[i].population;
            }
        }

        System.out.println(existingSunnyPopulation + maxAdditionalSunnyPopulation);
    }

    static void getMaxSunnyPopulation4(int n, Town[] town, int m, int[] cloudLocation, int[] cloudRange) {
        //sort towns
        Arrays.sort(town, Comparator.comparingInt(Town::getLocation));

        Integer[] singleCoveringCloudForTown = new Integer[n];
        int[] startTownForCloud = new int[m];
        int[] endTownForCloud = new int[m];

        Map<Integer, Long> singleCoveringCloudToAccumulatedPopulations = new HashMap<>();
        Set<Integer> citiesWithoutCoveringCloud = IntStream.range(0, n).boxed().collect(Collectors.toSet());
        long existingSunnyPopulation = 0;
        long maxAdditionalSunnyPopulation = 0;

        for (int i = 0; i < m; i++) {
            int startOfCloud = cloudLocation[i]-cloudRange[i];
            int lowerTown = binarySearchGreaterOrEqualToKey(town, startOfCloud);
            if (lowerTown == -1) {
                continue;
            }
            int endOfCloud = cloudLocation[i]+cloudRange[i];
            int higherTown = binarySearchLessOrEqualToKey(town, endOfCloud);
            if (higherTown == -1) {
                continue;
            }

            for (int j = lowerTown; j <= higherTown; j++) {
                citiesWithoutCoveringCloud.remove(j);
                if (singleCoveringCloudForTown[j] == null) {
                    singleCoveringCloudForTown[j] = i;
                    long currentAccumulation = Optional.ofNullable(singleCoveringCloudToAccumulatedPopulations.get(i)).orElse(0L);
                    singleCoveringCloudToAccumulatedPopulations.put(i, currentAccumulation + town[j].population);
                    maxAdditionalSunnyPopulation = Math.max(maxAdditionalSunnyPopulation, singleCoveringCloudToAccumulatedPopulations.get(i));
                } else if (singleCoveringCloudForTown[j] >= 0) {
                    singleCoveringCloudToAccumulatedPopulations.remove(singleCoveringCloudForTown[j]);
                    singleCoveringCloudForTown[j] = -1;
                }
            }
            startTownForCloud[i] = lowerTown;
            endTownForCloud[i] = higherTown;
        }



        /**
         * Traverse the towns to find the cloud that can be removed to increase the additional sunny population
         */
        for (int i : citiesWithoutCoveringCloud) {
            existingSunnyPopulation += town[i].population;
        }

        System.out.println(existingSunnyPopulation + maxAdditionalSunnyPopulation);
    }
}
