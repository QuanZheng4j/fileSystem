package com.example.filesystem.controller;

import com.example.filesystem.annotations.ResponseResult;
import com.example.filesystem.model.dto.Approval;
import com.example.filesystem.model.vo.LoginUser;
import com.example.filesystem.model.dto.PageDTO;
import com.example.filesystem.model.dto.SuccessUser;
import com.example.filesystem.model.po.User;
import com.example.filesystem.model.vo.ResetPwdUser;
import com.example.filesystem.model.vo.SelectUser;
import com.example.filesystem.model.vo.UserID;
import com.example.filesystem.service.UserService;
import com.example.filesystem.util.DateUtil;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author zq
 * Date 2020/1/14 10:35
 * Version 1.0
 */
@ResponseResult
@RequestMapping("/user")
@RestController
@Api(tags = "user相关接口 @ 郑权", description = "UserController")
public class UserController {
    private static final Logger loggger = LoggerFactory.getLogger(UserController.class);
    @Resource
    private UserService userService;

    @PostMapping("/userList")
    @ResponseBody
    @ApiOperation("查询全部信息")
    public PageDTO userList(@RequestBody SelectUser selectUser) {
        PageDTO pageDTO = userService.selectUserDTO(selectUser.getContent(),selectUser.getPageNum(),selectUser.getPageSize());
        return pageDTO;
    }

    @PostMapping("/retLoginCode")
    @ResponseBody
    @ApiOperation(value = "查询是否存在用户，返回状态", notes = "(0为账号不存在,1为允许登录,2为密码不正确)")
    public int retLoginCode(@RequestBody LoginUser loginUser) {

        List<Approval> approvals = userService.selectAllApproval();
        for (Approval approval: approvals){
            if(approval.getUserName().equals(loginUser.getUserName())){
                return 3;
            }
        }

        List<User> list = userService.selectAllUser();
        for (User user : list) {
            if (user.getUserName().equals(loginUser.getUserName())) {
                if (user.getPassWord().equals(loginUser.getPwd())) {
                    return 1;
                } else {
                    return 2;
                }
            }
        }
        return 0;
    }

    @PostMapping("/selectSuccessUser")
    @ResponseBody
    @ApiOperation("根据账号返回用户信息")
    public SuccessUser selectSuccessUser(@RequestBody LoginUser loginUser) {
        SuccessUser successUser = userService.selectSuccessUser(loginUser.getUserName(), loginUser.getPwd());
        return successUser;
    }

    @GetMapping("/approvalList")
    @ResponseBody
    @ApiOperation("返回所有审批人信息")
    public List<Approval> selectAllApproval() {
        List<Approval> list = userService.selectAllApproval();
        return list;
    }

    @PostMapping("/insertNewUser")
    @ResponseBody
    @ApiOperation("添加新员工")
    public int insertNewUser(@RequestBody User user) {
        return userService.insertNewUser(user);
    }

    @PostMapping("/updateUser")
    @ResponseBody
    @ApiOperation("修改员工信息(只能修改员工部门，员工姓名，用户密码，用户权限)")
    public int updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @PostMapping("/updateUserPassWord")
    @ResponseBody
    @ApiOperation("提供给员工修改密码的方法")
    public int updateUserPassWord(@RequestBody ResetPwdUser user)
    {
        loggger.info(user.getUserName() + "修改密码成功:" + DateUtil.dataToString(new Date()));
        return userService.updateUserPassWord(user);
    }

    @PostMapping("/deleteUser")
    @ResponseBody
    @ApiOperation("删除员工")
    public int deleteUser(@RequestBody UserID id) {
        if (id.getId() != "" && id.getId() != null) {
            return userService.deleteUser(id.getId());
        }
        return -1;
    }

}
