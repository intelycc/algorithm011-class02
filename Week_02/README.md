### 本周数据结构与算法总结
#### 散列表
散列表利用的是数组支持按下表随机访问数据的特性，所以其实数组的一种扩展，由数组演化而来
##### 散列函数
设计基本要求：
1. 散列函数计算得到的散列值是一个非负整数
2. 如果key1 == key2，那么哈希值一定相等
3. 如果key1 != key2，那么哈希值一定不相等（此点其实无法达到，无法完全避免冲突）

散列函数的设计不能太复杂，否则回影响效率
同时散列函数的值要尽可能随机且分布均匀，这样才能避免或者最小化冲突

常见散列函数：
MD5
SHA
CRC
求余
##### 散列冲突
1. 开放寻址法
2. 链表法
##### 效率
查询、添加、删除时间复杂度 O(1)，极端情况下为O(n)

#### 树
##### 常见概念
1. 节点的高度：节点到叶子节点的最长路径（边数）
2. 深度：根节点到这个节点所经历的边的个数
3. 节点的层数：节点的深度 + 1
4. 树的高度： 根节点的高度
##### 二叉树
每个节点最多有两个子节点
###### 满二叉树
叶子节点全都在对底层，除了叶子节点外，每个节点都有左右连个子节点
###### 完全二叉树
叶子节点都在最底下两层，最后一层的叶子节点都靠左排列，并且除了最后一层，其他层的节点个数都要达到最大
###### 二叉树存储
1. 链式存储法
2. 顺序存储法
###### 二叉树的遍历
1. 前序遍历
2. 中序遍历
3. 后序遍历
###### 二叉搜索树
定义：它或者是一棵空树，或者是具有下列性质的二叉树： 若它的左子树不空，则左子树上所有结点的值均小于它的根结点的值； 若它的右子树不空，则右子树上所有结点的值均大于它的根结点的值

插入、删除、查找效率都是： O(logn)
#### 堆
定义： 
1. 是一个完全二叉树
2. 堆中每个节点的值都必须大于等于（或小于等于）其子树中的每个节点的值
###### 往堆中插入一个元素
从下往上堆化
###### 删除堆定元素
从上往下堆化
#### 图
##### 基本概念
顶点(vertex)：图中的节点元素
边(edge)：顶点间建立的关系
度(degree): 顶点有多少相连的边
入度(In-degree)：有向图中，表示有多少条边指向这个顶点
出度(Out-degree): 有向图中，表示有多少条边是以这个顶点为起点指向其他顶点
##### 存储方法
1. 邻接矩阵
2. 邻接表
##### 搜索方法
1. 广度优先（BFS）
2. 深度优先（DFS）

### HashMap源码分析
#### 概述
HashMap是能根据key快速访问内存存储位置的数据结构
##### 类图
HashMap的继承结构很简单，主要的一条线就是：
HashMap->AbstractMap->Map
实现了Map相关的接口，Map的核心API如下：
```
//返回map中键值对映射的数量
int size();

//Map中是否不存在任何键值对
boolean isEmpty();

//map中是否存在指定key的键值对映射
boolean containsKey(Object key);

//map中是否存在键值对其中值对于给定值的
boolean contansValue(Object value);

//获取key对应的键值对
V get(Object key);

//添加一组键值对，会覆盖已有的
V put(K key, V value);

//map中移除key对应的键值对
V remove(Object key)

//清空map
void clear();

//返回key组成的视图，改变map也会影响此视图
Set<K> keySet();

//返回value组成的视图，改变map也会影响此视图
Collection<V> values();

//返回键值对集合视图，同样，改变map也会影响此视图
Set<Map.Entry<K, V>> entrySet();
```
Map中也有一个重要结构Entry，在Map中定义为接口，主要封装了map中存储的键值对（Key-value pair），entrySet()接口即返回该实体的集合，核心API如下：
```
K getKey();
V getValue();
V setValue(V value);
```
##### 类注释
可以得到以下信息：
1. 支持key为null，支持value为null
2. 和Hashtable有两点差别：支持null值；非线程安全
3. get和put提供了O(1)的时间复杂度
4. 迭代过程中hashmap如果被修改回快速失败

