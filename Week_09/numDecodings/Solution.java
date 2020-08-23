package com.company.dp.numDecodings;

//一条包含字母 A-Z 的消息通过以下方式进行了编码：
//
// 'A' -> 1
//'B' -> 2
//...
//'Z' -> 26
//
//
// 给定一个只包含数字的非空字符串，请计算解码方法的总数。
//
// 示例 1:
//
// 输入: "12"
//输出: 2
//解释: 它可以解码为 "AB"（1 2）或者 "L"（12）。
//
//
// 示例 2:
//
// 输入: "226"
//输出: 3
//解释: 它可以解码为 "BZ" (2 26), "VF" (22 6), 或者 "BBF" (2 2 6) 。
//
// Related Topics 字符串 动态规划
// 👍 452 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int numDecodings(String s) {
        if (s == null || s.length() == 0 || s.charAt(0) - '0' == 0) return 0;
        int[] dp = new int[s.length() + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i < dp.length; i++) {
            int num = s.charAt(i - 1) - '0';
            int preNum = s.charAt(i - 2) - '0';
            if (num == 0) {
                if (preNum == 1 || preNum == 2) {
                    dp[i] = dp[i - 2];
                } else {
                    return 0;
                }
            } else {
                if (preNum == 1 || (preNum == 2 && num <= 6)) {
                    dp[i] = dp[i - 1] + dp[i - 2];
                } else {
                    dp[i] = dp[i - 1];
                }
            }
        }
        return dp[dp.length - 1];
    }
}
//leetcode submit region end(Prohibit modification and deletion)
