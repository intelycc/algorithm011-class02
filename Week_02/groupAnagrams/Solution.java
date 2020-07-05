package com.company.map.groupAnagrams;

//给定一个字符串数组，将字母异位词组合在一起。字母异位词指字母相同，但排列不同的字符串。
//
// 示例:
//
// 输入: ["eat", "tea", "tan", "ate", "nat", "bat"]
//输出:
//[
//  ["ate","eat","tea"],
//  ["nat","tan"],
//  ["bat"]
//]
//
// 说明：
//
//
// 所有输入均为小写字母。
// 不考虑答案输出的顺序。
//
// Related Topics 哈希表 字符串


import java.util.*;

//leetcode submit region begin(Prohibit modification and deletion)
/*
* 注：一定要加一个分割符号，如“#”，否则假设以下情况：
* 2 1 11
* 2 11 1
* 这两个组合成字符串后是相等的
* */
class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        if (strs == null || strs.length == 0) return new ArrayList<>();
        Map<String, List<String>> m = new HashMap<>();
        int[] counter = new int[26];
        for (String str : strs) {
            Arrays.fill(counter, 0);
            char[] ca = str.toCharArray();
            for (char c : ca) counter[c - 'a']++;

            StringBuilder sb = new StringBuilder();
            for (int count : counter) {
                sb.append("#");
                sb.append(count);
            }
            String key = sb.toString();
            if (!m.containsKey(key)) m.put(key, new ArrayList<>());
            m.get(key).add(str);
        }
        return new ArrayList<>(m.values());
    }
}
//leetcode submit region end(Prohibit modification and deletion)
