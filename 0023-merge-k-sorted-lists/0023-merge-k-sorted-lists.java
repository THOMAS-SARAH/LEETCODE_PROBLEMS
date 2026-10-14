/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        
        // Edge cases
        if (lists == null || lists.length == 0) return null;
        
        // Initialize a Min-Heap based on node values
        PriorityQueue<ListNode> minHeap = new PriorityQueue<>((a, b) -> Integer.compare(a.val, b.val));
        
        // Add the head node of every non-empty list into the heap
        for (ListNode root : lists) {
            if (root != null) {
                minHeap.add(root);
            }
        }
        
        // Dummy node to act as the head anchor for the merged list
        ListNode dummy = new ListNode(-1);
        ListNode current = dummy;
        
        // Extract the minimum element and advance its pointer
        while (!minHeap.isEmpty()) {
            ListNode smallestNode = minHeap.poll();
            current.next = smallestNode;
            current = current.next;
            
            // If the extracted node has a next node, add it to the heap
            if (smallestNode.next != null) {
                minHeap.add(smallestNode.next);
            }
        }
        
        return dummy.next;
        
    }
}