package com.ll.spike.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ll.base.BaseApiService;
import com.ll.base.BaseResponse;
import com.ll.core.utils.RedisUtil;
import com.ll.spike.mapper.OrderMapper;
import com.ll.spike.mapper.SeckillMapper;
import com.ll.spike.mapper.entity.OrderEntity;
import com.ll.spike.mapper.entity.SeckillEntity;
import com.ll.spike.service.SpikeCommodityService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 秒杀商品服务实现
 * @author: LL
 * @create: 2019-09-28 21:20
 */
@RestController
@Slf4j
public class SpikeCommodityServiceImpl extends BaseApiService<JSONObject> implements SpikeCommodityService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private SeckillMapper seckillMapper;
    @Autowired
    private RedisUtil redisUtil;

    private long nxTimeout = 10;

    @Override
    public BaseResponse<JSONObject> spike(String phone, Long seckillId) {
        if (StringUtils.isBlank(phone)) {
            return setResultError("手机号不能为空");
        }
        if (StringUtils.isBlank(seckillId + "")) {
            return setResultError("商品库存ID不能为空");
        }

        SeckillEntity bySeckillId = seckillMapper.findBySeckillId(seckillId);
        if (bySeckillId == null) {
            return setResultError("商品信息不存在");
        }
        // 用redis setNx限制用户访问频率
        Boolean setNx = redisUtil.setNx(phone, seckillId + "", nxTimeout);
        if (!setNx) {
            return setResultError("访问次数过于频繁，请10秒后再试");
        }
        // 修改商品库存
        long version = bySeckillId.getVersion();
        int inventoryDeduction = seckillMapper.inventoryDeduction(seckillId, version);
        if (!toDaoResult(inventoryDeduction)) {
            log.error(">>>>>>修改库存失败inventoryDeduction{}", inventoryDeduction);
            return setResultError("亲，请稍后再试！");
        }
        // 添加秒杀商品订单
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setSeckillId(seckillId);
        orderEntity.setUserPhone(phone);
        int insertOrder = orderMapper.insertOrder(orderEntity);
        if (!toDaoResult(insertOrder)) {
            log.error(">>>>>>添加秒杀商品订单失败insertOrder{}", insertOrder);
            return setResultError("亲，请稍后再试！");
        }
        return setResultSuccess("恭喜，秒杀成功");
    }
}