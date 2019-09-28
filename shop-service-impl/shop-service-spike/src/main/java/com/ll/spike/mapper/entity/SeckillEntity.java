package com.ll.spike.mapper.entity;

import lombok.Data;

import java.util.Date;

/**
 * @description: 秒杀商品库存DO
 * @author: LL
 * @create: 2019-09-28 21:20
 */
@Data
public class SeckillEntity {

    /**
     * 库存id
     */
    private Long seckillId;
    /**
     * 商品名称
     */
    private String name;

    /**
     * 库存数量
     */
    private Long inventory;

    /**
     * 开启时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 秒杀抢购
     */
    private Long version;
}
