package com.example.filesystem.service.impl;

import com.example.filesystem.dao.mapper.RoleMapper;
import com.example.filesystem.model.po.Role;
import com.example.filesystem.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName RoleServiceImpl
 * @Description TODO
 * @Author zq
 * Date 2020/1/14 10:44
 * Version 1.0
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleMapper roleMapper;
    @Override
    public List<Role> selectAllUser() {
        List<Role> list = roleMapper.selectAllRole();
        return list;
    }
}
