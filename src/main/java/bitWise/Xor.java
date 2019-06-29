package bitWise;

public class Xor {
    public static void main(String[] args) {
        System.out.println(smartXor(65, 80));
    }

    /**
     * Prove by listing possible cases of bit combination
     * x            1   1   0   0
     * y            1   0   1   0
     * x^y          0   1   1   0
     * x|y - x&y    0   1   1   0
     * @param x
     * @param y
     * @return
     */
    static int smartXor(int x, int y) {
        return (x|y) - (x&y);
    }
}
