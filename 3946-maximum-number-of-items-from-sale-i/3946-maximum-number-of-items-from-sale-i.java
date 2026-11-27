class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int maxFactor = 0;
        for (int[] item : items) {
            maxFactor = Math.max(maxFactor, item[0]);
        }

        // Count frequency of each factor
        int[] freq = new int[maxFactor + 1];
        for (int[] item : items) {
            freq[item[0]]++;
        }

        // divisibleCount[f] = total items whose factor is a multiple of f
        int[] divisibleCount = new int[maxFactor + 1];
        for (int f = 1; f <= maxFactor; f++) {
            for (int multiple = f; multiple <= maxFactor; multiple += f) {
                divisibleCount[f] += freq[multiple];
            }
        }

        // dp[b] = max total item copies obtainable with budget b
        int[] dp = new int[budget + 1];

        for (int[] item : items) {
            int factor = item[0];
            int price = item[1];
            if (price > budget) continue;

            // First copy gives 1 purchased item + all other items divisible by factor
            int firstCopyGain = divisibleCount[factor];

            // 1. 0/1 Knapsack pass for the 1st copy (backward loop)
            for (int b = budget; b >= price; b--) {
                dp[b] = Math.max(dp[b], dp[b - price] + firstCopyGain);
            }

            // 2. Unbounded Knapsack pass for extra copies (forward loop)
            for (int b = price; b <= budget; b++) {
                dp[b] = Math.max(dp[b], dp[b - price] + 1);
            }
        }

        int maxItems = 0;
        for (int val : dp) {
            maxItems = Math.max(maxItems, val);
        }
        return maxItems;
    }
}