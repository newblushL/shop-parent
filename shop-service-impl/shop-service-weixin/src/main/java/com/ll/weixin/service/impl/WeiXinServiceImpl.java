package com.ll.weixin.service.impl;

import com.ll.weixin.service.WeiXinService;
import ll.entity.AppEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 微信服务接口实现
 * @author: LL
 * @create: 2019-08-27 15:15
 */
@RestController
public class WeiXinServiceImpl implements WeiXinService {
    @Override
    public AppEntity getApp() {
        return new AppEntity("1001","newblush");
    }
}