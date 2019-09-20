package com.ll.payment.factory;

import com.ll.payment.strategy.PayStrategy;
import org.apache.commons.lang.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: 初始化策略工厂
 * @author: LL
 * @create: 2019-09-20 20:40
 */
public class StrategyFactory {

    private static Map<String, PayStrategy> strategyBean = new ConcurrentHashMap<String, PayStrategy>();

    public static PayStrategy getPayStrategy(String classAddres) {
        if (StringUtils.isBlank(classAddres)) {
            return null;
        }
        try {
            // 单例
            PayStrategy payStrategyBean = strategyBean.get(classAddres);
            if (payStrategyBean != null) {
                return payStrategyBean;
            }
            // java反射机制
            Class<?> forName = Class.forName(classAddres);
            // 反射机制初始化对象
            PayStrategy payStrategy = (PayStrategy) forName.newInstance();
            strategyBean.put(classAddres, payStrategy);
            return payStrategy;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }
}