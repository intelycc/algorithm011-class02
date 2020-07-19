### 搜索算法
本周主要学习了搜索算法中的深度优先和广度优先算法
这两种算法都是**基于“图”这种数据结构**（当然也可以用于**树**，树是特化的图）
#### 深度优先搜索
##### 定义
从一个节点开始往一条路一直走到底，直到走不通则返回上一个节点走另外一条路，这个其实用的是**回溯**的思想
##### 算法要点
1. 递归
2. 回溯思想
3. 如果有环数据结构，需要visied
##### 算法效率
* 时间复杂度**O(E)**，E代表边的个数
* 空间复杂度**O(V)**， V代表顶点个数（visited数组需要存放所有顶点是否已经访问过）
##### 代码模板
其实根回溯的模板很像
```
visited = set()

def dfs(node, visited):
	visited.add(node)
	# process current node here.
	...
	for next_node in node.children():
		if not next_node in visited:
			dfs(next node, visited)
```
#### 广度优先搜索（BFS）
##### 定义
从一个顶点开始，沿着图的宽度遍历图，直到找到搜索的节点或者遍历完则算法中止，采用地毯式层层推进策略
##### 算法要点
1.  需要借助一个队列**queue**来存储已经访问但是其直接相连的节点尚未访问的节点
2.  如果有环数据结构（图），需要借助**visited**变量来标记访问过的节点，避免重复访问
##### 算法效率
* 时间复杂度**O(E)**，E代表边的个数
* 空间复杂度**O(V)**， V代表顶点个数（visited数组需要存放所有顶点是否已经访问过）
##### 代码模板
如算法要点里提到的，queue和visted是关键，主体是个循环
```
def BFS(graph, start, end):
	queue = []
	queue.append([start])
	visited.add(start)
	while queue:
		node = queue.pop()
		visited.add(node)
		
		process(node)
		nodes = generate_related_nodes(node)
		queue.push(nodes)
```
#### 贪心算法
##### 定义
贪心算法在解决某个问题时都是选择当下最优的解，不从整体最优考虑，有的时候局部最优的会推导到全局最优解的问题可以用此方法
##### 特点
* 局部最优不一定会是整体最优，所以贪心算法很多场景不适用
* 贪心算法的复杂度一般呈线性，所以如果一个问题能用贪心算法解决，一般也是最优解
* 每一步的最优解一定包含上一步的最优解，上一步之前的最优解则不保留

#### 二分查找
##### 定义
分治思想：有序区间，每次选取区间的中间元素进行比较，将查找区间缩小为一半，直到找到要查找的元素，或者区间长度为0
##### 前提条件
* 递增或递减
* 存在上下界
* 能够通过索引访问，如果链表就比较麻烦
##### 算法效率
时间复杂度：O(logn)
二分查找像搜索一棵二叉搜索树，复杂度即层高
##### 代码模板
```
left, right = 0, len(array) - 1
while left <= right:
	mid = (left + right) / 2
	if array[mid] == target:
		# find the target!!
		break or return result
	elif array[mid] < target:
		left = mid + 1
	else:
		right = mid - 1
```
容易出错的有以下几个地方：
* 循环退出条件是left <= right 而不是left < right。比如只有一个值的情况，那么就会漏掉
* mid的取值要防止溢出，(left+right)/2这种写法会有溢出风险，要改成 left+(right-left)/2
* left和right的更新left=mid+1，right=mid-1，如果写成left = mid 或 right = mid，那么可能导致循环无法退出
