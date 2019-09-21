package com.ll.payment.callback.template.factory;

import com.ll.core.utils.SpringContextUtil;
import com.ll.payment.callback.template.AbstractPayCallBackTemplate;

/**
 * @description: 获取集体实现的模版工厂
 * @author: LL
 * @create: 2019-09-21 16:43
 */
public class TemplateFactory {
    public static AbstractPayCallBackTemplate getAbstractPayCallBackTemplate(String beanId) {
        return (AbstractPayCallBackTemplate) SpringContextUtil.getBean(beanId);
    }
}