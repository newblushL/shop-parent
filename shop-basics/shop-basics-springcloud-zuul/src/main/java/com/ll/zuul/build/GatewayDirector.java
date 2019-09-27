package com.ll.zuul.build;

import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 连接build
 * @author: LL
 * @create: 2019-09-27 17:37
 */
@Component
public class GatewayDirector {
    @Resource(name = "verificationBuild")
    private GatewayBuild gatewayBuild;

    /**
     * 连接建造者
     *
     * @param ctx
     * @param ipAddres
     * @param response
     * @param request
     */
    public void direcot(RequestContext ctx, String ipAddres, HttpServletResponse response, HttpServletRequest request) {
        Boolean blackBlock = gatewayBuild.blackBlock(ctx, ipAddres, response);
        if (!blackBlock) {
            return;
        }
        Boolean verifyMap = gatewayBuild.toVerifyMap(ctx, request);
        if (!verifyMap) {
            return;
        }
        Boolean apiAuthority = gatewayBuild.apiAuthority(ctx, request);
        if (!apiAuthority) {
            return;
        }
    }
}