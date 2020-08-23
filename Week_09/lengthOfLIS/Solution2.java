package com.company.dp.lengthOfLIS;

//给定一个无序的整数数组，找到其中最长上升子序列的长度。
//
// 示例:
//
// 输入: [10,9,2,5,3,7,101,18]
//输出: 4
//解释: 最长的上升子序列是 [2,3,7,101]，它的长度是 4。
//
// 说明:
//
//
// 可能会有多种最长上升子序列的组合，你只需要输出对应的长度即可。
// 你算法的时间复杂度应该为 O(n2) 。
//
//
// 进阶: 你能将算法的时间复杂度降低到 O(n log n) 吗?
// Related Topics 二分查找 动态规划
// 👍 919 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution2 {
    public int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int res = 0;
        //dp[i]表示长度为i+1的子串，以dp[i]元素结尾。dp数组是递增的
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            //如果比最后一个大，那么其实就是比dp前面的元素都大，那么将其放在尾部
            if (nums[i] > dp[res]) {
                dp[++res] = nums[i];
            } else {
                //用二分法覆盖掉比他大的那个元素中最小的那个
                int left = 0, right = res, mid = left + (right - left) / 2;
                while (left < right) {
                    if (dp[mid] < nums[i]) {
                        left = left + 1;
                    } else {
                        right = right - 1;
                    }
                }
                dp[left] = nums[i];
            }
        }
        return res + 1;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
