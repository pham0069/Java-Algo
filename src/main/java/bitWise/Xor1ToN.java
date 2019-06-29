package bitWise;

public class Xor1ToN {
    public static void main(String[] args) {
        System.out.println(quickXor(6));
    }

    /**
     * Observation: if n even -> n ^ (n+1) = 1
     * 1 ^ 1 = 0
     * Thus if n divisible by 4, the end result = 0
     * @param n
     */
    static int quickXor(int n) {
        switch (n%4) {
            case 0: return n;
            case 1: return 1;
            case 2: return n+1;//1^n = 1+n for n even
            default: return 0;
        }
    }
}
