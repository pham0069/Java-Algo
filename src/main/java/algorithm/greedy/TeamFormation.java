package algorithm.greedy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.function.Function;

/**
 * https://www.hackerrank.com/challenges/team-formation/problem
 *
 * For an upcoming programming contest, Roy is forming some teams from the students of his university. A team can have
 * any number of contestants.
 *
 * Roy knows the skill level of each contestant. To make the teams work as a unit, he forms the teams based on some
 * rules. Each of the team members must have a unique skill level for the team. If a member's skill level is x[i] where
 * 0 < i, there exists another team member whose skill level is x[i]-1. Note that a contestant can write buggy code
 * and thus can have a negative skill level.
 *
 * The more contestants on the team, the more problems they can attempt at a time so Roy wants to form teams such that
 * the smallest team is as large as possible.
 *
 * For example, there are n=7 contestants with skill levels skills= [-1,0, 1, 2,2, 3]. There are many ways teams could
 * be formed, e.g. [-1], [0],...,[3]. At the other end of the spectrum, we could form team1=[-1,0,1,2,3] and team2= [2].
 * We're looking for the largest smaller team size though. Two sets that meet the criteria are team1=[-1,0,1,2] and
 * team2 = [2,3]. The largest smaller team size possible is 2.
 *
 * Note: There is an edge case where 0 contestants have registered. As no teams are to be created, the largest team
 * created will have 0 members.
 *
 * Input Format
 *
 * The first line contains an integer t, the number of test cases.
 *
 * Each of the next t lines contains a string of space-separated integers, n followed by n integers x[i], a list of the
 * contestants' skill levels.
 *
 * Constraints
 * 1 <= t <= 100
 * 0 <= n <= 10^6
 * -10^5 <= x[i] <= 10^5
 *
 * Output Format
 *
 * For each test case, print the size of largest possible smallest team on a separate line.
 *
 * Sample Input
 *
 4
 7 4 5 2 3 -4 -3 -5
 1 -4
 4 3 2 3 1
 7 1 -2 -3 -4 2 0 -1
 * Sample Output
 *
 * 3
 * 1
 * 1
 * 7
 * Explanation
 *
 * For the first case, Roy can form two teams: one with contestants with skill levels {-4,-3,-5} and the other one with
 * {4,5,2,3}. The first group containing 3 members is the smallest.
 *
 * In the second case, the only team is {-4}
 *
 * In the third case, the teams are {3} , {1,2,3}, the size of the smaller group being 1.
 *
 * In the last case, you can build one group containing all of the contestants. The size of the group equals the total
 * number of contestants.
 *
 * Time limits
 *
 * Time limits for this challenge are given here
 *
 * Note
 *
 * If n = 0, print 0.
 *
 * --------------------------------------------------------------------------------------------------------------------
 * The optimal result can achieved by a greedy approach.
 * At first, sort all the contestants in non decreasing order and iterate over them. When a contestant with skill level
 * x is found, always try to avoid making new teams and add them to a team with a highest skill level of (x-1). If no
 * such teams exist, create a new group. If there are multiple teams, add them to the groups with smallest numbers of
 * members.
 *
 * Idea: Since a team must have consequential skill levels, we only need to keep track of a team with its lowest skill
 * and highest skill.
 * Use a hash map to map skill level to all the teams having highest skill equal to the key. This allows quick check if
 * a team with certain highest skill exists. From existing teams, we need to get the team with smallest size, thus we
 * can use a priority queue to do that quickly. Since those teams have same highest skill, smallest size is equivalent
 * to largest lowest skill. When adding a new skill to existing team, remember to remove it from old entry queue and add
 * it to new entry queue.
 *
 * My own idea was to divide the whole list into segments of skill, each segment should have the longest consequential
 * skill levels, i.e. we cannot form a team whose member comes from both of any two segments.
 * The problem is reduced to finding largest smallest team size of smaller segments
 * But I got stuck from there
 */
//PriorityQueue teams = new PriorityQueue(Comparator.comparing((Function<Team, Integer>)team -> -team.getLow()));

public class TeamFormation {
    static Function<Team, Integer> smallestTeamPriority = team -> -team.getLow();
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        while (t -- > 0) {
            int n = sc.nextInt();
            int[] x = new int[n];
            for (int i = 0; i < n; i++)
                x[i] = sc.nextInt();
            System.out.println(getLargestSmallestTeamSize_Editor(x, n));
        }
    }

    static int getLargestSmallestTeamSize_Editor(int[] x, int n) {
        if (n == 0)
            return 0;

        Arrays.sort(x);

        Map<Integer, PriorityQueue<Team>> highestSkillToTeamMap = new HashMap<>();
        int previousSkill;
        for (int skill : x) {
            previousSkill = skill-1;
            if (!highestSkillToTeamMap.containsKey(previousSkill)) {
                Team newTeam = new Team(skill, skill);
                addTeam(newTeam, highestSkillToTeamMap);
            } else {
                Team smallestTeam = highestSkillToTeamMap.get(previousSkill).poll();
                highestSkillToTeamMap.computeIfPresent(previousSkill, (skillKey, teamsValue) -> {
                    return teamsValue.isEmpty()?null:teamsValue;
                });
                smallestTeam.high = skill;
                addTeam(smallestTeam, highestSkillToTeamMap);
            }
        }
        int minSize = n;
        for (PriorityQueue<Team> teams : highestSkillToTeamMap.values()) {
            Team smallestTeam = teams.poll();
            minSize = Math.min(minSize, smallestTeam.getSize());
        }
        return minSize;
    }

    private static void addTeam(Team team, Map<Integer, PriorityQueue<Team>> highestSkillToTeamMap) {
        int high = team.high;
        PriorityQueue<Team> teams = highestSkillToTeamMap.computeIfAbsent(high,
                skill -> new PriorityQueue(Comparator.comparing(smallestTeamPriority)));
        teams.add(team);
    }

    static class Team {
        int low, high;
        Team(int low, int high){
            this.low = low;
            this.high = high;
        }
        int getLow(){
            return low;
        }
        int getSize() {
            return high-low+1;
        }
    }

    static int getLargestSmallestTeamSize(int[] x, int n) {
        if (n == 0)
            return 0;

        Arrays.sort(x);
        List<List<Integer>> segmentList = new ArrayList<>();
        int current = x[0];
        int count = 1;
        List<Integer> segment = new ArrayList<>();
        for (int i = 1; i < n; i++) {
            if (x[i] == current) {
                count++;
            } else if (x[i] == (current+1)) {
                segment.add(count);
                current = x[i];
                count = 1;
            } else {
                if (segment.size() == 1)
                    return 1;
                segmentList.add(segment);
                segment = new ArrayList<>();
                current = x[i];
                count = 1;
            }
        }

        int maxSize = 1;
        for (List<Integer> s : segmentList) {
            maxSize = Math.max(maxSize, getSegmentLargestSmallestTeamSize(s.toArray(new Integer[0])));
        }
        return maxSize;
    }

    /**
     * Given a segment with count of consecutive skill values
     * Return the max size of smallest team that can be formed from this segment
     */
    static int getSegmentLargestSmallestTeamSize(Integer[] segment) {
        return -1;
    }
}
