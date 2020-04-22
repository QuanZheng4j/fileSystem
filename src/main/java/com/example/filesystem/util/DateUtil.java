package com.example.filesystem.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName DateUtil
 * @Description TODO
 * @Author zq
 * Date 2020/2/18 11:36
 * Version 1.0
 */
public class DateUtil {
    public static String dataToString(Date date){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }
    public static String dataToUrl(Date date){
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public static String dataToFolder(Date date){
        return new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(date);
    }
}
