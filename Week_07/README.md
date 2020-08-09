### 前缀（Trie）树
#### 概念
有多个名字，字典树、Trie树、前缀树
多叉树，专门用于处理**字符串匹配**的数据结构，用于解决在一组字符集合中快速查找某个字符串的问题
典型应用：搜索引擎用于文本词频统计
#### 基本性质
1. 根节点不包括字符，除根节点外每个节点都只包含一个字符
2. 从根节点到某一节点，路径经过的字符连起来就是该节点的字符串
3. 每个节点的所有子节点包含的字符都不相同
4. 是个多叉树
#### 效率
构建Trie树：时间复杂度为O(n)，n为字符串数量
Trie树中查找一个字符串：时间复杂度为O(k)，k是字符串长度
#### 实现
```java
class Trie {
    private boolean isEnd;
    private Trie[] next;
    /** Initialize your data structure here. */
    public Trie() {
        isEnd = false;
        next = new Trie[26];
    }
    
    /** Inserts a word into the trie. */
    public void insert(String word) {
        if (word == null || word.length() == 0) return;
        Trie curr = this;
        char[] words = word.toCharArray();
        for (int i = 0;i < words.length;i++) {
            int n = words[i] - 'a';
            if (curr.next[n] == null) curr.next[n] = new Trie();
            curr = curr.next[n];
        }
        curr.isEnd = true;
    }
    
    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        Trie node = searchPrefix(word);
        return node != null && node.isEnd;
    }
    
    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        Trie node = searchPrefix(prefix);
        return node != null;
    }

    private Trie searchPrefix(String word) {
        Trie node = this;
        char[] words = word.toCharArray();
        for (int i = 0;i < words.length;i++) {
            node = node.next[words[i] - 'a'];
            if (node == null) return null;
        }
        return node;
    }
}

```
#### 例题
##### 单词搜索II
###### 分析
1.此题首先想到的就是从每个节点开始采用dfs进行回溯，将每次回溯的单词去集合中查询，集合可以转化为Map，加速查询
2.但是此题如果集合用Trie树来处理的话，当遇到前缀已经不在Trie树的时候就可以剪枝，停止回溯，提高效率
###### 实现
```java
class Solution {
    public List<String> findWords(char[][] board, String[] words) {
        List<String> res = new ArrayList<>();
        //入参检查
        if (board == null || board.length == 0 || board[0].length == 0
        || words == null || words.length == 0) {
            return res;
        }
        //用words构建Trie树
        TrieNode root = new TrieNode();
        for (String word : words) {
            TrieNode node = root;
            for (int i = 0; i < word.length(); i++) {
                char ch = word.charAt(i);
                if (!node.contains(ch)) {
                    node.put(ch, new TrieNode());
                }
                node = node.get(ch);
            }
            node.setLetter(word);
        }
        //遍历二维网格，并对每个节点进行回溯
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                //剪枝
                if (!root.contains(board[i][j])) continue;
                backtrace(root, board, i, j, res);
            }
        }
        return res;
    }
    private void backtrace(TrieNode root, char[][] board, int i, int j, List<String> l) {
        char ch = board[i][j];
        TrieNode node = root.get(ch);
        //终止条件，即单词存在于树中
        if (node.getLetter() != null) {
            l.add(node.getLetter());
            node.setLetter(null);
        }
        board[i][j] = '#';//放置重复遍历
        int[] rowOffset = {-1, 0, 1, 0};
        int[] colOffset = {0, 1, 0, -1};
        //上下左右四个方向做选择
        for (int k = 0; k < 4; k++) {
            int newRow = i + rowOffset[k];
            int newCol = j + colOffset[k];
            if (newRow < 0 || newRow >= board.length || newCol < 0 || newCol >= board[0].length || board[newRow][newCol] == '#') {
                continue;
            }
            //剪枝
            if (node.contains(board[newRow][newCol])) {
                backtrace(node, board, newRow, newCol, l);
            }
        }
        board[i][j] = ch;//恢复现场
        return;
    }
}
//Trie树的单个节点
class TrieNode {
    private TrieNode[] links;
    private final int R = 26;
    private String letter;
    public TrieNode(){
        links = new TrieNode[R];
    }
    public TrieNode get(char c) {
        return links[c - 'a'];
    }
    public void put(char c, TrieNode root) {
        links[c - 'a'] = root;
    }
    public boolean contains(char c) {
        return links[c - 'a'] != null;
    }
    public void setLetter(String s) {
        letter = s;
    }
    public String getLetter() {
        return letter;
    }
}
```
###### 复杂度分析
时间复杂度：
  * 构建Trie树：O(j), j是words单词个数
  * 回溯需要： O（m * n * (4 * 3 ^ (L -1))）  m * n是网格数量，每个网格的回溯一开始都是4个方向，后续就是3个方向，总共深度不超过单词的最大长度L
  * 每次去Trie树中查找需要：O(L)，L是单词最大长度
  * 所以是：O（j + L * m * n * (4 * 3 ^ (L -1))），其中j个数有限，可以当常数处理，字母长度L也可以当常数处理，
  * 最终是：O（m * n * (4 * 3 ^ (L -1))）
  
