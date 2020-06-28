package com.company.list.removeDuplicates;

public class Solution {
    public static int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int i = 0;
        for (int j = 1; j < nums.length; j++){
            if (nums[j] != nums[i]){
                nums[++i] = nums[j];
            }
        }
        return i + 1;
    }
}
