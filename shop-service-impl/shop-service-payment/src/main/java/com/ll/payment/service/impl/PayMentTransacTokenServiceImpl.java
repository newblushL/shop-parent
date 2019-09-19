package com.ll.payment.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ll.base.BaseApiService;
import com.ll.base.BaseResponse;
import com.ll.core.token.GenerateToken;
import com.ll.payment.input.dto.PayCratePayTokenDTO;
import com.ll.payment.mapper.PaymentTransactionMapper;
import com.ll.payment.mapper.entity.PaymentTransactionEntity;
import com.ll.payment.service.PayMentTransacTokenService;
import com.ll.twitter.SnowflakeIdUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 支付交易Token令牌服务实现类
 * @author: LL
 * @create: 2019-09-19 21:47
 */
@RestController
public class PayMentTransacTokenServiceImpl extends BaseApiService<JSONObject> implements PayMentTransacTokenService {
    @Autowired
    private PaymentTransactionMapper paymentTransactionMapper;

    @Autowired
    private GenerateToken generateToken;

    @Override
        public BaseResponse<JSONObject> cratePayToken(PayCratePayTokenDTO payCratePayTokenDto) {
            String orderId = payCratePayTokenDto.getOrderId();
            if (StringUtils.isEmpty(orderId)) {
                return setResultError("订单号码不能为空!");
            }
            Long payAmount = payCratePayTokenDto.getPayAmount();
            if (payAmount == null) {
                return setResultError("金额不能为空!");
            }
            Long userId = payCratePayTokenDto.getUserId();
            if (userId == null) {
                return setResultError("userId不能为空!");
            }
            // 2.将输入插入数据库中 待支付记录
            PaymentTransactionEntity paymentTransactionEntity = new PaymentTransactionEntity();
            paymentTransactionEntity.setOrderId(orderId);
            paymentTransactionEntity.setPayAmount(payAmount);
            paymentTransactionEntity.setUserId(userId);
            // 使用雪花算法 生成全局id
            paymentTransactionEntity.setPaymentId(SnowflakeIdUtils.nextId());
            int result = paymentTransactionMapper.insertPaymentTransaction(paymentTransactionEntity);
            if (!toDaoResult(result)) {
                return setResultError("系统错误!");
            }
            // 获取主键id
            Long payId = paymentTransactionEntity.getId();
            if (payId == null) {
                return setResultError("系统错误!");
            }

            // 3.生成对应支付令牌
            String keyPrefix = "pay_";
            String token = generateToken.createToken(keyPrefix, payId + "");
            JSONObject dataResult = new JSONObject();
            dataResult.put("token", token);

            return setResultSuccess(dataResult);
    }

}
