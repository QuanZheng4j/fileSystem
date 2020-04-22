package com.example.filesystem.controller;

import com.example.filesystem.annotations.ResponseResult;
import com.example.filesystem.model.dto.PageDTO;
import com.example.filesystem.model.po.Department;
import com.example.filesystem.model.vo.SelectUser;
import com.example.filesystem.model.vo.UserID;
import com.example.filesystem.service.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName DepartmentController
 * @Description TODO
 * @Author zq
 * Date 2020/1/14 11:18
 * Version 1.0
 */
@ResponseResult
@RequestMapping("/department")
@RestController
@Api(tags = "department相关接口 @ 郑权", description = "DepartmentController")
public class DepartmentController {
    @Resource
    private DepartmentService departmentService;

    @GetMapping("/operationList")
    @ResponseBody
    @ApiOperation("查询全部信息")
    public List<Department> operationList() {
        List<Department> list = departmentService.selectAllDepartment();
        return list;
    }

    @PostMapping("insertDepartment")
    @ResponseBody
    @ApiOperation("添加一个新部门")
    public int insertDepartment(@RequestBody Department department){
       return departmentService.insertDepartment(department);
    }

    @PostMapping("updateDepartment")
    @ResponseBody
    @ApiOperation("修改部门名称")
    public int updateDepartment(@RequestBody Department department){
        return departmentService.updateDepartment(department);
    }

    @PostMapping("deleteDepartment")
    @ResponseBody
    @ApiOperation("删除一个部门")
    public int deleteDepartment(@RequestBody UserID id){
        return departmentService.deleteDepartment(id.getId());
    }

    @PostMapping("/selectDepartmentDTO")
    @ResponseBody
    @ApiOperation("查询全部信息")
    public PageDTO selectDepartmentDTO(@RequestBody SelectUser selectUser) {
        PageDTO pageDTO = departmentService.selectDepartmentDTO(selectUser.getContent(),selectUser.getPageNum(),selectUser.getPageSize());
        return pageDTO;
    }
}
