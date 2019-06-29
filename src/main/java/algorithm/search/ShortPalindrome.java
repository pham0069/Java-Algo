package algorithm.search;

import java.util.ArrayList;
import java.util.List;

/**
 * https://www.hackerrank.com/challenges/short-palindrome/problem
 * Consider a string, s, of n lowercase English letters where each character, s[i] (, denotes the letter at index i in s).
 * We define an  palindromic tuple of (a, b, c, d) to be a sequence of indices in s satisfying the following criteria:
 *
 * s[a] = s[d]
 * s[b] = s[c]
 * 0<= a < b < c < d < n, meaning that a, b, c, and d are ascending in value and are valid indices within string s.
 *
 * Given s, find and print the number of (a, b, c, d) tuples satisfying the above conditions.
 * As this value can be quite large, print it modulo (10^9 + 7).
 *
 * Constraints
 * 1 <= n <= 10^6
 * it's guaranteed that s only contains lowercase English letters
 *
 * Approach
 * 1. First idea is getting list of index for each character in string s
 * For each character, nested loop to get a pair of distinct index, i.e. a and d, as the first and fourth chars of palindrome
 * For each character, find the range of index that > a and < d.
 * Any 2 index obtained from this range can form a pair b, c for the palindrome
 * Searching for this range can use binary search on the index list
 *
 * However time out :<
 *
 * 2. Learn from discussion
 * For each character c, count the number of occurrence for c, cx, cxx, cxxc
 * where x is some character
 * total count of cxxc is the answer  we need
 *
 */
public class ShortPalindrome {
    static final int MOD = 1_000_000_007;

    static int shortPalindrome(String s) {
        char[] array = s.toCharArray();
        long count = 0;
        for (char c = 'a'; c <= 'z'; c++) {
            count = (count + makeArray(c, array)) % MOD;
        }
        return (int) count;
    }

    private static int makeArray(char aChar, char[] array) {
        // count occurrence of cx for each char x
        int[] cx = new int[26];
        // count occurrence of c, cxx, cxxc where x is some char
        int c = 0, cxx = 0, cxxc = 0;

        int xi;
        for (char x : array) {
            xi = x - 97;
            int prev = c;

            if (aChar == x) {
                cxxc = (cxxc + cxx) % MOD;
                c = (1 + c) % MOD;
            }

            cxx = (cxx + cx[xi]) % MOD;
            cx[xi] = (cx[xi] + prev) % MOD;
        }
        return cxxc;
    }

    // Timeout. Complexity O(NlogN*26*26)
    static int shortPalindrome2(String s) {
        List<List<Integer>> allFrequencies = new ArrayList<>(26);
        for (int i = 0; i < 26; i++) {
            allFrequencies.add(new ArrayList<>());
        }

        for (int i = 0; i < s.length(); i++) {
            allFrequencies.get(s.charAt(i)-97).add(i);
        }

        long count = 0;
        for (List<Integer> firstCharFreq : allFrequencies) {
            if (firstCharFreq.size() < 2) {
                continue;
            }
            for (int j = 0; j < firstCharFreq.size(); j++) {
                for (int k = j+1; k < firstCharFreq.size(); k++) {
                    int a= firstCharFreq.get(j);
                    int d = firstCharFreq.get(k);
                    if (d - a <= 2) {
                        continue;
                    }
                    for (List<Integer> secondCharFreq : allFrequencies) {
                        if (secondCharFreq.size() < 2) {
                            continue;
                        }
                        int lowerBound = binarySearchStrictlyGreaterThanKey(secondCharFreq, a);
                        if (lowerBound == -1) {
                            continue;
                        }
                        int upperBound = binarySearchStrictlyLessThanKey(secondCharFreq, d);
                        if (upperBound <= lowerBound) {
                            continue;
                        }
                        long numberInRange = upperBound - lowerBound +1;
                        count = (count + ((numberInRange*(numberInRange-1))/2))%MOD;
                    }
                }
            }
        }

        return (int) count;
    }

    static int binarySearchStrictlyGreaterThanKey(List<Integer> array, int key) {
        if (array.isEmpty()) {
            return -1;
        }
        return binarySearchStrictlyGreaterThanKey(array, 0, array.size()-1, key);
    }

    static int binarySearchStrictlyLessThanKey(List<Integer> array, int key) {
        if (array.isEmpty()) {
            return -1;
        }
        return binarySearchStrictlyLessThanKey(array, 0, array.size()-1, key);
    }

    /**
     * Find the smallest index such that element larger or equal to the key
     * The index is in the inclusive range from start to end
     * Return -1 if no such index is found
     * @param array
     * @param start
     * @param end
     * @param key
     * @return
     */
    static int binarySearchStrictlyGreaterThanKey(List<Integer> array, int start, int end, int key) {
        int low = start, high = end, mid =(low+high)/2;
        if (array.get(high) <= key) ////// mod
            return -1;
        int foundIndex = -1;
        while (low <= high) {
            if (array.get(mid) == key) {
                //to ensure returning max index satisfying the condition
                foundIndex = mid;
                break;
            } else if (array.get(mid) < key) {
                low = mid+1;
            } else {
                high = mid-1;
            }
            mid = (low+high)/2;
        }

        //// mod from here
        if (foundIndex == -1) {
            return low;
        }

        //find the largest index with value = key
        while (foundIndex < end && array.get(foundIndex + 1) == key) {
            foundIndex++;
        }
        return foundIndex+1;
    }

    static int binarySearchStrictlyLessThanKey(List<Integer> array, int start, int end, int key) {
        int low = start, high = end, mid =(low+high)/2;
        if (array.get(low) >= key) //////
            return -1;
        int foundIndex = -1;
        while (low <= high) {
            if (array.get(mid) == key) {
                //to ensure returning max index satisfying the condition
                foundIndex = mid;
                break;
            }
            else if (array.get(mid) < key) {
                low = mid+1;
            } else {
                high = mid-1;
            }
            mid = (low+high)/2;
        }

        //// mod from here
        if (foundIndex == -1) {
            return high;
        }

        //find the smallest index with value = key
        while (foundIndex > start && array.get(foundIndex - 1) == key) {
            foundIndex--;
        }
        return foundIndex-1;
    }

    public static void main(String[] args) {
        System.out.println(shortPalindrome("akakak"));
    }

}
