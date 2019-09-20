package com.ll.payment.strategy;

import com.ll.payment.mapper.entity.PaymentChannelEntity;
import com.ll.payment.output.dto.PayMentTransacDTO;

/**
 * @description: 支付接口共同实现行为算法
 * @author: LL
 * @create: 2019-09-20 20:18
 */
public interface PayStrategy {

    /**
     * @Author: LL
     * @Description:
     * @Date: 2019-09-20
     * @Param pymentChannel: 支付渠道参数
     * @Param payMentTransacDTO: 支付信息参数
     * @return: java.lang.String
     **/
    String toPayHtml(PaymentChannelEntity pymentChannel, PayMentTransacDTO payMentTransacDTO);
}