package com.ll.zuul.mapper.entity;

import lombok.Data;

import java.util.Date;

/**
 * 黑名单DO
 */
@Data
public class Blacklist {
    /**
     * 主键ID
     */

    private Integer id;
    /**
     * ip地址
     */
    private String ipAddres;
    /**
     * 限制类型
     */
    private Integer restrictionType;
    /**
     * 是否可用
     */
    private Integer availability;
    /**
     * 乐观锁
     */
    private Integer revision;
    /**
     * 创建人
     */
    private String createdBy;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 更新人
     */
    private String updatedBy;
    /**
     * 更新时间
     */
    private Date updatedTime;
}
