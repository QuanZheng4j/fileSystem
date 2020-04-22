package com.example.filesystem.model.po;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @ClassName Role
 * @Description 权限表
 * @Author zq
 * Date 2020/1/14 9:56
 * Version 1.0
 */
@Getter
@Setter
@ToString
public class Role {
    /**
     * id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 权限类型.
     */
    private String roleType;

    private Date createTime;

    private Date updateTime;
}
