import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        // 1. Sort the array to group duplicates together
        Arrays.sort(nums);
        
        // 2. Backtrack to find all unique combinations
        backtrack(nums, new boolean[nums.length], new ArrayList<>(), result);
        return result;
    }

    private void backtrack(int[] nums, boolean[] used, List<Integer> current, List<List<Integer>> result) {
        // Base case: If the current list matches the size of nums, a full permutation is found
        if (current.size() == nums.length) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            // Skip if the element is already used in the current path
            if (used[i]) {
                continue;
            }

            // 3. Skip duplicate elements to avoid duplicate permutations
            // If nums[i] == nums[i-1] and nums[i-1] is not used, it means the 
            // permutation branch starting with this duplicate value has already been completed.
            if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) {
                continue;
            }

            // Make choice
            used[i] = true;
            current.add(nums[i]);

            // Recurse
            backtrack(nums, used, current, result);

            // Undo choice (backtrack)
            current.remove(current.size() - 1);
            used[i] = false;
        }
    }
}
