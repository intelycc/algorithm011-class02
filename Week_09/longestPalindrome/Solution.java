package com.company.dp.longestPalindrome;

//给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
//
// 示例 1：
//
// 输入: "babad"
//输出: "bab"
//注意: "aba" 也是一个有效答案。
//
//
// 示例 2：
//
// 输入: "cbbd"
//输出: "bb"
//
// Related Topics 字符串 动态规划
// 👍 2588 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public String longestPalindrome(String s) {
        if (s == null || s.length() == 0) return s;
        int n = s.length();
        int maxBegin = 0, maxLen = 1;
        int[][] dp = new int[n][n];
        //初始化basecase
        for (int i = 0; i < n; i++) dp[i][i] = 1;
        //从下往上，从左往右递推
        for (int i = n - 2; i >= 0; i--) {
            for (int j = i + 1; j < s.length(); j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    //边界情况情况，当只有两个字符时
                    if (j - i <= 1) {
                        dp[i][j] = 2;
                    }else if (dp[i + 1][j - 1] != 0) {
                        dp[i][j] = dp[i + 1][j - 1] + 2;
                    }
                    if (dp[i][j] > maxLen) {
                        maxLen = dp[i][j];
                        maxBegin = i;
                    }
                }
            }
        }
        return s.substring(maxBegin, maxBegin + maxLen);
    }
}
//leetcode submit region end(Prohibit modification and deletion)

