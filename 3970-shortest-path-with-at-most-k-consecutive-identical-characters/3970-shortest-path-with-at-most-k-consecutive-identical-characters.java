import java.util.*;

class Solution {
    public int shortestPath(int n, int[][] edges, String labels, int k) {
        // Build adjacency list: u -> list of [v, w]
        List<int[]>[] adj = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            adj[edge[0]].add(new int[] { edge[1], edge[2] });
        }

        // Min-priority queue storing states: [cost, u, count]
        // Ordered by cost ascending
        PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a[0]));

        // Distances array to track minimum cost to reach (node, count)
        // count ranges from 1 to k, so size is k + 1
        long[][] dist = new long[n][k + 1];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], Long.MAX_VALUE);
        }

        // Starting at node 0 with count = 1 (since labels[0] appears once)
        dist[0][1] = 0;
        pq.offer(new long[] { 0, 0, 1 });

        while (!pq.isEmpty()) {
            long[] curr = pq.poll();
            long cost = curr[0];
            int u = (int) curr[1];
            int count = (int) curr[2];

            // If we reached destination node n - 1, return the cost
            if (u == n - 1) {
                return (int) cost;
            }

            if (cost > dist[u][count]) {
                continue;
            }

            for (int[] neighbor : adj[u]) {
                int v = neighbor[0];
                int w = neighbor[1];

                int nextCount = 1;
                if (labels.charAt(u) == labels.charAt(v)) {
                    nextCount = count + 1;
                }

                // Skip if consecutive identical characters exceed k
                if (nextCount > k) {
                    continue;
                }

                if (cost + w < dist[v][nextCount]) {
                    dist[v][nextCount] = cost + w;
                    pq.offer(new long[] { cost + w, v, nextCount });
                }
            }
        }

        // Find the minimum distance to reach node n - 1 with any valid count
        long minCost = Long.MAX_VALUE;
        for (int count = 1; count <= k; count++) {
            minCost = Math.min(minCost, dist[n - 1][count]);
        }

        return minCost == Long.MAX_VALUE ? -1 : (int) minCost;
    }
}