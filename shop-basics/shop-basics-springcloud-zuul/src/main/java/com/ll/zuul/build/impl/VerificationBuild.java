package com.ll.zuul.build.impl;

import com.alibaba.fastjson.JSONObject;
import com.ll.base.BaseResponse;
import com.ll.constants.Constants;
import com.ll.core.utils.SignUtil;
import com.ll.zuul.build.GatewayBuild;
import com.ll.zuul.feign.AuthorizationServiceFeign;
import com.ll.zuul.mapper.BlacklistMapper;
import com.ll.zuul.mapper.entity.Blacklist;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @description: GatawayBuild实现
 * @author: LL
 * @create: 2019-09-27 17:33
 */
@Component
@Slf4j
public class VerificationBuild implements GatewayBuild {
    @Autowired
    private BlacklistMapper blacklistMapper;

    @Autowired
    private AuthorizationServiceFeign authorizationServiceFeign;

    @Override
    public Boolean blackBlock(RequestContext ctx, String ipAddres, HttpServletResponse response) {
        // 验证IP地址黑名单
        Blacklist blacklist = blacklistMapper.findBlacklist(ipAddres);
        if (blacklist != null) {
            resultError(ctx, "Insufficient authority");
            return false;
        }
        return true;
    }

    @Override
    public Boolean toVerifyMap(RequestContext ctx, HttpServletRequest request) {
        // 验证签名拦截
        Map<String, String> verifyMap = SignUtil.toVerifyMap(request.getParameterMap(), false);
        if (!SignUtil.verify(verifyMap)) {
            resultError(ctx, "Sign fail");
            return false;
        }
        return true;
    }

    @Override
    public Boolean apiAuthority(RequestContext ctx, HttpServletRequest request) {
        String servletPath = request.getServletPath();
        log.info(">>>>>servletPath:" + servletPath + ",servletPath.substring(0, 5):" + servletPath.substring(0, 5));
        if (!servletPath.substring(0, 7).equals("/public")) {
            return true;
        }
        String accessToken = request.getParameter("accessToken");
        log.info(">>>>>accessToken验证:" + accessToken);
        if (StringUtils.isEmpty(accessToken)) {
            resultError(ctx, "AccessToken cannot be empty");
            return false;
        }
        // 调用接口验证accessToken是否失效
        BaseResponse<JSONObject> appInfo = authorizationServiceFeign.getAppInfo(accessToken);
        log.info(">>>>>>data:" + appInfo.toString());
        if (!isSuccess(appInfo)) {
            resultError(ctx, appInfo.getMsg());
            return false;
        }
        return true;
    }

    /**
     * 请求失败
     *
     * @param ctx
     * @param errorMsg
     */
    private void resultError(RequestContext ctx, String errorMsg) {
        ctx.setResponseStatusCode(401);
        ctx.setSendZuulResponse(false);
        ctx.setResponseBody(errorMsg);
    }

    /**
     * 接口直接返回true 或者false
     *
     * @param baseResp
     * @return
     */
    public Boolean isSuccess(BaseResponse<?> baseResp) {
        if (baseResp == null) {
            return false;
        }
        if (!baseResp.getCode().equals(Constants.HTTP_RES_CODE_200)) {
            return false;
        }
        return true;
    }
}