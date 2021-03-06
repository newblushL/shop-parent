package com.ll.payment.mapper;

import com.ll.payment.mapper.entity.PaymentTransactionEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description: PaymentTransactionMapper
 * @author: LL
 * @create: 2019-08-30 15:37
 */
@Repository
public interface PaymentTransactionMapper {
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO `payment_transaction` VALUES (null, #{payAmount}, '0', #{userId}, #{orderId}, null, null, now(), null, now(),null,#{paymentId},null);")
    int insertPaymentTransaction(PaymentTransactionEntity paymentTransactionEntity);

    @Select("SELECT ID AS ID ,pay_Amount AS payAmount,payment_Status AS paymentStatus,user_ID AS userId, order_Id AS orderId , created_Time as createdTime ,partypay_Id as partyPayId , payment_Id as paymentId ,payment_channel as paymentChannel FROM payment_transaction WHERE ID=#{id};")
    PaymentTransactionEntity selectById(Long id);

    @Select("SELECT ID AS ID ,pay_Amount AS payAmount,payment_Status AS paymentStatus,user_ID AS userId, order_Id AS orderId , created_Time as createdTime ,partypay_Id as partyPayId , payment_Id as paymentId ,payment_channel as paymentChannel FROM payment_transaction WHERE PAYMENT_ID=#{paymentId};")
    PaymentTransactionEntity selectByPaymentId(String paymentId);

    @Update("update payment_transaction SET PAYMENT_STATUS=#{paymentStatus},payment_channel=#{paymentChannel}   WHERE PAYMENT_ID=#{paymentId}; ")
    int updatePaymentStatus(@Param("paymentStatus") String paymentStatus, @Param("paymentId") String paymentId,
                            @Param("paymentChannel") String paymentChannel);

    @Select("SELECT ID AS ID ,pay_Amount AS payAmount,payment_Status AS paymentStatus,user_ID AS userId, order_Id AS orderId , created_Time as createdTime ,partypay_Id as partyPayId , payment_Id as paymentId ,payment_channel as paymentChannel FROM payment_transaction WHERE PAYMENT_ID=#{paymentId} and paymentStatus=0 ;")
    PaymentTransactionEntity selectByPaymentNoPayment(String paymentId);

    @Select("SELECT ID AS ID ,pay_Amount AS payAmount,payment_Status AS paymentStatus,user_ID AS userId, order_Id AS orderId , created_Time as createdTime ,partypay_Id as partyPayId , payment_Id as paymentId ,payment_channel as paymentChannel FROM payment_transaction WHERE paymentStatus=0 ;")
    List<PaymentTransactionEntity> selectByStatusStay();


}
