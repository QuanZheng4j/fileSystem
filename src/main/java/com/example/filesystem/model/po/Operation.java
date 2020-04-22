package com.example.filesystem.model.po;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @ClassName Operation
 * @Description 操作表
 * @Author zq
 * Date 2020/1/14 9:56
 * Version 1.0
 */
@Getter
@Setter
@ToString
public class Operation {

    /**
     * id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 文件路径
     */
    private String fileName;

    /**
     * 文件大小kb为单位
     */
    private String fileSize;

    /**申请原因*/
    private String reason;

    /**
     * 审批人
     */
    private String approvalName;

    /**
     * 审批时间
     */
    private Date approvalTime;

    /**
     * 审批结果
     */
    private String approvalResult;

    private Date createTime;

    private Date updateTime;
}
