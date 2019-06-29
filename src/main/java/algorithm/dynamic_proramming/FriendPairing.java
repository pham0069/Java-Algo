package algorithm.dynamic_proramming;

/**
 * https://www.geeksforgeeks.org/friends-pairing-problem/
 * Given n friends, each one can remain single or can be paired up with some other friend. Each friend can be paired
 * only once. Find out the total number of ways in which friends can remain single or can be paired up.
 * ---------------------------------------------------------------------------------------------------------------------
 * Sample input 1: n = 3
 * Sample output 1: 4
 * Explanation There are 4 possibilities of 3 people's status:
 * {1}, {2}, {3} : all single
 * {1}, {2,3} : 2 and 3 paired but 1 is single.
 * {1,2}, {3} : 1 and 2 are paired but 3 is single.
 * {1,3}, {2} : 1 and 3 are paired but 2 is single.
 * Note that {1,2} and {2,1} are considered same.
 * ---------------------------------------------------------------------------------------------------------------------
 * Denote W(n) as number of possibilities of n people's status.
 * If 1 is single, number of possibilities = W(n-1)
 * If 1 is paired, 1 can be paired with one of (n-1) people, number of possibilities after 1 is paired up = W(n-2)
 * W(n) = W(n-1) + (n-1)W(n-2)
 * Base case: W(1) = 1, W(2) = 2
 */
public class FriendPairing {
    public static void main(String[] args){
        int n = 3;
        System.out.println(getNumberOfStatus(n));
    }
    public static long getNumberOfStatus(int people){
        long prev = 1, cur = 2;
        if (people == 1 || people == 2)
            return people;
        long total = 0;
        for (int i = 3; i <= people; i++){
            total = cur + (i-1) * prev;
            prev = cur;
            cur = total;
        }
        return total;
    }
}
