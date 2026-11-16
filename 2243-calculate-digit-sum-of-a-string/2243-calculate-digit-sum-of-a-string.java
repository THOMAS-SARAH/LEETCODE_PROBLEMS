class Solution {
    public String digitSum(String s, int k) {
        // Continue rounds as long as the string length is greater than k
        while (s.length() > k) {
            StringBuilder newString = new StringBuilder();
            
            // Step 1 & 2: Divide into consecutive groups of size k and calculate their digit sums
            for (int i = 0; i < s.length(); i += k) {
                int sum = 0;
                int end = Math.min(i + k, s.length());
                
                // Sum the digits in the current group
                for (int j = i; j < end; j++) {
                    sum += s.charAt(j) - '0';
                }
                
                // Append the sum string to our new generated string
                newString.append(sum);
            }
            
            // Step 3: Merge consecutive groups together for the next round
            s = newString.toString();
        }
        
        return s;
    }
}