class Solution {
    class SegmentTree {
        int n;
        long[] sum;
        long[] lazy;

        public SegmentTree(int n) {
            this.n = n;
            sum = new long[4 * n];
            lazy = new long[4 * n];
        }

        private void push(int node, int l, int r) {
            if (lazy[node] != 0) {
                int mid = (l + r) / 2;
                apply(2 * node, l, mid, lazy[node]);
                apply(2 * node + 1, mid + 1, r, lazy[node]);
                lazy[node] = 0;
            }
        }

        private void apply(int node, int l, int r, long val) {
            sum[node] = (sum[node] + val * (r - l + 1)) % MOD;
            lazy[node] = (lazy[node] + val) % MOD;
        }

        public void update(int node, int l, int r, int ql, int qr, long val) {
            if (ql > r || qr < l) return;
            if (ql <= l && r <= qr) {
                apply(node, l, r, val);
                return;
            }
            push(node, l, r);
            int mid = (l + r) / 2;
            update(2 * node, l, mid, ql, qr, val);
            update(2 * node + 1, mid + 1, r, ql, qr, val);
            sum[node] = (sum[2 * node] + sum[2 * node + 1]) % MOD;
        }

        public long query(int node, int l, int r, int ql, int qr) {
            if (ql > r || qr < l) return 0;
            if (ql <= l && r <= qr) return sum[node];
            push(node, l, r);
            int mid = (l + r) / 2;
            return (query(2 * node, l, mid, ql, qr) + query(2 * node + 1, mid + 1, r, ql, qr)) % MOD;
        }
    }

    private static final int MOD = 1_000_000_007;

    public int sumCounts(int[] nums) {
        int n = nums.length;
        SegmentTree tree = new SegmentTree(n);
        
        // Map to store the last seen index of each element
        java.util.Map<Integer, Integer> lastSeen = new java.util.HashMap<>();
        
        long totalSum = 0;
        long currentSubarraysSum = 0; // Sum of distinct counts for all subarrays ending at current index i

        for (int i = 0; i < n; i++) {
            int x = nums[i];
            int prevIdx = lastSeen.getOrDefault(x, -1);
            
            // When element nums[i] appears at index i, its distinct count contribution 
            // is increased by 1 for all starting indices from (prevIdx + 1) to i.
            // Since (count + 1)^2 = count^2 + 2*count + 1, we update the segment tree range [prevIdx + 1, i] by adding: 
            // 2 * (distinct_count_of_subarray) + 1
            
            long rangeSum = tree.query(1, 0, n - 1, prevIdx + 1, i);
            long addedValue = (2 * rangeSum + (i - prevIdx)) % MOD;
            
            tree.update(1, 0, n - 1, prevIdx + 1, i, 1);
            
            currentSubarraysSum = (currentSubarraysSum + addedValue) % MOD;
            totalSum = (totalSum + currentSubarraysSum) % MOD;
            
            lastSeen.put(x, i);
        }

        return (int) totalSum;
    }
}