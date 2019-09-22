package com.ll.integral.consume;

import com.alibaba.fastjson.JSONObject;
import com.ll.integral.mapper.IntegralMapper;
import com.ll.integral.mapper.entity.IntegralEntity;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @description: 积分消费者
 * @author: LL
 * @create: 2019-09-22 21:13
 */
@Component
@Slf4j
public class IntegralConsumer {
    @Autowired
    private IntegralMapper integralMapper;

    /**
     * 接受MQ消息
     */
    @RabbitListener(queues = "integral_queue")
    public void process(Message message, @Headers Map<String, Object> headers,
                        Channel channel) {
        // try MQ不会进行重试机制
        try {
            String messageId = message.getMessageProperties().getMessageId();
            String msg = new String(message.getBody(), "UTF-8");
            log.info(">>>>messageId{},msg{}", messageId, msg);
            JSONObject jsonObject = JSONObject.parseObject(msg);
            String paymentId = jsonObject.getString("paymentId");
            if (StringUtils.isBlank(paymentId)) {
                log.error(">>>>支付id不能为空paymentId{}", paymentId);
                basicNack(message, channel);
                return;
            }
            IntegralEntity integralEntity = integralMapper.findIntegral(paymentId);
            if (integralEntity != null) {
                log.error(">>>>paymentId{},已经增加过积分", paymentId);
                basicNack(message, channel);
                return;
            }
            Integer userId = jsonObject.getInteger("userId");
            if (userId == null) {
                log.error("paymentId{},对应的userId参数为空", paymentId);
                basicNack(message, channel);
                return;
            }
            Long integral = jsonObject.getLong("integral");
            if (integral == null) {
                log.error("paymentId{},对应的integral参数为空", paymentId);
                basicNack(message, channel);
                return;
            }
            IntegralEntity integralDO = new IntegralEntity();
            integralDO.setPaymentId(paymentId);
            integralDO.setIntegral(integral);
            integralDO.setUserId(userId);
            integralDO.setAvailability(1);
            int insertIntegral = integralMapper.insertIntegral(integralDO);
            if (insertIntegral > 0) {
                basicNack(message, channel);
            }
        } catch (Exception e) {
            log.error(">>>>ERROR MSG:", e.getMessage());
            basicNack(message, channel);
        }
    }

    /**
     * 手动Ack
     *
     * @param message
     * @param channel
     */
    private void basicNack(Message message, Channel channel) {
        try {
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}