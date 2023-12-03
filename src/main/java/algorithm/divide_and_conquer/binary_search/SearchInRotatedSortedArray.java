package algorithm.divide_and_conquer.binary_search;

/**
 * There is an integer array nums sorted in ascending order (with distinct values).
 * Prior to being passed to your function, nums is possibly rotated at an unknown pivot index k (1 <= k < nums.length)
 * such that the resulting array is [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]] (0-indexed).
 * For example, [0,1,2,4,5,6,7] might be rotated at pivot index 3 and become [4,5,6,7,0,1,2].
 * Given the array nums after the possible rotation and an integer target,
 * return the index of target if it is in nums, or -1 if it is not in nums.
 * You must write an algorithm with O(log n) runtime complexity

 *
 * Example 1:
 * Input: nums = [4,5,6,7,0,1,2], target = 0
 * Output: 4
 * Example 2:
 * Input: nums = [4,5,6,7,0,1,2], target = 3
 * Output: -1
 * Example 3:
 * Input: nums = [1], target = 0
 * Output: -1
 * Constraints:
 * 1 <= nums.length <= 5000
 * -10^4 <= nums[i] <= 10^4
 * All values of nums are unique.
 * nums is an ascending array that is possibly rotated.
 * -10^4 <= target <= 10^4
 *
 *
 */
public class SearchInRotatedSortedArray {
    public static void main(String[] args) {
        int[] array = {4,5,6,7,0,1,2};
        System.out.println(getIndex(array, 0));
        System.out.println(getIndex(array, 3));
    }

    private static int getIndex(int[] array, int target) {
        return getIndexFromRotatedSorted(array, 0, array.length-1, target);
    }

    private static int getIndexFromRotatedSorted(int[] array, int start, int end, int target) {
        if (start > end) {
            return -1;
        }

        if (target == array[start]) {
            return start;
        }
        if (target == array[end]) {
            return end;
        }
        int mid = (start+end)/2;
        if (target == array[mid]) {
            return mid;
        }

        // start - mid sorted
        if (array[start] < array[mid]) {
            if (target > array[start] && target < array[mid]) {
                return getIndexFromSorted(array, start, mid-1, target);
            } else {
                return getIndexFromRotatedSorted(array, mid + 1, end, target);
            }

        // mid - end sorted
        } else { // i.e. (array[start] > array[mid])
            if (target > array[mid] && target < array[end]) {
                return getIndexFromSorted(array, mid+1, end, target);
            } else {
                return getIndexFromRotatedSorted(array, start, mid-1, target);
            }
        }
    }

    private static int getIndexFromSorted(int[] array, int start, int end, int target) {
        if (start > end) {
            return -1;
        }

        int mid = (start+end)/2;
        if (target == array[mid]) {
            return mid;
        }

        if (target > array[mid]) {
            return getIndexFromSorted(array, mid+1, end, target);
        } else {
            return getIndexFromSorted(array, start, mid-1, target);
        }
    }

    private static int search(int[] nums, int target){
        int start = 0, end = nums.length-1;
        if(target==nums[start])     return start;
        if(target==nums[end])       return end;
        while(start<end){
            int mid = start+(end-start)/2;
            if(nums[mid]==target)
                return mid;
            if(nums[start] <= nums[mid])
                if (target>=nums[start] && target<nums[mid]) {
                    end = mid-1;
                } else {
                    start = mid+1;
                }
            else
                if (target>nums[mid] && target<=nums[end]) {
                    start = mid+1;
                } else {
                    end = mid-1;
                }
        }
        return nums[start] == target ? start : -1;
    }
}
