package maths.gcd;

/**
 * https://www.geeksforgeeks.org/c-program-find-lcm-two-numbers/
 * https://www.geeksforgeeks.org/lcm-of-given-array-elements/
 * https://www.geeksforgeeks.org/gcd-two-array-numbers/
 *
 * GCD (Greatest Common Divisor) of two numbers is the largest number which can be divided by both numbers
 * LCM (Least Common Multiple) of two numbers is the smallest number which can divide both numbers.
 */
public class GcdAndLcm {
    public static int getGreatestCommonDivisor(int[] array){
        long gcd = array[0];
        for (int i = 1; i < array.length; i++)
            gcd = getGreatestCommonDivisor(gcd, array[i]);
        return (int) gcd;
    }

    public static long getGreatestCommonDivisor(long a, long b){
        if (a==0)
            return b;
        return getGreatestCommonDivisor(b%a, a);
    }
    public static long getLeastCommonMultiple(long a, long b){
        long gcd = getGreatestCommonDivisor(a, b);
        return a*b/gcd;
    }
    public static long getLeastCommonMultiple(int[] array){
        long lcm = array[0];
        for (int i = 1; i < array.length; i++)
            lcm = getLeastCommonMultiple(lcm, array[i]);
        return lcm;
    }


}
