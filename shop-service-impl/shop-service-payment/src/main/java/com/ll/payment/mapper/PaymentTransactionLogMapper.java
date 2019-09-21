package com.ll.payment.mapper;

import com.ll.payment.mapper.entity.PaymentTransactionLogEntity;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

/**
 * @description: PaymentTransactionLogMapper
 * @author: LL
 * @create: 2019-08-30 15:37
 */
@Repository
public interface PaymentTransactionLogMapper {

    @Insert("INSERT INTO `payment_transaction_log` VALUES (NULL, NULL, #{asyncLog}," +
            "NULL,#{transactionId}, null, null, NOW(), null, NOW(),null);")
    int insertTransactionLog(PaymentTransactionLogEntity paymentTransactionLog);

}
