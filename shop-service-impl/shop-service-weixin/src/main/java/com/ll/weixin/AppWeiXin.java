package com.ll.weixin;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @description: 微信服务项目
 * @author: LL
 * @create: 2019-08-27 15:34
 */
@SpringBootApplication
@EnableEurekaClient
@EnableSwagger2Doc
@EnableApolloConfig
public class AppWeiXin {

    public static void main(String[] args) {
        SpringApplication.run(AppWeiXin.class,args);
    }
}