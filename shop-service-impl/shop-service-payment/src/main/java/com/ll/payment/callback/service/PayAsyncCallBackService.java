package com.ll.payment.callback.service;

import com.ll.payment.callback.template.AbstractPayCallBackTemplate;
import com.ll.payment.callback.template.factory.TemplateFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 支付回调
 * @author: LL
 * @create: 2019-09-21 16:49
 */
@RestController
public class PayAsyncCallBackService {

    /**
     * Spring容器注入银联异步回调名称
     */
    private static final String UNIONPAYCALLBACK_TEMPLATE = "unionPayCallBackTemplate";

    /**
     * @Author: LL
     * @Description: 银联异步回调接口执行代码
     * @Date: 2019-09-21
     * @Param req:
     * @Param reps:
     * @return: java.lang.String
     **/
    @RequestMapping("/unionPayAsynCallback")
    public String unionPayAsyncCallbackTemplate(HttpServletRequest req,
                                                HttpServletResponse reps) {
        AbstractPayCallBackTemplate abstractPayCallBackTemplate =
                TemplateFactory.getAbstractPayCallBackTemplate(UNIONPAYCALLBACK_TEMPLATE);
        return abstractPayCallBackTemplate.payAsyncCallBack(req, reps);
    }
}