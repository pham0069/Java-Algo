package algorithm.string;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Queue;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/determining-dna-health/problem
 *
 * DNA is a nucleic acid present in the bodies of living things. Each piece of DNA contains a number of genes, some of
 * which are beneficial and increase the DNA's total health. Each gene has a health value, and the total health of a DNA
 * is the sum of the health values of all the beneficial genes that occur as a substring in the DNA. We represent genes
 * and DNA as non-empty strings of lowercase English alphabetic letters, and the same gene may appear multiple times as
 * a susbtring of a DNA.
 *
 * Given the following:
 *
 * 1. An array of beneficial gene strings, genes = {g[0], g[1], ..., g[n-1]}. Note that these gene sequences are not
 * guaranteed to be distinct.
 * 2. An array of gene health values, health = {h[0], h[1], ..., h[n-1]}, where each h[i] is the health value for gene
 * g[i].
 * 3. A set of s DNA strands where the definition of each strand has three components, start, end, and d, where string d
 * is a DNA for which genes g[start]... g[end] are healthy.
 * Find and print the respective total healths of the unhealthiest (minimum total health) and healthiest (maximum total
 * health) strands of DNA as two space-separated values on a single line.
 *
 *
 * Input Format
 *
 * The first line contains an integer, n, denoting the total number of genes.
 * The second line contains n space-separated strings describing the respective values of g[0], g[1]... g[n-1]  (i.e.,
 * the elements of genes).
 * The third line contains  space-separated integers describing the respective values of h[0], h[1]... h[n-1] (i.e.,
 * the elements of healths).
 * The fourth line contains an integer, s, denoting the number of strands of DNA to process.
 * Each of the s subsequent lines describes a DNA strand in the form start end d, denoting that the healthy genes for
 * DNA strand d are g[start], ... g[end] and their respective correlated health values are h[start]...h[end].
 *
 * Constraints
 * 1 <= n, s <= 10^5
 * 0 <= h[i] <= 10^7
 * 0 < first < last < n
 * 1 <= the sum of the lengths of all genes and DNA strands <= 2*10^6
 * It is guaranteed that each g[i] consists of lowercase English alphabetic letters only (i.e., a to z).
 * Output Format
 *
 * Print two space-separated integers describing the respective total health of the unhealthiest and the healthiest strands of DNA.
 *
 * Sample Input 0
 *
 6
 a b c aa d b
 1 2 3 4 5 6
 3
 1 5 caaab
 0 4 xyz
 2 4 bcdybc
 * Sample Output 0
 *
 0 19
 * Explanation 0
 * Given genes [1..5] and DNA strand 'caaab'
 * gene[1] 'b' is found once -> health = 2*1 = 2
 * gene[2] 'c' is found once -> health = 3*1 = 3
 * gene[3] 'aa' is found twice -> health = 4*2 = 8
 * gene[4] 'd' is not found -> health = 0
 * gene[5] 'b' is found once -> health = 6*1 = 6
 * The total health of 'caaab' is 2+3+8+6 = 19.
 *
 * The total health of 'xyz' is 0, because it contains no beneficial genes.
 * The total health of 'bcdybc' is 3*2+5 = 11.
 * The unhealthiest DNA strand is 'xyz' with a total health of 0, and the healthiest DNA strand is 'caaab' with a total
 * health of 19. Thus, we print 0 19 as our answer.
 * ---------------------------------------------------------------------------------------------------------------------
 *
 * This is equivalent to find matching pattern in a string
 * For each gene, find out how many time it appears in the DNA strand
 */
public class DeterminingDNAHealth {


    public static void main(String[] args) {
    }
}
