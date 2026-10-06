class Solution {
    public int numTrees(int n) {
        // dp[i] will store the number of unique BSTs that can be formed with i nodes
        int[] dp = new int[n + 1];
        
        // Base cases:
        // An empty tree (0 nodes) has 1 unique structure.
        // A tree with 1 node has 1 unique structure.
        dp[0] = 1;
        dp[1] = 1;
        
        // Fill the dp array iteratively for total nodes from 2 up to n
        for (int i = 2; i <= n; i++) {
            // For a tree of size i, consider each j-th node as the root
            // The left subtree will have (j) nodes
            // The right subtree will have (i - j - 1) nodes
            for (int j = 0; j < i; j++) {
                dp[i] += dp[j] * dp[i - j - 1];
            }
        }
        
        return dp[n];
    }
}
