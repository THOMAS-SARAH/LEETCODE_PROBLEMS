class Solution:
    def minimumChanges(self, s: str, k: int) -> int:
        n = len(s)
        
        # Precompute proper divisors for all lengths from 2 to n
        divisors = [[] for _ in range(n + 1)]
        for length in range(2, n + 1):
            for d in range(1, length // 2 + 1):
                if length % d == 0:
                    divisors[length].append(d)
                    
        # Helper function to find min changes to make s[i..j] a semi-palindrome
        def get_semi_palindrome_cost(i: int, j: int) -> int:
            length = j - i + 1
            min_changes = float('inf')
            
            for d in divisors[length]:
                changes = 0
                # Check each of the d groups
                for offset in range(d):
                    left = i + offset
                    # Last element in this group
                    right = j - (d - 1 - offset)
                    
                    while left < right:
                        if s[left] != s[right]:
                            changes += 1
                        left += d
                        right -= d
                min_changes = min(min_changes, changes)
                
            return min_changes

        # Precompute cost[i][j] for all valid substrings s[i..j] (length >= 2)
        cost = [[float('inf')] * n for _ in range(n)]
        for i in range(n):
            for j in range(i + 1, n):
                cost[i][j] = get_semi_palindrome_cost(i, j)
                
        # dp[parts][end_idx] = min changes to partition s[0..end_idx] into 'parts' substrings
        dp = [[float('inf')] * n for _ in range(k + 1)]
        
        # Base case: 1 partition covering s[0..j]
        for j in range(1, n):
            dp[1][j] = cost[0][j]
            
        # Fill DP table for 2 to k partitions
        for parts in range(2, k + 1):
            # Minimum index needed for 'parts' substrings of length >= 2 is (2 * parts - 1)
            for j in range(2 * parts - 1, n):
                # 'm' is the split point; previous (parts - 1) end at 'm', current is s[m+1..j]
                for m in range(2 * (parts - 1) - 1, j - 1):
                    if dp[parts - 1][m] != float('inf'):
                        dp[parts][j] = min(dp[parts][j], dp[parts - 1][m] + cost[m + 1][j])
                        
        return dp[k][n - 1]