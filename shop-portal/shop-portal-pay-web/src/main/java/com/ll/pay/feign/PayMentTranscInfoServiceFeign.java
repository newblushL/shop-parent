package com.ll.pay.feign;

import com.ll.payment.service.PayMentTransacInfoService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @description: 支付交易信息Feign客户端
 * @author: LL
 * @create: 2019-09-20 15:50
 */
@FeignClient("app-pay")
public interface PayMentTranscInfoServiceFeign extends PayMentTransacInfoService {
}