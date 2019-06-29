package io;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * In Scanner class if we call nextLine() method after any one of the seven nextXXX() method then the nextLine() doesn’t
 * not read values from console and cursor will not come into console it will skip that step. The nextXXX() methods are
 * nextInt(), nextFloat(), nextByte(), nextShort(), nextDouble(), nextLong(), next().
 *
 * In BufferReader class there is no such type of problem. This problem occurs only for Scanner class, due to nextXXX()
 * methods ignore newline character and nextLine() only reads till first newline character. If we use one more call of
 * nextLine() method between nextXXX() and nextLine(), then this problem will not occur because nextLine() will consume
 * the newline character.
 *
 * Other differences between Scanner and BufferedReader
 * 1. BufferedReader is synchronous while Scanner is not. BufferedReader should be used if we are working with multiple
 * threads.
 * 2. BufferedReader has significantly larger buffer memory than Scanner. The Scanner has a little buffer (1KB char buffer)
 * as opposed to the BufferedReader (8KB byte buffer), but it’s more than enough.
 * 3. BufferedReader is a bit faster as compared to scanner because scanner does parsing of input data and BufferedReader
 * simply reads sequence of characters.

 */
public class DifferenceBetweenScannerAndBufferedReader {
    public static void main(String args[]) throws IOException{
        //testWithBufferedReader();
        //testWithScanner();
        testWithScanner2();
    }

    /**
     * There's no problem with readLine
     */
    static void testWithBufferedReader() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter an integer"); // 50
        int a = Integer.parseInt(br.readLine());
        System.out.println("Enter a String"); // Geek
        String b = br.readLine();
        System.out.printf("You have entered:- " + a + " and name as " + b);// You have entered:- 50 and name as Geek
    }

    /**
     * nextInt() only reads '50' excluding the new line char
     * nextLine() hence reads this new line char and miss reading 'Geek'
     */
     static void testWithScanner() {
        Scanner scn = new Scanner(System.in);
        System.out.println("Enter an integer"); // 50
        int a = scn.nextInt();
        System.out.println("Enter a String"); // Geek
        String b = scn.nextLine();
        System.out.printf("You have entered:- " + a + " " + "and name as " + b);// You have entered:- 50 and name as
    }

    /**
     * Fix problem with nextLine()
     */
    static void testWithScanner2() {
        Scanner scn = new Scanner(System.in);
        System.out.println("Enter an integer");
        int a = scn.nextInt();
        //fix by reading the new line char after reading integer input
        scn.nextLine();
        System.out.println("Enter a String");
        String b = scn.nextLine();
        System.out.printf("You have entered:- " + a + " " + "and name as " + b);
    }
}
