package com.ll.spike;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @description: 秒杀服务
 * @author: LL
 * @create: 2019-09-28 21:20
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = "com.ll.spike.mapper")
@ComponentScan(basePackages = "com.ll")
public class AppSpike {

    public static void main(String[] args) {
        SpringApplication.run(AppSpike.class, args);
    }

}
