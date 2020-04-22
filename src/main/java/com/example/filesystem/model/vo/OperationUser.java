package com.example.filesystem.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName OperationUser
 * @Description 用户操作C#返回VO
 * @Author zq
 * Date 2020/1/18 16:09
 * Version 1.0
 */
@Data
public class OperationUser {
    private String id;
    private String Name;
    private String department;
    private String fileName;
    private String fileSize;
    private String reason;
    private String approvalName;
    private String approvalIP;
}
