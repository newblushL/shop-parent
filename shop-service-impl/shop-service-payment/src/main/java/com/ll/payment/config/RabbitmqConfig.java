package com.ll.payment.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: RabbitMQ关联队列相关配置
 * @author: LL
 * @create: 2019-09-22 19:40
 */
@Configuration
public class RabbitmqConfig {

    /**
     * 积分队列
     */
    public static final String INTEGRAL_DIC_QUEUE = "integral_queue";

    /**
     * 补单队列
     */
    public static final String INTEGRAL_CREATE_QUEUE = "integral_create_queue";

    /**
     * 交换机
     */
    private static final String INTEGRAL_EXCHANGE_NAME = "integral_exchange_name";

    /**
     * 生成积分队列
     *
     * @return
     */
    @Bean
    public Queue directIntegralDicQueue() {
        return new Queue(INTEGRAL_DIC_QUEUE);
    }

    /**
     * 生成补单队列
     *
     * @return
     */
    @Bean
    public Queue directCreateintegralQueue() {
        return new Queue(INTEGRAL_CREATE_QUEUE);
    }

    /**
     * 生成交换机
     *
     * @return
     */
    @Bean
    DirectExchange directintegralExchange() {
        return new DirectExchange(INTEGRAL_EXCHANGE_NAME);
    }

    /**
     * 积分与交换机绑定
     *
     * @return
     */
    @Bean
    Binding bindingExchangeintegralDicQueue() {
        return BindingBuilder.bind(directIntegralDicQueue()).to(directintegralExchange()).with("integralRoutingKey");
    }

    /**
     * 补单与交换机绑定
     *
     * @return
     */
    @Bean
    Binding bindingExchangeCreateintegral() {
        return BindingBuilder.bind(directCreateintegralQueue()).to(directintegralExchange()).with("integralRoutingKey");
    }

}