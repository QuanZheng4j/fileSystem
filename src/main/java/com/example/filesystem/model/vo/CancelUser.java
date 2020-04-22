package com.example.filesystem.model.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName CancelUser
 * @Description 取消申请返回VO
 * @Author zq
 * Date 2020/2/18 20:42
 * Version 1.0
 */
@Getter
@Setter
public class CancelUser {
    private String name;
    private String approvalIP;
}
