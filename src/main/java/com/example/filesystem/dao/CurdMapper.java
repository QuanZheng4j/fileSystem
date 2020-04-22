package com.example.filesystem.dao;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author: zq
 * @Description: 基础Mapper 包括基础CURD方法
 * @date 2018/8/17 14:53
 */
@EnableAutoConfiguration
public interface CurdMapper<T> extends Mapper<T>, MySqlMapper<T> {

}
