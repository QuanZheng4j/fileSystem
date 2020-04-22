package com.example.filesystem.service;

import com.example.filesystem.model.po.Role;

import java.util.List;

/**
 * @ClassName RoleService
 * @Description TODO
 * @Author zq
 * Date 2020/1/14 10:44
 * Version 1.0
 */
public interface RoleService {
    /**
     * @author zq
     * 查询全部信息
     */
    List<Role> selectAllUser();
}
