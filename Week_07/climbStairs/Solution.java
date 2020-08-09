package com.company.others.climbStairs;

//假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
//
// 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
//
// 注意：给定 n 是一个正整数。
//
// 示例 1：
//
// 输入： 2
//输出： 2
//解释： 有两种方法可以爬到楼顶。
//1.  1 阶 + 1 阶
//2.  2 阶
//
// 示例 2：
//
// 输入： 3
//输出： 3
//解释： 有三种方法可以爬到楼顶。
//1.  1 阶 + 1 阶 + 1 阶
//2.  1 阶 + 2 阶
//3.  2 阶 + 1 阶
//
// Related Topics 动态规划


//leetcode submit region begin(Prohibit modification and deletion)
/*递归解法
*
* */
class Solution {
    public int climbStairs(int n) {
        /*缓存，避免重复计算*/
        int[] cache = new int[n + 1];
        return doClimbStairs(n, cache);
    }

    private int doClimbStairs(int n, int[] cache) {
        /*已缓存则直接返回*/
        if (cache[n] > 0) return cache[n];
        if (n == 1) {
            cache[n] = 1;
        }else if (n == 2) {
            cache[n] = 2;
        } else {
            cache[n] = doClimbStairs(n - 1, cache) + doClimbStairs(n - 2, cache);
        }
        return cache[n];
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.climbStairs(3));
    }
}
//leetcode submit region end(Prohibit modification and deletion)