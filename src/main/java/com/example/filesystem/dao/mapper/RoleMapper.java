package com.example.filesystem.dao.mapper;

import com.example.filesystem.model.po.Role;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @ClassName RoleMapper
 * @Description TODO
 * @Author zq
 * Date 2020/1/14 10:44
 * Version 1.0
 */
public interface RoleMapper extends Mapper<Role> {
    /**
     * @author zq
     * 查询全部信息
     */
    List<Role> selectAllRole();
}
