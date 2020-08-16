package com.company.bit.totalNQueens;

//n 皇后问题研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。
//
//
//
// 上图为 8 皇后问题的一种解法。
//
// 给定一个整数 n，返回 n 皇后不同的解决方案的数量。
//
// 示例:
//
// 输入: 4
//输出: 2
//解释: 4 皇后问题存在如下两个不同的解法。
//[
// [".Q..",  // 解法 1
//  "...Q",
//  "Q...",
//  "..Q."],
//
// ["..Q.",  // 解法 2
//  "Q...",
//  "...Q",
//  ".Q.."]
//]
//
//
//
//
// 提示：
//
//
// 皇后，是国际象棋中的棋子，意味着国王的妻子。皇后只做一件事，那就是“吃子”。当她遇见可以吃的棋子时，就迅速冲上去吃掉棋子。当然，她横、竖、斜都可走一或 N
//-1 步，可进可退。（引用自 百度百科 - 皇后 ）
//
// Related Topics 回溯算法
// 👍 140 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    private int res = 0;
    public int totalNQueens(int n) {
        if (n <= 0) return 0;
        if (n > 32) return -1;
        dfs(n, 0, 0, 0, 0);
        return res;
    }

    private void dfs(int n, int row, int col, int pie, int na) {
        //终结条件
        if (row >= n) {
            res++;
            return;
        }
        //col | pie | na 求出所有可用位；~取反后，1代表可用，0代表不可用，因为col/pie/na中的1代表被占用
        //因为int是32位，所以要把n位前的都置为0，(1<<n) - 1 是将低n位全为1，其他为0
        //最终available中为1的bit就是可用的位
        int available = (~(col | pie | na)) & ((1 << n) - 1);
        //做选择
        while (available != 0) {
            //位运算技巧：可以取出最右边的第一个1,即从右到左进行选择
            int p = available & - available;
            //col | p可以将选择位置的影响的列计入p，(pie | p) << 1 左移一位即形成了左下，右下同理可得
            dfs(n, row + 1, col | p, (pie | p) << 1, (na | p) >> 1);
            //位运算技巧：清除最后一个为1的位，即清除之前的选择
            available &= (available - 1);
        }
        return;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

