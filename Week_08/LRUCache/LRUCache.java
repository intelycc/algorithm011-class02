package com.company.lru;

//运用你所掌握的数据结构，设计和实现一个 LRU (最近最少使用) 缓存机制。它应该支持以下操作： 获取数据 get 和 写入数据 put 。
//
// 获取数据 get(key) - 如果关键字 (key) 存在于缓存中，则获取关键字的值（总是正数），否则返回 -1。
//写入数据 put(key, value) - 如果关键字已经存在，则变更其数据值；如果关键字不存在，则插入该组「关键字/值」。当缓存容量达到上限时，它应该在
//写入新数据之前删除最久未使用的数据值，从而为新的数据值留出空间。
//
//
//
// 进阶:
//
// 你是否可以在 O(1) 时间复杂度内完成这两种操作？
//
//
//
// 示例:
//
// LRUCache cache = new LRUCache( 2 /* 缓存容量 */ );
//
//cache.put(1, 1);
//cache.put(2, 2);
//cache.get(1);       // 返回  1
//cache.put(3, 3);    // 该操作会使得关键字 2 作废
//cache.get(2);       // 返回 -1 (未找到)
//cache.put(4, 4);    // 该操作会使得关键字 1 作废
//cache.get(1);       // 返回 -1 (未找到)
//cache.get(3);       // 返回  3
//cache.get(4);       // 返回  4
//
// Related Topics 设计
// 👍 816 👎 0


import java.util.HashMap;
import java.util.Map;

//leetcode submit region begin(Prohibit modification and deletion)
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

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
//leetcode submit region end(Prohibit modification and deletion)

