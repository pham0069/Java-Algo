package algorithm.greedy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/fighting-pits/problem
 *
 * Meereen is famous for its fighting pits where fighters fight each other to the death.
 * Initially, there are n fighters and each fighter has a strength value. The n fighters are divided into k teams, and
 * each fighter belongs to exactly one team. For each fight, the Great Masters of Meereen choose two teams, x and y,
 * that must fight each other to the death. The teams attack each other in alternating turns, with team x always
 * launching the first attack. The fight ends when all the fighters on one of the teams are dead.
 * Assume each team always attacks optimally. Each attack is performed as follows:
 * 1. The attacking team chooses a fighter from their team with strength s.
 * 2. The chosen fighter chooses at most  fighters from other team and kills all of them.
 *
 * The Great Masters don't want to see their favorite fighters fall in battle, so they want to build their teams
 * carefully and know who will win different team matchups. They want you to perform two type of queries:
 * 1 p x - Add a new fighter with strength p to team x. It is guaranteed that this new fighter's strength value will not
 * be less than any current member of team .
 * 2 x y - Print the name of the team that would win a matchup between teams x and y in their current state (recall that
 * team x always starts first). It is guaranteed that x != y.
 *
 * Given the initial configuration of the teams and  queries, perform each query so the Great Masters can plan the next
 * fight. Note that you are determining the team that would be the winner if the two teams fought. No fighters are
 * actually dying in these matchups. Once added to a team, a fighter is available for all future potential matchups.
 * ---------------------------------------------------------------------------------------------------------------------
 * Input Format: The first line contains three space-separated integers describing the respective values of n (the number
 * of fighters), k (the number of teams), and q (the number of queries).
 * Each line  of the n subsequent lines contains two space-separated integers describing the respective values of
 * fighter i's strength, s_i, and team number, t_i.
 * Each of the q subsequent lines contains a space-separated query in one of the two formats defined in the Problem
 * Statement above (i.e., 1 p x or 2 x y).
 *
 * Constraints:
 * 1 <= n, q <= 2*10^5, 2<= k <= 2*10^5, 1 <= x, y, t_i <= k
 * 1 <= s_i, p <= 2*10^5
 * It is guaranteed that both teams in a query matchup will always have at least 1 fighter
 *
 * Output Format: After each type  query, print the name of the winning team on a new line. For example, if x = 1 and
 * y = 2 are matched up and x wins, you would print 1.
 *  ---------------------------------------------------------------------------------------------------------------------
 * Sample Input
 7 2 6
 1 1
 2 1
 1 1
 1 2
 1 2
 1 2
 2 2
 2 1 2
 2 2 1
 1 2 1
 1 2 1
 2 1 2
 2 2 1
 * Sample Output
 1
 2
 1
 1
 * Explanation: Team 1 has 3 fighters with strength levels: {1, 1, 2} and Team 2 has 4 fighters with strength levels:
 * {1, 1, 1, 2}. The first query matching up team 1 and 2 would play out as follows:
 * Team 1 attacks -> The fighter with strength 2 can kill one fighter with strength 2 and one fighter with strength 1.
 * Now, S1= {1, 1, 2}, and S2= {1, 1}.
 * Team 2 attacks ->  The fighter with strength 1 can kill the fighter with strength 2.
 * Now, S1 = {1, 1}, and S2 = {1, 1}.
 * Team 1 attacks -> The fighter with strength 1 can kill one fighter with strength 1.
 * Now, S1 = {1, 1}, and S2 = {1}.
 * Team 2 attacks -> The fighter with strength 1 can kill one fighter with strength 1.
 * Now, S1 = {1}, and S2 = {1}.
 * Team 1 attacks -> The fighter with strength 1 can kill the last fighter with strength 1. Now, S1 = {1}, and S2 empty.
 * After this last attack, all of Team 2's fighters would be dead. Thus, we print 1 as team  would win that fight.
 * ---------------------------------------------------------------------------------------------------------------------
 * Greedily in each attack, the attacking team will choose its most powerful fighter to kill the defending team's most
 * powerful fighters. Let a be the vector for the first team and b be the vector for the second team.
 * The following code calculates the winner in O(n) time:
 * team_a = 0, team_b = 0
 * while 1
 * if(team_a >= size of a) return team b wins
 * team_b += a[team_a]
 * if(team_b >= size of b) return team a wins
 * team_a += b[team_b]
 * ---------------------------------------------------------------------------------------------------------------------
 * Theorem: Given 2 team A with strengths {a_1, a_2, ... a_n} and team B with strengths {b_1, b_2, ... b_m} in
 * non-increasing order. The total strength of team A is greater or equal to that of team B.
 * Prove that if team A attacks first, team A would win the matchup.
 * For summary we have:
 * a_1 >= a_2 >= ... >= a_n > 0     (1)
 * b_1 >= b_2 >= .... >= b_m > 0    (2)
 * A = a_1 + a_2 + ... + a_n        (3)
 * B = b_1 + b_2 + ... + b_m        (4)
 * A >= B                           (5)
 *
 * Proof:
 * Team A attacks -> The fighter with strength a_1 will attack first
 * There are two cases:
 * 1. If a1 >= m, this attack will kill all team B -> A wins
 * 2. If a1 < m, this attack will kill fighters with strength b1, ... b{a1}
 * After this, team B attacks -> The fighter with strength b_{a1 + 1} will kill at most b_{a1+1} people in team A (6)
 * From (1), (3), n*a1 >= A,
 * From (2), (4), B > b1 + b2 + ... + b_{a1} >= b_{_1 + 1}*a1
 * Combined with (5), n*a1 >= A >= B > b_{a1 + 1}*a1 -> n > b_{a1 + 1} (7)
 * From (6) and (7), in the attack of b_{a1+1}, he cannot kill all the people in team A
 *
 * After this, it's team A's turn to attack. We need to prove that the remaining strength of team A is greater or equal
 * to that of team B.
 * The total strength of a1 fighters killed in team B is
 * KB1 = b1+ b2 + ... + b_{a1} >= b_{a1} * a1 (8)
 * The total strength of b_{a1} fighters killed in team A is
 * KA1 = a1 + a2 + ... + a_{b_{a1}} <= a1 * b_{a1} (9)
 * From (8) and (9), KA1 <= KB1
 * The remaining strength of team A is A1 = A - KA1 and of team B is B1 = B - KB1
 * Since A >= B, KA1 <= KB1, A1 >= B1.
 *
 * Now the problem becomes smaller but also satisfies condition 1-5 and it's team A turn to attack. The proceedings will
 * repeat and as proven above team A cannot lose when team B still have fighters. Finally, team A should win when B has
 * no more fighters. Theorem proved.
 *
 *
 */

