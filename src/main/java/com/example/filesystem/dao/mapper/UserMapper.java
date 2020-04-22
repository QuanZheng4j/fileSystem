package com.example.filesystem.dao.mapper;

import com.example.filesystem.model.dto.Approval;
import com.example.filesystem.model.dto.SuccessUser;
import com.example.filesystem.model.dto.Users;
import com.example.filesystem.model.po.User;
import com.example.filesystem.model.vo.SelectUser;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @ClassName UserMapper
 * @Description TODO
 * @Author zq
 * Date 2020/1/14 10:28
 * Version 1.0
 */
public interface UserMapper extends Mapper<User> {
    /**
     * @author zq
     * 查询全部信息
     */
    List<User> selectAllUser();

    /**
     * @author zq
     * 查询全部信息
     */
    List<Users> selectUserDTO(@Param("content") String content);

    /**
     * 根据账号返回用户信息
     */
    SuccessUser selectSuccessUser(@Param("userName") String userName, @Param("password") String password);

    /**
     * 返回所有审批人信息
     */
    List<Approval> selectAllApproval();

    /**
     * 添加一个新用户
     *
     * @param user
     * @return
     */
    int insertNewUser(User user);

    /**
     * 修改单个用户信息
     *
     * @param user
     * @return
     */
    int updateUser(User user);

    /**
     * 提供给用户修改密码的方法
     *
     * @param user
     * @return
     */
    int updateUserPassWord(User user);

    /**
     * 删除用户.
     * @param id
     * @return
     */
    int deleteUser(@Param("id") String id);
}
