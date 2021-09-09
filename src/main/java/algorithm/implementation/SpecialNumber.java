package algorithm.implementation;

/**
 * Special Number (SN) được định nghĩa như sau:
 *
 * + Số các chữ số là chẵn
 *
 * + Viết ngược hay xuôi đều bằng nhau
 *
 * + Nó chỉ chứa 2 hoặc 3 hoặc 4
 *
 * Ví dụ các số SN: 22, 4444, 4224, 42422424, .....
 *
 * Yêu cầu:
 *
 * Bài test cung cấp sẵn 1 function với parameter (i với 1 <= i <= 10^6)
 * là vị trí số cần tìm trong dãy SN. Hoàn thiện function và trả về số thứ i trong dãy SN
 *
 * input 1 should return output 22
 * input 4 should return output 2222
 * input 10 should return output 4224
 * input 100 should return output 42422424
 * input 555 should return output 243224422342
 * input 888 should return output 423324423324
 * input 1000 should return output 434342243434
 * input 5555 should return output 3223224334223223
 * input 55555 should return output 32444232322323244423
 *
 */
public class SpecialNumber {
    public static void main(String[] args) {
        System.out.println(getNthSpecialNumber(100));
        System.out.println(getNthSpecialNumber(555));
        System.out.println(getNthSpecialNumber(888));
        System.out.println(getNthSpecialNumber(1000));
        System.out.println(getNthSpecialNumber(5555));
        System.out.println(getNthSpecialNumber(55555));
    }

    //n = 3 + 3^2 + ... + 3^d + r where 0 <= r < 3^(d+1)
    //n < 3 + 3^2 + ... + 3^(d+1) = (3^(d+2)-3)/2
    //n >= (3^(d+1)-3)/2
    //log3(2*n+3)-2 < d <= log3(2*n+3)-1 -> d = Math.floor(loga) - 1
    static String getNthSpecialNumber(int n) {
        int d = (int) Math.floor(Math.log(2*n+3)/Math.log(3)) - 1;
        int r = n - (int) Math.round((Math.pow(3, d+1)-3)/2);
        String firstHalf;
        if (r == 0) {
            firstHalf = getFirstHalf(d, (int) Math.round(Math.pow(3,d)-1));
        } else {
            firstHalf = getFirstHalf(d + 1, r-1);
        }
        return getPalindrome(firstHalf);
    }

    // zero-index based rank
    // convert rank to 3-ary numbers
    static String getFirstHalf(int digitNum, int rank) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < digitNum; i++) {
            switch(rank%3) {
                case 0: builder.append("2"); break;
                case 1: builder.append("3"); break;
                case 2: builder.append("4"); break;
            }
            rank = rank/3;
        }
        return builder.reverse().toString();
    }

    static String getPalindrome(String firstHalf) {
        return firstHalf + new StringBuilder(firstHalf).reverse().toString();
    }
}
