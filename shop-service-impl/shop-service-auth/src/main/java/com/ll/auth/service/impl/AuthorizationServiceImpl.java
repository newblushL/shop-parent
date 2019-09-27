package com.ll.auth.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ll.auth.mapper.AppInfoMapper;
import com.ll.auth.mapper.entity.AppInfo;
import com.ll.auth.utils.Guid;
import com.ll.base.BaseApiService;
import com.ll.base.BaseResponse;
import com.ll.core.token.GenerateToken;
import com.ll.service.auth.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: auth实现
 * @author: LL
 * @create: 2019-09-27 22:04
 */
@RestController
public class AuthorizationServiceImpl extends BaseApiService<JSONObject> implements AuthorizationService {

    @Autowired
    private AppInfoMapper appInfoMapper;

    @Autowired
    private GenerateToken generateToken;

    @Override
    public BaseResponse<JSONObject> applyAppInfo(String appName) {
        // 验证参数
        if (StringUtils.isEmpty(appName)) {
            return setResultError("机构名称不能为空!");
        }
        // 生成appid和appScrec
        Guid guid = new Guid();
        String appId = guid.getAppId();
        String appSecret = guid.getAppSecret();
        // 添加数据库中
        AppInfo appInfo = new AppInfo(appName, appId, appSecret);
        int insertAppInfo = appInfoMapper.insertAppInfo(appInfo);
        if (!toDaoResult(insertAppInfo)) {
            return setResultError("申请失败!");
        }
        // 返回给客户端
        JSONObject data = new JSONObject();
        data.put("appId", appId);
        data.put("appSecret", appSecret);
        return setResultSuccess(data);
    }

    @Override
    public BaseResponse<JSONObject> getAccessToken(String appId, String appSecret) {
        // 使用appid+appSecret获取AccessToken
        // 参数验证
        if (StringUtils.isEmpty(appId)) {
            return setResultError("appId不能为空!");
        }
        if (StringUtils.isEmpty(appSecret)) {
            return setResultError("appSecret不能为空!");
        }
        // 使用appId+appSecret查询数据库
        AppInfo appInfo = appInfoMapper.selectByAppInfo(appId, appSecret);
        if (appInfo == null) {
            return setResultError("appId或者是appSecret错误");
        }
        // 获取应用机构信息 生成accessToken
        String dbAppId = appInfo.getAppId();
        String accessToken = generateToken.createToken("auth", dbAppId);
        JSONObject data = new JSONObject();
        data.put("accessToken", accessToken);
        return setResultSuccess(data);
    }

    @Override
    public BaseResponse<JSONObject> getAppInfo(String accessToken) {
        // 验证参数
        if (StringUtils.isEmpty(accessToken)) {
            return setResultError("AccessToken cannot be empty ");
        }
        // 从redis中获取accessToken
        String appId = generateToken.getToken(accessToken);
        if (StringUtils.isEmpty(appId)) {
            return setResultError("accessToken  invalid");
        }
        // 使用appid查询数据库
        AppInfo appInfo = appInfoMapper.findByAppInfo(appId);
        if (appInfo == null) {
            return setResultError("AccessToken  invalid");
        }
        // 返回应用机构信息
        JSONObject data = new JSONObject();
        data.put("appInfo", appInfo);
        return setResultSuccess(data);
    }
}