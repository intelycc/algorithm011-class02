### 递归
#### 递归要点
* 递归是一种**编程技巧**
* 问题可以用递归来解决需要同时满足下面三个条件：
    * 一个问题可以**分解为几个子问题**的解
    * 这个问题与分解后的子问题，除了数据规模不同，**求解思路完全一样**
    * 存在递归**终止条件**
* 误区：**不要人肉递归**，人脑不适合人肉递归
* 需要有**数据归纳法**思维
* 递归要警惕**重复计算**，即不要傻递归，可能高达O（2^n）的复杂度，可以通过缓存中间结果来优化
* 递归的代码一般都可以改写为**迭代**，因为递归本身就是借助操作系统的栈来实现的，我们可以手动模拟
#### 递归代码模板
```
public void recur(int level, int param) {
	// terminator 结束条件
	if (level > MAX_LEVEL) {
		// process result
		return;
	}
	// process current logic 处理本层逻辑
	process(level, param);
	// drill down 向下层递进
	recur( level: level + 1, newParam);
	// restore current status 清理本层状态
}
```
### 分治
#### 分治要点
* 本质是**分而治之**，将原问题分解成n个规模较小的问题，并且结构与原问题相似，递归解决这些子问题，然后合并结果得到原问题得解
* 分治是一种**处理问题的思想**，递归是一种编程技巧
* 分治一般适合用递归来实现，每一层递归涉及三个操作：
    1. 分解：将原问题分解成一系列**子问题**
    2. 解决：递归求解各个子问题，若**子问题足够小，则直接求解**
    3. 合并：将子问题的结果**合并**成原问题
* 分治能解决问题的特征：
    * 原问题与分解成的小问题具有**相同的模式**
    * 原问题分解成的子问题可以独立求解，**子问题之间没有相关性**
    * 具有**分解终止条件**，即当问题足够小的时候可以直接求解
    * 可以将子问题合并成原问题，**合并操作复杂度不能太高**，否则起不到减小算法复杂度效果
#### 分治代码模板
```
def divide_conquer(problem, param1, param2, ...):
	# recursion terminator 结束条件
	if problem is None:
		print_result
		return
	# prepare data 准备数据，切分为多个模式相同的子问题
	data = prepare_data(problem)
	subproblems = split_problem(problem, data)
	# conquer subproblems 求解子问题
	subresult1 = self.divide_conquer(subproblems[0], p1, ...)
	subresult2 = self.divide_conquer(subproblems[1], p1, ...)
	subresult3 = self.divide_conquer(subproblems[2], p1, ...)
	...
	# process and generate the final result 合并子问题
	result = process_result(subresult1, subresult2, subresult3, …)
	# revert the current level states 清理本层状态
```

### 回溯
#### 回溯要点

* 回溯算法的本质就是在**枚举所有情况**
* 一般适用于**没有规律**的情况（或者说是不了解其规律）
* 回溯适合用**递归**的方式去实现
* 最坏情况下，回溯算法会导致复杂度为**指数**时间
* 通过**剪枝**可以降低回溯的复杂度，剪枝本质上就是遇到满足或者不满足情况就不继续遍历
* 解决一个回溯的问题实际上是一个**决策树**的遍历过程，用树的结构去想象问题

#### 回溯代码模板
```
List result;
public void backtrack(路径, 选择列表) {
    if (满足结束条件) {
        result.add(路径);
        return;
    }
    for (选择 : 选择列表) {
        做选择;
        backtrack(路径, 选择列表);
        撤销选择;
    }
}
```
### 总结
递归、分治、回溯有很多相似的地方，但是他们其实是看问题的角度不同。
递归更多是一种编程技巧；分治是处理问题的思想，将复杂问题拆成简单的小问题，同时会用到递归解法；回溯本质上是遍历所有情况，也会利用到递归的解法
他们共同的思想就是寻找子问题，即重复性，利用重复性处理问题方法相同的特点结合递归的特点去解决问题。
