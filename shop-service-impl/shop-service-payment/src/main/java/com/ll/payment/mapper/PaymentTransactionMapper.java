package com.ll.payment.mapper;

import com.ll.payment.mapper.entity.PaymentTransactionEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @description: PaymentTransactionMapper
 * @author: LL
 * @create: 2019-08-30 15:37
 */
@Repository
public interface PaymentTransactionMapper {
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO `payment_transaction` VALUES (null, #{payAmount}, '0', #{userId}," +
            " #{orderId}, null, null, now(), null, now(),null,#{paymentId});")
    int insertPaymentTransaction(PaymentTransactionEntity paymentTransactionEntity);

    @Select("SELECT ID AS ID ,pay_Amount AS payAmount,payment_Status AS paymentStatus," +
            "user_ID AS userId, order_Id AS orderId , created_Time as createdTime ," +
            "partypay_Id as partyPayId , payment_Id as paymentId FROM payment_transaction " +
            "WHERE ID=#{id};")
    PaymentTransactionEntity selectById(Long id);

}
