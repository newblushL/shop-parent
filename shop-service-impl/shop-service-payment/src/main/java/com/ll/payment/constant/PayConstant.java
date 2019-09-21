package com.ll.payment.constant;

/**
 * @description: 支付回调常量
 * @author: LL
 * @create: 2019-09-20 20:40
 */

public interface PayConstant {

    String RESULT_NAME = "result";
    String RESULT_PAYCODE_201 = "201";
    String RESULT_PAYCODE_200 = "200";
    /**
     * 已经支付成功状态
     */
    Integer PAY_STATUS_SUCCESS = 1;
    /**
     * 返回银联通知成功
     */
    String YINLIAN_RESULT_SUCCESS = "ok";
    /**
     * 返回银联失败通知
     */
    String YINLIAN_RESULT_FAIL = "fail";

    /**
     * 银联响应成功码00
     */
    String YINLIAN_RESPCODE_00 = "00";
    /**
     * 银联响应成功码A6
     */
    String YINLIAN_RESPCODE_A6 = "A6";

}
