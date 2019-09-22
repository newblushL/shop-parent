package com.ll.payment.mq.producer;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: LL
 * @create: 2019-09-22 19:57
 */
@RestController
public class TestServiceImpl {

    @Autowired
    private IntegralProducer integralProducer;

    @GetMapping("/send")
    public String send() {
        JSONObject data = new JSONObject();
        data.put("paymentId", System.currentTimeMillis());
        data.put("userId", 123456);
        data.put("integral", 100);
        integralProducer.send(data);
        return "success";
    }
}