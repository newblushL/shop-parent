package com.ll.payment.service;

import com.ll.base.BaseResponse;
import com.ll.payment.output.dto.PayMentTransacDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @description: 支付交易信息接口
 * @author: LL
 * @create: 2019-09-20 15:21
 */
public interface PayMentTransacInfoService {

    /**
     * @Author: LL
     * @Description: 根据Token获取待支付记录
     * @Date: 2019-09-20
     * @Param token:
     * @return: com.ll.base.BaseResponse<com.ll.payment.output.dto.PayMentTransacDTO>
     **/
    @GetMapping("/tokenByPayMentTransc")
    BaseResponse<PayMentTransacDTO> tokenByPayMentTransc(@RequestParam String token);
}