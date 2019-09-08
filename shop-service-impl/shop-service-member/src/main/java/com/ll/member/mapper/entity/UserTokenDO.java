package com.ll.member.mapper.entity;

import lombok.Data;

/**
 * @description: 用户Token实体类DO
 * @author: LL
 * @create: 2019-08-30 15:37
 */
@Data
public class UserTokenDO extends BaseDO {
    /**
     * 用户token
     */
    private String token;
    /**
     * 登陆类型
     */
    private String loginType;

    /**
     * 设备信息
     */
    private String deviceInfor;
    /**
     * 用户userId
     */
    private Long userId;
}