package com.ll.payment.service;

import com.ll.payment.output.dto.PaymentChannelDTO;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @description: 支付渠道接口
 * @author: LL
 * @create: 2019-09-20 16:24
 */
public interface PayMentChannelService {


    /**
     * @Author: LL
     * @Description: 获取全部支付渠道
     * @Date: 2019-09-20
     * @return: com.ll.base.BaseResponse<java.util.List<com.ll.payment.service.PayMentChannel>>
     **/
    @GetMapping("/selectAll")
    List<PaymentChannelDTO> selectAll();
}