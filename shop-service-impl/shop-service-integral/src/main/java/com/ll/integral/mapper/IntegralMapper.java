package com.ll.integral.mapper;

import com.ll.integral.mapper.entity.IntegralEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


/**
 * @description: 积分Mapper
 * @author: LL
 * @create: 2019-09-21 16:49
 */
@Repository
public interface IntegralMapper {
    @Insert("INSERT INTO `integral` VALUES (NULL, #{userId}, #{paymentId},#{integral}, #{availability}, 0, null, now(), null, now());")
    int insertIntegral(IntegralEntity eiteIntegralEntity);

    @Select("SELECT  id as id ,USER_ID as userId, PAYMENT_ID as PAYMENTID ,INTEGRAL as " +
            "INTEGRAL ,AVAILABILITY as AVAILABILITY  FROM integral where PAYMENT_ID=#{paymentId}  AND AVAILABILITY='1';")
    IntegralEntity findIntegral(String paymentId);
}
