package com.company.tree.buildTreePre;

//根据一棵树的前序遍历与中序遍历构造二叉树。
//
// 注意:
//你可以假设树中没有重复的元素。
//
// 例如，给出
//
// 前序遍历 preorder = [3,9,20,15,7]
//中序遍历 inorder = [9,3,15,20,7]
//
// 返回如下的二叉树：
//
//     3
//   / \
//  9  20
//    /  \
//   15   7
// Related Topics 树 深度优先搜索 数组
// 👍 557 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

import java.util.HashMap;
import java.util.Map;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    int preIdx = 0;
    Map<Integer, Integer> m = new HashMap<>();
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder == null || inorder == null) return null;
        for (int i = 0; i < inorder.length; i++) {
            m.put(inorder[i], i);
        }
        return doBuildTree(preorder, inorder, 0, inorder.length - 1);
    }

    private TreeNode doBuildTree(int[] pre, int[] in, int begin, int end) {
        if (begin > end) return null;
        int val = pre[preIdx++];
        TreeNode root = new TreeNode(val);
        int index = m.get(val);
        root.left = doBuildTree(pre, in, begin, index - 1);
        root.right = doBuildTree(pre, in, index + 1, end);
        return root;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
