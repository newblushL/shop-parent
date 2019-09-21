package com.ll.payment.callback.template;

import com.ll.payment.constant.PayConstant;
import org.apache.commons.lang.StringUtils;
import org.springframework.scheduling.annotation.Async;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @description: 使用模版方法重构异步回调代码
 * @author: LL
 * @create: 2019-09-21 16:04
 */
public abstract class AbstractPayCallBackTemplate {

    /**
     * 获取所有请求参数
     *
     * @param req
     * @param resp
     * @return
     */
    protected abstract Map<String, String> verifySignature(HttpServletRequest req, HttpServletResponse resp);

    /**
     * 失败请求
     *
     * @return
     */
    protected abstract String failSuccess();

    /**
     * 成功请求
     *
     * @return
     */
    public abstract String successResult();

    /**
     * @Author: LL
     * @Description: 支付异步回调
     * @Date: 2019-09-21
     * @Param req:
     * @Param resp:
     * @return: java.lang.String
     **/
    public String payAsyncCallBack(HttpServletRequest req, HttpServletResponse resp) {
        // 获取所有请求参数封装为map集合进行验证
        Map<String, String> verifySignature = verifySignature(req, resp);
        // 采用异步形式将日志数据库中
        String paymentId = verifySignature.get("paymentId");
        if (StringUtils.isBlank(paymentId)) {
            return failSuccess();
        }
        payLog(verifySignature);
        // 验证签名
        String result = verifySignature.get(PayConstant.RESULT_NAME);
        if (result.equals(PayConstant.RESULT_PAYCODE_201)) {
            return failSuccess();
        }
        // 执行异步回调业务逻辑
        return asyncService(verifySignature);
    }

    /**
     * 异步回调逻辑
     *
     * @param verifySignature
     * @return
     */
    protected abstract String asyncService(Map<String, String> verifySignature);

    /**
     * 采用异步或者MQ形式存入数据库，不影响缓慢问题
     *
     * @param verifySignature
     */
    @Async
    protected abstract void payLog(Map<String, String> verifySignature);

}