package com.example.filesystem.controller;

import com.example.filesystem.annotations.ResponseResult;
import com.example.filesystem.model.dto.ReasonDTO;
import com.example.filesystem.model.vo.OperationUser;
import com.example.filesystem.model.dto.PageDTO;
import com.example.filesystem.model.po.Operation;
import com.example.filesystem.model.vo.SelectUser;
import com.example.filesystem.service.OperationService;
import com.example.filesystem.util.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @ClassName OperationController
 * @Description TODO
 * @Author zq
 * Date 2020/1/14 11:08
 * Version 1.0
 */
@ResponseResult
@RequestMapping("/operation")
@RestController
@Api(tags = "operation相关接口 @ 郑权", description = "OperationController")
public class OperationController {
    private static final Logger logger = LoggerFactory.getLogger(OperationController.class);
    @Resource
    private OperationService operationService;

    @GetMapping("/operationList")
    @ResponseBody
    @ApiOperation("查询操作表全部信息(list)")
    public List<Operation> operationList() {
        List<Operation> list = operationService.selectAllOperation();
        return list;
    }


    @GetMapping("/getAllOperation")
    @ResponseBody
    @ApiOperation("查询操作信息")
    public List<ReasonDTO> getAllOperation(Date timeStart, Date timeEnd) {
        List<ReasonDTO> list = operationService.getAllOperation(timeStart,timeEnd);
        return list;
    }

    @PostMapping("/selectReasonDTO")
    @ResponseBody
    @ApiOperation("返回所有审批结果")
    public PageDTO selectReasonDTO(@RequestBody SelectUser selectUser) {
        PageDTO pageDTO = operationService.selectReasonDTO(selectUser.getContent(),selectUser.getPageNum(),selectUser.getPageSize(),selectUser.getTimeStart(),selectUser.getTimeEnd());
        return pageDTO;
    }

    @PostMapping("deleteOperation")
    @ResponseBody
    @ApiOperation("批量删除数据")
    public String deleteOperation(@RequestBody List<String> strings) {
        if (strings != null && strings.size() > 0) {
            for (String s : strings){
                if(s.trim().equals("") || s == null){
                    logger.error("要删除的操作数据不存在:" + s + "--->>>" + DateUtil.dataToString(new Date()));
                }else {
                    operationService.deleteOperation(s);
                }
            }
            logger.info("删除数据成功:" + DateUtil.dataToString(new Date()));
            return "SUCCESS";
        }
        logger.error("删除数据失败:" + DateUtil.dataToString(new Date()));
        return "FAIL";
    }
}
