### 理论篇
##### 适用场景
* 一般适用求最值问题
* 具备最优子结构：问题的最优解包含子问题的最优解，即子问题的最优解能够推导出问题的最优解
* 重复子问题：不同的决策序列，到达某个状态时，可能产生重复的状态。这个不是动态规划问题必须的，但是是动态规划优于其他算法的地方。
* 无后效性：某个状态一旦确定，就不受这个状态以后决策的影响
##### 解题步骤
* 明确状态，定义dp数组（面试题一般难在这里）
* 定义状态转移方程 （竞赛题一般难在这里）
* 确定base case
* 遍历顺序可以根据base case和最终状态进行推导

**状态定义**：不同的状态定义，会影响最终的代码写法，也会影响问题的复杂度
**状态转移方程**：动态规划问题的关键，其实就是分析某个问题如果通过子问题来**递归**解决

##### 和其他算法的区别
* 回溯：回溯一般适用没有特别的规律，需要求解所有情况，算法复杂度是指数级别，比较高，只能用来解决小规模问题
* 分治：分治算法要求分割成的子问题不能有重复性，而动态规划则相反
* 贪心：贪心算法其实是动态规划的一种特殊情况（局部最优能够达到整体最优）

##### 其他
* 画出状态数组的图形会方便理解
* 如果一维不够，要考虑二维情况

### 实战篇
#### 最大正方形
##### 思路
最值问题一般适合用动态规划。
只要求得最大边长，那么就可以求出最大面积。
因为题目是个二维数组，所以自然想到用二维数组来存放状态。
1. 定义状态：dp[i][j]代表的是以matrix(i,j)为右下角的正方形最大边长
2. 定义状态转移方程：
    * 首先可以观察到如果要以matrix[i][j]为右下角形成正方形（非单1），那么其左、上、左上都必须是1
    * 其左、上、左上形成的正方形边长最小值限制了和matrix[i][j]形成的正方形的大小，类似短板理论
  所以状态转移方程如下：
  matrix[i][j] == '1'： 
        dp[i][j] = min(dp[i - 1][j], dp[i][j - 1], dp[i-1][j-1]) + 1
  matrix[i][j] == '0':
        dp[i][j] = 0 
3. 确定base case：
     dp矩阵第一行和第一列，因为在边界的原因，所以最大也只能形成边长为1的正方形，所以要先赋值。
     同时，可以从上至下正向遍历，因为能够保证当前dp[i][j]的左、上、左上都有值
##### 代码    
代码：
```java
class Solution {
    public int maximalSquare(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return 0;
        int max = 0;
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] dp = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
               //处理base case
                if ((i == 0 || j == 0) && matrix[i][j] == '1') {
                    dp[i][j] = 1;
                }else if (matrix[i][j] == '0') {
                    dp[i][j] = 0;
                } else {
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
                }
                max = Math.max(dp[i][j], max);
            }
        }
        return max * max;
    }
}
```
如果为了代码更简洁，不用额外处理边界，可以定义二维数组的时候多定义一层，这样就不用特殊的if来做判断，但是就是要注意取matrix的时候要-1，代码如下：
```java
class Solution {
    public int maximalSquare(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return 0;
        int max = 0;
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] dp = new int[rows + 1][cols + 1];
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                if (matrix[i - 1][j - 1] == '0') {
                    dp[i][j] = 0;
                } else {
                    int min = Math.min(dp[i][j - 1], dp[i - 1][j]);
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], min);
                }
                max = Math.max(max, dp[i][j]);
            }
        }
        return max * max;
    }
}
```
##### 效率
时间复杂度：把二维数组遍历了一遍，所以是O(height * width)
空间复杂度：额外定义了一个dp数组，所以是O(height * width)
#### 回文子串
##### 思路
1. 定义状态：这点比较难，先考虑一维情况，如果不行可以考虑二维，一般字符串问题用二维的比较多
   这里可以考虑dp[i][j]表示的是字符串s[i..j]是否为回文。可以想想i，j分别指向字符串中的两个位置来框定区间（i<=j）
2. 定义状态转移方程：这里就可以找重复子问题，当子问题dp[i+1][j - 1]已经有结果，那么如何推导dp[i][j]（相当于i，j指针各子往外扩散一级）：
    * 如果s[i] == s[j] 那么只要s[i+1...j-1]是回文，那么就能推导出s[i...j]也是回文，当然如果不是，那么s[i...j]肯定也不是
      s[i] == s[j] : dp[i][j] = dp[i+1][j-1]
    * 如果s[i] != s[j] 那无论s[i+1...j-1]是否是回文，s[i...j]都不是回文
      s[i] ！= s[j] : dp[i][j] = false
3. 确定base case
   i <= j ,所以二维矩阵中，i > j的都要初始化为false。
   而i == j 即只有一个字母的时候，自然也是回文，初始化为true，即矩阵对角线
   **特殊边界处理**：abba  比如，i，j分别指向两个b，此时子问题已经在矩阵对角线下方，是false，但是其实这种情况是属于回文子串，所以要特殊处理
4. 遍历方向：因为子问题在左下，所以要从下往上遍历，从左往右遍历

