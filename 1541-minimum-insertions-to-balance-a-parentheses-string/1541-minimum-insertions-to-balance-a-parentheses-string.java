class Solution {
    public int minInsertions(String s) {
        int insertions = 0;
        int openCount = 0; // Tracks needed '('

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == '(') {
                openCount++;
            } else {
                // Check if the current ')' is followed by another ')'
                if (i + 1 < s.length() && s.charAt(i + 1) == ')') {
                    i++; // Consume the consecutive second ')'
                } else {
                    insertions++; // Missing one ')' to make '))', so insert one
                }

                if (openCount > 0) {
                    openCount--; // Match with an existing '('
                } else {
                    insertions++; // Missing '(', so insert one
                }
            }
        }

        // Each remaining unmatched '(' needs two ')'
        insertions += openCount * 2;

        return insertions;
    }
}