##### 内部组成
```
//默认初始化容量16，必须是2的幂次方
static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;

//最大容量
static final int MAXIMUM_CAPACITY = 1 << 30;

//负载因子，决定何时扩容，是个百分比
static final float DEFAULT_LOAD_FACTOR = 0.75f;

//冲突链转化为红黑树的阈值，大于等于8时就转换为红黑树
static final int TREEIFY_THRESHOLD = 8;

//冲突链由红黑树转化为链表的阈值，小于等于6的时候会转化为链表
static final int UNTREEIFY_THRESHOLD = 6;

//Map中Entry的具体实现类
static class Node<K,V> implements Map.Entry<K,V>

//存储键值对的本体，其实是个Node数组
transient Node<K,V>[] table;

//hashmap大小
transient int size;

//扩容的阈值，通过capacity * load factory计算出来
int threshold;
```
#### 主要API实现
##### 初始化
提供了多种构造函数，大致如下：
1. 默认构造函数，会采用默认的大小即扩容因子
2. 提供初始化大小的构造函数
3. 提供初始化大小及扩容因子的构造函数
4. 提供了另外一个map作为入参的构造函数
主要看下第三种：
```
public HashMap(int initialCapacity, float loadFactor) {
    //不能小于0
    if (initialCapacity < 0)
        throw new IllegalArgumentException("Illegal initial capacity: " +
                                           initialCapacity);
    //超过最大容量就初始化成最大容量值
    if (initialCapacity > MAXIMUM_CAPACITY)
        initialCapacity = MAXIMUM_CAPACITY;
    //判断负载因子是否合法
    if (loadFactor <= 0 || Float.isNaN(loadFactor))
        throw new IllegalArgumentException("Illegal load factor: " +
                                           loadFactor);
    this.loadFactor = loadFactor;
    //根据负载因子计算扩容阈值
    this.threshold = tableSizeFor(initialCapacity);
}

//其实会找到离2的幂次方最近的数字
static final int tableSizeFor(int cap) {
    int n = cap - 1;
    n |= n >>> 1;
    n |= n >>> 2;
    n |= n >>> 4;
    n |= n >>> 8;
    n |= n >>> 16;
    return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
}
```
##### 添加元素：put()
总结下整个过程大致如下：
1.  数组为空则通过resize()进行初始化
2.  通过hash映射位置，如果所在位置没有元素，那么直接新建一个节点填入即可
3.  如果有元素，说明要处理冲突：
   3.1 如果冲突连是树，调用树的api来添加
   3.2 如果冲突连是链表，那么需要添加在链表最后，先遍历链表
        3.2.1 遇到key一样的（hash一样且值或地址一样），说明可能需要替换该节点，记录
        3.2.2 遍历到尾部，直接添加到尾部即可
   3.3  根据onlyIfAbsent来判断是否要替换找到节点的值
4. 最后size++，同时判断是否要扩容 

