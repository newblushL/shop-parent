package com.ll.payment.strategy.impl;

import com.ll.payment.mapper.entity.PaymentChannelEntity;
import com.ll.payment.output.dto.PayMentTransacDTO;
import com.ll.payment.strategy.PayStrategy;

/**
 * @description: 支付宝支付渠道
 * @author: LL
 * @create: 2019-09-20 20:39
 */
public class AliPayStrategy implements PayStrategy {
    @Override
    public String toPayHtml(PaymentChannelEntity pymentChannel, PayMentTransacDTO payMentTransacDTO) {
        return "支付宝支付渠道";
    }
}