空间复杂度:
 * O(n), n是words内的单词的字母总个数，需要一个Trie树来保存，每个不重复的字母都需要一个节点
### 并查集
#### 概念
并查集（Disjoint Sets）是一种**树形**的数据结构，用于处理一些不相交集合的合并及查询问题。
#### 代码
```java
class UnionFind { 
    private int count = 0; 
    private int[] parent; 
    public UnionFind(int n) { 
        count = n; 
        parent = new int[n]; 
        for (int i = 0; i < n; i++) { 
            parent[i] = i;
        }
    } 
    //查询root节点
    public int find(int p) { 
        while (p != parent[p]) { 
            parent[p] = parent[parent[p]]; //此种写法进行了路径压缩
            p = parent[p]; 
        }
        return p; 
    }
    //合并两个集合
    public void union(int p, int q) { 
        int rootP = find(p); 
        int rootQ = find(q); 
        if (rootP == rootQ) return; 
            parent[rootP] = rootQ; 
        count--;
    }
}
```
路径压缩还有另外的写法如：
```java
//递归写法
public int find(int i) {
    if (parent[i] == i) {
        return i;
    }
    parent[i] = find(parent[i]);
    return parent[i];
}
```
#### 复杂度分析
初始化： 需要将每个元素指向自己，O(n)
查找：O(1)，如果有路径压缩，基本上立刻能查找到 
合并： O(1)，同查找

#### 例题
##### 朋友圈
##### 分析
此题本质上是求有多少个连通分量，最容易想到的是可以用DFS进行。
不过结合并查集的特性，也可以使用并查集来确定有多少个集合。
默认开始所有人都各自是一个集合，通过遍历来合并集合，最终输出并查集的集合数量即获得答案
##### 代码
```java
class Solution {
    public int findCircleNum(int[][] M) {
        if (M == null || M.length == 0 || M[0].length == 0) {
            return 0;
        }
        //初始化一个并查集
        UnionFind uf = new UnionFind(M.length);
        for (int i = 0; i < M.length; i++) {
            //因为是对称的矩阵，此处做了优化，只遍历一半的矩阵
            for (int j = i + 1; j < M.length; j++) {
                if (M[i][j] == 1) {
                    uf.union(i, j);
                }
            }
        }
        return uf.getCount();
    }
    
    //并查集实现
    class UnionFind {
        private int[] parent;
        private int count;
        public UnionFind(int n) {
            count = n;
            parent = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
            return;
        }
        //带路劲压缩的查找
        public int find(int i) {
            if (parent[i] == i) {
                return i;
            }
            parent[i] = find(parent[i]);
            return parent[i];
        }
        //集合合并
        public void union(int p, int q) {
            int pRoot = find(p);
            int qRoot = find(q);
            if (pRoot == qRoot) return;
            parent[pRoot] = qRoot;
            count--;
            return;
        }
        public int getCount() {
            return count;
        }
    }
}
```
##### 复杂度分析
时间复杂度： 要遍历整个board，所以是O(n^2)，每次查询并查集因为了带了路径压缩，所以是O(1)，所以最终是O(n^2)
空间复杂度： O(n)，应为并查集数组需要额外的n个大小
### 高级搜索
#### 剪枝
##### 概念
剪枝是一种优化技巧。
回溯算法复杂度往往比较高，在回溯算法中可以使用剪枝技巧提高效率，因为剪枝后不需要穷举所有情况。其名字很形象，就是遇到无用的枝叶可以剪掉，即没有用的分支可以提前返回
##### 常见剪枝函数
1. 约束函数：在扩展节点处剪去不满足约束的子树
2. 限界函数：剪去得不到最优解的子树
##### 例题：N皇后
N皇后可以通过回溯的方法来实现，在实现过程中当前节点如果要放置皇后时，需要判断是否符合规则（横向、纵向，斜向都不能有其他皇后），即约束条件，如果不满足，那么由此展开的情况都可以放弃，也就达到了剪枝的效果
```java

class Solution {
     List<List<java.lang.String>> res = new ArrayList<>();
    public List<List<java.lang.String>> solveNQueens(int n) {
        if (n <= 0) return res;
        int[][] board = new int[n][n];
        dfs(board, 0, n);
        return res;
    }
    private void dfs(int[][] board, int row, int n) {
        //终止条件：当所有行都处理完时
        if (row == n) {
            res.add(transfer(board));
            return;
        }
        //做选择，分别从0列到最后一列进行选择
        for (int col = 0; col < n; col++) {
            if (!isValid(board, row, col)) continue;//剪枝
            board[row][col] = 1;
            dfs(board, row + 1, n);
            board[row][col] = 0;//回溯清理
        }
        return;
    }
    /*判断该位置是否可以放置皇后*/
    private boolean isValid(int[][] board, int row, int col) {
        //列
        for (int i = 0; i < row; i++) {
            if (board[i][col] == 1) return false;
        }
        //左上
        for (int i = row, j = col; i >= 0 && j >= 0; i--,j--) {
            if (board[i][j] == 1) return false;
        }
        //右上
        for (int i = row, j = col; i >= 0 && j < board[0].length; i--,j++) {
            if (board[i][j] == 1) return false;
        }
        return true;
    }
    /*棋盘转化为结果*/
    private List<java.lang.String> transfer(int[][] board) {
        List<java.lang.String> l = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 0) {
                    sb.append(".");
                } else if (board[i][j] == 1) {
                    sb.append("Q");
                }
            }
            l.add(sb.toString());
        }
        return l;
    }
}


```

