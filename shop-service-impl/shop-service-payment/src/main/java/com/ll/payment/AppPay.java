package com.ll.payment;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @description: 支付服务
 * @author: LL
 * @create: 2019-09-19 21:47
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
//@EnableSwagger2Doc
//@EnableApolloConfig
@ComponentScan(basePackages = {"com.ll"})
@MapperScan(basePackages = "com.ll.payment.mapper")
public class AppPay {

    public static void main(String[] args) {
        SpringApplication.run(AppPay.class, args);
    }
}
