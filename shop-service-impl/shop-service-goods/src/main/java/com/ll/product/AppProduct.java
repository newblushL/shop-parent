package com.ll.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * @description: 商品服务
 * @author: LL
 * @create: 2019-08-27 16:24
 */
@EnableEurekaClient
@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = {"com.ll.product.es"})
@ComponentScan(basePackages = {"com.ll"})
public class AppProduct {

    public static void main(String[] args) {
        SpringApplication.run(AppProduct.class, args);
    }

}
