package com.ll.spike.service;

import com.alibaba.fastjson.JSONObject;
import com.ll.base.BaseResponse;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description: 秒杀商品服务接口
 * @author: LL
 * @create: 2019-09-28 21:18
 */
public interface SpikeCommodityService {

    /**
     * 用户秒杀接口
     *
     * @return
     * @phone 手机号码<br>
     * @seckillId 库存id
     */
    @RequestMapping("/spike")
    BaseResponse<JSONObject> spike(String phone, Long seckillId);
}