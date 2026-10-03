class Solution {
    public boolean isSymmetric(TreeNode root) {
        if (root == null) return true;
        return isMirror(root.left, root.right);
    }

    private boolean isMirror(TreeNode t1, TreeNode t2) {
        // If both subtrees are empty, they are mirrors
        if (t1 == null && t2 == null) return true;
        
        // If only one subtree is empty, they are not mirrors
        if (t1 == null || t2 == null) return false;
        
        // The values must match, and their cross-children must be mirrors
        return (t1.val == t2.val)
            && isMirror(t1.left, t2.right)
            && isMirror(t1.right, t2.left);
    }
}
