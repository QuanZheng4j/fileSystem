package com.example.filesystem.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName SelectUser
 * @Description 分页查询请求VO
 * @Author zq
 * Date 2020/2/23 17:13
 * Version 1.0
 */
@Data
public class SelectUser {
    private String pageNum;
    private String pageSize;
    private String content;
    private Date timeStart;
    private Date timeEnd;
}
