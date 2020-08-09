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
class Solution2 {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        if (beginWord == null || endWord == null) return 0;
        Set<String> wordSet = new HashSet<>(wordList);
        if (!wordSet.contains(endWord)) return 0;
        Set<String> beginQueue = new HashSet<>();
        Set<String> endQueue = new HashSet<>();
        Set<String> visited = new HashSet<>();
        visited.add(beginWord);
        beginQueue.add(beginWord);
        endQueue.add(endWord);
        int count = 1;//beginword要算在内
        while (!beginQueue.isEmpty() && !endQueue.isEmpty()) {
            //优先选择小的进行扩散，可以减少复杂度
            if (beginQueue.size() > endQueue.size()) {
                Set<String> temp = endQueue;
                endQueue = beginQueue;
                beginQueue = temp;
            }
            //tempQueue会成为新的beginQueue
            Set<String> tempQueue = new HashSet<>();
            //遍历每层
            for (String word : beginQueue) {
                char[] ca = word.toCharArray();
                //依次对每个字符进行替换
                for (int i = 0; i < ca.length; i++) {
                    char original = ca[i];
                    //每个字符依次替换成a-z
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == original) continue;
                        ca[i] = c;
                        String newWord = String.valueOf(ca);
                        if (wordSet.contains(newWord)) {
                            //begin和end发生交汇，则可以返回，记住要加上本层的次数
                            if (endQueue.contains(newWord)) {
                                return count + 1; //因为都是在遍历完一层后才++的，所以此处提前返回也要+1
                            }
                            //如果替换后的单词在wordlist中，且没有被访问过，则加入队列
                            if(!visited.contains(newWord)){
                                tempQueue.add(newWord);
                                visited.add(newWord);
                            }
                        }
                        //还原
                        ca[i] = original;
                    }
                }
            }
            //遍历完一层则计数+1
            count++;
            beginQueue = tempQueue;
        }
        return 0;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

