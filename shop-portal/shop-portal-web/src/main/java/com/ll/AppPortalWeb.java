package com.ll;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @description: 门户网站服务
 * @author: LL
 * @create: 2019-08-27 16:24
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class AppPortalWeb {

    public static void main(String[] args) {
        SpringApplication.run(AppPortalWeb.class, args);
    }
}
