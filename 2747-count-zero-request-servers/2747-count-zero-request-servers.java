import java.util.Arrays;

class Solution {
    public int[] countServers(int n, int[][] logs, int x, int[] queries) {
        int m = queries.length;
        
        // Pair each query with its original index: {query_time, original_index}
        int[][] sortedQueries = new int[m][2];
        for (int i = 0; i < m; i++) {
            sortedQueries[i][0] = queries[i];
            sortedQueries[i][1] = i;
        }
        
        // Sort queries by their time values in ascending order
        Arrays.sort(sortedQueries, (a, b) -> Integer.compare(a[0], b[0]));
        
        // Sort logs by time in ascending order
        Arrays.sort(logs, (a, b) -> Integer.compare(a[1], b[1]));
        
        int[] ans = new int[m];
        int left = 0, right = 0;
        int activeServersCount = 0;
        int[] serverFrequency = new int[n + 1];
        
        // Process each query using the sliding window
        for (int i = 0; i < m; i++) {
            int qTime = sortedQueries[i][0];
            int originalIdx = sortedQueries[i][1];
            
            int windowStart = qTime - x;
            int windowEnd = qTime;
            
            // Expand window right boundary to include logs <= windowEnd
            while (right < logs.length && logs[right][1] <= windowEnd) {
                int serverId = logs[right][0];
                if (serverFrequency[serverId] == 0) {
                    activeServersCount++;
                }
                serverFrequency[serverId]++;
                right++;
            }
            
            // Shrink window left boundary to exclude logs < windowStart
            while (left < logs.length && logs[left][1] < windowStart) {
                int serverId = logs[left][0];
                serverFrequency[serverId]--;
                if (serverFrequency[serverId] == 0) {
                    activeServersCount--;
                }
                left++;
            }
            
            // Zero-request servers = Total servers (n) - Active servers in window
            ans[originalIdx] = n - activeServersCount;
        }
        
        return ans;
    }
}