package com.ll.payment.callback.template;

import com.ll.payment.constant.PayConstant;
import com.ll.payment.mapper.PaymentTransactionLogMapper;
import com.ll.payment.mapper.entity.PaymentTransactionLogEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @description: 使用模版方法重构异步回调代码
 * @author: LL
 * @create: 2019-09-21 16:04
 */
@Component
@Slf4j
public abstract class AbstractPayCallBackTemplate {

    @Autowired
    private PaymentTransactionLogMapper paymentTransactionLogMapper;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

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
    @Transactional
    public String payAsyncCallBack(HttpServletRequest req, HttpServletResponse resp) {
        // 获取所有请求参数封装为map集合进行验证
        Map<String, String> verifySignature = verifySignature(req, resp);
        String paymentId = verifySignature.get("paymentId");
        if (StringUtils.isBlank(paymentId)) {
            return failSuccess();
        }
        log.info(">>>>>>>>>01");
        // 采用异步形式将日志数据库中
        threadPoolTaskExecutor.execute(new PayLogThread(verifySignature));
        // 验证签名
        log.info(">>>>>>>>>04");
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
     * 采用异步或者MQ形式存入数据库，不影响效率问题
     *
     * @param verifySignature
     */
    public void payLog(Map<String, String> verifySignature) {
        PaymentTransactionLogEntity paymentTransactionLog = new PaymentTransactionLogEntity();
        paymentTransactionLog.setTransactionId(verifySignature.get("paymentId"));
        paymentTransactionLog.setAsyncLog(verifySignature.toString());
        paymentTransactionLogMapper.insertTransactionLog(paymentTransactionLog);
    }

    /**
     * 使用多线程写入日志，加快响应提高效率，使用线程池维护线程
     */
    class PayLogThread implements Runnable {
        private Map<String, String> verifySignature;

        PayLogThread(Map<String, String> verifySignature) {
            this.verifySignature = verifySignature;
        }

        @Override
        public void run() {
            log.info(">>>>>>>>>02");
            payLog(verifySignature);
            log.info(">>>>>>>>>03");
        }
    }
}