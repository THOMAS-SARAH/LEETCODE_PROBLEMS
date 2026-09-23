import java.util.ArrayList;
import java.util.List;

public class Solution {
    public List<String> restoreIpAddresses(String s) {
        List<String> result = new ArrayList<>();
        // An IP address has 4 segments, each up to 3 digits. Max length = 12.
        // Min length required for 4 segments is 4 digits.
        if (s == null || s.length() < 4 || s.length() > 12) {
            return result;
        }
        
        backtrack(s, 0, new ArrayList<>(), result);
        return result;
    }

    private void backtrack(String s, int startIndex, List<String> currentSegments, List<String> result) {
        // Base case: If we have found exactly 4 segments
        if (currentSegments.size() == 4) {
            // If we have also consumed the entire string, it's a valid IP
            if (startIndex == s.length()) {
                result.add(String.join(".", currentSegments));
            }
            return;
        }

        // Optimization: Early pruning if remaining characters can't fill or exceed the remaining slots
        int remainingSegments = 4 - currentSegments.size();
        int remainingChars = s.length() - startIndex;
        if (remainingChars < remainingSegments || remainingChars > remainingSegments * 3) {
            return;
        }

        // Try extracting segments of length 1, 2, and 3
        for (int len = 1; len <= 3; len++) {
            // Ensure we don't go out of bounds
            if (startIndex + len > s.length()) {
                break;
            }

            String segment = s.substring(startIndex, startIndex + len);

            // Check for valid segment constraints:
            // 1. No leading zeros (e.g., "01" or "00" are invalid, but "0" is valid)
            // 2. Value must be <= 255
            if ((segment.length() > 1 && segment.charAt(0) == '0') || 
                (segment.length() == 3 && Integer.parseInt(segment) > 255)) {
                continue;
            }

            // Choose
            currentSegments.add(segment);
            
            // Explore next segments
            backtrack(s, startIndex + len, currentSegments, result);
            
            // Unchoose (Backtrack)
            currentSegments.remove(currentSegments.size() - 1);
        }
    }
}
