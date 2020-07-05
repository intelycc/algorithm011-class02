package com.company.tree.inorderTraversal;

//给定一个二叉树，返回它的中序 遍历。
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
//输出: [1,3,2]
//
// 进阶: 递归算法很简单，你可以通过迭代算法完成吗？
// Related Topics 栈 树 哈希表
// 👍 551 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

import java.util.ArrayList;
import java.util.List;

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
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        doInorderTraversal(root, res);
        return res;
    }

    private void doInorderTraversal(TreeNode root, List<Integer> res) {
        if (root == null) return;
        doInorderTraversal(root.left, res);
        res.add(root.val);
        doInorderTraversal(root.right, res);
        return;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