#### 双向BFS
##### 概念
双向BFS是一种优化技巧。
双向BFS是基于基础的BFS算法的基础上进行改进：基础的BFS是从一头开始进行BFS扩散，遇到终点停止；而双向BFS是从起点和终点同时开始BFS扩散，两边有交集时停止
##### 限制
双向BFS也有其局限，一般要知道终点在哪里
##### 效率
从Big O的角度除非，实际上是一样的，都是O(n)
但是如果画出图直观感受就是可能遍历一半的节点就可以搜索到了，省去一些遍历

##### 模板代码
模板代码（伪代码如下）
```java
public class Template {
    public boolean dfs(Object begin, Object target) {
        Set<Object> beginSet;
        Set<Object> endSet;
        Set<Object> visited;
        beginSet.add(begin);
        endSet.add(target);
        while(!beginSet.isEmpty() && !endSet.isEmpty()) {
            //优化：从小的开始扩散
            if (beginSet.size() > endSet.size()) {
                Set<String> temp = endSet;
                endSet = beginSet;
                beginSet = temp;
            }
            //tempQueue存储着下一层的元素，会成为新的beginQueue
            Set<Object> tempSet;
            //遍历本层元素
            for (Object em : beginSet) {
                //将元素的连通节点都进行处理
                for (Object child : em.children) {
                    //相交了则返回
                    if (endSet.contains(child)) {
                        return true;
                    }
                    //如果没有访问过，则加入
                    if (!visited.contains(child)) {
                        tempSet.add(child);
                        visited.add(child);
                    }
                }
            }
            beginSet = tempSet;
        }
        return false;
    }
}
```
#### A*算法
##### 定义
是静态路网中求解最短路径最有效的直接搜索方法

### AVL树和红黑树
#### AVL树
##### 定义
平衡二叉搜索树是为了解决二叉树可能退化成单链表得情况，造成插入、删除得效率变成O(n)
定义：二叉树中任意一个节点的左右子树高度差不能大于1
##### 旋转方式
* LL情况：右旋
* RR情况：左旋
* LR情况：先左旋后右旋
* RL情况：先右旋后左旋
 ##### 复杂度
插入、删除、查找都为O(logn)
#### 红黑树
##### 定义
因为AVL树旋转频率太高，所以需要一类旋转频率不那么高的树，同时高度差控制在一定范围内即可，于是产生了红黑树
定义：
1. 根节点是黑的
2. 每个叶节点也是黑的，叶节点不存储数据
3. 任何相邻的节点都不能为红色，红色节点被黑色节点隔开
4. 每个节点，从该节点到达其可达叶子节点的左右路径，都包含相同数目的黑色节点
##### 复杂度
插入、删除、查找都为O(logn)