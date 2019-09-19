package com.ll.payment.service;

import com.alibaba.fastjson.JSONObject;
import com.ll.base.BaseResponse;
import com.ll.payment.input.dto.PayCratePayTokenDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @description: 支付交易Token令牌服务
 * @author: LL
 * @create: 2019-09-19 21:47
 */
public interface PayMentTransacTokenService {
    /**
     * 创建支付令牌
     *
     * @return
     */
    @GetMapping("/cratePayToken")
    BaseResponse<JSONObject> cratePayToken(@Validated PayCratePayTokenDTO payCratePayTokenDTO);
}