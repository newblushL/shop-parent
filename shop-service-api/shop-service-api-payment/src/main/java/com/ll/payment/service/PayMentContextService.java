package com.ll.payment.service;

import com.alibaba.fastjson.JSONObject;
import com.ll.base.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @description: 根据不同的渠道ID返回不同的支付提交表单
 * @author: LL
 * @create: 2019-09-20 19:41
 */
public interface PayMentContextService {

    @GetMapping("/toPayHtml")
    BaseResponse<JSONObject>toPayHtml(String channelId,String payToken);
}