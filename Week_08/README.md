### 位运算
#### 位运算符
* 算术左移： << 向左进行移位操作，最高位符号位保持不变，低位补 0
* 逻辑左移：<<< 向左进行移位操作，高位丢弃，低位补 0
* 算术右移：>> 向右进行移位操作，对无符号数，高位补 0，对于有符号数，高位补符号位
* 逻辑右移：>>> 最高位补0，最低位丢失
* 按位或：|  两位中只要有 1 位为 1 结果就是 1，两位都为 0 则结果为 0
* 按位与：&  只有当两位都是 1 时结果才是 1，否则为 0
* 按位取反：~ 0 则变为 1，1 则变为 0
* 按位异或：^ 两个位相同则为 0，不同则为 1
#### 优先级
优先级比较容易搞错
移位运算符、单目运算中的取反运算符 > 比较运算符
&, |, ^  < 比较运算符 
#### 技巧
1. 判断整型奇偶性
只需要判断最后1位是否为1
x & 1 == 0 偶数
x & 1 == 1 奇数

2. 判断第n位是否为1
x & (1 << n)) == 0 不是
x & (1 << n)) == 1 是的

3. 将第n位设置为1;将第n位设置为0
y = x | (1 << n)  设置为1
y = x &  ~(1 << n) 设置为0

4. 将第n位的值取反
y = x ^ (1 << n)

5. 将最右边的1设置为0（清零最低位）
y = x & (x - 1)

6. 整除
x >> 1

7. 得到最低位的1（保留最低位的1）
x & -x
#### 例题
##### N皇后II
N皇后的解题基本思路就是采用回溯。
有一个地方可以用bitmap进行优化，就是判断当前行中哪些位置可以放置皇后
此题综合运用了多种位运算技巧，如：
*  (1 << n) - 1 构造除了一个最为n位为1，其他为0的bitmap
* n & (n - 1) 将最后一个1清除
* n & -n 只保留最低位的1
等等，详细介绍见注释
```java
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
```

### 布隆过滤器
#### 概念
布隆过滤器是一个很长的二进制向量和一系列随机映射函数。
用于检索一个元素是否在一个集合中。当数据量级非常大的时候，布隆过滤器的优势就出来了
优点：空间效率高，查询速度快
缺点：有一定的误识别率、删除困难
#### 常见应用
1. 网页URL去重（比如爬虫，避免重复爬取相同的URL）
2. 垃圾邮件判别 （从上亿的垃圾邮箱列表判断邮箱是否属于垃圾邮箱）
3. 集合重复元素的判别
4. 查询加速（比如作为缓存，先进行判断，如果无法确定，再到数据库层面）

