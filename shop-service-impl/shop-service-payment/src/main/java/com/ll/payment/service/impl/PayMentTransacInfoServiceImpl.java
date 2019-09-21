package com.ll.payment.service.impl;

import com.ll.base.BaseApiService;
import com.ll.base.BaseResponse;
import com.ll.core.bean.PropertyUtils;
import com.ll.core.token.GenerateToken;
import com.ll.payment.mapper.PaymentTransactionMapper;
import com.ll.payment.mapper.entity.PaymentTransactionEntity;
import com.ll.payment.output.dto.PayMentTransacDTO;
import com.ll.payment.service.PayMentTransacInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 支付交易信息实现类
 * @author: LL
 * @create: 2019-09-20 15:28
 */
@RestController
public class PayMentTransacInfoServiceImpl extends BaseApiService<PayMentTransacDTO> implements PayMentTransacInfoService {

    @Autowired
    private GenerateToken generateToken;

    @Autowired
    private PaymentTransactionMapper paymentTransactionMapper;

    @Override
    public BaseResponse<PayMentTransacDTO> tokenByPayMentTransac(String token) {
        if (StringUtils.isBlank(token)) {
            setResultError("Token不能为空！");
        }
        String payMentToken = generateToken.getToken(token);
        if (StringUtils.isBlank(payMentToken)) {
            setResultError("Token已失效！");
        }
        PaymentTransactionEntity paymentTransactionEntity =
                paymentTransactionMapper.selectById(Long.parseLong(payMentToken));
        if (paymentTransactionEntity == null) {
            setResultError("未查询到支付记录!");
        }
        return setResultSuccess(PropertyUtils.doToDto(paymentTransactionEntity,
                PayMentTransacDTO.class));
    }
}