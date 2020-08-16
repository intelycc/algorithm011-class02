package com.company.backtrace.solveNQueens;

//n 皇后问题研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。
//
//
//
// 上图为 8 皇后问题的一种解法。
//
// 给定一个整数 n，返回所有不同的 n 皇后问题的解决方案。
//
// 每一种解法包含一个明确的 n 皇后问题的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。
//
// 示例:
//
// 输入: 4
//输出: [
// [".Q..",  // 解法 1
//  "...Q",
//  "Q...",
//  "..Q."],
//
// ["..Q.",  // 解法 2
//  "Q...",
//  "...Q",
//  ".Q.."]
//]
//解释: 4 皇后问题存在两个不同的解法。
//
//
//
//
// 提示：
//
//
// 皇后，是国际象棋中的棋子，意味着国王的妻子。皇后只做一件事，那就是“吃子”。当她遇见可以吃的棋子时，就迅速冲上去吃掉棋子。当然，她横、竖、斜都可走一到七步
//，可进可退。（引用自 百度百科 - 皇后 ）
//
// Related Topics 回溯算法
// 👍 491 👎 0


import com.sun.org.apache.xpath.internal.operations.String;

import java.util.ArrayList;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    List<List<java.lang.String>> res = new ArrayList<>();
    public List<List<java.lang.String>> solveNQueens(int n) {
        if (n <= 0) return res;
        int[][] board = new int[n][n];
        dfs(board, 0, n);
        return res;
    }
    private void dfs(int[][] board, int row, int n) {
        if (row == n) {
            res.add(transfer(board));
            return;
        }
        for (int col = 0; col < n; col++) {
            if (!isValid(board, row, col)) continue;
            board[row][col] = 1;
            dfs(board, row + 1, n);
            board[row][col] = 0;
        }
        return;
    }

    private boolean isValid(int[][] board, int row, int col) {
        for (int i = 0; i < row; i++) {
            if (board[i][col] == 1) return false;
        }

        for (int i = row, j = col; i >= 0 && j >= 0; i--,j--) {
            if (board[i][j] == 1) return false;
        }

        for (int i = row, j = col; i >= 0 && j < board[0].length; i--,j++) {
            if (board[i][j] == 1) return false;
        }
        return true;
    }

    private List<java.lang.String> transfer(int[][] board) {
        List<java.lang.String> l = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 0) {
                    sb.append(".");
                } else if (board[i][j] == 1) {
                    sb.append("Q");
                }
            }
            l.add(sb.toString());
        }
        return l;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

