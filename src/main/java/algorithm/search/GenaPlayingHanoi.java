package algorithm.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * https://www.hackerrank.com/challenges/gena/problem
 *
 * The Tower of Hanoi is a famous game consisting of  rods and a number of discs of incrementally different diameters.
 * The puzzle starts with the discs neatly stacked on one rod, ordered by ascending size with the smallest disc at the top.
 * The game's objective is to move the entire stack to another rod, obeying the following rules:
 *
 * 1. Only one disk can be moved at a time.
 * 2. In one move, remove the topmost disk from one rod and move it to another rod.
 * 3. No disk may be placed on top of a smaller disk.
 *
 * Gena has a modified version of the Tower of Hanoi.
 * This game of Hanoi has 4 rods and N disks ordered by ascending size.
 * Gena has already made a few moves following the rules above.
 * Given the state of Gena's Hanoi, determine the minimum number of moves needed
 * to restore the tower to its original state with all disks on rod 1.
 *
 * Note: Gena's rods are numbered from 1 to 4.
 * The radius of a disk is its index in the input array,
 * so disk 1 is the smallest disk with a radius of 1,
 * and disk N is the largest with a radius of N.
 *
 * Example:
 * posts = [4, 3, 2, 1]
 *
 * In this case, the disks are arranged from large to small across the 4 rods.
 * The largest disk, disk 4, is already on rod 1, so move disks 3, 2 and 1 to rod 1, in that order.
 * It takes 3 moves to reset the game.
 *
 *
 * posts = [4, 2, 2, 1]
 * The largest disk, disk 4 with radius 4, is already on rod 1.
 * Disk 3 is on rod 2 and must be below disk 2.
 * Move disk 2 to rod 3, disk 3 to rod 1 and disk 2 to rod 1.
 * Now move disk 1 to rod 1. It takes 4 moves to reset the game.
 *
 * Constraints
 * 1 <= n <= 10
 *
 *
 * Solution: Do BFS
 * - From 1 state (current location of disk on rods),
 * we can loop among rods for 2 times
 * to find valid moves (smaller disk cannot be below larger disk)
 * and thus infer next possible state
 * we can make 4*3 = 12 different moves from each position
 * - By BFS, it ensures we can reach the final goal (all disk on rod 1)
 * in minimum moves
 *
 * Optimizations required
 * 1. Instead of representing a state of a rod (list of disk on it) as a list of numbers,
 * we can represent it as an integer, where bit i is set if ith disk is located on this rod
 * For a state of 4 rods, it would be an array of 4 integers
 *
 * Alternatively, we can use a single integer to represent the state of all 4 rods
 * A rod number (0-3) can be represented in 2 bits
 * For ith disk, we can use bits 2*i and 2*i+1 to indicate the rod number that is holding the disk
 * n <= 10 -> need at most 20 bits for this representation
 * -> an int (4 bytes or 32 bits) suffices for this purpose
 *
 * 2. It is noted that if we swap any two of 2nd,3rd,4th rods 
 * then the minimal distance to the solution remains unchanged
 * thus we can consider two permutations of (2nd,3rd,4th rods) as same state
 * hence eliminate the states to be added to the BFS queue
 *
 * 3. BFS double-ended technique???
 */
public class GenaPlayingHanoi {
    static int RODS = 4;

    static class State {
        int[] rods;
        int moves;
        State(int[] rods, int moves) {
            this.rods = rods;
            this.moves = moves;
        }
    }

    static class Tuple {
        int[] rods;

        Tuple(int[] rods) {
            int[] sorted = Arrays.copyOf(rods, rods.length);
            Arrays.sort(sorted, 1, rods.length);
            this.rods = sorted;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Tuple) {
                Tuple that = (Tuple) o;
                for (int i = 0; i < rods.length; i++) {
                    if (rods[i] != that.rods[i]) {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }

        public int hashCode() {
            int hash = 0;
            for (int i = 0; i < rods.length; i++) {
                hash = (hash + rods[i])*131;
            }
            return hash;
        }
    }

    static int hanoi(List<Integer> posts) {
        int n = posts.size();
        // all bits from 0 to n-1 set to 1
        int goal = (int) (Math.pow(2, n) - 1);

        int[] rods = new int[RODS];
        for (int i = 0 ; i < n; i++) {
            //set bit i to 1 if disc i for the rod that this disc is located at
            rods[posts.get(i)-1] |= (1 << i);
        }

        if (rods[0] == goal) {
            return 0;
        }

        //bfs
        Deque<State> deque = new ArrayDeque<>();
        deque.add(new State(rods, 0));
        Set<Tuple> visited = new HashSet<>();
        visited.add(new Tuple(rods));
        Integer d1, d2;

        while (!deque.isEmpty()) {
            State state = deque.remove();
            int[] cur = state.rods;

            for (int i = 0; i < RODS; i++) {
                d1 = topDisc(cur[i]);
                if (d1 == null) {
                    continue;
                }

                for (int j = 0; j < RODS; j++) {
                    d2 = topDisc(cur[j]);
                    if (d2 == null || d1 < d2) { // this also implies i!= j
                        // move d1 from rod i to rod j (on top of d2)
                        int[] newRods = Arrays.copyOf(cur, RODS);
                        newRods[i] = popDisc(cur[i], d1);
                        newRods[j] = addDisc(cur[j], d1);

                        Tuple tuple = new Tuple(newRods);
                        if (!visited.contains(tuple)) {
                            if (newRods[0] == goal) {
                                return state.moves + 1;
                            }
                            visited.add(tuple);
                            deque.add(new State(newRods, state.moves+1));
                        }

                    }
                }
            }
        }

        return -1;
    }

    static int[] sort(int[] rods) {
        int[] sorted = new int[RODS];
        sorted[0] = rods[0];
        Arrays.sort(sorted, 1, 4);
        return sorted;
    }

    static int addDisc(int rod, int i) {
        return rod | 1 << i; // 1<<i: all bits = 0, except ith bit
    }

    static int popDisc(int rod, int i) {
        return rod & ~(1 << i); // ~(1<<i): all bits = 1, except ith bit
    }

    // find the LSB set
    static Integer topDisc(int rod) {
        if (rod == 0) {
            return null;
        }

        int i = 0;
        while((rod & 1) != 1) {
            rod >>=1;
            i++;
        }
        return i;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> loc = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        System.out.println(hanoi(loc));

        bufferedReader.close();
    }
}
