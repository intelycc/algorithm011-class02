package com.company.list.moveZeroes;

public class Solution {
    public void moveZeroes(int[] nums) {
        for(int i = 0; i < nums.length; i++){
            if(nums[i] == 0){
                continue;
            }
            int zeroindex = -1;
            for(int j = i - 1; j >= 0; j--){
                if(nums[j] != 0)
                    break;
                zeroindex = j;
            }
            if(zeroindex != -1){
                int swap;
                swap = nums[i];
                nums[i] = nums[zeroindex];
                nums[zeroindex] = swap;
            }
        }
    }
}
