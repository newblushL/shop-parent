package com.ll.weixin.service;

import com.ll.base.BaseResponse;
import com.ll.entity.AppEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @description: 微信服务接口
 * @author: LL
 * @create: 2019-08-27 14:59
 */
@Api(tags = "微信服务接口")
public interface WeiXinService{

    /**
     * @Author: LL
     * @Description: 获取应用接口
     * @Date: 2019-08-27
     * @return: ll.entity.AppEntity
     **/
    @ApiOperation(value = "微信应用服务接口")
    @GetMapping("/getApp")
    BaseResponse<AppEntity> getApp();
}