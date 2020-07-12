package com.company.others.permute;

//给定一个 没有重复 数字的序列，返回其所有可能的全排列。
//
// 示例:
//
// 输入: [1,2,3]
//输出:
//[
//  [1,2,3],
//  [1,3,2],
//  [2,1,3],
//  [2,3,1],
//  [3,1,2],
//  [3,2,1]
//]
// Related Topics 回溯算法
// 👍 783 👎 0


import java.util.ArrayList;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    List<List<Integer>> ans = new ArrayList<>();
    public List<List<Integer>> permute(int[] nums) {
        doPermute(new ArrayList<>(), nums);
        return ans;
    }

    private void doPermute(List<Integer> res, int[] nums) {
        if (res.size() == nums.length) {
            ans.add(new ArrayList<>(res));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (res.contains(nums[i])) continue;
            res.add(nums[i]);
            doPermute(res, nums);
            res.remove(res.size() - 1);
        }
        return;
    }

}
//leetcode submit region end(Prohibit modification and deletion)

