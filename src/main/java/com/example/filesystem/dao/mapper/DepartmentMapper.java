package com.example.filesystem.dao.mapper;

import com.example.filesystem.model.dto.DepartmentDTO;
import com.example.filesystem.model.po.Department;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @ClassName DepartmentMapper
 * @Description TODO
 * @Author zq
 * Date 2020/1/14 11:16
 * Version 1.0
 */
public interface DepartmentMapper extends Mapper<Department> {
    /**
     * @author zq
     * 查询全部信息
     */
    List<Department> selectAllDepartment();

    /**
     * 添加新部门.
     *
     * @return
     */
    int insertDepartment(Department department);

    /**
     * 修改部门名称
     *
     * @return
     */
    int updateDepartment(Department department);

    /**
     * 删除一个部门
     *
     * @return
     */
    int deleteDepartment(@Param("id") Integer id);

    /**
     * 部门对象用于操作.
     */
    List<DepartmentDTO> selectDepartmentDTO(@Param("content") String content);
}
