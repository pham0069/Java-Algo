package puzzle;

import java.util.Scanner;

/**
 * https://www.geeksforgeeks.org/puzzle-22-maximum-chocolates/
 * https://www.geeksforgeeks.org/program-chocolate-wrapper-puzzle/
 *
 * Given following three values, the task is to find the total number of maximum chocolates you can eat.
 *
 * money : Money you have to buy chocolates
 * price : Price of a chocolate
 * wrap : Number of wrappers to be returned for getting one extra chocolate.
 * It may be assumed that all given values are positive integers and greater than 1.
 *
 * For example, money = 15, price = 1, wrap = 3
 * Number of chocolates = 15 + 5 + 1 + 1 = 22
 */

public class ChocolateAndWrapper {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int money = sc.nextInt();
        int price = sc.nextInt();
        int wrap = sc.nextInt();

        int total = money/price;
        int wrappers = total;

        while (wrappers >= wrap) {
            int exchange = wrappers/wrap;
            total += exchange;
            int remaining = wrappers %wrap;
            wrappers = exchange + remaining;
        }

        System.out.println(total);
    }

}
