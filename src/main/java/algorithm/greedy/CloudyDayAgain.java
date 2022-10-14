package algorithm.greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class CloudyDayAgain {
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
        CloudPoint[] cloud = new CloudPoint[2*m];
        int[] cloudLocation = new int[m];
        int[] cloudRange = new int[m];
        split = bufferedReader.readLine().split(" ");
        for (int i = 0; i < m; i++) {
            cloudLocation[i] = Integer.parseInt(split[i]);
        }
        split = bufferedReader.readLine().split(" ");
        for (int i = 0; i < m; i++) {
            cloudRange[i] = Integer.parseInt(split[i]);
            cloud[2*i] = new CloudPoint(cloudLocation[i] - cloudRange[i], i, false);
            cloud[2*i+1] = new CloudPoint(cloudLocation[i] + cloudRange[i], i, true);
        }

        getMaxSunnyPopulation(n, town, m, cloud);
    }

    static void getMaxSunnyPopulation(int n, Town[] town, int m, CloudPoint[] cloudPoint) {
        //sort towns
        Arrays.sort(town, Comparator.comparing(Town::getLocation));
        Arrays.sort(cloudPoint, Comparator.comparing(CloudPoint::getLocation));

        long sunnyPopulation = 0;
        long maxAdditionalSunnyPopulation = 0;

        int numCloud = 0;
        Set<Integer> cloudIndex = new HashSet<>();
        int startCloudIndex = 0;
        long additionalSunnyPopulation = 0;

        int j = 0;

        for (int i = 0; i < 2*m; i++) {
            if (!cloudPoint[i].isEnd) {
                numCloud++;
                cloudIndex.add(cloudPoint[i].index);

                // start a new range of cloud that does not overlap with any other range of clouds
                if (numCloud == 1) {
                    startCloudIndex = cloudPoint[i].index;
                    while (j < n && town[j].location < cloudPoint[i].location) {
                        sunnyPopulation += town[j].population;
                        j++;
                    }
                // from first cloud start to this cloud start, all cities under this range are covered by single cloud
                } else if (numCloud == 2) {
                    while (j < n && town[j].location < cloudPoint[i].location) {
                        additionalSunnyPopulation += town[j].population;
                        j++;
                    }
                // for other clouds, they overlap with at least other 2 clouds, so no use to try removing them
                } else {
                    while (j < n && town[j].location < cloudPoint[i].location) {
                        j++;
                    }
                }
            } else {
                // the start cloud does not overlap with any other clouds, hence can try to remove it
                // simply get all towns within its range as additionalSunnyPopulation
                if (numCloud == 1) {
                    while(j < n && town[j].location <= cloudPoint[i].location) {
                        additionalSunnyPopulation += town[j].population;
                        j++;
                    }
                    maxAdditionalSunnyPopulation = Math.max(maxAdditionalSunnyPopulation, additionalSunnyPopulation);
                    additionalSunnyPopulation = 0;
                } else {
                    while(j < n && town[j].location <= cloudPoint[i].location) {
                        j++;
                    }

                    // the start cloud has ended, we could get the additionalSunnyPopulation by removing this start cloud
                    if ((cloudPoint[i].index == startCloudIndex)) {
                        maxAdditionalSunnyPopulation = Math.max(maxAdditionalSunnyPopulation, additionalSunnyPopulation);
                        additionalSunnyPopulation = 0;
                    }
                }

                numCloud--;
                cloudIndex.remove(cloudPoint[i].index);

                // only 1 cloud left in the range, which should become the next start cloud index
                if (numCloud == 1) {
                    startCloudIndex = cloudIndex.iterator().next();
                }
            }
        }

        while (j < n) {
            sunnyPopulation += town[j].population;
            j++;
        }

        System.out.println(sunnyPopulation + maxAdditionalSunnyPopulation);
    }

    static class Town {
        int location;
        int population;
        int getLocation() {
            return location;
        }
    }

    static class CloudPoint {
        int location;
        int index;
        boolean isEnd;

        CloudPoint(int location, int index, boolean isEnd) {
            this.location = location;
            this.index = index;
            this.isEnd = isEnd;
        }
        int getLocation() {
            return location;
        }
    }
}