public class FightingPits {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int fighters = sc.nextInt();
        int teams = sc.nextInt();
        int queries = sc.nextInt();
        List<List<Integer>> strengths = new ArrayList<>();
        List<List<Long>> accumulativeStrengths = new ArrayList<>();
        int strength, team, q;
        for (int i = 0; i < teams; i++){
            strengths.add(new ArrayList<>());
            accumulativeStrengths.add(new ArrayList<>());
        }
        //store fighters' strength
        for (int i = 0; i < fighters; i++){
            strength = sc.nextInt();
            team = sc.nextInt() - 1;
            strengths.get(team).add(strength);
        }
        //process team's strength
        for (int i = 0; i < teams; i++){
            //sort
            Collections.sort(strengths.get(i));
            //calculate team's accumulative sorted strengths
            long sum = 0;
            List<Integer> teamStrengths = strengths.get(i);
            for (int j = 0; j < teamStrengths.size(); j ++) {
                sum += teamStrengths.get(j);
                accumulativeStrengths.get(i).add(sum);
            }
        }

        for (int i = 0; i < queries; i++){
            q = sc.nextInt();

            if (q == 1){
                //add new fighter to a team
                int s = sc.nextInt();
                team = sc.nextInt()-1;
                strengths.get(team).add(s); //no need to re-sort because s is ensured to be bigger than max strength of team t
                int teamSize  = strengths.get(team).size();
                long totalTeamStrength = teamSize==1?s:accumulativeStrengths.get(team).get(teamSize-2) + s;
                accumulativeStrengths.get(team).add(totalTeamStrength);
            } else {
                //fight between team x and y
                int x = sc.nextInt() - 1;
                int y = sc.nextInt() - 1;
                //fight(strengths, accumulativeStrengths, x, strengths.get(x).size(), y, strengths.get(y).size());
                int winner = fight2(strengths.get(x), strengths.get(y), accumulativeStrengths.get(x), accumulativeStrengths.get(y), x, y);
                printWinner(winner);
            }
        }
    }

    private static void printWinner(int teamIndex) {
        System.out.println(teamIndex+1);
    }

    /**
     * not quickly return result based on total strength of team
     */
    public static void fight_Naive(List<List<Integer>> strengths, int x, int sizeX, int y, int sizeY){
        if (sizeX <= 0){
            System.out.println(y+1);
            return;
        }
        if (sizeY <= 0){
            System.out.println(x+1);
            return;
        }
        int s = strengths.get(x).get(sizeX-1);
        fight_Naive(strengths, y, sizeY-s, x, sizeX);
    }

    /**
     * recursive call, may cause timeout due to returning too many stacks?
     */
    public static void fight(List<List<Integer>> strengths, List<List<Long>> accumulativeStrengths, int x, int sizeX, int y, int sizeY){
        if (sizeX <= 0){
            System.out.println(y+1);
            return;
        }
        if (sizeY <= 0){
            System.out.println(x+1);
            return;
        }
        if (accumulativeStrengths.get(x).get(sizeX-1) >= accumulativeStrengths.get(y).get(sizeY-1)){
            System.out.println(x+1);
            return;
        }

        int s = strengths.get(x).get(sizeX-1);
        fight(strengths, accumulativeStrengths, y, sizeY-s, x, sizeX);
    }

    /**
     * switch to iterative call to avoid recursive issue
     */
    public static int fight2(List<Integer> strengthsX, List<Integer> strengthsY,
                              List<Long> accumulativeStrengthsX, List<Long> accumulativeStrengthsY,
                              int x, int y){
        int sizeX = strengthsX.size();
        int sizeY = strengthsY.size();
        boolean turnX = true;
        while (true) {
            if (turnX) {
                if (sizeX <= 0){
                    return y;
                } else if (sizeY <= 0){
                    return x;
                } else if (accumulativeStrengthsX.get(sizeX-1) >= accumulativeStrengthsY.get(sizeY-1)){
                    return x;
                } else {
                    sizeY -= strengthsX.get(sizeX-1);
                    if (sizeY <= 0)
                        return x;
                }
            } else {
                if (sizeY <= 0){
                    return x;
                } else if (sizeX <= 0){
                    return y;
                } else if (accumulativeStrengthsY.get(sizeY-1) >= accumulativeStrengthsX.get(sizeX-1)){
                    return y;
                } else {
                    sizeX -= strengthsY.get(sizeY-1);
                    if (sizeX <= 0)
                        return y;
                }
            }
            turnX = !turnX;
        }
    }
}
