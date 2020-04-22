package com.example.filesystem.util;

import java.util.UUID;

/**
 * @ClassName UUIDUtil
 * @Description 生成UUID
 * @Author zq
 * Date 2020/2/5 11:03
 * Version 1.0
 */
public class UUIDUtil {
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
