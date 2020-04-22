package com.example.filesystem.service;

import com.example.filesystem.model.dto.DepartmentDTO;
import com.example.filesystem.model.dto.PageDTO;
import com.example.filesystem.model.po.Department;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName DepartmentService
 * @Description TODO
 * @Author zq
 * Date 2020/1/14 11:17
 * Version 1.0
 */
public interface DepartmentService {
    /**
     * @author zq
     * 查询全部信息
     */
    List<Department> selectAllDepartment();


    /**
     * 添加新部门.
     * @return
     */
    int insertDepartment(Department department);

    /**
     * 修改部门名称
     * @return
     */
    int updateDepartment(Department department);

    /**
     * 删除一个部门
     * @return
     */
    int deleteDepartment(@Param("id") String id);


    /**
     * 部门对象用于操作.
     */
    PageDTO selectDepartmentDTO(@Param("content") String content,@Param("pageNum") String pageNum,@Param("pageSize")String pageSize);
}
