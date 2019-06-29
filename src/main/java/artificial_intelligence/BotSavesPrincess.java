package artificial_intelligence;

import java.util.Scanner;

/**
 * Princess Peach is trapped in one of the four corners of a square grid. You are in the center of the grid and can
 * move one step at a time in any of the four directions. Can you rescue the princess?
 *
 * Input format
 *
 * The first line contains an odd integer N (3 <= N < 100) denoting the size of the grid. This is followed by an NxN
 * grid. Each cell is denoted by '-' (ascii value: 45). The bot position is denoted by 'm' and the princess position is
 * denoted by 'p'.
 *
 * Grid is indexed using Matrix Convention
 *
 * Output format
 *
 * Print out the moves you will take to rescue the princess in one go. The moves must be separated by '\n', a newline.
 * The valid moves are LEFT or RIGHT or UP or DOWN.
 *
 * Sample input
 3
 ---
 -m-
 p--
 * Sample output
 DOWN
 LEFT
 */
public class BotSavesPrincess {
    private enum Direction {
        LEFT,
        RIGHT,
        UP,
        DOWN,
    }

    private static final char PRINCESS = 'p';
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); sc.nextLine();
        Direction[] directions = new Direction[2];
        for (int i = 0; i < n; i++) {
            String s = sc.nextLine();
            if (i==0) {
                if (s.charAt(0) == PRINCESS){
                    directions[0] = Direction.UP;
                    directions[1] = Direction.LEFT;
                } else if (s.charAt(n-1) == PRINCESS) {
                    directions[0] = Direction.UP;
                    directions[1] = Direction.RIGHT;
                }
            } else if (i == (n-1)) {
                if (s.charAt(0) == PRINCESS){
                    directions[0] = Direction.DOWN;
                    directions[1] = Direction.LEFT;
                } else if (s.charAt(n-1) == PRINCESS) {
                    directions[0] = Direction.DOWN;
                    directions[1] = Direction.RIGHT;
                }
            }
        }

        for (int i = 0; i < (n-1)/2; i++) System.out.println(directions[0].name());
        for (int i = 0; i < (n-1)/2; i++) System.out.println(directions[1].name());
    }
}
