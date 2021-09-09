package algorithm.implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * https://www.hackerrank.com/challenges/bon-appetit/problem
 *
 * Two friends Anna and Brian, are deciding how to split the bill at a dinner.
 * Each will only pay for the items they consume.
 * Brian gets the check and calculates Anna's portion.
 * You must determine if his calculation is correct.
 *
 * For example, assume the bill has the following prices: bill = {2, 4, 6}.
 * Anna declines to eat item k = bill[2] which costs 6.
 * If Brian calculates the bill correctly, Anna will pay (2+4)/2 = 3.
 * If he includes the cost of bill[2], he will calculate (2+4+6)/2 = 6.
 * In the second case, he should refund 3 to Anna.
 *
 * Constraints
 * 2<= n <= 10^5
 * 0 <= bill[i] <=  10^4
 */
public class BillDivision {

    static void bonAppetit(List<Integer> bill, int k, int b) {
        int sum = bill.stream().reduce(0, (x, y) -> x+y);
        int actualCost = (sum-bill.get(k))/2;
        if (actualCost == b) {
            System.out.println("Bon Appetit");
        } else {
            System.out.println(b-actualCost);
        }

    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String[] nk = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(nk[0]);

        int k = Integer.parseInt(nk[1]);

        List<Integer> bill = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        int b = Integer.parseInt(bufferedReader.readLine().trim());

        bonAppetit(bill, k, b);

        bufferedReader.close();
    }
}
