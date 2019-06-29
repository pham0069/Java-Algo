package algorithm.dynamic_proramming;

/**
 * https://www.geeksforgeeks.org/tiling-problem/
 * Given a “2 x n” board and tiles of size “2 x 1”, count the number of ways to tile the given board using the 2 x 1 tiles.
 * A tile can either be placed horizontally i.e., as a 1 x 2 tile or vertically i.e., as 2 x 1 tile.
 *
 * For examples: n = 3, output = 3
 * Explanation: We need 3 tiles to tile the board of size  2 x 3.
 * We can tile the board using following ways
 * 1) Place all 3 tiles vertically.
 * 2) Place first tile vertically and remaining 2 tiles horizontally.
 * 3) Place first 2 tiles horizontally and remaining tiles vertically
 *
 * n = 4, output = 5
 * Explanation: For a 2 x 4 board, there are 5 ways
 * 1) All 4 vertical
 * 2) All 4 horizontal
 * 3) First 2 vertical, remaining 2 horizontal
 * 4) First 2 horizontal, remaining 2 vertical
 * 5) Corner 2 vertical, middle 2 horizontal
 *
 * Denote S[n] as the number of ways to tile the board 2xn
 * If the first board is placed vertically, the remaining area to tile is 2x(n-1). The the number of ways is S[n-1]
 * If the first two boards are placed horizontally, the remaining area to tile is 2x(n-2). The the number of ways is S[n-2]
 * S[n] = S[n-1] + S[n-2]
 * Base case S[1] = 1, S[2] = 2
 * This is equivalent to calculating Fibonaci numbers
 */
public class TilingProblem {
}