具体的过程可以看以下源代码注释：
```
public V put(K key, V value) {
    return putVal(hash(key), key, value, false, true);
}

final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
               boolean evict) {
    Node<K,V>[] tab; Node<K,V> p; int n, i;
    //数组为空，那么通过resize进行初始化
    if ((tab = table) == null || (n = tab.length) == 0)
        n = (tab = resize()).length;
    //如果映射的位置是空的，表示没有任何元素，那么直接new一个node在该位置即可
    if ((p = tab[i = (n - 1) & hash]) == null)
        tab[i] = newNode(hash, key, value, null);
    //否则，解决冲突
    else {
        Node<K,V> e; K k;
        //如果冲突的第一个节点key和hash都和新加的相等，那么先暂时保存在e
        if (p.hash == hash &&
            ((k = p.key) == key || (key != null && key.equals(k))))
            e = p;
        //冲突链是树，那么使用树添加的api
        else if (p instanceof TreeNode)
            e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
        //冲突连是个链表，新节点放在尾部
        else {
            for (int binCount = 0; ; ++binCount) {
                //如果p已经是链表尾节点了，那么直接添加，同时看看是否触发转树
                if ((e = p.next) == null) {
                    p.next = newNode(hash, key, value, null);
                    if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                        treeifyBin(tab, hash);
                    break;
                }
                //遍历过程如果发现和某个元素key相等
                if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k))))
                    break;
                //更改指针，实现循环
                p = e;
            }
        }
        //e不为空说明需要替换e指向节点的值
        if (e != null) { // existing mapping for key
            V oldValue = e.value;
            //根据onlyIfAbsent来判断是否要进行替换
            if (!onlyIfAbsent || oldValue == null)
                e.value = value;
            afterNodeAccess(e);
            return oldValue;
        }
    }
    ++modCount;
    //如果超过阈值，那么要扩容
    if (++size > threshold)
        resize();
    afterNodeInsertion(evict);
    return null;
}
```
注意，并不是冲突链大于等于8就一定转化为树的，只有在链大于等于8，且hash数组长度大于等于MIN_TREEIFY_CAPACITY（默认64）才会进行转化，否则只会扩容
```
final void treeifyBin(Node<K,V>[] tab, int hash) {
    int n, index; Node<K,V> e;
    //此处有个< MIN_TREEIFY_CAPACITY的判断
    if (tab == null || (n = tab.length) < MIN_TREEIFY_CAPACITY)
        resize();
    else if ((e = tab[index = (n - 1) & hash]) != null) {
        TreeNode<K,V> hd = null, tl = null;
        do {
            TreeNode<K,V> p = replacementTreeNode(e, null);
            if (tl == null)
                hd = p;
            else {
                p.prev = tl;
                tl.next = p;
            }
            tl = p;
        } while ((e = e.next) != null);
        if ((tab[index] = hd) != null)
            hd.treeify(tab);
    }
}
```
还有为什么转化树的阈值是8，源码注释中有说明，根据泊松分布的概率计算链表各个长度出现的概率，在达到8的概率已经非常小的，如果出现说明hash算法可能存在问题，为了提高效率转化为红黑树，：
```
* 0:    0.60653066
* 1:    0.30326533
* 2:    0.07581633
* 3:    0.01263606
* 4:    0.00157952
* 5:    0.00015795
* 6:    0.00001316
* 7:    0.00000094
* 8:    0.00000006
```
##### 查询：get()
get大致步骤如下：
1. 以下几种情况可以直接返回
    1.1 数组为空
    1.2 对应下标的元素为空
2. 查询数组第一个元素，命中（比较hash及值或地址）则返回
3. 如果是树则调用树的API查询
4. 如果是链表则遍历查询
详细的见如下注释
```
public V get(Object key) {
    Node<K,V> e;
    return (e = getNode(hash(key), key)) == null ? null : e.value;
}

final Node<K,V> getNode(int hash, Object key) {
    Node<K,V>[] tab; Node<K,V> first, e; int n; K k;
    //如果数组不为空并且对应下标的元素不为空才继续
    if ((tab = table) != null && (n = tab.length) > 0 &&
        (first = tab[(n - 1) & hash]) != null) {
        //检查第一个元素，如果hash值相同并且key地址相同或值相同，那么命中，直接返回
        if (first.hash == hash && // always check first node
            ((k = first.key) == key || (key != null && key.equals(k))))
            return first;
         //第一个元素未命中，则继续
        if ((e = first.next) != null) {
            //如果是树，则用树的查询方式
            if (first instanceof TreeNode)
                return ((TreeNode<K,V>)first).getTreeNode(hash, key);
            //否则依次遍历查询链表
            do {
                if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k))))
                    return e;
            } while ((e = e.next) != null);
        }
    }
    //走到这步说明没有查找到
    return null;
}
```

