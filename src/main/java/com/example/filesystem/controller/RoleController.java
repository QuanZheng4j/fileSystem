package com.example.filesystem.controller;

import com.example.filesystem.annotations.ResponseResult;
import com.example.filesystem.model.po.Role;
import com.example.filesystem.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName RoleController
 * @Description TODO
 * @Author zq
 * Date 2020/1/14 10:46
 * Version 1.0
 */
@ResponseResult
@RequestMapping("/role")
@RestController
@Api(tags = "role相关接口 @ 郑权", description = "RoleController")
public class RoleController {
    @Resource
    private RoleService roleService;

    @GetMapping("/roleList")
    @ResponseBody
    @ApiOperation("查询全部信息")
    public List<Role> roleList() {
        List<Role> list = roleService.selectAllUser();
        return list;
    }
}
