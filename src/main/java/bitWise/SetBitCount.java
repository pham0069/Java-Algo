package bitWise;

/**
 * https://www.geeksforgeeks.org/count-set-bits-in-an-integer/
 */
public class SetBitCount {
    public static void main(String[] args) {
        int n = 100;
        System.out.println(countSetBitInBinaryRep(n));
        System.out.println(countByClearingLowestBit(n));
        System.out.println(countWithLookupTable(n));
    }

    /*
     * complexity = O(logn)
     */
    static int countSetBitInBinaryRep(int n) {
        int count = 0;
        while (n> 0) {
            count += n&1;//if lowest bit set, count +1
            n >>=1;//n = n/2, move to the next bit
        }
        return count;
    }

    /**
     * If we keep clearing set bit of a number, we finally get 0 value
     * Number of clearing times = number of set bits
     * Brian Kernighan’s Algorithm
     * @param n
     * @return
     */
    static int countByClearingLowestBit(int n) {
        int count = 0;
        while (n!= 0) {
            count ++;
            n = n & (n-1);
        }
        return count;
    }

    /**
     * Create a table to store number of set bits for all 8-bit numbers (< 256)
     * Consider an integer (32-bit) as a group of 4 8-bit numbers
     * @param n
     * @return
     */
    static int countWithLookupTable(int n) {
        int[] table = new int[256];
        initializeTable(table);
        return (table[n & 0xff]             // lowest 8-bit
                + table[(n >> 8) & 0xff]    // next 8-bit
                + table[(n >> 16) & 0xff]   // next 8-bit
                + table[n >> 24]);          // highest 8-bit
    }

    static void initializeTable(int[] table) {
        table[0] = 0;
        for (int i = 0; i < 256; i++) {
            table[i] = (i & 1) + table[i / 2];
        }
    }

}
