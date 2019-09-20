package com.ll.pay.feign;

import com.ll.payment.service.PayMentChannelService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @description: 支付渠道Feign客户端
 * @author: LL
 * @create: 2019-09-20 16:40
 */
@FeignClient("app-pay")
public interface PayMentChannelServiceFeign extends PayMentChannelService {
}