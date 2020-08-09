package com.company.backtrace.generateParenthesis;

//数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
//
//
//
// 示例：
//
// 输入：n = 3
//输出：[
//       "((()))",
//       "(()())",
//       "(())()",
//       "()(())",
//       "()()()"
//     ]
//
// Related Topics 字符串 回溯算法
// 👍 1179 👎 0


import java.util.ArrayList;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    List<String> ans = new ArrayList<>();
    public List<String> generateParenthesis(int n) {
        dfs("", 0, 0, n);
        return ans;
    }
    private void dfs(String s, int leftCnt, int rightCnt, int pair) {
        if (leftCnt == rightCnt && leftCnt == pair) {
            ans.add(s);
        }
        if (leftCnt < pair) {
            dfs(s + "(", leftCnt + 1, rightCnt, pair);
        }
        if (rightCnt < leftCnt) {
            dfs(s + ")", leftCnt, rightCnt + 1, pair);
        }
        return;
    }


}
//leetcode submit region end(Prohibit modification and deletion)
