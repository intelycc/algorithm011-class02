package com.company.stack.longestValidParentheses;

//给定一个只包含 '(' 和 ')' 的字符串，找出最长的包含有效括号的子串的长度。
//
// 示例 1:
//
// 输入: "(()"
//输出: 2
//解释: 最长有效括号子串为 "()"
//
//
// 示例 2:
//
// 输入: ")()())"
//输出: 4
//解释: 最长有效括号子串为 "()()"
//
// Related Topics 字符串 动态规划
// 👍 812 👎 0


import java.util.ArrayList;
import java.util.Stack;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution2 {
    public int longestValidParentheses(String s) {
        if (s == null || s.length() == 0) return 0;
        int res = 0;
        int[] dp = new int[s.length()];
        //basecase是dp[0] = 0
        for (int i = 1; i < s.length(); i++) {
            //左括号结尾，一定不是有效括号串。所以只用判断右括号结尾的情况
            if (s.charAt(i) == ')'){
                //前一个位置是'('那么和当前位置组成了一组独立括号对，只要再加上dp[i-2]即可
                if (s.charAt(i - 1) == '(') {
                    dp[i] = i - 2 >= 0 ? dp[i - 2] + 2 : 2;
                } else if (s.charAt(i - 1) == ')' && i - dp[i - 1] - 1 >= 0 && s.charAt(i - dp[i - 1] - 1) == '(') {
                    //前一个位置是')'，那么需要跳过dp[i-1]个位置后的下个位置跟位置i的')'组成一对，否则找不到以i结尾的合法子串
                    //不要漏了dp[i - dp[i - 1] - 2],比如)()(()))
                    dp[i] = dp[i - 1] + 2 + (i - dp[i - 1] - 2 >= 0 ? dp[i - dp[i - 1] - 2] : 0);
                }
            }
            res = Math.max(dp[i], res);
        }
        return res;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
