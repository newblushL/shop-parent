package com.ll.weixin.service;

import ll.entity.AppEntity;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @description: 微信服务接口
 * @author: LL
 * @create: 2019-08-27 14:59
 */
public interface WeiXinService {

    /**
     * @Author: LL
     * @Description: 获取应用接口
     * @Date: 2019-08-27
     * @return: ll.entity.AppEntity
     **/
    @GetMapping("/getApp")
    AppEntity getApp();
}