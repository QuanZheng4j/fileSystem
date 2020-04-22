package com.example.filesystem.dao.mapper;

import com.example.filesystem.model.vo.OperationUser;
import com.example.filesystem.model.dto.ReasonDTO;
import com.example.filesystem.model.po.Operation;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

/**
 * @ClassName OperationMapper
 * @Description TODO
 * @Author zq
 * Date 2020/1/14 11:04
 * Version 1.0
 */
public interface OperationMapper extends Mapper<Operation> {

    /**
     * @author zq
     * 查询全部信息
     */
    List<Operation> selectAllOperation();

    /**
     * 查询所有操作信息.
     *
     * @return 查询操作信息
     */
    List<ReasonDTO> getAllOperation(@Param("timeStart") Date timeStart,@Param("timeEnd") Date timeEnd);

    /**
     * 删除数据.
     */
    void deleteOperation(String id);

    /**
     * 添加一条操作记录.
     */
    int addOperation(Operation operation);


    /**
     * @author zq
     * 返回所有审批结果
     */
    List<ReasonDTO> selectReasonDTO(@Param("content") String content, @Param("timeStart") Date timeStart,@Param("timeEnd") Date timeEnd);
}
