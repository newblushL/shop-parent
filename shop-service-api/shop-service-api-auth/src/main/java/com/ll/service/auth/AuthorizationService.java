package com.ll.service.auth;

import com.alibaba.fastjson.JSONObject;
import com.ll.base.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @description: auth开放授权接口
 * @author: LL
 * @create: 2019-09-27 21:58
 */
public interface AuthorizationService {

    /**
     * 机构申请 获取appid 和appsecret
     *
     * @return
     */
    @GetMapping("/applyAppInfo")
    BaseResponse<JSONObject> applyAppInfo(@RequestParam("appName") String appName);

    /**
     * 使用appid 和appsecret密钥获取AccessToken
     *
     * @param appId
     * @param appSecret
     * @return
     */
    @GetMapping("/getAccessToken")
    BaseResponse<JSONObject> getAccessToken(@RequestParam("appId") String appId,
                                            @RequestParam("appSecret") String appSecret);

    /**
     * 验证Token是否失效
     *
     * @param accessToken
     * @return
     */
    @GetMapping("/getAppInfo")
    BaseResponse<JSONObject> getAppInfo(@RequestParam("accessToken") String accessToken);

}