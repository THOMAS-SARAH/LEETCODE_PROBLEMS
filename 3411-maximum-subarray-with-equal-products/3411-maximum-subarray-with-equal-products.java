class Solution {
    public int maxLength(int[] nums) {
        int n = nums.length;
        int maxLen = 1;

        // Maximum possible LCM of any subset of positive integers <= 10 is 2520
        final int MAX_POSSIBLE_LCM = 2520;

        for (int i = 0; i < n; i++) {
            long prod = 1;
            int curGcd = 0;
            int curLcm = 1;

            for (int j = i; j < n; j++) {
                int val = nums[j];
                curGcd = (j == i) ? val : gcd(curGcd, val);
                curLcm = lcm(curLcm, val);

                // Safe overflow-aware product check:
                // prod * val cannot exceed MAX_POSSIBLE_LCM * curGcd if valid
                long bound = (long) MAX_POSSIBLE_LCM * curGcd;
                if (prod > bound / val) {
                    break;
                }
                prod *= val;

                if (prod == (long) curLcm * curGcd) {
                    maxLen = Math.max(maxLen, j - i + 1);
                }
            }
        }

        return maxLen;
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = a % b;
            a = b;
            b = temp;
        }
        return a;
    }

    private int lcm(int a, int b) {
        return (a / gcd(a, b)) * b;
    }
}