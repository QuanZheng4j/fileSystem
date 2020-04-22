package com.example.filesystem.model.dto;

import lombok.Data;

/**
 * @ClassName Users
 * @Description 用户管理返回DTO
 * @Author zq
 * Date 2020/2/23 16:38
 * Version 1.0
 */
@Data
public class Users {
    private String id;
    private String name;
    private String department;
    private String role;
    private String userName;
    private String password;
}
