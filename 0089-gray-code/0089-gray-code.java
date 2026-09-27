import java.util.ArrayList;
import java.util.List;

public class Solution {
    public List<Integer> grayCode(int n) {
        List<Integer> result = new ArrayList<>();
        int totalNumbers = 1 << n; // 2^n total numbers
        
        for (int i = 0; i < totalNumbers; i++) {
            // Gray code formula: i ^ (i / 2)
            result.add(i ^ (i >> 1));
        }
        
        return result;
    }
}

