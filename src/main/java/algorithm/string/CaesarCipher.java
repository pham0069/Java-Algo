package algorithm.string;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * https://www.hackerrank.com/challenges/caesar-cipher-1/problem?isFullScreen=true
 *
 * Julius Caesar protected his confidential information by encrypting it using a cipher. Caesar's cipher shifts each letter by a number of letters. If the shift takes you past the end of the alphabet, just rotate back to the front of the alphabet. In the case of a rotation by 3, w, x, y and z would map to z, a, b and c.
 *
 * Original alphabet:      abcdefghijklmnopqrstuvwxyz
 * Alphabet rotated +3:    defghijklmnopqrstuvwxyzabc
 * Example
 *
 *
 * The alphabet is rotated by , matching the mapping above. The encrypted string is .
 *
 * Note: The cipher only encrypts letters; symbols, such as -, remain unencrypted.
 *
 * Function Description
 *
 * Complete the caesarCipher function in the editor below.
 *
 * caesarCipher has the following parameter(s):
 *
 * string s: cleartext
 * int k: the alphabet rotation factor
 * Returns
 *
 * string: the encrypted string
 * Input Format
 *
 * The first line contains the integer, , the length of the unencrypted string.
 * The second line contains the unencrypted string, .
 * The third line contains , the number of letters to rotate the alphabet by.
 *
 * Constraints
 * 1 <= n <= 100
 * 0 <= k <= 100
 *
 * s is a valid ASCII string without any spaces.
 *
 * Sample Input
 *
 * 11
 * middle-Outz
 * 2
 * Sample Output
 *
 * okffng-Qwvb
 * Explanation
 *
 * Original alphabet:      abcdefghijklmnopqrstuvwxyz
 * Alphabet rotated +2:    cdefghijklmnopqrstuvwxyzab
 *
 * m -> o
 * i -> k
 * d -> f
 * d -> f
 * l -> n
 * e -> g
 * -    -
 * O -> Q
 * u -> w
 * t -> v
 * z -> b
 */
public class CaesarCipher {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        String s = bufferedReader.readLine();

        int k = Integer.parseInt(bufferedReader.readLine().trim());

        String result = caesarCipher(s, k);
        System.out.println(result);
    }

    private static String caesarCipher(String text, int key) {
        Map<Character, Character> map = getClearToCipherMap(key);
        StringBuffer buffer = new StringBuffer();
        for (char c: text.toCharArray()) {
            buffer.append(map.getOrDefault(c, c));
        }
        return buffer.toString();
    }

    private static Map<Character, Character> getClearToCipherMap(int key) {
        Map<Character, Character> map = new HashMap<>();
        for (char c = 'A'; c <= 'Z'; c++) {
            map.put(c, (char) ((c+key-'A')%26 + 'A'));
        }
        for (char c = 'a'; c <= 'z'; c++) {
            map.put(c, (char) ((c+key-'a')%26 + 'a'));
        }
        return map;
    }
}
