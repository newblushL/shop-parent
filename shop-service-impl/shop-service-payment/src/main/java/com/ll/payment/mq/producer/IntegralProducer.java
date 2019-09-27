package com.ll.payment.mq.producer;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description: 积分生产者
 * @author: LL
 * @create: 2019-09-22 19:45
 */
@Component
@Slf4j
public class IntegralProducer implements RabbitTemplate.ConfirmCallback {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息
     *
     * @param jsonObject
     */
    @Transactional
    public void send(JSONObject jsonObject) {

        String jsonString = jsonObject.toJSONString();
        System.out.println("jsonString:" + jsonString);
        String paymentId = jsonObject.getString("paymentId");
        // 封装消息
        Message message = MessageBuilder.withBody(jsonString.getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_JSON).setContentEncoding("utf-8").setMessageId(paymentId)
                .build();
        // 构建回调返回的数据（消息id）
        this.rabbitTemplate.setMandatory(true);
        this.rabbitTemplate.setConfirmCallback(this);
        CorrelationData correlationData = new CorrelationData(jsonString);
        rabbitTemplate.convertAndSend("integral_exchange_name", "integralRoutingKey", message, correlationData);

    }

    /**
     * 生产消息确认机制 生产者往服务器端发送消息的时候，采用应答机制
     *
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            log.info(">>>>>MQ消息发送确实成功");
        } else {
            JSONObject jsonObject = JSONObject.parseObject(correlationData.getId());
            send(jsonObject);
            log.error(">>>>>消息发送确认失败{}", cause);
        }

    }
}