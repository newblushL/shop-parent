package com.ll.payment.callback.template.unionpay;

import com.ll.payment.callback.template.AbstractPayCallBackTemplate;
import com.ll.payment.constant.PayConstant;
import com.ll.payment.mapper.PaymentTransactionMapper;
import com.ll.payment.mapper.entity.PaymentTransactionEntity;
import com.unionpay.acp.sdk.AcpService;
import com.unionpay.acp.sdk.LogUtil;
import com.unionpay.acp.sdk.SDKConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static com.unionpay.acp.demo.BackRcvResponse.getAllRequestParam;

/**
 * @description: 银联支付回调模版实现
 * @author: LL
 * @create: 2019-09-21 16:19
 */
@Component
public class UnionPayCallBackTemplate extends AbstractPayCallBackTemplate {
    @Autowired
    private PaymentTransactionMapper paymentTransactionMapper;

    @Override
    protected Map<String, String> verifySignature(HttpServletRequest req, HttpServletResponse resp) {
        LogUtil.writeLog("BackRcvResponse接收后台通知开始");

        String encoding = req.getParameter(SDKConstants.param_encoding);
        // 获取银联通知服务器发送的后台通知参数
        Map<String, String> reqParam = getAllRequestParam(req);
        LogUtil.printRequestLog(reqParam);

        // 重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
        if (!AcpService.validate(reqParam, encoding)) {
            LogUtil.writeLog("验证签名结果[失败].");
            reqParam.put(PayConstant.RESULT_NAME, PayConstant.RESULT_PAYCODE_201);
        } else {
            LogUtil.writeLog("验证签名结果[成功].");
            // 【注：为了安全验签成功才应该写商户的成功处理逻辑】交易成功，更新商户订单状态
            // 获取后台通知的数据，其他字段也可用类似方式获取
            String orderId = reqParam.get("orderId");
            reqParam.put("paymentId", orderId);
            reqParam.put(PayConstant.RESULT_NAME, PayConstant.RESULT_PAYCODE_200);
        }
        LogUtil.writeLog("BackRcvResponse接收后台通知结束");
        return reqParam;
    }

    @Override
    protected String failSuccess() {
        return PayConstant.YINLIAN_RESULT_FAIL;
    }

    @Override
    public String successResult() {
        return PayConstant.YINLIAN_RESULT_SUCCESS;
    }

    @Override
    protected String asyncService(Map<String, String> verifySignature) {
        String paymentId = verifySignature.get("orderId");
        String respCode = verifySignature.get("respCode");
        // 判断respCode是否成功
        if (!respCode.equals(PayConstant.YINLIAN_RESPCODE_00) && !respCode.equals(PayConstant.YINLIAN_RESPCODE_A6)) {
            // 失败
            return failSuccess();
        }
        // 网络延迟重试的情况，查询状态支付状态，避免重复增加积分等问题(幂等性问题)
        PaymentTransactionEntity paymentTransactionEntity = paymentTransactionMapper.selectByPaymentId(paymentId);
        if (paymentTransactionEntity.getPaymentStatus().equals(PayConstant.PAY_STATUS_SUCCESS)) {
            return successResult();
        }
        // 修改为已支付状态
        paymentTransactionMapper.updatePaymentStatus(
                PayConstant.PAY_STATUS_SUCCESS + "", paymentId);
        return successResult();
    }

}