package com.ll.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @description: 认证服务
 * @author: LL
 * @create: 2019-09-19 21:47
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
// @EnableApolloConfig
@MapperScan(basePackages = "com.ll.auth.mapper")
@ComponentScan(basePackages = "com.ll")
public class AppAuth {

    public static void main(String[] args) {
        SpringApplication.run(AppAuth.class, args);
    }

}
