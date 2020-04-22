package com.example.filesystem.service.impl;

import com.example.filesystem.dao.mapper.UserMapper;
import com.example.filesystem.model.dto.Approval;
import com.example.filesystem.model.dto.PageDTO;
import com.example.filesystem.model.dto.SuccessUser;
import com.example.filesystem.model.dto.Users;
import com.example.filesystem.model.po.User;
import com.example.filesystem.model.vo.ResetPwdUser;
import com.example.filesystem.model.vo.SelectUser;
import com.example.filesystem.service.UserService;
import com.example.filesystem.util.DateUtil;
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
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author zq
 * Date 2020/1/14 10:29
 * Version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger loggger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private UserMapper userMapper;

    @Override
    public PageDTO selectUserDTO(String content, String pageNum, String pageSize) {
        PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        List<Users> list = userMapper.selectUserDTO(content);
        PageInfo pageInfo = new PageInfo(list);
        PageDTO pageDTO = new PageDTO();
        pageDTO.setPages(pageInfo.getPages());
        pageDTO.setTotal(pageInfo.getTotal());
        pageDTO.setResult(pageInfo.getList());
        return pageDTO;
    }

    @Override
    public List<User> selectAllUser() {
        List<User> list = userMapper.selectAllUser();
        return list;
    }

    @Override
    public SuccessUser selectSuccessUser(String userName, String password) {
        SuccessUser successUser = userMapper.selectSuccessUser(userName, password);
        return successUser;
    }

    @Override
    public List<Approval> selectAllApproval() {
        List<Approval> list = userMapper.selectAllApproval();
        return list;
    }

    @Override
    public int insertNewUser(User user) {
        List<User> users = userMapper.selectAllUser();
        for (User userFind : users) {
            if (userFind.getUserName().equals(user.getUserName())) {
                return -1;
            }
        }
        user.setId(UUIDUtil.getUUID());
        user.setCreateTime(new Date());
        userMapper.insertNewUser(user);
        return 1;
    }

    @Override
    public int updateUser(User user) {
        List<User> users = userMapper.selectAllUser();
        for (User findId : users) {
            if (findId.getId().equals(user.getId())) {
                user.setUpdateTime(new Date());
                userMapper.updateUser(user);
                return 1;
            }
        }
        return -1;
    }

    @Override
    public int updateUserPassWord(ResetPwdUser user) {
        if (user != null) {
            List<User> users = userMapper.selectAllUser();
            for (User find : users) {
                if ((find.getUserName().equals(user.getUserName())) && find.getPassWord().equals(user.getPassword())) {
                    find.setPassWord(user.getNewPassword());
                    find.setUpdateTime(new Date());
                    userMapper.updateUserPassWord(find);
                    return 1;
                }
            }
        }
        return -1;
    }

    @Override
    public int deleteUser(String id) {
        if (id != "" && id != null) {
            List<User> users = userMapper.selectAllUser();
            for (User findId : users) {
                if (findId.getId().equals(id)) {
                    userMapper.deleteUser(id);
                    loggger.info("删除用户信息:" + findId + "--->>>Time:" + DateUtil.dataToString(new Date()));
                    return 1;
                }
            }
        }
        return -1;
    }


}