##### 代码
```java
class Solution {
    public int countSubstrings(String s) {
        if (s == null || s.length() == 0) return 0;
        int n = s.length();
        int count  = s.length();
        boolean[][] dp = new boolean[n][n];
        //初始化对角线的值
        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
        }
        //从下往上，从左往右遍历，只遍历对角线以上的
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                //dp方程推导
                if (s.charAt(i) == s.charAt(j)) {
                    if (j - i == 1) {
                        dp[i][j] = true;
                    } else {
                        dp[i][j] = dp[i + 1][j - 1];
                    }
                } else {
                    dp[i][j] = false;
                }
                if (dp[i][j]) count++;
            }
        }
        return count;
    }
}
```
##### 效率
时间复杂度：遍历了 n*n / 2 个格子，所以时间复杂度是O(n * n)
空间复杂度：用在dp数组上，是O(n * n)

#### 编辑距离
##### 思路
也是道求最值的题目，考虑动态规划。
1. 定义状态： 两个单词，一般考虑二维数组。dp[i][j]代表word1的0...i的子串转换到word2的0...j的子串他们需要多少步数。分解成了子问题。
2. 定义状态转移方程：
   直观的可以看出，当word1[i] == word2[j]的时候，因为字符相同，不需要编辑，所以编辑的次数取决于其子串即dp[i -1][j - 1]。
   而当word1[i] ！= word2[j]，有几种方法可以操作：
   1. word1通过增加一个字符来匹配word2[j]，此时编辑距离相当于dp[i][j - 1] + 1 （通过一次增加操作，相当于在子串基础上+1）
   2. word1删除该字符，此时编辑距离相当于dp[i - 1][j] + 1（通过一次删除操作，那么相当于i少了一个字符）
   3. word1通过转换该字符来匹配word2[j]，此时相当于dp[i - 1][j - 1] + 1(子串的结果加一次转换操作)
  以上三个操作都能达到效果，所以取最小的。
  所以dp转移方程就出来了：
   word1[i] == word2[j]时：dp[i][j] = dp[i - 1][j - 1]
   word1[i] ！= word2[j]时：dp[i][j] = min(dp[i][j - 1], dp[i - 1][j], dp[i - 1][j - 1]) + 1
 3.定义base case：
 要考虑当有个字符为空的情况，此时就等于另外一个字符的字符数（通过增加、删除来达到）
 考虑这个边界情况，将dp方程定义大一级比较好，多出的即代表“”
 那么basecase就是把这些边界情况给初始化好即可。
 
 **递推方向**：由于子问题在矩阵上是分布于左、上、左上，所以可以从上至下遍历
 ##### 代码
 
```java
class Solution {
    public int minDistance(String word1, String word2) {
        if (word1 == null || word2 == null) return 0;
        int m = word1.length();
        int n = word2.length();
        int[][] dp = new int[m + 1][n + 1];
        //初始化base case
        for (int i = 1; i <= m; i++) dp[i][0] = dp[i - 1][0] + 1;
        for (int j = 1; j <= n; j++) dp[0][j] = dp[0][j - 1] + 1;
        //从上至下递推
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    //dp[i][j - 1] 增加操作；dp[i - 1][j]删除操作；dp[i - 1][j - 1] 替换操作
                    dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i][j - 1], dp[i - 1][j])) + 1;
                }
            }
        }
        return dp[m][n];
    }
}
```

##### 时间复杂度
时间复杂度：遍历一遍整个dp数组，所以是O(M* N)
空间复杂度：需要一个二维dp数组，所以是O(M* N)

#### 戳气球
##### 思路
1. 定义状态：dp[i][j] 可以表示i，j开区间内气球戳破后能产生最大的金币数量
2. 定义状态转移方程：在i，j开区间内，假设k（i < k < j）是最后一个被戳破的气球，那么其金币数就是
  point[k] * point[i] * point[j] 加上其子问题产生的最多金币数量 dp[i][k] + dp[k][j]。因为k在被戳破前，其区间内其他气球也一定会被戳破
  所以状态转移方程也出来了：dp[i][j] = dp[i][k] + dp[k][j] + point[k] * point[i] * point[j]
  所以在给定区间i，j之间，只要穷举期间的所有k，就可以得出dp[i][j]的最大值
3. 定义base case：
* 当最后一个气球戳破的时候，相当于左右两边的边界是值为1的虚拟气球（不可戳破），所以可以将原来的数组转化为头尾边界值为1的新数组points[n + 2]，n是原数组大小
* 0 <=i<=n + 1, j <= i + 1 这区间的没有气球可以戳，都为0
* 递推方向：当前问题dp[i][j]依赖节点本行左侧所有值的最大值dp[i][k]，及当前列下方最大值dp[k][j]，所以要从下到上，从左至右递推

##### 代码
```java
class Solution {
    public int maxCoins(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int n = nums.length;
        int[] points = new int[n + 2];
        //因为数组默认初始化值是0，所以此处basecase也设置好了，矩阵对角线即下方的都是0
        int[][] dp = new int[n + 2][n + 2];
        //为了方便处理边界，将边界设置为两个虚拟气球
        points[0] = 1;
        points[n + 1] = 1;
        for (int i = 1; i < n + 1; i++) {
            points[i] = nums[i - 1];
        }
        //从下往上，从左往右进行递推
        for (int i = n; i >= 0; i--) {
            for (int j = i + 1; j < n + 2; j++) {
                for (int k = i + 1; k < j; k++) {
                    dp[i][j] = Math.max(dp[i][j], points[i] * points[j] * points[k] + dp[i][k] + dp[k][j]);
                }
            }
        }
        // 0...n+1开区间即所求的区间
        return dp[0][n + 1];
    }
}
```
##### 效率
时间复杂度：因为两重循环中还有一层k的循环，所以是O(n^3)
空间复杂度：需要一个二维dp数组，所以是O(n^2)



   