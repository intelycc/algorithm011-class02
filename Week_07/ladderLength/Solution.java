package com.company.others.ladderLength;

//给定两个单词（beginWord 和 endWord）和一个字典，找到从 beginWord 到 endWord 的最短转换序列的长度。转换需遵循如下规则：
//
//
//
// 每次转换只能改变一个字母。
// 转换过程中的中间单词必须是字典中的单词。
//
//
// 说明:
//
//
// 如果不存在这样的转换序列，返回 0。
// 所有单词具有相同的长度。
// 所有单词只由小写字母组成。
// 字典中不存在重复的单词。
// 你可以假设 beginWord 和 endWord 是非空的，且二者不相同。
//
//
// 示例 1:
//
// 输入:
//beginWord = "hit",
//endWord = "cog",
//wordList = ["hot","dot","dog","lot","log","cog"]
//
//输出: 5
//
//解释: 一个最短转换序列是 "hit" -> "hot" -> "dot" -> "dog" -> "cog",
//     返回它的长度 5。
//
//
// 示例 2:
//
// 输入:
//beginWord = "hit"
//endWord = "cog"
//wordList = ["hot","dot","dog","lot","log"]
//
//输出: 0
//
//解释: endWord "cog" 不在字典中，所以无法进行转换。
// Related Topics 广度优先搜索
// 👍 372 👎 0


import java.util.*;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> set = new HashSet<>(wordList);//用于存放单词字典，方便快速查询
        set.remove(beginWord);//需要剔除开始单词

        Set<String> visited = new HashSet<>();//图的BFS中用于判断是否已经访问过
        visited.add(beginWord);

        LinkedList<String> queue = new LinkedList<>();//BFS辅助
        queue.offer(beginWord);

        int wordlen = beginWord.length();//所有单词长度都是一样的，记录一下
        int count = 1;//结果，算上了beginword
        /*广度优先算法*/
        while (!queue.isEmpty()) {
            int qSize = queue.size();
            for (int i = 0; i < qSize; i++) {
                String word = queue.poll();
                if (word.equals(endWord)) return count;
                /*以下开始查出字典中符合条件的单词，即图能连接的点，加入队列*/
                char[] ca = word.toCharArray();
                /*每个字符进行26个字母替换，然后在字典set中找是否有匹配的*/
                for (int j = 0; j < wordlen; j++) {
                    char originalChar = ca[j];
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (originalChar == c) continue;
                        ca[j] = c;
                        String newWord = String.valueOf(ca);
                        if (set.contains(newWord) && !visited.contains(newWord)) {
                            queue.offer(newWord);
                            visited.add(newWord);
                        }
                    }
                    ca[j] = originalChar;
                }
            }
            count++;
        }
        return 0;
    }


}
//leetcode submit region end(Prohibit modification and deletion)
