package com.company.trie.findWords;

//给定一个二维网格 board 和一个字典中的单词列表 words，找出所有同时在二维网格和字典中出现的单词。
//
// 单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母在一个单词中不允许被重复使用。
//
//
// 示例:
//
// 输入:
//words = ["oath","pea","eat","rain"] and board =
//[
//  ['o','a','a','n'],
//  ['e','t','a','e'],
//  ['i','h','k','r'],
//  ['i','f','l','v']
//]
//
//输出: ["eat","oath"]
//
// 说明:
//你可以假设所有输入都由小写字母 a-z 组成。
//
// 提示:
//
//
// 你需要优化回溯算法以通过更大数据量的测试。你能否早点停止回溯？
// 如果当前单词不存在于所有单词的前缀中，则可以立即停止回溯。什么样的数据结构可以有效地执行这样的操作？散列表是否可行？为什么？ 前缀树如何？如果你想学习如何
//实现一个基本的前缀树，请先查看这个问题： 实现Trie（前缀树）。
//
// Related Topics 字典树 回溯算法
// 👍 208 👎 0


import java.util.ArrayList;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public List<String> findWords(char[][] board, String[] words) {
        List<String> res = new ArrayList<>();
        if (board == null || board.length == 0 || board[0].length == 0
                || words == null || words.length == 0) {
            return res;
        }
        TrieNode root = new TrieNode();
        for (String word : words) {
            TrieNode node = root;
            for (int i = 0; i < word.length(); i++) {
                char ch = word.charAt(i);
                if (!node.contains(ch)) {
                    node.put(ch, new TrieNode());
                }
                node = node.get(ch);
            }
            node.setLetter(word);
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (!root.contains(board[i][j])) continue;
                backtrace(root, board, i, j, res);
            }
        }

        return res;
    }

    private void backtrace(TrieNode root, char[][] board, int i, int j, List<String> l) {
        char ch = board[i][j];
        TrieNode node = root.get(ch);
        if (node.getLetter() != null) {
            l.add(node.getLetter());
            node.setLetter(null);
        }
        board[i][j] = '#';
        int[] rowOffset = {-1, 0, 1, 0};
        int[] colOffset = {0, 1, 0, -1};
        for (int k = 0; k < 4; k++) {
            int newRow = i + rowOffset[k];
            int newCol = j + colOffset[k];
            if (newRow < 0 || newRow >= board.length || newCol < 0 || newCol >= board[0].length || board[newRow][newCol] == '#') {
                continue;
            }
            if (node.contains(board[newRow][newCol])) {
                backtrace(node, board, newRow, newCol, l);
            }
        }
        board[i][j] = ch;
        return;
    }
}

class TrieNode {
    private TrieNode[] links;
    private final int R = 26;
    private String letter;

    public TrieNode(){
        links = new TrieNode[R];
    }

    public TrieNode get(char c) {
        return links[c - 'a'];
    }

    public void put(char c, TrieNode root) {
        links[c - 'a'] = root;
    }

    public boolean contains(char c) {
        return links[c - 'a'] != null;
    }

    public void setLetter(String s) {
        letter = s;
    }

    public String getLetter() {
        return letter;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
