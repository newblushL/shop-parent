package com.ll.spike.mapper;

import com.ll.spike.mapper.entity.OrderEntity;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

/**
 * @description: OrderMapper
 * @author: LL
 * @create: 2019-09-28 21:20
 */
@Repository
public interface OrderMapper {

    @Insert("INSERT INTO `order` VALUES (#{seckillId},#{userPhone}, '1', now());")
    int insertOrder(OrderEntity orderEntity);
}
