package artificial_intelligence;

import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/saveprincess2
 *
 * In this version of "Bot saves princess", Princess Peach and bot's position are randomly set. Can you save the princess?
 *
 * Input Format
 *
 * The first line of the input is N (<100), the size of the board (NxN). The second line of the input contains two space
 * separated integers, which is the position of the bot.
 *
 * Grid is indexed using Matrix Convention
 *
 * The position of the princess is indicated by the character 'p' and the position of the bot is indicated by the
 * character 'm' and each cell is denoted by '-' (ascii value: 45).
 *
 * Output Format
 *
 * Output only the next move you take to rescue the princess. Valid moves are LEFT, RIGHT, UP or DOWN
 *
 * Sample input
 5
 2 3
 -----
 -----
 p--m-
 -----
 -----
 * Sample output
 LEFT
 */
public class BotSavePrincess2 {
    private static class Location {
        Location(int x, int y) {
            this.x = x;
            this.y = y;
        }
        int x, y;
    }

    private static final char PRINCESS = 'p';
    private static final char BOT = 'm';
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); sc.nextLine();
        Location princess = null, bot = null;
        for (int i = 0; i < n; i++) {
            String s = sc.nextLine();
            if (princess == null) {
                int j = s.indexOf(PRINCESS);
                if (j >= 0)
                    princess = new Location(i, j);
            }
            if (bot == null) {
                int j = s.indexOf(BOT);
                if (j >= 0)
                    bot = new Location(i, j);
            }
        }

        /*
        String direction = bot.x < princess.x ? "DOWN":"UP";
        for (int i = 0; i < Math.abs(bot.x - princess.x); i++) System.out.println(direction);
        direction = bot.y < princess.y ? "RIGHT":"LEFT";
        for (int i = 0; i < Math.abs(bot.y - princess.y); i++) System.out.println(direction);
         */

        if (bot.x != princess.x) {
            System.out.println(bot.x < princess.x ? "DOWN":"UP");
        } else {
            if (bot.y != princess.y)
                System.out.println(bot.y < princess.y ? "RIGHT":"LEFT");
        }
    }
}
