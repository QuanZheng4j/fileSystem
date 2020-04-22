package com.example.filesystem.model.po;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @ClassName User
 * @Description 用户信息表
 * @Author zq
 * Date 2020/1/14 9:56
 * Version 1.0
 */
@Getter
@Setter
@ToString
public class User {
    /**
     * id.
     */
    @Id
    private String id;

    /**
     * 权限Id.
     */
    private int roleId;

    /**
     * 部门Id.
     */
    private int departmentId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 账号(审批账号使用IP地址来表示账号)
     */
    private String userName;

    /**
     * 密码
     */
    private String passWord;

    private Date createTime;

    private Date updateTime;
}
