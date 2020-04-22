package com.example.filesystem.model.vo;

import lombok.Data;

/**
 * @ClassName ResetPwdUser
 * @Description TODO
 * @Author zq
 * Date 2020/2/26 15:46
 * Version 1.0
 */
@Data
public class ResetPwdUser {
    private String userName;
    private String password;
    private String newPassword;
}
