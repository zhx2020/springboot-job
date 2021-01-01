package com.zwl.job.utils;

import java.util.Arrays;

public class MyUtil {

    public static int[] parseSalary(String str) {
        int[] nums = new int[2];
        if (str == null || str.equals("")) {
            return nums;
        }
        if (str.contains("天")) {
            int i = str.indexOf("元");
            nums[0] = Integer.parseInt(str.substring(0, i)) * 30;
            nums[1] = Integer.parseInt(str.substring(0, i)) * 20;
            return nums;
        }
        boolean isYear= str.contains("年");
        boolean isWan = str.contains("万");
        int i = str.indexOf("/");
        str = str.substring(0, i - 1);
        String[] arr = str.split("-");
        double min = Double.parseDouble(arr[0]) * 1000;
        double max = Double.parseDouble(arr[1]) * 1000;
        if (isYear) {
            min = min / 12;
            max = max / 12;
        }
        if (isWan) {
            min = min * 10;
            max = max * 10;
        }
        nums[0] = (int) min;
        nums[1] = (int) max;
        return nums;
    }

    public static void main(String[] args) {
        String salary = "30-60万/年";
        System.out.println(Arrays.toString(MyUtil.parseSalary(salary)));
    }
}
