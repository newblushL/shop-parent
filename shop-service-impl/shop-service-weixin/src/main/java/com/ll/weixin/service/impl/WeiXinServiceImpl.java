package com.ll.weixin.service.impl;

import com.ll.base.BaseApiService;
import com.ll.base.BaseResponse;
import com.ll.entity.AppEntity;
import com.ll.weixin.service.WeiXinService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 微信服务接口实现
 * @author: LL
 * @create: 2019-08-27 15:15
 */
@RestController
public class WeiXinServiceImpl extends BaseApiService<AppEntity> implements WeiXinService {

    @Value("${weixin.name}")
    String name;

    @Override
    public BaseResponse<AppEntity> getApp() {
        return setResultSuccess(new AppEntity("1001", name));
    }
}