package com.ll.spike.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ll.base.BaseApiService;
import com.ll.base.BaseResponse;
import com.ll.core.token.GenerateToken;
import com.ll.spike.mapper.SeckillMapper;
import com.ll.spike.mapper.entity.SeckillEntity;
import com.ll.spike.producer.SpikeCommodityProducer;
import com.ll.spike.service.SpikeCommodityService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 秒杀商品实现
 * @author: LL
 * @create: 2019-09-28 21:20
 */
@RestController
@Slf4j
public class SpikeCommodityServiceImpl extends BaseApiService<JSONObject> implements SpikeCommodityService {

    @Autowired
    private SeckillMapper seckillMapper;
    @Autowired
    private GenerateToken generateToken;
    @Autowired
    private SpikeCommodityProducer spikeCommodityProducer;


    @Override
    @Transactional
    public BaseResponse<JSONObject> spike(String phone, Long seckillId) {
        if (StringUtils.isBlank(phone)) {
            return setResultError("手机号不能为空");
        }
        if (StringUtils.isBlank(seckillId + "")) {
            return setResultError("商品库存ID不能为空");
        }
        // 从redis从获取对应的秒杀token
        String seckillToken = generateToken.getListKeyToken(seckillId + "");
        if (StringUtils.isEmpty(seckillToken)) {
            log.info(">>>seckillId:{}, 亲，该秒杀已经售空，请下次再来!", seckillId);
            return setResultError("亲，该秒杀已经售空，请下次再来!");
        }

        // 获取到秒杀token之后，异步放入mq中实现修改商品的库存
        sendSeckillMsg(seckillId, phone);
        return setResultSuccess("正在排队中.......");
    }

    /**
     * 发送消息到MQ
     *
     * @param seckillId
     * @param phone
     */
    @Async
    void sendSeckillMsg(Long seckillId, String phone) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("seckillId", seckillId);
        jsonObject.put("phone", phone);
        spikeCommodityProducer.send(jsonObject);
    }

    @Override
    public BaseResponse<JSONObject> addSpikeToken(Long seckillId, Long tokenQuantity) {
        // 验证参数
        if (seckillId == null) {
            return setResultError("商品库存id不能为空!");
        }
        if (tokenQuantity == null) {
            return setResultError("token数量不能为空!");
        }
        SeckillEntity seckillEntity = seckillMapper.findBySeckillId(seckillId);
        if (seckillEntity == null) {
            return setResultError("商品信息不存在!");
        }
        // 使用多线程异步生产令牌
        createSeckillToken(seckillId, tokenQuantity);
        return setResultSuccess("令牌正在生成中.....");
    }

    /**
     * 生成token令牌桶
     *
     * @param seckillId
     * @param tokenQuantity
     */
    @Async
    void createSeckillToken(Long seckillId, Long tokenQuantity) {
        generateToken.createListToken("seckill_", seckillId + "", tokenQuantity);
    }

}