package com.company.tree.preorderTraversal;

//给定一个二叉树，返回它的 前序 遍历。
//
// 示例:
//
// 输入: [1,null,2,3]
//   1
//    \
//     2
//    /
//   3
//
//输出: [1,2,3]
//
//
// 进阶: 递归算法很简单，你可以通过迭代算法完成吗？
// Related Topics 栈 树
// 👍 296 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution2 {
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        doPreorderTraversal(root, res);
        return res;
    }
    private void doPreorderTraversal(TreeNode root, List<Integer> res) {
        if (root == null) return;
        res.add(root.val);
        doPreorderTraversal(root.left, res);
        doPreorderTraversal(root.right, res);
        return;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
