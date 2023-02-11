package algorithm.dynamic_proramming;

import java.util.Stack;

/**
 * Given a rows x cols binary matrix filled with 0's and 1's
 * Find the largest rectangle containing only 1's and return its area
 */
public class MaxRectangle {
    public static void main(String[] args) {
        int[][] matrix = {
                {0, 0, 1},
                {0, 1, 1},
                {1, 1, 1},
                {0, 1, 1},
        };
        System.out.println(getMaxRectangle(matrix));
    }

    //O(m*n)
    private static int getMaxRectangle(int[][] matrix) {
        int m = matrix.length;
        int n  = matrix[0].length;

        int[] height = new int[n];
        int maxRect = 0;

        for (int i = 0; i < m; i++) {

            // populate height array
            for (int j = 0; j < n; j++) {
                if (height[j] == 0) {
                    // find the height starting from row i, at col j
                    for (int k = i; k < m; k++) {
                        if (matrix[k][j] == 1) {
                            height[j] += 1;
                        } else {
                            break;
                        }
                    }
                } else {
                    height[j] = height[j]-1;
                }
            }

            // get max rectangle at given row
            maxRect = Math.max(maxRect, getMaxRectangle(height));
        }

        return maxRect;
    }

    //O(n)
    private static int getMaxRectangle(int[] height) {
        int size = height.length;

        Stack<Integer> leftStack = new Stack<>();
        int[] leftIndex = new int[size];// store rightmost index on the left that smaller than height at given index
        for (int i = 0; i < size; i++) {
            buildStack(leftStack, leftIndex, height, i);
        }

        Stack<Integer> rightStack = new Stack<>();
        int[] rightIndex = new int[size];// store leftmost index on the right that smaller than height at given index
        for (int i = size-1; i >= 0; i--) {
            buildStack(rightStack, rightIndex, height, i);
        }

        int width;
        int maxRect = 0;
        for (int i = 0; i < size; i++) {
            width = 1;
            if (leftIndex[i] != -1) {
                width += i-1 - leftIndex[i];
            }

            if (rightIndex[i] != -1) {
                width += rightIndex[i] - i - 1;
            } else {
                width += size - 1 - i;
            }
            maxRect = Math.max(maxRect, height[i]*width);
        }

        return maxRect;
    }

    //O(n)
    private static void buildStack(Stack<Integer> stack, int[] index, int[] height, int i) {
        while (!stack.empty()) {
            if (height[stack.peek()] < height[i]) {
                index[i] = stack.peek();
                break;
            } else {
                stack.pop();
            }
        }

        if (stack.empty()) {
            index[i] = -1; // -1 means non-exist
        }

        stack.push(i);
    }

}
