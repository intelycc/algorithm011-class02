package com.company.others.numIslands;

//给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
//
// 岛屿总是被水包围，并且每座岛屿只能由水平方向或竖直方向上相邻的陆地连接形成。
//
// 此外，你可以假设该网格的四条边均被水包围。
//
//
//
// 示例 1:
//
// 输入:
//[
//['1','1','1','1','0'],
//['1','1','0','1','0'],
//['1','1','0','0','0'],
//['0','0','0','0','0']
//]
//输出: 1
//
//
// 示例 2:
//
// 输入:
//[
//['1','1','0','0','0'],
//['1','1','0','0','0'],
//['0','0','1','0','0'],
//['0','0','0','1','1']
//]
//输出: 3
//解释: 每座岛屿只能由水平和/或竖直方向上相邻的陆地连接而成。
//
// Related Topics 深度优先搜索 广度优先搜索 并查集
// 👍 658 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution2 {
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        int m = grid.length;
        int n = grid[0].length;
        UnionFind uf = new UnionFind(m * n + 1); //多设置一个虚拟节点，所有水域都和他合并
        int[] rowD = {-1, 0, 1, 0};
        int[] colD = {0, 1, 0, -1};
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                    //如果当前是陆地，遍历四个方向，进行合并，此处可以优化为只向下和左遍历
                    for (int k = 0; k < 4; k++) {
                        int newRow = i + rowD[k];
                        int newCol = j + colD[k];
                        if (newRow < 0 || newRow >= m
                                || newCol < 0 || newCol >= n
                                || grid[newRow][newCol] != '1') {
                            continue;
                        }
                        uf.union(i * n + j, newRow * n + newCol);
                    }
                } else {
                    //如果当前是水域，则和虚拟节点合并
                    uf.union(i * n + j, m * n);
                }
            }
        }
        return uf.getCount() - 1;
    }
    //并查集
    class UnionFind {
        private int[] parent;
        private int count;
        public UnionFind(int n) {
            parent = new int[n];
            count = n;
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
            return;
        }

        public void union(int p, int q) {
            int pRoot = find(p);
            int qRoot = find(q);
            if (pRoot == qRoot) return;
            parent[pRoot] = qRoot;
            count--;
            return;
        }

        public int find(int i) {
            if (parent[i] == i) {
                return parent[i];
            }
            parent[i] = find(parent[i]);
            return parent[i];
        }

        public int getCount() {
            return count;
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)