### LRU缓存
#### 概念
LRU cache是一种缓存，LRU是一种淘汰策略，即最近最少使用，是应用最广泛的策略之一
#### 其他常见淘汰策略
FIFO ：先进先出
LFU：最少使用（淘汰频次低的）
RR： 随机替换
MRU：最新使用（淘汰最新的）
#### 实现
* Java版简单实现如下，采用HashMap + 双向链表，时间复杂度为O(1)
* 链表中最近最新的靠近head，即将淘汰的（最近最少使用）的接近tail
* 用HashMap可以实现O(1)的查找
* 当获取或者更新value的时候都要将节点挪到第一个，所以为了挪动效率高，使用双向链表（单链表找pre麻烦）
```java
class LRUCache {
    int capacity;
    int size;
    private Map<Integer, DLinkedNode> cache;
    private DLinkedNode head, tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.cache = new HashMap<>(capacity);
        //为了代码方便，两个哨兵节点
        head = new DLinkedNode();
        tail = new DLinkedNode();
        head.next = tail;
        tail.pre = head;
    }

    public int get(int key) {
        DLinkedNode node = cache.get(key);
        if (node == null) {
            return -1;
        }
        moveToHead(node);
        return node.value;
    }

    public void put(int key, int value) {
        DLinkedNode node = cache.get(key);
        if (node != null) {
            node.value = value;
            //更新值也要移动到头部
            moveToHead(node);
        } else {
            DLinkedNode newNode = new DLinkedNode(key, value);
            cache.put(key, newNode);
            head.next.pre = newNode;
            newNode.next = head.next;
            newNode.pre = head;
            head.next = newNode;
            size++;
            if (size > capacity) {
                DLinkedNode removeNode = removeTail();
                cache.remove(removeNode.key);
                size--;
            }
        }
        return;
    }

    private void moveToHead(DLinkedNode node) {
        //记录前后节点
        DLinkedNode nextNode = node.next;
        DLinkedNode preNode = node.pre;
        //调整前后节点的指针
        preNode.next = nextNode;
        nextNode.pre = preNode;
        //调整当前节点的指针
        node.next = head.next;
        node.pre = head;
        //调整head及第一个节点的指针
        head.next.pre = node;
        head.next = node;
        return;
    }

    private DLinkedNode removeTail() {
        if (size == 0) return null;
        DLinkedNode removeNode = tail.pre;
        DLinkedNode preNode = removeNode.pre;
        preNode.next = tail;
        tail.pre = preNode;
        return removeNode;
    }

    class DLinkedNode {
        DLinkedNode pre;
        DLinkedNode next;
        int key;
        int value;

        public DLinkedNode() {
        }

        public DLinkedNode(int k, int v) {
            key = k;
            value = v;
        }
    }
}
```
### 排序算法
#### 冒泡排序
两两比较，一次冒泡会将最大的一个“冒”到尾部
##### 效率及稳定性
* 时间复杂度：最好O(n)，最坏和平均O(n^2)  
* 是否原地排序： 是，因为只需要交换时的一个临时变量，是常量级的临时空间，为O(1)
* 是否是稳定的排序算法： 是，因为当两个元素相等的时候我们可以选择不交换，那么相对顺序就不会变化
##### 实现
```java
public void bubbleSort(int[] a) {
    if (a == null || a.length <= 1) return;
    int len = a.length;
    for (int i = 0; i < len; i++) {
        boolean flag = false;
        for (int j = 0; j < len - i - 1; j++) {
            if (a[j] > a[j + 1]) {
                int temp = a[j];
                a[j] = a[j + 1];
                a[j + 1] = temp;
                flag = true;
            }
        }
        if (!flag) return;
    }
    return;
}
```
#### 插入排序
从未排序的集合中选第一个元素，插入到已排序区间的合适位置
##### 效率及稳定性
* 时间复杂度：最好O(n) 最坏及平均O(n^2)
* 是否原地排序： 是。不需要额外空间，空间复杂度为O(1)
* 是否是稳定的排序算法： 是。只要值相等的时候插入到元素后面即可保持相对有序
##### 实现
```java
public void insertionSort(int[] a) {
    if (a == null || a.length <= 1) return;
    int len = a.length;
    for (int i = 1; i < len; i++) {
        int data = a[i];
        int j = i - 1;
        for (; j >= 0; j--) {
            if (a[j] > data) {
                a[j + 1] = a[j];
            } else {
                break;
            }
        }
        a[j + 1] = data;
    }
    return;
}
```
#### 选择排序
从未排序的区间里选出最小的元素，放置到排序区间的最后一个
##### 效率及稳定性
* 时间复杂度： 最好、最坏、平均都要O(n ^ 2)
* 是否原地排序： 是。只需要一个临时变量用于交换，常量级空间复杂度，O(1)
* 是否是稳定的排序算法： 不是。因为每次在未排序区间找到元素后会和第一个元素交换，这样破坏了稳定性，如3，8，3，2，10 第一个3和2交换后就破坏了稳定性
##### 实现
```java
public void selectionSort(int[] a) {
    if (a == null || a.length <= 1) return;
    int len = a.length;
    for (int i = 0; i < len; i++) {
        int min = a[i];
        int minIdx = i;
        for (int j = i + 1; j < len; j++) {
            if (a[j] < min) {
                min = a[j];
                minIdx = j;
            }
        }
        int temp = a[i];
        a[i] = a[minIdx];
        a[minIdx] = temp;
    }
    return;
}
```
#### 归并排序
分治思想，将数组从中间分为前后两个区间，分别用同样方法对前后两个区间排序再合并
##### 效率及稳定性
* 时间复杂度：最好、最坏、平均都是O(nlogn)
* 是否原地排序：不是。需要开辟一个额外的空间存储，复杂度是O(n)
* 是否稳定：是。因为在合并的过程中，对于相等的情况下，我们可以把前面区间的先方进去，这样就是稳定的了
##### 实现
```java
public void mergeSort(int[] a) {
    if (a == null || a.length <= 1) return;
    mergeSorted(a, 0, a.length - 1);
}
private void mergeSorted(int[] a, int p, int q) {
    if (p >= q) return;
    int mid = p + (q - p) / 2;
    mergeSorted(a, p, mid);
    mergeSorted(a, mid + 1, q);
    merge(a, p, mid, mid + 1, q);
}
private void merge(int[] a, int p, int m, int n, int q) {
    int[] temp = new int[q - p + 1];
    int k = 0;
    int i = p;
    int j = n;
    while (i <= m && j <= q) {
        if (a[i] <= a[j]) {
            temp[k++] = a[i++];
        } else {
            temp[k++] = a[j++];
        }
    }
    while (i <= m) temp[k++] = a[i++];
    while (j <= q) temp[k++] = a[j++];
    for (i = 0; i < temp.length; i++) {
        a[p++] = temp[i];
    }
    return;
}
```
#### 快速排序
选择一个基准元素，将小于基准元素的放左侧，大于的放右侧，同样对左侧、右侧区间都执行类似操作
##### 效率及稳定性
时间复杂度：最差O(n^2)，最好及平均O(nlogn)
是否原地排序：是，常量级的空间占用，为O(1)
是否稳定排序：否，因为有交换元素
##### 实现
```java
public void quickSort(int[] a) {
    if (a == null || a.length <= 1) return;
    quickSorted(a, 0, a.length - 1);
}
private void quickSorted(int[] a, int p, int r) {
    if (p >= r) return;
    int q = partition(a, p, r);
    quickSorted(a, p, q - 1);
    quickSorted(a, q + 1, r);
}
private int partition(int[] a, int p, int r) {
    int pivot = a[r];
    int i = p;
    for (int j = p; j < r; j++) {
        if (a[j] < pivot) {
            int temp = a[i];
            a[i] = a[j];
            a[j] = temp;
            i++;
        }
    }
    int temp = a[i];
    a[i] = a[r];
    a[r] = temp;
    return i;
}
```