package com.ll.pay.feign;

import com.ll.payment.service.PayMentContextService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @description: 支付提交表单Feign客户端
 * @author: LL
 * @create: 2019-09-21 17:22
 */
@FeignClient("app-pay")
public interface PayMentContextServiceFeign extends PayMentContextService {
}