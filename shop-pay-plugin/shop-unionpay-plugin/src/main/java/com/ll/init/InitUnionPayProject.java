package com.ll.init;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.unionpay.acp.sdk.SDKConfig;

/**
 * @description: 银联支付项目初始化
 * @author: LL
 * @create: 2019-08-27 16:24
 */
@Component
public class InitUnionPayProject implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        SDKConfig.getConfig().loadPropertiesFromSrc();
    }
}
