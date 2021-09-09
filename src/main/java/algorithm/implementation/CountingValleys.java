package algorithm.implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * https://www.hackerrank.com/challenges/counting-valleys/problem
 *
 */
public class CountingValleys {
    public static int countingValleys(int steps, String path) {
        int height = 0;
        int valley = 0;
        boolean down;
        for (int i = 0; i < steps; i++) {
            down = path.charAt(i) == 'D';

            //start of valley, traverse until  reach end of valley
            if (height == 0 && down) {
                height -=1;
                while (height != 0) {
                    i++;
                    down = path.charAt(i) == 'D';
                    if (down) {
                        height -=1;
                    } else {
                        height +=1;
                    }
                }
                valley++;
            } else {
                if (down) {
                    height -=1;
                } else {
                    height +=1;
                }
            }
        }
        return valley;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int steps = Integer.parseInt(bufferedReader.readLine().trim());

        String path = bufferedReader.readLine();

        System.out.println(countingValleys(steps, path));
        bufferedReader.close();
    }
}
