package com.ll.zuul.filter;

import com.ll.zuul.build.GatewayDirector;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @description: 网关拦截
 * @author: LL
 * @create: 2019-09-27 16:07
 */
@Component
public class GatewayFilter extends ZuulFilter {
    @Autowired
    private GatewayDirector gatewayDirector;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response = ctx.getResponse();
        response.setContentType("utf-8");
        String ipAddr = getIpAddr(request);
        gatewayDirector.direcot(ctx, ipAddr, response, request);
        // 防止XXS脚本攻击
        Map<String, List<String>> filterParameters = filterParameters(request, ctx);
        ctx.setRequestQueryParams(filterParameters);
        return null;
    }

    /**
     * 过滤参数
     *
     * @param request
     * @param ctx
     * @return
     */
    private Map<String, List<String>> filterParameters(HttpServletRequest request, RequestContext ctx) {
        Map<String, List<String>> requestQueryParams = ctx.getRequestQueryParams();
        if (requestQueryParams == null) {
            requestQueryParams = new HashMap<>();
        }
        Enumeration em = request.getParameterNames();
        while (em.hasMoreElements()) {
            String name = (String) em.nextElement();
            String value = request.getParameter(name);
            ArrayList<String> arrayList = new ArrayList<>();
            // 将参数转化为html参数 防止xss攻击 < 转为&lt;
            arrayList.add(StringEscapeUtils.escapeHtml(value));
            requestQueryParams.put(name, arrayList);
        }
        return requestQueryParams;
    }

    /**
     * 获取Ip地址
     *
     * @param request
     * @return
     */
    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}