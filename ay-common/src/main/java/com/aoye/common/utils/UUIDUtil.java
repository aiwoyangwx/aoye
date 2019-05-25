package com.aoye.common.utils;

import java.util.UUID;

/**
* @Description:    UUID工具类
* @Author:         Alex
* @CreateDate:     2019/5/6
*/
public class UUIDUtil {
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static void main(String[] args) {
        System.out.println(getUUID());
    }
}
