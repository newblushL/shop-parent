package com.ll.payment.service.impl;

import com.ll.base.BaseApiService;
import com.ll.integral.mapper.MapperUtils;
import com.ll.payment.mapper.PaymentChannelMapper;
import com.ll.payment.mapper.entity.PaymentChannelEntity;
import com.ll.payment.output.dto.PaymentChannelDTO;
import com.ll.payment.service.PayMentChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description: 支付渠道实现类
 * @author: LL
 * @create: 2019-09-20 16:30
 */
@RestController
public class PayMentChannelServiceImpl extends BaseApiService<List<PaymentChannelDTO>> implements PayMentChannelService {

    @Autowired
    private PaymentChannelMapper paymentChannelMapper;

    @Override
    public List<PaymentChannelDTO> selectAll() {
        List<PaymentChannelEntity> paymentChannelEntities = paymentChannelMapper.selectAll();
        if (paymentChannelEntities != null && paymentChannelEntities.size() > 0) {
            return MapperUtils.mapAsList(paymentChannelEntities, PaymentChannelDTO.class);
        }
        return null;
    }
}