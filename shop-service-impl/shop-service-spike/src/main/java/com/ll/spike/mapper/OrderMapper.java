package com.ll.spike.mapper;

import com.ll.spike.mapper.entity.OrderEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @description: OrderMapper
 * @author: LL
 * @create: 2019-09-28 21:20
 */
@Repository
public interface OrderMapper {

    @Insert("INSERT INTO `order_ms` VALUES (#{seckillId},#{userPhone}, '1', now());")
    int insertOrder(OrderEntity orderEntity);

    @Select("SELECT seckill_id AS seckillid,user_phone as userPhone , state as state FROM " +
            "order_ms WHERE USER_PHONE=#{phone}  and seckill_id=#{seckillId}  AND STATE='1';")
    OrderEntity findByOrder(@Param("phone") String phone, @Param("seckillId") Long seckillId);
}