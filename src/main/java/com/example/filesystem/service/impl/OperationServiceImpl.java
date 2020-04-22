package com.example.filesystem.service.impl;

import com.example.filesystem.dao.mapper.OperationMapper;
import com.example.filesystem.model.vo.OperationUser;
import com.example.filesystem.model.dto.PageDTO;
import com.example.filesystem.model.dto.ReasonDTO;
import com.example.filesystem.model.po.Operation;
import com.example.filesystem.service.OperationService;
import com.example.filesystem.util.UUIDUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @ClassName OperationServiceImpl
 * @Description TODO
 * @Author zq
 * Date 2020/1/14 11:07
 * Version 1.0
 */
@Service
public class OperationServiceImpl implements OperationService {
    private static final Logger loggger = LoggerFactory.getLogger(OperationServiceImpl.class);

    @Resource
    private OperationMapper operationMapper;

    @Override
    public List<Operation> selectAllOperation() {
        List<Operation> list = operationMapper.selectAllOperation();
        return list;
    }

    @Override
    public List<ReasonDTO> getAllOperation(Date timeStart, Date timeEnd) {
        List<ReasonDTO> list = operationMapper.getAllOperation(timeStart,timeEnd);
        return list;
    }

    @Override
    public void deleteOperation(String id) {
        //此处传递进来的值如果为非ID也会执行成功，会造成空删除执行成功
        List<Operation> list = operationMapper.selectAllOperation();
        for (Operation operation : list) {
            if (operation.getId().equals(id)) {
                operationMapper.deleteOperation(id);
                loggger.info("删除操作表内容:" + operation);
            }
        }
    }

    @Override
    public int addOperation(Operation operation) {
        operation.setId(UUIDUtil.getUUID());
        operation.setCreateTime(new Date());
        operationMapper.addOperation(operation);
        return 1;
    }

    @Override
    public PageDTO selectReasonDTO(String content, String pageNum, String pageSize, Date timeStart, Date timeEnd) {
        PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        List<ReasonDTO> list = operationMapper.selectReasonDTO(content, timeStart, timeEnd);
        PageInfo pageInfo = new PageInfo(list);
        PageDTO pageDTO = new PageDTO();
        pageDTO.setPages(pageInfo.getPages());
        pageDTO.setTotal(pageInfo.getTotal());
        pageDTO.setResult(pageInfo.getList());
        return pageDTO;
    }
}
