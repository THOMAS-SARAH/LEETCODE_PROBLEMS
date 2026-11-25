import java.util.HashMap;
import java.util.Map;

class Solution {
    private static final long BASE = 100003L;
    private static final long MOD = 1_000_000_007L;

    public int smallestUniqueSubarray(int[] nums) {
        int n = nums.length;
        long[] pow = new long[n + 1];
        pow[0] = 1L;
        for (int i = 1; i <= n; i++) {
            pow[i] = (pow[i - 1] * BASE) % MOD;
        }

        int left = 1;
        int right = n;
        int ans = n;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (hasUniqueSubarrayOfLength(nums, mid, pow)) {
                ans = mid;
                right = mid - 1; // Try to find an even smaller unique length
            } else {
                left = mid + 1;  // Need a longer window to get uniqueness
            }
        }

        return ans;
    }

    private boolean hasUniqueSubarrayOfLength(int[] nums, int len, long[] pow) {
        int n = nums.length;
        long hash = 0L;

        // Initial window of size `len`
        for (int i = 0; i < len; i++) {
            hash = (hash * BASE + nums[i]) % MOD;
        }

        Map<Long, Integer> freq = new HashMap<>();
        freq.put(hash, 1);

        long highestPower = pow[len - 1];

        // Slide the window across `nums`
        for (int i = len; i < n; i++) {
            long outgoing = (nums[i - len] * highestPower) % MOD;
            hash = (hash - outgoing + MOD) % MOD;
            hash = (hash * BASE + nums[i]) % MOD;

            freq.put(hash, freq.getOrDefault(hash, 0) + 1);
        }

        for (int count : freq.values()) {
            if (count == 1) {
                return true;
            }
        }

        return false;
    }
}