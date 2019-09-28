package com.ll.spike.mapper.entity;

import lombok.Data;

import java.util.Date;

/**
 * @description: 秒杀成功订单DO
 * @author: LL
 * @create: 2019-09-28 21:20
 */
@Data
public class OrderEntity {

    /**
     * 库存id
     */
    private Long seckillId;
    /**
     * 用户手机号码
     */
    private String userPhone;
    /**
     * 订单状态
     */
    private Long state;
    /**
     * 创建时间
     */
    private Date createTime;
}
