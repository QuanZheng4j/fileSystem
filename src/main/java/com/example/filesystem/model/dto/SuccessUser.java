package com.example.filesystem.model.dto;

import lombok.Data;

/**
 * @ClassName SuccessUser
 * @Description 登录成功返回用户信息DTO
 * @Author zq
 * Date 2020/1/18 11:18
 * Version 1.0
 */
@Data
public class SuccessUser {
    private String id;
    private String name;
    private String departmentType;

}
