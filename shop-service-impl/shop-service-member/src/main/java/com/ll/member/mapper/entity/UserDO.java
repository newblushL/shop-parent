package com.ll.member.mapper.entity;

import lombok.Data;

import java.sql.Date;

/**
 * @description: 用户实体类DO
 * @author: LL
 * @create: 2019-08-30 15:37
 */
@Data
public class UserDO {

    /**
     * userid
     */

    private Long userId;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 密码
     */
    private String password;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 性别 0 男 1女
     */
    private char sex;
    /**
     * 年龄
     */
    private Long age;
    /**
     * 注册时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 账号是否可以用 1 正常 0冻结
     */
    private char isAvalible;
    /**
     * 用户头像
     */
    private String picImg;
    /**
     * 用户关联 QQ 开放ID
     */
    private String qqOpenid;
    /**
     * 用户关联 微信 开放ID
     */
    private String wxOpenid;
}
