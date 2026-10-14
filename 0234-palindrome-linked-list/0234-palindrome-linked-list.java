class Solution {
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) return true;
        
        Stack<Integer> stack = new Stack<>();
        ListNode current = head;
        
        // Push all elements into the stack
        while (current != null) {
            stack.push(current.val);
            current = current.next;
        }
        
        // Compare elements from the list with stack pops
        current = head;
        while (current != null) {
            if (current.val != stack.pop()) {
                return false;
            }
            current = current.next;
        }
        
        return true;
    }
}