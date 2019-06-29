package algorithm.recursive;

/**
 *
 * https://www.geeksforgeeks.org/c-program-for-tower-of-hanoi/
 *
 * Tower of Hanoi is a mathematical puzzle where we have three rods and n disks.
 * The objective of the puzzle is to move the entire stack to another rod,
 * obeying the following simple rules:
 *
 * 1. Only one disk can be moved at a time.
 * 2. Each move consists of taking the upper disk from one of the stacks and placing it on top of another stack i.e. a disk can only be moved if it is the uppermost disk on a stack.
 * 3. No disk may be placed on top of a smaller disk.
 */
public class TowerOfHanoi {
    static void towerOfHanoi(int n, int fromRod, int toRod, int auxRod) {
        if (n == 1) {
            System.out.println(String.format("Move disk %d from rod %d to rod %d", 1, fromRod, toRod));
            return;
        }
        // move disk 1 -> n-1 from fromRod to auxRod
        towerOfHanoi(n - 1, fromRod, auxRod, toRod);
        // move disk n from fromRod to toRod
        System.out.println(String.format("Move disk %d from rod %d to rod %d", n, fromRod, toRod));
        // move disk 1 -> n-1 from auxRod to toRod
        towerOfHanoi(n - 1, auxRod, toRod, fromRod);
    }

    public static void  main(String args[]) {
        int n = 4; // Number of disks
        towerOfHanoi(n, 1, 2, 3);
    }
}
