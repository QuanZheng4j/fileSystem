package com.example.filesystem.model.dto;

import lombok.Data;

/**
 * @ClassName ReasonDTO
 * @Description 页面所有操作返回DTO
 * @Author zq
 * Date 2020/2/23 20:57
 * Version 1.0
 */
@Data
public class ReasonDTO {
    private String id;
    private String name;
    private String fileName;
    private String fileSize;
    private String reason;
    private String approvalName;
    private String approvalResult;
    private String approvalTime;
}
