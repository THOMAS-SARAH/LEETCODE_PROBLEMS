class Solution {
    public String maxSumOfSquares(int num, int sum) {
        // A greedy approach: To maximize the sum of squares of digits and 
        // simultaneously maximize the number itself, we should place the largest
        // possible digits (9s) from left to right.
        if (sum < 0 || (long) num * 9 < sum) {
            return "";
        }
        
        StringBuilder sb = new StringBuilder(num);
        for (int i = 0; i < num; i++) {
            int digit = Math.min(9, sum);
            sb.append(digit);
            sum -= digit;
        }
        
        // If there is still sum left over, it's impossible to form a valid number of length `num`.
        if (sum > 0) {
            return "";
        }
        
        return sb.toString();
    }
}