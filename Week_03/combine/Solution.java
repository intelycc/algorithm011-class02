package com.company.others.combine;

//给定两个整数 n 和 k，返回 1 ... n 中所有可能的 k 个数的组合。
//
// 示例:
//
// 输入: n = 4, k = 2
//输出:
//[
//  [2,4],
//  [3,4],
//  [2,3],
//  [1,2],
//  [1,3],
//  [1,4],
//]
// Related Topics 回溯算法
// 👍 309 👎 0


import java.util.ArrayList;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    List<List<Integer>> ans = new ArrayList<>();
    public List<List<Integer>> combine(int n, int k) {
        doCombine(n, k, new ArrayList<>());
        return ans;
    }

    private void doCombine(int n, int k, List<Integer> res) {
        /*达到k个，可以加入结果集*/
        if (res.size() == k) {
            ans.add(new ArrayList<>(res));
            return;
        }
        if (n <= 0) return;
        /*不选当前节点*/
        doCombine(n - 1, k, res);
        /*选择当前节点*/
        res.add(n);
        doCombine(n - 1, k, res);
        /*清理本层数据*/
        res.remove(res.size() - 1);
    }
}
//leetcode submit region end(Prohibit modification and deletion)

