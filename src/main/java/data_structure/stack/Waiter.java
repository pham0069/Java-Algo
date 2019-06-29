package data_structure.stack;

import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;

/**
 * https://www.hackerrank.com/challenges/waiter/problem
 *
 * You are a waiter at a party. There are N stacked plates on pile A[0]. Each plate has a number written on it. Then there
 * will be Q iterations. In i-th iteration, you start picking up the plates in stack A[i-1] from the top one by one and
 * check whether the number written on the plate is divisible by the i-th prime. If the number is divisible, you stack that
 * plate on pile B[i]. Otherwise, you stack that plate on pile A[i]. After Q iterations, plates can only be on pile B[1],
 * B[2]... B[Q], A[Q]. Output numbers on these plates from top to bottom of each piles in order of B[1], B[2], ... B[Q],
 * A[Q].
 */
public class Waiter {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int q = sc.nextInt();

        //find the first q prime numbers and store them in primes
        int[] primes = fillPrimes(q);

        //store plates in stack a0
        Stack<Integer> a0 = new Stack<>();
        for (int i = 0; i < n; i++){
            a0.push(sc.nextInt());
        }
        //prepare empty stacks ai and bi
        Stack[] b = new Stack[q];
        for (int i = 0; i < q; i++)
            b[i] = new Stack<Integer>();
        Stack<Integer> ai = new Stack<>();

        //iterate for q times
        for (int i = 0; i < q; i++){
            while (!a0.isEmpty()){
                int top = a0.pop();
                if (top % primes[i] == 0)
                    b[i].push(top);
                else
                    ai.push(top);
            }
            a0 = ai;
            ai = new Stack<>();
            printStack(b[i]);
        }
        printStack(a0);

    }

    static int[] fillPrimes(int q){
        int counter = 0;
        int n = 2;
        int[] primes = new int[q];
        while(counter < q){
            if (isPrime(n)){
                primes[counter] = n;
                ++counter;
            }
            ++n;
        }
        return primes;
    }
    static boolean isPrime(int p){
        int sr = (int) (Math.sqrt(p));
        for (int i = 2; i <= sr; i++ ){
            if (p%i == 0)
                return false;
        }
        return true;
    }

    static void printStackIterator(Stack<Integer> s){//print from bottom to top
        Iterator<Integer> it = s.iterator();
        while (it.hasNext()){
            int x = it.next();
            System.out.println(x);
        }
    }

    static void printStack(Stack<Integer> s){//print from top to bottom
        while (!s.isEmpty()){
            System.out.println(s.pop());
        }
    }
}
