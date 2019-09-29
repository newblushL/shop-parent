package com.ll.spike.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ll.base.BaseApiService;
import com.ll.base.BaseResponse;
import com.ll.spike.mapper.OrderMapper;
import com.ll.spike.mapper.entity.OrderEntity;
import com.ll.spike.service.OrderSeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 秒杀记录实现
 * @author: LL
 * @create: 2019-09-29 16:23
 */
@RestController
public class OrderSeckillServiceImpl extends BaseApiService<JSONObject> implements OrderSeckillService {
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public BaseResponse<JSONObject> getOrder(String phone, Long seckillId) {
        if (StringUtils.isEmpty(phone)) {
            return setResultError("手机号码不能为空!");
        }
        if (seckillId == null) {
            return setResultError("商品库存id不能为空!");
        }
        OrderEntity orderEntity = orderMapper.findByOrder(phone, seckillId);
        if (orderEntity == null) {
            return setResultError("正在排队中.....");
        }
        return setResultSuccess("恭喜你秒杀成功!");
    }
}