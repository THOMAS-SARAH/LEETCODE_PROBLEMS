import java.util.*;

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;
        int maxFactor = 0;
        long minPrice = Long.MAX_VALUE;

        for (int[] item : items) {
            if (item[0] > maxFactor) {
                maxFactor = item[0];
            }
            if (item[1] < minPrice) {
                minPrice = item[1];
            }
        }

        // Count how many items exist with each factor value
        long[] factorCount = new long[maxFactor + 1];
        for (int[] item : items) {
            factorCount[item[0]]++;
        }

        // For each factor f, count total items whose factor is a multiple of f
        long[] multiplesCount = new long[maxFactor + 1];
        for (int f = 1; f <= maxFactor; f++) {
            if (factorCount[f] == 0) continue;
            for (int m = f; m <= maxFactor; m += f) {
                multiplesCount[f] += factorCount[m];
            }
        }

        // Collect (price, boostedQuantity) pairs where buying a copy yields 2 items
        // (1 purchased + 1 free) and is at least as cost-effective as buying the cheapest item
        List<long[]> boosted = new ArrayList<>();
        for (int[] item : items) {
            int f = item[0];
            long price = item[1];
            long gain = multiplesCount[f] - 1; // exclude item itself
            if (gain > 0 && price <= 2L * minPrice) {
                boosted.add(new long[]{price, gain});
            }
        }

        // Buy cheaper boosted copies first
        boosted.sort(Comparator.comparingLong(a -> a[0]));

        long remBudget = budget;
        long totalItems = 0;

        for (long[] pair : boosted) {
            long price = pair[0];
            long qty = pair[1];
            if (remBudget < price) break;

            long buy = Math.min(qty, remBudget / price);
            totalItems += buy * 2L;
            remBudget -= buy * price;
        }

        // Spend any leftover budget on the cheapest item (1 copy each)
        totalItems += remBudget / minPrice;

        return (int) totalItems;
    }
}