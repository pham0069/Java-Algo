package algorithm.implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Oddity {
    private static final String ODD = "odd";
    private static final String EVEN = "even";

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = Integer.parseInt(br.readLine());
        }

        printOddity(array);
    }

    static void printOddity(int[] array) {
        for (int i : array) {
            printOddity(i);
        }
    }

    static void printOddity(int number) {
        boolean isEven = number % 2 == 0;
        System.out.println(String.format("%d is %s", number, isEven ? EVEN : ODD));
    }
}
