package com.ll.payment.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ll.base.BaseApiService;
import com.ll.base.BaseResponse;
import com.ll.payment.factory.StrategyFactory;
import com.ll.payment.mapper.PaymentChannelMapper;
import com.ll.payment.mapper.entity.PaymentChannelEntity;
import com.ll.payment.output.dto.PayMentTransacDTO;
import com.ll.payment.service.PayMentContextService;
import com.ll.payment.service.PayMentTransacInfoService;
import com.ll.payment.strategy.PayStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: PayContextService实现类
 * @author: LL
 * @create: 2019-09-20 19:43
 */
@RestController
public class PayMentContextServiceImpl extends BaseApiService<JSONObject> implements PayMentContextService {

    @Autowired
    private PaymentChannelMapper paymentChannelMapper;

    @Autowired
    private PayMentTransacInfoService payMentTransacInfoService;

    @Override
    public BaseResponse<JSONObject> toPayHtml(String channelId, String payToken) {
        PaymentChannelEntity paymentChannelEntity = paymentChannelMapper.selectBychannelId(channelId);
        if (paymentChannelEntity == null) {
            return setResultError("没有查询到支付渠道");
        }
        BaseResponse<PayMentTransacDTO> payMentTransacDTOBaseResponse =
                payMentTransacInfoService.tokenByPayMentTransac(payToken);
        if (!isSuccess(payMentTransacDTOBaseResponse)) {
            return setResultError(payMentTransacDTOBaseResponse.getMsg());
        }
        PayMentTransacDTO payMentTransacDTO = payMentTransacDTOBaseResponse.getData();
        //反射机制
        String classAddres = paymentChannelEntity.getClassAddres();
        PayStrategy payStrategy = StrategyFactory.getPayStrategy(classAddres);
        String payHtml = payStrategy.toPayHtml(paymentChannelEntity, payMentTransacDTO);
        JSONObject data = new JSONObject();
        data.put("payHtml", payHtml);
        return setResultSuccess(data);
    }
}
