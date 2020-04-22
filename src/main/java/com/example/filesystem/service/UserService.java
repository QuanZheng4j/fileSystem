package com.example.filesystem.service;

import com.example.filesystem.model.dto.Approval;
import com.example.filesystem.model.dto.PageDTO;
import com.example.filesystem.model.dto.SuccessUser;
import com.example.filesystem.model.po.User;
import com.example.filesystem.model.vo.ResetPwdUser;
import com.example.filesystem.model.vo.SelectUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName UserService
 * @Description TODO
 * @Author zq
 * Date 2020/1/14 10:29
 * Version 1.0
 */
public interface UserService {

    /**
     * @author zq
     * 查询全部信息
     */
    List<User> selectAllUser();

    /**
     * @author zq
     * 查询全部信息
     */
    PageDTO selectUserDTO(@Param("content") String content,@Param("pageNum") String pageNum,@Param("pageSize")String pageSize);
    /**
     * 根据账号返回用户信息
     */
    SuccessUser selectSuccessUser(@Param("userName") String userName , @Param("password") String password);

    /**
     * 返回所有审批人信息
     */
    List<Approval> selectAllApproval();

    /**
     * 添加一个新用户
     * @param user
     */
    int insertNewUser(User user);

    /**
     * 修改单个用户信息
     * @param user
     * @return
     */
    int updateUser(User user);

    /**
     * 提供给用户修改密码的方法
     * @param user
     * @return
     */
    int updateUserPassWord(ResetPwdUser user);

    /**
     * 删除用户.
     * @param id
     * @return
     */
    int deleteUser(@Param("id") String id);
}
