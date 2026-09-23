public class Solution {
    public int firstMissingPositive(int[] nums) {
        int n = nums.length;

        // Step 1: Place each number in its correct index if possible
        // The ideal position for value 'x' is index 'x - 1'
        for (int i = 0; i < n; i++) {
            while (nums[i] > 0 && nums[i] <= n && nums[i] != nums[nums[i] - 1]) {
                // Swap nums[i] with the element at its target index
                int targetIndex = nums[i] - 1;
                int temp = nums[i];
                nums[i] = nums[targetIndex];
                nums[targetIndex] = temp;
            }
        }

        // Step 2: Scan the array to find the first index mismatch
        for (int i = 0; i < n; i++) {
            if (nums[i] != i + 1) {
                return i + 1; // Found the missing positive integer
            }
        }

        // Step 3: If all positions 1 to n are correct, the missing one is n + 1
        return n + 1;
    }
}
