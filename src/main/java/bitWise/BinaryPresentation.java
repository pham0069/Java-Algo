package bitWise;

public class BinaryPresentation {
    public static void main(String[] args) {

    }

    static void oldSchool(int x) {
        for(int i = 1, j = 0; i < 256; i = i << 1, j++)
            System.out.println(j + " " + ((x & i) > 0 ? 1 : 0));
    }
    static void printBinaryString(int x) {
        System.out.println(Integer.toBinaryString(x));
    }
}
