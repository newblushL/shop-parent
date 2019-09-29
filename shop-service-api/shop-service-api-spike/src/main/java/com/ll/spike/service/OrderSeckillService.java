package com.ll.spike.service;

import com.alibaba.fastjson.JSONObject;
import com.ll.base.BaseResponse;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description: 秒杀记录接口
 * @author: LL
 * @create: 2019-09-29 16:17
 */
public interface OrderSeckillService {

    /**
     * 获取秒杀记录
     *
     * @param phone
     * @param seckillId
     * @return
     */
    @RequestMapping("/getOrder")
    BaseResponse<JSONObject> getOrder(String phone, Long seckillId);

}