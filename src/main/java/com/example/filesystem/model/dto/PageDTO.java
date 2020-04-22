package com.example.filesystem.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author: zq.
 * @date: 2018/9/17 16:05
 * @description: 分页返回数据封装
 */
@ToString
@Getter
@Setter
public class PageDTO<T> {

    /** 总页数.*/
    private Integer pages;

    /** 数据总条数.*/
    private Long total;

    /** 分页数据.*/
    private List<T> result;

    public PageDTO(Integer pages, Long total, List<T> result) {
        this.pages = pages;
        this.total = total;
        this.result = result;
    }

    public PageDTO() {
    }
}
