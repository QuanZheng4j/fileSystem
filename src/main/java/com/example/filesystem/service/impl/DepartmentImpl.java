package com.example.filesystem.service.impl;

import com.example.filesystem.controller.UserController;
import com.example.filesystem.dao.mapper.DepartmentMapper;
import com.example.filesystem.model.dto.DepartmentDTO;
import com.example.filesystem.model.dto.PageDTO;
import com.example.filesystem.model.po.Department;
import com.example.filesystem.service.DepartmentService;
import com.example.filesystem.util.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @ClassName DepartmentImpl
 * @Description TODO
 * @Author zq
 * Date 2020/1/14 11:17
 * Version 1.0
 */
@Service
public class DepartmentImpl implements DepartmentService {
    private static final Logger loggger = LoggerFactory.getLogger(DepartmentImpl.class);
    @Resource
    private DepartmentMapper departmentMapper;

    @Override
    public List<Department> selectAllDepartment() {
        List<Department> list = departmentMapper.selectAllDepartment();
        return list;
    }

    @Override
    public int insertDepartment(Department department) {

        List<Department> list = departmentMapper.selectAllDepartment();
        for (Department findType : list) {
            if (findType.getDepartmentType().equals(department.getDepartmentType())) {
                return -1;
            }
        }
        department.setCreateTime(new Date());
        departmentMapper.insertDepartment(department);
        return 1;
    }

    @Override
    public int updateDepartment(Department department) {
        List<Department> list = departmentMapper.selectAllDepartment();
        for (Department findType : list) {
            if (findType.getDepartmentType().equals(department.getDepartmentType())) {
                return -1;
            }
        }
        department.setUpdateTime(new Date());
        departmentMapper.updateDepartment(department);
        return 1;
    }

    @Override
    public int deleteDepartment(String id) {
        if (id != "" && id != null) {
            List<Department> list = departmentMapper.selectAllDepartment();
            for (Department findId : list) {
                if(findId.getId() == Integer.parseInt(id)){
                    departmentMapper.deleteDepartment(Integer.parseInt(id));
                    loggger.info("删除部门:" + findId.getDepartmentType() + "--->>>Time:" + DateUtil.dataToString(new Date()));
                    return 1;
                }
            }
        }
        return -1;
    }

    @Override
    public PageDTO selectDepartmentDTO(String content,String pageNum,String pageSize) {
        PageHelper.startPage(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
        List<DepartmentDTO> list = departmentMapper.selectDepartmentDTO(content);
        PageInfo pageInfo = new PageInfo(list);
        PageDTO pageDTO = new PageDTO();
        pageDTO.setPages(pageInfo.getPages());
        pageDTO.setTotal(pageInfo.getTotal());
        pageDTO.setResult(pageInfo.getList());
        return pageDTO;

    }
}
