package com.company.others.findAnagrams;

//给定一个字符串 s 和一个非空字符串 p，找到 s 中所有是 p 的字母异位词的子串，返回这些子串的起始索引。
//
// 字符串只包含小写英文字母，并且字符串 s 和 p 的长度都不超过 20100。
//
// 说明：
//
//
// 字母异位词指字母相同，但排列不同的字符串。
// 不考虑答案输出的顺序。
//
//
// 示例 1:
//
//
//输入:
//s: "cbaebabacd" p: "abc"
//
//输出:
//[0, 6]
//
//解释:
//起始索引等于 0 的子串是 "cba", 它是 "abc" 的字母异位词。
//起始索引等于 6 的子串是 "bac", 它是 "abc" 的字母异位词。
//
//
// 示例 2:
//
//
//输入:
//s: "abab" p: "ab"
//
//输出:
//[0, 1, 2]
//
//解释:
//起始索引等于 0 的子串是 "ab", 它是 "ab" 的字母异位词。
//起始索引等于 1 的子串是 "ba", 它是 "ab" 的字母异位词。
//起始索引等于 2 的子串是 "ab", 它是 "ab" 的字母异位词。
//
// Related Topics 哈希表
// 👍 351 👎 0


import java.util.ArrayList;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> res = new ArrayList<>();
        if (s == null || p == null || s.length() < p.length()) return res;
        int pLen = p.length();
        int[] pCounter = new int[26];
        int[] sCounter = new int[26];
        for (int i = 0; i < pLen; i++) pCounter[p.charAt(i) - 'a']++;//统计p字母出现次数
        for (int i = 0; i < pLen; i++) sCounter[s.charAt(i) - 'a']++;//统计s的前pLen个字母出现次数
        int left = 0, right = pLen;
        //[left,right)是个左闭右开的区间
        while (right < s.length()) {
            if(isAnagrams(sCounter, pCounter)) res.add(left);
            //收缩左侧，同时左侧字母的频次减1
            sCounter[s.charAt(left) - 'a']--;
            left++;
            //扩大右侧，同时右侧字母的频次加1
            sCounter[s.charAt(right) - 'a']++;
            right++;
        }
        //因为左闭右开的原因，最后一个要判断，否则会漏掉
        if (isAnagrams(sCounter, pCounter)) res.add(left);
        return res;
    }

    private boolean isAnagrams(int[] sArr, int[] pArr) {
        for (int i = 0; i < pArr.length; i++) {
            if (sArr[i] != pArr[i]) return false;
        }
        return true;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

