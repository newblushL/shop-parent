package com.ll.payment.input.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @description: 支付令牌请求实体类
 * @author: LL
 * @create: 2019-09-19 21:44
 */
@Data
public class PayCratePayTokenDTO {
    /**
     * 支付金额
     */
    @NotBlank(message = "支付金额不能为空")
    private Long payAmount;
    /**
     * 订单号码
     */
    @NotBlank(message = "订单号码不能为空")
    private String orderId;

    /**
     * userId
     */
    @NotBlank(message = "userId不能空")
    private Long userId;
}