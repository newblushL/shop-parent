package com.ll.payment.service;

import com.alibaba.fastjson.JSONObject;
import com.ll.base.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @description: 支付提交表单接口
 * @author: LL
 * @create: 2019-09-20 19:41
 */
public interface PayMentContextService {

    /**
     * @Author: LL
     * @Description: 根据不同的渠道ID返回不同的支付提交表单
     * @Date: 2019-09-21
     * @Param channelId:
     * @Param payToken:
     * @return: com.ll.base.BaseResponse<com.alibaba.fastjson.JSONObject>
     **/
    @GetMapping("/toPayHtml")
    BaseResponse<JSONObject> toPayHtml(@RequestParam String channelId, @RequestParam String payToken);
}