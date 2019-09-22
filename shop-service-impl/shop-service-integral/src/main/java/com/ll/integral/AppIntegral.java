package com.ll.integral;
/**
 * @description: 积分服务
 * @author: LL
 * @create: 2019-09-22 21:13
 */

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = "com.ll.integral.mapper")
public class AppIntegral {

    public static void main(String[] args) {
        SpringApplication.run(AppIntegral.class, args);
    }

}
