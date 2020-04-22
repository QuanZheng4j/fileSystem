package com.example.filesystem.model.po;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName Department
 * @Description 部门分类表
 * @Author zq
 * Date 2020/1/14 9:55
 * Version 1.0
 */
@Getter
@Setter
@ToString
public class Department implements Serializable {
    /**
     * id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 部门分类.
     */
    private String departmentType;

    private Date createTime;

    private Date updateTime;
}
