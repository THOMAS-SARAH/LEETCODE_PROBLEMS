import java.util.Arrays;

class Solution {
    
    // Helper function to calculate the sum of digits of a number
    private int getDigitSum(int n) {
        int sum = 0;
        while (n > 0) {
            sum += n % 10;
            n /= 10;
        }
        return sum;
    }

    public int minSwaps(int[] nums) {
        int n = nums.length;
        
        // Store pairs of [digit_sum, value, original_index]
        Integer[][] arr = new Integer[n][3];
        for (int i = 0; i < n; i++) {
            arr[i][0] = getDigitSum(nums[i]);
            arr[i][1] = nums[i];
            arr[i][2] = i;
        }
        
        // Sort based on digit sum, then by value
        Arrays.sort(arr, (a, b) -> {
            if (!a[0].equals(b[0])) {
                return Integer.compare(a[0], b[0]);
            }
            return Integer.compare(a[1], b[1]);
        });
        
        // Find the number of cycles to compute minimum swaps
        boolean[] visited = new boolean[n];
        int cycles = 0;
        
        for (int i = 0; i < n; i++) {
            // If already visited or element is already in its correct sorted position
            if (visited[i] || arr[i][2] == i) {
                continue;
            }
            
            // Traverse the cycle
            int curr = i;
            int cycleSize = 0;
            while (!visited[curr]) {
                visited[curr] = true;
                curr = arr[curr][2];
                cycleSize++;
            }
            
            if (cycleSize > 1) {
                cycles += (cycleSize - 1);
            }
        }
        
        return cycles;
    }
}