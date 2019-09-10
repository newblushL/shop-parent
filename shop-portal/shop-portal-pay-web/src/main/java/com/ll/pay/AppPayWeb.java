package com.ll.pay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class AppPayWeb {

	public static void main(String[] args) {
		SpringApplication.run(AppPayWeb.class, args);
	}

}
